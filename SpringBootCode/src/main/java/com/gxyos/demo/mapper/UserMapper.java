package com.gxyos.demo.mapper;

import com.gxyos.demo.entity.SignUp;
import com.gxyos.demo.entity.UserInf;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    // 查询语句
    @Select("select * from user")
    List<UserInf> user_list();

    // 插入实现注册
    @Insert("INSERT INTO user VALUES (#{userid},#{password},#{name},#{phone},#{industry})")
    public int insert(SignUp user);

    // 实现登录验证操作
    @Select("select * from user where userid = #{userid} AND password = #{password}")
    public UserInf check(String userid, String password);







}
