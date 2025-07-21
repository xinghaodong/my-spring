package com.example.myspring.controller;


import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.Role;
import com.example.myspring.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/findAll")
    public ResponseDto<List<Role>> getAll() {
        return ResponseDto.success(roleService.getAll());
    }
}
