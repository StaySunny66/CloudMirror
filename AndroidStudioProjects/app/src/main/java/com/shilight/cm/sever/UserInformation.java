package com.shilight.cm.sever;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.dynamicanimation.animation.SpringAnimation;

public class UserInformation {
    private String name;
    private String pass;
    private String userid;
    private String phone;
    private String industry;



    public static UserInformation getInformation(Context mContext){
        SharedPreferences mPref = mContext.getSharedPreferences("CONF", Activity.MODE_PRIVATE);
        UserInformation r = new UserInformation();
        r.userid  = mPref.getString("userid","");
        r.pass  = mPref.getString("pass","");
        r.name  = mPref.getString("name","");
        r.phone  = mPref.getString("phone","");
        r.industry  = mPref.getString("industry","");
        return r;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
