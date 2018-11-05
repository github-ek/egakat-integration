package com.egakat.integration.core.files.components.decorators;

import java.io.Serializable;

import org.apache.commons.lang3.Validate;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.config.archivos.dto.EtlRequestDto;
import com.egakat.integration.core.files.components.Constantes;

import lombok.val;

public class MayusculasDecorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable> extends Decorator<T, ID> {

	public MayusculasDecorator(Decorator<T, ID> inner) {
		super(inner);
	}

	@Override
	public EtlRequestDto<T,ID> transformar(EtlRequestDto<T,ID> archivo) {
		val result = super.transformar(archivo);
		Validate.notNull(result, Constantes.VALOR_NO_PUEDE_SER_NULO + "result");
		Validate.notNull(result.getDatos(), Constantes.VALOR_NO_PUEDE_SER_NULO + "result.getDatos()");

		val datos = result.getDatos().toUpperCase();
		result.setDatos(datos);

		return result;
	}
}
