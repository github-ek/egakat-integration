package com.egakat.io.core.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.egakat.io.core.dto.ActualizacionDto;
import com.egakat.io.core.dto.ErrorIntegracionDto;
import com.egakat.io.core.enums.EstadoIntegracionType;
import com.egakat.io.core.service.api.PullService;
import com.egakat.io.core.service.api.crud.ActualizacionCrudService;
import com.egakat.io.core.service.api.crud.ErrorIntegracionCrudService;

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

	protected String defaultCorrelacion() {
		val result = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
		return result;
	}

	protected void enqueue(String correlacion, List<I> inputs) {
		int i = 1;
		int n = inputs.size();
		for (val input : inputs) {
			val actualizacion = asModel(correlacion, input);
			log(actualizacion, i, n);

			val errores = new ArrayList<ErrorIntegracionDto>();

			enqueue(input, actualizacion, errores);

			if (!errores.isEmpty()) {
				try {
					onError(input, actualizacion, errores);
					updateOnError(actualizacion, errores);
				} catch (RuntimeException e) {
					log.error("Exception:", e);
				}
			}

			i++;
		}
	}

	protected void enqueue(I input, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		try {
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
		} catch (RuntimeException e) {
			val error = getErroresService().error(actualizacion, "", e);
			errores.add(error);
		}
	}

	abstract protected ActualizacionDto asModel(String correlacion, I input);

	protected void validateInput(I input, ActualizacionDto model, List<ErrorIntegracionDto> errores) {

	}

	protected boolean shouldBeDiscarded(I input, ActualizacionDto model) {
		return false;
	}

	protected void onSuccess(I input, ActualizacionDto model) {

	}

	protected void updateOnSuccess(I input, ActualizacionDto actualizacion) {
		getActualizacionesService().enqueue(actualizacion);
	}

	protected void onDiscard(I input, ActualizacionDto model) {

	}

	protected void updateOnDiscard(I input, ActualizacionDto actualizacion) {

	}

	protected void onError(I input, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		actualizacion.setEstadoIntegracion(EstadoIntegracionType.ERROR_ESTRUCTURA);
		actualizacion.setReintentos(0);
	}

	protected void updateOnError(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		getActualizacionesService().update(actualizacion, actualizacion.getEstadoIntegracion(), errores);
	}

	protected void log(ActualizacionDto a, int i, int n) {
		if (a != null) {
			val format = "integracion={}, correlacion={}, id externo={}: {} de {}.";
			log.debug(format, a.getIntegracion(), a.getCorrelacion(), a.getIdExterno(), i, n);
		}
	}
}