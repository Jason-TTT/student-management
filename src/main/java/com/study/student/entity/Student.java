package com.study.student.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Student {
    private int id;
    @NotBlank(message = "学生姓名不能为空")
    private String studentName;
    @Min(value = 1, message = "学生年龄不能小于1")
    private int studentAge;
    @Min(value = 1,message = "学生班级不能小于1")
    private int classId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }


    public Student() {
    }

    @Override
    public String toString() {
        return  id + "," + studentName + "," + studentAge + "," + classId;
    }
}
