package com.egakat.integration.core.files.components.checkers;

import org.springframework.util.StringUtils;

import com.egakat.integration.config.archivos.dto.CampoDto;

public class ExpresionRegularChecker implements CampoChecker<String> {

	@Override
	public void check(CampoDto campo, String valor) {
		if (StringUtils.hasLength(valor)) {
			if (campo.getPattern() != null) {
				if (!campo.getPattern().matcher(valor).matches()) {
					// @formatter:off
					String message = String.format(
							getError(),
							campo.getCodigo(), 
							valor, 
							campo.getNombre(), 
							campo.getExpresionRegular());
					// @formatter:on
					throw new RuntimeException(message);
				}
			}
		}
	}

	@Override
	public String getError() {
		return "%s:El valor %s del campo %s no cumple con el patron %s.";
	}
}
