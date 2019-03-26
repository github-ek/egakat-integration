package com.egakat.integration.archivos.service.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.archivos.dto.ArchivoErrorDto;

@Transactional(readOnly = true)
public interface ArchivoErrorCrudService extends CrudService<ArchivoErrorDto, Long> {

	List<ArchivoErrorDto> findAllByArchivoId(long archivo);
}
