package com.egakat.integration.files.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.files.domain.Llave;

public interface LlaveRepository extends IdentifiedDomainObjectRepository<Llave, Long> {

	List<Llave> findAllByTipoArchivoId(long tipoArchivo);
}
