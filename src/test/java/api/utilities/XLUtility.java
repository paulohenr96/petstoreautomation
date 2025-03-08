package api.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {

	public FileInputStream fi;
	public FileOutputStream fo;

	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;

	public XLUtility(String path) {
		this.path = path;
	}

	public int getRowCount(String sheetName) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		workbook.close();
		fi.close();
		return rowCount;
	}

	public int getCellCount(String sheetName, int rownum) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		int cellCount = (row == null) ? 0 : row.getLastCellNum();
		workbook.close();
		fi.close();
		return cellCount;
	}

	public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		if (row == null) {
			workbook.close();
			fi.close();
			return "";
		}
		cell = row.getCell(colnum);
		String data = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case STRING:
				data = cell.getStringCellValue();
				break;
			case NUMERIC:
				data = String.valueOf(cell.getNumericCellValue());
				break;
			case BOOLEAN:
				data = String.valueOf(cell.getBooleanCellValue());
				break;
			default:
				data = "";
			}
		}
		workbook.close();
		fi.close();
		return data;
	}

	public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			sheet = workbook.createSheet(sheetName);
		}
		row = sheet.getRow(rownum);
		if (row == null) {
			row = sheet.createRow(rownum);
		}
		cell = row.getCell(colnum);
		if (cell == null) {
			cell = row.createCell(colnum);
		}
		cell.setCellValue(data);
		fi.close();
		fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fo.close();
	}

	public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        if (row != null) {
            cell = row.getCell(colnum);
            if (cell != null) {
                style = workbook.createCellStyle();
                style.setFillForegroundColor(IndexedColors.RED.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(style);
            }
        }
        fi.close();
        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fo.close();
    }

    public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        if (row != null) {
            cell = row.getCell(colnum);
            if (cell != null) {
                style = workbook.createCellStyle();
                style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(style);
            }
        }
        fi.close();
        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fo.close();
    }

}
