package com.shilight.cm.ui.dashboard;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.LinkedList;
import java.util.List;

import com.qmuiteam.qmui.widget.pullLayout.QMUIPullRefreshView;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.shilight.cm.DetailActivity;
import com.shilight.cm.Http;
import com.shilight.cm.Login;
import com.shilight.cm.Logindata;
import com.shilight.cm.R;
import com.shilight.cm.Sever;
import com.shilight.cm.databinding.FragmentDashboardBinding;
import com.shilight.cm.server_adapter;
import com.shilight.cm.sever.ResultCode;
import com.shilight.cm.sever.UserLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashboardFragment extends Fragment {
    private server_adapter mAdapter = null;
    private ListView list_ser;
    private List<Sever> mData = null;

    private FragmentDashboardBinding binding;
    private handler mHandler = new handler();
    ProgressBar mp ;
    ImageView mImg;
    Button login_button;
    QMUIPullRefreshLayout qmuiPullRefreshLayout;
    private static final String TAG = "DS";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        list_ser = root.findViewById(R.id.ser_list);
        mp = root.findViewById(R.id.pb);
        login_button = root.findViewById(R.id.login_butt);
        mImg = root.findViewById(R.id.add);
        qmuiPullRefreshLayout = root.findViewById(R.id.pullup);

        mImg.setOnClickListener(view -> {

            AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
            builder3.setTitle("添加服务器");
            View my_view = inflater.inflate(R.layout.mydialog,null,false) ;
            //my.setBackgroundResource(R.drawable.yuanjiao_input_th);
            builder3.setView(my_view);
            EditText serName,serUid;
            serUid = my_view.findViewById(R.id.serUid);
            serName = my_view.findViewById(R.id.ser_name);

            builder3.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    if(serName.getText().toString().equals("")||serUid.getText().toString().equals("")){

                        Toast.makeText(getActivity(),"检查输入内容",Toast.LENGTH_SHORT).show();
                    }else{

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                               try {
                                   int code = UserLogin.bangding(getActivity(),serName.getText().toString(),serUid.getText().toString());
                                   Message msg = Message.obtain();
                                   msg.obj = code;
                                   msg.what = 9 ;
                                   mHandler.sendMessage(msg);

                               }catch (JSONException ignore){}


                            }
                        }).start();


                    }






                }
            });
            builder3.show();
        });

        list_ser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ;
                Log.i(TAG, "onItemClick: "+((TextView)view.findViewById(R.id.ser_name)).getText());
                Intent  go = new Intent();
                go.putExtra("serName",((TextView)view.findViewById(R.id.ser_name)).getText().toString());
                go.setClass(getActivity(), DetailActivity.class);
                startActivity(go);
            }
        });

        if(UserLogin.isLogin(getActivity())){


            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Sever> Data = new LinkedList<Sever>();

                    Http my = new Http();
                    String ser_data =  my.POST("https://api-cm.shilight.cn/ser/per/list","user="+UserLogin.get_user(getActivity()));
                    try {
                        JSONObject  js = new JSONObject(ser_data);
                        JSONArray list = js.getJSONObject("data").getJSONArray("data");
                        for(int i = 0;i<list.length();i++){
                            Data.add(new Sever(list.getJSONObject(i).getString("uid"),Integer.parseInt(list.getJSONObject(i).getString("osType")),
                                    (int)list.getJSONObject(i).getDouble("cpuUsed"),(int)list.getJSONObject(i).getDouble("ramPercent")));
                        }
                    }catch (JSONException ignore){
                    }
                    try {
                        Thread.sleep(500);
                    }catch (Exception ignore){
                    }
                    Message msg = Message.obtain();
                    msg.obj = Data;
                    msg.what = 6;
                    mHandler.sendMessage(msg);
                }
            }).start();

        }else{


            login_button.setVisibility(View.VISIBLE);
            mp.setVisibility(View.GONE);


        }

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent();
                go.setClass(getActivity(), Login.class);
                startActivity(go);
            }
        });



        qmuiPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Sever> Data = new LinkedList<Sever>();

                        Http my = new Http();
                        String ser_data =  my.POST("https://api-cm.shilight.cn/ser/per/list","user="+UserLogin.get_user(getActivity()));
                        try {
                            JSONObject  js = new JSONObject(ser_data);
                            JSONArray list = js.getJSONObject("data").getJSONArray("data");
                            for(int i = 0;i<list.length();i++){
                                Data.add(new Sever(list.getJSONObject(i).getString("uid"),Integer.parseInt(list.getJSONObject(i).getString("osType")),
                                        (int)list.getJSONObject(i).getDouble("cpuUsed"),(int)list.getJSONObject(i).getDouble("ramPercent")));
                            }
                        }catch (JSONException ignore){
                        }
                        try {
                            Thread.sleep(500);
                        }catch (Exception ignore){
                        }
                        Message msg = Message.obtain();
                        msg.obj = Data;
                        msg.what = 8;
                        mHandler.sendMessage(msg);
                    }
                }).start();


            }
        });




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class handler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.e("1","666");
            switch (msg.what){
                case 200 :break;
                case 301 :Toast.makeText(getActivity(),"登录失败",Toast.LENGTH_SHORT).show();break;
                case 6  :
                    Log.i("1","666");
                    list_ser.setAdapter(new server_adapter((LinkedList<Sever>) msg.obj, getActivity()));
                    list_ser.setVisibility(View.VISIBLE);
                    mp.setVisibility(View.GONE);
                    break;
                case 9:
                    if (((int)msg.obj)== ResultCode.SUCCEED){
                        Toast.makeText(getActivity(),"绑定成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"绑定失败！",Toast.LENGTH_SHORT).show();

                    }break;
                case 8:
                    Log.i("1","666");
                    list_ser.setAdapter(new server_adapter((LinkedList<Sever>) msg.obj, getActivity()));
                    list_ser.setVisibility(View.VISIBLE);
                    mp.setVisibility(View.GONE);
                    qmuiPullRefreshLayout.finishRefresh();
                    break;
            }
        }



    }

}


