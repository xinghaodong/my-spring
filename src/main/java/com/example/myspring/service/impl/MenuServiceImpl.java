package com.example.myspring.service.impl;


import com.example.myspring.entity.Cesium;
import com.example.myspring.entity.Menu;
import com.example.myspring.mapper.MenuMapper;
import com.example.myspring.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
      List<Menu> menus = menuMapper.selectList(null);
//        实现一个树形方法 返回
        return buildTree(menus);
    }

    private List<Menu> buildTree(List<Menu> menus) {
        // 1. 创建一个 Map，用于快速通过 id 查找菜单
        Map<Integer, Menu> menuMap = new HashMap<>();
        System.out.println(menus);
        menus.forEach(menu -> menuMap.put(menu.getId(), menu));
        // 2. 构建最终的树形结构
        List<Menu> tree = new ArrayList<>();
        menus.forEach(menu -> {
            Integer parentId = menu.getParentId();

            if (parentId == null || parentId == 0) {
                // 如果 parentId 为空或 0，说明是根节点
                tree.add(menu);
            } else {
                // 否则查找父节点并加入其 children 列表
                Menu parent = menuMap.get(parentId);
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(menu);
                }
            }
        });
        return tree;
    }
}
