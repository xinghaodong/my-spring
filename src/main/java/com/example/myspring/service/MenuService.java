package com.example.myspring.service;

import com.example.myspring.entity.Menu;

import java.util.List;

public interface MenuService {
//    全部菜单返回tree
    List<Menu> getAll();
//    全部菜单返回list
    List<Menu> getAllList();

    Void deleteMenu(Integer id);

    Menu getById(Integer id);

//    新增菜单
    Menu addMenu(Menu menu);

//    修改菜单
    Menu updateMenu(Menu menu);
}
