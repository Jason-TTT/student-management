package com.study.student.conmon;

import org.springframework.aop.framework.AopInfrastructureBean;

public class ApiResponse<T>{
        private int code;
        private String message;
        private T data;
        public ApiResponse(int code, String message, T data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static<T> ApiResponse<T> success(T data){
            return new ApiResponse<>(200,"查询成功",data);
    }

    public static<T> ApiResponse<T> success(int code,String message,T data){
            return new ApiResponse<>(code,message,data);

    } public static<T> ApiResponse<T> error(int code,String message,T data){
            return new ApiResponse<>(code,message,data);
    }

}
