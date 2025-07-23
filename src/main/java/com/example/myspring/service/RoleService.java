package com.example.myspring.service;

import com.example.myspring.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAll();

    Role getRoleByUserId(Integer userId);

    Role updateRole(Role role);
}
