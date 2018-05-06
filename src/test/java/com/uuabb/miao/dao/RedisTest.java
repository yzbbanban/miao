package com.uuabb.miao.dao;

import com.uuabb.miao.service.IAreaService;
import com.uuabb.miao.service.impl.AreaServiceImpl;
import com.uuabb.miao.entity.Area;
import com.uuabb.miao.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
     *查询数据
     *并将数据数据存在redis里
     */
    @Test
    public void testfind() {
        //如果缓存存在
        boolean hasKey = redisUtils.exists(key.concat("1011"));
        if(hasKey){
            //获取缓存
            Object object =  redisUtils.get(key.concat("1011"));
            logger.info("从缓存获取的数据"+ object);
        }else{
            //从DB中获取信息
            logger.info("从数据库中获取数据");
            Area area = areaService.getAreaById(2);//根据ID查询，因为是测试，就直接写了id为1011
            System.out.println(area);
            //数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
            redisUtils.set(key.concat(area.getAreaId().toString()),area,10L, TimeUnit.MINUTES);
            logger.info("数据插入缓存"+area.toString());
        }

    }

    @Test
    public void testCache(){
        String s= (String) redisUtils.get("getAreaList()");
        System.out.println(s);
    }
    @Test
    public void testPattern(){
        stringRedisTemplate.keys("*"+"ss"+"*");
        String s= (String) redisUtils.get("getAreaList()");
        System.out.println(s);
    }

    /**
     * @Date:10:11 2017/12/20
     *删除数据
     *
     */
    @Test
    public void testdel(){
        //缓存存在
        boolean hasKey = redisUtils.exists(key.concat("1011"));
        if(hasKey){
            Object object =  redisUtils.get(key.concat("1011"));
            redisUtils.remove(key.concat("1011"));
            logger.info("从缓存中删除数据");
        }else {
            logger.info("缓存中没有数据！");
        }
    }

    private static final String POSTS_DATA = "posts:%s";
    private static final String POSTS_ID = "posts:id";

    @Test
    public void testHash(){
        Long id=0L;
        String.format(POSTS_DATA,"");
        String token= UUID.randomUUID().toString();
        long timestamp=System.currentTimeMillis()/1000;
        String key="user:";
//        addHash("user:",token,timestamp);
//        removeLimit(key);
        Set<Object> set=redisUtils. zRevRange(key,1,-1);
        System.out.println("===set===> "+set);
    }

    private void addHash(String key,String token,long timestamp){

        redisUtils.zAdd(key,token,timestamp);
    }

    private void removeLimit(String key){
        long limit=redisUtils.zRemRangeByRank(key,0,1);
        System.out.println("=======> "+limit);
    }


    @Test
    public void testString(){
        String key="ban";
        redisUtils.set(key,2);

        Object s = redisUtils.get(key);
        System.out.println("-----key----->"+s);
        redisUtils.incr(key,3L);
    }

}
