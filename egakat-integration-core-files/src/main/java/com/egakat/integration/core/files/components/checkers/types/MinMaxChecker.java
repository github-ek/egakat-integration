package com.egakat.integration.core.files.components.checkers.types;

import com.egakat.integration.core.files.components.checkers.CampoChecker;
import com.egakat.integration.files.dto.CampoDto;

public abstract class MinMaxChecker<T extends Comparable<T>> implements CampoChecker<T> {
	@Override
	public void check(CampoDto campo, T valor) {
		T valorLimite = getValorLimite(campo);
		if (valorLimite != null) {
			if (comparar(valor, valorLimite)) {
				String mensaje = String.format(getError(), campo.getCodigo(), valor.toString(), campo.getNombre(),
						valorLimite.toString());
				throw new IllegalArgumentException(mensaje);
			}
		}
	}

	abstract protected T getValorLimite(CampoDto campo);

	abstract protected boolean comparar(T valor, T valorLimite);
}