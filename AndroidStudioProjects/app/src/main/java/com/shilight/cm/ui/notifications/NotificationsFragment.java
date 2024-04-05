package com.shilight.cm.ui.notifications;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.shilight.cm.CveDataAdapter;
import com.shilight.cm.Http;
import com.shilight.cm.R;
import com.shilight.cm.databinding.FragmentNotificationsBinding;
import com.shilight.cm.sever.Safe;
import com.shilight.cm.sever.SafeHyDat;
import com.shilight.cm.ui.home.HomeFragment;
import com.shilight.cm.viewdata.OverViewData;

import java.util.ArrayList;
import java.util.Random;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    Callback callback = new Callback() ;

    Spinner mS;
    BarChart mbar;
    ProgressBar mbap,list_cvep;
    TextView core,allBug;
    QMUIPullRefreshLayout qmuiPullRefreshLayout;
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mS = root.findViewById(R.id.ss);
        mS.setSelection(Safe.get_hy(getActivity()));
        mbar = root.findViewById(R.id.loudong);
        mbap = root.findViewById(R.id.loudongp);
        core = root.findViewById(R.id.core);
        allBug = root.findViewById(R.id.allbug);
        qmuiPullRefreshLayout = root.findViewById(R.id.pullup);
        listView = root.findViewById(R.id.cveList);
        list_cvep = root.findViewById(R.id.list_cvep);


        qmuiPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }
            @Override
            public void onMoveRefreshView(int offset) {

            }
            @Override
            public void onRefresh() {
                get_new(2);
            }
        });

        mS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Safe.set_hy(i,getActivity());
                get_new(1);
                Log.i("序号", "onItemSelected: "+i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //get_new(1);

        return root;
    }


    private void setChart(BarChart barChart) {
        barChart.setDescription(null);                             //设置描述文字为null
        barChart.setDrawBarShadow(false);                          //绘制当前展示的内容顶部阴影
        barChart.setPinchZoom(false);                              //设置x轴和y轴能否同时缩放。默认否
        barChart.setMaxVisibleValueCount(10);                       //设置图表能显示的最大值，仅当setDrawValues()属性值为true时有用
        barChart.setFitBars(true);                                 //设置X轴范围两侧柱形条是否显示一半



        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.parseColor("#ff03A9F4"));
        xAxis.setTextSize(9);
        xAxis.setDrawLabels(true);                                 //是否显示X坐标轴上的刻度，默认是true
        xAxis.setLabelCount(7,false);                             //第一个参数是轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        YAxis leftAxis = barChart.getAxisLeft();              //获取到y轴，分左右
        leftAxis.setLabelCount(5, false);         //第一个参数是轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        leftAxis.setDrawGridLines(false);                      //不要横网格
        leftAxis.setSpaceTop(20f);                            //设置在图表上最高处的值相比轴上最高值的顶端空间（总轴范围的百分比）
        leftAxis.setAxisMinimum(0f);                          //为这个轴设置一个自定义的最小值。如果设置,这个值不会自动根据所提供的数据计算
        leftAxis.setTextColor(Color.parseColor("#ff03A9F4"));

        leftAxis.setDrawAxisLine(false);                      //设置为true,绘制轴线
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);       //y轴的数值显示在外侧
        //这里也可以自定义y轴显示样式。和x轴的自定义方法一样

        barChart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);//不设置图例


        barChart.setTouchEnabled(false);
    }

    private void  setData(BarChart barChart,int[] qs){
        //1,BarEntry集合,
        ArrayList<BarEntry> barEntriesData = new ArrayList<>();

        Random random = new Random();
        for(int i=0;i<qs.length;i++){
            barEntriesData.add(new BarEntry(i, qs[i]));
        }

        //2，BarDataSet
        BarDataSet bardataSet = new BarDataSet(barEntriesData,"");
        bardataSet.setDrawValues(false);
        bardataSet.setColor(Color.parseColor("#21B9FA"));

        //3，把BarDataSet数据添加到IBarDataSet集合
        ArrayList<IBarDataSet> iBarDataSets = new ArrayList<>();
        iBarDataSets.add(bardataSet);
        //4,BarData
        BarData barData = new BarData(iBarDataSets);
        //5,设置数据
        barChart.setData(barData);
    }

    void get_new(int s){

        new Thread(new Runnable() {
            @Override
            public void run() {

                Message m = Message.obtain();
                m.obj = Safe.getSafeDat(getActivity());
                m.what=s;

                callback.sendMessage(m);

            }
        }).start();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class Callback extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){

                SafeHyDat overViewData = (SafeHyDat)msg.obj;
                if(overViewData!=null){
                    allBug.setText(String.valueOf((int)overViewData.getCore()[0]));
                    core.setText(String.valueOf((int)overViewData.getCore()[1]));
                    listView.setAdapter(new CveDataAdapter(overViewData.getCveData(),getActivity()));

                    setChart(mbar);
                    setData(mbar,overViewData.getAa());
                    mbap.setVisibility(View.GONE);
                    mbar.setVisibility(View.VISIBLE);
                    list_cvep.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                }else{
                    Toast.makeText(getActivity(),"获取失败",Toast.LENGTH_LONG).show();

                }


            }
            if(msg.what == 2){
                SafeHyDat overViewData = (SafeHyDat)msg.obj;
                if(overViewData!=null){
                    allBug.setText(String.valueOf((int)overViewData.getCore()[0]));
                    core.setText(String.valueOf((int)overViewData.getCore()[1]));
                    listView.setAdapter(new CveDataAdapter(overViewData.getCveData(),getActivity()));
                    list_cvep.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                    setChart(mbar);
                    setData(mbar,overViewData.getAa());
                    mbap.setVisibility(View.GONE);
                    mbar.setVisibility(View.VISIBLE);
                    qmuiPullRefreshLayout.finishRefresh();

                }else {
                    Toast.makeText(getActivity(),"获取失败",Toast.LENGTH_LONG).show();

                }


            }
        }
    }
}