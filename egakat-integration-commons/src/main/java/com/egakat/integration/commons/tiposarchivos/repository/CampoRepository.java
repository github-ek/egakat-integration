package com.egakat.integration.commons.tiposarchivos.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.core.data.jpa.repository.QueryByCodigo;
import com.egakat.integration.commons.tiposarchivos.domain.Campo;

public interface CampoRepository extends IdentifiedDomainObjectRepository<Campo, Long>, QueryByCodigo<Campo, Long> {

	List<Campo> findAllByTipoArchivoId(long tipoArchivo);
}
