package com.uuabb.miao.dao;

import com.uuabb.miao.entity.User;

/**
 * Created by brander on 2018/3/1
 */
public interface UserDao {
    /**
     * 插入区域信息
     *
     * @param user
     * @return
     */
    int insertUser(User user);


    /**
     * @return
     */
    int getCount();

}
