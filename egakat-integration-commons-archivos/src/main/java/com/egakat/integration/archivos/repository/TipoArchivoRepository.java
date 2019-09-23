package com.egakat.integration.archivos.repository;

import java.util.List;
import java.util.Optional;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.archivos.domain.TipoArchivo;

public interface TipoArchivoRepository extends IdentifiedDomainObjectRepository<TipoArchivo, Long> {

	Optional<TipoArchivo> findByCodigo(String codigo);

	List<TipoArchivo> findAllByIdGrupoTipoArchivo(long grupoTipoArchivo);

}
