package com.egakat.integration.core.files.components.checkers.types;

import java.math.BigDecimal;

import com.egakat.integration.commons.tiposarchivo.dto.CampoDto;

public class DecimalMinChecker extends MinChecker<BigDecimal> {
	@Override
	protected BigDecimal getValorLimite(CampoDto campo) {
		return campo.getValorDecimalMin();
	}
	
	@Override
	public String getError() {
		return "%s:El valor %f del campo %s debe ser mayor o igual que %s.";
	}
}
