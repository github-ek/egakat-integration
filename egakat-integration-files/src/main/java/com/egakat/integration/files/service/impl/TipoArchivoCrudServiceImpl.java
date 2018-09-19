package com.egakat.integration.files.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.files.domain.TipoArchivo;
import com.egakat.integration.files.dto.TipoArchivoDto;
import com.egakat.integration.files.repository.GrupoTipoArchivoRepository;
import com.egakat.integration.files.repository.TipoArchivoRepository;
import com.egakat.integration.files.service.api.TipoArchivoCrudService;

import lombok.val;

@Service
public class TipoArchivoCrudServiceImpl extends CrudServiceImpl<TipoArchivo, TipoArchivoDto, Long>
		implements TipoArchivoCrudService {

	@Autowired
	private TipoArchivoRepository repository;

	@Autowired
	private GrupoTipoArchivoRepository grupoTipoArchivorepository;

	@Override
	protected TipoArchivoRepository getRepository() {
		return repository;
	}

	@Override
	protected TipoArchivoDto asModel(TipoArchivo entity) {

		// @formatter:off
		val result = TipoArchivoDto.builder()
				.id(entity.getId())
				
				.idGrupoTipoArchivo(entity.getGrupoTipoArchivo().getId())
				.codigo(entity.getCodigo())
				.nombre(entity.getNombre())
				.descripcion(entity.getDescripcion())
				.separadorRegistros(entity.getSeparadorRegistros())
				.separadorCampos(entity.getSeparadorCampos())
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
	protected TipoArchivo mergeEntity(TipoArchivoDto model, TipoArchivo entity) {
		val grupoTipoArchivo = grupoTipoArchivorepository.getOne(model.getIdGrupoTipoArchivo());

		entity.setGrupoTipoArchivo(grupoTipoArchivo);
		entity.setCodigo(model.getCodigo());
		entity.setNombre(model.getNombre());
		entity.setDescripcion(model.getDescripcion());
		entity.setSeparadorRegistros(model.getSeparadorRegistros());
		entity.setSeparadorCampos(model.getSeparadorCampos());
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

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public List<TipoArchivoDto> findAllActivos() {
		val comparator = Comparator.comparing(TipoArchivoDto::getIdGrupoTipoArchivo).thenComparing(TipoArchivoDto::getCodigo);

		val entities = getRepository().findAllByActivo(true);
		val result = asModels(entities).stream().sorted(comparator).collect(toList());
		return result;
	}
	
	@Override
	public List<TipoArchivoDto> findAllByGrupoTipoArchivoId(long grupoTipoArchivo) {
		val entities = getRepository().findAllByGrupoTipoArchivoId(grupoTipoArchivo);
		val result = asModels(entities);
		return result;
	}
}
