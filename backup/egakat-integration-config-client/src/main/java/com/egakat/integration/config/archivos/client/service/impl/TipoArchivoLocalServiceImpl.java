package com.egakat.integration.config.archivos.client.service.impl;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.web.client.components.RestClient;
import com.egakat.core.web.client.properties.RestProperties;
import com.egakat.core.web.client.service.impl.LocalQueryServiceImpl;
import com.egakat.integration.components.IntegrationConfigRestClient;
import com.egakat.integration.config.archivos.client.service.api.TipoArchivoLocalService;
import com.egakat.integration.config.archivos.constants.RestConstants;
import com.egakat.integration.config.archivos.dto.CampoDto;
import com.egakat.integration.config.archivos.dto.DirectorioDto;
import com.egakat.integration.config.archivos.dto.DirectorioObservableDto;
import com.egakat.integration.config.archivos.dto.LlaveDto;
import com.egakat.integration.config.archivos.dto.TipoArchivoDto;
import com.egakat.integration.properties.IntegrationConfigRestProperties;

import lombok.val;

@Service
public class TipoArchivoLocalServiceImpl extends LocalQueryServiceImpl<TipoArchivoDto, Long>
		implements TipoArchivoLocalService {

	@Autowired
	private IntegrationConfigRestProperties properties;

	@Autowired
	private IntegrationConfigRestClient restClient;

	protected RestProperties getProperties() {
		return properties;
	}

	@Override
	protected RestClient getRestClient() {
		return restClient;
	}

	@Override
	protected String getResourceName() {
		return RestConstants.tipoArchivo;
	}

	@Override
	protected Class<TipoArchivoDto> getResponseType() {
		return TipoArchivoDto.class;
	}

	@Override
	protected Class<TipoArchivoDto[]> getArrayReponseType() {
		return TipoArchivoDto[].class;
	}

	@Override
	public TipoArchivoDto findOneById(Long id) {
		return super.findOneById(id);
	}

	@Override
	public TipoArchivoDto findOneByCodigo(String codigo) {
		val query = "?codigo={codigo}";

		val response = getRestClient().getOneQuery(getResourcePath(), query, getResponseType(), codigo);
		val result = response.getBody();
		return result;
	}

	@Override
	public List<TipoArchivoDto> findAllByAplicacion(String aplicacion) {
		val query = "?aplicacion={aplicacion}";

		val response = getRestClient().getAllQuery(getResourcePath(), query, getArrayReponseType(), aplicacion);
		val result = asList(response.getBody());
		return result;
	}

	@Override
	public List<CampoDto> findAllCamposByTipoArchivo(long tipoArchivo) {
		val query = RestConstants.camposByTipoArchivo;

		val response = getRestClient().getAllQuery(getResourcePath(), query, CampoDto[].class, tipoArchivo);
		val result = asList(response.getBody());
		return result;
	}

	@Override
	public List<LlaveDto> findAllLlavesByTipoArchivo(long tipoArchivo) {
		val query = RestConstants.llavesByTipoArchivo;

		val response = getRestClient().getAllQuery(getResourcePath(), query, LlaveDto[].class, tipoArchivo);
		val result = asList(response.getBody());
		return result;
	}

	@Override
	public List<DirectorioDto> findAllDirectoriosByTipoArchivo(long tipoArchivo) {
		val query = RestConstants.directoriosByTipoArchivo;

		val response = getRestClient().getAllQuery(getResourcePath(), query, DirectorioDto[].class, tipoArchivo);
		val result = asList(response.getBody());
		return result;
	}

	@Override
	public List<DirectorioObservableDto> findAllDirectoriosObservablesByTipoArchivo(long tipoArchivo) {
		val query = RestConstants.directoriosObservablesByTipoArchivo;

		val response = getRestClient().getAllQuery(getResourcePath(), query, DirectorioObservableDto[].class,
				tipoArchivo);
		val result = asList(response.getBody());
		return result;
	}

	@Override
	public void cacheEvict() {

	}
}