package com.example.myspring.service.impl;

import com.example.myspring.entity.Role;
import com.example.myspring.mapper.RoleMapper;
import com.example.myspring.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class RoleSerivceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


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
    public Role updateRole(Role role) {
// 先根据传入的id查询出数据库中对应的数据
        Role existing = roleMapper.selectById(role.getId());
        System.out.println( "查询到的数据：" + existing );
        if (existing == null) {
            return null;
        }
        role.setUpdatedAt(LocalDateTime.now());
        roleMapper.updateById(role);
        return roleMapper.selectById(role.getId());
    }
}
