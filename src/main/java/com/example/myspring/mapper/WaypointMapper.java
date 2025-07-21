package com.example.myspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myspring.entity.Waypoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WaypointMapper extends BaseMapper<Waypoint> {
//    自定义查询
    @Select("SELECT id, latitude, longitude, height FROM waypoint WHERE routeid = #{cesiumId}")
    List<Waypoint> selectByCesiumId( Integer cesiumId );

}
