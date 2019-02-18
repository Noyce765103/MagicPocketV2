package com.example.magicpocketv1;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magicpocketv1.Utils.DateUtil;
import com.example.magicpocketv1.Utils.GlobalUtil;
import com.example.magicpocketv1.Utils.MyValueFomatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 *
 * 分析界面,配置文件为fragment_statistics.xml
 */
public class StatisticsFragment extends Fragment {

    public LineChart lineChart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics,null);
        initViews(view);
        return view;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(lineChart!=null){
            Log.d("StatisticsFragment","refresh");
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();

        }
    }

    private void initViews(View view) {
        lineChart = view.findViewById(R.id.line_chart);
        initData();


    }

    private void initData() {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setLabelCount(6,false);
        YAxis yLeft = lineChart.getAxisLeft();



        ArrayList<Double> accountDetail = GlobalUtil.getInstance().getSevenDayData(GlobalUtil.getInstance().dataBaseHelper.getWeekDate());
        ArrayList<Entry> valsY = new ArrayList<Entry>();
        if(accountDetail.size() == 0){
            lineChart.setData(new LineData());
            lineChart.invalidate();
        }else if(accountDetail.size() > 0 && accountDetail.size() < 7){
            for(int i = 0;i<accountDetail.size();i++){
                float y_Data = (float) (accountDetail.get(i).doubleValue());
                //String x_Data = xx.get(i);
                Entry temp = new Entry(i,y_Data);
                valsY.add(temp);
            }
        }else{
            for(int i = 0;i<accountDetail.size();i++){
                float y_Data = (float) (accountDetail.get(i).doubleValue());
                //String x_Data = xx.get(i);
                Entry temp = new Entry(i,y_Data);
                valsY.add(temp);
            }
        }


        LineDataSet dataSet = new LineDataSet(valsY,"七日消费线");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setColor(Color.RED);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        xAxis.setValueFormatter(new MyValueFomatter());
        if(accountDetail.size()==0){
            xAxis.setLabelCount(0,false);
        }else{
            xAxis.setLabelCount(accountDetail.size()-1,false);
        }

        xAxis.setTextSize(5f);

        LineData lineData = new LineData(dataSets);
        LimitLine ll_high = new LimitLine(100f, "消费偏高");
        ll_high.setLineColor(Color.RED);
        ll_high.setLineWidth(2f);
        ll_high.setTextColor(Color.RED);
        ll_high.setTextSize(12f);

        LimitLine ll_mid = new LimitLine(80f, "消费高");
        ll_mid.setLineColor(Color.YELLOW);
        ll_mid.setLineWidth(2f);
        ll_mid.setTextColor(Color.YELLOW);
        ll_mid.setTextSize(12f);

        LimitLine ll_low = new LimitLine(40f, "正常消费");
        ll_low.setLineColor(Color.GREEN);
        ll_low.setLineWidth(2f);
        ll_low.setTextColor(Color.GREEN);
        ll_low.setTextSize(12f);



        yLeft.addLimitLine(ll_high);
        yLeft.addLimitLine(ll_mid);
        yLeft.addLimitLine(ll_low);


        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
