package com.example.myspring.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@TableName("internal_user")
@Data
@Getter
@Setter
public class InternalUser {
    @TableId(type = IdType.AUTO)
    private Integer id;


    private String username;

    private String name;

    private String password;

    private String email;

    private String theme;

    private String age;

  //@JsonProperty 映射JSON字段 指定JSON字段名，前端是要created_at，不然
    /**
     * Lombok 的 @Data 会自动生成：
     * public Date getCreated_at() {
     *     return created_at;
     * }
     * public void setCreated_at(Date created_at) {
     *     this.created_at = created_at;
     * }
     * 但 Jackson 默认会找：
     * getCreatedAt() 和 setCreatedAt(Date)
     * 因为它默认使用 驼峰命名策略
     * 所以如果你字段是 created_at，Jackson 会尝试找 getCreatedAt()，但我的方法是 getCreated_at()，导致找不到，字段就为 null 。
     *
     */
    @JsonProperty("created_at")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Date created_at;

   @JsonProperty("updated_at")
    @TableField(value = "updated_at", fill = FieldFill.UPDATE)
    private Date updated_at;
}
