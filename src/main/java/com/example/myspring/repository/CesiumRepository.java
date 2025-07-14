package com.example.myspring.repository;

import com.example.myspring.dto.CesiumDTO;
import com.example.myspring.dto.WaypointDTO;
import com.example.myspring.entity.Cesium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CesiumRepository extends JpaRepository<Cesium, Integer> {
    /**
     * 查询所有 不关联子表
     * @return
     */
    @Query("SELECT new com.example.myspring.dto.CesiumDTO(" +
            "c.id, c.name, c.pointNum, c.time, c.status, c.speed, c.trackmileage, c.createdAt, c.updatedAt" +
            ") FROM Cesium c")
    List<CesiumDTO> findAllProjectedBy();

    /**
     * 查询一个 关联子表
     * @param id
     */
    @Query("SELECT new com.example.myspring.dto.CesiumDTO(" +
            "c.id, c.name, c.pointNum, c.time, c.status, c.speed, c.trackmileage, c.createdAt, c.updatedAt" +
            ") FROM Cesium c WHERE c.id = :id")
    Optional<CesiumDTO> findByIdProjectedBy(@Param("id") Integer id);

}