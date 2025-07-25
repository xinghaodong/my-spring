package com.example.myspring.fileList.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter

@TableName("filelist")
public class FileListEntity {

    @TableId( type = IdType.AUTO )
    private Integer id;

    @TableField("fileName")
    private String fileName;

    @TableField("filePath")
    private String filePath;

    @TableField("fileSize")
    private Integer fileSize;

    @TableField("contentType")
    private String contentType;


    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "created_at",fill = FieldFill.INSERT)
    private LocalDateTime created_at;
}
