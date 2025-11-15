package cn.bugstack.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminpwdDTO {

    private String oldpwd;
    private String newpwd;
}
