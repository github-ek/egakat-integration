package com.egakat.integration.core.files.service.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.integration.files.domain.Registro;

@Transactional(readOnly = true)
public interface InputService<T extends Registro> {
	
	String getTipoArchivoCodigo();
	
	List<Long> getArchivosPendientes();
	
	@Transactional
	void extraer(long archivoId);
}