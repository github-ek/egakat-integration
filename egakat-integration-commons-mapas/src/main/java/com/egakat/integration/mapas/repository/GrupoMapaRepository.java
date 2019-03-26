package com.egakat.integration.mapas.repository;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.core.data.jpa.repository.QueryByCodigo;
import com.egakat.integration.mapas.domain.GrupoMapa;

public interface GrupoMapaRepository
		extends IdentifiedDomainObjectRepository<GrupoMapa, Long>, QueryByCodigo<GrupoMapa, Long> {
}
