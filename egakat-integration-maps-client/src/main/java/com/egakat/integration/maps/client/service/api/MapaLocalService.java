package com.egakat.integration.maps.client.service.api;

import com.egakat.core.web.client.service.api.LocalQueryService;
import com.egakat.integration.maps.dto.MapaDto;

public interface MapaLocalService extends LocalQueryService<MapaDto, Long> {
	
	String findMapaValorByMapaIdAndMapaClave(Long idMapa, String mapaClave);
	
}
