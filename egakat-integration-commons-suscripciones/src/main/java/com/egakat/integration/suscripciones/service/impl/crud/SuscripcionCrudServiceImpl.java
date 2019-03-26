package com.egakat.integration.suscripciones.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.suscripciones.domain.Suscripcion;
import com.egakat.integration.suscripciones.dto.SuscripcionDto;
import com.egakat.integration.suscripciones.repository.SuscripcionRepository;
import com.egakat.integration.suscripciones.service.api.crud.SuscripcionCrudService;

import lombok.val;

@Service
public class SuscripcionCrudServiceImpl extends CrudServiceImpl<Suscripcion, SuscripcionDto, Long>
		implements SuscripcionCrudService {
	@Autowired
	private SuscripcionRepository<Suscripcion> repository;

	@Override
	protected SuscripcionRepository<Suscripcion> getRepository() {
		return repository;
	}

	@Override
	protected SuscripcionDto asModel(Suscripcion entity) {
		val model = newModel();

		mapModel(entity, model);

		model.setId(entity.getId());
		model.setSuscripcion(entity.getSuscripcion());
		model.setIdExterno(entity.getIdExterno());
		model.setEstadoSuscripcion(entity.getEstadoSuscripcion());
		
		model.setArg0(entity.getArg0());
		model.setArg1(entity.getArg1());
		model.setArg2(entity.getArg2());
		model.setArg3(entity.getArg3());
		model.setArg4(entity.getArg4());
		model.setArg5(entity.getArg5());
		model.setArg6(entity.getArg6());
		model.setArg7(entity.getArg7());
		model.setArg8(entity.getArg8());
		model.setArg9(entity.getArg9());

		return model;
	}

	@Override
	protected SuscripcionDto newModel() {
		return new SuscripcionDto();
	}

	@Override
	protected Suscripcion mergeEntity(SuscripcionDto model, Suscripcion entity) {
		entity.setSuscripcion(model.getSuscripcion());
		entity.setIdExterno(model.getIdExterno());
		entity.setEstadoSuscripcion(model.getEstadoSuscripcion());
		
		entity.setArg0(model.getArg0());
		entity.setArg1(model.getArg1());
		entity.setArg2(model.getArg2());
		entity.setArg3(model.getArg3());
		entity.setArg4(model.getArg4());
		entity.setArg5(model.getArg5());
		entity.setArg6(model.getArg6());
		entity.setArg7(model.getArg7());
		entity.setArg8(model.getArg8());
		entity.setArg9(model.getArg9());

		return entity;

	}

	@Override
	protected Suscripcion newEntity() {
		return new Suscripcion();
	}

	@Override
	public boolean exists(String suscripcion, String idExterno) {
		val result = getRepository().existsBySuscripcionAndIdExterno(suscripcion, idExterno);
		return result;
	}

	@Override
	public SuscripcionDto findOneBySuscripcionAndIdExterno(String suscripcion, String idExterno) {
		val entities = getRepository().findAllBySuscripcionAndIdExterno(suscripcion, idExterno);
		val entity = entities.findFirst().get();
		val result = asModel(entity);
		return result;
	}
}