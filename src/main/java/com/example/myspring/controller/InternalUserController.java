package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.InternalUser;
import com.example.myspring.service.InternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/internalusers")
public class InternalUserController {
    @Autowired
    private InternalUserService internalUserService;

    // 新增：分页查询接口
    @GetMapping("/find")
    public ResponseDto<Map<String, Object>> getUserList(@RequestParam int page,@RequestParam int pageSize) {
        return ResponseDto.success(internalUserService.getUserPage(page, pageSize));
    }
}
