package com.shilight.cm.viewdata;

import static com.shilight.cm.sever.UserLogin.get_user;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.shilight.cm.Http;

import org.json.JSONException;
import org.json.JSONObject;

public class OverViewData {
    private static final String HOSTS = "https://api-cm.shilight.cn" ;
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

    // 获取系统中存储的数据
    public static OverViewData getData(Context mContext){
        OverViewData r = new OverViewData();
        SharedPreferences m =  mContext.getSharedPreferences("CONF", Activity.MODE_PRIVATE);
        return r;
    }

    // 重新获取数据（网络获取） 不可主线程
    public static OverViewData getDataOnline(Context mContext){
        OverViewData r = new OverViewData();
        Http http = new Http();
        String s = http.POST(HOSTS+"/ser/Overview","userid="+get_user(mContext));
        try {
            JSONObject m = new JSONObject(s);
            JSONObject data = m.getJSONObject("data").getJSONObject("data");
            int[] a = new int[4];
            for (int i=0;i<4;i++){
                a[i] =  data.getJSONArray("allData").getInt(i);
            }
            r.setAllData(a);
            int[] b = new int[7];
            for (int j=0;j<7;j++){
                b[j] =  data.getJSONArray("onlineRate").getInt(j);
            }
            r.setOnlineRate(b);


            r.setSerNum(data.getInt("serNum"));
            r.setShutdownSer(data.getInt("shutdownSer"));
            r.setOnlineSer(data.getInt("onlineSer"));
        }catch (JSONException ignore){}

        return r;
    }


}
