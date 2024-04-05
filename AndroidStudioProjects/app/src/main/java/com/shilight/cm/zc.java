package com.shilight.cm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shilight.cm.sever.ResultCode;
import com.shilight.cm.sever.UserLogin;

import org.json.JSONException;

public class zc extends AppCompatActivity {

    EditText pass,user,pass2,phone,name;
    callBack mCallBack = new callBack();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zc);

        getSupportActionBar().hide();
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

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        pass2 = findViewById(R.id.pass2);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.email);

        ((Button)findViewById(R.id.ADD)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    int resutl = 10;
                    @Override
                    public void run() {
                        try {
                           resutl =  UserLogin.signup(zc.this,user.getText().toString(),pass.getText().toString(),phone.getText().toString(),name.getText().toString(),"null");
                           Message m = Message.obtain();
                           m.obj = resutl;
                           m.what = 1;
                           mCallBack.sendMessage(m);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Message m = Message.obtain();
                            m.obj = resutl;
                            m.what = 1;
                            mCallBack.sendMessage(m);
                        }

                    }
                }).start();
            }
        });
    }

    class callBack extends Handler{


        @Override
        public void handleMessage(@NonNull Message msg) {
            if(((int)msg.obj)== ResultCode.SUCCEED){

                Toast.makeText(zc.this,"注册成功",Toast.LENGTH_SHORT).show();
                finish();
            }else if(((int)msg.obj)== ResultCode.USER_EXIST){
                Toast.makeText(zc.this,"注册失败，用户已存在",Toast.LENGTH_SHORT).show();


            }

        }
    }
}