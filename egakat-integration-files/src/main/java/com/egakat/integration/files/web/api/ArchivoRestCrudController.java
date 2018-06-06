package com.egakat.integration.files.web.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egakat.core.web.api.controllers.CrudRestController;
import com.egakat.integration.files.constants.RestConstants;
import com.egakat.integration.files.dto.ArchivoDto;
import com.egakat.integration.files.dto.ArchivoErrorDto;
import com.egakat.integration.files.enums.EstadoArchivoType;
import com.egakat.integration.files.service.api.ArchivoCrudService;
import com.egakat.integration.files.service.api.ArchivoErrorCrudService;

import lombok.val;

@RestController
@RequestMapping(value = RestConstants.archivo, produces = MediaType.APPLICATION_JSON_VALUE)
public class ArchivoRestCrudController extends CrudRestController<ArchivoDto, Long> {

	@Autowired
	private ArchivoCrudService service;

	@Autowired
	private ArchivoErrorCrudService erroresService;

	@Override
	protected ArchivoCrudService getService() {
		return service;
	}

	@GetMapping(path = "get-by/{argv}")
	public ResponseEntity<List<Long>> getAllByTipoArchivoCodigoAndEstadoIn(@MatrixVariable String tipoArchivoCodigo,
			@MatrixVariable(required = false, defaultValue = "") List<EstadoArchivoType> estado) {
		val result = getService().findAllIdByTipoArchivoCodigoAndEstadoIn(tipoArchivoCodigo, estado);
		return ResponseEntity.ok(result);
	}

	@GetMapping(path = "get-by/{argv}", params = { "option=crud" })
	public ResponseEntity<List<ArchivoDto>> getAllByTipoArchivoCodigoAndEstadoIn(
			@MatrixVariable List<Long> tipoArchivo,
			@MatrixVariable(required = false, defaultValue = "") List<EstadoArchivoType> estado,
			@MatrixVariable(required = false, defaultValue = "*") String nombre,
			@MatrixVariable(required = false) @DateTimeFormat(pattern = FORMATO_DATE) LocalDate fechaDesde,
			@MatrixVariable(required = false) @DateTimeFormat(pattern = FORMATO_DATE) LocalDate fechaHasta,
			@MatrixVariable(required = false, defaultValue = "*") String usuario) {
		val result = getService().findAllIdBy(tipoArchivo, estado, nombre, fechaDesde,
				fechaHasta, usuario);
		return ResponseEntity.ok(result);
	}

	@GetMapping(RestConstants.erroresByArchivo)
	public ResponseEntity<List<ArchivoErrorDto>> getErrores(@PathVariable long id) {
		val result = erroresService.findAllByArchivoId(id);
		return ResponseEntity.ok(result);
	}
}
