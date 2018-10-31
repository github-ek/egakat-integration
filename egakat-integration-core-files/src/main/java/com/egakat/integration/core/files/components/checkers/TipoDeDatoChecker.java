package com.egakat.integration.core.files.components.checkers;

import org.springframework.util.StringUtils;

import com.egakat.integration.commons.tiposarchivo.dto.CampoDto;
import com.egakat.integration.core.files.components.checkers.types.DateChecker;
import com.egakat.integration.core.files.components.checkers.types.DateTimeChecker;
import com.egakat.integration.core.files.components.checkers.types.DecimalChecker;
import com.egakat.integration.core.files.components.checkers.types.IntegerChecker;
import com.egakat.integration.core.files.components.checkers.types.TimeChecker;

public class TipoDeDatoChecker implements CampoChecker<String> {

	@Override
	public void check(CampoDto campo, String valor) {
		if (StringUtils.hasLength(valor)) {
			switch (campo.getTipoDato()) {
			case INTEGER:
				(new IntegerChecker()).check(campo, valor);
				break;
			case DECIMAL:
				(new DecimalChecker()).check(campo, valor);
				break;
			case DATETIME:
				(new DateTimeChecker()).check(campo, valor);
				break;
			case DATE:
				(new DateChecker()).check(campo, valor);
				break;
			case TIME:
				(new TimeChecker()).check(campo, valor);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public String getError() {
		return "";
	}

}
