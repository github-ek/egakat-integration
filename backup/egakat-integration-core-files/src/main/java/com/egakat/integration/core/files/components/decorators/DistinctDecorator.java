package com.egakat.integration.core.files.components.decorators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.commons.archivos.dto.EtlRequestDto;
import com.egakat.integration.commons.archivos.dto.RegistroDto;

import lombok.val;

public class DistinctDecorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable> extends Decorator<T, ID> {

	public DistinctDecorator(Decorator<T, ID> inner) {
		super(inner);
	}

	@Override
	public EtlRequestDto<T, ID> transformar(EtlRequestDto<T, ID> request) {
		val result = super.transformar(request);

		val lineas = new ArrayList<String>();
		val campos = request.getCamposNoIgnorados().stream().map(a -> a.getCodigo()).collect(Collectors.toList());

		val registros = new ArrayList<RegistroDto<T, ID>>();
		int n = result.getRegistros().size();
		for (int i = 1; i < n; i++) {
			val registro = result.getRegistros().get(i);
			val sb = new StringBuilder();

			for (val key : campos) {
				val value = StringUtils.defaultString(registro.getDatos().get(key));
				sb.append(value).append("|");
			}
			String linea = sb.toString();

			if (lineas.contains(linea)) {
				System.out.println(i + "|" + linea);
				continue;
			} else {
				lineas.add(linea);
				registros.add(registro);
			}
		}

		result.getRegistros().clear();
		result.getRegistros().addAll(registros);
		return result;
	}
}
