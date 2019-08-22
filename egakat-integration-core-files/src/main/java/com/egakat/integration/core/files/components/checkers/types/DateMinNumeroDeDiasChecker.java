package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

import com.egakat.integration.config.archivos.dto.CampoDto;

public class DateMinNumeroDeDiasChecker extends MinChecker<ChronoLocalDate> {
	@Override
	protected LocalDate getValorLimite(CampoDto campo) {
		LocalDate result = null;
		if (campo.getValorEnteroMin() != null) {
			result = LocalDate.now().plusDays(campo.getValorEnteroMin());
		}
		return result;
	}
}