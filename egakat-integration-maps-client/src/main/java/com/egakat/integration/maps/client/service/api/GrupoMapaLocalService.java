package com.egakat.integration.maps.client.service.api;

import java.util.List;

import com.egakat.core.web.client.service.api.LocalQueryService;
import com.egakat.integration.maps.dto.GrupoMapaDto;
import com.egakat.integration.maps.dto.MapaDto;

public interface GrupoMapaLocalService extends LocalQueryService<GrupoMapaDto, Long> {

	GrupoMapaDto getByCodigo(String codigo);

	List<MapaDto> getMapas(long id);

	MapaDto getMapaByCodigo(long id, String codigo);
}
