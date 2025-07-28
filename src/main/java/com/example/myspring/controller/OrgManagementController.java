package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.OrgManagement;
import com.example.myspring.service.OrgManagementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orgManagement")
public class OrgManagementController {

    private final OrgManagementService orgManagementService;

    public OrgManagementController(OrgManagementService orgManagementService) {
        this.orgManagementService = orgManagementService;
    }

    /**
     * 获取所有组织信息
     * @return 所有组织信息
     */
    @RequestMapping()
    public ResponseDto<List<OrgManagement>> getAll() {
        return ResponseDto.success(orgManagementService.getAll());
    }

    /**
     * 新增组织信息
     * @param orgManagement orgManagement
     * @return 新增组织信息
     */
    @RequestMapping("/add")
    public ResponseDto<OrgManagement> add(@RequestBody OrgManagement orgManagement) {
        System.out.println(orgManagement);
        return ResponseDto.success(orgManagementService.add(orgManagement));
    }

    /**
     * 删除组织信息
     * @param obj obj
     */
    @RequestMapping("/delete")
    public ResponseDto<Void> delete(@RequestBody Map<String,Integer> obj) {
        Integer id = obj.get("id");
        System.out.println(id);
        orgManagementService.delete(id); // 异常会由 GlobalExceptionHandler 处理
        return ResponseDto.success("删除成功",null);
    }

    /**
     * 获取详情
     * @param id id
     * @return 详情
     */
    @RequestMapping("/detail")
    public ResponseDto<OrgManagement> detail(@RequestParam Integer id) {
        return ResponseDto.success(orgManagementService.detail(id));
    }

}
