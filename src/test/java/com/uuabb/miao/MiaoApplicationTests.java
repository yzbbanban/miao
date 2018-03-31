package com.uuabb.miao;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
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
        List<Map<String,String>> idList = Lists.newArrayList();
        for (int i = 0; i < pageSize; i++) {
            Elements coinTables = doc.select(".table").select(".maintable");
            Elements coinTbody = coinTables.get(0).getElementsByTag("tbody");
            Elements coinTr = coinTbody.get(0).getElementsByTag("tr");
            for (int j = 0; j < coinTr.size(); j++) {
                //获取所要的id、币种名称
//                System.out.println(coinTr.get(j).attr("id"));
                Element coinA=coinTr.get(j).getElementsByTag("td").get(1);
                String coinEnName=coinA.getElementsByTag("a").first().text();
                String id = coinTr.get(j).attr("id");
                if (!StringUtils.isEmpty(id)) {
                    Map<String ,String> map= Maps.newHashMap(id,coinEnName);
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

        for (Map<String,String> map:idList) {
            Iterator<String> iter = map.keySet().iterator();
            while(iter.hasNext()){
                String key=iter.next();
                String value = map.get(key);
                value = value.replaceAll("[\\u4e00-\\u9fa5\\-]","" );
                System.out.println(key+" ："+value);
            }
        }
    }

}
