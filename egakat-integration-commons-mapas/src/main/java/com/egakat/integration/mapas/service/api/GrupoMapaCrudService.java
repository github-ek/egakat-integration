package com.egakat.integration.mapas.service.api;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.core.services.crud.api.QueryByCodigoService;
import com.egakat.integration.mapas.dto.GrupoMapaDto;

public interface GrupoMapaCrudService extends CrudService<GrupoMapaDto, Long> , QueryByCodigoService<GrupoMapaDto, Long> {
}
