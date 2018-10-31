package com.egakat.integration.commons.tiposarchivos.repository;

import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.core.data.jpa.repository.QueryByCodigo;
import com.egakat.integration.commons.tiposarchivos.domain.TipoArchivo;

public interface TipoArchivoRepository extends IdentifiedDomainObjectRepository<TipoArchivo, Long>, QueryByCodigo<TipoArchivo, Long> {

	List<TipoArchivo> findAllByGrupoTipoArchivoId(Long idGrupoTipoArchivo);
	
	List<TipoArchivo> findAllByActivo(boolean activo);
}
