package Util;

/**
 * Created by ChenLD on 2017/12/15.
 *
 * @author ChenLD
 * @version 1.0
 */
public class TypeConvertHelper {

    /**
     * @param inputS
     * @Return MAX_VALUE = 0x7fffffffffffffffL or MIN_VALUE = 0x8000000000000000L; is returned.
     *  If no valid conversion could be performed, a zero value is returned.
     */
    public static  Long stringToInteger(String inputS){
        return atoi(inputS);
    }

    private static Long atoi(String inputS) {
        inputS = inputS.trim();
        return Long.valueOf(inputS);
    }

    public static void main(String[] args){

    }
}
