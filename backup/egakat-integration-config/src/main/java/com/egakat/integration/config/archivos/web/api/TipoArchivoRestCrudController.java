package com.egakat.integration.config.archivos.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egakat.core.web.api.controllers.CrudRestController;
import com.egakat.core.web.api.controllers.NotFoundException;
import com.egakat.integration.config.archivos.constants.RestConstants;
import com.egakat.integration.config.archivos.dto.CampoDto;
import com.egakat.integration.config.archivos.dto.DirectorioDto;
import com.egakat.integration.config.archivos.dto.DirectorioObservableDto;
import com.egakat.integration.config.archivos.dto.LlaveDto;
import com.egakat.integration.config.archivos.dto.TipoArchivoDto;
import com.egakat.integration.config.archivos.service.api.CampoCrudService;
import com.egakat.integration.config.archivos.service.api.DirectorioCrudService;
import com.egakat.integration.config.archivos.service.api.LlaveCrudService;
import com.egakat.integration.config.archivos.service.api.TipoArchivoCrudService;

import lombok.val;

@RestController
@RequestMapping(value = RestConstants.tipoArchivo, produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoArchivoRestCrudController extends CrudRestController<TipoArchivoDto, Long> {

	@Autowired
	private CampoCrudService camposService;

	@Autowired
	private LlaveCrudService llavesService;

	@Autowired
	private DirectorioCrudService directoriosService;

	@Autowired
	private TipoArchivoCrudService service;

	@Override
	protected TipoArchivoCrudService getService() {
		return service;
	}

	@GetMapping(params = { "codigo" })
	public ResponseEntity<TipoArchivoDto> getByCodigo(@RequestParam String codigo) {
		val result = getService().findByCodigo(codigo);

		if (!result.isPresent()) {
			throw new NotFoundException();
		}

		return ResponseEntity.ok(result.get());
	}

	@GetMapping(params = { "aplicacion" })
	public ResponseEntity<List<TipoArchivoDto>> getByAplicacion(@RequestParam String aplication) {
		val result = getService().findAllByAplicacion(aplication);
		return ResponseEntity.ok(result);
	}

	@GetMapping(RestConstants.camposByTipoArchivo)
	public ResponseEntity<List<CampoDto>> getCampos(@PathVariable long id) {
		val result = camposService.findAllByTipoArchivoId(id);
		return ResponseEntity.ok(result);
	}

	@GetMapping(RestConstants.llavesByTipoArchivo)
	public ResponseEntity<List<LlaveDto>> getLlaves(@PathVariable long id) {
		val result = llavesService.findAllByTipoArchivoId(id);
		return ResponseEntity.ok(result);
	}

	@GetMapping(RestConstants.directoriosByTipoArchivo)
	public ResponseEntity<List<DirectorioDto>> getDirectorios(@PathVariable long id) {
		val result = directoriosService.findAllByTipoArchivoId(id);
		return ResponseEntity.ok(result);
	}

	@GetMapping(RestConstants.directoriosObservablesByTipoArchivo)
	public ResponseEntity<List<DirectorioObservableDto>> getDirectoriosObservables(@PathVariable long id) {
		val result = directoriosService.findAllObservablesByTipoArchivoId(id);
		return ResponseEntity.ok(result);
	}
}