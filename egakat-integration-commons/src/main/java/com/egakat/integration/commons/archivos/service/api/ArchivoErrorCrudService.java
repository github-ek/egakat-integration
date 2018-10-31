package com.egakat.integration.commons.archivos.service.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.commons.archivos.dto.ArchivoErrorDto;

@Transactional(readOnly = true)
public interface ArchivoErrorCrudService extends CrudService<ArchivoErrorDto, Long> {

	List<ArchivoErrorDto> findAllByArchivoId(long archivo);
}
