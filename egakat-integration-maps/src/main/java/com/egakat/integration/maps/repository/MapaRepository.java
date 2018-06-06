package com.egakat.integration.maps.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.maps.domain.Mapa;

public interface MapaRepository extends IdentifiedDomainObjectRepository<Mapa, Long> {
	Mapa findOneByGrupoMapaIdAndCodigo(Long grupoMapa, String codigo);

	List<Mapa>  findAllByGrupoMapaId(long grupoMapa);
}
