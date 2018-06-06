package com.egakat.integration.maps.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.egakat.core.web.client.service.impl.LocalQueryServiceImpl;
import com.egakat.integration.maps.client.components.MapasRestProperties;
import com.egakat.integration.maps.client.service.api.MapaLocalService;
import com.egakat.integration.maps.constants.RestConstants;
import com.egakat.integration.maps.dto.MapaDto;

import lombok.val;

@Service
public class MapaLocalServiceImpl extends LocalQueryServiceImpl<MapaDto, Long> implements MapaLocalService {

	@Autowired
	private MapasRestProperties properties;

	protected MapasRestProperties getProperties() {
		return properties;
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

	@Cacheable(cacheNames = "mapas", sync = true)
	@Override
	public MapaDto findOneById(Long id) {
		return super.findOneById(id);
	}

	@Cacheable(cacheNames = "mapas-valores", sync = true)
	@Override
	public String findMapaValorByMapaIdAndMapaClave(Long idMapa, String mapaClave) {
		String result = null;
		val model = findOneById(idMapa);
		if (model != null) {
			result = model.getValores().get(mapaClave);
		}
		return result;
	}
}