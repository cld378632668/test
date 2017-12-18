package Util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;
import java.util.*;

/**
 * Created by ChenLD on 2017/10/23.
 *
 * @author ChenLD
 * @version 1.0
 */
public class FileHelper {

//写操作

    /**
     * 创建新文件
     * @param fileName
     * @return
     */
    public static String createFile(String fileName){
        File file = new File(fileName);
        if (file.exists())
            file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK.";
    };

    /**
     *写字符串到一个文件
     * @param content  例如 "FirstLine\r\nFiled1\tFile2\tFile3\r\n"
     * @param fileName
     * @return
     */
    public static String writeStringToFile(String content,String fileName){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK.";
    };


    //读取操作


    /**
     * 文本文件按行读取存入一个List<String[]>
     * @param pathName
     * @return
     */
    public static List<String[]> readFileToList(String pathName,String seperator){
        File file = new File(pathName);
        ArrayList<String[]> outputList = new ArrayList<String[]>();
        try {
            FileReader fileReader = new FileReader(pathName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String tmp_s = null;
            while((tmp_s = bufferedReader.readLine()) != null){
                outputList.add(tmp_s.split(seperator));
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return outputList;
    };

    /**
     * 文本文件按行读取后添加\n 拼装成一个完整字符串
     * @param pathName
     * @return
     */
    public static String readFileToString(String pathName){
        File file = new File(pathName);
        StringBuffer outputStringBuffer = new StringBuffer();
        try {
            FileReader fileReader = new FileReader(pathName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String tmp_s = null;
            while((tmp_s = bufferedReader.readLine()) != null){
               outputStringBuffer.append(tmp_s+"\n");
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return outputStringBuffer.toString();
    };


 //文本内数据处理

    /**
     * 替换大文件中的分隔符
     * @param filePathName
     * @return
     */
    public static String replaceSeperator(String filePathName,String seperator){

        return "OK!";
    }



 //文件处理

    /**
     * 过滤出一个文件夹下的满足一定条件的所有文件，按照一定规则重命名【例子】
     * @param dirPath_s
     */
    public static String batchFileRenameExample(String dirPath_s){
        File directory = new File(dirPath_s);
        if(!directory.isAbsolute()){
            try {
                throw new Exception("it is not a directory！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FileFilter filesFilter = new FileFilter() {
            public boolean accept(File pathname) {
                //if the filename satisfies a certain condition
                return true;
            }
        };
        File[] files = directory.listFiles(filesFilter);
        for (int i = 0; i < files.length; i++) {
            files[i].renameTo(new File(files[i].getParent(),"newName"+i));
        }

        return "Success.";
    }

    /**
     * 数据去重
     */
    public void removeDuplication(){
        //利用   Set<String> set = new HashSet<String>();
    }

    /**
     * commoms io 按行处理大文件，节约90%以上的内存
     */
    public void  dealByLineTemplate(){
        File file = null;
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(file,"UTF-8");
            while(it.hasNext()){
                String line = it.nextLine();
                //do something with line
            }
        } catch (IOException e) {

        }
       finally {
            LineIterator.closeQuietly(it);
        }
    }

    /**
     * 文件流 + java.util.Scanner 按行处理大文件，节约90%以上的内存
     * @param args
     */
    public void scanLineTemplate(){
        //取消注释继续写完就行
//        FileInputStream inputStream = null;
//        Scanner sc =null;
//        try{
//            inputStream = new FileInputStream(path);
//            sc = new Scanner(inputStream, "UTF-8");
//
//        }
    }

    public static void main(String[] args){
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("034245.sst","rw");
            System.out.println(randomAccessFile.length()/(1024)+"KB");

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

    }
}
