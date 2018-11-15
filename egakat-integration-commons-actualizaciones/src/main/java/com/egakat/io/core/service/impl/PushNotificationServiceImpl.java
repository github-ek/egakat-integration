package com.egakat.io.core.service.impl;

import java.util.List;

import com.egakat.io.core.dto.ActualizacionDto;
import com.egakat.io.core.dto.ErrorIntegracionDto;

import lombok.val;

public abstract class PushNotificationServiceImpl<O, R> extends PushServiceImpl<O, R> {

	protected void push(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		try {
			if (errores.isEmpty()) {
				O output = asOutput(actualizacion, errores);

				if (errores.isEmpty()) {
					validateOutput(output, actualizacion, errores);

					if (errores.isEmpty()) {
						val discard = shouldBeDiscarded(output, actualizacion);

						if (!discard) {
							R response = push(output, actualizacion, errores);

							if (errores.isEmpty()) {
								onSuccess(response, output, actualizacion);
								updateOnSuccess(response, output, actualizacion);
							}
						} else {
							onDiscard(output, actualizacion);
							updateOnDiscard(output, actualizacion);
						}
					}
				}
			}
		} catch (RuntimeException e) {
			val error = getErroresService().error(actualizacion, "", e);
			errores.add(error);

			val retry = shouldBeRetried(e, actualizacion);
			if (retry) {
				actualizacion.setReintentos(actualizacion.getReintentos());
			}
		}
	}

	protected abstract O asOutput(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

	protected void validateOutput(O output, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {

	}

	protected abstract R push(O output, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

	protected void onSuccess(R response, O output, ActualizacionDto actualizacion) {

	}

	protected void updateOnSuccess(R response, O output, ActualizacionDto actualizacion) {
		getActualizacionesService().update(actualizacion);
	}

	protected boolean shouldBeDiscarded(O output, ActualizacionDto actualizacion) {
		return false;
	}

	protected void onDiscard(O output, ActualizacionDto actualizacion) {
		actualizacion.setSubEstadoIntegracion("DESCARTADO");
	}

	protected void updateOnDiscard(O output, ActualizacionDto actualizacion) {
		getActualizacionesService().update(actualizacion);
	}
}