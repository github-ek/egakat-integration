package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.egakat.integration.core.files.components.checkers.CampoChecker;
import com.egakat.integration.files.dto.CampoDto;
import com.egakat.integration.files.enums.DatoType;

import lombok.val;

public class DateTimeChecker extends DataTypeChecker<ChronoLocalDate> {
	@Override
	protected ChronoLocalDate parse(CampoDto campo, String valor) {
		DateTimeFormatter formatter = campo.getDateTimeFormatter();
		try {
			val result = LocalDateTime.parse(valor, formatter).toLocalDate();
			return result;
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	@Override
	protected List<CampoChecker<ChronoLocalDate>> getCheckers() {
		val result = super.getCheckers();
		result.add(new DateMinChecker());
		result.add(new DateMaxChecker());
		result.add(new DateMinNumeroDeDiasChecker());
		result.add(new DateMaxNumeroDeDiasChecker());
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