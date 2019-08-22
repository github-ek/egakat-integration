package com.egakat.integration.core.files.service.impl;

import java.util.ArrayList;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.integration.commons.archivos.domain.Registro;
import com.egakat.integration.commons.archivos.dto.ArchivoDto;
import com.egakat.integration.commons.archivos.dto.EtlRequestDto;
import com.egakat.integration.config.archivos.service.api.CampoCrudService;
import com.egakat.integration.config.archivos.service.api.DirectorioCrudService;
import com.egakat.integration.config.archivos.service.api.LlaveCrudService;
import com.egakat.integration.config.archivos.service.api.TipoArchivoCrudService;
import com.egakat.integration.core.files.components.Constantes;

import lombok.val;

@Service
public class RequestService<T extends Registro> {

	@Autowired
	private TipoArchivoCrudService tiposArchivoService;

	@Autowired
	private CampoCrudService camposService;

	@Autowired
	private LlaveCrudService llavesService;

	@Autowired
	private DirectorioCrudService directoriosService;

	public EtlRequestDto<T, Long> buildRequest(ArchivoDto archivo) {
		val tipoArchivo = tiposArchivoService.findOneById(archivo.getIdTipoArchivo());
		val campos = camposService.findAllByTipoArchivoId(archivo.getIdTipoArchivo());
		val llaves = llavesService.findAllByTipoArchivoId(archivo.getIdTipoArchivo());
		val directorio = directoriosService.findAllByTipoArchivoId(archivo.getIdTipoArchivo()).stream().findFirst();

		Validate.notNull(archivo, Constantes.VALOR_NO_PUEDE_SER_NULO + "archivo");
		Validate.notEmpty(archivo.getNombre(), Constantes.VALOR_NO_PUEDE_SER_UNA_CADENA_VACIA + "archivo.getNombre()");
		Validate.notNull(tipoArchivo, Constantes.VALOR_NO_PUEDE_SER_NULO + "tipoArchivo");
		Validate.notEmpty(campos, Constantes.COLECCION_NO_PUEDE_ESTAR_VACIA + "campos");
		Validate.isTrue(!directorio.isPresent(), Constantes.VALOR_NO_PUEDE_SER_NULO + "directorio");

		val request = new EtlRequestDto<T, Long>();

		request.setArchivo(archivo);
		request.setTipoArchivo(tipoArchivo);
		request.setCampos(campos);
		request.setLlaves(llaves);
		request.setDirectorio(directorio.get());
		request.setDatos(null);
		request.setRegistros(new ArrayList<>());

		return request;
	}
}