package com.egakat.integration.commons.mapas.service.api;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.core.services.crud.api.QueryByCodigoService;
import com.egakat.integration.commons.mapas.dto.GrupoMapaDto;

public interface GrupoMapaCrudService extends CrudService<GrupoMapaDto, Long> , QueryByCodigoService<GrupoMapaDto, Long> {
}
