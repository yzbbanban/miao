package com.uuabb.miao.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/3/13 0013.
 * 采集数据
 */
public class DataCollectionClient {

    public static String get_url;

    public DataCollectionClient(String url) {
        this.get_url = url;
    }

    /**
     * 接口调用 GET
     */
    public static String httpURLConectionGET() {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(get_url);    // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
        }
        return sb.toString();
    }
}
