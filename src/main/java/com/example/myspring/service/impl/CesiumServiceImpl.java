package com.example.myspring.service.impl;


import com.example.myspring.entity.Cesium;
import com.example.myspring.entity.Waypoint;
import com.example.myspring.mapper.CesiumMapper;
import com.example.myspring.mapper.WaypointMapper;
import com.example.myspring.service.CesiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class CesiumServiceImpl implements CesiumService {
    @Autowired
    private CesiumMapper cesiumMapper;
    @Autowired
    private WaypointMapper waypointMapper;

    @Override
    public List<Cesium> getAll() {
        return cesiumMapper.selectList(null);
    }

    @Override
    public Cesium getById(Integer id) {
//       先查询 Cesium 数据
        Cesium cesium = cesiumMapper.selectById(id);
        if (cesium == null) {
            return null;
        }
//      再查询 Waypoint 子表数据
        System.out.println("查询到的数据：" + cesium);
        List<Waypoint> waypoints = waypointMapper.selectByCesiumId(id);
        cesium.setTempWaypoints(new HashSet<>(waypoints));
        return cesium;
    }


//    @Override
//    public List<CesiumDTO> getAll() {
//        return CesiumMapper.findAllProjectedBy(); // 改为 DTO 查询
//    }


}