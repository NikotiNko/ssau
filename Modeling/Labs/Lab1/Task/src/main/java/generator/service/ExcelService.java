package generator.service;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Артем on 06.11.2016.
 */
public class ExcelService {

    public static void putValues(
            String file,
            double mx,
            double dx,
            int count,
            double[] intervalsBounds,
            int[] intervalsValues
    ) throws IOException {
        HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet myExcelSheet = myExcelBook.getSheet("Лист1");
        HSSFRow row = myExcelSheet.getRow(0);

        row.createCell(0).setCellValue(mx);
        row.createCell(1).setCellValue(Math.sqrt(dx));
        row.createCell(2).setCellValue(count);

        for (int i = 0; i < intervalsBounds.length; i++) {
            row = myExcelSheet.getRow(i);
            row.createCell(3).setCellValue(intervalsBounds[i]);
        }

        for (int i = 0; i < intervalsValues.length; i++) {
            row = myExcelSheet.getRow(i);
            row.createCell(4).setCellValue(intervalsValues[i]);
        }
        myExcelBook.write(new FileOutputStream(file));
        myExcelBook.close();
    }

}
