package com.gxyos.demo.entity;

public class ser_login {
    private String serUid;
    private String ip;
    private String time;
    private String address;
    private int    state;

    public String getSerUid() {
        return serUid;
    }

    public void setSerUid(String serUid) {
        this.serUid = serUid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
