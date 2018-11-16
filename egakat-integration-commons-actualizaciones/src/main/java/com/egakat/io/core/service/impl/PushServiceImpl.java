package com.egakat.io.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import com.egakat.io.core.dto.ActualizacionDto;
import com.egakat.io.core.dto.ErrorIntegracionDto;
import com.egakat.io.core.enums.EstadoIntegracionType;
import com.egakat.io.core.service.api.PushService;
import com.egakat.io.core.service.api.crud.ActualizacionCrudService;
import com.egakat.io.core.service.api.crud.ErrorIntegracionCrudService;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract public class PushServiceImpl<O, R> implements PushService {

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

	@Override
	public void push() {
		val actualizaciones = getPendientes();

		int i = 1;
		int n = actualizaciones.size();
		for (val actualizacion : actualizaciones) {
			log(actualizacion, i, n);

			val errores = new ArrayList<ErrorIntegracionDto>();

			push(actualizacion, errores);

			if (!errores.isEmpty()) {
				try {
					if (hasErrors(actualizacion, errores)) {
						onError(actualizacion, errores);
						updateOnError(actualizacion, errores);
					} else {
						onRetry(actualizacion, errores);
					}
				} catch (RuntimeException e) {
					log.error("Exception:", e);
				}
			}

			i++;
		}
	}

	abstract protected List<ActualizacionDto> getPendientes();

	abstract protected void push(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

	protected void onError(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		actualizacion.setEstadoIntegracion(EstadoIntegracionType.ERROR_CARGUE);
		actualizacion.setReintentos(0);
	}

	protected void updateOnError(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		getActualizacionesService().update(actualizacion, actualizacion.getEstadoIntegracion(), errores);
	}

	protected boolean shouldBeRetried(RuntimeException e, ActualizacionDto actualizacion) {
		boolean result = false;

		if (e instanceof HttpStatusCodeException) {
			if (((HttpStatusCodeException) e).getStatusCode() == HttpStatus.GATEWAY_TIMEOUT) {
				result = true;
			}
		}

		return result;
	}

	protected void onRetry(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {

	}

	protected void updateOnRetry(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		getActualizacionesService().update(actualizacion, actualizacion.getEstadoIntegracion(), errores);
	}

	protected boolean hasErrors(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		boolean result = false;
		if (!errores.isEmpty()) {
			if (actualizacion.getReintentos() == 0 || actualizacion.getReintentos() < getNumeroMaximoIntentos()) {
				result = true;
			}
		}
		return result;
	}

	protected int getNumeroMaximoIntentos() {
		return 30;
	}

	protected void log(ActualizacionDto a, int i, int n) {
		val format = "integracion={}, correlacion={}, id externo={}: {} de {}.";
		log.debug(format, a.getIntegracion(), a.getCorrelacion(), a.getIdExterno(), i, n);
	}

}