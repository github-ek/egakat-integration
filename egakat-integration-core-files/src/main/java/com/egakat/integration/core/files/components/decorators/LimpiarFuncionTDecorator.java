package com.egakat.integration.core.files.components.decorators;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.core.files.components.Constantes;
import com.egakat.integration.files.dto.EtlRequestDto;

import lombok.val;

public class LimpiarFuncionTDecorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable>
extends Decorator<T, ID> {

	public LimpiarFuncionTDecorator(Decorator<T,ID> inner) {
		super(inner);
	}

	@Override
	public EtlRequestDto<T,ID> transformar(EtlRequestDto<T,ID> archivo) {
		val result = super.transformar(archivo);
		Validate.notNull(result, Constantes.VALOR_NO_PUEDE_SER_NULO + "result");

		Pattern pattern = Pattern.compile((".*=T\\(\\\".*\\\"\\).*"));
		Pattern begin = Pattern.compile("=T\\(\\\"", Pattern.DOTALL);
		Pattern end = Pattern.compile("\\\"\\)", Pattern.DOTALL);

		val registros = result.getRegistros();
		for (val registro : registros) {
			String linea = registro.getLinea();
			if (pattern.matcher(linea).matches()) {
				linea = begin.matcher(linea).replaceAll("");
				linea = end.matcher(linea).replaceAll("");
			}
			registro.setLinea(linea);
		}

		return result;
	}
}