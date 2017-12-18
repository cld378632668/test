package database;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by ChenLD on 2017/11/20.
 *
 * @author ChenLD
 * @version 1.0
 */
public class Config {
    //fieldName 对应 db.properties中的 jdbc.fieldName
    public String driver;
    public String url;
    public String username;
    public String password;
    public String initSize;
    public String maxSize;
    private String health;
    public String delay;
    private String period;
    public String timeout;

    public Config() {

    }

    public void init(String configFilePath) throws FileNotFoundException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(configFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Object key : properties.keySet()) {
                String fieldName = key.toString().replace("jdbc.",""); //获取形参，怎么获取呢?这不就是配置文件的key去掉，去掉什么呢？去掉"jdbc."
            try{
                Field field = this.getClass().getDeclaredField(fieldName);
                Method setFieldMethod = this.getClass().getMethod(toSetField(fieldName.toString()),field.getType());
                setFieldMethod.invoke(this, properties.getProperty(key.toString()));//调用setter方法
            }catch(Exception e){
                //do nothing
            }

        }
    }

    //根据fieldName得到fieldName对应的setter方法
    public String toSetField(String fieldName) {
        char[] charArray = fieldName.toCharArray();
        charArray[0] -= 32;//把一个字符串的首字母变成大写
        return "set" + new String(charArray);
    }

    @Test
    public void test(){
        try {
            init("db.properties");
        } catch (FileNotFoundException e) {

        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setInitSize(String initSize) {
        this.initSize = initSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDriver() {
        return driver;
    }

    public String getHealth() {
        return health;
    }

    public String getPeriod() {
        return period;
    }
}
