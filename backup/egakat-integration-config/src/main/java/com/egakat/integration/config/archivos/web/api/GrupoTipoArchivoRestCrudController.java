package com.egakat.integration.config.archivos.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egakat.core.web.api.controllers.CrudRestController;
import com.egakat.integration.config.archivos.constants.RestConstants;
import com.egakat.integration.config.archivos.dto.GrupoTipoArchivoDto;
import com.egakat.integration.config.archivos.dto.TipoArchivoDto;
import com.egakat.integration.config.archivos.service.api.GrupoTipoArchivoCrudService;
import com.egakat.integration.config.archivos.service.api.TipoArchivoCrudService;

import lombok.val;

@RestController
@RequestMapping(value = RestConstants.grupoTipoArchivo, produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoTipoArchivoRestCrudController extends CrudRestController<GrupoTipoArchivoDto, Long> {

	@Autowired
	private TipoArchivoCrudService tipoArchivoCrudService;
	
	@Autowired
	private GrupoTipoArchivoCrudService service;

	@Override
	protected GrupoTipoArchivoCrudService getService() {
		return service;
	}
	
	@GetMapping(RestConstants.tiposArchivoByGrupoTipoArchivo)
	public ResponseEntity<List<TipoArchivoDto>> getTiposArchivos(@PathVariable long id) {
		val result = tipoArchivoCrudService.findAllByGrupoTipoArchivoId(id);
		return ResponseEntity.ok(result);
	}
}
