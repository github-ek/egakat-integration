package com.egakat.integration.config.mapas.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.web.client.components.RestClient;
import com.egakat.core.web.client.properties.RestProperties;
import com.egakat.core.web.client.service.impl.LocalQueryServiceImpl;
import com.egakat.integration.components.IntegrationConfigRestClient;
import com.egakat.integration.config.mapas.client.service.api.MapaLocalService;
import com.egakat.integration.config.mapas.constants.RestConstants;
import com.egakat.integration.config.mapas.dto.MapaDto;
import com.egakat.integration.properties.IntegrationConfigRestProperties;

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