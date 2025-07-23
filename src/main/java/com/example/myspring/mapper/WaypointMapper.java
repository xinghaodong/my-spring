package com.example.myspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myspring.entity.Waypoint;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface WaypointMapper extends BaseMapper<Waypoint> {
//    自定义查询 ORDER BY id ASC
    @Select("SELECT id, latitude, longitude, height FROM waypoint WHERE route_id = #{cesiumId}")
    List<Waypoint> selectByCesiumId( Integer cesiumId );

    // 根据航线ID删除所有关联的航点
    @Delete("DELETE FROM waypoint WHERE route_id = #{cesiumId}")
    int deleteByCesiumId(@Param("cesiumId") Integer cesiumId);

}
