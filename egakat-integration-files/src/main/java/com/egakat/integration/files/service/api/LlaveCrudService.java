package com.egakat.integration.files.service.api;

import java.util.List;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.files.dto.LlaveDto;

public interface LlaveCrudService extends CrudService<LlaveDto, Long> {

	List<LlaveDto> findAllByTipoArchivoId(long tipoArchivo);
}
