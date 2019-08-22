
package com.egakat.integration.core.files.components.checkers.types;

import lombok.val;

public abstract class MinChecker<T extends Comparable<T>> extends MinMaxChecker<T> {
	@Override
	protected boolean comparar(T valor, T valorLimite) {
		int test = valor.compareTo(valorLimite);
		val result = (test < 0);
		return result;
	}

	@Override
	public String getError() {
		return "%s:El valor %s del campo %s debe ser mayor o igual que %s.";
	}
}