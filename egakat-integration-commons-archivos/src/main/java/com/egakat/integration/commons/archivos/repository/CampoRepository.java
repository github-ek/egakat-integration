package com.egakat.integration.commons.archivos.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.commons.archivos.domain.Campo;

public interface CampoRepository extends IdentifiedDomainObjectRepository<Campo, Long> {
	List<Campo> findAllByIdTipoArchivo(long tipoArchivo);
}
