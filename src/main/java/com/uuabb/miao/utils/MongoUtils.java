package com.uuabb.miao.utils;

import com.mongodb.WriteResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author ban
 */
@Component
public class MongoUtils {
    private final Logger log = Logger.getLogger(MongoUtils.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String QUERY_CRITERIA = "请填写查询条件";

    private static final String NO_DATA = "没有此数据";

    private static final String FALSE_DELETE = "删除失败";
    private static final String RIGHT_RANGE_PARAM = "请传入正确的范围参数";
    private static final String UPDATE_PARAM = "请填写需要更新的数据";

    /**
     * 插入一条数据
     *
     * @param obj  插入的数据
     * @param name 插入的表名
     */
    public void saveObject(Object obj, String name) {
        try {
            mongoTemplate.save(obj, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据条件获取获取mongo集合的数量
     *
     * @param name    集合名称
     * @param columns 需要查询的数据列，为null则查询所有
     * @param data    需要查询的值，为null则查询所有
     * @return 集合数量
     */
    public Long getCount(String name, String[] columns, Object[] data) {
        try {
            Query query = new Query();
            if (columns != null && data != null) {
                for (int i = 0; i < columns.length; i++) {
                    query.addCriteria(Criteria.where(columns[i]).is(data[i]));
                }
            }
            return mongoTemplate.count(query, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据范围条件获取获取mongo集合的数量
     *
     * @param name      集合名称
     * @param columns   需要查询的数据列，为null则查询所有
     * @param data      需要查询的值，为null则查询所有
     * @param flagName  需要查询的范围字段
     * @param startFlag 需要查询的起始值
     * @param endFlag   需要查询的起始值
     * @return 集合数量
     */
    public Long getRangeCount(String name, String[] columns, Object[] data,
                              String flagName, Object startFlag, Object endFlag) {
        if (startFlag == null || endFlag == null) {
            return null;
        }
        try {
            Query query = new Query();
            if (columns != null && data != null) {
                for (int i = 0; i < columns.length; i++) {
                    query.addCriteria(Criteria.where(columns[i]).is(data[i]));
                }
                query.addCriteria(Criteria.where(flagName).gte(startFlag).lte(endFlag));
            }
            return mongoTemplate.count(query, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据某个条件获取此类别分组的数量
     *
     * @param name   集合名
     * @param column 分组条件
     * @return 数量
     */
    public Integer getDistinctCount(String name, String column) {
        try {
            return mongoTemplate.getCollection(name).distinct(column).size();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据某个条件获取此类别分组列表
     *
     * @param name   集合名
     * @param column 分组条件
     * @return 列表
     */
    public List getDistinctList(String name, String column) {
        try {
            return mongoTemplate.getCollection(name).distinct(column);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 插入多条数据
     *
     * @param objects 插入的集合
     * @param name    插入表名
     */
    public <T> void saveObjects(List<T> objects, String name) {
        mongoTemplate.insert(objects, name);
    }


    /**
     * 分页查询数据
     *
     * @param name   数据表名
     * @param column 需要查的列名称
     * @param id     指定要查询的数据
     * @param skip   页数
     * @param limit  每页数量
     * @param clz    类（返回值）
     * @return 返回集合数据
     */
    public <T> List<T> queryDataByPage(String name, String column, Object id, int skip, int limit, Class<T> clz) {
        try {
            if (column != null && id != null) {
                Query query = new Query();
                query.addCriteria(Criteria.where(column).is(id));
                return mongoTemplate.find(query.limit(limit).skip(skip), clz, name);
            } else {
                log.info(QUERY_CRITERIA);
                return null;
            }
        } catch (Exception e) {
            log.error(NO_DATA);
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询所有数据
     *
     * @param name   数据表名
     * @param column 需要查的列名称
     * @param id     指定要查询的数据
     * @param clz    类（返回值）
     * @return 返回集合数据
     */
    public <T> List<T> queryAllData(String name, String column, Object id, Class<T> clz) {
        try {
            if (column != null && id != null) {
                Query query = new Query();
                query.addCriteria(Criteria.where(column).is(id));
                return mongoTemplate.find(query, clz, name);
            } else {
                log.info(QUERY_CRITERIA);
                return null;
            }
        } catch (Exception e) {
            log.error(NO_DATA);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取单条数据库中的最新数据
     *
     * @param name    数据表名
     * @param columns 确认需要更新的列名字
     * @param data    确认的值
     * @param clz     类名
     * @return 查询的数据
     */
    public <T> T queryOneData(String name, String[] columns, Object[] data, Class<T> clz) {
        try {
            if (columns != null && data != null) {
                Query query = new Query();
                for (int i = 0; i < columns.length; i++) {
                    query.addCriteria(Criteria.where(columns[i]).is(data[i]));
                }
                return mongoTemplate.findOne(query, clz, name);
            } else {
                log.info(QUERY_CRITERIA);
                return null;
            }
        } catch (Exception e) {
            log.error(NO_DATA);
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取单条数据库中的最新数据
     *
     * @param name    数据表名
     * @param columns 确认需要更新的列名字
     * @param data    确认的值
     * @param order   排序字段
     * @param clz     类名
     * @return 查询的数据
     */
    public <T> T queryOneDataByPage(String name, String[] columns, Object[] data, String order, Class<T> clz) {
        try {
            if (columns != null && data != null) {
                int skip = 0;
                int limit = 1;
                Query query = new Query();
                for (int i = 0; i < columns.length; i++) {
                    query.addCriteria(Criteria.where(columns[i]).is(data[i]));
                }
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, order)));
                return mongoTemplate.findOne(query.limit(limit).skip(skip), clz, name);
            } else {
                log.info(QUERY_CRITERIA);
                return null;
            }
        } catch (Exception e) {
            log.error(NO_DATA);
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新数据(不能使用内部类)
     *
     * @param name    数据表名
     * @param columns 确认需要更新的列名字
     * @param data    确认需要更新的列的值
     * @param clzData 需要更新的数据
     * @param clz     类名
     * @return 更新成功的信息
     */
    public <T> Boolean updateOneData(String name, String[] columns, Object[] data, T clzData, Class<T> clz) {
        try {
            Query query = new Query();
            Update update = new Update();
            if (columns != null && data != null) {
                for (int i = 0; i < columns.length; i++) {
                    query.addCriteria(Criteria.where(columns[i]).is(data[i]));
                }
                Field[] field = clzData.getClass().getDeclaredFields();
                for (int i = 0; i < field.length; i++) {
                    try {
                        //设置是否允许访问，不是修改原来的访问权限修饰词。
                        field[i].setAccessible(true);
                        //Field表示的字段名和字段值
                        if (field[i].get(clzData) != null) {
                            update.set(field[i].getName(), field[i].get(clzData));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                WriteResult w = mongoTemplate.updateMulti(query, update, clz, name);
                return w.getN() >= 1;
            } else {
                log.info(UPDATE_PARAM);
                return null;
            }
        } catch (Exception e) {
            log.error(NO_DATA);
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除数据
     *
     * @param name    数据表名
     * @param columns 需要查的列名称
     * @param data    指定要查询的数据
     * @param clz     类（返回值）
     * @return 删除结果
     */
    public <T> Boolean deleteOneData(String name, String[] columns, Object[] data, Class<T> clz) {
        try {
            Query query = new Query();
            for (int i = 0; i < columns.length; i++) {
                query.addCriteria(Criteria.where(columns[i]).is(data[i]));
            }
            WriteResult result = mongoTemplate.remove(query, clz, name);
            return result.getN() >= 1;
        } catch (Exception e) {
            log.info(FALSE_DELETE);
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除小于限定参数的值
     *
     * @param name     集合名称
     * @param flagName 限定名称
     * @param endFlag  限定参数
     * @param clz      类
     * @param <T>      类
     * @return 删除结果
     */
    public <T> Boolean deleteDataLte(String name, String flagName, Long endFlag, Class<T> clz) {
        try {
            return deleteDataByRange(name, null, null, flagName, null, endFlag, clz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除小于限定参数的值
     *
     * @param name      集合名称
     * @param flagName  限定名称
     * @param startFlag 限定参数
     * @param clz       类
     * @param <T>       类
     * @return 删除结果
     */
    public <T> Boolean deleteDataGte(String name, String flagName, Long startFlag, Class<T> clz) {
        try {
            return deleteDataByRange(name, null, null, flagName, startFlag, null, clz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除小于限定参数的值
     *
     * @param name      集合名称
     * @param flagName  限定名称
     * @param startFlag 限定参数
     * @param clz       类
     * @param <T>       类
     */
    public <T> void deleteDataByRange(String name, String flagName, Long startFlag, Long endFlag, Class<T> clz) {
        try {
            deleteDataByRange(name, null, null, flagName, startFlag, endFlag, clz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除规定范围的集合数据
     * 范围数据可按照需要： >、< 传入单个范围参数
     *
     * @param name      集合名称
     * @param columns   列名 限定条件 限定为null则从整个表查找要删除的数据
     * @param data      限定条件值
     * @param flagName  限定数据名称
     * @param startFlag 范围参数：开始标志 great than 大于参数
     * @param endFlag   范围参数：结束标志 less than 小于参数
     * @param clz       删除的类型
     * @param <T>       类
     */
    public <T> Boolean deleteDataByRange(String name, String[] columns, Object[] data, String flagName, Long startFlag, Long endFlag, Class<T> clz) {
        try {
            if (startFlag == null && endFlag == null) {
                log.info(RIGHT_RANGE_PARAM);
                return false;
            }
            if (columns == null && data != null || columns != null && data == null) {
                log.info(RIGHT_RANGE_PARAM);
                return false;
            }
            Query query = new Query();
            if (startFlag == null) {
                if (columns == null) {
                    query.addCriteria(Criteria.where(flagName).lte(endFlag));
                } else {
                    for (int i = 0; i < columns.length; i++) {
                        query.addCriteria(Criteria.where
                                (columns[i]).is(data[i]));
                    }
                    query.addCriteria(Criteria.where(flagName).lte(endFlag));
                }
            } else if (endFlag == null) {
                if (columns == null) {
                    query.addCriteria(Criteria.where(flagName).gte(startFlag));
                } else {
                    for (int i = 0; i < columns.length; i++) {
                        query.addCriteria(Criteria.where
                                (columns[i]).is(data[i]));
                    }
                    query.addCriteria(Criteria.where(flagName).gte(startFlag));
                }
            } else {
                if (columns == null) {
                    query.addCriteria(Criteria.where(flagName).gte(startFlag).lte(endFlag));
                } else {
                    for (int i = 0; i < columns.length; i++) {
                        query.addCriteria(Criteria.where
                                (columns[i]).is(data[i]));
                    }
                    query.addCriteria(Criteria.where(flagName).gte(startFlag).lte(endFlag));
                }
            }
            WriteResult result = mongoTemplate.remove(query, clz, name);
            return result.getN() >= 1;
        } catch (Exception e) {
            log.info(FALSE_DELETE);
            throw new RuntimeException(e);
        }

    }


    /**
     * 分页查询所有数据
     *
     * @param name  数据表名
     * @param skip  页数
     * @param limit 每页数量
     * @param clz   类（返回值）
     * @return 返回集合数据
     */
    public <T> List<T> queryAllDataByPage(String name, int skip, int limit, Class<T> clz) {
        try {
            Query query = new Query();
            return mongoTemplate.find(query.limit(limit).skip(skip), clz, name);
        } catch (Exception e) {
            log.info(NO_DATA);
            throw new RuntimeException(e);
        }

    }

    /**
     * 分页查询特定的数据
     *
     * @param name    数据表名
     * @param columns 需要查的列名称
     * @param data    指定要查询的数据
     * @param skip    页数
     * @param limit   每页数量
     * @param clz     类（返回值）
     * @return 返回集合数据
     */
    public <T> List<T> queryDataByPage(String name, String[] columns, Object[] data, int skip, int limit, Class<T> clz) {
        try {
            Query query = new Query();
            if (columns != null && data != null) {
                for (int i = 0; i < columns.length - 1; i++) {
                    query.addCriteria(Criteria.where(columns[i]).is(data[i]));
                }
            } else {
                log.info(QUERY_CRITERIA);
                return null;
            }
            return mongoTemplate.find(query.limit(limit).skip(skip), clz, name);
        } catch (Exception e) {
            log.info(NO_DATA);
            throw new RuntimeException(e);
        }

    }

    /**
     * 倒序查询一段范围的数据
     *
     * @param name      数据表名
     * @param columns   需要查的列名称
     * @param data      指定要查询的数据
     * @param flagName  限定范围的名称
     * @param startFlag 开始范围
     * @param endFlag   结束范围
     * @param order     排序
     * @param clz       类（返回值）
     * @return 返回集合数据
     */
    public <T> List<T> queryDataByRangeDESC(String name, String[] columns, Object[] data, String flagName,
                                            Long startFlag, Long endFlag, String order, Class<T> clz) {
        return queryDataByRange(name, columns, data, flagName, startFlag, endFlag, order, Sort.Direction.DESC, 1, 10, clz);
    }

    /**
     * 倒序查询一段范围的数据
     *
     * @param name      数据表名
     * @param columns   需要查的列名称
     * @param data      指定要查询的数据
     * @param flagName  限定范围的名称
     * @param startFlag 开始范围
     * @param endFlag   结束范围
     * @param order     排序
     * @param skip      下标起始
     * @param limit     每页限定值
     * @param clz       类（返回值）
     * @return 返回集合数据
     */
    public <T> List<T> queryDataByRangeDESC(String name, String[] columns, Object[] data, String flagName,
                                            Long startFlag, Long endFlag, String order, int skip, int limit, Class<T> clz) {
        return queryDataByRange(name, columns, data, flagName, startFlag, endFlag, order, Sort.Direction.DESC, skip, limit, clz);
    }

    /**
     * 正序查询一段范围的数据
     *
     * @param name      数据表名
     * @param columns   需要查的列名称
     * @param data      指定要查询的数据
     * @param flagName  限定范围的名称
     * @param startFlag 开始范围
     * @param endFlag   结束范围
     * @param order     排序
     * @param skip      下标起始
     * @param limit     每页限定值
     * @param clz       类（返回值）
     * @return 返回集合数据
     */
    public <T> List<T> queryDataByRangeASC(String name, String[] columns, Object[] data,
                                           String flagName, Long startFlag, Long endFlag,
                                           String order, int skip, int limit, Class<T> clz) {
        return queryDataByRange(name, columns, data, flagName, startFlag, endFlag, order, Sort.Direction.ASC, skip, limit, clz);
    }

    /**
     * 正序查询大于规定字段的列表
     *
     * @param name     数据表名
     * @param columns  需要查的列名称
     * @param data     指定要查询的数据
     * @param flagName 数据名称
     * @param flag     限定范围的名称
     * @param skip     下标起始
     * @param limit    每页限定值
     * @param <T>      类（返回值）
     * @return 返回集合数据
     */
    public <T> List<T> queryDataGteASC(String name, String[] columns, Object[] data,
                                       String flagName, Long flag, int skip, int limit, Class<T> clz) {
        return queryDataGte(name, columns, data, flagName, flag, Sort.Direction.ASC, skip, limit, clz);
    }

    /**
     * 倒序查询大于规定字段的列表
     *
     * @param name     数据表名
     * @param columns  需要查的列名称
     * @param data     指定要查询的数据
     * @param flagName 数据名称
     * @param flag     限定范围的名称
     * @param skip     下标起始
     * @param limit    每页限定值
     * @param <T>      类（返回值）
     * @return 列表
     */
    public <T> List<T> queryDataGteDESC(String name, String[] columns, Object[] data, String flagName,
                                        Long flag, int skip, int limit, Class<T> clz) {
        return queryDataGte(name, columns, data, flagName, flag, Sort.Direction.DESC, skip, limit, clz);
    }

    /**
     * 倒序查询小于规定字段的列表
     *
     * @param name     数据表名
     * @param columns  需要查的列名称
     * @param data     指定要查询的数据
     * @param flagName 数据名称
     * @param flag     限定范围的名称
     * @param skip     下标起始
     * @param limit    每页限定值
     * @param <T>      类（返回值）
     * @return 列表
     */
    public <T> List<T> queryDataLteDESC(String name, String[] columns, Object[] data, String flagName, Long flag,
                                        int skip, int limit, Class<T> clz) {
        return queryDataLte(name, columns, data, flagName, flag, Sort.Direction.DESC, skip, limit, clz);
    }


    /**
     * 正序查询小于规定字段的列表
     *
     * @param name     数据表名
     * @param columns  需要查的列名称
     * @param data     指定要查询的数据
     * @param flagName 数据名称
     * @param flag     限定范围的名称
     * @param skip     下标起始
     * @param limit    每页限定值
     * @param <T>      类（返回值）
     * @return 列表
     */
    public <T> List<T> queryDataLteASC(String name, String[] columns, Object[] data, String flagName, Long flag,
                                       int skip, int limit, Class<T> clz) {
        return queryDataLte(name, columns, data, flagName, flag, Sort.Direction.ASC, skip, limit, clz);
    }

    /**
     * 查询大于规定字段的列表
     *
     * @param name     数据表名
     * @param columns  需要查的列名称
     * @param data     指定要查询的数据
     * @param flagName 数据名称
     * @param flag     限定范围的名称
     * @param model    排序
     * @param skip     下标起始
     * @param limit    每页限定值
     * @param clz      类（返回值）
     * @param <T>      类（返回值）
     * @return 列表
     */
    private <T> List<T> queryDataGte(String name, String[] columns, Object[] data,
                                     String flagName, Long flag, Sort.Direction model, int skip, int limit, Class<T> clz) {
        if (columns == null || data == null) {
            log.info(QUERY_CRITERIA);
            return null;
        }
        try {
            Query query = new Query();
            if (flag == null) {
                for (int i = 0; i < columns.length; i++) {
                    query.addCriteria(Criteria.where(columns[i]).is(data[i]));
                }
            } else {
                for (int i = 0; i < columns.length; i++) {
                    query.addCriteria(Criteria.where
                            (columns[i]).is(data[i]));
                }
                if (flagName != null) {
                    query.addCriteria(Criteria.where
                            (flagName).gte(flag));
                }
            }
            query.with(new Sort(new Sort.Order(model, flagName)));
            return mongoTemplate.find(query.limit(limit).skip(skip), clz, name);
        } catch (Exception e) {
            log.info(NO_DATA);
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询小于规定字段的列表
     *
     * @param name     数据表名
     * @param columns  需要查的列名称
     * @param data     指定要查询的数据
     * @param flagName 数据名称
     * @param flag     限定范围的名称
     * @param model    排序
     * @param clz      类（返回值）
     * @param <T>      类（返回值）
     * @return 列表
     */
    private <T> List<T> queryDataLte(String name, String[] columns, Object[] data,
                                     String flagName, Long flag, Sort.Direction model, int skip, int limit, Class<T> clz) {
        if (columns == null || data == null) {
            log.info(QUERY_CRITERIA);
            return null;
        }
        try {
            Query query = new Query();
            if (flag == null) {
                for (int i = 0; i < columns.length; i++) {
                    query.addCriteria(Criteria.where(columns[i]).is(data[i]));
                }
            } else {
                for (int i = 0; i < columns.length; i++) {
                    query.addCriteria(Criteria.where
                            (columns[i]).is(data[i]));
                }
                if (flagName != null) {
                    query.addCriteria(Criteria.where
                            (flagName).lte(flag));
                }
            }
            query.with(new Sort(new Sort.Order(model, flagName)));
            return mongoTemplate.find(query.limit(limit).skip(skip), clz, name);
        } catch (Exception e) {
            log.info(NO_DATA);
            throw new RuntimeException(e);
        }
    }


    /***
     * 查询一段范围的数据
     *
     * @param name      数据表名
     * @param columns   需要查的列名称
     * @param data      指定要查询的数据
     * @param flagName  限定范围的名称
     * @param startFlag 开始范围
     * @param endFlag   结束范围
     * @param order     排序
     * @param clz       类（返回值）
     * @param <T>       类（返回值）
     * @return 返回集合数据
     */
    private <T> List<T> queryDataByRange(String name, String[] columns, Object[] data,
                                         String flagName, Long startFlag, Long endFlag,
                                         String order, Sort.Direction model,
                                         int skip, int limit, Class<T> clz) {
        if (columns == null || data == null) {
            log.info(QUERY_CRITERIA);
            return null;
        }
        try {
            Query query = new Query();
            if (startFlag == null || endFlag == null) {
                for (int i = 0; i < columns.length; i++) {
                    query.addCriteria(Criteria.where(columns[i]).is(data[i]));
                }
                if (flagName != null) {
                    query.addCriteria(Criteria.where(flagName).gte(startFlag).lte(endFlag));
                }
            } else {
                for (int i = 0; i < columns.length; i++) {
                    query.addCriteria(Criteria.where(columns[i]).is(data[i]));
                }
                if (flagName != null) {
                    query.addCriteria(Criteria.where(flagName).gte(startFlag).lte(endFlag));
                }
            }
            query.with(new Sort(new Sort.Order(model, order)));
            return mongoTemplate.find(query.limit(limit).skip(skip), clz, name);
        } catch (Exception e) {
            log.info(NO_DATA);
            throw new RuntimeException(e);
        }
    }

    /***
     * 查询一段范围的数据,不可使用内部类或者继承父类
     *
     * @param name     数据表名
     * @param data     需要查的数据
     * @param data     指定要查询的数据
     * @param order    排序
     * @param clz      类（返回值）
     * @param <T>      类（返回值）
     * @return 返回集合数据
     */
    public <T, V> List<T> queryDataRangeByObj(String name, V data,
                                              String order,
                                              Sort.Direction model, Class<T> clz) {
        if (data == null) {
            log.info(QUERY_CRITERIA);
            return null;
        }
        try {
            int skip = 0;
            int limit = 10;
            Long startFlag = 0L;
            Long endFlag = 0L;
            Query query = new Query();
            Field[] field = data.getClass().getDeclaredFields();
            for (int i = 0; i < field.length; i++) {
                try {
                    //设置是否允许访问，不是修改原来的访问权限修饰词。
                    field[i].setAccessible(true);
                    //Field表示的字段名和字段值
                    String fieldName = field[i].getName();
                    if (field[i].get(data) != null
                            && isNotContents(fieldName)) {
                        query.addCriteria(Criteria.where(fieldName).is(field[i].get(data)));
                    }
                    if (fieldName.contains("no")) {
                        skip = (int) field[i].get(data);
                    }
                    if (fieldName.contains("size")) {
                        limit = (int) field[i].get(data);
                    }
                    if (fieldName.contains("start")) {
                        startFlag = (Long) field[i].get(data);
                    }
                    if (fieldName.contains("end")) {
                        endFlag = (Long) field[i].get(data);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            query.with(new Sort(new Sort.Order(model, order)));
            return mongoTemplate.find(query.limit(limit).skip(skip), clz, name);
        } catch (Exception e) {
            log.info(NO_DATA);
            throw new RuntimeException(e);
        }
    }

    /**
     * 不包含字段
     *
     * @param name
     * @return true不包含
     */
    private boolean isNotContents(String name) {
        boolean end = name.contains("end");
        boolean start = name.contains("start");
        boolean no = name.contains("no");
        boolean size = name.contains("size");
        return !(end || start || no || size);
    }


}
