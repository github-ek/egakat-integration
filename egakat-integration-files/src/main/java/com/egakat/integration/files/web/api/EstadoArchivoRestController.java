package com.egakat.integration.files.web.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egakat.integration.files.constants.RestConstants;
import com.egakat.integration.files.enums.EstadoArchivoType;

import lombok.val;

@RestController
@RequestMapping(value = RestConstants.estadoArchivo, produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoArchivoRestController {

	@GetMapping
	public ResponseEntity<EstadoArchivoType[]> list() {
		val result = EstadoArchivoType.values();
		return ResponseEntity.ok(result);
	}
}
