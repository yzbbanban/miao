package com.uuabb.miao.config.controller;

import com.uuabb.miao.config.service.IAreaService;
import com.uuabb.miao.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brander on 2018/3/1
 */
@RestController
@RequestMapping("/superAdmin")
public class AreaController {
    @Autowired
    private IAreaService iAreaService;


    @RequestMapping("/testRedis")
    public String testRedis(){
        return "hello world";
    }

    @RequestMapping(value = "getAreaList", method = RequestMethod.GET)
    public Map<String, Object> getAreaList() {
        List<Area> areas = iAreaService.getAreaList();
        Map<String, Object> map = new HashMap<>();
        map.put("areaList", areas);
        return map;
    }

    @RequestMapping(value = "getAreaById", method = RequestMethod.GET)
    public Map<String, Object> getAreaById(Integer areaId) {
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
    public Map<String, Object> deleteAreaById(Integer areaId) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", iAreaService.deleteAreaById(areaId));
        return map;
    }
}
