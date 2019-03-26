package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

import com.egakat.integration.config.archivos.dto.CampoDto;

public class DateTimeMinNumeroDeDiasChecker extends MinChecker<ChronoLocalDateTime<?>> {
	@Override
	protected ChronoLocalDateTime<?> getValorLimite(CampoDto campo) {
		LocalDateTime result = null;
		if (campo.getValorEnteroMin() != null) {
			result = LocalDate.now().atStartOfDay().plusDays(campo.getValorEnteroMin());
		}
		return result;
	}
}