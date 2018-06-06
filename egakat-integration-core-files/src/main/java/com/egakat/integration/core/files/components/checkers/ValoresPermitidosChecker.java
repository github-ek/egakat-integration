package com.egakat.integration.core.files.components.checkers;

import org.springframework.util.StringUtils;

import com.egakat.integration.files.dto.CampoDto;

import lombok.val;

public class ValoresPermitidosChecker implements CampoChecker<String> {
	@Override
	public void check(CampoDto campo, String valor) {
		if (StringUtils.hasLength(valor)) {
			val valoresPermitidos = campo.valoresPermitidos();
			if (valoresPermitidos != null) {
				if (!valoresPermitidos.contains(valor)) {
					// @formatter:off
					String message = String.format(
							getError(),
							campo.getCodigo(), 
							valor, 
							campo.getNombre(), 
							campo.getValoresPermitidos());
					// @formatter:on
					throw new RuntimeException(message);
				}
			}
		}
	}

	@Override
	public String getError() {
		return "%s:El valor %s del campo %s no se encuentra entre los valores permitidos, los cuales son:%s.";
	}
}
