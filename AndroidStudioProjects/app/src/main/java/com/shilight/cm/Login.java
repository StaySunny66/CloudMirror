package com.shilight.cm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.shilight.cm.sever.ResultCode;
import com.shilight.cm.sever.UserLogin;

import org.json.JSONException;

public class Login extends AppCompatActivity {
    Callback mCallback = new Callback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        EditText user = findViewById(R.id.LOGIN_USER);
        EditText pass = findViewById(R.id.LOGIN_PASS);

        ((Button)findViewById(R.id.LOGIN)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                           int res =  UserLogin.login(Login.this,user.getText().toString(),pass.getText().toString());
                           Message r = Message.obtain();
                           r.what = res;
                           mCallback.sendMessage(r);
                           Log.i("login" ,"start");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });

        ((Button)findViewById(R.id.TO_ADD)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent();
                go.setClass(Login.this, zc.class);
                startActivity(go);
            }
        });
    }
    class Callback extends Handler{

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == ResultCode.SUCCEED){
                Log.i("登录界面","LOGIN successed" );
                finish();
            }else{
                Log.i("登录界面","LOGIN Error" + msg.what );
            }
        }
    }

}