package com.egakat.integration.commons.archivos.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.commons.archivos.domain.ArchivoError;

public interface ArchivoErrorRepository extends IdentifiedDomainObjectRepository<ArchivoError, Long> {

	List<ArchivoError> findAllByArchivoId(long archivo);

}
