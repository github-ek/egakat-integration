package com.egakat.integration.service.impl.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.integration.domain.Actualizacion;
import com.egakat.integration.dto.ActualizacionDto;
import com.egakat.integration.dto.ErrorIntegracionDto;
import com.egakat.integration.enums.EstadoIntegracionType;
import com.egakat.integration.enums.EstadoNotificacionType;
import com.egakat.integration.repository.ActualizacionRepository;
import com.egakat.integration.service.api.crud.ActualizacionCrudService;

import lombok.val;

@Service
public class ActualizacionCrudServiceImpl extends
		AbstractIntegracionEntityCrudServiceImpl<Actualizacion, ActualizacionDto> implements ActualizacionCrudService {

	@Autowired
	private ActualizacionRepository repository;

	@Override
	protected ActualizacionRepository getRepository() {
		return repository;
	}

	@Override
	protected ActualizacionDto asModel(Actualizacion entity) {
		val model = newModel();

		mapModel(entity, model);

		model.setId(entity.getId());
		model.setIntegracion(entity.getIntegracion());
		model.setCorrelacion(entity.getCorrelacion());
		model.setIdExterno(entity.getIdExterno());

		model.setEstadoIntegracion(entity.getEstadoIntegracion());
		model.setSubEstadoIntegracion(entity.getSubEstadoIntegracion());
		model.setEstadoNotificacion(entity.getEstadoNotificacion());
		model.setEntradasEnCola(entity.getEntradasEnCola());
		model.setReintentos(entity.getReintentos());

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
		model.setDatos(entity.getDatos());

		return model;
	}

	@Override
	protected ActualizacionDto newModel() {
		return new ActualizacionDto();
	}

	@Override
	protected Actualizacion mergeEntity(ActualizacionDto model, Actualizacion entity) {

		entity.setIntegracion(model.getIntegracion());
		entity.setCorrelacion(model.getCorrelacion());
		entity.setIdExterno(model.getIdExterno());

		entity.setEstadoIntegracion(model.getEstadoIntegracion());
		entity.setSubEstadoIntegracion(model.getSubEstadoIntegracion());
		entity.setEstadoNotificacion(model.getEstadoNotificacion());
		entity.setEntradasEnCola(model.getEntradasEnCola());
		entity.setReintentos(model.getReintentos());

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
		entity.setDatos(model.getDatos());

		return entity;
	}

	@Override
	protected Actualizacion newEntity() {
		return new Actualizacion();
	}

	@Override
	public List<ActualizacionDto> findAllByIntegracionAndEstadoIntegracionIn(String integracion,
			EstadoIntegracionType... estados) {

		val entities = getRepository().findAllByIntegracionAndEstadoIntegracionIn(integracion, estados);
		val result = asModels(entities);
		return result;
	}

	@Override
	public List<ActualizacionDto> findAllByIntegracionAndEstadoIntegracionAndSubEstadoIntegracionIn(String integracion,
			EstadoIntegracionType estadoIntegracion, String... subEstadosIntegracion) {

		val entities = getRepository().findAllByIntegracionAndEstadoIntegracionAndSubEstadoIntegracionIn(integracion,
				estadoIntegracion, subEstadosIntegracion);
		val result = asModels(entities);
		return result;
	}

	@Override
	public List<ActualizacionDto> findAllNoNotificadasByIntegracionAndEstadoIntegracionIn(String integracion,
			EstadoIntegracionType... estados) {

		val entities = getRepository().findAllByIntegracionAndEstadoIntegracionInAndEstadoNotificacion(integracion,
				estados, EstadoNotificacionType.NOTIFICAR);
		val result = asModels(entities);
		return result;
	}

	@Override
	public List<String> findAllCorrelacionesByEstadoIntegracionIn(List<EstadoIntegracionType> estados) {
		val result = getRepository().findAllCorrelacionesByEstadoIntegracionIn(estados);
		return result;
	}

	@Override
	public ActualizacionDto enqueue(ActualizacionDto model) {
		// @formatter:off
		val optional = getRepository().findAllByIntegracionAndIdExterno(model.getIntegracion(), model.getIdExterno())
				.sorted((a,b)-> Long.compare(b.getId(), a.getId()))
				.findFirst();
		// @formatter:on
		ActualizacionDto result;

		if (!optional.isPresent()) {
			result = create(model);
		} else {
			Actualizacion entity = optional.get();
			boolean test = EstadoIntegracionType.PROCESADO.equals(entity.getEstadoIntegracion());
			test |= EstadoIntegracionType.DESCARTADO.equals(entity.getEstadoIntegracion());

			if (test) {
				entity.setEstadoIntegracion(EstadoIntegracionType.NO_PROCESADO);
				entity.setEntradasEnCola(0);
				entity.setReintentos(0);
				entity = getRepository().saveAndFlush(entity);
			} else {
				if (entity.getEntradasEnCola() == 0) {
					entity.setEntradasEnCola(entity.getEntradasEnCola() + 1);
					entity = getRepository().saveAndFlush(entity);
				}
			}

			result = asModel(entity);
		}
		return result;
	}

	@Override
	public ActualizacionDto update(ActualizacionDto model, EstadoIntegracionType estado,
			List<ErrorIntegracionDto> errores) {
		if (errores.isEmpty()) {
			throw new RuntimeException(
					"La colección de errores esta vacía. Se requiere al menos de un error para realizar esta operación");
		}

		model.setEstadoIntegracion(estado);
		if (estado.isError()) {
			model.setEstadoNotificacion(EstadoNotificacionType.NOTIFICAR);
		}

		getErroresService().create(errores);

		val result = update(model);
		return result;
	}

	@Override
	public ActualizacionDto updateErrorNotificacion(ActualizacionDto model, List<ErrorIntegracionDto> errores) {

		if (errores.isEmpty()) {
			throw new RuntimeException(
					"La colección de errores esta vacía. Se requiere al menos de un error para realizar esta operación");
		}

		model.setEstadoNotificacion(EstadoNotificacionType.ERROR);
		getErroresService().create(errores);

		val result = update(model);
		return result;
	}
}