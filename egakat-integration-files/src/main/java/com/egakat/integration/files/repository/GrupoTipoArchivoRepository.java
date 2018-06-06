package com.egakat.integration.files.repository;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.core.data.jpa.repository.QueryByCodigo;
import com.egakat.integration.files.domain.GrupoTipoArchivo;

public interface GrupoTipoArchivoRepository extends IdentifiedDomainObjectRepository<GrupoTipoArchivo, Long>, QueryByCodigo<GrupoTipoArchivo, Long> {

}
