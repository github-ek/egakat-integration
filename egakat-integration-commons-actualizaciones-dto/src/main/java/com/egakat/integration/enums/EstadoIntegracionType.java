package com.egakat.integration.enums;

public enum EstadoIntegracionType {
	// @formatter:off
    NO_PROCESADO,
    
    ERROR_ESTRUCTURA,
    ESTRUCTURA_VALIDA, 
    
    ERROR_VALIDACION, 
    VALIDADO, 
    
    ERROR_CARGUE,
    CARGADO,
    
    EN_PROCESO,
    ERROR_PROCESAMIENTO,
    PROCESADO,
    
    DESCARTADO
    ;
	// @formatter:on

	public boolean isError() {
		switch (this) {
		case ERROR_ESTRUCTURA:
		case ERROR_VALIDACION:
		case ERROR_CARGUE:
		case ERROR_PROCESAMIENTO:
			return true;
		default:
			return false;
		}
	}
}
