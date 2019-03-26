package com.egakat.integration.service.impl.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.egakat.integration.domain.IntegracionEntity;
import com.egakat.integration.dto.ActualizacionDto;
import com.egakat.integration.dto.ErrorIntegracionDto;
import com.egakat.integration.dto.IntegracionEntityDto;
import com.egakat.integration.enums.EstadoIntegracionType;
import com.egakat.integration.enums.EstadoNotificacionType;
import com.egakat.integration.service.api.crud.ActualizacionCrudService;
import com.egakat.integration.service.api.crud.IntegracionEntityCrudService;

import lombok.val;

public abstract class IntegracionEntityCrudServiceImpl<E extends IntegracionEntity, M extends IntegracionEntityDto>
		extends AbstractIntegracionEntityCrudServiceImpl<E, M> implements IntegracionEntityCrudService<M> {

	@Autowired
	private ActualizacionCrudService actualizacionesService;

	public ActualizacionCrudService getActualizacionesService() {
		return actualizacionesService;
	}

	@Override
	public M create(M model, ActualizacionDto actualizacion, EstadoIntegracionType estado) {
		if (estado.isError()) {
			val format = "Se esta intentado actualizar al estado %s. Esta operación no admite estados de error";
			throw new RuntimeException(String.format(format, estado.toString()));
		}

		actualizacion.setEstadoIntegracion(estado);
		actualizacion.setEstadoNotificacion(EstadoNotificacionType.NOTIFICAR);
		getActualizacionesService().update(actualizacion);
		val result = create(model);
		return result;
	}

	@Override
	public M create(M model, ActualizacionDto actualizacion, EstadoIntegracionType estado,
			List<ErrorIntegracionDto> errores) {

		getActualizacionesService().update(actualizacion, estado, errores);
		val result = create(model);
		return result;
	}

	@Override
	public M update(M model, ActualizacionDto actualizacion, EstadoIntegracionType estado) {
		if (estado.isError()) {
			val format = "Se esta intentado actualizar al estado %s. Esta operación no admite estados de error";
			throw new RuntimeException(String.format(format, estado.toString()));
		}

		actualizacion.setEstadoIntegracion(estado);
		actualizacion.setEstadoNotificacion(EstadoNotificacionType.NOTIFICAR);
		getActualizacionesService().update(actualizacion);
		val result = update(model);
		return result;
	}

	@Override
	public M update(M model, ActualizacionDto actualizacion, EstadoIntegracionType estado,
			List<ErrorIntegracionDto> errores) {

		getActualizacionesService().update(actualizacion, estado, errores);
		val result = update(model);
		return result;
	}
}