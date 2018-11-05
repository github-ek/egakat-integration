package com.egakat.integration.config.mapas.client.service.api;

import com.egakat.core.web.client.service.api.CacheEvictSupported;
import com.egakat.core.web.client.service.api.LocalQueryService;
import com.egakat.integration.config.mapas.dto.MapaDto;

public interface MapaLocalService extends LocalQueryService<MapaDto, Long>, CacheEvictSupported {

	String findMapaValorByMapaIdAndMapaClave(Long idMapa, String mapaClave);

}
