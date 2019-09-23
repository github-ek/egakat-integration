package com.egakat.integration.core.files.components.checkers;

import com.egakat.integration.commons.archivos.dto.CampoDto;

public interface CampoChecker<T> {
	public void check(CampoDto campo, T valor);

	public String getError();
}
