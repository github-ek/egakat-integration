package com.egakat.integration.archivos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.egakat.integration.archivos.domain.Registro;
import com.egakat.integration.archivos.enums.EstadoRegistroType;

@NoRepositoryBean
public interface RegistroRepository<T extends Registro> extends JpaRepository<T, Long> {
	List<T> findAllByIdArchivoAndEstadoIn(Long id, List<EstadoRegistroType> estados);
	
	List<T> findAllByIdArchivoAndEstadoNotIn(Long id, List<EstadoRegistroType> estados);
	
	List<Long> findAllArchivoIdByEstadoIn(List<EstadoRegistroType> estados);
}
