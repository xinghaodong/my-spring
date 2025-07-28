package com.example.myspring.fileList.service;

import com.example.myspring.fileList.entity.FileListEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileListService {
//    查询所有的附件列表 返回数组对象
    List<FileListEntity> queryAll();

//    根据id查询附件
    FileListEntity queryById(Integer id);


    FileListEntity insert(MultipartFile avatar) throws IOException;

//    根据id删除附件
    void deleteById(Integer id);
}
