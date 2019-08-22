package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalTime;

import com.egakat.integration.config.archivos.dto.CampoDto;

public class TimeMinChecker extends MinChecker<LocalTime> {
	@Override
	protected LocalTime getValorLimite(CampoDto campo) {
		return campo.getValorHoraMin();
	}
}