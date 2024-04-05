package com.gxyos.demo.controller;

import com.gxyos.demo.entity.CveData;
import com.gxyos.demo.entity.Result;
import com.gxyos.demo.mapper.SerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class SafeController {
    @Autowired
    SerMapper mapper;
    @PostMapping("Safe/bySer")
    public Result getBug(String osType){
        List<CveData> data;
        if(osType.equals("1")){
            data  =  mapper.getCveList("%Linux%");
        }else {
            data =  mapper.getCveList("%Windows%");
        }
        return Result.ok().setDat(data);
    }

    @PostMapping("Safe/search")
    public Result getDetailBug(String mes){
        List<CveData> data;
        data  =  mapper.getCveList("%"+mes+"%");
        return Result.ok().setDat(data);
    }

    @PostMapping("Safe/hyid")
    public Result getDetailBugById(String id){
        int[] a = getTONGJI(Integer.parseInt(id));
        Result result = Result.ok().setDat(a);
        result.data.put("cvelist",getcve(Integer.parseInt(id)));
        double[] aa = new double[2];
        aa[0] = a[4];  ///今年行业漏洞
        aa[1] = 70 + (1000-a[4])/1000.0*30;      ///安全评分
        result.data.put("core",aa);
        return result;
    }

    public int[] getTONGJI(int hy){
        String hydata [][] = new String[7][];
        hydata[0] = new String[]{"%Windows%", "%Mac os%", "%office%", "%Linux%", "%Adobe%"};
        hydata[1] = new String[]{"%Mysql%", "%Nginx%", "%Apache%", "%Linux%", "%sqlsever%"};
        hydata[2] = new String[]{"%hadoop%", "%ZooKeeper%", "%MapReduce%", "%Spark%", "%Windows%"};
        hydata[3] = new String[]{"%Opencv%", "%Linux%", "%TensorFlow%", "%Caffe%", "%Theano%"};
        hydata[4] = new String[]{"%IOT%", "%MQTT%", "%Linux%", "%OpenWrt%", "%Keil%"};
        hydata[5] = new String[]{"%HTTP%", "%HTTPS%", "%TCP%", "%阿三士大夫%", "%啊沙发上%"};
        hydata[6] = new String[]{"%vmware%", "%KVM%", "%Centos%", "%Apache%", "%Nginx%"};
        String year[] = new String[5];
        year[0] = "%2018%";
        year[1] = "%2019%";
        year[2] = "%2020%";
        year[3] = "%2021%";
        year[4] = "%2022%";
        int dat[] = new int[5];
        for(int i = 0;i<5;i++){
            dat[i] = mapper.get_count(year[i],hydata[hy][0],hydata[hy][1],hydata[hy][2],hydata[hy][3],hydata[hy][4]);
        }
        return dat;
    }

    public List<CveData> getcve(int hy){
        String hydata [][] = new String[7][];
        hydata[0] = new String[]{"%Windows%", "%Mac os%", "%office%", "%Linux%", "%Adobe%"};
        hydata[1] = new String[]{"%Mysql%", "%Nginx%", "%Apache%", "%Linux%", "%sqlsever%"};
        hydata[2] = new String[]{"%hadoop%", "%ZooKeeper%", "%MapReduce%", "%Spark%", "%Windows%"};
        hydata[3] = new String[]{"%Opencv%", "%Linux%", "%TensorFlow%", "%Caffe%", "%Theano%"};
        hydata[4] = new String[]{"%IOT%", "%MQTT%", "%Linux%", "%OpenWrt%", "%Keil%"};
        hydata[5] = new String[]{"%HTTP%", "%HTTPS%", "%TCP%", "%阿三士大夫%", "%啊沙发上%"};
        hydata[6] = new String[]{"%vmware%", "%KVM%", "%Centos%", "%Apache%", "%Nginx%"};
        List<CveData> data;
        data = mapper.get_new(hydata[hy][0],hydata[hy][1],hydata[hy][2],hydata[hy][3],hydata[hy][4]);
        return data;
    }

}
