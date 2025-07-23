package com.example.myspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myspring.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
