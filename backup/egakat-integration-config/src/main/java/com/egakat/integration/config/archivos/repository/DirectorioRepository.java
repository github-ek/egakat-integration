package com.egakat.integration.config.archivos.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.config.archivos.domain.Directorio;

public interface DirectorioRepository extends IdentifiedDomainObjectRepository<Directorio, Long> {

	List<Directorio> findAllByTipoArchivoId(long tipoArchivo);
}
