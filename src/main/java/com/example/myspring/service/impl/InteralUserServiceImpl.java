package com.example.myspring.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myspring.entity.InternalUser;
import com.example.myspring.mapper.InternalUserMapper;
import com.example.myspring.service.InternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InteralUserServiceImpl implements InternalUserService {
    @Autowired
    private InternalUserMapper internalUserMapper;
    @Override
    public List<InternalUser> getAll() {
        return internalUserMapper.selectList(null);
    }

    @Override
    public Map<String, Object> getUserPage(int page, int pageSize) {
        Page<InternalUser> userPage = new Page<>(page, pageSize);
        internalUserMapper.selectPage(userPage, null); // 无条件分页

        Map<String, Object> result = new HashMap<>();
        System.out.println(userPage);
        result.put("data", userPage.getRecords());
        result.put("total", userPage.getTotal());
        return result;
    }
}
