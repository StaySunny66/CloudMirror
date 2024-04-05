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

public class CveDataAdapter extends BaseAdapter {

    private LinkedList<CveData> mData;
    private Context mContext;

    public CveDataAdapter(LinkedList<CveData> mData, Context mContext) {
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
        view = LayoutInflater.from(mContext).inflate(R.layout.cvelist,parent,false);

        ((TextView)(view.findViewById(R.id.Name))).setText(mData.get(i).getName());
        ((TextView)(view.findViewById(R.id.description))).setText(mData.get(i).getDescription());
        ((TextView)(view.findViewById(R.id.status))).setText("阶段: "+mData.get(i).getPhase());

        return view;
    }
}
