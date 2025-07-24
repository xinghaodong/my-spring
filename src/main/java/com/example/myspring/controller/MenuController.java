package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.Menu;
import com.example.myspring.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping()
    public ResponseDto<List<Menu>> getAll() {
        return ResponseDto.success(menuService.getAll());
    }

    //    删除菜单
    @RequestMapping("/deletemenu")
    public ResponseDto<Void> deleteMenu(@RequestBody Map<String, Integer> obj) {
        Integer id = obj.get("id");
        menuService.deleteMenu(id);
        return ResponseDto.success("删除成功", null);
    }
}
