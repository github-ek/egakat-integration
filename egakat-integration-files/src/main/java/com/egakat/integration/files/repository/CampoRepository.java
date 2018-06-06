package com.egakat.integration.files.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.core.data.jpa.repository.QueryByCodigo;
import com.egakat.integration.files.domain.Campo;

public interface CampoRepository extends IdentifiedDomainObjectRepository<Campo, Long>, QueryByCodigo<Campo, Long> {

	List<Campo> findAllByTipoArchivoId(long tipoArchivo);
}
