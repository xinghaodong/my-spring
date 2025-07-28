package com.example.myspring.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myspring.entity.Cesium;
import com.example.myspring.entity.Waypoint;
import com.example.myspring.mapper.CesiumMapper;
import com.example.myspring.mapper.WaypointMapper;
import com.example.myspring.service.CesiumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CesiumServiceImpl implements CesiumService {

    private final CesiumMapper cesiumMapper;

    private final WaypointMapper waypointMapper;

    public CesiumServiceImpl(CesiumMapper cesiumMapper, WaypointMapper waypointMapper) {
        this.cesiumMapper = cesiumMapper;
        this.waypointMapper = waypointMapper;
    }


    @Override
    public List<Cesium> getAll() {
        return cesiumMapper.selectList(null);
    }

    /**
     *
     * 根据 id 查询航线信息
     * @param id id
     * @return Cesium
     */
    @Override
    @Transactional
    public Cesium getById(Integer id) {
//       先查询 Cesium 数据
        Cesium cesium = cesiumMapper.selectById(id);
        if (cesium == null) {
            return null;
        }
//      再查询 Waypoint 子表数据
        System.out.println("查询到的数据：" + cesium);
        List<Waypoint> waypoints = waypointMapper.selectByCesiumId(id);
        cesium.setTempWaypoints(waypoints);
        return cesium;
    }

    /**
     *
     * 分页查询 航线列表
     * @param page page
     * @param pageSize pageSize
     * @return Map
     */
    @Override
    public Map<String, Object> getCesiumPage(int page, int pageSize) {
        Page<Cesium> cesiumPage = new Page<>(page, pageSize);
        cesiumMapper.selectPage(cesiumPage, null);
        Map<String,Object> result = new HashMap<>();
        result.put("list", cesiumPage.getRecords());
        result.put("total", cesiumPage.getTotal());
        return result;
    }


    /**
     * 更新航线
     * @param cesium cesium
     * @return cesium
     */
    @Override
    @Transactional
    public Cesium updateCesium(Cesium cesium) {
//        Integer cesiumId = cesium.getId();
        // 先查是否存在
        Cesium existing = cesiumMapper.selectById(cesium.getId());
        if (existing == null) {
            return null;
        }
        // 手动设置更新时间
        cesium.setUpdatedAt(LocalDateTime.now());
        // 更新父表数据
        cesiumMapper.updateById(cesium);
        // 处理子表数据
        if (cesium.getTempWaypoints() != null && !cesium.getTempWaypoints().isEmpty()) {
            // 删除该航线原有的所有航点
            waypointMapper.deleteByCesiumId(cesium.getId());
            // 插入新的航点数据
            List<Waypoint> waypoints = new ArrayList<>(cesium.getTempWaypoints());
            for (Waypoint waypoint : waypoints) {
                waypoint.setRouteId(cesium.getId());
                waypointMapper.insert(waypoint);
            }
        }
        return cesium;
    }


    /**
     * 删除航线
     * @param id id
     */
    @Override
    @Transactional
    public void deleteCesium(Integer id) {
        Cesium existing = cesiumMapper.selectById(id);
        if (existing != null) {
            // 删除航点数据
            waypointMapper.deleteByCesiumId(id);
            // 删除主数据
            cesiumMapper.deleteById(id);
        }
    }

    /**
     * 创建航线
     * @param cesium cesium
     * @return cesium
     */
    @Override
    @Transactional
    public Cesium createCesium(Cesium cesium) {
        System.out.println("createCesium: " + cesium);
        // 手动设置创建时间
        cesium.setCreatedAt(LocalDateTime.now());
        cesiumMapper.insert(cesium);
        // 再把子表的数据插入
        if (cesium.getTempWaypoints() != null && !cesium.getTempWaypoints().isEmpty()) {
            List<Waypoint> waypoints = new ArrayList<>(cesium.getTempWaypoints());
            for (Waypoint waypoint : waypoints) {
                waypoint.setRouteId(cesium.getId());
                waypointMapper.insert(waypoint);
            }
        }
        return cesium;
    }
}