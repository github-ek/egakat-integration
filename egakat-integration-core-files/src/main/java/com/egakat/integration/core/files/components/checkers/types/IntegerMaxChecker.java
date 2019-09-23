package com.egakat.integration.core.files.components.checkers.types;

import com.egakat.integration.commons.archivos.dto.CampoDto;

public class IntegerMaxChecker extends MaxChecker<Long> {
	@Override
	protected Long getValorLimite(CampoDto campo) {
		return campo.getValorEnteroMax();
	}
}
