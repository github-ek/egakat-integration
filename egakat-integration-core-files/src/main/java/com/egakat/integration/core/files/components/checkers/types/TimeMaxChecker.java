package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalTime;

import com.egakat.integration.commons.tiposarchivo.dto.CampoDto;

public class TimeMaxChecker extends MaxChecker<LocalTime> {
	@Override
	protected LocalTime getValorLimite(CampoDto campo) {
		return campo.getValorHoraMax();
	}
}
