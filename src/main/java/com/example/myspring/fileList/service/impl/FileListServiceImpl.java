package com.example.myspring.fileList.service.impl;

import com.example.myspring.fileList.entity.FileListEntity;
import com.example.myspring.fileList.mapper.FileListMapper;
import com.example.myspring.fileList.service.FileListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class FileListServiceImpl implements FileListService {

    @Autowired
    private FileListMapper fileListMapper;


    @Override
    public List<FileListEntity> queryAll() {
        return fileListMapper.selectList(null);
    }

    @Override
    public FileListEntity queryById(Integer id) {
        return fileListMapper.selectById(id);
    }

    @Override
    public void insert(String userId, MultipartFile avatar) {
//        return fileListMapper.insert(new FileListEntity(null, userId, avatar.getOriginalFilename(), avatar.getContentType(), avatar.getSize(), LocalDateTime.now()))
    }


}
