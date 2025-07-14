package com.example.myspring.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // 匹配所有以 /api 开头的路径
                .allowedOrigins("http://localhost:3000", "http://127.0.0.1:3000","http://localhost:5173","http://localhost:5174","http://127.0.0.1:8080") // 允许的前端域名
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 支持的方法
                .exposedHeaders("Authorization") // 暴露给前端的头部
                .allowCredentials(true) // 是否允许携带 Cookie
                .maxAge(3600); // 预检请求缓存时间（秒）
    }
}