package com.egakat.integration.core.files.components.decorators;

import static com.egakat.integration.core.files.components.Constantes.VALOR_NO_PUEDE_SER_UNA_CADENA_VACIA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.core.files.components.Constantes;
import com.egakat.integration.files.dto.EtlRequestDto;
import com.egakat.integration.files.dto.RegistroDto;

import lombok.val;

public class LineasSplitterDecorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable> extends Decorator<T, ID> {

	public LineasSplitterDecorator(Decorator<T, ID> inner) {
		super(inner);
	}

	@Override
	public EtlRequestDto<T, ID> transformar(EtlRequestDto<T, ID> archivo) {
		val result = super.transformar(archivo);
		Validate.notNull(result, Constantes.VALOR_NO_PUEDE_SER_NULO + "result");
		Validate.notNull(result.getDatos(), Constantes.VALOR_NO_PUEDE_SER_NULO + "result.getDatos()");

		String separadorRegistros = result.getRegExpSeparadorRegistros();
		Validate.notEmpty(separadorRegistros, VALOR_NO_PUEDE_SER_UNA_CADENA_VACIA + "separadorRegistros");

		String separadorCampos = result.getRegExpSeparadorCampos();
		Validate.notEmpty(separadorCampos, VALOR_NO_PUEDE_SER_UNA_CADENA_VACIA + "separadorCampos");

		val lineas = result.getDatos().split(separadorRegistros, -1);
		val registros = split(lineas, separadorCampos);

		result.getRegistros().clear();
		result.getRegistros().addAll(registros);

		return result;
	}

	private List<RegistroDto<T, ID>> split(String[] lineas, String separadorCampos) {
		List<RegistroDto<T, ID>> result = new ArrayList<>();
		int i = 0;
		for (val linea : lineas) {
			if (StringUtils.isEmpty(linea)) {
				continue;
			}

			String lineaNormalizada = StringUtils.replacePattern(linea, separadorCampos, "");
			if (StringUtils.isEmpty(lineaNormalizada)) {
				continue;
			}

			result.add(new RegistroDto<T, ID>(i++, linea));
		}
		return result;
	}
}