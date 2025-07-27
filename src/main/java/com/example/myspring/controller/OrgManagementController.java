package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.OrgManagement;
import com.example.myspring.service.OrgManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseDto<OrgManagement> add(@RequestBody OrgManagement orgManagement) {
        System.out.println(orgManagement);
        return ResponseDto.success(orgManagementService.add(orgManagement));
    }

    /**
     * 删除组织信息
     * @param obj
     */
    @RequestMapping("/delete")
    public ResponseDto<Void> delete(@RequestBody Map<String,Integer> obj) {
        Integer id = obj.get("id");
        System.out.println(id);
        orgManagementService.delete(id); // 异常会由 GlobalExceptionHandler 处理
        return ResponseDto.success("删除成功",null);
    }
}
