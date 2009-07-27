/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.res0w.jwms.method;

import java.io.File;
import java.io.IOException;
import jxl.*;
import jxl.write.*;

/**
 *
 * @author lqik2004
 */
public class PrintRemainInfo {

    private WritableSheet sheet;
    private WritableWorkbook workbook;

    public void create(String URL, String sheetname) throws IOException {
        workbook = Workbook.createWorkbook(new File(URL));
        sheet = workbook.createSheet(sheetname, 0);
    }

    public void writeSheet(int c, int r, String data) throws WriteException {
        Label label = new Label(c, r, data);
        sheet.addCell(label);
    }

    public void close() throws IOException, WriteException {
        workbook.write();
        workbook.close();
    }
}
