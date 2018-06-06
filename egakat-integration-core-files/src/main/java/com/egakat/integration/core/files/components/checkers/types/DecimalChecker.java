package com.egakat.integration.core.files.components.checkers.types;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import com.egakat.integration.core.files.components.checkers.CampoChecker;
import com.egakat.integration.files.dto.CampoDto;
import com.egakat.integration.files.enums.DatoType;

import lombok.val;

public class DecimalChecker extends DataTypeChecker<BigDecimal> {
	@Override
	protected BigDecimal parse(CampoDto campo, String valor) {
		DecimalFormat formatter = campo.getDecimalFormat();
		try {
			val result = (BigDecimal) formatter.parse(valor);
			return result;
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	protected String getEjemplosValidos(CampoDto campo) {
		DecimalFormat formatter = campo.getDecimalFormat();

		StringBuilder sb = new StringBuilder();
		sb.append(formatter.format(-123.123456));
		sb.append(";");
		sb.append(formatter.format(123.123456));
		sb.append(";");
		sb.append(formatter.format(123));
		sb.append(";");
		sb.append(formatter.format(0.123456));

		return sb.toString();
	}

	@Override
	protected List<CampoChecker<BigDecimal>> getCheckers() {
		val result = super.getCheckers();
		result.add(new DecimalMinChecker());
		result.add(new DecimalMaxChecker());
		return result;
	}

	@Override
	protected DatoType getTipoDato() {
		return DatoType.DECIMAL;
	}
}