package com.egakat.integration.config.archivos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.config.archivos.domain.Directorio;
import com.egakat.integration.config.archivos.dto.DirectorioDto;
import com.egakat.integration.config.archivos.repository.DirectorioRepository;
import com.egakat.integration.config.archivos.repository.TipoArchivoRepository;
import com.egakat.integration.config.archivos.service.api.DirectorioCrudService;

import lombok.val;

@Service
public class DirectorioCrudServiceImpl extends CrudServiceImpl<Directorio, DirectorioDto, Long>
		implements DirectorioCrudService {

	@Autowired
	private DirectorioRepository repository;

	@Autowired
	private TipoArchivoRepository tipoArchivorepository;

	@Override
	protected DirectorioRepository getRepository() {
		return repository;
	}

	@Override
	protected DirectorioDto asModel(Directorio entity) {
		val result = newModel();
		mapModel(entity, result);

		result.setIdTipoArchivo(entity.getTipoArchivo().getId());
		result.setSubdirectorioTemplate(entity.getSubdirectorioTemplate());;
		result.setRegexp(entity.getRegexp());
		result.setDirectorioEntradas(entity.getDirectorioEntradas());
		result.setDirectorioTemporal(entity.getDirectorioTemporal());
		result.setDirectorioDump(entity.getDirectorioDump());
		result.setDirectorioProcesados(entity.getDirectorioProcesados());
		result.setDirectorioErrores(entity.getDirectorioErrores());
		result.setDirectorioSalidas(entity.getDirectorioSalidas());

		return result;
	}

	@Override
	protected DirectorioDto newModel() {
		return new DirectorioDto();
	}

	@Override
	protected Directorio mergeEntity(DirectorioDto model, Directorio entity) {
		val tipoArchivo = tipoArchivorepository.getOne(model.getIdTipoArchivo());

		entity.setTipoArchivo(tipoArchivo);
		entity.setSubdirectorioTemplate(model.getSubdirectorioTemplate());
		entity.setRegexp(model.getRegexp());
		entity.setDirectorioEntradas(model.getDirectorioEntradas());
		entity.setDirectorioTemporal(model.getDirectorioTemporal());
		entity.setDirectorioDump(model.getDirectorioDump());
		entity.setDirectorioProcesados(model.getDirectorioProcesados());
		entity.setDirectorioErrores(model.getDirectorioErrores());
		entity.setDirectorioSalidas(model.getDirectorioSalidas());

		return entity;
	}

	@Override
	protected Directorio newEntity() {
		return new Directorio();
	}

	@Override
	public List<DirectorioDto> findAllByTipoArchivoId(long tipoArchivo) {
		val entities = getRepository().findAllByTipoArchivoId(tipoArchivo);
		val result = asModels(entities);
		return result;
	}
}