package com.example.myspring.config;

import com.example.myspring.entity.Cesium;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private int code;
    private String message;
    private T data;

    // 静态方法方便调用
    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(200, "操作成功", data);
    }

    public static <T> ResponseDto<T> success(String message, T data) {
        return new ResponseDto<>(200, message, data);
    }

    public static ResponseDto<Cesium> error(int code, String message) {
        return new ResponseDto<>(code, message, null);
    }
}