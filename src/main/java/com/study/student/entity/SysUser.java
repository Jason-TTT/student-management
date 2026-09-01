package com.study.student.entity;

import jakarta.validation.constraints.NotBlank;

public class SysUser {
    int id;
    @NotBlank(message="姓名不能为空")
    String userName;
    @NotBlank(message ="密码不得为空")
    String passWord;
    String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
