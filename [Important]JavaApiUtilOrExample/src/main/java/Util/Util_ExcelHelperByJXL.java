package Util;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.List;
import java.util.*;
/**
 * Created by ChenLD on 2017/10/20.
 *
 * @author ChenLD
 * @version 1.0
 *          读写Excel
 */
public class Util_ExcelHelperByJXL {
    /**
     * 将List<List<String>> 写入excel
     * @param table
     * @param xls_path
     * @return
     */
    public static String write2xls(ArrayList<ArrayList<String>> table, String xls_path) {
        //创建Excel文件
        File file = new File(xls_path);
        try {
            file.createNewFile();
            //创建工作簿
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            //创建sheet
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            Label label = null;
            int i = table.size(); //table的行数
            for (int row = 0; row < i; row++) {
                int j = table.get(row).size(); //table当前行的列数
                for (int col = 0; col < j; col++) {
                    label = new Label(col, row, table.get(row).get(col)); //!!!Label对应一个单元格，先col，再row
                    sheet.addCell(label);
                }
            }
            //写入数据
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            return e.toString();
        }
        return "success!";
    }
}
