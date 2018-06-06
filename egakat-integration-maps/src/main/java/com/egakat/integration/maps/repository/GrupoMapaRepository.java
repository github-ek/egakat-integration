package com.egakat.integration.maps.repository;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.core.data.jpa.repository.QueryByCodigo;
import com.egakat.integration.maps.domain.GrupoMapa;

public interface GrupoMapaRepository extends IdentifiedDomainObjectRepository<GrupoMapa, Long>, QueryByCodigo<GrupoMapa, Long> {
}
