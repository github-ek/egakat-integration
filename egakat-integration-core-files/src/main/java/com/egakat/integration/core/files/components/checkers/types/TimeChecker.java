package com.egakat.integration.core.files.components.checkers.types;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.egakat.integration.core.files.components.checkers.CampoChecker;
import com.egakat.integration.files.dto.CampoDto;
import com.egakat.integration.files.enums.DatoType;

import lombok.val;

public class TimeChecker extends DataTypeChecker<LocalTime> {
	@Override
	protected LocalTime parse(CampoDto campo, String valor) {
		DateTimeFormatter formatter = campo.getDateTimeFormatter();
		try {
			val result = LocalTime.parse(valor, formatter);
			return result;
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	@Override
	protected List<CampoChecker<LocalTime>> getCheckers() {
		val result = super.getCheckers();
		result.add(new TimeMinChecker());
		result.add(new TimeMaxChecker());
		result.add(new TimeMinNumeroDeDiasChecker());
		result.add(new TimeMaxNumeroDeDiasChecker());
		return result;
	}

	@Override
	protected String getEjemplosValidos(CampoDto campo) {
		DateTimeFormatter formatter = campo.getDateTimeFormatter();
		StringBuilder sb = new StringBuilder();
		sb.append(formatter.format(LocalTime.now()));
		return sb.toString();
	}

	@Override
	protected DatoType getTipoDato() {
		return DatoType.TIME;
	}
}