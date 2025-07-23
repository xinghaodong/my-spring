package com.example.myspring.controller;


import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.Role;
import com.example.myspring.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    /**
     * 查询所有角色
     * @return
     */
    @GetMapping("/findAll")
    public ResponseDto<List<Role>> getAll() {
        return ResponseDto.success(roleService.getAll());
    }

    /**
     * 根据用户id查询角色
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public ResponseDto<Role> getRoleByUserId(Integer id) {
        return ResponseDto.success(roleService.getRoleByUserId(id));
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @PostMapping("/update")
    public ResponseDto<Role> updateRole(@RequestBody Role role) {
        return ResponseDto.success(roleService.updateRole(role));
    }
}
