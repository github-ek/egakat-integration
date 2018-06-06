package com.egakat.integration.core.files.service.impl;

import static com.egakat.integration.files.enums.EstadoMensajeType.CORREGIDO;
import static com.egakat.integration.files.enums.EstadoMensajeType.NO_PROCESADO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.egakat.integration.core.files.service.api.OutputService;
import com.egakat.integration.files.client.service.api.TipoArchivoLocalService;
import com.egakat.integration.files.domain.Mensaje;
import com.egakat.integration.files.repository.MensajeRepository;

import lombok.val;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class OutputServiceImpl<T extends Mensaje> implements OutputService<T> {

	@Autowired
	private TipoArchivoLocalService tipoArchivoLocalService;

	abstract protected MensajeRepository<T> getRepository();

	@Override
	public List<T> getMensajesPendientes() {
		val result = getRepository().findAllIdByEstadoIn(Arrays.asList(NO_PROCESADO,CORREGIDO));
		return result;
	}

	// -----------------------------------------------------------------------------------------------------------
	// -- EXTRAER
	// -----------------------------------------------------------------------------------------------------------
	@Override
	public void enviar(List<Long> ids) {
		
		val tipoArchivo = tipoArchivoLocalService.findOneByCodigo(this.getTipoArchivoCodigo());
		System.out.println(tipoArchivo.getCodigo());
//		val directorio = tipoArchivoLocalService.getDirectorioByTipoArchivo(tipoArchivo.getId());

//		val now = LocalDateTime.now();
//		val root = directorio.getDirectorioSalidas();
//		val destino = Paths.get(directorio.getSubdirectorioSalidas());;
//		
//		destino = destino.resolve(getDirectorioBackup(now));
//		destino = destino.resolve(getNombreArchivoBackup(Paths.get("TEST"), now) + ".TXT");
//		crearDirectorioSiNoExiste(destino.getParent());
		
	//	log.info("Realizando la generación del archivo ", destino);
//		try (BufferedWriter bw = new BufferedWriter(new FileWriter(destino.toString()))) {
//			bw.write(datos);
//		}

//		try {
//			dump(origen, destino, request.getDatos());
//		} catch (IOException e) {
//			throw new EtlRuntimeException(request.getArchivo().getId(), e.getMessage(), e);
//		}
	}

	protected Path getDirectorioBackup(LocalDateTime now) {
		String value = now.format(getDateTimeFormatter());
		Path result = Paths.get(value.substring(0, 6)).resolve(value.substring(0, 8));
		return result;
	}

	protected String getNombreArchivoBackup(Path archivo, LocalDateTime now) {
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
}