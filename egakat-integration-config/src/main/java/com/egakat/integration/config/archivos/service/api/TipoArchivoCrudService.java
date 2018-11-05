package com.egakat.integration.config.archivos.service.api;

import java.util.List;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.core.services.crud.api.QueryByCodigoService;
import com.egakat.integration.config.archivos.dto.TipoArchivoDto;

public interface TipoArchivoCrudService
		extends CrudService<TipoArchivoDto, Long>, QueryByCodigoService<TipoArchivoDto, Long> {

	List<TipoArchivoDto> findAllActivos();

	List<TipoArchivoDto> findAllByGrupoTipoArchivoId(long grupoTipoArchivo);
}
