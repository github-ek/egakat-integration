package com.egakat.integration.core.files.components.checkers.types;

import java.math.BigDecimal;

import com.egakat.integration.commons.archivos.dto.CampoDto;

public class DecimalMaxChecker extends MaxChecker<BigDecimal> {
	@Override
	protected BigDecimal getValorLimite(CampoDto campo) {
		return campo.getValorDecimalMax();
	}
	
	@Override
	public String getError() {
		return "%s:El valor %f del campo %s debe ser menor o igual que %s.";
	}
}
