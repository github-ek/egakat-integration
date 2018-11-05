package com.egakat.integration.config.mapas.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egakat.core.web.api.controllers.CrudRestController;
import com.egakat.integration.config.mapas.constants.RestConstants;
import com.egakat.integration.config.mapas.dto.MapaDto;
import com.egakat.integration.config.mapas.service.api.MapaCrudService;

@RestController
@RequestMapping(value = RestConstants.mapa, produces = MediaType.APPLICATION_JSON_VALUE)
public class MapaRestCrudController extends CrudRestController<MapaDto, Long> {

	@Autowired
	private MapaCrudService service;

	@Override
	protected MapaCrudService getService() {
		return service;
	}
}
