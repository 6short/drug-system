package cn.bugstack.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "login-reg.jwt")
@Data
public class JwtProperties {

    /**
     * 管理员生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 用户生成jwt令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

    /**
     * 药剂师生成jwt令牌相关配置
     */
    private String doctorSecretKey;
    private long doctorTtl;
    private String doctorTokenName;

}
