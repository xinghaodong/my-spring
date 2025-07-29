package com.example.myspring.service;

import com.example.myspring.entity.InternalUser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface InternalUserService  {
    List<InternalUser> getAll();

    // 分页方法
    Map<String, Object> getUserPage(int page, int pageSize);

    InternalUser add(InternalUser internalUser);

    Map<String,Object> login(String username, String password);

    // 根据用户名查询用户
    InternalUser getByUsername(String username);

    InternalUser getById(Integer id);

    InternalUser updateUser(InternalUser internalUser) throws IOException;

    void deleteUser(Integer id);

    Map<String, String> refreshToken(String refreshToken);
}
