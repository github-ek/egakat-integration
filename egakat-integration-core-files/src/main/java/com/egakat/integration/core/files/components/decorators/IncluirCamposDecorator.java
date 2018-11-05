package com.egakat.integration.core.files.components.decorators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.text.StrSubstitutor;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.config.archivos.dto.CampoDto;
import com.egakat.integration.config.archivos.dto.EtlRequestDto;
import com.egakat.integration.core.files.components.Constantes;

import lombok.val;

public class IncluirCamposDecorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable> extends Decorator<T, ID> {

	public IncluirCamposDecorator(Decorator<T, ID> inner) {
		super(inner);
	}

	@Override
	public EtlRequestDto<T, ID> transformar(EtlRequestDto<T, ID> archivo) {
		val result = super.transformar(archivo);
		Validate.notNull(result, Constantes.VALOR_NO_PUEDE_SER_NULO + "result");

		val campos = result.getCamposIncluidos();
		val registros = result.getRegistros();

		if (!registros.isEmpty() && !campos.isEmpty()) {
			Map<String, String> constantes = new HashMap<>();
			List<CampoDto> variables = new ArrayList<>();

			for (val campo : campos) {
				String valor = campo.getValorPredeterminado();

				if (!(StringUtils.contains(valor, "${") && StringUtils.contains(valor, "}"))) {
					if (campo.isTruncarCaracteres()) {
						valor = StringUtils.left(valor, campo.getNumeroCaracteres());
					}
					constantes.put(campo.getCodigo(), valor);
				} else {
					variables.add(campo);
				}
			}

			for (val registro : registros) {
				registro.getDatos().putAll(constantes);
			}

			for (val campo : variables) {
				String valor = campo.getValorPredeterminado();
				for (val registro : registros) {
					StrSubstitutor sub = new StrSubstitutor(registro.getDatos(), "${", "}");
					String s = sub.replace(valor);
					if (campo.isTruncarCaracteres()) {
						s = StringUtils.left(s, campo.getNumeroCaracteres());
					}
					registro.getDatos().put(campo.getCodigo(), s);
				}
			}
		}

		return result;
	}
}