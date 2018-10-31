package com.egakat.integration.commons.tiposarchivos.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.commons.tiposarchivos.domain.Llave;

public interface LlaveRepository extends IdentifiedDomainObjectRepository<Llave, Long> {

	List<Llave> findAllByTipoArchivoId(long tipoArchivo);
}
