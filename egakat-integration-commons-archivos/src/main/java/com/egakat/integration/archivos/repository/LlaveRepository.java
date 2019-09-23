package com.egakat.integration.archivos.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.archivos.domain.Llave;

public interface LlaveRepository extends IdentifiedDomainObjectRepository<Llave, Long> {

	List<Llave> findAllByIdTipoArchivo(long tipoArchivo);

}
