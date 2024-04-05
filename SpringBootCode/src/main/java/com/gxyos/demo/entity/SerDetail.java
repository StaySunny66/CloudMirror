package com.gxyos.demo.entity;

public class SerDetail {

    private String ser_UID;
    private String name;
    private String ip;
    private String port;
    private String user_id;
    private String type;
    private  String others;
    private String uid ;
    private double coreNum ;
    private double cpuUsed;
    private double ramAll;
    private double ramUsed;
    private double ramPercent;
    private String osType;
    private long time;

    public String getSer_UID() {
        return ser_UID;
    }

    public void setSer_UID(String ser_UID) {
        this.ser_UID = ser_UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
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
