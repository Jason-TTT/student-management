package com.study.student.service;

import com.study.student.dto.ChangePassWordRequest;
import com.study.student.entity.SysUser;
import com.study.student.exception.BusinessException;
import com.study.student.mapper.SysUserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.debug("用户开始注册,username={}",sysUser.getUserName());
        SysUser sysUser1=sysUserMapper.findByUsername(sysUser.getUserName());
        if(sysUser1!=null){
            log.warn("用户已存在,username={}",sysUser1.getUserName());
            throw new BusinessException(400,"用户名已经存在");
        }
//      Bcrypt技术
        String encode = passwordEncoder.encode(sysUser.getPassWord());
        sysUser.setPassWord(encode);
        sysUser.setRole("user");
        sysUserMapper.addUser(sysUser);
        log.info("用户注册正常,username={}",sysUser.getUserName());
    }

    public SysUser login(SysUser sysUser) {
        log.debug("用户登录开始校验,username={}",sysUser.getUserName());
        SysUser sysUser1=sysUserMapper.findByUsername(sysUser.getUserName());
        if(sysUser1==null){
            log.warn("登录失败，用户不存在,username={}",sysUser.getUserName());
            throw new BusinessException(401,"该用户不存在");
        }
        if(!passwordEncoder.matches(sysUser.getPassWord(),sysUser1.getPassWord())){
            log.warn("登录失败，密码错误,username={}",sysUser.getUserName());
            throw new BusinessException(401,"密码输入错误");
        }
        log.info("登录成功,username={}",sysUser.getUserName());
        return sysUser1;
    }
    public void update(String username, ChangePassWordRequest request) {
        log.debug("用户开始修改密码,username={}",username);
        SysUser sysUser1=sysUserMapper.findByUsername(username);
        if(sysUser1==null){
            ;log.warn("用户不存在或者登录异常,username={}",username);
            throw new BusinessException(401,"用户不存在或登录异常");
        }
       boolean flag =passwordEncoder.matches(request.getOldPassword(),sysUser1.getPassWord());
       if(!flag){
           log.warn("原密码错误,username={}",username);
           throw new BusinessException(400,"密码错误");
       }
//       Bcrypt 技术
       String encode = passwordEncoder.encode(request.getNewPassword());
       sysUserMapper.updatePassword(username,encode);
       log.info("用户修改密码成功,username={}",username);
    }
}

