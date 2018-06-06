package com.egakat.integration.core.files.service.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.integration.files.domain.Mensaje;

@Transactional(readOnly = true)
public interface OutputService<T extends Mensaje> {
	
	String getTipoArchivoCodigo();

	List<T> getMensajesPendientes();

	@Transactional
	void enviar(List<Long> ids);
}