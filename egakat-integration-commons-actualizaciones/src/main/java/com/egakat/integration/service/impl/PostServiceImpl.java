package com.egakat.integration.service.impl;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.left;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import com.egakat.core.web.client.exception.ReintentableException;
import com.egakat.integration.dto.ActualizacionDto;
import com.egakat.integration.dto.ErrorIntegracionDto;
import com.egakat.integration.dto.IntegracionEntityDto;
import com.egakat.integration.service.api.PostService;
import com.egakat.integration.service.api.crud.ActualizacionCrudService;
import com.egakat.integration.service.api.crud.ErrorIntegracionCrudService;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class PostServiceImpl<I, O, R, ID> implements PostService<I, ID> {

	@Autowired
	private ActualizacionCrudService actualizacionesService;

	@Autowired
	private ErrorIntegracionCrudService erroresService;

	protected ActualizacionCrudService getActualizacionesService() {
		return actualizacionesService;
	}

	protected ErrorIntegracionCrudService getErroresService() {
		return erroresService;
	}

	protected abstract String getIntegracion();

	protected String getOperacion() {
		val result = String.format("POST %s", getIntegracion());
		return result;
	}

	public Optional<ID> post(I input) {
		val correlacion = defaultCorrelacion();
		return post(input, correlacion);
	}

	public Optional<ID> post(I input, String correlacion) {
		Optional<ID> result = Optional.empty();

		val optional = enqueue(correlacion, input);

		if (optional.isPresent()) {
			val errores = new ArrayList<ErrorIntegracionDto>();
			val actualizacion = optional.get();

			boolean retry = false;

			try {
				result = post(input, actualizacion, errores);
			} catch (RuntimeException e) {
				if (retry(actualizacion, e)) {
					retry = true;
					actualizacion.setReintentos(actualizacion.getReintentos() + 1);
				}

				val error = getErroresService().error(actualizacion, "", retry, e);
				errores.add(error);
			}

			if (!errores.isEmpty()) {
				if (retry) {
					onRetry(actualizacion, errores);
					updateOnRetry(actualizacion, errores);
				} else {
					onError(actualizacion, errores);
					updateOnError(actualizacion, errores);
				}
			}
		}

		return result;
	}

	protected Optional<ActualizacionDto> enqueue(String correlacion, I input) {
		val model = asActualizacion(correlacion, input);

		try {
			val result = getActualizacionesService().enqueue(model);
			switch (result.getEstadoIntegracion()) {
			case NO_PROCESADO:
				return Optional.of(result);
			default:
				break;
			}
		} catch (RuntimeException e) {
			getErroresService().create(model, "", e);
		}
		return Optional.empty();
	}

	protected abstract ActualizacionDto asActualizacion(String correlacion, I input);

	protected Optional<ID> post(I input, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		validateInput(input, actualizacion, errores);

		if (errores.isEmpty()) {
			O output = asOutput(input, actualizacion, errores);

			if (errores.isEmpty()) {
				validateOutput(output, input, actualizacion, errores);

				if (errores.isEmpty()) {
					val discard = shouldBeDiscarded(output, input, actualizacion);

					if (!discard) {
						R result = push(output, input, actualizacion, errores);

						if (errores.isEmpty()) {
							onSuccess(result, output, input, actualizacion);
							ID id = updateOnSuccess(result, output, input, actualizacion);
							return Optional.of(id);
						}
					} else {
						onDiscard(output, input, actualizacion);
						updateOnDiscard(output, input, actualizacion);
					}
				}
			}
		}

		return Optional.empty();
	}

	protected void validateInput(I input, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {

	}

	protected abstract O asOutput(I input, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

	protected void validateOutput(O output, I input, ActualizacionDto actualizacion,
			List<ErrorIntegracionDto> errores) {

	}

	protected boolean shouldBeDiscarded(O output, I input, ActualizacionDto actualizacion) {
		return false;
	}

	protected abstract R push(O output, I input, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

	protected abstract void onSuccess(R result, O output, I input, ActualizacionDto actualizacion);

	protected abstract ID updateOnSuccess(R result, O output, I input, ActualizacionDto actualizacion);

	protected void onDiscard(O output, I input, ActualizacionDto actualizacion) {

	}

	protected void updateOnDiscard(O output, I input, ActualizacionDto actualizacion) {
		getActualizacionesService().update(actualizacion);
	}

	protected abstract void onError(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

	protected void updateOnError(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		try {
			getActualizacionesService().update(actualizacion, actualizacion.getEstadoIntegracion(), errores);
		} catch (RuntimeException e) {
			log.error("Exception:", e);
		}
	}

	protected void onRetry(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {

	}

	protected void updateOnRetry(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		try {
			getActualizacionesService().update(actualizacion, actualizacion.getEstadoIntegracion(), errores);
		} catch (RuntimeException e) {
			log.error("Exception:", e);
		}
	}

	protected boolean retry(ActualizacionDto actualizacion, RuntimeException e) {
		boolean result = false;

		if (actualizacion.getReintentos() < getNumeroMaximoIntentos()) {
			result = isRetryableException(e);
		}

		return result;
	}

	protected int getNumeroMaximoIntentos() {
		return 30;
	}

	protected boolean isRetryableException(RuntimeException e) {
		boolean result = false;

		if (e instanceof ReintentableException) {
			result = true;
		}

		if (e instanceof ResourceAccessException) {
			result = true;
		}

		return result;
	}

	protected void log(ActualizacionDto actualizacion, int i, int n) {
		if (actualizacion != null) {
			val format = "{}: Procesando {} de {}: id externo={}, contenido={} ";
			log.debug(format, getOperacion(), i, n, actualizacion.getIntegracion(), actualizacion.getIdExterno(),
					actualizacion);
		}
	}

	protected String normalizar(String str, int len) {
		val result = left(defaultString(str), len);
		return result;
	}

	protected String defaultCorrelacion() {
		val result = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
		return result;
	}
	
	protected ErrorIntegracionDto errorAtributoRequeridoNoSuministrado(IntegracionEntityDto input, String codigo,
			String... arg) {
		val mensaje = "Este atributo no admite valores nulos o vacios";
		val result = getErroresService().error(input, codigo, mensaje, arg);
		return result;
	}
}
