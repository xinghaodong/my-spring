package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.Menu;
import com.example.myspring.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

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
     * @param id id
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

   /*
    * 根据父级id查询菜单
    */
    @RequestMapping("/getMenusByPid")
    public ResponseDto<List<Menu>> getMenusByPid(@RequestParam(required = false) Integer pid) {
        List<Menu> menuList = menuService.getMenusByPid(pid);
        return ResponseDto.success(menuList);
    }

//    根据前端传入的id集合保存菜单主要实现重新排序的功能
    @PostMapping("/saveMenuSort")
    public ResponseDto<Void> saveMenuSort(@RequestBody List<Integer> ids) {
        System.out.println(ids);
        menuService.saveMenuSort(ids);
        return ResponseDto.success("保存成功", null);
    }

}
