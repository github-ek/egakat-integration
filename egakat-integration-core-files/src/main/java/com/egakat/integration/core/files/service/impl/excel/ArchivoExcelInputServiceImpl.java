package com.egakat.integration.core.files.service.impl.excel;

import org.springframework.beans.factory.annotation.Autowired;

import com.egakat.integration.core.files.components.readers.ExcelWorkSheetReader;
import com.egakat.integration.core.files.components.readers.Reader;
import com.egakat.integration.core.files.service.impl.InputServiceImpl;
import com.egakat.integration.files.domain.Registro;

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