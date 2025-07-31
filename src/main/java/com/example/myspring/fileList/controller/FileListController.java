package com.example.myspring.fileList.controller;


import com.example.myspring.annotation.Public;
import com.example.myspring.config.ResponseDto;
import com.example.myspring.fileList.entity.FileListEntity;
import com.example.myspring.fileList.service.FileListService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class FileListController  {

    private final FileListService fileListService;

    public FileListController(FileListService fileListService) {
        this.fileListService = fileListService;
    }

    /**
     * 获取所有文件列表
     * @return 文件列表
     */
    @RequestMapping("/list")
    public List<FileListEntity>  getAll(){
        return fileListService.queryAll();
    }


    /**
     * 根据id 获取文件
     * @param id id
     */
    @Public
    @RequestMapping("/detail")
    public FileListEntity getById(@RequestParam(value = "id") Integer id){
        return fileListService.queryById(id);
    }

    /**
     * 附件上传
     * @param avatar 文件
     */
    @Public
    @PostMapping("/uploadFile")
    public ResponseDto<FileListEntity> uploadFile(@RequestParam("avatar") MultipartFile avatar) {
        System.out.println("上传文件：" + avatar);
        try {

            System.out.println("上传文件：" + avatar.getOriginalFilename());

            // 保存文件并返回附件对象
            FileListEntity savedFile = fileListService.insert(avatar);

            return ResponseDto.success(savedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
