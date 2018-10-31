package com.egakat.integration.commons.mapas.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.commons.mapas.domain.GrupoMapa;
import com.egakat.integration.commons.mapas.dto.GrupoMapaDto;
import com.egakat.integration.commons.mapas.repository.GrupoMapaRepository;
import com.egakat.integration.commons.mapas.service.api.GrupoMapaCrudService;

import lombok.val;

@Service
public class GrupoMapaCrudServiceImpl extends CrudServiceImpl<GrupoMapa, GrupoMapaDto, Long>
		implements GrupoMapaCrudService {

	@Autowired
	private GrupoMapaRepository repository;

	@Override
	protected GrupoMapaRepository getRepository() {
		return repository;
	}

	@Override
	protected GrupoMapaDto asModel(GrupoMapa entity) {
		val result = newModel();
		mapModel(entity, result);

		result.setCodigo(entity.getCodigo());
		result.setNombre(entity.getNombre());
		result.setDescripcion(entity.getDescripcion());
		result.setOrdinal(entity.getOrdinal());
		result.setActivo(entity.isActivo());

		return result;
	}

	@Override
	protected GrupoMapaDto newModel() {
		return new GrupoMapaDto();
	}

	@Override
	protected GrupoMapa mergeEntity(GrupoMapaDto model, GrupoMapa entity) {

		entity.setCodigo(model.getCodigo());
		entity.setNombre(model.getNombre());
		entity.setDescripcion(model.getDescripcion());
		entity.setOrdinal(model.getOrdinal());
		entity.setActivo(model.isActivo());

		return entity;
	}

	@Override
	protected GrupoMapa newEntity() {
		return new GrupoMapa();
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public Optional<GrupoMapaDto> findByCodigo(String codigo) {
		val optional = getRepository().findByCodigo(codigo);
		val result = asModel(optional);
		return result;
	}
}