package com.egakat.integration.commons.archivos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.archivos.domain.TipoArchivo;
import com.egakat.integration.archivos.dto.TipoArchivoDto;
import com.egakat.integration.archivos.repository.TipoArchivoRepository;
import com.egakat.integration.commons.archivos.service.api.TipoArchivoCrudService;

import lombok.val;

@Service
public class TipoArchivoCrudServiceImpl extends CrudServiceImpl<TipoArchivo, TipoArchivoDto, Long>
		implements TipoArchivoCrudService {

	@Autowired
	private TipoArchivoRepository repository;

	@Override
	protected TipoArchivoRepository getRepository() {
		return repository;
	}

	@Override
	protected TipoArchivoDto asModel(TipoArchivo entity) {

		val result = newModel();
		mapModel(entity, result);

		result.setIdGrupoTipoArchivo(entity.getIdGrupoTipoArchivo());
		result.setCodigo(entity.getCodigo());
		result.setNombre(entity.getNombre());
		result.setDescripcion(entity.getDescripcion());
		result.setSeparadorRegistros(entity.getSeparadorRegistros());
		result.setSeparadorCampos(entity.getSeparadorCampos());
		//result.setAplicacion(entity.getAplicacion());
		result.setOrdinal(entity.getOrdinal());
		result.setActivo(entity.isActivo());

		return result;
	}

	@Override
	protected TipoArchivoDto newModel() {
		return new TipoArchivoDto();
	}

	@Override
	protected TipoArchivo mergeEntity(TipoArchivoDto model, TipoArchivo entity) {
		entity.setIdGrupoTipoArchivo(model.getIdGrupoTipoArchivo());
		entity.setCodigo(model.getCodigo());
		entity.setNombre(model.getNombre());
		entity.setDescripcion(model.getDescripcion());
		entity.setSeparadorRegistros(model.getSeparadorRegistros());
		entity.setSeparadorCampos(model.getSeparadorCampos());
		//entity.setAplicacion(model.getAplicacion());
		entity.setOrdinal(model.getOrdinal());
		entity.setActivo(model.isActivo());

		return entity;
	}

	@Override
	protected TipoArchivo newEntity() {
		return new TipoArchivo();
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public Optional<TipoArchivoDto> findByCodigo(String codigo) {
		val optional = getRepository().findByCodigo(codigo);
		val result = asModel(optional);
		return result;
	}

	@Override
	public List<TipoArchivoDto> findAllByGrupoTipoArchivoId(long grupoTipoArchivo) {
		val entities = getRepository().findAllByIdGrupoTipoArchivo(grupoTipoArchivo);
		val result = asModels(entities);
		return result;
	}
}
