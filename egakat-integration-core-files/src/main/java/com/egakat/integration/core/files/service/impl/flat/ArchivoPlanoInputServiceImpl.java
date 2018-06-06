package com.egakat.integration.core.files.service.impl.flat;

import org.springframework.beans.factory.annotation.Autowired;

import com.egakat.integration.core.files.components.readers.CharsetDetectorFileReader;
import com.egakat.integration.core.files.components.readers.Reader;
import com.egakat.integration.core.files.service.impl.InputServiceImpl;
import com.egakat.integration.files.domain.Registro;

public abstract class ArchivoPlanoInputServiceImpl<T extends Registro>
		extends InputServiceImpl<T> {

	@Autowired
	protected CharsetDetectorFileReader reader;

	public ArchivoPlanoInputServiceImpl() {
		super();
	}

	@Override
	protected Reader getReader() {
		return reader;
	}
}