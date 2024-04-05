package com.gxyos.demo.entity;


import com.gxyos.demo.mapper.SerMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class OverViewData {
    @Autowired
    SerMapper mapper;
    private int serNum;
    private int onlineSer;
    private int shutdownSer;

    private int[] onlineRate ;
    private int[] allData ;

    public int getSerNum() {
        return serNum;
    }

    public void setSerNum(int serNum) {
        this.serNum = serNum;
    }

    public int getOnlineSer() {
        return onlineSer;
    }

    public void setOnlineSer(int onlineSer) {
        this.onlineSer = onlineSer;
    }

    public int getShutdownSer() {
        return shutdownSer;
    }

    public void setShutdownSer(int shutdownSer) {
        this.shutdownSer = shutdownSer;
    }

    public int[] getOnlineRate() {
        return onlineRate;
    }

    public void setOnlineRate(int[] onlineRate) {
        this.onlineRate = onlineRate;
    }

    public int[] getAllData() {
        return allData;
    }

    public void setAllData(int[] allData) {
        this.allData = allData;
    }



}
