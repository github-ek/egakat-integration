package com.egakat.integration.config.mapas.web.api;

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
import com.egakat.integration.config.mapas.constants.RestConstants;
import com.egakat.integration.config.mapas.dto.GrupoMapaDto;
import com.egakat.integration.config.mapas.dto.MapaDto;
import com.egakat.integration.config.mapas.service.api.GrupoMapaCrudService;
import com.egakat.integration.config.mapas.service.api.MapaCrudService;

import lombok.val;

@RestController
@RequestMapping(value = RestConstants.grupoMapa, produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoMapaRestCrudController extends CrudRestController<GrupoMapaDto, Long> {

	@Autowired
	private MapaCrudService mapaCrudservice;

	@Autowired
	private GrupoMapaCrudService service;

	@Override
	protected GrupoMapaCrudService getService() {
		return service;
	}

	@GetMapping(params = { "codigo" })
	public ResponseEntity<GrupoMapaDto> get(@RequestParam String codigo) {
		val result = getService().findByCodigo(codigo);

		if (!result.isPresent()) {
			throw new NotFoundException();
		}

		return ResponseEntity.ok(result.get());
	}

	@GetMapping(RestConstants.mapasByGrupoMapa)
	public ResponseEntity<List<MapaDto>> get(@PathVariable long id) {
		val result = mapaCrudservice.findAllByGrupoMapa(id);
		return ResponseEntity.ok(result);
	}

	@GetMapping(path = RestConstants.mapasByGrupoMapa, params = { "codigo" })
	public ResponseEntity<MapaDto> getMapaByCodigo(@PathVariable long id, @RequestParam String codigo) {
		val result = mapaCrudservice.findOneByGrupoMapaIdAndCodigo(id, codigo);
		if (!result.isPresent()) {
			throw new NotFoundException();
		}
		return ResponseEntity.ok(result.get());
	}
}