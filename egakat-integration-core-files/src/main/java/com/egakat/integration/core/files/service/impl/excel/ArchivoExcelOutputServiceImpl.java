package com.egakat.integration.core.files.service.impl.excel;

import com.egakat.integration.core.files.service.impl.OutputServiceImpl;
import com.egakat.integration.files.domain.Mensaje;

public abstract class ArchivoExcelOutputServiceImpl<T extends Mensaje> extends OutputServiceImpl<T> {

//	@Autowired
//	private ExcelWorkSheetReader reader;
//
//	public ArchivoExcelExtractServiceImpl() {
//		super();
//	}
//
//	@Override
//	protected Reader getReader() {
//		if (this.reader.getWorkSheetName() == null) {
//			this.reader.setWorkSheetName(getWorkSheetName());
//		}
//		return reader;
//	}

	abstract protected String getWorkSheetName();
}