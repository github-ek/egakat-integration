package com.egakat.integration.maps.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.maps.domain.GrupoMapa;
import com.egakat.integration.maps.dto.GrupoMapaDto;
import com.egakat.integration.maps.repository.GrupoMapaRepository;
import com.egakat.integration.maps.service.api.GrupoMapaCrudService;

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
		// @formatter:off
		val result = GrupoMapaDto
				.builder()
				.id(entity.getId())
				.codigo(entity.getCodigo())
				.nombre(entity.getNombre())
				.descripcion(entity.getDescripcion())
				.ordinal(entity.getOrdinal())
				.activo(entity.isActivo())
				.version(entity.getVersion())
				.creadoPor(entity.getCreadoPor())
				.fechaCreacion(entity.getFechaCreacion())
				.modificadoPor(entity.getModificadoPor())
				.fechaModificacion(entity.getFechaModificacion())

				.build();
		// @formatter:on
		return result;
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