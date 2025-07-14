    package com.example.myspring.service;

    import com.example.myspring.dto.CesiumDTO;
    import com.example.myspring.dto.WaypointDTO;
    import com.example.myspring.entity.Cesium;

    import java.util.List;
    import java.util.Optional;

    public interface CesiumService {
        List<CesiumDTO> getAll(); // 改成返回 DTO
        Optional<Cesium> getById(Integer id);
        Cesium save(Cesium cesium);
        void deleteById(Integer id);
//        查询一个
        Optional<CesiumDTO> getByIdProjectedBy(Integer id);
    }