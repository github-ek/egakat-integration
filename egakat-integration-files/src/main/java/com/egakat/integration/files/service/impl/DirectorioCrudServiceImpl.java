package com.egakat.integration.files.service.impl;

import static com.egakat.econnect.config.client.service.api.GrupoConfiguracionLocalService.CONFIGURACION_ETL_FILES_DIRECTORIOS;
import static com.egakat.econnect.config.client.service.api.GrupoConfiguracionLocalService.CONFIGURACION_ETL_FILES_REGEXP;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.econnect.config.client.service.api.GrupoConfiguracionLocalService;
import com.egakat.integration.files.domain.Directorio;
import com.egakat.integration.files.dto.DirectorioDto;
import com.egakat.integration.files.repository.DirectorioRepository;
import com.egakat.integration.files.repository.TipoArchivoRepository;
import com.egakat.integration.files.service.api.DirectorioCrudService;

import lombok.val;

@Service
public class DirectorioCrudServiceImpl extends CrudServiceImpl<Directorio, DirectorioDto, Long>
		implements DirectorioCrudService {

	@Autowired
	private GrupoConfiguracionLocalService grupoConfiguracionLocalService;

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
		val grupoDirectorios = grupoConfiguracionLocalService.getByCodigo(CONFIGURACION_ETL_FILES_DIRECTORIOS);
		val id = grupoDirectorios.getId();
		val template = entity.getSubdirectorioTemplate();

		String directorioEntradas = "entradas";
		String directorioTemporal = "temporal";
		String directorioDump = "dump";
		String directorioProcesados = "procesados";
		String directorioErrores = "errores";
		String directorioSalidas = "salidas";

		val entradas = getSubdirectorio(template, id, entity.getDirectorioEntradas(), directorioEntradas);
		val temporal = getSubdirectorio(template, id, entity.getDirectorioTemporal(), directorioTemporal);
		val dump = getSubdirectorio(template, id, entity.getDirectorioDump(), directorioDump);
		val procesados = getSubdirectorio(template, id, entity.getDirectorioProcesados(), directorioProcesados);
		val errores = getSubdirectorio(template, id, entity.getDirectorioErrores(), directorioErrores);
		val salidas = getSubdirectorio(template, id, entity.getDirectorioSalidas(), directorioSalidas);

		val grupoRegExp = grupoConfiguracionLocalService.getByCodigo(CONFIGURACION_ETL_FILES_REGEXP);
		val expresionRegular = getConfiguracion(grupoRegExp.getId(), entity.getRegexp());

		// @formatter:off
		val result = DirectorioDto
				.builder()
				.id(entity.getId())
				
				.idTipoArchivo(entity.getTipoArchivo().getId())
				.subdirectorioTemplate(entity.getSubdirectorioTemplate())
				.subdirectorioEntradas(entradas)
				.subdirectorioTemporal(temporal)
				.subdirectorioDump(dump)
				.subdirectorioProcesados(procesados)
				.subdirectorioErrores(errores)
				.subdirectorioSalidas(salidas)

				.regexp(entity.getRegexp())
				.expresionRegular(expresionRegular)

				.directorioEntradas(entity.getDirectorioEntradas())
				.directorioTemporal(entity.getDirectorioTemporal())
				.directorioDump(entity.getDirectorioDump())
				.directorioProcesados(entity.getDirectorioProcesados())
				.directorioErrores(entity.getDirectorioErrores())
				.directorioSalidas(entity.getDirectorioSalidas())

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
	protected Directorio asEntity(DirectorioDto model, Directorio entity) {
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

	// -----------------------------------------------'-------------------------------------------------------------------------------------
	// --
	// ------------------------------------------------------------------------------------------------------------------------------------
	private String getConfiguracion(Long grupoConfiguracion, String codigo) {
		val result = grupoConfiguracionLocalService
				.getConfiguracionByGrupoConfiguracionIdAndConfiguracionCodigo(grupoConfiguracion, codigo);
		return result.getValor();
	}

	private String getSubdirectorio(String template, Long grupoDirectorio, String directorioCodigo,
			String tipoDirectorio) {
		String result;
		if (!"".equals(directorioCodigo)) {
			val directorioRaiz = getConfiguracion(grupoDirectorio, directorioCodigo);
			val valueMap = new HashMap<String, String>();
			valueMap.put("DIRECTORIO_RAIZ", directorioRaiz);
			valueMap.put("TIPO_DIRECTORIO", tipoDirectorio);
			result = StrSubstitutor.replace(template, valueMap);
		} else {
			result = "";
		}
		return result;
	}
}