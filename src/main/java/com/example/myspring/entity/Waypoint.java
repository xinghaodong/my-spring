package com.example.myspring.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@TableName("waypoint") // 指定表名（如果表名与类名不同）
public class Waypoint {

    @TableId(type = IdType.AUTO) // 自增主键
    private Integer id;

    // 精度配置需在数据库表结构中定义，或通过 @TableField 指定字段名
    @TableField("latitude")
    private BigDecimal latitude;

    @TableField("longitude")
    private BigDecimal longitude;

    @TableField("height")
    private Double height;

    // 添加与Cesium的关联字段
    @TableField("route_id") // 如果数据库字段名是route_id
    private Integer routeId;

}