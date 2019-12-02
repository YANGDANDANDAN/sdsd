package cn.q.yangdan.pojo.Utils;

import cn.q.yangdan.pojo.UserAgentInfo;
import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UserAgentUtils {
    public static UserAgentInfo getUserAgentInfo(String userAgent) {

        String decode = null;
        try {
            decode = URLDecoder.decode(userAgent, "utf-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        UserAgentInfo userAgentInfo = new UserAgentInfo();
        UASparser uaSparser ;
        try {
            uaSparser = new UASparser(OnlineUpdater.getVendoredInputStream());
            cz.mallat.uasparser.UserAgentInfo uaInfo = uaSparser.parse(decode);
            userAgentInfo.setOsName(uaInfo.getOsName());
            userAgentInfo.setOsVersion(uaInfo.getOsCompany());
            userAgentInfo.setBrowserName(uaInfo.getUaName().split(" ")[0]);
            userAgentInfo.setBrowserVersion(uaInfo.getBrowserVersionInfo());
            return userAgentInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
