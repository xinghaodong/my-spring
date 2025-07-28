package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.Cesium;
import com.example.myspring.service.CesiumService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cesium")
public class CesiumController {
    private final CesiumService cesiumService;

    public CesiumController(CesiumService cesiumService) {
        this.cesiumService = cesiumService;
    }

    /**
     * 查询全部
     * @param page page
     * @return 全部
     */
    @GetMapping("/list")
    public ResponseDto<Map<String, Object>> getAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize ) {
        System.out.println(page);
        System.out.println(pageSize);
        return ResponseDto.success(cesiumService.getCesiumPage(page, pageSize));
    }

    /**
     * 根据id查询 详情
     * @param id id
     * @return 详情
     */
    @GetMapping("/detail")
    public ResponseDto<Cesium> getById(@RequestParam Integer id) {
        return ResponseDto.success(cesiumService.getById(id));
    }


    /***
     * 修改航线
     * @param cesium cesium
     * @return 修改结果
     */
    @PostMapping("/update")
    public ResponseDto<Cesium> updateCesium(@RequestBody Cesium cesium) {
        return ResponseDto.success(cesiumService.updateCesium(cesium));
    }
    /***
     * 删除航线
     * @param obj obj
     * @return 删除结果
     */
    @PostMapping("/delete")
    public ResponseDto<Void> deleteCesium(@RequestBody Map<String,Integer> obj) {
        System.out.println(obj);
        Integer id = obj.get("id");
        cesiumService.deleteCesium(id);
        return ResponseDto.success("删除成功",null);
    }

    /**
     * 新增航线
     * @param cesium cesium
     * @return 新增结果
     */

    @PostMapping("/create")
    public ResponseDto<Cesium> createCesium(@RequestBody Cesium cesium) {
        return ResponseDto.success(cesiumService.createCesium(cesium));
    }


}