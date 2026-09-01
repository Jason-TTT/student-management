package com.study.student.interceptor;

import com.study.student.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
//        获取当前会话，但不创建新会话（false 表示如果没有会话则返回 null）
        HttpSession session = request.getSession(false);

        if(session == null||session.getAttribute("sysUserId")==null) {
            throw new BusinessException(401,"未登入");
        }
        return true;
    }
}
