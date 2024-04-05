package com.gxyos.demo.controller;

import com.gxyos.demo.entity.OverViewData;
import com.gxyos.demo.entity.Result;
import com.gxyos.demo.entity.SeverState;
import com.gxyos.demo.entity.ser_login;
import com.gxyos.demo.mapper.SerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeverController {
    @Autowired
    SerMapper mapper;
    @PostMapping("/ser/register")
    public Result add_ser(String uid){
        mapper.add_ser(uid);
        return Result.ok().setDat("finish");
    }

    @PostMapping("/ser/upstate")
    public Result getSeverState(SeverState mSeverState){
        if(mapper.update(mSeverState)==1){
            return Result.ok().setDat("ok");
        }else {
            return Result.error().setDat("error");
        }
    }

    @PostMapping("/upsafety")
    public Result getSeverSafety(){
        return Result.ok().setDat("");
    }

    @PostMapping("ser/per/list")
    public Result get_user_per(String user){
       return Result.ok().setDat(mapper.get_state(user));
    }

    @PostMapping("ser/login/data")
    public Result up_login_data(ser_login ser_login){
        if(mapper.insert_login(ser_login)==1){
            return Result.ok().setDat(1);
        }else {
            return Result.error().setDat('0');
        }
    }



    @GetMapping ("ser/login/data")
    public Result get_ser_login(String serUid){
        return Result.ok().setDat(mapper.get_login_data(serUid));
    }



    @GetMapping ("ser/detail/get")
    public Result get_ser_detail(String serUid){
        return Result.ok().setDat(mapper.get_ser_detail(serUid));
    }



    @PostMapping("ser/bangding")
    public Result banding_ser(String userid,String serUid,String serName){
        return Result.ok().setDat(mapper.bangding_ser( serUid, userid, serName));
    }



    @PostMapping("ser/Overview")
    public Result getSerCount(String userid){

        OverViewData overViewData = new OverViewData();
        overViewData.setSerNum(mapper.get_ser_count(userid));
        overViewData.setOnlineSer(getSerOnline(userid));
        overViewData.setShutdownSer(overViewData.getSerNum()-overViewData.getOnlineSer());

        int[] a = new int[4];
        a[0] = getLoadHigh(userid);
        a[1] = overViewData.getShutdownSer();
        a[2] = getYDDLcount(userid);
        a[3] = getBugCount(userid);
        overViewData.setAllData(a);
        int[] b = new int[7];
        b[0] = 12;b[1] = 11;b[2] = 8;
        b[3] = 12;b[4] = 11;b[5] = 8;b[6] = 10;
        overViewData.setOnlineRate(b);
        return Result.ok().setDat(overViewData);
    }

    public int getSerOnline(String userid){
        int count = 0;
        List<SeverState> allser =  mapper.get_state(userid);
        for(int i = 0;i<allser.size();i++){
            if(System.currentTimeMillis()/1000 - allser.get(i).getTime() < 60){
                count ++;
            }
        }
        System.out.println(count);
        return count;
    }

    public int getYDDLcount(String userid){
        int count = 0;
        List<SeverState> allser =  mapper.get_state(userid);
        for(int i = 0;i<allser.size();i++){
            List<ser_login> ser_logins = mapper.get_login_data(allser.get(i).getUid());
            for(int j = 0;j<ser_logins.size();j++){
                if(ser_logins.get(j).getState()!=0){
                    count ++;
                }
            }
        }
        return count;
    }

    public int getLoadHigh(String userid){
        int count = 0;
        List<SeverState> allser =  mapper.get_state(userid);
        for(int i = 0;i<allser.size();i++){

            if(allser.get(i).getRamPercent() >= 80||allser.get(i).getCpuUsed()>=70){
                count ++;
            }
        }
        System.out.println(count);
        return count;
    }

    public int getBugCount(String user){

        return 2;
    }





}
