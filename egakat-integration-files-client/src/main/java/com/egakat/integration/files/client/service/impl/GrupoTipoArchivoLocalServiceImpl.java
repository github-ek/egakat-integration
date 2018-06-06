package com.egakat.integration.files.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.web.client.service.impl.LocalQueryServiceImpl;
import com.egakat.integration.files.client.components.FilesRestProperties;
import com.egakat.integration.files.client.service.api.GrupoTipoArchivoLocalService;
import com.egakat.integration.files.constants.RestConstants;
import com.egakat.integration.files.dto.GrupoTipoArchivoDto;

@Service
public class GrupoTipoArchivoLocalServiceImpl extends LocalQueryServiceImpl<GrupoTipoArchivoDto, Long> implements GrupoTipoArchivoLocalService {

	@Autowired
	private FilesRestProperties properties;

	protected FilesRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return RestConstants.grupoTipoArchivo;
	}

	@Override
	protected Class<GrupoTipoArchivoDto> getResponseType() {
		return GrupoTipoArchivoDto.class;
	}

	@Override
	protected Class<GrupoTipoArchivoDto[]> getArrayReponseType() {
		return GrupoTipoArchivoDto[].class;
	}
}
