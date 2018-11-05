package com.egakat.integration.core.files.components.checkers.types;

import java.util.List;

import com.egakat.integration.config.archivos.dto.CampoDto;
import com.egakat.integration.config.archivos.enums.DatoType;
import com.egakat.integration.core.files.components.checkers.CampoChecker;

import lombok.val;

public class IntegerChecker extends DataTypeChecker<Long> {
	@Override
	protected Long parse(CampoDto campo, String valor) {
		try {
			val result = Long.parseLong(valor);
			return result;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	protected String getEjemplosValidos(CampoDto campo) {
		StringBuilder sb = new StringBuilder();
		sb.append(123456);
		sb.append(",");
		sb.append(-123456);

		return sb.toString();
	}

	@Override
	protected DatoType getTipoDato() {
		return DatoType.INTEGER;
	}

	@Override
	protected List<CampoChecker<Long>> getCheckers() {
		val result = super.getCheckers();
		result.add(new IntegerMinChecker());
		result.add(new IntegerMaxChecker());
		return result;
	}
}
