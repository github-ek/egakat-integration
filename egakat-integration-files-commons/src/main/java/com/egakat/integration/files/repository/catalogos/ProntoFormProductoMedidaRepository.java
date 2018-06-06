package com.egakat.integration.files.repository.catalogos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egakat.integration.files.enums.EstadoRegistroType;
import com.egakat.integration.files.domain.catalogos.ProntoFormProductoMedida;
import com.egakat.integration.files.repository.RegistroRepository;

public interface ProntoFormProductoMedidaRepository extends RegistroRepository<ProntoFormProductoMedida> {

	@Override
	@Query("SELECT DISTINCT a.idArchivo FROM ProntoFormProductoMedida a WHERE a.estado IN (:estados) ORDER BY a.idArchivo")
	List<Long> findAllArchivoIdByEstadoIn(@Param("estados") List<EstadoRegistroType> estado);
}
