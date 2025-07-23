package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.OrgManagement;
import com.example.myspring.service.OrgManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orgManagement")
public class OrgManagementController {
    @Autowired
    private OrgManagementService orgManagementService;

    @RequestMapping()
    public ResponseDto<List<OrgManagement>> getAll() {
        return ResponseDto.success(orgManagementService.getAll());
    }

}
