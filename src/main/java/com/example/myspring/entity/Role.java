package com.example.myspring.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@TableName("role")
@Data
@Getter
@Setter
public class Role {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer states;


    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.UPDATE)
    private LocalDateTime updatedAt;

}
