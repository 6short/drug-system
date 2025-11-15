package cn.bugstack.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//账号密码登陆（账号是手机号
public class AdminLoginDTO {

    private String email;

    private String password;

}
