package com.egakat.integration.commons.tiposarchivos.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.commons.tiposarchivos.domain.Directorio;

public interface DirectorioRepository extends IdentifiedDomainObjectRepository<Directorio, Long> {

	List<Directorio> findAllByTipoArchivoId(long tipoArchivo);
}
