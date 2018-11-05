package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

import com.egakat.integration.config.archivos.dto.CampoDto;

public class DateMinChecker extends MinChecker<ChronoLocalDate> {
	@Override
	protected LocalDate getValorLimite(CampoDto campo) {
		return campo.getValorFechaMin();
	}
}