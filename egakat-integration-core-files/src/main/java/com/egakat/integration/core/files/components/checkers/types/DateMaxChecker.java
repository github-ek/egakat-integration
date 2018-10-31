package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

import com.egakat.integration.commons.tiposarchivo.dto.CampoDto;


public class DateMaxChecker extends MaxChecker<ChronoLocalDate> {
	@Override
	protected LocalDate getValorLimite(CampoDto campo) {
		return campo.getValorFechaMax();
	}
}
