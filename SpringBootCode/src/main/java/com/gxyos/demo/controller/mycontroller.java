package com.gxyos.demo.controller;

import com.gxyos.demo.entity.Result;
import com.gxyos.demo.entity.ResultCode;
import com.gxyos.demo.entity.Server;
import com.gxyos.demo.entity.UserInf;
import com.gxyos.demo.mapper.SerMapper;
import com.gxyos.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class mycontroller {
    @Autowired
    private UserMapper user;
    @Autowired
    private SerMapper Ser;

    @GetMapping("/hello")
    public String hello(){
        return "get 方法";
    }

    @PostMapping("/hello")
    public String hi(){
        return "post 方法";
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(String name){
        return "test!"+name;
    }

    @GetMapping("/user/list")
    public List get_all_user(){

        List<UserInf>  list = user.user_list();
        System.out.println(list);
        return  list;

    }


    @PostMapping ("/server/list")
    public List get_user_ser(String user_id){
        System.out.println(user_id);
        List<Server> list = Ser.getSerList(user_id);
        return  list;

    }

    @PostMapping ("/server/add")
    public Result severAdd(Server sever){
        try {

            int result = Ser.serverAdd(sever);
            if(result==1) {
                return Result.ok().setDat("finish");
            }else if(result==0){
                return Result.error().setDat("error");
            }else {
                return Result.code(ResultCode.UNKNOWN_ERROR).setDat("unknown err");
            }
        }catch (Exception e){

            return Result.code(ResultCode.SERVER_EXISTED).setDat("unknown err");

        }
    }

    @PostMapping("/server/register")
    public Result serverRegister(String user_id,String ser_UID){

        try{
           int  result =  Ser.serverRegister(user_id,ser_UID);
           if(result==1) {
                return Result.ok().setDat("finish");
           }else if(result==0){
                return Result.error().setDat("error");
           }else {
                return Result.code(ResultCode.UNKNOWN_ERROR).setDat("unknown err");
           }
        }catch (Exception ignore){
            return Result.code(ResultCode.UNKNOWN_ERROR).setDat("unknown err");

        }

    }



}
