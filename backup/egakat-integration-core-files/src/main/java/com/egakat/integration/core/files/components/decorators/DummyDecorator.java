package com.egakat.integration.core.files.components.decorators;

import java.io.Serializable;

import com.egakat.core.domain.IdentifiedDomainObject;

public class DummyDecorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable> extends Decorator<T, ID> {

	public DummyDecorator() {
		super();
	}

	public DummyDecorator(Decorator<T, ID> inner) {
		super(inner);
	}
}
