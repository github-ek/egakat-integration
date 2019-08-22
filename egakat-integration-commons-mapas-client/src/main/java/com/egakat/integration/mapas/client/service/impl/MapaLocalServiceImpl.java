package com.anexa.integration.mapas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anexa.core.web.client.components.RestClient;
import com.anexa.core.web.client.properties.RestProperties;
import com.anexa.core.web.client.service.impl.LocalQueryServiceImpl;
import com.anexa.integration.mapas.client.components.IntegrationConfigRestClient;
import com.anexa.integration.mapas.client.properties.IntegrationConfigRestProperties;
import com.anexa.integration.mapas.client.service.api.MapaLocalService;
import com.anexa.integration.mapas.constants.RestConstants;
import com.anexa.integration.mapas.dto.MapaDto;

import lombok.val;

@Service
public class MapaLocalServiceImpl extends LocalQueryServiceImpl<MapaDto, Long> implements MapaLocalService {

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
		return RestConstants.mapa;
	}

	@Override
	protected Class<MapaDto> getResponseType() {
		return MapaDto.class;
	}

	@Override
	protected Class<MapaDto[]> getArrayReponseType() {
		return MapaDto[].class;
	}

	@Override
	public MapaDto findOneById(Long id) {
		return super.findOneById(id);
	}

	@Override
	public String findMapaValorByMapaIdAndMapaClave(Long idMapa, String mapaClave) {
		String result = null;
		val model = findOneById(idMapa);
		if (model != null) {
			result = model.getValores().get(mapaClave);
		}
		return result;
	}

	@Override
	public void cacheEvict() {

	}
}