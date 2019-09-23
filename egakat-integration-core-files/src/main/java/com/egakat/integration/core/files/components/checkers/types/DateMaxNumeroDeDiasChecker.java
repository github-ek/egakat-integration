package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

import com.egakat.integration.commons.archivos.dto.CampoDto;

public class DateMaxNumeroDeDiasChecker extends MaxChecker<ChronoLocalDate> {
	@Override
	protected LocalDate getValorLimite(CampoDto campo) {
		LocalDate result = null;
		if (campo.getValorEnteroMax() != null) {
			result = LocalDate.now().plusDays(campo.getValorEnteroMax());
		}
		return result;
	}
}