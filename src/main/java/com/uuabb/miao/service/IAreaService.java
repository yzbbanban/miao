package com.uuabb.miao.service;

import com.uuabb.miao.entity.Area;

import java.util.List;

/**
 * Created by brander on 2018/3/1
 */
public interface IAreaService {
    List<Area> getAreaList();

    Area getAreaById(int areaId);

    boolean addAreaById(Area area);

    boolean modifyArea(Area area);

    boolean deleteAreaById(int areaId);


    boolean update(int areaId);

}
