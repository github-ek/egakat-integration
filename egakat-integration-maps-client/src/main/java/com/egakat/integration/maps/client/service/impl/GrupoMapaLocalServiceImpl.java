package com.egakat.integration.maps.client.service.impl;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.web.client.service.impl.LocalQueryServiceImpl;
import com.egakat.integration.maps.client.components.MapasRestProperties;
import com.egakat.integration.maps.client.service.api.GrupoMapaLocalService;
import com.egakat.integration.maps.constants.RestConstants;
import com.egakat.integration.maps.dto.GrupoMapaDto;
import com.egakat.integration.maps.dto.MapaDto;

import lombok.val;

@Service
public class GrupoMapaLocalServiceImpl extends LocalQueryServiceImpl<GrupoMapaDto, Long> implements GrupoMapaLocalService {

	@Autowired
	private MapasRestProperties properties;

	protected MapasRestProperties getProperties() {
		return properties;
	}

	@Override
	protected String getResourceName() {
		return RestConstants.grupoMapa;
	}

	@Override
	protected Class<GrupoMapaDto> getResponseType() {
		return GrupoMapaDto.class;
	}

	@Override
	protected Class<GrupoMapaDto[]> getArrayReponseType() {
		return GrupoMapaDto[].class;
	}

	@Override
	public GrupoMapaDto getByCodigo(String codigo) {
		val query = "?codigo={codigo}";

		val response = getRestClient().getOneQuery(getResourcePath(), query, getResponseType(), codigo);
		val result = response.getBody();
		return result;
	}

	@Override
	public List<MapaDto> getMapas(long grupoMapa) {
		val query = RestConstants.mapa;

		val response = getRestClient().getAllQuery(getResourcePath(), query, MapaDto[].class, grupoMapa);
		val result = asList(response.getBody());
		return result;
	}

	@Override
	public MapaDto getMapaByCodigo(long grupoMapa, String codigo) {
		val query = RestConstants.mapa + "?codigo={codigo}";

		val response = getRestClient().getOneQuery(getResourcePath(), query, MapaDto.class, grupoMapa, codigo);
		val result = response.getBody();
		return result;
	}
}