package com.egakat.integration.archivos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.egakat.integration.archivos.domain.Mensaje;
import com.egakat.integration.archivos.enums.EstadoMensajeType;

@NoRepositoryBean
public interface MensajeRepository<T extends Mensaje> extends JpaRepository<T, Long> {
	List<T> findAllIdByEstadoIn(List<EstadoMensajeType> estados);
}
