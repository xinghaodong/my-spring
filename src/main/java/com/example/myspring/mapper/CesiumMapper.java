package com.example.myspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myspring.entity.Cesium;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CesiumMapper extends BaseMapper<Cesium> {
// 如果需要自定义查询，可以在这里添加
}
