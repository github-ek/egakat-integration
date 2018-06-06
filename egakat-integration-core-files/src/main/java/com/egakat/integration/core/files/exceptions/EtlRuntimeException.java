package com.egakat.integration.core.files.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.egakat.integration.files.dto.ArchivoErrorDto;

import lombok.Getter;
import lombok.val;

@Getter
public class EtlRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private List<ArchivoErrorDto> errores = new ArrayList<>();

	public EtlRuntimeException(long archivoId, String message) {
		super(message);
		val error = ArchivoErrorDto.error(archivoId, message);
		this.errores.add(error);
	}

	public EtlRuntimeException(long archivoId, String message, Throwable cause) {
		super(message, cause);
		val error = ArchivoErrorDto.error(archivoId, message);
		this.errores.add(error);
	}

	public EtlRuntimeException(long archivoId, String message, List<ArchivoErrorDto> errores) {
		super(message);
		this.errores.addAll(errores);
	}

	public EtlRuntimeException(long archivoId, String message, List<ArchivoErrorDto> errores, Throwable cause) {
		super(message, cause);
		this.errores.addAll(errores);
	}
}
