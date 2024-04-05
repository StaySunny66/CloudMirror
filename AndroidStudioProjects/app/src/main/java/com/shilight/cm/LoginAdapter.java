package com.shilight.cm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

public class LoginAdapter extends BaseAdapter {

    private LinkedList<Logindata> mData;
    private Context mContext;

    public LoginAdapter(LinkedList<Logindata> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.login_adapter,parent,false);

        ((TextView)view.findViewById(R.id.ip)).setText("IP:"+mData.get(i).getIp());
        ((TextView)view.findViewById(R.id.login_address)).setText("登录位置:"+mData.get(i).getAddress());
        ((TextView)view.findViewById(R.id.login_time)).setText("登录时间:"+mData.get(i).getTime());

        return view;
    }
}
