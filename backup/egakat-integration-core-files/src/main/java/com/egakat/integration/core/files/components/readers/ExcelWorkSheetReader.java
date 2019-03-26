package com.egakat.integration.core.files.components.readers;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;

@Component
@Scope(SCOPE_PROTOTYPE)
@Getter
@Setter
public class ExcelWorkSheetReader implements Reader {
	public static final char NON_BREAKING_SPACE = 160;

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private static DataFormatter formatter = new DataFormatter();

	@NonNull
	private String workSheetName;

	private int rowOffset = 0;
	private int colOffset = 0;
	private Integer lastCellNum;

	@Override
	public String read(Path archivo) throws IOException {
		// http://stackoverflow.com/questions/4929646/how-to-get-an-excel-blank-cell-value-in-apache-poi
		Workbook workbook = createWorkBook(archivo);
		Sheet sheet = getWorkSheet(workbook);

		String result = getData(sheet);

		workbook.close();
		return result;
	}

	private Workbook createWorkBook(Path archivo) throws FileNotFoundException, IOException {
		Workbook workbook = null;
		InputStream in = new FileInputStream(archivo.toFile());

		try {
			workbook = WorkbookFactory.create(in);
		} catch (EncryptedDocumentException e1) {
			throw new RuntimeException(e1);
		} catch (InvalidFormatException e1) {
			throw new RuntimeException(e1);
		} finally {
			if (workbook == null) {
				in.close();
			}
		}
		return workbook;
	}

	private Sheet getWorkSheet(Workbook workbook) {
		Sheet sheet;
		int n = workbook.getNumberOfSheets();
		try {
			int index = Integer.valueOf(this.getWorkSheetName());
			if (index == 0 && n > 1) {
				throw new RuntimeException(
						"Se esperaba que este libro de excel solo contviera una hoja, sin ebargo contiene " + n
								+ ".Elimine las hojas de mas yasea que esten visibles u ocultas.");
			}
			sheet = workbook.getSheetAt(index);
		} catch (NumberFormatException e) {
			sheet = workbook.getSheet(this.getWorkSheetName());

			if (sheet == null) {
				val sb = new StringBuilder();
				sb.append("Se esperaba encontrar una hoja con el nombre \"").append(this.getWorkSheetName()).append("\", ").append("pero solo se econtrarón las siguientes:");
				for (int i = 0; i < n; i++) {
					sb.append(workbook.getSheetName(i)).append(", ");
				}
				throw new RuntimeException(sb.toString());
			}
		}
		return sheet;
	}

	private String getData(Sheet sheet) {
		int numeroColumnasEsperado = getNumeroDeColumnasEsperado(sheet, rowOffset);

		StringBuilder sb = new StringBuilder();
		int n = sheet.getLastRowNum();
		for (int i = rowOffset; i <= n; i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				for (int j = colOffset; j < numeroColumnasEsperado; j++) {
					String text = "";
					val cell = row.getCell(j);
					if (cell != null) {
						text = getCellText(cell);
					}
					sb.append(text).append("\t");
				}

				if (sb.length() > 0) {
					sb.setLength(sb.length() - 1);
				}
				sb.append("\n");
			}
		}

		String result = sb.toString();
		return result;
	}

	private int getNumeroDeColumnasEsperado(Sheet sheet, int rowOffset) {
		int result = 0;

		Integer n = this.getLastCellNum();
		if (n != null) {
			result = this.getLastCellNum().intValue();
		} else {
			Row row = sheet.getRow(rowOffset);

			if (row != null) {
				result = row.getLastCellNum();
			}
		}

		return result;
	}

	private String getCellText(Cell cell) {
		String text = "";

		if (cell != null) {
			text = formatter.formatCellValue(cell);
			// Alternatively, get the value and format it yourself
			switch (cell.getCellTypeEnum()) {
			case STRING:
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
					text = ldt.format(dateTimeFormatter);
					text = StringUtils.remove(text, " 00:00:00");
				}
				break;
			case BOOLEAN:
				break;
			case FORMULA:
				break;
			case BLANK:
				break;
			default:
			}
		}

		if (text.startsWith("T(\"") && text.endsWith("\")")) {
			if (text.equals("T(\"\")")) {
				text = "";
			} else {
				text = text.substring(3, text.length() - 2);
			}
		}

		text = StringUtils.replaceChars(text, "\t\n", "  ");
		return text;
	}
}
