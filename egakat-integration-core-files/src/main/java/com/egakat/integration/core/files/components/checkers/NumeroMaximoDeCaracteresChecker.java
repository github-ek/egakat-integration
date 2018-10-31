package com.egakat.integration.core.files.components.checkers;

import org.springframework.util.StringUtils;

import com.egakat.integration.commons.tiposarchivo.dto.CampoDto;

public class NumeroMaximoDeCaracteresChecker implements CampoChecker<String> {
	@Override
	public void check(CampoDto campo, String valor) {
		if (StringUtils.hasLength(valor)) {
			if (valor.length() > campo.getNumeroCaracteres()) {
			// @formatter:off
			String message = String.format(
					getError(),
					campo.getCodigo(), 
					valor, 
					campo.getNombre(), 
					valor.length(), 
					campo.getNumeroCaracteres());
			// @formatter:on
				throw new RuntimeException(message);
			}
		}
	}

	@Override
	public String getError() {
		return "%s:El valor %s del campo %s contiene %d caracteres, el maxímo numero de caracteres es %d.";
	}
}