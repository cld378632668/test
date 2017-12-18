package database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ChenLD on 2017/11/21.
 *
 * @author ChenLD
 * @version 1.0
 */
public class ConnectionPools {

    private Vector<Connection> freePools = new Vector<>();
    private Vector<Connection> usingPools;
    private int maxSize = 10;
    private int initSize = 3;
    private Config config;

    public ConnectionPools(Config config){

        try {
            System.out.println(config.getDriver());
            Class.forName(config.getDriver());
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
            System.out.println("here" + e.toString());
        }

        this.config = config;
        this.maxSize = new Integer(config.maxSize);
        this.initSize = new Integer(config.initSize);
        usingPools = new Vector<>();

        for (int i = 0; i <initSize ; i++) {
            try {
                Connection conn = DriverManager.getConnection(config.url,config.username,config.password);
                usingPools.add(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * do shceduled task according to config
         */
        if (Boolean.valueOf(config.getHealth())){
            MyTimeTask myTimeTask = new MyTimeTask();
            new java.util.Timer().schedule(myTimeTask,Long.valueOf(config.delay),Long.valueOf(config.getPeriod()));
        }
    }

    public  synchronized  Connection getConn(){
        Connection conn = null;
        if (freePools.size() > 0 ){
            conn = freePools.remove(0);
            usingPools.add(conn);
            return  conn;
        }else{
            if (usingPools.size() < maxSize){
                try {
                    conn = DriverManager.getConnection(config.url,config.username,config.password);
                    usingPools.add(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else{//等待一定时间后重新申请线程
                try {
                    Thread.sleep(1000);
                    return getConn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return conn;
    }

    /**
     * Move a connection from usingPools to freePools
     * @param conn
     * @return
     */
    public synchronized boolean releaseConn(Connection conn){
       return  usingPools.remove(conn) &&  freePools.add(conn) ;
    }

    /**
     * simulate： the client requests resources from the thread pool and free resources
     * @param args
     */
    public  static  void  main(String[] args){
        Config config = new Config();
        try {
            config.init("db.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ConnectionPools connectionPools = new ConnectionPools(config);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Connection connection = connectionPools.getConn();
                System.out.println("客户端从线程池申请到一个链接："+connection);
                Random random = new Random();
                int runTime = random.nextInt(2000);
                try {
                    Thread.sleep(runTime);
                } catch (InterruptedException e) {

                }
                connectionPools.releaseConn(connection);
                System.out.println("连接"+connection +"使用了"+runTime + "秒。");

            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 60;i++){
            executorService.submit(runnable);
        }
        executorService.shutdown();

    }


    class MyTimeTask extends TimerTask{

        @Override
        public void run() {

        }
    }
}
