package com.egakat.integration.config.mapas.client.service.api;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.egakat.core.web.client.service.api.CacheEvictSupported;
import com.egakat.core.web.client.service.api.LocalQueryService;
import com.egakat.integration.config.mapas.dto.MapaDto;

public interface MapaLocalService extends LocalQueryService<MapaDto, Long>, CacheEvictSupported {

	@Cacheable(cacheNames = "mapa-by-id", unless = "#result == null")
	MapaDto findOneById(Long id);

	@Cacheable(cacheNames = "mapas-valores", unless = "#result == null")
	String findMapaValorByMapaIdAndMapaClave(Long idMapa, String mapaClave);

	@CacheEvict(cacheNames = { "mapa-by-id", "mapas-valores" }, allEntries = true)
	void cacheEvict();
}