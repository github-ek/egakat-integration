package com.egakat.integration.service.impl;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.left;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import com.egakat.core.web.client.exception.ReintentableException;
import com.egakat.integration.dto.ActualizacionDto;
import com.egakat.integration.dto.ErrorIntegracionDto;
import com.egakat.integration.service.api.PushService;
import com.egakat.integration.service.api.crud.ActualizacionCrudService;
import com.egakat.integration.service.api.crud.ErrorIntegracionCrudService;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class PushServiceImpl<I, O, R> implements PushService {

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
		val result = String.format("PUSH %s", getIntegracion());
		return result;
	}

	@Override
	public boolean push() {
		boolean result = true;
		val operacion = getOperacion();

		val actualizaciones = getPendientes();
		int i = 1;
		int n = actualizaciones.size();

		log.info("{}: numero de pendientes={}", operacion, n);

		if (n > 0) {
			init(actualizaciones);

			for (val actualizacion : actualizaciones) {
				val errores = new ArrayList<ErrorIntegracionDto>();
				boolean retry = false;

				try {
					log(actualizacion, i, n);

					push(actualizacion, errores);
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
						result = false;
						onRetry(actualizacion, errores);
						updateOnRetry(actualizacion, errores);
					} else {
						onError(actualizacion, errores);
						updateOnError(actualizacion, errores);
					}
				}

				i++;
			}
		}
		return result;
	}

	protected void init(List<ActualizacionDto> actualizaciones) {

	}

	protected abstract List<ActualizacionDto> getPendientes();

	protected void push(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		I input = getInput(actualizacion, errores);

		if (errores.isEmpty()) {
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
								updateOnSuccess(result, output, input, actualizacion);
							}
						} else {
							onDiscard(output, input, actualizacion);
							updateOnDiscard(output, input, actualizacion);
						}
					}
				}
			}
		}
	}

	protected abstract I getInput(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

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

	protected abstract void updateOnSuccess(R result, O output, I input, ActualizacionDto actualizacion);

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
}