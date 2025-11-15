package cn.bugstack.entity;

import cn.bugstack.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;//用户id
    private String phone;//电话号码
    private String password;//密码
    private String email;//邮箱
    private String icon;//头像
    private Gender gender;//性别
    private LocalDateTime createTime;

}
