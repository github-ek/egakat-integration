package com.egakat.integration.core.files.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.egakat.integration.commons.archivos.domain.Registro;
import com.egakat.integration.core.files.components.readers.ExcelWorkSheetReader;
import com.egakat.integration.core.files.components.readers.Reader;

public abstract class ArchivoExcelInputServiceImpl<T extends Registro> extends InputServiceImpl<T> {

	@Autowired
	private ExcelWorkSheetReader reader;

	public ArchivoExcelInputServiceImpl() {
		super();
	}

	@Override
	protected Reader getReader() {
		if (this.reader.getWorkSheetName() == null) {
			this.reader.setWorkSheetName(getWorkSheetName());
		}
		return reader;
	}

	abstract protected String getWorkSheetName();
}