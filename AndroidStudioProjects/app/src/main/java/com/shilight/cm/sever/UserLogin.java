package com.shilight.cm.sever;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.shilight.cm.Http;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class UserLogin {
   static String TAG = "userLogin class";
    private static final String HOSTS = "https://api-cm.shilight.cn" ;

    public static boolean isLogin(Context mContext){
        SharedPreferences mPref = mContext.getSharedPreferences("CONF",Activity.MODE_PRIVATE);
        String  user = mPref.getString("userid","");
        Log.i(TAG, user);
        return !user.equals("");
    }

    public static String get_user(Context mContext){
        SharedPreferences mPref = mContext.getSharedPreferences("CONF",Activity.MODE_PRIVATE);
        String  user = mPref.getString("userid","");
        return user;
    }


    public static int login(Context mContext,String user,String password) throws JSONException {

        Http mHttp = new Http();
        String Result =  mHttp.POST(HOSTS+"/user/signin","userid=" + user+ "&password=" + password);
        JSONObject js = new JSONObject(Result);
        if(js.getInt("result")==ResultCode.SUCCEED){
            SharedPreferences.Editor mPref = mContext.getSharedPreferences("CONF",Activity.MODE_PRIVATE).edit();
            mPref.putString("userid",js.getJSONObject("data").getJSONObject("data").getString("userid"));
            mPref.putString("password",password);
            mPref.putString("name",js.getJSONObject("data").getJSONObject("data").getString("name"));
            mPref.putString("phone",js.getJSONObject("data").getJSONObject("data").getString("phone"));
            mPref.commit();
          //  mPref.putString("industry",js.getJSONObject("data").getJSONObject("data").getString("industry"));

        }
        return js.getInt("result");
    }


    public static int bangding(Context mContext,String serName,String serUid) throws JSONException{

        Http mHttp = new Http();
        String Result =  mHttp.POST(HOSTS+"/ser/bangding","userid=" + get_user(mContext) + "&serName=" + serName+ "&serUid=" + serUid);
        JSONObject js = new JSONObject(Result);
        if(js.getInt("result")==ResultCode.SUCCEED){
            return ResultCode.SUCCEED;
        }
        return js.getInt("result");

    }



    public static int signup(Context mContext ,String user,String password,String phone,String name,String industry) throws JSONException {

        Http mHttp = new Http();
        String Result =  mHttp.POST(HOSTS + "/user/singup",
                "userid=" + user+ "&password=" + password +
                "&name=" + name + "&phone=" + phone +
                "&industry=" + industry);

        JSONObject js = new JSONObject(Result);
        if(js.getInt("result")==ResultCode.SUCCEED){
            SharedPreferences.Editor mPref = mContext.getSharedPreferences("CONF",Activity.MODE_PRIVATE).edit();
            mPref.putString("userid",js.getJSONObject("data").getJSONObject("data").getString("userid"));
            mPref.putString("password",js.getJSONObject("data").getJSONObject("data").getString("password"));
            mPref.putString("name",js.getJSONObject("data").getJSONObject("data").getString("name"));
            mPref.putString("phone",js.getJSONObject("data").getJSONObject("data").getString("phone"));
            mPref.putString("industry",js.getJSONObject("data").getJSONObject("data").getString("industry"));
            mPref.commit();

        }
        return js.getInt("result");
    }








}
