package com.egakat.integration.suscripciones.service.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = false)
public interface SuscripcionService<M> {

	List<Long> findAllSuscripciones();
	
	List<Long> findAllSuscripciones(Map<String, ?> paramMap);

	Optional<M> findByIdSuscripcion(Long id);
}
