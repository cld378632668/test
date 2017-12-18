package Util;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by ChenLD on 2017/10/23.
 *
 * @author ChenLD
 * @version 1.0
 */

public class FileCSVHelperByOpenCSV {

    /**
     * 读取CSV全部内容
     * @param filePath
     * @param seperator 分隔符
     * @return
     */
    public static List<String[]> readCSVToList(String filePath, char seperator){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {

        }
        CSVReader csvReader = new CSVReader(fileReader,seperator);
        List<String[]> lines = null;
        try {
            lines = csvReader.readAll();
        } catch (IOException e) {

        }
        return  lines;
    }

}
