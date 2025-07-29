package com.example.myspring.filter;

import com.example.myspring.annotation.Public;
import com.example.myspring.service.InternalUserService;
import com.example.myspring.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final InternalUserService internalUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是 OPTIONS 请求，放行（跨域）
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 检查是否带有 @Public 注解
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(Public.class) ||
                    handlerMethod.getBeanType().isAnnotationPresent(Public.class)) {
                return true;
            }
        }

        // 获取 Authorization Header
        String authHeader = request.getHeader("Authorization");
        System.out.println("authHeader: " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            returnJson(response, 400, "请求缺少 Token，请检查是否已传递");
            return false;
        }

        String token = authHeader.substring(7);
        Claims claims = jwtUtil.getClaimsFromToken(token);

        System.out.println("claims: " + claims);

        if (claims == null || jwtUtil.isTokenExpired(claims)) {
            returnJson(response, 401, "Token 验证失败，请检查 Token 是否正确");
            return false;
        }

        // 设置认证上下文（可选：存入 ThreadLocal / SecurityContext）
        Long userId = claims.get("userId", Long.class);
        String username = claims.getSubject();
        request.setAttribute("userId", userId);
        request.setAttribute("username", username);

        return true;
    }

    private void returnJson(HttpServletResponse response, int code, String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK); // 注意：返回 200，但业务码区分
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("message", message);
        response.getWriter().write(mapper.writeValueAsString(result));
    }
}