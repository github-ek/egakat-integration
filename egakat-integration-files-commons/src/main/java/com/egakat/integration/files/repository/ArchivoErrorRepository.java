package com.egakat.integration.files.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.files.domain.ArchivoError;

public interface ArchivoErrorRepository extends IdentifiedDomainObjectRepository<ArchivoError, Long> {

	List<ArchivoError> findAllByArchivoId(long archivo);

}
