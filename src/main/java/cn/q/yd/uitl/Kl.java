package cn.q.yd.uitl;

import cn.q.yd.pojo.RegionInfo;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

import java.io.IOException;

public class Kl {
//    @Test
//    public void test() throws IOException {
//        HttpClient httpClient = new HttpClient();
//        GetMethod getMethod = new GetMethod("http://192.168.226.142:80/index.html");
//        int i = httpClient.executeMethod(getMethod);
//        System.out.println("i = " + i);
//    }

    public static int sendGetRequest1(String uri, NameValuePair... nvps) {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(uri);
        getMethod.setQueryString(nvps);
        try {
            int i = httpClient.executeMethod(getMethod);
            return i;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public static RegionInfo sendGetRequest2(String ip) {
        try {


            HttpClient httpClient = new HttpClient();
            GetMethod getMethod = new GetMethod("http://ip.taobao.com/service/getIpInfo.php");
            getMethod.addRequestHeader("content-Type", "application/x-www-form-urlencoded?charset=utf-8");

            NameValuePair[] nameValuePairs = {
                    new NameValuePair("ip", ip)
            };
            getMethod.setQueryString(nameValuePairs);
            int i = httpClient.executeMethod(getMethod);
            RegionInfo regionInfo = new RegionInfo();
            if (i == HttpStatus.SC_OK) {
                String responseString = getMethod.getResponseBodyAsString();
                JSONObject jsonObject = JSONObject.parseObject(responseString);
                JSONObject data = jsonObject.getJSONObject("data");
                String country = data.getString("country");
                String region = data.getString("region");
                String city = data.getString("city");
                regionInfo.setCountry(country);
                regionInfo.setProvince(region);
                regionInfo.setCity(city);

            }
            return regionInfo;

        } catch (Exception e) {
                e.printStackTrace();
        }
        return null;

    }
}
