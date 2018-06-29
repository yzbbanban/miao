package com.uuabb.miao.dao;

import com.google.gson.Gson;
import com.itranswarp.compiler.JavaStringCompiler;
import com.uuabb.miao.utils.JsoupUtils;
import org.jsoup.nodes.Document;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by ban on 2018/6/28.
 */
public class Test {
    public static void main(String[] args) {
        try {
            JavaStringCompiler compiler = new JavaStringCompiler();
            Map<String, byte[]> results = compiler.compile("UserProxy.java", JAVA_SOURCE_CODE);
            Class<?> clazz = compiler.loadClass("com.uuabb.miao.dao.UserProxy", results);
            // try instance:
            System.out.println("--------------->" + clazz.newInstance());

            Field[] field = clazz.newInstance().getClass().getClass().getDeclaredFields();
            for (Field f : field) {
                f.setAccessible(true);
                System.out.println("++++++++>" + f.getName());

            }
            Message msg = (Message) clazz.newInstance();
            System.out.println("===============>" + ((Message) clazz.newInstance()).getClass().getName());

            String url = "https://bihangqing.yobtc.com/exchangeinfo/v1/exchanges?pageSize=100&pageNum=1&sortType=&searchText=";
            Document doc = JsoupUtils.getDoucumentInfoFromUrlIgnoreType(url);
            System.out.println("doc" + doc.text());
            Gson gson = new Gson();
            Message m = gson.fromJson(doc.text(), Message.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static final String JAVA_SOURCE_CODE = "/* a single java source file */     "
            + "package com.uuabb.miao.dao;                                      "
            + "public class UserProxy extends com.uuabb.miao.dao.Message {      "
            + "    private String data;                                         "
            + "    public String getData() {                                    "
            + "        return data;                                             "
            + "    }                                                            "
            + "    public void setData(String data) {                           "
            + "        this.data = data;                                        "
            + "    }                                                            "
            + "    @Override                                                    "
            + "    public String toString() {                                   "
            + "        return data;                                             "
            + "    }                                                            "
            + "}";
}

