package com.egakat.integration.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;

import com.egakat.integration.domain.Actualizacion;
import com.egakat.integration.enums.EstadoIntegracionType;
import com.egakat.integration.enums.EstadoNotificacionType;

public interface ActualizacionRepository extends IntegracionEntityRepository<Actualizacion> {

	List<Actualizacion> findAllByIntegracionAndEstadoIntegracionIn(String integracion,
			EstadoIntegracionType[] estadosIntegracion);

	List<Actualizacion> findAllByIntegracionAndEstadoIntegracionAndSubEstadoIntegracionIn(String integracion,
			EstadoIntegracionType estadoIntegracion, String... subEstadosIntegracion);

	Slice<Actualizacion> findAllByIntegracionAndEstadoIntegracionIn(String integracion,
			EstadoIntegracionType[] estadosIntegracion, Pageable pageable);

	Slice<Actualizacion> findAllByIntegracionAndEstadoIntegracionAndSubEstadoIntegracion(String integracion,
			EstadoIntegracionType estadoIntegracion, String subEstadoIntegracion, Pageable pageable);

	List<Actualizacion> findAllByIntegracionAndEstadoIntegracionInAndEstadoNotificacion(String integracion,
			EstadoIntegracionType[] estadosIntegracion, EstadoNotificacionType estadoNotificacion);

	@Query("SELECT DISTINCT a.correlacion FROM Actualizacion a WHERE a.estadoIntegracion IN (:estados) ORDER BY a.correlacion")
	List<String> findAllCorrelacionesByEstadoIntegracionIn(List<EstadoIntegracionType> estados);
}