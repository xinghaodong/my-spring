package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.Cesium;
import com.example.myspring.entity.Menu;
import com.example.myspring.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping()
    public ResponseDto<List<Menu>> getAll() {
        return ResponseDto.success(menuService.getAll());
    }
}
