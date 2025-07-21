package com.example.myspring.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    private Date createdAt;

    @TableField(value = "updated_at", fill = FieldFill.UPDATE)
    private Date updatedAt;

}
