package com.egakat.integration.service.api.crud;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.integration.dto.ActualizacionDto;
import com.egakat.integration.dto.ErrorIntegracionDto;
import com.egakat.integration.dto.IntegracionEntityDto;
import com.egakat.integration.enums.EstadoIntegracionType;

public interface IntegracionEntityCrudService<M extends IntegracionEntityDto>
		extends AbstractIntegracionEntityCrudService<M> {

	@Transactional
	M create(M model, ActualizacionDto actualizacion, EstadoIntegracionType estado);
	
	@Transactional
	M create(M model, ActualizacionDto actualizacion, EstadoIntegracionType estado, List<ErrorIntegracionDto> errores);

	@Transactional
	M update(M model, ActualizacionDto actualizacion, EstadoIntegracionType estado);

	@Transactional
	M update(M model, ActualizacionDto actualizacion, EstadoIntegracionType estado, List<ErrorIntegracionDto> errores);
}