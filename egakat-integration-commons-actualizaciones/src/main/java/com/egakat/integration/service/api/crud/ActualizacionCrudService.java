package com.egakat.integration.service.api.crud;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.integration.dto.ActualizacionDto;
import com.egakat.integration.dto.ErrorIntegracionDto;
import com.egakat.integration.enums.EstadoIntegracionType;

public interface ActualizacionCrudService extends AbstractIntegracionEntityCrudService<ActualizacionDto> {

	@Transactional(readOnly = true)
	List<ActualizacionDto> findAllByIntegracionAndEstadoIntegracionIn(String integracion,
			EstadoIntegracionType... estados);

	@Transactional(readOnly = true)
	List<ActualizacionDto> findAllByIntegracionAndEstadoIntegracionAndSubEstadoIntegracionIn(String integracion,
			EstadoIntegracionType estadoIntegracion, String... subEstadoIntegracion);

	@Transactional(readOnly = true)
	List<ActualizacionDto> findAllNoNotificadasByIntegracionAndEstadoIntegracionIn(String integracion,
			EstadoIntegracionType... estados);

	@Transactional(readOnly = true)
	List<String> findAllCorrelacionesByEstadoIntegracionIn(List<EstadoIntegracionType> estados);

	@Transactional
	ActualizacionDto enqueue(ActualizacionDto model);

	@Transactional
	ActualizacionDto update(ActualizacionDto model, EstadoIntegracionType estado, List<ErrorIntegracionDto> errores);

	@Transactional
	ActualizacionDto updateErrorNotificacion(ActualizacionDto model, List<ErrorIntegracionDto> errores);
}
