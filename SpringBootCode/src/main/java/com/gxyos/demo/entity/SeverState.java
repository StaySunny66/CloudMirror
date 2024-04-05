package com.gxyos.demo.entity;

public class SeverState {
    private String uid ;
    private double coreNum ;
    private double cpuUsed;
    private double ramAll;
    private double ramUsed;
    private double ramPercent;
    private String osType;
    private long time;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getCoreNum() {
        return coreNum;
    }

    public void setCoreNum(double coreNum) {
        this.coreNum = coreNum;
    }

    public double getCpuUsed() {
        return cpuUsed;
    }

    public void setCpuUsed(double cpuUsed) {
        this.cpuUsed = cpuUsed;
    }

    public double getRamAll() {
        return ramAll;
    }

    public void setRamAll(double ramAll) {
        this.ramAll = ramAll;
    }

    public double getRamUsed() {
        return ramUsed;
    }

    public void setRamUsed(double ramUsed) {
        this.ramUsed = ramUsed;
    }

    public double getRamPercent() {
        return ramPercent;
    }

    public void setRamPercent(double ramPercent) {
        this.ramPercent = ramPercent;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
