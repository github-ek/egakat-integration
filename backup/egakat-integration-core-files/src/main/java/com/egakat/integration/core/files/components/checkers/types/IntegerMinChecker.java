package com.egakat.integration.core.files.components.checkers.types;

import com.egakat.integration.config.archivos.dto.CampoDto;

public class IntegerMinChecker extends MinChecker<Long> {
	@Override
	protected Long getValorLimite(CampoDto campo) {
		return campo.getValorEnteroMin();
	}
}
