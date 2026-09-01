package com.study.student.mapper;



import com.study.student.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("""
            select id, student_name, student_age, class_id from student
            where id =#{id}
            """)
    Student findById(int id);

    @Select("""
            <script>
            select id, student_name, student_age, class_id from student
            <where>
        
            <if test="name!=null and name!=''">
            student_name like concat('%',#{name},'%')
            </if>
          
            <if test="classId!=null">
            and class_id=#{classId}
            </if>
         
        
           </where>
        
           order by
          
           <choose>
           <when test="sortBy=='age'">
           student_age
          </when>
          
          <otherwise>
          id
          </otherwise>
          
          </choose>
          
          <choose>
          
           <when test="order=='desc'">
            desc

           </when>
         
           <otherwise>
            asc

           </otherwise>
         
           </choose>

           limit #{limit} offset #{offset}
           </script>
          """)
    List<Student> findByCondition(
            @Param("limit") int limit,
            @Param("offset") int offset,
            @Param("name") String name,
            @Param("classId") Integer classId,
            @Param("order") String order,
            @Param("sortBy") String sortBy

    );

    @Select("""
            <script>
            select count(*) from student
            <where>
           
            <if test="name!=null and name!=''">
            student_name like concat('%',#{name},'%')
            </if>
           
            <if test="classId!=null">
            and class_id=#{classId}
            </if>
           
           </where>
           </script>
           """)
    long countByCondition( @Param("name") String name,
                           @Param("classId") Integer classId

    );

    @Select("""
            select id from class
            where id=#{classId}
            """)
    Integer countClassById(@Param("classId") int classId);

    @Select ("""
            select id from score
            where student_id=#{id}
            """)
    Integer countScoreById(@Param("id") int id);


    @Insert("""
             insert into student(student_name, student_age, class_id)
                      values(#{studentName}, #{studentAge}, #{classId})
            """)

 @Options(useGeneratedKeys = true,keyProperty = "id")
    int add(Student student);



    @Update("""
             update student
                set student_name = #{studentName},
                    student_age = #{studentAge},
                    class_id = #{classId}
                where id = #{id}
            """)
    int update(Student student);

    @Delete("""
            delete from student
            where id=#{id}
            """)
    int deleteById(int id);
   }

