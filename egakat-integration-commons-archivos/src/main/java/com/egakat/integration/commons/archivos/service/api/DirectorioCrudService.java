package com.egakat.integration.commons.archivos.service.api;

import java.util.List;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.archivos.dto.DirectorioDto;
import com.egakat.integration.archivos.dto.DirectorioObservableDto;


public interface DirectorioCrudService extends CrudService<DirectorioDto, Long> {

	List<DirectorioDto> findAllByTipoArchivoId(long tipoArchivo);
	
	List<DirectorioObservableDto> findAllObservablesByTipoArchivoId(long tipoArchivo);
}
