package com.egakat.integration.files.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.files.domain.ArchivoError;
import com.egakat.integration.files.dto.ArchivoErrorDto;
import com.egakat.integration.files.repository.ArchivoErrorRepository;
import com.egakat.integration.files.repository.ArchivoRepository;
import com.egakat.integration.files.service.api.ArchivoErrorCrudService;

import lombok.val;

@Service
public class ArchivoErrorCrudServiceImpl
		extends CrudServiceImpl<ArchivoError, ArchivoErrorDto, Long>
		implements ArchivoErrorCrudService {
	
	@Autowired
	private ArchivoErrorRepository repository;

	@Autowired
	private ArchivoRepository archivoRepository;

	@Override
	protected ArchivoErrorRepository getRepository() {
		return repository;
	}

	@Override
	protected ArchivoErrorDto asModel(ArchivoError entity) {
		// @formatter:off
		val result = ArchivoErrorDto
				.builder()
				.id(entity.getId())
				.idArchivo(entity.getArchivo().getId())
				.numeroLinea(entity.getNumeroLinea())
				.codigo(entity.getCodigo())
				.mensaje(entity.getMensaje())
				.datos(entity.getDatos())
				
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
	protected ArchivoError mergeEntity(ArchivoErrorDto model, ArchivoError entity) {
		val archivo = archivoRepository.getOne(model.getIdArchivo());
		
		entity.setArchivo(archivo);
		entity.setNumeroLinea(model.getNumeroLinea());
		entity.setCodigo(model.getCodigo());
		entity.setMensaje(model.getMensaje());
		entity.setDatos(model.getDatos());

		return entity;
	}

	@Override
	protected ArchivoError newEntity() {
		return new ArchivoError();
	}

	@Override
	public List<ArchivoErrorDto> findAllByArchivoId(long archivo) {
		val entities = getRepository().findAllByArchivoId(archivo);
		val result = asModels(entities);
		return result;
	}
}