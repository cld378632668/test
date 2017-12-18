package Util.template;

/**
 * Created by ChenLD on 2017/10/27.
 *
 * @author ChenLD
 * @version 1.0
 */
public class SqlHelperTemplate {

    /**
     * convert results to an insert sql string
     * @return
     */
    public static String ResultToInsertSqlString(){

        //INSERT INTO 表名称 VALUES (值1, 值2,....)
        //INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
      return  "insert into `" ;
    };
}
