package com.example.myspring.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@TableName("menu")
@Data
@Getter
@Setter
public class Menu {
    @TableId( type = IdType.AUTO )
    private Integer id;

    private String name;

    private String url;

    // 父菜单ID
    @TableField("parentId")
    private Integer parentId;

    private String component;

    private String icon;

    private String keepalive;

    private String vuepage;

    private Integer sorts;

    private String code;

    private String menutype;

    private String perms;

    private String isscreen;


    // 子菜单列表，非数据库字段
    @TableField(exist = false)
    private List<Menu> children;

    // 父菜单对象，非数据库字段
    @TableField(exist = false)
    private Menu parent;

}
