package com.egakat.integration.files.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.egakat.integration.files.enums.EstadoMensajeType;
import com.egakat.integration.files.domain.Mensaje;

@NoRepositoryBean
public interface MensajeRepository<T extends Mensaje> extends JpaRepository<T, Long> {
	List<T> findAllIdByEstadoIn(List<EstadoMensajeType> estados);
}
