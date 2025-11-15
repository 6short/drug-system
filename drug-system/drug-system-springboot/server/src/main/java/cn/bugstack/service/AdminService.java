package cn.bugstack.service;

import cn.bugstack.dto.AdminLoginDTO;
import cn.bugstack.dto.AdminSigninDTO;
import cn.bugstack.dto.AdminpwdDTO;
import cn.bugstack.entity.Admin;
import cn.bugstack.entity.AdminSignEmailDTO;
import cn.bugstack.result.Result;

public interface AdminService {
    Admin login(AdminLoginDTO adminLoginDTO);

    Result sendCode(String phone);

    Result sendEmail(String email);

    void signin(AdminSigninDTO adminSignDTO);

    Result SignEmail(AdminSignEmailDTO adminSignEmailDTO);

    Result fixpwd(AdminpwdDTO adminpwdDTO);

}
