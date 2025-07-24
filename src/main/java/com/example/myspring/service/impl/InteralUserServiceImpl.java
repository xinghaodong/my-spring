package com.example.myspring.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myspring.entity.InternalUser;
import com.example.myspring.entity.Role;
import com.example.myspring.mapper.InternalUserMapper;
import com.example.myspring.mapper.RoleMapper;
import com.example.myspring.service.InternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class InteralUserServiceImpl implements InternalUserService {
    @Autowired
    private InternalUserMapper internalUserMapper;
    @Autowired
    private RoleMapper roleMapper;

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

    @Override
    public InternalUser getById(Integer id) {
//        通过过来的id 查询用户信息
        InternalUser internalUser = internalUserMapper.selectById(id);
        if (internalUser != null) {
            List<Role> roleIds = roleMapper.findRolesByUserId(internalUser.getId());
//          便利 roleIds 取出角色id 放入 internalUser.roleIds 中，这里需要和 nestjs 端对应
            List<Integer> Ids = new ArrayList<>();
            for (Role role : roleIds) {
                Ids.add(role.getId());
                internalUser.setRoleIds(Ids);
            }
            return internalUser;
        }
        return null;
    }


    /**
     * 修改用户
     * @param internalUser
     * @return
     */
    @Override
    public InternalUser updateUser(InternalUser internalUser) {
        System.out.println("修改的用户信息：" + internalUser);
//       先获取是否存在
        InternalUser oldUser = internalUserMapper.selectById(internalUser.getId());
        System.out.println("修改前的用户信息：" + oldUser);
        internalUserMapper.updateById(internalUser);
//        再获取 用户关联的角色
        List<Role> roles = roleMapper.findRolesByUserId(internalUser.getId());
        for( Role role : roles ){
            internalUserMapper.deleteRolesByUserId(internalUser.getId());
        }
        return  internalUser;
    }


    /**
     * 添加用户
     *
     * @param internalUser
     * @return
     */
    @Override
//    @Transactional
    public InternalUser add(InternalUser internalUser) {
        System.out.println("添加的用户信息：" + internalUser);
        // 先取roleIds数组集合
        List<Integer> roleIds = internalUser.getRoleIds();
//        设置创建时间
        internalUser.setCreatedAt(LocalDateTime.now());
//        暂时先设置一个密码
        internalUser.setPassword("888888");
        // 先插入用户，让数据库生成自增ID
        internalUserMapper.insert(internalUser);
        System.out.println("插入后的用户ID：" + internalUser.getId());
        // 然后使用生成的ID添加角色关联
        if (roleIds != null && !roleIds.isEmpty()) {
            for (Integer roleId : roleIds) {
                System.out.println("添加的用户ID：" + internalUser.getId());
                System.out.println("添加的角色ID：" + roleId);
                internalUserMapper.addRole(internalUser.getId(), roleId);
            }
        }

        return internalUser;
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
        result.put("token", "123455");
        return result;
//        更具用户名查询用户
//        这里需要把 internalUser  对象都包装在一个对象里informationObject里
//        InternalUser internalUser = getByUsername(username);
//        return internalUser;
    }

}
