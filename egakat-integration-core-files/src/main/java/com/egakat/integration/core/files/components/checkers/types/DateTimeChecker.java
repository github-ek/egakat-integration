package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.egakat.integration.config.archivos.dto.CampoDto;
import com.egakat.integration.config.archivos.enums.DatoType;
import com.egakat.integration.core.files.components.checkers.CampoChecker;

import lombok.val;

public class DateTimeChecker extends DataTypeChecker<ChronoLocalDateTime<?>> {
	@Override
	protected ChronoLocalDateTime<?> parse(CampoDto campo, String valor) {
		DateTimeFormatter formatter = campo.getDateTimeFormatter();
		try {
			val temporalAccessor = formatter.parseBest(valor, LocalDateTime::from, LocalDate::from);

			LocalDateTime result;
			if (temporalAccessor instanceof LocalDateTime) {
				result = (LocalDateTime) temporalAccessor;
			} else {
				result = ((LocalDate) temporalAccessor).atStartOfDay();
			}
			return result;

		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	@Override
	protected List<CampoChecker<ChronoLocalDateTime<?>>> getCheckers() {
		val result = super.getCheckers();
		result.add(new DateTimeMinChecker());
		result.add(new DateTimeMaxChecker());
		result.add(new DateTimeMinNumeroDeDiasChecker());
		result.add(new DateTimeMaxNumeroDeDiasChecker());
		return result;
	}

	@Override
	protected String getEjemplosValidos(CampoDto campo) {
		DateTimeFormatter formatter = campo.getDateTimeFormatter();
		StringBuilder sb = new StringBuilder();
		sb.append(formatter.format(LocalDateTime.now()));
		return sb.toString();
	}

	@Override
	protected DatoType getTipoDato() {
		return DatoType.DATETIME;
	}
}