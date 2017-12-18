package Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ChenLD on 2017/10/20.
 *
 * @author ChenLD
 * @version 1.0
 */
public class RegexHelper {

    /**
     *
     * @param content
     * @param regexString
     * @return  ArrayList<String> 匹配出的文本列表
     */
    public static List getMatchList(String content, String regexString) {
        List match_list = new ArrayList<String>();
        Pattern p = Pattern.compile(regexString);
        Matcher m = p.matcher(content);
        while(m.find()){
            match_list.add(m.group());
        }
        return match_list;
    }

    public static void main(String[] args){

    }
}
