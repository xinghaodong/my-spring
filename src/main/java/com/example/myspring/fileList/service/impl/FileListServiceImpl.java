package com.example.myspring.fileList.service.impl;

import com.example.myspring.fileList.entity.FileListEntity;
import com.example.myspring.fileList.mapper.FileListMapper;
import com.example.myspring.fileList.service.FileListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FileListServiceImpl implements FileListService {

    @Autowired
    private FileListMapper fileListMapper;

    // 设置文件上传目录
    private static final String UPLOAD_DIR = "uploads/";
    private final String uploadPath = System.getProperty("user.dir") + "/src/main/resources/static/" + UPLOAD_DIR;


    @Override
    public List<FileListEntity> queryAll() {
        return fileListMapper.selectList(null);
    }

    @Override
    public FileListEntity queryById(Integer id) {
        return fileListMapper.selectById(id);
    }

    @Override
    public FileListEntity insert(MultipartFile avatar) throws IOException {
        System.out.println("上传文件：" + avatar);
//        return fileListMapper.insert(new FileListEntity(null, userId, avatar.getOriginalFilename(), avatar.getContentType(), avatar.getSize(), LocalDateTime.now()))
//    解析 传来的  文件

        // 确保上传目录存在
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        // 生成唯一文件名
        String originalFilename = avatar.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
        // 保存文件到服务器
        String filePath = uploadPath + uniqueFilename;
        File dest = new File(filePath);
        avatar.transferTo(dest);

        FileListEntity fileListEntity = new FileListEntity();
        fileListEntity.setFileName(avatar.getOriginalFilename());
        fileListEntity.setContentType(avatar.getContentType());
        fileListEntity.setFilePath("uploads/" + uniqueFilename);
        fileListEntity.setCreated_at(LocalDateTime.now());
        fileListEntity.setFileSize((int) avatar.getSize());
        // 插入数据库
        fileListMapper.insert(fileListEntity);
        return fileListEntity;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        fileListMapper.deleteById(id);
    }


}
