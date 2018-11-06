package com.egakat.integration.config.archivos.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.web.client.components.RestClient;
import com.egakat.core.web.client.properties.RestProperties;
import com.egakat.core.web.client.service.impl.LocalQueryServiceImpl;
import com.egakat.integration.components.IntegrationConfigRestClient;
import com.egakat.integration.config.archivos.client.service.api.GrupoTipoArchivoLocalService;
import com.egakat.integration.config.archivos.constants.RestConstants;
import com.egakat.integration.config.archivos.dto.GrupoTipoArchivoDto;
import com.egakat.integration.properties.IntegrationConfigRestProperties;

@Service
public class GrupoTipoArchivoLocalServiceImpl extends LocalQueryServiceImpl<GrupoTipoArchivoDto, Long>
		implements GrupoTipoArchivoLocalService {

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