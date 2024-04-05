package com.shilight.cm;

public class Logindata {
    private String ip;
    private String time;
    private String address;

    public Logindata(String ip, String time, String address) {
        this.ip = ip;
        this.time = time;
        this.address = address;
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
}
