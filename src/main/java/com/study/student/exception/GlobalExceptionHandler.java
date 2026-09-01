package com.study.student.exception;

import com.study.student.conmon.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //    专门盯着所有 Controller，如果它们抛异常，我可以统一处理


        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
                errors.put(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                );
            }
            return ResponseEntity.badRequest().body(ApiResponse.error(400, "参数校验失败", errors));
        }

        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {

            return ResponseEntity.status(ex.getCode()).body(
                    ApiResponse.error(ex.getCode(), ex.getMessage(), null)
            );
        }


//  如果前面没有更专门的异常处理方法能处理它，那普通 Exception 我来兜底。
//        Spring 会优先找更具体、更匹配的异常处理器
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ApiResponse.error(500, "服务器错误", null));
          }
        }

