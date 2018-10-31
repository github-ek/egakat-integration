package com.egakat.integration.commons.tiposarchivos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.commons.tiposarchivo.dto.GrupoTipoArchivoDto;
import com.egakat.integration.commons.tiposarchivos.domain.GrupoTipoArchivo;
import com.egakat.integration.commons.tiposarchivos.repository.GrupoTipoArchivoRepository;
import com.egakat.integration.commons.tiposarchivos.service.api.GrupoTipoArchivoCrudService;

import lombok.val;

@Service
public class GrupoTipoArchivoCrudServiceImpl extends CrudServiceImpl<GrupoTipoArchivo, GrupoTipoArchivoDto, Long>
		implements GrupoTipoArchivoCrudService {

	@Autowired
	private GrupoTipoArchivoRepository repository;

	@Override
	protected GrupoTipoArchivoRepository getRepository() {
		return repository;
	}

	@Override
	protected GrupoTipoArchivoDto asModel(GrupoTipoArchivo entity) {
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
	protected GrupoTipoArchivoDto newModel() {
		return new GrupoTipoArchivoDto();
	}

	@Override
	protected GrupoTipoArchivo mergeEntity(GrupoTipoArchivoDto model, GrupoTipoArchivo entity) {

		entity.setCodigo(model.getCodigo());
		entity.setNombre(model.getNombre());
		entity.setDescripcion(model.getDescripcion());
		entity.setOrdinal(model.getOrdinal());
		entity.setActivo(model.isActivo());

		return entity;
	}

	@Override
	protected GrupoTipoArchivo newEntity() {
		return new GrupoTipoArchivo();
	}
}