package com.uuabb.miao.service.impl;

import com.uuabb.miao.dao.UserDao;
import com.uuabb.miao.entity.User;
import com.uuabb.miao.service.IAreaService;
import com.uuabb.miao.dao.AreaDao;
import com.uuabb.miao.entity.Area;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by brander on 2018/3/1
 */
@Service
public class AreaServiceImpl implements IAreaService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private UserDao userDao;

    @Override
    @CacheEvict(value = "area", key = "getAreaList()", allEntries = true)
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }

    @Override
    @CacheEvict(value = "area", key = "getAreaById()", allEntries = true)
    public Area getAreaById(int areaId) {
        return areaDao.queryAreaById(areaId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addAreaById(Area area) {
        if (!StringUtils.isEmpty(area.getAreaName())) {
            try {
                int effectNum = areaDao.insertArea(area);
                if (effectNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("插入区域信息失败!");
                }
            } catch (Exception e) {
                throw new RuntimeException("插入区域信息失败!" + e.getMessage());
            }
        } else {
            throw new RuntimeException("区域信息不能为空");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean modifyArea(Area area) {
        if (area.getAreaId() != null && area.getAreaId() > 0) {
            try {
                int effectNum = areaDao.updateArea(area);
                if (effectNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("更新区域信息失败!");
                }
            } catch (Exception e) {
                throw new RuntimeException("更新区域信息失败!" + e.getMessage());
            }

        } else {
            throw new RuntimeException("区域信息不能为空");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteAreaById(int areaId) {
        if (areaId > 0) {
            try {
                int effectNum = areaDao.deleteArea(areaId);
                if (effectNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("删除区域信息失败!");
                }
            } catch (Exception e) {
                throw new RuntimeException("删除区域信息失败!" + e.getMessage());
            }

        } else {
            throw new RuntimeException("区域id不能为空");
        }
    }

    /**
     * 查询的并发测试
     *
     * @param areaId 区域id
     * @return true 成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.REPEATABLE_READ)
    public boolean updateSelect(int areaId, String name) {
        try {
            String message = "------> " + name + ": ";
            int time = new Random().nextInt(5) * 100;
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if ("test10".equals(name)) {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Area area = areaDao.queryAreaById(areaId);

            //1	admin	e10adc3949ba59abbe56e057f20f883e	690844664@qq.com	admin	view
            User user = new User();
            user.setDeviceId("ios");
            user.setPermission("" + System.currentTimeMillis());
            user.setRole("admin");
            user.setUserEmail("ban@ban.com");
            user.setUserName(name);
            user.setUserPassword("ban");
            userDao.insertUser(user);

            int c = userDao.getCount();
            logger.info(message + c);

            if (area.getPriority().intValue() == c) {
                area.setAreaName(name);
                int res = areaDao.updateSelect(area);
                logger.info(message + "*****************************************");
                if (res == 0) {
                    throw new RuntimeException("xxxxxx");
                }
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

}
