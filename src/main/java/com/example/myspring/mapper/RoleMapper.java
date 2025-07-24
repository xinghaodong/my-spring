package com.example.myspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myspring.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

//    @Select("SELECT * FROM role WHERE id IN (SELECT role_id FROM internal_user_role WHERE internal_user_id = #{internalUserId})")
//    List<Role> selectRolesByUserId(Integer internalUserId);

    @Select("SELECT role.* FROM role " +
            "INNER JOIN internal_user_roles_role ON role.id = internal_user_roles_role.roleId " +
            "WHERE internal_user_roles_role.internalUserId = #{userId}")
    List<Role> findRolesByUserId(Integer userId);

}
