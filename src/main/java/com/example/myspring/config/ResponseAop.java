package com.example.myspring.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class ResponseAop {
    @Around("execution(* com.example..controller..*(..))")
    public Object wrapResponse(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();

        // 排除已经包装的响应
        if (result instanceof ResponseDto || result instanceof ResponseEntity) {
            return result;
        }

        // 特殊处理Optional
        if (result instanceof Optional) {
            Optional<?> opt = (Optional<?>) result;
            return opt.map(ResponseDto::success);
        }

        return ResponseDto.success(result);
    }
}
