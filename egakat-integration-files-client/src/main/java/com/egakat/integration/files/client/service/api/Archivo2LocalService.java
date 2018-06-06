package com.egakat.integration.files.client.service.api;

import java.util.List;

import com.egakat.core.web.client.service.api.LocalCrudService;
import com.egakat.integration.files.dto.ArchivoDto;
import com.egakat.integration.files.dto.ArchivoErrorDto;
import com.egakat.integration.files.enums.EstadoArchivoType;

public interface Archivo2LocalService extends LocalCrudService<ArchivoDto, Long> {

	List<ArchivoDto> findAllByEstadoIn(List<EstadoArchivoType> estados);

	List<ArchivoDto> findAllByTipoArchivoCodigoAndEstadoIn(String tipoArchivoCodigo, List<EstadoArchivoType> estados);

	List<Long> findAllIdByTipoArchivoCodigoAndEstadoIn(String tipoArchivoCodigo, List<EstadoArchivoType> estados);
	
	ArchivoDto extraido(ArchivoDto archivo, List<ArchivoErrorDto> errores);

	ArchivoDto transformado(ArchivoDto archivo, List<ArchivoErrorDto> errores);

	ArchivoDto cargado(ArchivoDto archivo, List<ArchivoErrorDto> errores);
}