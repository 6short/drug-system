package cn.bugstack.controller.admin;


import cn.bugstack.dto.AdminLoginDTO;
import cn.bugstack.dto.AdminSigninDTO;
import cn.bugstack.dto.AdminpwdDTO;
import cn.bugstack.entity.Admin;
import cn.bugstack.entity.AdminSignEmailDTO;
import cn.bugstack.properties.JwtProperties;
import cn.bugstack.result.Result;
import cn.bugstack.service.AdminService;
import cn.bugstack.utils.JwtUtil;
import cn.bugstack.vo.AdminLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 管理员登录
     * @param adminLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        log.info("用户传过来的登录信息DTO:{}", adminLoginDTO);
        Admin admin = adminService.login(adminLoginDTO);

        Map<String,Object> claims = new HashMap<>();
        claims.put("adminId",admin.getId());

        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        AdminLoginVO adminLoginVO = AdminLoginVO.builder()
                .id(admin.getId())
                .email(admin.getEmail())
                .token(token)
                .build();
return Result.success(adminLoginVO);
    }

    //发送验证码（电话号码版，不采用)
    @PostMapping("code")
    public Result sendCode(@RequestParam("phone") String phone){
        return adminService.sendCode(phone);
    }


    //管理员注册(电话号码版，不采用)
    @PostMapping("/signin")
    public  Result signin(@RequestBody AdminSigninDTO adminSigninDTO) {
           adminService.signin(adminSigninDTO);
            return Result.success();

    }

    @PostMapping("email")
    public Result sendEmail(@RequestParam("email") String email ) {
            adminService.sendEmail(email);
            return Result.success();
    }


    @PostMapping("/signemail")
    public Result signemail(@RequestBody AdminSignEmailDTO adminSignEmailDTO) {
            adminService.SignEmail(adminSignEmailDTO);
            return Result.success();
    }

    @PutMapping("fixpwd")
    public Result ifxpwd(@RequestBody AdminpwdDTO adminpwdDTO){
        adminService.fixpwd(adminpwdDTO);
        return Result.success();
    }

}
