package com.egakat.integration.commons.archivos.service.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.commons.archivos.dto.ArchivoDto;
import com.egakat.integration.commons.archivos.dto.ArchivoErrorDto;
import com.egakat.integration.commons.archivos.enums.EstadoArchivoType;

@Transactional(readOnly = true)
public interface ArchivoCrudService extends CrudService<ArchivoDto, Long> {

	List<ArchivoDto> findAllByTipoArchivoIdAndIntervaloFechas(long idTipoArchivo, LocalDateTime desde,
			LocalDateTime hasta);

	List<ArchivoDto> findAllByEstadoIn(List<EstadoArchivoType> estados);

	List<Long> findAllIdByTipoArchivoCodigoAndEstadoIn(String tipoArchivoCodigo, EstadoArchivoType... estados);

	List<ArchivoDto> findAllIdBy(List<Long> tiposArchivo, List<EstadoArchivoType> estados, String nombre,
			LocalDate fechaDesde, LocalDate fechaHasta, String usuario);

	@Transactional
	ArchivoDto registrarResultadosValidacionEstructura(ArchivoDto archivo, List<ArchivoErrorDto> errores);

	@Transactional
	ArchivoDto registrarResultadosValidacionNegocio(ArchivoDto archivo, List<ArchivoErrorDto> errores);
}
