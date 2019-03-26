package com.egakat.integration.suscripciones.service.api.crud;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.suscripciones.dto.SuscripcionDto;

public interface SuscripcionCrudService extends CrudService<SuscripcionDto, Long> {

	@Transactional(readOnly = true)
	boolean exists(String suscripcion, String idExterno);

	@Transactional(readOnly = true)
	SuscripcionDto findOneBySuscripcionAndIdExterno(String suscripcion, String idExterno);
}
