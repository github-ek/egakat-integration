package com.egakat.integration.commons.archivos.service.api;

import java.util.List;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.core.services.crud.api.QueryByCodigoService;
import com.egakat.integration.archivos.dto.TipoArchivoDto;

public interface TipoArchivoCrudService
		extends CrudService<TipoArchivoDto, Long>, QueryByCodigoService<TipoArchivoDto, Long> {

	List<TipoArchivoDto> findAllByGrupoTipoArchivoId(long grupoTipoArchivo);
}
