package com.egakat.integration.core.files.service.impl;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.egakat.integration.commons.archivos.domain.Registro;
import com.egakat.integration.commons.archivos.dto.ArchivoErrorDto;
import com.egakat.integration.commons.archivos.enums.EstadoArchivoType;
import com.egakat.integration.commons.archivos.repository.RegistroRepository;
import com.egakat.integration.commons.archivos.service.api.ArchivoCrudService;
import com.egakat.integration.config.archivos.dto.EtlRequestDto;
import com.egakat.integration.core.files.components.Constantes;
import com.egakat.integration.core.files.components.decorators.CamposSplitterDecorator;
import com.egakat.integration.core.files.components.decorators.CheckNumeroDeColumnasDecorator;
import com.egakat.integration.core.files.components.decorators.CheckRegistrosDuplicadosDecorator;
import com.egakat.integration.core.files.components.decorators.CheckRestriccionesDeCamposDecorator;
import com.egakat.integration.core.files.components.decorators.Decorator;
import com.egakat.integration.core.files.components.decorators.DummyDecorator;
import com.egakat.integration.core.files.components.decorators.IncluirCamposDecorator;
import com.egakat.integration.core.files.components.decorators.LineasSplitterDecorator;
import com.egakat.integration.core.files.components.decorators.MayusculasDecorator;
import com.egakat.integration.core.files.components.decorators.NormalizarSeparadoresDeRegistroDecorator;
import com.egakat.integration.core.files.components.readers.Reader;
import com.egakat.integration.core.files.exceptions.EtlRuntimeException;
import com.egakat.integration.core.files.service.api.InputService;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class InputServiceImpl<T extends Registro> implements InputService<T> {

	@Autowired
	private RequestService<T> requestService;

	@Autowired
	private ArchivoCrudService archivoService;

	abstract protected Reader getReader();

	abstract protected RegistroRepository<T> getRepository();

	@Override
	public List<Long> getArchivosPendientes() {
		val result = archivoService.findAllIdByTipoArchivoCodigoAndEstadoIn(getTipoArchivoCodigo(),
				EstadoArchivoType.NO_PROCESADO);
		return result;
	}

	// -----------------------------------------------------------------------------------------------------------
	// -- EXTRAER
	// -----------------------------------------------------------------------------------------------------------
	@Override
	public void extraer(long archivoId) {
		val archivo = archivoService.findOneById(archivoId);
		val errores = new ArrayList<ArchivoErrorDto>();

		EtlRequestDto<T, Long> request = requestService.buildRequest(archivo);
		boolean error = false;
		try {
			request = extraer(request);
			request = transformar(request);
			request = cargar(request);
		} catch (EtlRuntimeException e) {
			error = true;
			errores.addAll(e.getErrores());
		} catch (RuntimeException e) {
			error = true;
			errores.add(ArchivoErrorDto.error(archivoId, e, 0, ""));
		} finally {
			if (!error) {
				backupSuccess(request);
			} else {
				backupError(request);
			}
		}
		archivoService.registrarResultadosValidacionEstructura(request.getArchivo(), errores);
	}

	// -----------------------------------------------------------------------------------------------------------
	// -- EXTRAER
	// -----------------------------------------------------------------------------------------------------------
	protected EtlRequestDto<T, Long> extraer(EtlRequestDto<T, Long> request) {
		Validate.notNull(request, Constantes.VALOR_NO_PUEDE_SER_NULO + "request");
		Validate.notNull(getReader(), Constantes.VALOR_NO_PUEDE_SER_NULO + "getReader()");

		Path path = request.getPathRuta();
		Validate.notNull(path, Constantes.VALOR_NO_PUEDE_SER_NULO + "request.getPathRuta()");
		Validate.isTrue(Files.exists(path), Constantes.NO_SE_ENCONTRO_EL_ARCHIVO + path.toString());

		try {
			val datos = this.getReader().read(path);
			request.setDatos(datos);

			dump(request);

			return request;
		} catch (IOException | RuntimeException e) {
			log.error(Constantes.ERROR_LECTURA_ARCHIVO, path.getFileName().toString(), e.getMessage());
			throw new EtlRuntimeException(request.getArchivo().getId(), e.getMessage(), e);
		}
	}

	protected void dump(EtlRequestDto<T, Long> request) {
		Validate.notNull(request, Constantes.VALOR_NO_PUEDE_SER_NULO + "request");
		Validate.notNull(request.getDirectorio(), Constantes.VALOR_NO_PUEDE_SER_NULO + "request.getDirectorio()");

		if (request.getDirectorio().isDump()) {
			val origen = request.getPathRuta();
			Validate.notNull(origen, Constantes.VALOR_NO_PUEDE_SER_NULO + "request.getPathRuta()");
			Validate.isTrue(Files.exists(origen), Constantes.NO_SE_ENCONTRO_EL_ARCHIVO + origen.toString());

			val now = LocalDateTime.now();
			Path destino = Paths.get(request.getSubdirectorioDump());
			destino = destino.resolve(getDirectorioBackup(now));
			destino = destino.resolve(getNombreArchivoBackup(origen, now) + ".TXT");

			log.info("Realizando el volcado de datos del archivo {} en el archivo {}", origen.getFileName(), destino);

			try {
				dump(origen, destino, request.getDatos());
			} catch (IOException e) {
				throw new EtlRuntimeException(request.getArchivo().getId(), e.getMessage(), e);
			}
		}
	}

	protected void dump(Path origen, Path destino, String datos) throws IOException {
		Validate.notNull(origen, Constantes.VALOR_NO_PUEDE_SER_NULO + "origen");
		Validate.notNull(destino, Constantes.VALOR_NO_PUEDE_SER_NULO + "destino");
		Validate.isTrue(Files.exists(origen), Constantes.NO_SE_ENCONTRO_EL_ARCHIVO + origen.toString());

		crearDirectorioSiNoExiste(destino.getParent());
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(destino.toString()))) {
			bw.write(datos);
		}
	}

	// -----------------------------------------------------------------------------------------------------------
	// -- TRANSFORMAR
	// -----------------------------------------------------------------------------------------------------------
	protected EtlRequestDto<T, Long> transformar(EtlRequestDto<T, Long> request) {
		val result = this.getTransformador().transformar(request);
		return result;
	}

	protected Decorator<T, Long> getTransformador() {
		// @formatter:off
		val result = getMapEntidadDecorator(
				new CheckRegistrosDuplicadosDecorator<>(
				new CheckRestriccionesDeCamposDecorator<>(
				getFiltrarRegistrosDecorator(
				getEnriquecerCamposDecorator(
				new IncluirCamposDecorator<>(
				new CamposSplitterDecorator<>(
				new CheckNumeroDeColumnasDecorator<>(
				getLimpiarLineasDecorator(
				new LineasSplitterDecorator<>(
				getIncluirEncabezadoDecorator(
				new NormalizarSeparadoresDeRegistroDecorator<>(
				new MayusculasDecorator<>(
				new DummyDecorator<>()
				)))))))))))));
		// @formatter:on

		return result;
	}

	abstract protected Decorator<T, Long> getMapEntidadDecorator(Decorator<T, Long> inner);

	protected Decorator<T, Long> getFiltrarRegistrosDecorator(Decorator<T, Long> inner) {
		return new DummyDecorator<T, Long>(inner);
	}

	protected Decorator<T, Long> getEnriquecerCamposDecorator(Decorator<T, Long> inner) {
		return new DummyDecorator<T, Long>(inner);
	}

	protected Decorator<T, Long> getLimpiarLineasDecorator(Decorator<T, Long> inner) {
		return new DummyDecorator<T, Long>(inner);
	}

	protected Decorator<T, Long> getIncluirEncabezadoDecorator(Decorator<T, Long> inner) {
		return new DummyDecorator<T, Long>(inner);
	}

	// -----------------------------------------------------------------------------------------------------------
	// -- CARGAR
	// -----------------------------------------------------------------------------------------------------------

	@Transactional(readOnly = false)
	protected EtlRequestDto<T, Long> cargar(EtlRequestDto<T, Long> request) {
		val registros = request.getRegistros();
		Validate.notEmpty(registros, "El archivo no contiene registros validos.");

		registros.stream().forEach(registro -> {
			T entity = getRepository().saveAndFlush(registro.getEntidad());
			registro.setEntidad(entity);
		});

		return request;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// BACKUP
	// ----------------------------------------------------------------------------------------------------------------
	protected void backupSuccess(EtlRequestDto<T, Long> request) {
		val origen = request.getPathRuta();
		val destino = Paths.get(request.getSubdirectorioProcesados());

		try {
			val ruta = backup(origen, destino);
			request.getArchivo().setRuta(ruta.toString());
		} catch (IOException e) {
			fatal(request, e);
		}
	}

	protected void backupError(EtlRequestDto<T, Long> request) {
		val origen = request.getPathRuta();
		val destino = Paths.get(request.getSubdirectorioErrores());

		try {
			val ruta = backup(origen, destino);
			request.getArchivo().setRuta(ruta.toString());
		} catch (IOException e) {
			fatal(request, e);
		}
	}

	protected Path backup(Path origen, Path destino) throws IOException {
		Validate.notNull(origen, Constantes.VALOR_NO_PUEDE_SER_NULO + "origen");
		Validate.notNull(destino, Constantes.VALOR_NO_PUEDE_SER_NULO + "destino");

		if (Files.exists(origen)) {
			val now = LocalDateTime.now();
			destino = destino.resolve(getDirectorioBackup(now));
			destino = destino.resolve(getNombreArchivoBackup(origen, now));

			crearDirectorioSiNoExiste(destino.getParent());
			Files.move(origen, destino);
		}

		return destino;
	}

	protected Path getDirectorioBackup(LocalDateTime now) {
		String value = now.format(getDateTimeFormatter());
		Path result = Paths.get(value.substring(0, 6)).resolve(value.substring(0, 8));
		return result;
	}

	protected String getNombreArchivoBackup(Path archivo, LocalDateTime now) {
		Validate.notNull(archivo, Constantes.VALOR_NO_PUEDE_SER_NULO + "archivo");
		Validate.notEmpty(archivo.toString(), Constantes.VALOR_NO_PUEDE_SER_UNA_CADENA_VACIA + "archivo.toString()");
		val prefijo = now.format(getDateTimeFormatter());
		val result = prefijo + "_" + archivo.getFileName().toString();
		return result;
	}

	protected void crearDirectorioSiNoExiste(Path path) throws IOException {
		if (Files.notExists(path)) {
			log.info("Creando directorio {}", path);
			Files.createDirectories(path);
		}
	}

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

	private DateTimeFormatter getDateTimeFormatter() {
		return dateTimeFormatter;
	}

	protected void fatal(EtlRequestDto<T, Long> archivo) {
		fatal(archivo, null);
	}

	// ----------------------------------------------------------------------------------------------------------------
	// FATAL
	// ----------------------------------------------------------------------------------------------------------------
	protected void fatal(EtlRequestDto<T, Long> archivo, Throwable t) {
		val origen = Paths.get(archivo.getArchivo().getRuta());
		val now = LocalDateTime.now().format(getDateTimeFormatter());

		String archivoError;
		if (t != null) {
			archivoError = String.format("%s-%s-%s.error", now, origen.getFileName(), t.getClass().getName());
		} else {
			archivoError = String.format("%s-%s.error", now, origen.getFileName());
		}

		try {
			Path pathError = origen.resolveSibling(archivoError);
			Files.move(origen, pathError, REPLACE_EXISTING);
		} catch (IOException | RuntimeException e) {
			String mensaje = "Ocurrio el siguiente error al intentar renombrar el archivo {} al nombre {}";
			log.error(mensaje, origen.toString(), archivoError, e);
		}
	}
}