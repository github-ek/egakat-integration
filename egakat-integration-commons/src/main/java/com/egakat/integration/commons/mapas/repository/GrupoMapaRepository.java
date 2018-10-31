package com.egakat.integration.commons.mapas.repository;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.core.data.jpa.repository.QueryByCodigo;
import com.egakat.integration.commons.mapas.domain.GrupoMapa;

public interface GrupoMapaRepository
		extends IdentifiedDomainObjectRepository<GrupoMapa, Long>, QueryByCodigo<GrupoMapa, Long> {
}
