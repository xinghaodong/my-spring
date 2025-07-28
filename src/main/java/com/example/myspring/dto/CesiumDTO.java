package com.example.myspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
public class CesiumDTO {
    private Integer id;
    private String name;
    private Integer pointNum;
    private Integer time;
    private Integer status;
    private String speed;
    private String trackmileage;
    private Date created_at;
    private Date updated_at;
}