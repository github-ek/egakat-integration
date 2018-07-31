package com.egakat.integration.core.files.service.api;

import java.util.List;

import com.egakat.integration.files.domain.Registro;

public interface InputService<T extends Registro> {
	
	String getTipoArchivoCodigo();
	
	List<Long> getArchivosPendientes();

	void extraer(long archivoId);
}