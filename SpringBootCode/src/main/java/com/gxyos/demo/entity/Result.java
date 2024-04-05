package com.gxyos.demo.entity;

import javax.xml.crypto.Data;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Result {
    private int result;
    public Map<String,Object> data = new HashMap<>();


    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public static Result ok(){
      Result r = new Result();
      r.result = ResultCode.SUCCEED;
      return r;
    }

    public static Result error(){
        Result r = new Result();
        r.result = ResultCode.FALSE;
        return r;
    }

    public static Result code(int code){
        Result r = new Result();
        r.result = code;
        return r;
    }

    public Result setDat(Object obj){
        this.data.put("data",obj);
        return this;

    }


}
