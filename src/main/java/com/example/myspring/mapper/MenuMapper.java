package com.example.myspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myspring.entity.Menu;
import com.example.myspring.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {


    // 从中间表 role_menus_menu 根据传来的 菜单id数据库字段是 menuId
    @Select("SELECT * FROM role WHERE id IN (SELECT roleId FROM role_menus_menu WHERE menuId = #{menuId})")
    List<Role> queryRoleIdsByMenuId(@Param("menuId") Integer menuId);

//    从中间表 role_menus_menu 根据传来的 菜单menuId 删除关联的信息
    @Delete("DELETE FROM role_menus_menu WHERE menuId = #{menuId}")
    void deleteRolesByMenuId(@Param("menuId") int menuId);

//     从中间表 role_menus_menu 根据传来的 角色id 删除关联的信息
    @Delete("DELETE FROM role_menus_menu WHERE roleId = #{roleId}")
    void deleteMenusByRoleId(@Param("roleId") int roleId);

    @Insert("INSERT INTO role_menus_menu (menuId, roleId) VALUES (#{menuId}, #{roleId})")
    void addMenusRole(@Param("menuId") int menuId, @Param("roleId") int roleId);


    //    根据用户角色查询菜单

    // 根据角色ID查询关联的菜单
    @Select("SELECT * FROM menu WHERE id IN (SELECT menuId FROM role_menus_menu WHERE roleId = #{roleId})")
    List<Menu> findMenusByRoleId(@Param("roleId") Integer roleId);

    @Select("SELECT * FROM menu WHERE code = #{code}")
    Menu selectByCode(String code);
}
