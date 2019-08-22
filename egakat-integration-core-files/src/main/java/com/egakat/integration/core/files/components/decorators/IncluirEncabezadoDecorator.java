package com.egakat.integration.core.files.components.decorators;

import static com.egakat.integration.core.files.components.Constantes.VALOR_NO_PUEDE_SER_UNA_CADENA_VACIA;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang3.Validate;
import org.springframework.util.StringUtils;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.commons.archivos.dto.EtlRequestDto;
import com.egakat.integration.core.files.components.Constantes;

import lombok.val;

public class IncluirEncabezadoDecorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable>
		extends Decorator<T, ID> {

	public IncluirEncabezadoDecorator(Decorator<T, ID> inner) {
		super(inner);
	}

	@Override
	public EtlRequestDto<T, ID> transformar(EtlRequestDto<T, ID> archivo) {
		val result = super.transformar(archivo);
		Validate.notNull(result, Constantes.VALOR_NO_PUEDE_SER_NULO + "result");
		Validate.notNull(result.getDatos(), Constantes.VALOR_NO_PUEDE_SER_NULO + "result.getDatos()");

		String separadorRegistros = archivo.getUnescapedSeparadorRegistros();
		String separadorCampos = archivo.getUnescapedSeparadorCampos();

		Validate.notEmpty(separadorRegistros,
				VALOR_NO_PUEDE_SER_UNA_CADENA_VACIA + "archivo.getUnescapedSeparadorRegistros()");
		Validate.notEmpty(separadorCampos,
				VALOR_NO_PUEDE_SER_UNA_CADENA_VACIA + "archivo.getUnescapedSeparadorCampos()");

		val campos = new ArrayList<String>();

		// @formatter:off
		archivo.getCamposNoIncluidos()
		.stream()
		.sorted((a,b) -> Integer.compare(a.getOrdinal(), b.getOrdinal()))
		.forEach(a -> {
			campos.add(a.getNombre());
		});
		// @formatter:on

		val encabezados = StringUtils.collectionToDelimitedString(campos, separadorCampos);

		result.setDatos(encabezados + separadorRegistros + result.getDatos());

		return result;
	}
}
