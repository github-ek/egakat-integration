package com.egakat.integration.files.client.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.web.client.service.impl.LocalCrudServiceImpl;
import com.egakat.integration.files.client.components.FilesRestProperties;
import com.egakat.integration.files.client.service.api.Archivo2LocalService;
import com.egakat.integration.files.constants.RestConstants;
import com.egakat.integration.files.dto.ArchivoDto;
import com.egakat.integration.files.dto.ArchivoErrorDto;
import com.egakat.integration.files.enums.EstadoArchivoType;

import lombok.val;

@Service
public class ArchivoLocalServiceImpl extends LocalCrudServiceImpl<ArchivoDto, Long> implements Archivo2LocalService {

	@Autowired
	private FilesRestProperties properties;

	@Override
	protected FilesRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return RestConstants.archivo;
	}

	@Override
	protected Class<ArchivoDto> getResponseType() {
		return ArchivoDto.class;
	}

	@Override
	protected Class<ArchivoDto[]> getArrayReponseType() {
		return ArchivoDto[].class;
	}

	@Override
	public List<ArchivoDto> findAllByEstadoIn(List<EstadoArchivoType> estados) {
		val query = "?" + buildMatrixVariable("estado", estados);

		val response = getRestClient().getAllQuery(getResourcePath(), query, getArrayReponseType());
		val result = Arrays.asList(response.getBody());
		return result;
	}

	@Override
	public List<ArchivoDto> findAllByTipoArchivoCodigoAndEstadoIn(String tipoArchivoCodigo,
			List<EstadoArchivoType> estados) {
		val query = "get-by/;tipoArchivoCodigo={tipoArchivoCodigo};" + buildMatrixVariable("estado", estados);

		val response = getRestClient().getAllQuery(getResourcePath(), query, getArrayReponseType(), tipoArchivoCodigo);
		val result = Arrays.asList(response.getBody());
		return result;
	}

	@Override
	public List<Long> findAllIdByTipoArchivoCodigoAndEstadoIn(String tipoArchivoCodigo,
			List<EstadoArchivoType> estados) {
		val query = "get-by/;tipoArchivoCodigo={tipoArchivoCodigo};" + buildMatrixVariable("estado", estados);

		val response = getRestClient().getAllQuery(getResourcePath(), query, Long[].class, tipoArchivoCodigo);
		val result = Arrays.asList(response.getBody());
		return result;
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// TODO
	@Override
	public ArchivoDto extraido(ArchivoDto archivo, List<ArchivoErrorDto> errores) {
		val result = procesado(archivo, errores, "extraido");
		return result;
	}

	@Override
	public ArchivoDto transformado(ArchivoDto archivo, List<ArchivoErrorDto> errores) {
		val result = procesado(archivo, errores, "transformado");
		return result;
	}

	@Override
	public ArchivoDto cargado(ArchivoDto archivo, List<ArchivoErrorDto> errores) {
		val result = procesado(archivo, errores, "cargado");
		return result;
	}

	protected ArchivoDto procesado(ArchivoDto archivo, List<ArchivoErrorDto> errores, String option) {
		val url = getResourcePath() + "/{id}?option={option}&version={version}";
		val response = getRestClient().post(url, errores, getResponseType(), archivo.getId(), option,
				archivo.getVersion());
		val result = response.getBody();
		return result;
	}
}
