package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalTime;

import com.egakat.integration.config.archivos.dto.CampoDto;

import lombok.val;

public class TimeMinNumeroDeDiasChecker extends MinChecker<LocalTime> {
	@Override
	protected LocalTime getValorLimite(CampoDto campo) {
		LocalTime result = null;
		val minutos = campo.getValorEnteroMin();
		if (minutos != null) {
			result = LocalTime.now().plusMinutes(minutos);
		}
		return result;
	}
}