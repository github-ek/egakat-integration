package com.egakat.integration.commons.tiposarchivos.service.api;

import java.util.List;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.commons.tiposarchivo.dto.LlaveDto;

public interface LlaveCrudService extends CrudService<LlaveDto, Long> {

	List<LlaveDto> findAllByTipoArchivoId(long tipoArchivo);
}
