package com.shilight.cm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shilight.cm.sever.Safe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "aa";
    String serUid = "";
    Callback mCallback = new Callback();
    TextView ser_core;
    TextView ser_cpu;
    TextView ser_ip;
    TextView ser_name;
    TextView os_name;
    TextView ser_ram;
    TextView ser_ram_all;
    ListView login;
    ListView Cve;
    ProgressBar mp;
    ProgressBar cve_p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();
        if     (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
         ser_core=findViewById(R.id.ser_core);
         ser_cpu=findViewById(R.id.ser_cpu);
         ser_ip=findViewById(R.id.ser_ip);
         ser_name=findViewById(R.id.ser_name);
         os_name=findViewById(R.id.ser_os);
         ser_ram=findViewById(R.id.ser_ram);
         ser_ram_all=findViewById(R.id.ser_ram_all);
         login = findViewById(R.id.login_list);
         mp=findViewById(R.id.detail_pro);
         Cve = findViewById(R.id.list_cve);
         cve_p = findViewById(R.id.cve_pro);

        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Log.i(TAG, "onCreate: " +this.getIntent().getExtras().getString("serName"));
        serUid = this.getIntent().getExtras().getString("serName");

        new Thread(new Runnable() {
            @Override
            public void run() {

                HTTPGET mHttp = new HTTPGET();
                String m = mHttp.GET("https://api-cm.shilight.cn/ser/detail/get?serUid="+serUid);
                Log.i(TAG, "run: "+m);

                try {
                    JSONObject S = new JSONObject(m);
                    Message M = Message.obtain();
                    M.what = 0;
                    M.obj = S;
                    mCallback.sendMessage(M);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String login = mHttp.GET("https://api-cm.shilight.cn/ser/login/data?serUid="+serUid);
                Log.i(TAG, "run: "+"https://api-cm.shilight.cn/ser/login/data?serUid="+serUid);

                try {
                    JSONObject SS = new JSONObject(login);
                    Message ss = Message.obtain();
                    ss.what = 1;
                    ss.obj = SS;
                    mCallback.sendMessage(ss);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    void getCve(String type){
        new Thread(new Runnable() {
            @Override
            public void run() {

                LinkedList<CveData>  data= Safe.getOsCve(type);
                if(data!=null){
                    Message m = Message.obtain();
                    m.what = 10;
                    m.obj = data;
                    mCallback.sendMessage(m);

                }

            }
        }).start();

    }

    class Callback extends Handler{

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                try {
                   JSONObject m =  ((JSONObject)msg.obj).getJSONObject("data").getJSONObject("data");
                    Log.i(TAG, "handleMessage: "+m.getDouble("coreNum"));
                    ser_core.setText("核心数: "+String.valueOf((int) m.getDouble("coreNum"))+"核");
                    ser_cpu.setText("CPU使用: "+String.valueOf(m.getDouble("cpuUsed"))+"%");
                    ser_ip.setText(m.getString("ip"));
                    ser_name.setText(m.getString("name"));
                    ser_ram.setText("已用内存: "+String.format("%1.2f", (m.getDouble("ramUsed")/1024.0/1024.0/1024.0))+" GB");
                    ser_ram_all.setText("全部内存: "+String.format("%1.2f", (m.getDouble("ramAll")/1024.0/1024.0/1024.0))+" GB");
                    if(m.getString("type").equals("1")){
                        os_name.setText("操作系统: Linux");
                        getCve("1");
                    }else{
                        os_name.setText("操作系统: Windows");
                        getCve("0");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if(msg.what==1){
                List<Logindata>  data = null;
                Log.i(TAG, "handleMessage:eeeee "+((JSONObject)msg.obj));

                try {
                    data = new LinkedList<Logindata>();
                    JSONArray m =  ((JSONObject)msg.obj).getJSONObject("data").getJSONArray("data");
                    for(int i = 0;i < m.length();i++){
                        data.add(new Logindata(m.getJSONObject(i).getString("ip"),m.getJSONObject(i).getString("time"),m.getJSONObject(i).getString("address")));
                    }
                    login.setAdapter(new LoginAdapter((LinkedList<Logindata>)data,DetailActivity.this));
                    login.setVisibility(View.VISIBLE);
                    mp.setVisibility(View.GONE);

                }catch (JSONException ignore){

                }
            }

            if(msg.what==10){

                Cve.setAdapter(new CveDataAdapter((LinkedList<CveData>) msg.obj,DetailActivity.this));
                cve_p.setVisibility(View.GONE);
                Cve.setVisibility(View.VISIBLE);




            }

        }
    }
}