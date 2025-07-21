package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.Cesium;
import com.example.myspring.service.CesiumService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cesium")
public class CesiumController {
    private final CesiumService cesiumService;

    public CesiumController(CesiumService cesiumService) {
        this.cesiumService = cesiumService;
    }

    /**
     * 查询全部
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseDto<List<Cesium>> getAll() {  // 明确指定泛型类型
        return ResponseDto.success(cesiumService.getAll());
    }

    /**
     * 根据id查询 详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public ResponseDto<Cesium> getById(@RequestParam Integer id) {
        return ResponseDto.success(cesiumService.getById(id));
    }

    

}