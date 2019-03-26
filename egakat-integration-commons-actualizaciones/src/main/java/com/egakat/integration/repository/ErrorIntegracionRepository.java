package com.egakat.integration.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.domain.ErrorIntegracion;
import com.egakat.integration.enums.EstadoNotificacionType;

public interface ErrorIntegracionRepository extends IdentifiedDomainObjectRepository<ErrorIntegracion, Long> {

	List<ErrorIntegracion> findAllByEstadoNotificacionIn(EstadoNotificacionType[] estados);

	List<ErrorIntegracion> findAllByEstadoNotificacionInAndFechaCreacionGreaterThanEqual(
			EstadoNotificacionType[] estados, LocalDateTime fechaDesde);

	List<ErrorIntegracion> findAllByIntegracionAndEstadoNotificacionIn(String integracion,
			EstadoNotificacionType[] estados);

	List<ErrorIntegracion> findAllByIntegracionAndIdExternoAndCorrelacion(String integracion, String idExterno,
			String correlacion);
}