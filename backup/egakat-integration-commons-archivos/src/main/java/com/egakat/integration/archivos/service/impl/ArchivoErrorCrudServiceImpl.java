package com.egakat.integration.archivos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.archivos.domain.ArchivoError;
import com.egakat.integration.archivos.dto.ArchivoErrorDto;
import com.egakat.integration.archivos.repository.ArchivoErrorRepository;
import com.egakat.integration.archivos.repository.ArchivoRepository;
import com.egakat.integration.archivos.service.api.ArchivoErrorCrudService;

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
		val result = newModel();
		mapModel(entity, result);

		result.setIdArchivo(entity.getArchivo().getId());
		result.setNumeroLinea(entity.getNumeroLinea());
		result.setCodigo(entity.getCodigo());
		result.setMensaje(entity.getMensaje());
		result.setDatos(entity.getDatos());
				
		return result;
	}
	
	@Override
	protected ArchivoErrorDto newModel() {
		return new ArchivoErrorDto();
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