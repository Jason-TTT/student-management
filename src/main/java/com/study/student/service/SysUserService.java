package com.study.student.service;

import com.study.student.dto.ChangePassWordRequest;
import com.study.student.entity.SysUser;
import com.study.student.exception.BusinessException;
import com.study.student.mapper.StudentMapper;
import com.study.student.mapper.SysUserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {

    public final SysUserMapper sysUserMapper;
    public final PasswordEncoder passwordEncoder;
    public SysUserService(SysUserMapper sysUserMapper, PasswordEncoder passwordEncoder) {
        this.sysUserMapper = sysUserMapper;
        this.passwordEncoder = passwordEncoder;
    }
//不把信息返回给前端
    public void register(SysUser sysUser) {
        SysUser sysUser1=sysUserMapper.findByUsername(sysUser.getUserName());
        if(sysUser1!=null){
            throw new BusinessException(400,"用户名已经存在");
        }
//      Bcrypt技术
        String encode = passwordEncoder.encode(sysUser.getPassWord());
        sysUser.setPassWord(encode);
        sysUser.setRole("user");
        sysUserMapper.addUser(sysUser);
    }

    public SysUser login(SysUser sysUser) {
        SysUser sysUser1=sysUserMapper.findByUsername(sysUser.getUserName());
        if(sysUser1==null){
            throw new BusinessException(401,"该用户不存在");
        }
        if(!passwordEncoder.matches(sysUser.getPassWord(),sysUser1.getPassWord())){
            throw new BusinessException(401,"密码输入错误");
        }
        return sysUser1;
    }
    public void update(String username, ChangePassWordRequest request) {

        SysUser sysUser1=sysUserMapper.findByUsername(username);

       boolean flag =passwordEncoder.matches(request.getOldPassword(),sysUser1.getPassWord());
       if(!flag){
           throw new BusinessException(400,"密码错误");
       }
//       Bcrypt 技术
       String encode = passwordEncoder.encode(request.getNewPassword());
       sysUserMapper.updatePassword(username,encode);

    }
}

