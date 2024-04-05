package com.gxyos.demo.entity;

import com.github.kevinsawicki.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class IpDetail {

    public static String getAddress(String ip){

        Map data = new HashMap();
        data.put("ip", ip);
        String resp = HttpRequest.get("https://zjip.market.alicloudapi.com/lifeservice/QueryIpAddr/query").header("Authorization","APPCODE da0c551dee2c445c9d628bac23660a79").body();
        System.out.println("---------------response parameter:" + resp);

        System.out.println(resp);
        return resp;

    }


}
