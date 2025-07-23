package com.example.myspring.controller;

import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.InternalUser;
import com.example.myspring.service.InternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class InternalUserController {
    @Autowired
    private InternalUserService internalUserService;

    // 新增：分页查询接口
    @GetMapping("/internalusers/find")
    public ResponseDto<Map<String, Object>> getUserList(@RequestParam int page,@RequestParam int pageSize) {
        return ResponseDto.success(internalUserService.getUserPage(page, pageSize));
    }

    /**
     * 员工登录接口
     * post 请求
     * /auth/login
     * return 登录成功后的员工信息
     */
    @PostMapping("/auth/login")
    public ResponseDto<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        return ResponseDto.success(internalUserService.login(username, password));
    }

    /**
     * 获取员工信息接口
     * get 请求
     * @param id
     * return 员工信息
     */

    @GetMapping("internalusers/detail")
    public ResponseDto<InternalUser> getUserDetail(@RequestParam Integer id) {
        return ResponseDto.success(internalUserService.getById(id));
    }
}
