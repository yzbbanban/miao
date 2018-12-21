package com.uuabb.miao.dao;

import com.uuabb.miao.entity.Area;

import java.util.List;

/**
 * Created by brander on 2018/3/1
 */
public interface AreaDao {
    
    //列出区域列表
    List<Area> queryArea();

    //根据Id列出具体区域
    Area queryAreaById(int areaId);

    //插入区域信息
    int insertArea(Area area);

    //更新区域信息
    int updateArea(Area area);

    //删除区域信息
    int deleteArea(int areaId);

    int getCount();
}
