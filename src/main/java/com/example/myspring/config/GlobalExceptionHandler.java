package com.example.myspring.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception ex) {
        ErrorResponseDto error = new ErrorResponseDto(500, "系统内部错误:     " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 可以添加更多特定异常的处理，例如：
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingPathVariable() {
        ErrorResponseDto error = new ErrorResponseDto(400, "缺少必须的参数");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}