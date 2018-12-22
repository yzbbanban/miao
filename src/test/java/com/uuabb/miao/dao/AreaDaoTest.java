package com.uuabb.miao.dao;

import com.uuabb.miao.entity.Area;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
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
        List<Area> areas = areaDao.queryArea();
        System.out.println(areas);
    }

    @Test
    public void queryAreaById() throws Exception {
        Area area = areaDao.queryAreaById(1);
        System.out.println(area);
    }

    @Test
    public void insertArea() throws Exception {
        Area area = new Area();
        area.setAreaName("南京");
        area.setPriority(3);
        int id = areaDao.insertArea(area);
        System.out.println(id);
    }

    @Test
    public void updateArea() throws Exception {
        Area area = new Area();
        area.setAreaId(1);
        area.setAreaName("吸睛");
        area.setPriority(3);
        areaDao.updateArea(area);
    }

    @Test
    public void deleteArea() throws Exception {
        areaDao.deleteArea(1);
    }

    @Test
    public void testSQl() {
        new Thread(() -> testRead()).start();
        new Thread(() -> testInsert()).start();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class}, isolation = Isolation.READ_COMMITTED)
    public void testRead() {

        int receiveCount = areaDao.getCount();
        System.out.println("---testRead 1-->" + receiveCount);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        receiveCount = areaDao.getCount();
        System.out.println("---testRead 2-->" + receiveCount);

    }

    public void testInsert() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Area area = new Area();
        area.setAreaName("徐州");
        area.setPriority(3);
        int id = areaDao.insertArea(area);
        System.out.println("---testInsert-->" + id);
    }


    @Test
    public void testModify() {
        new Thread(() -> updateTest1()).start();
        new Thread(() -> updateTest2()).start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateTest1() {

        Area area = areaDao.queryAreaById(10);
        System.out.println("---1-->" + area);
        area.setCreateTime(new Date());
        area.setAreaName("北京");
        areaDao.updateArea(area);
    }

    public void updateTest2() {
        Area area = areaDao.queryAreaById(10);
        System.out.println("---2-->" + area);
        area.setCreateTime(new Date());
        area.setAreaName("上海2");
        areaDao.updateArea(area);
    }

}