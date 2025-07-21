package com.example.myspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myspring.entity.InternalUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InternalUserMapper extends BaseMapper<InternalUser> {
}
