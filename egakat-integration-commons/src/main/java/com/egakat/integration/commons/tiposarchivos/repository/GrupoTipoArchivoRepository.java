package com.egakat.integration.commons.tiposarchivos.repository;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.core.data.jpa.repository.QueryByCodigo;
import com.egakat.integration.commons.tiposarchivos.domain.GrupoTipoArchivo;

public interface GrupoTipoArchivoRepository extends IdentifiedDomainObjectRepository<GrupoTipoArchivo, Long>, QueryByCodigo<GrupoTipoArchivo, Long> {

}
