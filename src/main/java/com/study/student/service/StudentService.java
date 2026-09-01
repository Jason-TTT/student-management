package com.study.student.service;

import com.study.student.conmon.PageResult;
import com.study.student.entity.Student;
import com.study.student.exception.BusinessException;
import com.study.student.mapper.StudentMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import tools.jackson.databind.ser.jdk.JDKMiscSerializers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class StudentService {

    //依赖注入
    private final StudentMapper studentMapper;

    public StudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public Student findById(int id) {
        Student student=studentMapper.findById(id);
        if(student==null){
            throw new BusinessException(404,"学生不存在");
        }
        return student;
    }

    public PageResult<Student> findByCondition(int page, int size,String name,Integer classId,String order,String sortBy){
//      page为当前页面数
        if(page<1||size<1){
            throw new BusinessException(400,"参数异常");
        }
//        因为name存" " ,"   "等现象
        if(name==null||name.isBlank()){
            name=null;
        }

        if(!"desc".equalsIgnoreCase(order)&& !"asc".equalsIgnoreCase(order)){
            order="asc";
        }else{
           order=order.toLowerCase();
        }
        if(!"age".equalsIgnoreCase(sortBy) && !"id".equalsIgnoreCase(sortBy)){
            sortBy="id";
        }else{
           sortBy=sortBy.toLowerCase();
        }
        int offset = (page - 1) * size;

//        查这一页的数据
        List<Student> list=studentMapper.findByCondition(size, offset, name, classId,order,sortBy);
        long total=studentMapper.countByCondition(name, classId);
        long totalpage=(total+size-1)/size;
        return new PageResult<>(list,page,size,total,totalpage);
    }


    public Student addStudent(Student student) {
        Integer row =studentMapper.countClassById(student.getClassId());
        if(row==null){
            throw new BusinessException(400,"班级不存在");
        }
        studentMapper.add(student);
        return student;
    }

    public Student updateStudent(Student newstudents, int id) {

        newstudents.setId(id);
//        先检查后update
        Integer row2 =studentMapper.countClassById(newstudents.getClassId());
        if(row2==null){
            throw new BusinessException(400,"班级不存在");
        }

        int row = studentMapper.update(newstudents);
        if (row == 0) {
            throw new BusinessException(404,"学生不存在");
        }


        return studentMapper.findById(newstudents.getId());
    }


    public void deleteStudent(int id) {
        Integer row =studentMapper.countClassById(id);
        if(row!=null){
            throw new BusinessException(400,"该学生存在成绩记录，无法删除");
        }
        int row2 =studentMapper.deleteById(id);
        if(row2==0){
            throw new BusinessException(404,"学生不存在");
        }
    }
}