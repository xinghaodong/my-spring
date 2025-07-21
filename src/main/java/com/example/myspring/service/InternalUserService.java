package com.example.myspring.service;

import com.example.myspring.entity.InternalUser;
import java.util.List;
import java.util.Map;

public interface InternalUserService  {
    List<InternalUser> getAll();

    // 分页方法
    Map<String, Object> getUserPage(int page, int pageSize);
}
