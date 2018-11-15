package com.egakat.integration.commons.archivos.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.egakat.core.dto.AuditableEntityDto;

import lombok.AllArgsConstructor;
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
public class ArchivoErrorDto extends AuditableEntityDto<Long> {

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
		val result = new ArchivoErrorDto();

		result.setIdArchivo(archivoId);
		result.setMensaje(mensaje);
		result.setNumeroLinea(numeroLinea);
		result.setDatos(datos);
		result.setCodigo(codigo);
		result.setCreadoPor("");
		result.setModificadoPor("");

		return result;
	}
}