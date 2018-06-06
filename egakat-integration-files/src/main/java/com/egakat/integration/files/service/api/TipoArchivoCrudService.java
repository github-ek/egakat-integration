package com.egakat.integration.files.service.api;

import java.util.List;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.core.services.crud.api.QueryByCodigoService;
import com.egakat.integration.files.dto.TipoArchivoDto;

public interface TipoArchivoCrudService
		extends CrudService<TipoArchivoDto, Long>, QueryByCodigoService<TipoArchivoDto, Long> {

	
	List<TipoArchivoDto> findAllActivos();
	
	List<TipoArchivoDto> findAllByGrupoTipoArchivoId(long grupoTipoArchivo);
}
