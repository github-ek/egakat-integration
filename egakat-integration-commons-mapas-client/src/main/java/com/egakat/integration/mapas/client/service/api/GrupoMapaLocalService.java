package com.anexa.integration.mapas.client.service.api;

import java.util.List;

import com.anexa.core.web.client.service.api.CacheEvictSupported;
import com.anexa.core.web.client.service.api.LocalQueryService;
import com.anexa.integration.mapas.dto.GrupoMapaDto;
import com.anexa.integration.mapas.dto.MapaDto;

public interface GrupoMapaLocalService extends LocalQueryService<GrupoMapaDto, Long>, CacheEvictSupported {

	GrupoMapaDto getByCodigo(String codigo);

	List<MapaDto> getMapas(long id);

	MapaDto getMapaByCodigo(long id, String codigo);
}
