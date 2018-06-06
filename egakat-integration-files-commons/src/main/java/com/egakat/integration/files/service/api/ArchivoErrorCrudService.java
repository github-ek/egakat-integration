package com.egakat.integration.files.service.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.files.configuration.JpaConfiguration;
import com.egakat.integration.files.dto.ArchivoErrorDto;

@Transactional(readOnly = true, transactionManager = JpaConfiguration.TRANSACTION_MANAGER)
public interface ArchivoErrorCrudService extends CrudService<ArchivoErrorDto, Long> {

	List<ArchivoErrorDto> findAllByArchivoId(long archivo);
}
