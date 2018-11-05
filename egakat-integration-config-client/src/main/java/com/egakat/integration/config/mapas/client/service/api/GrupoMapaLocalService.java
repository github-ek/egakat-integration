package com.egakat.integration.config.mapas.client.service.api;

import java.util.List;

import com.egakat.core.web.client.service.api.CacheEvictSupported;
import com.egakat.core.web.client.service.api.LocalQueryService;
import com.egakat.integration.config.mapas.dto.GrupoMapaDto;
import com.egakat.integration.config.mapas.dto.MapaDto;

public interface GrupoMapaLocalService extends LocalQueryService<GrupoMapaDto, Long>, CacheEvictSupported {

	GrupoMapaDto getByCodigo(String codigo);

	List<MapaDto> getMapas(long id);

	MapaDto getMapaByCodigo(long id, String codigo);
}
