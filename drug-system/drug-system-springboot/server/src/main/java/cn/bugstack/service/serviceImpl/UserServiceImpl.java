package cn.bugstack.service.serviceImpl;

import cn.bugstack.mapper.UserMapper;
import cn.bugstack.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    //根据id查询用户
    public User getUser(Integer id){
        return userMapper.getById(id);
    }
}
