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
        result.put("data", userPage.getRecords());
        result.put("total", userPage.getTotal());
        return result;
    }

    /**
     * 根据用户名查询用户信息
     */
    @Override
    public InternalUser getByUsername(String username) {
        System.out.println("查询的用户名：" + username);
        return internalUserMapper.selectByUsername(username);
    }


    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Map<String, Object> login(String username, String password) {
        InternalUser internalUser = getByUsername(username);
        Map<String, Object> result = new HashMap<>();
        result.put("informationObject", internalUser);
//        这里先暂时写死后续在搞jtw 双token
        result.put("token","123455");
        return result;
//        更具用户名查询用户
//        这里需要把 internalUser  对象都包装在一个对象里informationObject里
//        InternalUser internalUser = getByUsername(username);
//        return internalUser;
    }

}
