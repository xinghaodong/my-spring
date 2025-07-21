package com.example.myspring.entity;

import com.baomidou.mybatisplus.annotation.*;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Cesium实体类，用于表示Cesium相关数据
 */
@TableName("cesium")
@Data
@Getter
@Setter
public class Cesium {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO) // 自增主键
    private Integer id;

    /**
     * 名称，不能为空
     */
    private String name;

    /**
     * 点的数量，int类型，不能为空
     */
    @TableField("pointNum")
    private Integer pointNum;

    /**
     * 时间，int类型，不能为空
     */
    private Integer time;

    /**
     * 状态，int类型，不能为空
     */
    private Integer status;

    /**
     * 速度，string类型，不能为空
     */
    private String speed;

    /**
     * 总距离，string类型，不能为空
     */
    private String trackmileage;

    /**
     * 创建时间，不可更新
     */
    @JsonProperty("created_at")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt; // 数据库: created_at (DATETIME)

    /**
     * 更新时间
     */
    @JsonProperty("updated_at")
    @TableField(value = "updated_at", fill = FieldFill.UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 关联的临时航点集合，与Waypoint实体类的route属性关联
     */
    @TableField(exist = false) // 表示这不是数据库字段
    private Set<Waypoint> tempWaypoints = new HashSet<>();
}
