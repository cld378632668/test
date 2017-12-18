package Util;

/**
 * Created by ChenLD on 2017/10/31.
 *
 * @author ChenLD
 * @version 1.0
 */
public class StringHelper {
    public static boolean isNotEmpty(CharSequence cs) {
        return !StringHelper.isEmpty(cs);
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
