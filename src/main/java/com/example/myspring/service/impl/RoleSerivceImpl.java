package com.example.myspring.service.impl;

import com.example.myspring.entity.Menu;
import com.example.myspring.entity.Role;
import com.example.myspring.mapper.MenuMapper;
import com.example.myspring.mapper.RoleMapper;
import com.example.myspring.service.MenuService;
import com.example.myspring.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleSerivceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public List<Role> getAll() {
        return roleMapper.selectList(null);
    }

    @Override
    public Role getRoleByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }
        return roleMapper.selectById(userId);
    }

    @Override
    @Transactional
    public Role updateRole(Role role) {
        // 先根据传入的id查询出数据库中对应的数据
        Role existing = roleMapper.selectById(role.getId());
        if (existing == null) {
            return null;
        }
        role.setUpdatedAt(LocalDateTime.now());
        roleMapper.updateById(role);
        return roleMapper.selectById(role.getId());
    }

    /**
     * 获取角色菜单
     * @param id
     * @return
     */
    @Override
    public List<Integer> getRoleMenus(Integer id) {
//      Map<String,Object> menus = (Map<String, Object>) menuService.getAllList();
//        判断如果是超管 id是1
        List<Menu> menus;
        if (id == 1){
           menus = menuService.getAllList();
        }else {
//            根据角色id 查询关联的菜单
           menus = menuMapper.findMenusByRoleId(id);
        }
//        转成ArrayList
        return menus.stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
    }

    /**
     * @param obj
     * @return
     */
    @Override
    public Role updateRoleMenus(Map<String, Object> obj) {
//        前端传来的有两个参数，一个是角色id，一个是菜单menuIds集合
        Integer roleId = (Integer) obj.get("id");
        List<Integer> menuIds = (List<Integer>) obj.get("menuIds");
        System.out.println(roleId);
        System.out.println(menuIds);

//        先删除之前关联的菜单
      menuMapper.deleteMenusByRoleId(roleId);
//        设置新的关联
        for (Integer menuId : menuIds){
            menuMapper.addMenusRole(menuId, roleId);
        }
        return null;
    }
}
