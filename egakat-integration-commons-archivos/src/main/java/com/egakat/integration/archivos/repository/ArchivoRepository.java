package com.egakat.integration.archivos.repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.archivos.domain.Archivo;
import com.egakat.integration.archivos.enums.EstadoArchivoType;

public interface ArchivoRepository extends IdentifiedDomainObjectRepository<Archivo, Long> {

	List<Archivo> findAllByIdTipoArchivoAndFechaCreacionGreaterThanEqualAndFechaCreacionLessThanEqual(
			long idTipoArchivo, LocalDateTime fechaDesde, LocalDateTime fechaHasta);

	List<Archivo> findAllByEstadoIn(List<EstadoArchivoType> estados);

	// @formatter:off
	//https://stackoverflow.com/questions/31011797/spring-data-returns-listbiginteger-instead-of-listlong?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
	@Query(value = 
			  " SELECT a.id_archivo  "
			+ " FROM archivos a "
			+ " INNER JOIN tipos_archivo b ON "
			+ "		b.id_tipo_archivo = a.id_tipo_archivo "
			+ " AND b.codigo = :codigo "
			+ " WHERE a.estado IN (:estados) "
			+ " ORDER BY a.id_archivo ", nativeQuery = true)
	List<BigInteger> findAllIdByTipoArchivoCodigoAndEstadoIn(
			 @Param("codigo") String tipoArchivoCodigo
			,@Param("estados") List<String> estados);
	// @formatter:on

	List<Archivo> findAllByIdTipoArchivoInAndEstadoInAndNombreLikeAndFechaCreacionBetweenAndCreadoPorLike(
			List<Long> tiposArchivo, List<EstadoArchivoType> estados, String nombre, LocalDateTime fechaDesde,
			LocalDateTime fechaHasta, String usuario);
}
