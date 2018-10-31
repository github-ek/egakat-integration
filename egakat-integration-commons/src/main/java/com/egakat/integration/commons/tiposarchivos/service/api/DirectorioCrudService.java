package com.egakat.integration.commons.tiposarchivos.service.api;

import java.util.List;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.commons.tiposarchivo.dto.DirectorioDto;

public interface DirectorioCrudService extends CrudService<DirectorioDto, Long> {

	List<DirectorioDto> findAllByTipoArchivoId(long tipoArchivo);
}
