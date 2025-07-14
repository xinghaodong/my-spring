package com.example.myspring.service.impl;



import com.example.myspring.dto.CesiumDTO;
import com.example.myspring.dto.WaypointDTO;
import com.example.myspring.entity.Cesium;
import com.example.myspring.repository.CesiumRepository;
import com.example.myspring.service.CesiumService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CesiumServiceImpl implements CesiumService {

    private final CesiumRepository cesiumRepository;

    public CesiumServiceImpl(CesiumRepository cesiumRepository) {
        this.cesiumRepository = cesiumRepository;
    }



    @Override
    public List<CesiumDTO> getAll() {
        return cesiumRepository.findAllProjectedBy(); // 改为 DTO 查询
    }
    @Override
    public Optional<Cesium> getById(Integer id) {
        return cesiumRepository.findById(id);
    }


    @Override
    public Cesium save(Cesium cesium) {
        return cesiumRepository.save(cesium);
    }

    @Override
    public void deleteById(Integer id) {

    }
//    查询详情
    @Override
    public Optional<CesiumDTO> getByIdProjectedBy(Integer id) {
        return cesiumRepository.findByIdProjectedBy(id);
    }
}