package com.egakat.integration.files.client.service.impl;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.web.client.service.impl.LocalQueryServiceImpl;
import com.egakat.integration.files.client.components.FilesRestProperties;
import com.egakat.integration.files.client.service.api.TipoArchivoLocalService;
import com.egakat.integration.files.constants.RestConstants;
import com.egakat.integration.files.dto.CampoDto;
import com.egakat.integration.files.dto.DirectorioDto;
import com.egakat.integration.files.dto.LlaveDto;
import com.egakat.integration.files.dto.TipoArchivoDto;

import lombok.val;

@Service
public class TipoArchivoLocalServiceImpl extends LocalQueryServiceImpl<TipoArchivoDto, Long>
		implements TipoArchivoLocalService {

	@Autowired
	private FilesRestProperties properties;

	@Override
	protected FilesRestProperties getProperties() {
		return properties;
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
	public List<TipoArchivoDto> findAllActivos() {
		val response = getRestClient().getAllQuery(getResourcePath(), "", getArrayReponseType());
		val result = asList(response.getBody());
		return result;
	}

	@Override
	public TipoArchivoDto findOneByCodigo(String codigo) {
		val query = "?codigo={codigo}";

		val response = getRestClient().getOneQuery(getResourcePath(), query, getResponseType(), codigo);
		val result = response.getBody();
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
	public DirectorioDto findOneDirectorioByTipoArchivo(long tipoArchivo) {
		val query = RestConstants.directorioByTipoArchivo;

		val response = getRestClient().getOneQuery(getResourcePath(), query, DirectorioDto.class, tipoArchivo);
		val result = response.getBody();
		return result;
	}
}