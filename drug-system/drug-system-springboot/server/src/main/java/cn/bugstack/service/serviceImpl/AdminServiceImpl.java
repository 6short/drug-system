package cn.bugstack.service.serviceImpl;

import cn.bugstack.constant.MessageConstant;
import cn.bugstack.dto.AdminLoginDTO;
import cn.bugstack.dto.AdminSigninDTO;
import cn.bugstack.dto.AdminpwdDTO;
import cn.bugstack.entity.Admin;
import cn.bugstack.entity.AdminSignEmailDTO;
import cn.bugstack.exception.AdminNotFoundException;
import cn.bugstack.exception.PasswordErrorException;
import cn.bugstack.exception.againException;
import cn.bugstack.mapper.AdminMapper;
import cn.bugstack.properties.JwtProperties;
import cn.bugstack.result.Result;
import cn.bugstack.service.AdminService;
import cn.bugstack.service.MailService;
import cn.bugstack.utils.JwtUtil;
import cn.bugstack.utils.RegexUtils;
import cn.bugstack.vo.AdminSignVO;
import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static cn.bugstack.constant.RedisConstants.SIGN_EAMIL_TTL;
import static cn.bugstack.constant.RedisConstants.SIGN_EMAIL_KEY;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private MailService mailService;

    @Autowired
    private AdminMapper adminMapper;

    public Admin login (AdminLoginDTO adminLoginDTO){
        String email = adminLoginDTO.getEmail();
        String password = adminLoginDTO.getPassword();

        Admin admin = adminMapper.getByEmail(email);
        if (admin == null){
            throw new AdminNotFoundException(MessageConstant.PHONE_NOT_FOUND);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if(!password.equals(admin.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        return admin;
    }

    @Override
    public Result sendCode(String phone){

        return Result.success();
    };

    @Override
    public Result sendEmail(String email){

        //1. 校验邮箱
        if (RegexUtils.isEmailInvalid(email)) {
            //2. 如果不符合，返回错误信息
            return Result.fail("手机号格式错误");
        }

        String code = RandomUtil.randomNumbers(6);

        stringRedisTemplate.opsForValue().set(
                SIGN_EMAIL_KEY+ email,
                code,
                SIGN_EAMIL_TTL,
                TimeUnit.MINUTES
        );

        mailService.sendEmailCode(email, code);
        return Result.success();
    }

    //注册新用户
    public void signin(AdminSigninDTO adminSigninDTO){

    }


    //使用邮箱注册
    public Result SignEmail(AdminSignEmailDTO adminSignEmailDTO){
            String email = adminSignEmailDTO.getEmail();
        if (RegexUtils.isEmailInvalid(email)) {
            //2. 如果不符合，返回错误信息
            return Result.fail("邮箱格式错误");
        }

        String cachecode =stringRedisTemplate.opsForValue().get(
                SIGN_EMAIL_KEY + email);
        String code = adminSignEmailDTO.getCode();
        if(code==null || !cachecode.equals(code)){
            return Result.fail("验证码错误");
        }

        Admin admin = adminMapper.getByEmail(email);

        if(admin!=null){
            //用户已存在
            throw new againException(MessageConstant.AGAIN_ADMIN);
        }

        String password = adminSignEmailDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        adminSignEmailDTO.setPassword(password);

        Admin admin1 =new Admin();
        BeanUtils.copyProperties(adminSignEmailDTO,admin1);
        admin1.setPassword(password);
        admin1.setEmail(email);
        admin1.setUsername(UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        adminMapper.regAdmin(admin1);

        //颁发令牌
        Map<String, Object> claims = new HashMap<>(); // jsonwebtoken包底层就是Map<String, Object>格式，不能修改！
        claims.put("adminId", admin1.getId());
        // 需要加个token给他，再返回响应
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        AdminSignVO adminSignVO = AdminSignVO.builder()
                .id(admin1.getId())
                .username(admin1.getUsername())
                .token(token)
                .build();
        return Result.success(adminSignVO);
    }

    public Result fixpwd(AdminpwdDTO adminpwdDTO){


        Admin
        String newpwd = adminpwdDTO.getNewpwd();
        adminMapper.fixpwd(newpwd);
        return Result.success();
    }

}
