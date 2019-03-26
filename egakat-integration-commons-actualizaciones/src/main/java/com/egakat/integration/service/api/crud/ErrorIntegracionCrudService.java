package com.egakat.integration.service.api.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.dto.ErrorIntegracionDto;
import com.egakat.integration.dto.IntegracionEntityDto;
import com.egakat.integration.enums.EstadoNotificacionType;

public interface ErrorIntegracionCrudService extends CrudService<ErrorIntegracionDto, Long> {

	@Transactional(readOnly = true)
	List<ErrorIntegracionDto> findAll(IntegracionEntityDto model);

	@Transactional(readOnly = true)
	List<ErrorIntegracionDto> findAllByEstadoNotificacionIn(String integracion, EstadoNotificacionType... estados);

	@Transactional(readOnly = true)
	List<ErrorIntegracionDto> findAllByEstadoNotificacionIn(EstadoNotificacionType... estados);

	@Transactional(readOnly = true)
	List<ErrorIntegracionDto> findAllByEstadoNotificacionIn(LocalDateTime fechaDesde,
			EstadoNotificacionType... estados);

	// -----------------------------------------------------------------------------------------------------
	@Transactional
	void create(IntegracionEntityDto model, String codigo, String mensaje, String... arg);

	@Transactional
	void create(String integracion, String correlacion, String codigo, String mensaje, String... arg);

	@Transactional
	void create(IntegracionEntityDto model, String codigo, boolean ignorar, String mensaje, String... arg);

	@Transactional
	void create(String integracion, String correlacion, String codigo, boolean ignorar, String mensaje, String... arg);

	// -----------------------------------------------------------------------------------------------------
	@Transactional
	void create(IntegracionEntityDto model, String codigo, Throwable t);

	@Transactional
	void create(String integracion, String correlacion, String codigo, Throwable t);

	@Transactional
	void create(IntegracionEntityDto model, String codigo, boolean ignorar, Throwable t);

	@Transactional
	void create(String integracion, String correlacion, String codigo, boolean ignorar, Throwable t);

	// -----------------------------------------------------------------------------------------------------
	ErrorIntegracionDto error(IntegracionEntityDto model, String codigo, String mensaje, String... arg);

	ErrorIntegracionDto error(IntegracionEntityDto model, String codigo, Throwable t);

	ErrorIntegracionDto error(IntegracionEntityDto model, String codigo, boolean ignorar, String mensaje,
			String... arg);

	ErrorIntegracionDto error(IntegracionEntityDto model, String codigo, boolean ignorar, Throwable e);

	ErrorIntegracionDto error(String integracion, String correlacion, String idExterno, String codigo, boolean ignorar,
			Throwable e);

}
