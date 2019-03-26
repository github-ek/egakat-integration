package com.egakat.integration.config.archivos.service.impl;

import static java.util.stream.Collectors.toList;

import static com.egakat.econnect.config.client.service.api.GrupoConfiguracionLocalService.CONFIGURACION_ETL_FILES_DIRECTORIOS;
import static com.egakat.econnect.config.client.service.api.GrupoConfiguracionLocalService.CONFIGURACION_ETL_FILES_REGEXP;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.econnect.config.client.service.api.GrupoConfiguracionLocalService;
import com.egakat.integration.config.archivos.domain.Directorio;
import com.egakat.integration.config.archivos.dto.DirectorioDto;
import com.egakat.integration.config.archivos.dto.DirectorioObservableDto;
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
		result.setSubdirectorioTemplate(entity.getSubdirectorioTemplate());
		;
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

	@Override
	public List<DirectorioObservableDto> findAllObservablesByTipoArchivoId(long tipoArchivo) {
		val entities = getRepository().findAllByTipoArchivoId(tipoArchivo);
		val result = entities.stream().map(e -> asObservable(e)).collect(toList());
		return result;
	}

	@Autowired
	private GrupoConfiguracionLocalService grupoConfiguracionLocalService;

	protected DirectorioObservableDto asObservable(Directorio entity) {

		val grupo = grupoConfiguracionLocalService.getByCodigo(CONFIGURACION_ETL_FILES_DIRECTORIOS);
		String directorioRaizEntradas = getConfiguracion(grupo.getId(), entity.getDirectorioEntradas());
		String directorioRaizTemporal = getConfiguracion(grupo.getId(), entity.getDirectorioTemporal());
		String directorioRaizDump = getConfiguracion(grupo.getId(), entity.getDirectorioDump());
		String directorioRaizProcesados = getConfiguracion(grupo.getId(), entity.getDirectorioProcesados());
		String directorioRaizErrores = getConfiguracion(grupo.getId(), entity.getDirectorioErrores());
		String directorioRaizSalidas = getConfiguracion(grupo.getId(), entity.getDirectorioSalidas());

		val template = entity.getSubdirectorioTemplate();
		val entradas = getSubdirectorio(template, directorioRaizEntradas, "entradas");
		val temporal = getSubdirectorio(template, directorioRaizTemporal, "temporal");
		val dump = getSubdirectorio(template, directorioRaizDump, "dump");
		val procesados = getSubdirectorio(template, directorioRaizProcesados, "procesados");
		val errores = getSubdirectorio(template, directorioRaizErrores, "errores");
		val salidas = getSubdirectorio(template, directorioRaizSalidas, "salidas");

		val expresionRegular = getExpresionRegular(entity);

		val result = new DirectorioObservableDto();

		result.setId(entity.getId());
		result.setIdTipoArchivo(entity.getTipoArchivo().getId());
		result.setDirectorioEntradas(entradas);
		result.setDirectorioTemporal(temporal);
		result.setDirectorioDump(dump);
		result.setDirectorioProcesados(procesados);
		result.setDirectorioErrores(errores);
		result.setDirectorioSalidas(salidas);
		result.setRegexp(expresionRegular);

		return result;
	}

	protected String getConfiguracion(Long grupo, String codigo) {
		val result = grupoConfiguracionLocalService.getConfiguracionByGrupoConfiguracionIdAndConfiguracionCodigo(grupo,
				codigo);
		return result.getValor();
	}

	protected String getSubdirectorio(String template, String directorioRaiz, String tipoDirectorio) {
		String result;
		val valueMap = new HashMap<String, String>();
		valueMap.put("DIRECTORIO_RAIZ", directorioRaiz);
		valueMap.put("TIPO_DIRECTORIO", tipoDirectorio);
		result = StrSubstitutor.replace(template, valueMap);
		return result;
	}

	protected String getExpresionRegular(Directorio entity) {
		val grupoRegExp = grupoConfiguracionLocalService.getByCodigo(CONFIGURACION_ETL_FILES_REGEXP);

		val result = getConfiguracion(grupoRegExp.getId(), entity.getRegexp());
		return result;
	}
}