package com.uuabb.miao.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

/**
 * Created by ban on 2018/4/3.
 */
@Component
public class JsoupUtils {
    /**
     * 爬取html数据
     *
     * @param url 请求路径
     * @return 返回doc信息
     * @throws Exception
     */
    public static Document getDoucumentInfoFromUrl(String url) throws Exception {
        Document doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)")
                .cookie("auth", "token")
                .maxBodySize(0)//防止静态页面加载不全
                .timeout(50000).get();
        return doc;
    }

    /**
     * 爬取html数据（忽略contentType）
     *
     * @param url 请求路径
     * @return 返回doc信息
     * @throws Exception
     */
    public static Document getDoucumentInfoFromUrlIgnoreType(String url) throws Exception {
        Document doc = Jsoup.connect(url).ignoreContentType(true).userAgent("Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)")
                .timeout(50000).get();
        return doc;
    }

    /**
     * 爬取html数据 （添加语系）
     *
     * @param url 请求路径
     * @return 返回doc信息
     * @throws Exception
     */
    public static Document getDoucumentInfoFromUrlCN(String url) throws Exception {
        Document doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)")
                .cookie("auth", "token").cookie("lang", "cn")
                .timeout(50000).get();
        return doc;
    }

    /**
     * 解析html数据
     *
     * @param html html内容
     * @return 返回doc信息
     * @throws Exception
     */
    public static Document parseHtml(String html) throws Exception {
        Document doc = Jsoup.parse(html);
        return doc;
    }


    /**
     * 获取ajax动态信息
     *
     * @return
     * @throws Exception
     */
    public static String getWebClientInfo(String url) throws Exception {
        //TODO 屏蔽日志信息 怎么屏蔽啊！！
        //构造一个webClient 模拟Chrome 浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //屏蔽日志信息
        //LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
        //        "org.apache.commons.logging.impl.NoOpLog");
        //java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        //支持JavaScript
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(5000);
        HtmlPage rootPage = webClient.getPage(url);
        //设置一个运行JavaScript的时间
        webClient.waitForBackgroundJavaScript(10 * 1000);
        String html = rootPage.asXml();
        return html;
    }

}
