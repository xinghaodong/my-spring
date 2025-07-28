package com.example.myspring.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@TableName("org_management")
public class OrgManagement {
    @TableId(type = IdType.AUTO)
    private Integer organid;

    private String organame;

    private String orgcode;

    // 父菜单ID
    @TableField("parentId")
    private Integer parentId;

//    父菜单容器字段 children
    @TableField(exist = false)
    private List<OrgManagement> children;


//    关联的用户对象数组，非数据库字段
    @TableField(exist = false)
    private List<InternalUser> employees;



}
