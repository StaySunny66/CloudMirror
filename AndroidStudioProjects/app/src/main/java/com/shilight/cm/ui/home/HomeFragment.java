package com.shilight.cm.ui.home;

import static com.shilight.cm.viewdata.OverViewData.getDataOnline;

import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.shilight.cm.Login;
import com.shilight.cm.R;
import com.shilight.cm.databinding.FragmentHomeBinding;
import com.shilight.cm.sever.UserLogin;
import com.shilight.cm.viewdata.OverViewData;

public class HomeFragment extends Fragment {


    Callback callback = new Callback() ;

    private FragmentHomeBinding binding;
    PieChart mpiechart;
    BarChart mBarChart;
    ProgressBar s1p,data1p,data2p;
    LinearLayout s1,s1s;
    Button login;
    TextView all,online,shutdown,message;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mpiechart = root.findViewById(R.id.char2);
        s1 = root.findViewById(R.id.s1);
        data1p = root.findViewById(R.id.data1p);
        data2p = root.findViewById(R.id.data2p);
        login = root.findViewById(R.id.login);
        s1p = root.findViewById(R.id.s1p);
        s1s = root.findViewById(R.id.s1s);
        mBarChart = root.findViewById(R.id.data1);

        all = root.findViewById(R.id.sernum);
        online = root.findViewById(R.id.onlinenum);
        shutdown = root.findViewById(R.id.shutdownnum);
        message = root.findViewById(R.id.messagenum);
        mpiechart.setHoleRadius(30F);
        mpiechart.setTransparentCircleRadius(35F);
        mpiechart.setCenterText("报警");

        if(UserLogin.isLogin(getActivity())){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message m =  Message.obtain();
                    OverViewData mm = getDataOnline(getActivity());

                    if(mm.getOnlineRate()!=null){
                        m.obj =mm ;
                        m.what = 0;
                        callback.sendMessage(m);
                    }else{
                        m.what = 1;
                        callback.sendMessage(m);
                    }


                }
            }).start();


        }else {
            s1p.setVisibility(View.GONE);
           login.setVisibility(View.VISIBLE);

        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent();
                go.setClass(getActivity(), Login.class);
                startActivity(go);
            }
        });
        setChart(mBarChart);
        setData(mBarChart);

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

    private void  setData(BarChart barChart){
        //1,BarEntry集合,
        ArrayList<BarEntry> barEntriesData = new ArrayList<>();

        Random random = new Random();
        for(int i=1;i<=7;i++){
            barEntriesData.add(new BarEntry(i, 50));
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



    private boolean showData(int x,int y,int z,int w){
        PieEntry pie1 = new PieEntry(x,"负载");
        PieEntry pie2 = new PieEntry(y,"离线");
        PieEntry pie3 = new PieEntry(z,"异地登录");
        PieEntry pie4 = new PieEntry(w,"漏洞");

        List<PieEntry> pieEntryList = new ArrayList<>();
        pieEntryList.add(pie1);pieEntryList.add(pie2);
        pieEntryList.add(pie3);pieEntryList.add(pie4);


        PieDataSet set = new PieDataSet(pieEntryList,"");
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#465EF2"));
        colors.add(Color.parseColor("#9587C4"));
        colors.add(Color.parseColor("#43489F"));
        colors.add(Color.parseColor("#18BDCF"));


        set.setColors(colors);//添加颜色
        set.setSliceSpace(3f);//切割空间
        set.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);//值在图表外显示

        PieData pieData = new PieData(set);
        set.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);//值在图表外显示(所占百分比)

        Description description = mpiechart.getDescription();//获得图表的描述
        description.setText("");
        Legend legend = mpiechart.getLegend();//获得图例的描述
        legend.setEnabled(false);
        mpiechart.animateXY(500, 500);
        mpiechart.setData(pieData);
        return true;

    }

    @Override
    public void onResume() {
        super.onResume();
        if(UserLogin.isLogin(getActivity())){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message m =  Message.obtain();
                    OverViewData mm = getDataOnline(getActivity());

                    if(mm.getOnlineRate()!=null){
                        m.obj =mm ;
                        m.what = 0;
                        callback.sendMessage(m);
                    }else{
                        m.what = 1;
                        callback.sendMessage(m);
                    }


                }
            }).start();


        }else {
            s1p.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);

        }

        setChart(mBarChart);
        setData(mBarChart);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    class Callback extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
              OverViewData overViewData =   (OverViewData)msg.obj;

              all.setText(String.valueOf(overViewData.getSerNum()));
              shutdown.setText(String.valueOf(overViewData.getShutdownSer()));
              online.setText(String.valueOf(overViewData.getOnlineSer()));
              message.setText(String.valueOf(overViewData.getShutdownSer()+overViewData.getAllData()[0]+overViewData.getAllData()[2]));
              showData(overViewData.getAllData()[0],overViewData.getAllData()[1],overViewData.getAllData()[2],overViewData.getAllData()[3]);
              s1s.setVisibility(View.GONE);
              s1.setVisibility(View.VISIBLE);


              data2p.setVisibility(View.GONE);
              mpiechart.setVisibility(View.VISIBLE);

              data1p.setVisibility(View.GONE);
              mBarChart.setVisibility(View.VISIBLE);
            } else if(msg.what==1){
                Toast.makeText(getActivity(),"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    }
}