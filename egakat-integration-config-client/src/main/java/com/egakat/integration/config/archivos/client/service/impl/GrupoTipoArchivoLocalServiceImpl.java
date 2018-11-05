package com.egakat.integration.config.archivos.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.web.client.properties.RestProperties;
import com.egakat.core.web.client.service.impl.LocalQueryServiceImpl;
import com.egakat.integration.config.archivos.client.service.api.GrupoTipoArchivoLocalService;
import com.egakat.integration.config.archivos.constants.RestConstants;
import com.egakat.integration.config.archivos.dto.GrupoTipoArchivoDto;
import com.egakat.integration.config.properties.IntegrationConfigRestProperties;

@Service
public class GrupoTipoArchivoLocalServiceImpl extends LocalQueryServiceImpl<GrupoTipoArchivoDto, Long> implements GrupoTipoArchivoLocalService {

	@Autowired
	private IntegrationConfigRestProperties properties;

	protected RestProperties getProperties() {
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