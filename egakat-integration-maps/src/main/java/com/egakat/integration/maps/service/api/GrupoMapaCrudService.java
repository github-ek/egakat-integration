package com.egakat.integration.maps.service.api;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.core.services.crud.api.QueryByCodigoService;
import com.egakat.integration.maps.dto.GrupoMapaDto;

public interface GrupoMapaCrudService extends CrudService<GrupoMapaDto, Long> , QueryByCodigoService<GrupoMapaDto, Long> {
}
