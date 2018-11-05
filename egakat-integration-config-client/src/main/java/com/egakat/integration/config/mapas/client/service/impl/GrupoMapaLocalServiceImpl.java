package com.egakat.integration.config.mapas.client.service.impl;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.egakat.core.web.client.properties.RestProperties;
import com.egakat.core.web.client.service.impl.LocalQueryServiceImpl;
import com.egakat.integration.config.mapas.client.service.api.GrupoMapaLocalService;
import com.egakat.integration.config.mapas.constants.RestConstants;
import com.egakat.integration.config.mapas.dto.GrupoMapaDto;
import com.egakat.integration.config.mapas.dto.MapaDto;
import com.egakat.integration.config.properties.IntegrationConfigRestProperties;

import lombok.val;

@Service
public class GrupoMapaLocalServiceImpl extends LocalQueryServiceImpl<GrupoMapaDto, Long>
		implements GrupoMapaLocalService {

	@Autowired
	private IntegrationConfigRestProperties properties;

	protected RestProperties getProperties() {
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

	@Cacheable(cacheNames = "grupo-mapa-by-codigo", sync = true, unless = "#result == null")
	@Override
	public GrupoMapaDto getByCodigo(String codigo) {
		val query = "?codigo={codigo}";

		val response = getRestClient().getOneQuery(getResourcePath(), query, getResponseType(), codigo);
		val result = response.getBody();
		return result;
	}

	@Cacheable(cacheNames = "mapas-by-grupo-mapa", sync = true, unless = "#result == null")
	@Override
	public List<MapaDto> getMapas(long grupoMapa) {
		val query = RestConstants.mapa;

		val response = getRestClient().getAllQuery(getResourcePath(), query, MapaDto[].class, grupoMapa);
		val result = asList(response.getBody());
		return result;
	}

	@Cacheable(cacheNames = "mapa-by-grupo-mapa-and-mapa-codigo", sync = true, unless = "#result == null")
	@Override
	public MapaDto getMapaByCodigo(long grupoMapa, String codigo) {
		val query = RestConstants.mapa + "?codigo={codigo}";

		val response = getRestClient().getOneQuery(getResourcePath(), query, MapaDto.class, grupoMapa, codigo);
		val result = response.getBody();
		return result;
	}

	@CacheEvict(cacheNames = { "grupo-mapa-by-codigo", "mapas-by-grupo-mapa",
			"mapa-by-grupo-mapa-and-mapa-codigo" }, allEntries = true)
	@Override
	public void cacheEvict() {

	}
}