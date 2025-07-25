package com.example.myspring.fileList.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myspring.fileList.entity.FileListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileListMapper extends BaseMapper<FileListEntity> {

    List<FileListEntity> selectByUserId(Integer id);
}
