package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalTime;

import com.egakat.integration.config.archivos.dto.CampoDto;

import lombok.val;

public class TimeMaxNumeroDeDiasChecker extends MaxChecker<LocalTime> {
	@Override
	protected LocalTime getValorLimite(CampoDto campo) {
		LocalTime result = null;
		val minutos = campo.getValorEnteroMax();
		if (minutos != null) {
			result = LocalTime.now().plusMinutes(minutos);
		}
		return result;
	}
}