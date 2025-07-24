package com.example.myspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myspring.entity.InternalUser;
import com.example.myspring.entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InternalUserMapper extends BaseMapper<InternalUser> {
    @Select("SELECT * FROM internal_user WHERE username = #{username}")
    InternalUser selectByUsername(@Param("username") String username);

    List<Role> selectRolesByUserId(Integer id);

   // 给用户添加角色 中间关联表 internal_user_roles_role
    @Insert("INSERT INTO internal_user_roles_role (internalUserId, roleId) VALUES (#{internalUserId}, #{roleId})")
    void addRole(@Param("internalUserId") Integer internalUserId, @Param("roleId") Integer roleId);

    /***
     * 删除用户角色
     * @param userId
     */
    @Select("DELETE FROM internal_user_roles_role WHERE internalUserId = #{userId}")
    void deleteRolesByUserId(@Param("userId") Integer userId);

}
