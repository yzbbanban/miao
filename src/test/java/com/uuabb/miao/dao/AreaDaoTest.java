package com.uuabb.miao.dao;

import com.uuabb.miao.entity.Area;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by brander on 2018/3/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaDaoTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void queryArea() throws Exception {
        List<Area> areas=areaDao.queryArea();
        System.out.println(areas);
    }

    @Test
    public void queryAreaById() throws Exception {
        Area area=areaDao.queryAreaById(1);
        System.out.println(area);
    }

    @Test
    public void insertArea() throws Exception {
        Area area=new Area();
        area.setAreaName("南京");
        area.setPriority(3);
        int id=areaDao.insertArea(area);
        System.out.println(id);
    }

    @Test
    public void updateArea() throws Exception {
        Area area=new Area();
        area.setAreaId(1);
        area.setAreaName("吸睛");
        area.setPriority(3);
        areaDao.updateArea(area);
    }

    @Test
    public void deleteArea() throws Exception {
        areaDao.deleteArea(1);
    }

}