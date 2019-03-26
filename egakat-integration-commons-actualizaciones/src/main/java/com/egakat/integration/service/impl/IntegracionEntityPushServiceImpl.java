package com.egakat.integration.service.impl;

import java.util.List;

import com.egakat.integration.dto.ActualizacionDto;
import com.egakat.integration.dto.ErrorIntegracionDto;
import com.egakat.integration.dto.IntegracionEntityDto;
import com.egakat.integration.enums.EstadoIntegracionType;
import com.egakat.integration.service.api.crud.AbstractIntegracionEntityCrudService;

import lombok.val;

public abstract class IntegracionEntityPushServiceImpl<M extends IntegracionEntityDto, I,O, R>
		extends PushServiceImpl<I, O, R> {

	protected abstract AbstractIntegracionEntityCrudService<M> getCrudService();

	protected void push(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		try {
			val model = getModel(actualizacion, errores);

			if (errores.isEmpty()) {
				O output = asOutput(model, actualizacion, errores);

				if (errores.isEmpty()) {
					validateOutput(output, model, actualizacion, errores);

					if (errores.isEmpty()) {
						val discard = shouldBeDiscarded(output, model, actualizacion);

						if (!discard) {
							R response = push(output, model, actualizacion, errores);

							if (errores.isEmpty()) {
								onSuccess(response, output, model, actualizacion);
								updateOnSuccess(response, output, model, actualizacion);
							}
						} else {
							onDiscard(output, model, actualizacion);
							updateOnDiscard(output, model, actualizacion);
						}
					}
				}
			}
		} catch (RuntimeException e) {
			val error = getErroresService().error(actualizacion, "", e);
			errores.add(error);

			//val retry = shouldBeRetried(e, actualizacion);
			//if (retry) {
			//	actualizacion.setReintentos(actualizacion.getReintentos());
			//}
		}
	}

	protected abstract M getModel(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

	protected abstract O asOutput(M model, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

	protected void validateOutput(O output, M model, ActualizacionDto actualizacion,
			List<ErrorIntegracionDto> errores) {

	}

	protected abstract R push(O output, M model, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

	protected void onSuccess(R response, O output, M model, ActualizacionDto actualizacion) {
		actualizacion.setEstadoIntegracion(EstadoIntegracionType.CARGADO);
		actualizacion.setReintentos(0);
	}

	protected void updateOnSuccess(R response, O output, M model, ActualizacionDto actualizacion) {
		//getCrudService().update(model, actualizacion, actualizacion.getEstadoIntegracion());
	}

	protected boolean shouldBeDiscarded(O output, M model, ActualizacionDto actualizacion) {
		return false;
	}

	protected void onDiscard(O output, M model, ActualizacionDto actualizacion) {
		actualizacion.setSubEstadoIntegracion("DESCARTADO");
	}

	protected void updateOnDiscard(O output, M model, ActualizacionDto actualizacion) {
		getActualizacionesService().update(actualizacion);
	}
}