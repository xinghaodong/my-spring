package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.Menu;
import com.example.myspring.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 详情
     * @param id
     */
    @RequestMapping("/detail")
    public ResponseDto<Menu> getById(@RequestParam Integer id) {
        Menu menu = menuService.getById(id);
        return ResponseDto.success(menu);
    }

    /**
     * 新增
     * @param menu
     * post
     */

    @PostMapping("/addmenu")
    public ResponseDto<Menu> addMenu(@RequestBody Menu menu) {
        System.out.println(menu);

        Menu menu1 = menuService.addMenu(menu);
        return ResponseDto.success("添加成功", menu1);
    }

    /**
     * 修改
     * @param menu
     * post
     */

    @PostMapping("/update")
    public ResponseDto<Menu> updateMenu(@RequestBody Menu menu) {
        Menu menu1 = menuService.updateMenu(menu);
        return ResponseDto.success("修改成功", menu1);
    }

}
