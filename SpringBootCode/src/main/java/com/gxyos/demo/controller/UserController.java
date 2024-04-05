package com.gxyos.demo.controller;


import com.gxyos.demo.entity.Result;
import com.gxyos.demo.entity.ResultCode;
import com.gxyos.demo.entity.SignUp;
import com.gxyos.demo.entity.UserInf;
import com.gxyos.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// 用户有关操作

@RestController
public class UserController {
     @Autowired
     private UserMapper user;
     // 注册项目
     @PostMapping("/user/singup")
     public Result singUp(SignUp S_user){
          try{
               if(user.insert(S_user)==1){
                    return Result.ok().setDat(S_user);
               }else{
                    return Result.code(ResultCode.UNKNOWN_ERROR).setDat(S_user);
               }
          }catch (Exception e) {
               return Result.error().setDat(S_user);
          }
     }

     //登录控制
     @PostMapping("user/signin")
     public Result singIn(String password,String userid){

            UserInf result =  user.check(userid,password);
            if(result == null ) return Result.error().setDat(null);
            if(result.getUserid().equals(userid)){

                  return Result.ok().setDat(result);

            }else {
                 return Result.error().setDat(result);
            }


     }

}
