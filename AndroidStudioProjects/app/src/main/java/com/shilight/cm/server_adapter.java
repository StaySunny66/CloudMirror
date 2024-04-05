package com.shilight.cm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;

public class server_adapter extends BaseAdapter {

    private LinkedList<Sever> mData;
    private Context mContext;

    public server_adapter(LinkedList<Sever> mData, Context mContext) {
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
        view = LayoutInflater.from(mContext).inflate(R.layout.server_adapter,parent,false);

        ImageView ser_img = view.findViewById(R.id.ser_img);
        TextView ser_name =  view.findViewById(R.id.ser_name);
        ProgressBar ser_cpu=  view.findViewById(R.id.ser_cpu);
        ProgressBar ser_ram=  view.findViewById(R.id.ser_ram);

        ser_cpu.setProgress(mData.get(i).getCpu());
        ser_name.setText(mData.get(i).getName());
        ser_img.setBackgroundResource(mData.get(i).getSystem());
        ser_ram.setProgress(mData.get(i).getRam());
        return view;
    }
}
