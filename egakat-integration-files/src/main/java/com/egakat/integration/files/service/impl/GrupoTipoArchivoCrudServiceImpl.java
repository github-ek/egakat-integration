package com.egakat.integration.files.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.files.domain.GrupoTipoArchivo;
import com.egakat.integration.files.dto.GrupoTipoArchivoDto;
import com.egakat.integration.files.repository.GrupoTipoArchivoRepository;
import com.egakat.integration.files.service.api.GrupoTipoArchivoCrudService;

import lombok.val;

@Service
public class GrupoTipoArchivoCrudServiceImpl
		extends CrudServiceImpl<GrupoTipoArchivo, GrupoTipoArchivoDto, Long>
		implements GrupoTipoArchivoCrudService {
	
	@Autowired
	private GrupoTipoArchivoRepository repository;

	@Override
	protected GrupoTipoArchivoRepository getRepository() {
		return repository;
	}

	@Override
	protected GrupoTipoArchivoDto asModel(GrupoTipoArchivo entity) {
		// @formatter:off
		val result = GrupoTipoArchivoDto
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
	protected GrupoTipoArchivo asEntity(GrupoTipoArchivoDto model, GrupoTipoArchivo entity) {

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