/*
 *通过jxl api读取，excel文档信息
 * 可以输入特定列号和行号来规定范围
 */
package method;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.*;
import jxl.read.biff.BiffException;

/**
 *
 * @author res0w
 */
public class ReadExcel {

    public ReadExcel() {
    }

    /**
     *
     * @param filename 输入文件名；
     * @param sheetNum  输入工作簿的序号，从0开始；
     * @param beRow 工作表开始的行号，从0开始（例如：第一行，即为0）；
     * @param endRow 工作表结束的行号；
     * @param infoNum  “商品全名”列号从零开始，（例如：A，为0）；
     * @param amountNum “库存数量”；
     * @param priceNum  “成本均价”；
     * @param allPrsNum “库存总价”；
     * @return  返回object[][]二维数组；
     */
    public Object[][] ReadExcelforInitStore(String filename, int sheetNum, int beRow,
            int endRow, int infoNum, int amountNum, int priceNum, int allPrsNum) {
        Object[][] array = new Object[endRow - beRow + 1][4];
        try {
            Workbook workbook = Workbook.getWorkbook(new File(filename));
            Sheet sheet = workbook.getSheet(sheetNum);
            int j = 0;
            for (int i = beRow; i <= endRow; i++) {
                Cell info = sheet.getCell(infoNum, i);
                Cell amount = sheet.getCell(amountNum, i);
                Cell price = sheet.getCell(priceNum, i);
                Cell allPrice = sheet.getCell(allPrsNum, i);
                array[j][0] = info.getContents();
                array[j][1] = amount.getContents();
                array[j][2] = price.getContents();
                array[j][3] = allPrice.getContents();
                j++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return array;
    }

    public static void main(String[] args) {
        ReadExcel rd = new ReadExcel();
        Object[][] array = rd.ReadExcelforInitStore("temp.xls", 0, 3, 470, 2, 3, 4, 5);
        System.out.println(array[5][0]);
        System.out.println(array[5][1]);
        System.out.println(array[5][2]);
        System.out.println(array[5][3]);
    }
}
