package com.study.student.dto;

import jakarta.validation.constraints.NotBlank;

public class ChangePassWordRequest {
//    数据库实体长什么样，不代表每个接口请求就必须长什么样
    @NotBlank(message = "密码不得为空")
    private String oldPassword;
    @NotBlank(message = "密码不得为空")
    private String newPassword;
    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ChangePassWordRequest() {
    }
}
