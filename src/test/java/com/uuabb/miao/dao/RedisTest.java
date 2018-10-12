package com.uuabb.miao.dao;

import com.uuabb.miao.service.IAreaService;
import com.uuabb.miao.service.impl.AreaServiceImpl;
import com.uuabb.miao.entity.Area;
import com.uuabb.miao.utils.RedisUtils;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by brander on 2018/3/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    private static final String key = "springboot1219";//这里的key值可以自己修改
    public static final Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IAreaService areaService;//小伙伴请根据自己的service类做修改


    /**
     * @Date:10:11 2017/12/20
     * 查询数据
     * 并将数据数据存在redis里
     */
    @Test
    public void testfind() {
        //如果缓存存在
        boolean hasKey = redisUtils.exists(key.concat("1011"));
        if (hasKey) {
            //获取缓存
            Object object = redisUtils.get(key.concat("1011"));
            logger.info("从缓存获取的数据" + object);
        } else {
            //从DB中获取信息
            logger.info("从数据库中获取数据");
            Area area = areaService.getAreaById(2);//根据ID查询，因为是测试，就直接写了id为1011
            System.out.println(area);
            //数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
            redisUtils.set(key.concat(area.getAreaId().toString()), area, 10L, TimeUnit.MINUTES);
            logger.info("数据插入缓存" + area.toString());
        }

    }

    @Test
    public void testCache() {
        String s = (String) redisUtils.get("getAreaList()");
        System.out.println(s);
    }

    @Test
    public void testPattern() {
        stringRedisTemplate.keys("*" + "ss" + "*");
        String s = (String) redisUtils.get("getAreaList()");
        System.out.println(s);
    }

    /**
     * @Date:10:11 2017/12/20
     * 删除数据
     */
    @Test
    public void testdel() {
        //缓存存在
        boolean hasKey = redisUtils.exists(key.concat("1011"));
        if (hasKey) {
            Object object = redisUtils.get(key.concat("1011"));
            redisUtils.remove(key.concat("1011"));
            logger.info("从缓存中删除数据");
        } else {
            logger.info("缓存中没有数据！");
        }
    }

    private static final String POSTS_DATA = "posts:%s";
    private static final String POSTS_ID = "posts:id";

    @Test
    public void testHash() {
        Long id = 0L;
        String.format(POSTS_DATA, "");
        String token = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis() / 1000;
        String key = "user:";
//        addHash("user:",token,timestamp);
//        removeLimit(key);
        Set<Object> set = redisUtils.zRevRange(key, 1, -1);
        System.out.println("===set===> " + set);
    }

    private void addHash(String key, String token, long timestamp) {

        redisUtils.zAdd(key, token, timestamp);
    }

    private void removeLimit(String key) {
        long limit = redisUtils.zRemRangeByRank(key, 0, 1);
        System.out.println("=======> " + limit);
    }


    @Test
    public void testString() {
        String key = "ban";
        redisUtils.set(key, 2);

        Object s = redisUtils.get(key);
        System.out.println("-----key----->" + s);
        redisUtils.incr(key, 3L);


    }

    @Test
    public void pattern() {
//        redisUtils.sadd("banban", "ban_1");
//        redisUtils.sadd("banban", "yang_2");
//        redisUtils.sadd("banban", "keke_3");
//        redisUtils.sadd("banban", "cici_4");
//        redisUtils.sadd("banban", "vovo_5");
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", "5");
//        map.put("name", "vovo");
//        map.put("title", "vovoO(∩_∩)O~~");
//        redisUtils.hmSet("map5", map);
        Set<Object> names = redisUtils.getMembers("banban");
        System.out.println(names);
        Iterator<Object> it = names.iterator();
        String name = "c";
        Set<Object> result = new HashSet<>();
        while (it.hasNext()) {
            String str = String.valueOf(it.next());
            if (str.contains(name)) {
                str = str.split("_")[1];
                result.add(str);
            }
        }

        System.out.println(result);
        Iterator<Object> reit = result.iterator();
        while (reit.hasNext()){
            System.out.println(redisUtils.hmGet("map"+String.valueOf(reit.next())));
        }


    }


    private static final Pattern QUERY_RE = Pattern.compile("[+-]?[a-z']{2,}");
    private static final Set<String> STOP_WORDS = new HashSet<String>();

    public class Query {
        public final List<List<String>> all = new ArrayList<List<String>>();
        public final Set<String> unwanted = new HashSet<String>();
    }

    public Query parse(String queryString) {
        Query query = new Query();
        Set<String> current = new HashSet<String>();
        Matcher matcher = QUERY_RE.matcher(queryString.toLowerCase());
        while (matcher.find()) {
            String word = matcher.group().trim();
            char prefix = word.charAt(0);
            if (prefix == '+' || prefix == '-') {
                word = word.substring(1);
            }

            if (word.length() < 2 || STOP_WORDS.contains(word)) {
                continue;
            }

            if (prefix == '-') {
                query.unwanted.add(word);
                continue;
            }

            if (!current.isEmpty() && prefix != '+') {
                query.all.add(new ArrayList<String>(current));
                current.clear();
            }
            current.add(word);
        }

        if (!current.isEmpty()) {
            query.all.add(new ArrayList<String>(current));
        }
        return query;
    }


    @Test
    public void testInter(){
        redisUtils.in();
    }


}
