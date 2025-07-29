package com.example.myspring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
     * 避免重复代码：无需在每个 Controller 方法中写 try-catch。
     * 统一错误格式：所有异常返回相同的 JSON 结构。
     */

    // 处理参数缺失异常（@RequestParam required=true 但未传）
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingParam(MissingServletRequestParameterException ex) {
        ErrorResponseDto error = new ErrorResponseDto(400, "必须提供参数: " + ex.getParameterName());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 当请求缺少 @PathVariable 参数时触发（例如 @GetMapping("/user/{id}") 但访问 /user/ 没有id时
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingPathVariable() {
        ErrorResponseDto error = new ErrorResponseDto(400, "缺少必须的参数");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    //   处理 Exception（兜底处理） 捕获所有未被其他 @ExceptionHandler 处理的异常，避免直接向客户端暴露堆栈信息。
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<?>> handleAllExceptions(Exception ex) {
        ResponseDto<?> error;
        HttpStatus status;

        if (ex instanceof IllegalArgumentException) {
            error = ResponseDto.fail(ex.getMessage());  // 直接返回你抛出的友好信息
            status = HttpStatus.BAD_REQUEST;
        } else {
            // 其他未知异常才视为“系统错误”
            error = ResponseDto.fail("系统内部错误");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("e: ", ex);
            // 建议打印日志：log.error("系统异常", ex);
        }

        return new ResponseEntity<>(error, status);
    }
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception ex) {
//        ErrorResponseDto error = new ErrorResponseDto(500, "系统内部错误:     " + ex.getMessage());
//        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}