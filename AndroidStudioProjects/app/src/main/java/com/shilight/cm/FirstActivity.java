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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.util.zip.GZIPOutputStream;

public class FirstActivity extends AppCompatActivity {

    Callback my = new Callback();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
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

        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        AnimationSet animationSet = new AnimationSet(true);
      /*
          Animation还有几个方法
          setFillAfter(boolean fillAfter)
          如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
          setFillBefore(boolean fillBefore)
          如果fillBefore的值为真的话，动画结束后，控件停留在动画开始的状态
          setStartOffset(long startOffset)
          设置动画控件执行动画之前等待的时间
          setRepeatCount(int repeatCount)
          设置动画重复执行的次数
       */
        Animation zhangch = new AlphaAnimation(0.0f,1.0f);
        zhangch.setDuration(1000);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                //X轴初始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //X轴移动的结束位置
                Animation.RELATIVE_TO_SELF,0.0f,
                //y轴开始位置
                Animation.RELATIVE_TO_SELF,0.4f,
                //y轴移动后的结束位置
                Animation.RELATIVE_TO_SELF,0.0f);
        //3秒完成动画
        translateAnimation.setDuration(600);
        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        animationSet.setFillAfter(true);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(zhangch);

        ((TextView)findViewById(R.id.tittle)).startAnimation(animationSet);

        AnimationSet animationSet2 = new AnimationSet(true);
        animationSet2.addAnimation(zhangch);


        ((TextView)findViewById(R.id.textView2)).startAnimation(animationSet2);
        ((TextView)findViewById(R.id.textView3)).startAnimation(animationSet2);


        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i<2){

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;

                }
                my.sendMessage(Message.obtain());

            }
        }).start();


    }
    class Callback extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Intent go = new Intent();
            go.setClass(FirstActivity.this,MainActivity.class);
            startActivity(go);
            finish();
        }
    }

}