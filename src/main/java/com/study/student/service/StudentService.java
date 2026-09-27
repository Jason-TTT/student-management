package com.study.student.service;

import com.study.student.conmon.PageResult;
import com.study.student.entity.Student;
import com.study.student.exception.BusinessException;
import com.study.student.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class StudentService {

    //依赖注入
    private final StudentMapper studentMapper;

    public StudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public Student findById(int id) {
        log.debug("开始查询学生 id={}",id);
        Student student=studentMapper.findById(id);
        if(student==null){
            throw new BusinessException(404,"学生不存在");
        }
        return student;
    }
    public PageResult<Student> findByCondition(int page, int size,String name,Integer classId,String order,String sortBy){
        log.debug("用户开始分页查询");
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
        log.debug("用户开始添加学生");
        Integer row =studentMapper.countClassById(student.getClassId());
        if(row==null){
            throw new BusinessException(400,"班级不存在");
        }
        studentMapper.add(student);
        log.info("添加成功,id={}",student.getId());
        return student;
    }
    public Student updateStudent(Student newStudent, int id) {
        log.debug("用户开始编辑存在用户数据,id={}",id);
        newStudent.setId(id);
//        先检查后update
        Integer row2 =studentMapper.countClassById(newStudent.getClassId());
        if(row2==null){
            throw new BusinessException(400,"班级不存在");
        }
        int row = studentMapper.update(newStudent);
        if (row == 0) {
            throw new BusinessException(404,"学生不存在");
        }
        log.info("编辑成功,id={}",id);
        return studentMapper.findById(newStudent.getId());
    }


    public void deleteStudent(int id) {
        log.debug("用户开始进行删除操作,id={}",id);
        Integer row =studentMapper.countScoreById(id);
        if(row!=null){
            log.warn("存在成绩记录关联，无法删除,id={}",id);
            throw new BusinessException(400,"该学生存在成绩记录，无法删除");

        }
        int row2 =studentMapper.deleteById(id);
        if(row2==0){
            throw new BusinessException(404,"学生不存在");
        }
        log.info("删除成功,id={}",id);
    }
}