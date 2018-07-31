package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

import com.egakat.integration.files.dto.CampoDto;

public class DateTimeMaxNumeroDeDiasChecker extends MaxChecker<ChronoLocalDateTime<?>> {
	@Override
	protected ChronoLocalDateTime<?> getValorLimite(CampoDto campo) {
		LocalDateTime result = null;
		if (campo.getValorEnteroMax() != null) {
			result = LocalDate.now().atStartOfDay().plusDays(campo.getValorEnteroMax());
		}
		return result;
	}
}