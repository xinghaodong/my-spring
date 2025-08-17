package com.example.myspring.config;

import com.example.myspring.filter.JwtAuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // 标记为配置类，Spring 会自动加载
public class WebConfig implements WebMvcConfigurer {

    private final JwtAuthenticationInterceptor jwtAuthenticationInterceptor;

    public WebConfig(JwtAuthenticationInterceptor jwtAuthenticationInterceptor) {
        this.jwtAuthenticationInterceptor = jwtAuthenticationInterceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 获取项目根目录下的 uploads 路径
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

        // 映射 /uploads/** → 文件目录（原有）
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir)
                .setCachePeriod(3600);

        //  映射 /api/uploads/** → 同一个文件目录
        registry.addResourceHandler("/api/uploads/**")
                .addResourceLocations("file:" + uploadDir)
                .setCachePeriod(3600);

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthenticationInterceptor)
                .addPathPatterns("/**")                  // 拦截所有请求
                .excludePathPatterns(                     // 但排除以下路径
                        "/uploads/**",         // 排除上传的静态资源
                        "/api/uploads/**"
                );
//        其他接口 使用@Public注解 排除认证
    }
}