package cn.bugstack.entity;

import lombok.Data;

@Data
public class AdminSignEmailDTO {

    private String email;
    private String password;
    private String code;
}
