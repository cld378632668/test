package Util.template;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by ChenLD on 2017/10/31.
 *
 * @author ChenLD
 * @version 1.0
 */
public class SerializeHelper {

    /**
     * 将实现了序列化接口的对象写入文件
     * @param object
     * @param filePath
     * @return
     */
    public static String objectToFile(Object object,String filePath){
        try {
            FileOutputStream fs = new FileOutputStream(filePath);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(object);
            os.close();
        } catch (FileNotFoundException e) {
            return "Failure.";
        } catch (IOException e) {

        }

        return "Success.";
    }
}
