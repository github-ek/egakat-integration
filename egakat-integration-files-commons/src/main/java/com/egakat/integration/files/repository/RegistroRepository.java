package com.egakat.integration.files.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.egakat.integration.files.enums.EstadoRegistroType;
import com.egakat.integration.files.domain.Registro;

@NoRepositoryBean
public interface RegistroRepository<T extends Registro> extends JpaRepository<T, Long> {
	List<T> findAllByIdArchivoAndEstadoIn(Long id, List<EstadoRegistroType> estados);
	
	List<T> findAllByIdArchivoAndEstadoNotIn(Long id, List<EstadoRegistroType> estados);
	
	List<Long> findAllArchivoIdByEstadoIn(List<EstadoRegistroType> estados);
}
