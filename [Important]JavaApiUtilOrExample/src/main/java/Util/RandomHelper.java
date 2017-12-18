package Util;

import java.util.Random;

/**
 * Created by ChenLD on 2017/11/6.
 *
 * @author ChenLD
 * @version 1.0
 */
public  class RandomHelper {


    /**
     * 生成0 - multiplier 之内的double
     * @param multiplier
     * @return
     */
    double randomDouble(int multiplier){
        Random random = new Random();
        return random.nextDouble()*multiplier; //random.nextDouble()生成(0,1)之间的double
    }




    public static void main(String[] args){
        Random random = new Random();
        int i = 20;
        while(i > 0){
            System.out.println(random.nextDouble()*100);
            i--;
        }
    }
}
