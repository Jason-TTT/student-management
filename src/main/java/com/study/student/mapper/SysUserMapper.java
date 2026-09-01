package com.study.student.mapper;

import com.study.student.entity.SysUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SysUserMapper {

    @Select("""
    select  id, username, password, role  from  sys_user
    where username =#{userName}
   """)
  SysUser findByUsername(@Param("userName") String userName);

    @Insert("""
            insert into sys_user(username,password,role)
            values (#{userName},#{passWord},#{role})
            """)
    @Options(useGeneratedKeys = true,keyProperty ="id" )
    int addUser(SysUser user);

    @Update("""
            update sys_user
            set password=#{passWord}
            where username=#{userName}
            """)
    int updatePassword(@Param("userName") String userName, @Param("passWord") String passWord);
}

