package com.uuabb.miao;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import com.google.gson.Gson;
import com.uuabb.miao.config.DataCollectionClient;
import com.uuabb.miao.config.MsgWebSocketClient;
import com.uuabb.miao.config.SimpleEchoSocket;
import com.uuabb.miao.config.WebClientEnum;
import com.uuabb.miao.entity.BDate;
import com.uuabb.miao.webSocket.MyClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoApplicationTests {

    @Test
    public void contextLoads() throws Exception {
//		BigDecimal bigDecimal=new BigDecimal("2.2");
//		BigDecimal bigDecimal2=new BigDecimal("2.5");
//		System.out.println(bigDecimal.add(bigDecimal2));


        String url = "https://www.feixiaohao.com";
        Document doc = Jsoup.connect(url).userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000).get();
        //先爬取多少页
        Elements pages = doc.getElementsByClass("pageList");
        Elements pagesArr = pages.first().getElementsByTag("a");
        System.out.println(pagesArr.get(pagesArr.size() - 2).text());
        int pageSize = Integer.parseInt(pagesArr.get(pagesArr.size() - 2).text());
        List<Map<String, String>> idList = Lists.newArrayList();
        for (int i = 0; i < pageSize; i++) {
            Elements coinTables = doc.select(".table").select(".maintable");
            Elements coinTbody = coinTables.get(0).getElementsByTag("tbody");
            Elements coinTr = coinTbody.get(0).getElementsByTag("tr");
            for (int j = 0; j < coinTr.size(); j++) {
                //获取所要的id、币种名称
//                System.out.println(coinTr.get(j).attr("id"));
                Element coinA = coinTr.get(j).getElementsByTag("td").get(1);
                String coinEnName = coinA.getElementsByTag("a").first().text();
                String id = coinTr.get(j).attr("id");
                if (!StringUtils.isEmpty(id)) {
                    Map<String, String> map = Maps.newHashMap(id, coinEnName);
                    idList.add(map);
                }
            }

        }
//        System.out.println(idList);
        //https://api.feixiaohao.com/coinhisdata/bitcoin/
        Document doc1 = Jsoup.connect("https://api.feixiaohao.com/coinhisdata/bitcoin/").userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000).get();
//        System.out.println(doc1.getElementsByTag("body").text());

        for (Map<String, String> map : idList) {
            Iterator<String> iter = map.keySet().iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                String value = map.get(key);
                value = value.replaceAll("[\\u4e00-\\u9fa5\\-]", "");
                System.out.println(key + " ：" + value);
            }
        }
    }

    @Test
    public void testBCoin() {
        try {

            DataCollectionClient dataCollectionClient = new DataCollectionClient("https://bittrex.com/api/v1.1/public/getmarketsummaries");
            String result = DataCollectionClient.httpURLConectionGET();

            Gson gson = new Gson();

            BDate bDate = gson.fromJson(result, BDate.class);
            List<BDate.ResultBean> resultBeenList = bDate.getResult();
            StringBuilder sb = new StringBuilder();
            for (BDate.ResultBean re : resultBeenList) {
                String pair = re.getMarketName().toLowerCase();
                String[] pairArr = pair.split("-");
                sb.append(pairArr[1] + "_" + pairArr[0] + ",");
            }
            System.out.println(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WebSocketClient client;
    @Test
    public void testWebSocket() throws URISyntaxException, UnsupportedEncodingException {
        String destUri = "wss://www.coinw.me/myecho/topCirculation";

        try {
            WebClientEnum.CLIENT.initClient(new MsgWebSocketClient(destUri));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    @Test
    public void testPoloniex() {
        try {

            String htmlAddress = "https://poloniex.com/public?command=returnTicker";
            URL url = new URL(htmlAddress);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.
                    openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            connection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sbStr = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sbStr.append(line);
            }
            bufferedReader.close();
            connection.disconnect();
            String result = new String(sbStr.toString().getBytes(), "utf-8");
            System.out.println(result);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGDAX() {
        try {

            String url = "https://www.gdax.com/trade/BTC-USD";
//            Document doc = Jsoup.connect(url)
//                    .header("Accept-Encoding", "gzip, deflate")
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
//                    .maxBodySize(0)
//                    .timeout(600000)
//                    .get();
////            System.out.println("=======> "+doc.html());
//            Elements elements = doc.getElementsByClass("page_content");
//            System.out.println("======>" + elements.html());
//
//            System.out.println("Thank God!");
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性   请求头 关于http协议请求头内容可以查看我的另一篇博客:http://www.cnblogs.com/dougest/p/7130147.html
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            String result = "";
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
            System.out.println(result);//打印出百度的html

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
