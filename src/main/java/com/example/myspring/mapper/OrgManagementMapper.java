package com.example.myspring.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myspring.entity.OrgManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface OrgManagementMapper extends BaseMapper<OrgManagement> {
//    根据 orgcode 编码查询是否有相同的组织
    @Select("SELECT * FROM org_management WHERE orgcode = #{orgcode}")
    OrgManagement selectByOrgcode(String orgcode);

}
