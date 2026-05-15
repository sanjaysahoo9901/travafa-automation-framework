package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;

public class ExcelReader {

    private Workbook workbook;
    private Sheet sheet;

    public ExcelReader(String filePath, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            sheet    = workbook.getSheet(sheetName);
            System.out.println("✅ Excel loaded: " + filePath);

            if (sheet == null) {
                System.out.println("❌ Sheet '" + sheetName + "' NOT FOUND!");
                System.out.println("📋 Available sheets:");
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    System.out.println("   - " + workbook.getSheetName(i));
                }
            } else {
                System.out.println("✅ Sheet found. Physical rows: " + sheet.getPhysicalNumberOfRows());
            }

        } catch (Exception e) {
            System.out.println("❌ Excel error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows() - 1;
    }

    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) return "";
        Cell cell = row.getCell(colNum);
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    public void closeFile() {
        try { workbook.close(); }
        catch (Exception e) { e.printStackTrace(); }
    }
}