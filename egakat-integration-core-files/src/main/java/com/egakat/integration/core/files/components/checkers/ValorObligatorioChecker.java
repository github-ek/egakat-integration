package com.egakat.integration.core.files.components.checkers;

import org.springframework.util.StringUtils;

import com.egakat.integration.commons.tiposarchivo.dto.CampoDto;

public class ValorObligatorioChecker implements CampoChecker<String> {

	@Override
	public void check(CampoDto campo, String valor) {
		if (!StringUtils.hasLength(valor) && campo.isObligatorioEstructura()) {
			// @formatter:off
			String message = String.format(
					getError(),
					campo.getCodigo(), 
					campo.getNombre());
			// @formatter:on
			throw new RuntimeException(message);
		}
	}

	@Override
	public String getError() {
		return "%s:No se suministró un valor en el campo %s. Este campo es obligatorio y siempre debe ser diligenciado.";
	}
}
