package com.egakat.integration.archivos.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.archivos.domain.Directorio;

public interface DirectorioRepository extends IdentifiedDomainObjectRepository<Directorio, Long> {

	List<Directorio> findAllByIdTipoArchivo(long tipoArchivo);

}
