package com.study.student.controller;

import com.study.student.config.PasswordConfig;
import com.study.student.conmon.ApiResponse;
import com.study.student.dto.ChangePassWordRequest;
import com.study.student.entity.SysUser;
import com.study.student.exception.BusinessException;
import com.study.student.service.SysUserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
     public final SysUserService sysUserService;
     public AuthController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
     }

     @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody SysUser sysUser) {
         sysUserService.register(sysUser);
         return ResponseEntity.status(201).body(ApiResponse.success(201,null,null));
    }
//    像数据库提交 数据 而不用get
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@Valid @RequestBody SysUser sysUser, HttpSession session) {

         SysUser user =sysUserService.login(sysUser);
//       session保存防止再次刷新完后不知道是谁
         session.setAttribute("sysUserId", user.getId());
         session.setAttribute("sysUserName", user.getUserName());
         session.setAttribute("sysUserRole", user.getRole());

         return ResponseEntity.ok(ApiResponse.success(200,null,null));
    }

//    验证 Session 到底有没有真的记住登录状 类似cookie 找到对应的httpSession JSESSIONID
//    得到
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Map<String,Object>>> me(HttpSession session) {

         Map<String,Object> map = new HashMap<>();
         map.put("sysUserId",session.getAttribute("sysUserId"));
         map.put("sysUserName",session.getAttribute("sysUserName"));
         map.put("sysUserRole",session.getAttribute("sysUserRole"));

         return ResponseEntity.ok(ApiResponse.success(200,"获取成功",map));
    }

   @PostMapping("/layout")
//   提交操作
    public ResponseEntity<ApiResponse<Void>> layout(HttpSession session) {
//         让整个session失效
         session.invalidate();
         return ResponseEntity.ok(ApiResponse.success(200,null,null));
    }
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePassWordRequest changePassWordRequest, HttpSession session) {
                //存入的时候是Object 所以做一个强转
               String username=(String) session.getAttribute("sysUserName");
               sysUserService.update(username,changePassWordRequest);
               session.invalidate();
               return  ResponseEntity.ok(ApiResponse.success(200,"密码已修改，请重新登入",null));
    }

}
