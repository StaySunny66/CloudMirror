package com.shilight.cm.sever;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;
import android.util.Log;

import com.shilight.cm.CveData;
import com.shilight.cm.Http;
import com.shilight.cm.Logindata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class Safe {

    private static final String HOSTS = "https://api-cm.shilight.cn" ;


    public static LinkedList<CveData> getOsCve(String type){

        Http m = new Http();
        String s = m.POST(HOSTS+"/Safe/bySer","osType="+type);
        try {
            JSONObject mj = new JSONObject(s);
            JSONArray data = mj.getJSONObject("data").getJSONArray("data");
            LinkedList<CveData> result = new LinkedList<CveData>();

            for(int i = 0;i < data.length();i++){
                JSONObject ss =  data.getJSONObject(i);
                result.add(new CveData(ss.getString("name"),ss.getString("status"),ss.getString("description"),ss.getString("references"),ss.getString("phase"),ss.getString("votes")));
            }
            return  result;
        }catch (JSONException ignore){}

        return null;
    }


    public static Object getSafeDat(Context mContext){


        Http m = new Http();
        String s = m.POST(HOSTS+"/Safe/hyid","id="+get_hy(mContext));
        try {
            JSONObject mj = new JSONObject(s);
            JSONArray qsData = mj.getJSONObject("data").getJSONArray("data");
            JSONArray coreData = mj.getJSONObject("data").getJSONArray("core");
            JSONArray cveList =mj.getJSONObject("data").getJSONArray("cvelist");
            SafeHyDat result = new SafeHyDat();

            double core[] = new double[2];
            for(int i = 0;i < coreData.length();i++){
                core[i] = coreData.getDouble(i);
            }

            result.setCore(core);

            int qs[] = new int[5];
            for(int i = 0;i < qsData.length();i++){
                qs[i] = qsData.getInt(i);
            }
            result.setAa(qs);

            for(int i = 0;i < cveList.length();i++){
                JSONObject ss =  cveList.getJSONObject(i);
                result.getCveData().add(new CveData(ss.getString("name"),ss.getString("status"),ss.getString("description"),ss.getString("references"),ss.getString("phase"),ss.getString("votes")));
            }

            return  result;
        }catch (JSONException ignore){}

        return null;
    }

    public static LinkedList<CveData> getSafeDetail(String type){

        Http m = new Http();

        String s = m.POST(HOSTS+"/Safe/bySer","osType="+type);
        try {
            JSONObject mj = new JSONObject(s);
            JSONArray data = mj.getJSONObject("data").getJSONArray("data");

            LinkedList<CveData> result = new LinkedList<CveData>();

            for(int i = 0;i < data.length();i++){
                JSONObject ss =  data.getJSONObject(i);
                result.add(new CveData(ss.getString("name"),ss.getString("status"),ss.getString("description"),ss.getString("references"),ss.getString("phase"),ss.getString("votes")));
            }
            return  result;
        }catch (JSONException ignore){}

        return null;
    }

    public static int set_hy(int hy ,Context mContext) {

            SharedPreferences.Editor mPref = mContext.getSharedPreferences("CONF", Activity.MODE_PRIVATE).edit();
            mPref.putInt("hy",hy);
            mPref.commit();
            //  mPref.putString("industry",js.getJSONObject("data").getJSONObject("data").getString("industry"));
            return hy;
    }

    public static int get_hy(Context mContext) {

        SharedPreferences mPref = mContext.getSharedPreferences("CONF",Activity.MODE_PRIVATE);
        return mPref.getInt("hy",1);
    }



}
