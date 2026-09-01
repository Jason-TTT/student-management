package com.study.student.controller;

import com.study.student.conmon.ApiResponse;
import com.study.student.conmon.PageResult;
import com.study.student.entity.Student;
import com.study.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    //依赖注入
    public final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResult<Student>>> getStudentsPage
            (@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
//如果没有数据的话就使用 1 /  10
            @RequestParam(value ="name",required = false ) String name,
            @RequestParam(value ="classId",required = false ) Integer classId,
            @RequestParam(value ="order",defaultValue = "asc") String order,
            @RequestParam(value = "sortBy",defaultValue = "id") String sortBy
            ){
                return ResponseEntity.ok().body(ApiResponse.success(studentService.findByCondition(page,size,name,classId,order,sortBy)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudent(@PathVariable int id) {

        Student student = studentService.findById(id);

        return ResponseEntity.ok(ApiResponse.success(student));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Student>> addStudent(@Valid @RequestBody Student student) {
            Student newStudent = studentService.addStudent(student);

        return ResponseEntity
                .status(201)
                .body(
                        ApiResponse.success(
                                201,
                                "新增成功",
                                newStudent
                        )
                );
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable int id,@Valid @RequestBody Student student) {
                Student student1 = studentService.updateStudent(student,id);
                return ResponseEntity.ok(ApiResponse.success(student1));
        }

        @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteStudent(@PathVariable int id) {
//调用下层执行指向性代码
              studentService.deleteStudent(id);
              return ResponseEntity.ok(ApiResponse.success(null));

        }
    }

