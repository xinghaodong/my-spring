package com.example.myspring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myspring.entity.Menu;
import com.example.myspring.entity.Role;
import com.example.myspring.mapper.MenuMapper;
import com.example.myspring.mapper.RoleMapper;
import com.example.myspring.service.MenuService;
import com.example.myspring.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleSerivceImpl implements RoleService {

    private final RoleMapper roleMapper;

    private final MenuService menuService;

    private final MenuMapper menuMapper;

    public RoleSerivceImpl(RoleMapper roleMapper, MenuService menuService, MenuMapper menuMapper) {
        this.roleMapper = roleMapper;
        this.menuService = menuService;
        this.menuMapper = menuMapper;
    }


    @Override
    public List<Role> getAll() {
        return roleMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Role getRoleByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }
        return roleMapper.selectById(userId);
    }

    /**
     * 更新角色
     * @param role role
     * @return role
     */
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
     * @param id id
     * @return 角色菜单列表
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
     * 更新角色菜单 对应前端 角色管理 - 分配资源按钮
     * @param obj obj
     * @return 角色实体对象
     */
    @Override
    public Role updateRoleMenus(Map<String, Object> obj) {
//        前端传来的有两个参数，一个是角色id，一个是菜单menuIds集合
        Integer roleId = (Integer) obj.get("id");
        Role role = roleMapper.selectById(roleId);
        @SuppressWarnings("unchecked")
        List<Integer> menuIds = (List<Integer>) obj.getOrDefault("menuIds", Collections.emptyList());
        System.out.println(roleId);
        System.out.println(menuIds);

//        先删除之前关联的菜单
        menuMapper.deleteMenusByRoleId(roleId);
//        设置新的关联
        for (Integer menuId : menuIds){
            menuMapper.addMenusRole(menuId, roleId);
        }
        return role;
    }

    /**
     * 添加角色
     * @param role role
     */
    @Override
    public Role addRole(Role role) {
//        查询角色名称时候重复 可以使用mybatis-plus提供的方法 QueryWrapper 查询 当然也可以自己在mapper 写sql查询啦
        Role existing = roleMapper.selectOne(new QueryWrapper<Role>().eq("name", role.getName()));
        if (existing != null) {
            throw new IllegalArgumentException("角色名称重复");
        }
        role.setCreatedAt(LocalDateTime.now());
        roleMapper.insert(role);
        return role;
    }
}
