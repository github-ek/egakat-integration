package com.egakat.integration.config.mapas.service.api;

import java.util.List;
import java.util.Optional;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.config.mapas.dto.MapaDto;

public interface MapaCrudService extends CrudService<MapaDto, Long> {
	Optional<MapaDto> findOneByGrupoMapaIdAndCodigo(Long grupoMapa, String codigo);

	List<MapaDto>  findAllByGrupoMapa(long id);
}