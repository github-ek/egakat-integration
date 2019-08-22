package com.egakat.integration.core.files.components.decorators;

import java.io.Serializable;

import org.apache.commons.lang3.Validate;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.commons.archivos.dto.EtlRequestDto;
import com.egakat.integration.core.files.components.Constantes;

public abstract class Decorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable> {

	private Decorator<T, ID> inner;

	public Decorator() {
		inner = null;
	}

	public Decorator(Decorator<T, ID> inner) {
		this.inner = inner;
	}

	public EtlRequestDto<T, ID> transformar(EtlRequestDto<T, ID> archivo) {
		Validate.notNull(archivo, Constantes.VALOR_NO_PUEDE_SER_NULO + "archivo");
		if (inner != null) {
			return inner.transformar(archivo);
		} else {
			return archivo;
		}
	}
}
