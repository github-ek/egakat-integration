package com.egakat.integration.files.service.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.files.configuration.JpaConfiguration;
import com.egakat.integration.files.dto.ArchivoDto;
import com.egakat.integration.files.dto.ArchivoErrorDto;
import com.egakat.integration.files.enums.EstadoArchivoType;

@Transactional(readOnly = true, transactionManager = JpaConfiguration.TRANSACTION_MANAGER)
public interface ArchivoCrudService extends CrudService<ArchivoDto, Long> {

	List<ArchivoDto> findAllByTipoArchivoIdAndIntervaloFechas(long idTipoArchivo, LocalDateTime desde,
			LocalDateTime hasta);

	List<ArchivoDto> findAllByEstadoIn(List<EstadoArchivoType> estados);

	List<Long> findAllIdByTipoArchivoCodigoAndEstadoIn(String tipoArchivoCodigo, List<EstadoArchivoType> estados);

	List<ArchivoDto> findAllIdBy(List<Long> tiposArchivo, List<EstadoArchivoType> estados, String nombre,
			LocalDate fechaDesde, LocalDate fechaHasta, String usuario);

	@Transactional
	ArchivoDto registrarResultadosValidacionEstructura(ArchivoDto archivo, List<ArchivoErrorDto> errores);

	@Transactional
	ArchivoDto registrarResultadosValidacionNegocio(ArchivoDto archivo, List<ArchivoErrorDto> errores);
}
