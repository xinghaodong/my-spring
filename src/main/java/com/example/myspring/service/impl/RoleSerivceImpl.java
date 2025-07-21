package com.example.myspring.service.impl;

import com.example.myspring.entity.Role;
import com.example.myspring.mapper.RoleMapper;
import com.example.myspring.service.RoleService;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}
