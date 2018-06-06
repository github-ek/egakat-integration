package com.egakat.integration.files.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.egakat.commons.dto.EntityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoErrorDto extends EntityDto<Long> {

	private static final long serialVersionUID = 1L;

	private long idArchivo;

	@NumberFormat
	private int numeroLinea;

	@NotNull
	@Size(max = 50)
	private String codigo;

	@NotNull
	@Size(max = 1024)
	private String mensaje;

	@NotNull
	@Size(max = -1)
	private String datos;

	@Builder
	public ArchivoErrorDto(Long id, int version, String creadoPor, LocalDateTime fechaCreacion, String modificadoPor,
			LocalDateTime fechaModificacion, long idArchivo, int numeroLinea, @NotNull @Size(max = 50) String codigo,
			@NotNull @Size(max = 1024) String mensaje, @NotNull @Size(max = -1) String datos) {
		super(id, version, creadoPor, fechaCreacion, modificadoPor, fechaModificacion);
		this.idArchivo = idArchivo;
		this.numeroLinea = numeroLinea;
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.datos = datos;
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// --
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static ArchivoErrorDto error(long archivoId, Throwable t) {
		return error(archivoId, t, 0, "");
	}

	public static ArchivoErrorDto error(long archivoId, String mensaje) {
		return error(archivoId, mensaje, 0, "", "");
	}

	public static ArchivoErrorDto error(long archivoId, String mensaje, String codigo) {
		return error(archivoId, mensaje, 0, "", codigo);
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// --
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static ArchivoErrorDto error(long archivoId, Throwable t, int numeroLinea, String datos) {
		if (t == null) {
			System.out.println();
			t = new IllegalArgumentException("Throwable t == null");
		}
		String mensaje = t.getMessage() == null ? t.getClass().getName() : t.getMessage();
		return error(archivoId, mensaje, numeroLinea, datos, "");
	}

	public static ArchivoErrorDto error(long archivoId, String mensaje, int numeroLinea, String datos) {
		return error(archivoId, mensaje, numeroLinea, datos, "");
	}

	public static ArchivoErrorDto error(long archivoId, String mensaje, int numeroLinea, String datos, String codigo) {
		// @formatter:off
		val result = ArchivoErrorDto
				.builder()
				.idArchivo(archivoId)
				.mensaje(mensaje)
				.numeroLinea(numeroLinea)
				.datos(datos)
				.codigo(codigo)
				.modificadoPor("")
				.creadoPor("")
				.build();
		// @formatter:on

		return result;
	}
}