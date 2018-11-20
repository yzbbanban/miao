package com.uuabb.miao.controller;

import com.uuabb.miao.service.IAreaService;
import com.uuabb.miao.entity.Area;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by brander on 2018/3/1
 */
@RestController
@RequestMapping("/superAdmin")
public class AreaController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IAreaService iAreaService;

    @RequestMapping(value = "getAreaList", method = RequestMethod.GET)
    public Map<String, Object> getAreaList() {
        List<Area> areas = iAreaService.getAreaList();
        Map<String, Object> map = new HashMap<>();
        map.put("areaList", areas);
        return map;
    }

    @RequestMapping(value = "getAreaById", method = RequestMethod.GET)
    public Map<String, Object> getAreaById(int areaId) {
        int a = 1 / 0;
        Area area = iAreaService.getAreaById(areaId);
        Map<String, Object> map = new HashMap<>();
        map.put("area", area);
        return map;
    }

    @RequestMapping(value = "addAreaById", method = RequestMethod.POST)
    public Map<String, Object> addAreaById(@RequestBody Area area) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", iAreaService.addAreaById(area));
        return map;
    }

    @RequestMapping(value = "modifyArea", method = RequestMethod.POST)
    public Map<String, Object> modifyArea(@RequestBody Area area) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", iAreaService.modifyArea(area));
        return map;
    }

    @RequestMapping(value = "deleteAreaById", method = RequestMethod.POST)
    public Map<String, Object> deleteAreaById(int areaId) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", iAreaService.deleteAreaById(areaId));
        return map;
    }


    @GetMapping("/order")
    public Callable<String> order() throws Exception {

        logger.info("主线程开始");

        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("子线程开始");
                Thread.sleep(1000);
                logger.info("子线程返回");
                return "success";
            }
        };
        logger.info("主线程返回");
        return result;
    }


}
