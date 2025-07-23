package com.example.myspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myspring.entity.InternalUser;
import com.example.myspring.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InternalUserMapper extends BaseMapper<InternalUser> {
    @Select("SELECT * FROM internal_user WHERE username = #{username}")
    InternalUser selectByUsername(@Param("username") String username);

    List<Role> selectRolesByUserId(Integer id);

    //    或者直接用 排除 password
//    QueryWrapper<InternalUser> wrapper = new QueryWrapper<>();
//    wrapper.eq("username",username).select("id","username","email","theme",“theme","created_at","updated_at"); // 排除 password
//    InternalUser user = internalUserMapper.selectOne(wrapper);
}
