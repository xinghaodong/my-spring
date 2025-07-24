package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.OrgManagement;
import com.example.myspring.service.OrgManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orgManagement")
public class OrgManagementController {
    @Autowired
    private OrgManagementService orgManagementService;

    /**
     * 获取所有组织信息
     * @return
     */
    @RequestMapping()
    public ResponseDto<List<OrgManagement>> getAll() {
        return ResponseDto.success(orgManagementService.getAll());
    }

    /**
     * 新增组织信息
     * @param orgManagement
     * @return
     */
    @RequestMapping("/add")
    public ResponseDto<OrgManagement> add(OrgManagement orgManagement) {
        return ResponseDto.success(orgManagementService.add(orgManagement));
    }

    /**
     * 删除组织信息
     * @param id
     */
    @RequestMapping("/delete")
    public ResponseDto<Void> delete(@RequestParam(required = true) Integer id) {
        orgManagementService.delete(id); // 异常会由 GlobalExceptionHandler 处理
        return ResponseDto.success("删除成功",null);
    }
}
