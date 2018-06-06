package com.egakat.integration.core.transformation.service.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.integration.files.domain.Registro;



@Transactional(readOnly = true)
public interface TransformationService<T extends Registro> {

	List<Long> getArchivosPendientes();

	@Transactional
	void transformar(Long archivoId);
}
