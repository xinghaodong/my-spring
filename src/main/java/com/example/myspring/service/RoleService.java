package com.example.myspring.service;

import com.example.myspring.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
    List<Role> getAll();

    Role getRoleByUserId(Integer userId);

    Role updateRole(Role role);

    List<Integer> getRoleMenus(Integer id);

    Role updateRoleMenus(Map<String, Object> obj);

    Role addRole(Role role);
}
