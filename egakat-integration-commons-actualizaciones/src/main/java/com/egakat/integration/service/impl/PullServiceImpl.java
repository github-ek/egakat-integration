package com.egakat.integration.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import com.egakat.core.web.client.exception.ReintentableException;
import com.egakat.integration.dto.ActualizacionDto;
import com.egakat.integration.dto.ErrorIntegracionDto;
import com.egakat.integration.enums.EstadoIntegracionType;
import com.egakat.integration.service.api.PullService;
import com.egakat.integration.service.api.crud.ActualizacionCrudService;
import com.egakat.integration.service.api.crud.ErrorIntegracionCrudService;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract public class PullServiceImpl<I> implements PullService {

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
		val result = String.format("PULL %s", getIntegracion());
		return result;
	}

	protected String defaultCorrelacion() {
		val result = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
		return result;
	}

	protected void init() {

	}

	protected List<ActualizacionDto> enqueue(String correlacion, List<I> inputs) {
		val result = new ArrayList<ActualizacionDto>();

		int i = 1;
		int n = inputs.size();
		for (val input : inputs) {
			val errores = new ArrayList<ErrorIntegracionDto>();

			val actualizacion = asModel(correlacion, input);
			try {
				log(actualizacion, i, n);

				enqueue(input, actualizacion, errores);
			} catch (RuntimeException e) {
				val error = getErroresService().error(actualizacion, "", e);
				errores.add(error);
			}

			if (errores.isEmpty()) {
				result.add(actualizacion);
			} else {
				onError(input, actualizacion, errores);
				updateOnError(actualizacion, errores);
			}

			i++;
		}

		return result;
	}

	protected void enqueue(I input, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		validateInput(input, actualizacion, errores);

		if (errores.isEmpty()) {
			val discard = shouldBeDiscarded(input, actualizacion);

			if (!discard) {
				onSuccess(input, actualizacion);
				updateOnSuccess(input, actualizacion);
			} else {
				onDiscard(input, actualizacion);
				updateOnDiscard(input, actualizacion);
			}
		}
	}

	abstract protected ActualizacionDto asModel(String correlacion, I input);

	protected void validateInput(I input, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {

	}

	protected boolean shouldBeDiscarded(I input, ActualizacionDto actualizacion) {
		return false;
	}

	protected void onSuccess(I input, ActualizacionDto actualizacion) {

	}

	protected void updateOnSuccess(I input, ActualizacionDto actualizacion) {
		getActualizacionesService().enqueue(actualizacion);
	}

	protected void onDiscard(I input, ActualizacionDto actualizacion) {

	}

	protected void updateOnDiscard(I input, ActualizacionDto actualizacion) {

	}

	protected void onError(I input, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		actualizacion.setEstadoIntegracion(EstadoIntegracionType.ERROR_ESTRUCTURA);
		actualizacion.setReintentos(0);
	}

	protected void updateOnError(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		try {
			getActualizacionesService().update(actualizacion, actualizacion.getEstadoIntegracion(), errores);
		} catch (RuntimeException e) {
			log.error("Exception:", e);
		}
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
			val format = "{}: Registrando {} de {}: id externo={}, contenido={} ";
			log.debug(format, getOperacion(), i, n, actualizacion.getIdExterno(), actualizacion);
		}
	}
}