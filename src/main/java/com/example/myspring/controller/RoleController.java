package com.example.myspring.controller;


import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.Role;
import com.example.myspring.mapper.MenuMapper;
import com.example.myspring.service.MenuService;
import com.example.myspring.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuMapper menuMapper;


    /**
     * 查询所有角色
     * @return 角色列表
     */
    @GetMapping("/findAll")
    public ResponseDto<List<Role>> getAll() {
        return ResponseDto.success(roleService.getAll());
    }

    /**
     * 新增角色
     * @param role role
     * @return 角色
     */

    @PostMapping("/create")
    public ResponseDto<Role> addRole(@RequestBody Role role) {
        return ResponseDto.success(roleService.addRole(role));
    }

    /**
     * 根据用户id查询角色
     * @param id id
     * @return 角色
     */
    @GetMapping("/detail")
    public ResponseDto<Role> getRoleByUserId(Integer id) {
        return ResponseDto.success(roleService.getRoleByUserId(id));
    }

    /**
     * 修改角色
     * @param role role
     * @return 角色
     */
    @PostMapping("/update")
    public ResponseDto<Role> updateRole(@RequestBody Role role) {
        return ResponseDto.success(roleService.updateRole(role));
    }

    /***
     * 获取角色菜单
     * @param id id
     * @return 角色的菜单列表
     */
    @GetMapping("/getRoleMenus")
    public ResponseDto<List<Integer>> getRoleMenus(@RequestParam Integer id) {
//        System.out.println(menus);
        List<Integer> menuIds = roleService.getRoleMenus(id);
        return ResponseDto.success(menuIds);
    }

    /**
     * 修改角色菜单资源
     * post 请求
     * @param obj obj
     * @return
     * 待完善
     */
    @PostMapping("/assignMenusToRole")
    public ResponseDto<Role> updateRoleMenus(@RequestBody Map<String, Object> obj) {
        System.out.println(obj);
        return ResponseDto.success(roleService.updateRoleMenus(obj));
    }
}
