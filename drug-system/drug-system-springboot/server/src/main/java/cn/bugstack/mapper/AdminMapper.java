package cn.bugstack.mapper;

import cn.bugstack.annotation.AutoFill;
import cn.bugstack.entity.Admin;
import cn.bugstack.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {

    @Select("select * from admin where email = #{email}")
    Admin getByEmail(String email);


    @Insert("insert into admin(password,email,username) values "+
    "(#{password},#{email},#{username})")
    @AutoFill(value= OperationType.INSERT)
    void regAdmin(Admin admin);

    @Update("update admin set password=#{newpwd} where ")
    void fixpwd(String newpwd);

}
