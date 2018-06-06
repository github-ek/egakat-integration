package com.egakat.integration.files.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.core.data.jpa.repository.QueryByCodigo;
import com.egakat.integration.files.domain.TipoArchivo;

public interface TipoArchivoRepository extends IdentifiedDomainObjectRepository<TipoArchivo, Long>, QueryByCodigo<TipoArchivo, Long> {

	List<TipoArchivo> findAllByGrupoTipoArchivoId(Long idGrupoTipoArchivo);
	
	List<TipoArchivo> findAllByActivo(boolean activo);
}
