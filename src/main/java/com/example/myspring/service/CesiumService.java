    package com.example.myspring.service;

    import com.example.myspring.entity.Cesium;

    import java.util.List;

    public interface CesiumService {
        List<Cesium> getAll(); // 改成返回 DTO

        Cesium getById(Integer id);
    }