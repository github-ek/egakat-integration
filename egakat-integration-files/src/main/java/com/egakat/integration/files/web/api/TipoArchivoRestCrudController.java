package com.egakat.integration.files.web.api;

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
import com.egakat.integration.files.constants.RestConstants;
import com.egakat.integration.files.dto.CampoDto;
import com.egakat.integration.files.dto.DirectorioDto;
import com.egakat.integration.files.dto.LlaveDto;
import com.egakat.integration.files.dto.TipoArchivoDto;
import com.egakat.integration.files.service.api.CampoCrudService;
import com.egakat.integration.files.service.api.DirectorioCrudService;
import com.egakat.integration.files.service.api.LlaveCrudService;
import com.egakat.integration.files.service.api.TipoArchivoCrudService;

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

	@GetMapping(PATH_LIST)
	public ResponseEntity<List<TipoArchivoDto>> get() {
		val result = getService().findAllActivos();
		return ResponseEntity.ok(result);
	}
	
	@GetMapping(params = { "codigo" })
	public ResponseEntity<TipoArchivoDto> get(@RequestParam String codigo) {
		val result = getService().findByCodigo(codigo);

		if (!result.isPresent()) {
			throw new NotFoundException();
		}

		return ResponseEntity.ok(result.get());
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

	@GetMapping(RestConstants.directorioByTipoArchivo)
	public ResponseEntity<DirectorioDto> getEstados(@PathVariable long id) {
		val list = directoriosService.findAllByTipoArchivoId(id);

		DirectorioDto result = null;
		if (list.size() == 1) {
			result = list.get(0);
		} else {
			result = null;
		}
		
		return ResponseEntity.ok(result);
	}
}