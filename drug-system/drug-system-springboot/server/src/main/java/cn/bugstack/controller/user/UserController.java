package cn.bugstack.controller.user;

import cn.bugstack.properties.JwtProperties;
import cn.bugstack.result.Result;
import cn.bugstack.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;


    //通过账号（为手机号）密码登陆
   @PostMapping("/login")
   //public Result login(@RequestBody User user) {
     //  return User;
   //}

    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Integer id){
        log.info("用户id:{}", id);
        User user = userService.getUser(id);
        return Result.success(user);
    }

}
