package com.egakat.integration.core.files.components.checkers.types;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.egakat.integration.config.archivos.dto.CampoDto;
import com.egakat.integration.config.archivos.enums.DatoType;
import com.egakat.integration.core.files.components.checkers.CampoChecker;

import lombok.val;

public abstract class DataTypeChecker<T> implements CampoChecker<String> {
	@Override
	public void check(CampoDto campo, String valor) {
		if (StringUtils.hasLength(valor)) {
			T valorTest = null;
			try {
				valorTest = parse(campo, valor);
			} catch (IllegalArgumentException e) {
				String mensaje = gen(campo, valor);
				throw new IllegalArgumentException(mensaje, e);
			}
			val checkers = getCheckers();
			for (CampoChecker<T> checker : checkers) {
				checker.check(campo, valorTest);
			}
		}
	}

	abstract protected T parse(CampoDto campo, String valor);

	protected String gen(CampoDto campo, String valor) {
		String ejemplos = getEjemplosValidos(campo);
		String mensaje = String.format(getError(), campo.getCodigo(), valor, campo.getNombre(), getTipoDato(),
				ejemplos);
		return mensaje;

	}

	@Override
	public String getError() {
		return "%s:El valor %s del campo %s no es un valor de tipo %s valido.Ejemplos validos son:%s";
	}

	abstract protected String getEjemplosValidos(CampoDto campo);

	abstract protected DatoType getTipoDato();

	protected List<CampoChecker<T>> getCheckers() {
		List<CampoChecker<T>> result = new ArrayList<>();
		return result;
	}
}