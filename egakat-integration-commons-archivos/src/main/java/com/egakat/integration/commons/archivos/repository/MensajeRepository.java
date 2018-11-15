package com.egakat.integration.commons.archivos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.egakat.integration.commons.archivos.domain.Mensaje;
import com.egakat.integration.commons.archivos.enums.EstadoMensajeType;

@NoRepositoryBean
public interface MensajeRepository<T extends Mensaje> extends JpaRepository<T, Long> {
	List<T> findAllIdByEstadoIn(List<EstadoMensajeType> estados);
}
