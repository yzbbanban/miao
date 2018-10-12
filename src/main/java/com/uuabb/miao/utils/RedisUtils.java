package com.uuabb.miao.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by brander on 2018/3/27
 */
@Service
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 增加
     *
     * @param key
     * @param value
     * @return
     */
    public Long incr(String key, long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }


    /**
     * 增加浮点数
     *
     * @param key
     * @param value
     * @return
     */
    public Double incr(String key, double value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param maps
     */
    public void hmSet(final String key, Map<String, Object> maps) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
//        hash.put(key, hashKey, value);
        hash.putAll(key, maps);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGetValue(final String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @return
     */
    public Object hmGet(final String key) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.entries(key);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(final String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 移除
     *
     * @param key
     * @param item
     * @return
     */
    public Long hmRemove(final String key, Object... item) {
        return redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(final String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void add(final String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> getMembers(final String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(final String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * 获取下标集合（正序）
     *
     * @param key
     * @param from
     * @param to
     * @return
     */
    public Set<Object> zRange(final String key, long from, long to) {
        return redisTemplate.opsForZSet().range(key, from, to);
    }

    /**
     * 有序集合获取,根据 score 区间获取
     *
     * @param key
     * @param score
     * @param score1
     * @return
     */
    public Set<Object> rangeByScore(final String key, double score, double score1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, score, score1);
    }

    /**
     * 计算有序集合的数量
     *
     * @param key
     * @return
     */
    public Long zCard(final String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 删除有序集合区间数据
     *
     * @param key
     * @param from
     * @param to
     */
    public Long zRemRangeByRank(final String key, long from, long to) {
        return redisTemplate.opsForZSet().removeRange(key, from, to);
    }

    public Double zScore(final String key, Object obj) {
        return redisTemplate.opsForZSet().score(key, obj);
    }

    /**
     * 删除多个有序集合
     *
     * @param key
     * @param var2
     * @return
     */
    public Long zRemove(final String key, Object... var2) {
        return redisTemplate.opsForZSet().remove(key, var2);
    }

    /**
     * 根据下标获取所有有序集合（倒序大到小）
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> zRevRange(final String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 分值自增
     *
     * @param key
     * @param obj   自增列
     * @param index 增加数
     */
    public void zIncrBy(final String key, Object obj, double index) {
        redisTemplate.opsForZSet().incrementScore(key, obj, index);
    }

    /**
     * 事务
     *
     * @param key
     * @param obj   自增列
     * @param index 增加数
     */
    public void pipeline(final String key, Object obj, double index) {
//        redisTemplate.executePipelined();
    }

    public void in() {
        Set<String> sets1=new HashSet<>();

        redisTemplate.opsForSet().intersectAndStore("setValue", "destSetValue", "intersectValue");
        Set set = redisTemplate.opsForSet().members("intersectValue");
        System.out.println("通过intersectAndStore(K key, K otherKey, K destKey)方法将求出来的交集元素保存:" + set);
    }

}
