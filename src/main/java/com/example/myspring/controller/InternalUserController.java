package com.example.myspring.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.myspring.config.ResponseDto;
import com.example.myspring.entity.InternalUser;
import com.example.myspring.service.InternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        System.out.println(request);
        String username = request.get("username").trim();
        String password = request.get("password").trim();
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

    /**
     * 新增员工
     * post 请求
     * return 新增员工信息
     */
    @PostMapping("internalusers/add")
    public ResponseDto<InternalUser> addUser(@RequestBody InternalUser internalUser) {
        System.out.println(internalUser);
        return ResponseDto.success(internalUserService.add(internalUser));
    }

    /**
     * 修改员工信息接口
     * post 请求
     */
    @PostMapping("internalusers/update")
    public ResponseDto<InternalUser> updateUser(@RequestBody InternalUser internalUser) throws IOException {
        System.out.println(internalUser);
        return ResponseDto.success(internalUserService.updateUser(internalUser));
    }

    /**
     * 删除员工接口
     * post 请求
     */

    @PostMapping("internalusers/delete")
    public ResponseDto<Void> deleteUser(@RequestBody Map<String, Integer> id) {
        System.out.println(id);
        internalUserService.deleteUser(id.get("id"));
        return ResponseDto.success("删除成功",null);

    }
}
