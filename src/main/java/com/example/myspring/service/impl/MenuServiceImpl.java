package com.example.myspring.service.impl;


import com.example.myspring.entity.Menu;
import com.example.myspring.entity.Role;
import com.example.myspring.mapper.MenuMapper;
import com.example.myspring.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate; // 注入 JdbcTemplate

    @Override
    public List<Menu> getAll() {
      List<Menu> menus = menuMapper.selectList(null);
//        实现一个树形方法 返回
        return buildTree(menus);
    }

    /**
     * 查询全部菜单啊
     * @return list菜单
     */
    @Override
    public List<Menu> getAllList() {
        return menuMapper.selectList(null);
    }

    @Override
    @Transactional
    public Void deleteMenu(Integer id) {
//        巡查是否存在
        Menu menu = menuMapper.selectById(id);
        if (menu == null) {
            return null;
        }
        // 先删除中间表的关联数据
        // 方式1：使用 JdbcTemplate
        jdbcTemplate.update("DELETE FROM role_menus_menu WHERE menuId = ?", id);

        // 方式2: 在 Mapper 处理中间表 RoleMenuMapper
        //        @Delete("DELETE FROM role_menus_menu WHERE menuId = #{menuId}")
        //        int deleteByMenuId(@Param("menuId") Integer menuId);
        //        注入 roleMenuMapper 直接调用  roleMenuMapper.deleteByMenuId(id);
        menuMapper.deleteById(id);
        return null;
    }


    /**
     * 详情
     * @param id
     * @return
     */
    @Override
    public Menu getById(Integer id) {
//       判断 id 是否能找到
        if (menuMapper.selectById(id) == null) {
            return null;
        }
        Menu menu = menuMapper.selectById(id);
//        关联查询中间表 menu_roles_role 资源对应的角色
        List<Role> roleList = menuMapper.queryRoleIdsByMenuId(id);
        System.out.println( "查询到的数据：" + roleList );
        // 提取角色ID列表
        List<Integer> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleIds.add(role.getId());
        }
        menu.setRoleIds(roleIds);

        System.out.println( "查询到的数据：" + menu );
        return menu;
    }

    /**
     * 新增
     * @param menu
     * @return
     */
    @Override
    public Menu addMenu(Menu menu) {
        menuMapper.insert(menu);
        return menu;
    }

    /**
     * 修改
     * @param menu
     * @return
     */
    @Override
    public Menu updateMenu(Menu menu) {
//        首选gen
        int id = menu.getId();
        System.out.println(id);
        if( id == 0){
            return null;
        }
        Menu menu1 = menuMapper.selectById(id);
//        判断 menu1 存在
        if(menu1 == null){
            return null;
        }
        menuMapper.updateById(menu);

//        删除关联的角色
        menuMapper.deleteRolesByMenuId(id);

            System.out.println("删除成功");
//            添加关联的角色
//        定义一个数字数组
        List<Integer> roleIds = menu.getRoleIds();
        for (Integer role : menu.getRoleIds()) {
            System.out.println("添加的菜单ID：" + id);
            System.out.println("添加的角色ID：" + role);
            menuMapper.addMenusRole(id, role);
        }
        return menu;
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
