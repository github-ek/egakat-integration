package com.egakat.integration.config.archivos.service.api;

import java.util.List;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.config.archivos.dto.CampoDto;

public interface CampoCrudService extends CrudService<CampoDto, Long> {

	List<CampoDto> findAllByTipoArchivoId(long tipoArchivo);
}
