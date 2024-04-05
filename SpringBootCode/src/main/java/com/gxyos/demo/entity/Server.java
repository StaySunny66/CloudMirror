package com.gxyos.demo.entity;

public class Server {

    private String ser_UID;
    private String name;
    private String ip;
    private String port;
    private String user_id;
    private String type;
    private  String others;

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
}
