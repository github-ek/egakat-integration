package com.egakat.integration.commons.archivos.service.impl;

import static com.egakat.integration.commons.archivos.enums.EstadoArchivoType.ERROR_ESTRUCTURA;
import static com.egakat.integration.commons.archivos.enums.EstadoArchivoType.ERROR_VALIDACION;
import static com.egakat.integration.commons.archivos.enums.EstadoArchivoType.ESTRUCTURA_VALIDA;
import static com.egakat.integration.commons.archivos.enums.EstadoArchivoType.VALIDADO;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.commons.archivos.domain.Archivo;
import com.egakat.integration.commons.archivos.dto.ArchivoDto;
import com.egakat.integration.commons.archivos.dto.ArchivoErrorDto;
import com.egakat.integration.commons.archivos.enums.EstadoArchivoType;
import com.egakat.integration.commons.archivos.repository.ArchivoRepository;
import com.egakat.integration.commons.archivos.service.api.ArchivoCrudService;
import com.egakat.integration.commons.archivos.service.api.ArchivoErrorCrudService;

import lombok.val;

@Service

public class ArchivoCrudServiceImpl extends CrudServiceImpl<Archivo, ArchivoDto, Long> implements ArchivoCrudService {

	@Autowired
	private ArchivoErrorCrudService erroresCrudService;

	@Autowired
	private ArchivoRepository repository;

	@Override
	protected ArchivoRepository getRepository() {
		return repository;
	}

	@Override
	protected ArchivoDto asModel(Archivo entity) {
		val result = newModel();
		mapModel(entity, result);

		result.setIdTipoArchivo(entity.getIdTipoArchivo());
		result.setNombre(entity.getNombre());
		result.setEstado(entity.getEstado());
		result.setRuta(entity.getRuta());

		return result;
	}

	@Override
	protected ArchivoDto newModel() {
		return new ArchivoDto();
	}

	@Override
	protected Archivo mergeEntity(ArchivoDto model, Archivo entity) {
		entity.setIdTipoArchivo(model.getIdTipoArchivo());
		entity.setNombre(model.getNombre());
		entity.setEstado(model.getEstado());
		entity.setRuta(model.getRuta());

		return entity;
	}

	@Override
	protected Archivo newEntity() {
		return new Archivo();
	}

	@Override
	public List<ArchivoDto> findAllByTipoArchivoIdAndIntervaloFechas(long tipoArchivo, LocalDateTime desde,
			LocalDateTime hasta) {
		val entities = getRepository()
				.findAllByIdTipoArchivoAndFechaCreacionGreaterThanEqualAndFechaCreacionLessThanEqual(tipoArchivo, desde,
						hasta);
		val result = asModels(entities);
		return result;
	}

	@Override
	public List<ArchivoDto> findAllByEstadoIn(List<EstadoArchivoType> estados) {
		val entities = getRepository().findAllByEstadoIn(estados);
		val result = asModels(entities);
		return result;
	}

	@Override
	public List<Long> findAllIdByTipoArchivoCodigoAndEstadoIn(String tipoArchivoCodigo, EstadoArchivoType... estados) {
		val e = Arrays.asList(estados).stream().map(Enum::name).collect(Collectors.toList());
		// @formatter:off
		val result = getRepository().findAllIdByTipoArchivoCodigoAndEstadoIn(tipoArchivoCodigo,e)
				.stream()
				.map(BigInteger::longValue)
				.collect(Collectors.toList());
		// @formatter:on
		return result;
	}

	@Override
	public List<ArchivoDto> findAllIdBy(List<Long> tiposArchivo, List<EstadoArchivoType> estados, String nombre,
			LocalDate fechaDesde, LocalDate fechaHasta, String usuario) {

		if (estados.isEmpty()) {
			estados.addAll(Arrays.asList(EstadoArchivoType.values()));
		}

		if (fechaDesde == null) {
			fechaDesde = LocalDate.now();
		}
		if (fechaHasta == null) {
			fechaHasta = LocalDate.now().plusDays(1);
		}
		fechaHasta = fechaHasta.plusDays(1);

		nombre = nombre.replace('*', '%');
		usuario = usuario.replace('*', '%');

		val entities = getRepository()
				.findAllByIdTipoArchivoInAndEstadoInAndNombreLikeAndFechaCreacionBetweenAndCreadoPorLike(tiposArchivo,
						estados, nombre, fechaDesde.atStartOfDay(), fechaHasta.atStartOfDay(), usuario);
		val result = asModels(entities);
		return result;
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public ArchivoDto registrarResultadosValidacionEstructura(ArchivoDto archivo, List<ArchivoErrorDto> errores) {
		canExtract(archivo);

		clearNullValues(errores);
		if (errores.size() > 0) {
			archivo.setEstado(ERROR_ESTRUCTURA);
		} else {
			archivo.setEstado(ESTRUCTURA_VALIDA);
		}
		saveErrores(archivo.getId(), errores);

		val result = this.update(archivo);
		return result;
	}

	@Override
	public ArchivoDto registrarResultadosValidacionNegocio(ArchivoDto archivo, List<ArchivoErrorDto> errores) {
		canTransform(archivo);

		clearNullValues(errores);
		if (errores.size() > 0) {
			archivo.setEstado(ERROR_VALIDACION);
		} else {
			archivo.setEstado(VALIDADO);
		}
		saveErrores(archivo.getId(), errores);

		val result = this.update(archivo);
		return result;
	}

	protected void clearNullValues(List<ArchivoErrorDto> errores) {
		val notNull = errores.stream().filter(a -> a != null).collect(Collectors.toList());
		errores.clear();
		errores.addAll(notNull);
	}

	protected void saveErrores(long id, List<ArchivoErrorDto> errores) {
		val old = this.erroresCrudService.findAllByArchivoId(id);
		for (val error : old) {
			this.erroresCrudService.delete(error.getId(), error.getVersion());
		}

		for (val error : errores) {
			error.setIdArchivo(id);
			erroresCrudService.create(error);
		}
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	protected void canExtract(ArchivoDto archivo) {
		switch (archivo.getEstado()) {
		case NO_PROCESADO:
			break;
		default:
			String msg = "El estado del archivo es %s. En este estado el archivo no puede leerse.";
			msg = String.format(msg, archivo.getEstado().toString());
			throw new RuntimeException(msg);
		}
	}

	protected void canTransform(ArchivoDto archivo) {
		switch (archivo.getEstado()) {
		case ESTRUCTURA_VALIDA:
		case ERROR_VALIDACION:
		case VALIDADO:
		case ERROR_PROCESAMIENTO:
			break;
		default:
			String msg = "El archivo %d se encuentra en el estado %s. En este estado el archivo no puede se transformado.";
			msg = String.format(msg, archivo.getId(), archivo.getEstado().toString());
			throw new RuntimeException(msg);
		}
	}
}