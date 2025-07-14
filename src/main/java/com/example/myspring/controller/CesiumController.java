package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.dto.CesiumDTO;
import com.example.myspring.entity.Cesium;
import com.example.myspring.service.CesiumService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cesium")
public class CesiumController {
    private final CesiumService cesiumService;
    public CesiumController(CesiumService cesiumService) {
        this.cesiumService = cesiumService;
    }
    /**
     * 查询全部
     * @return
     */
    @GetMapping
    public ResponseDto<List<CesiumDTO>> getAll() {
        return ResponseDto.success(cesiumService.getAll());
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public ResponseDto<Optional<CesiumDTO>> getByIdProjectedBy(@RequestParam Integer id) {
        return ResponseDto.success(cesiumService.getByIdProjectedBy(id));
    }
    /**
     * 新增航线
     * @param cesium
     * @return
     */
    @PostMapping
    public Cesium addCesium(@RequestBody Cesium cesium) {
        System.out.println("收到请求数据：" + cesium);
        return cesiumService.save(cesium);
    }
}