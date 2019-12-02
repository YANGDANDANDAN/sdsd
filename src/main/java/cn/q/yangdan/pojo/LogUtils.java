package cn.q.yangdan.pojo;

import cn.q.yangdan.pojo.Utils.EventLogConstant;
import cn.q.yangdan.pojo.Utils.UserAgentUtils;
import cn.q.yd.pojo.RegionInfo;
import cn.q.yd.uitl.IpUtil;
import cn.q.yd.uitl.Kl;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.yarn.api.records.timeline.TimelineEvents;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.awt.event.KeyListener;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LogUtils {

//    public static Map<String, String> getLog(String log) {
//        Map<String, String> params = new HashMap<String, String>();
//        if (StringUtils.isNotEmpty(log)) {
//            String[] log_files = log.split(EventLogConstant.LOG_COLUMN_NAME_SEPARTOR);
//            if (log_files.length == 4 && log_files[3].contains(EventLogConstant.LOG_SEPARTOR)) {
//                String fields1 = log_files[0];
//                params.put(EventLogConstant.LOG_COLUMN_NAME_IP, fields1);
//                String field4 = log_files[3];
//                String ages = field4.split("\\" + EventLogConstant.LOG_SEPARTOR)[1];
//
//                putArgInMap(ages, params);
//                return params;
//            }
//
//        }
//        return null;
//    }
public static LogWritable getLog(String log) {
    LogWritable params = new LogWritable();
    if (StringUtils.isNotEmpty(log)) {
        //1.切日志
        String[] log_files = log.split(EventLogConstant.LOG_COLUMN_NAME_SEPARTOR);
        //2. 校验日志长度是否合理
        if (log_files.length == 4 && log_files[3].contains(EventLogConstant.LOG_SEPARTOR)) {
            //3. 将前三个切分的field存放到map
            String field1 = log_files[0];
            params.setIp(field1);
            //4. 处理第四个参数
            //4.1 切第四个参数
            String field4 = log_files[3];
            String args = field4.split("\\"+EventLogConstant.LOG_SEPARTOR)[1];
            //4.2 将参数添加到map
            putArgInLogWriable(args, params);
            return params;
        }
    }
    return null;
}
    public static void putArgInLogWriable(String args, LogWritable params) {
        try {
            //1.解码
            String decode = URLDecoder.decode(args, EventLogConstant.LOG_CHARSET);
            //2. 切割参数
            String[] pas = decode.split(EventLogConstant.PARAM_SEPARTOR);

            //------------- 间章 ----------------------
            /*
             *
             */
            //1) 获取要封装的对象的class对象
            Class<LogWritable> clazz = EventLogConstant.CLASS_NAME;

            //3. 遍历往map中添加参数:en=e_pv
            for (String param : pas) {
                String[] arg = param.split(EventLogConstant.ARG_SEPARTOR);
                String name = arg[0];
                String value = arg[1];
                //2) 获取要封装的对象的field对象
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true); // 忽略private的限制
                //3) 通过filed对象给logwriable对象设置属性值
                field.set(params, value);
            }
            //4. 获取到特殊的参数进行处理
            //4.1 将ip转换为地址
            String ip = params.getIp();
            RegionInfo regionInfo = Kl.sendGetRequest2(ip);
            params.setCountry(regionInfo.getCountry());
            params.setProvince(regionInfo.getProvince());
            params.setCity(regionInfo.getCity());

            //4.2 将useragent转换为系统和浏览器
            String useragent = params.getB_iev();
            UserAgentInfo userAgentInfo = UserAgentUtils.getUserAgentInfo(useragent);
            params.setBrowserName(userAgentInfo.getBrowserName());
            params.setBrowserVersion(userAgentInfo.getBrowserVersion());
            params.setOsName(userAgentInfo.getOsName());
            params.setOsVersion(userAgentInfo.getOsVersion());

            //4.3 将时间戳转换为日期
            String c_time = params.getC_time();
            String date = timestamp2DateString(c_time);
            params.setC_time(date);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String timestamp2DateString(String timestamp) {
        long longtime = Long.parseLong(timestamp);
        Date date = new Date(longtime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str_date = format.format(date);

        return str_date;
    }

    public static void putArgInMap(String args, Map<String, String> params) {
        try {

            String decode = URLDecoder.decode(args,EventLogConstant.LOG_CHARSET);
            String[] pas = decode.split(EventLogConstant.PARAM_SEPARTOR);
            for (String pa : pas) {
                String[] arg = pa.split(EventLogConstant.ARG_SEPARTOR);
                String name = arg[0];
                String value = arg[1];
                params.put(name, value);
            }

            String ip = params.get(EventLogConstant.LOG_COLUMN_NAME_IP);
            RegionInfo regionInfo = Kl.sendGetRequest2(ip);
            params.put(EventLogConstant.LOG_COLUMN_NAME_COUNTRY, regionInfo.getCountry());
            params.put(EventLogConstant.LOG_COLUMN_NAME_PROVINCE, regionInfo.getProvince());
            params.put(EventLogConstant.LOG_COLUMN_NAME_CITY, regionInfo.getCity());

            String useragent = params.get(EventLogConstant.LOG_COLUMN_NAME_USER_AGENT);
            UserAgentInfo userAgentInfo = UserAgentUtils.getUserAgentInfo(useragent);
            params.put(EventLogConstant.LOG_COLUMN_NAME_BROWSER_NAME, userAgentInfo.getBrowserName());
            params.put(EventLogConstant.LOG_COLUMN_NAME_BROWSER_VERSION, userAgentInfo.getBrowserVersion());
            params.put(EventLogConstant.LOG_COLUMN_NAME_OS_NAME, userAgentInfo.getOsName());
            params.put(EventLogConstant.LOG_COLUMN_NAME_OS_VERSION, userAgentInfo.getOsVersion());

            String c_time = params.get(EventLogConstant.LOG_COLUMN_NAME_CLIENT_TIME);
            String date = timestamp2DateString(c_time);
            params.put(EventLogConstant.LOG_COLUMN_NAME_CLIENT_TIME, date);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
//   @Test
//   public void test()
//   {
//
//       Map<String, String> log = LogUtils.getLog("192.168.49.1\0011574932647.936\001192.168.49.101\001/index.html?en=e_pv&p_url=http%3A%2F%2Flocalhost%3A8080%2Fszgp1902_log%2Fdemo4.jsp&p_ref=http%3A%2F%2Flocalhost%3A8080%2Fszgp1902_log%2Fdemo4.jsp&tt=%E6%B5%8B%E8%AF%95%E9%A1%B5%E9%9D%A24&ver=1&pl=website&sdk=js&u_ud=74C0F0D2-2EA0-4AE4-8291-046665BE36A1&u_mid=aidon&u_sd=818B38BC-DC5D-4D04-8964-D2FF18D8609A&c_time=1574903848035&l=zh-CN&b_iev=Mozilla%2F5.0%20(Windows%20NT%2010.0%3B%20Win64%3B%20x64%3B%20rv%3A70.0)%20Gecko%2F20100101%20Firefox%2F70.0&b_rst=2144*1206");
//       System.out.println(log);
//
//   }
}
