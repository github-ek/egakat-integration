package com.egakat.integration.core.files.components.checkers.types;

import java.time.chrono.ChronoLocalDateTime;

import com.egakat.integration.config.archivos.dto.CampoDto;

import lombok.val;

public class DateTimeMinChecker extends MinChecker<ChronoLocalDateTime<?>> {
	@Override
	protected ChronoLocalDateTime<?> getValorLimite(CampoDto campo) {
		val result = campo.getValorFechaMin();
		if (result == null) {
			return null;
		}
		return result.atStartOfDay();
	}
}