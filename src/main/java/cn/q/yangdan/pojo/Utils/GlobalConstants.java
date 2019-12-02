package cn.q.yangdan.pojo.Utils;

/**
 * 全局常量
 */
public class GlobalConstants {
    //运行日期的key
    public static final String RUNNING_DATE_FORMAT = "running_date";

    public static final String DEFAULT_VALUE = "未知";

    public static final String ALL_OF_VALUE = "all";

    public static final String DRIVER = "com.mysql.jdbc.Driver";

    public static final String URL = "jdbc:mysql://cdh2:3306/report";

    public static final String USERNAME = "root";

    public static final String PASSWORD = "123456";

    public static final String OUTPUT_COLLECTOR_PREFIX = "collector_";
    public static final String OUTPUT_ETL_PREFIX = "/etl/";

    public static final String INPUT_ETL_PREFIX = "/source_data/";

    public static final  int BATCH_DEFULT_NUMBER = 20;

    public static final long DAY_OF_MILLSECOND = 86400000;
}
