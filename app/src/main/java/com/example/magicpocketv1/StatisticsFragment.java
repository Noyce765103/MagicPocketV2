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

import com.example.magicpocketv1.Bean.AccountBean;
import com.example.magicpocketv1.Utils.DateUtil;
import com.example.magicpocketv1.Utils.GlobalUtil;
import com.example.magicpocketv1.Utils.MyValueFomatter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 分析界面,配置文件为fragment_statistics.xml
 */
public class StatisticsFragment extends Fragment {
    public View myView;
    public LineChart lineChart;
    public PieChart pieChart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics,null);
        myView = view;
        initViews(view);
        return view;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(lineChart!=null&&pieChart!=null){
            initViews(myView);
            //lineChart.notifyDataSetChanged();
            //lineChart.invalidate();

        }
    }

    private void initViews(View view) {
        lineChart = view.findViewById(R.id.line_chart);
        pieChart = view.findViewById(R.id.pie_chart);
        initData();
        initPieData();


    }

    private void initPieData() {
        String descriptionStr = "过去七日消费种类占比";
        Description description = new Description();
        description.setText(descriptionStr);
        ArrayList<LinkedList<AccountBean>> record = GlobalUtil.getInstance().dataBaseHelper.getWeekDate();
        ArrayList<String> dataList = GlobalUtil.getInstance().getSevenDayCostTitle(record);
        ArrayList<PieEntry> mPie = new ArrayList<>();
        for(String data:dataList){
            int sum = GlobalUtil.getInstance().getTotalRecordCount(record);
            int[] count = GlobalUtil.getInstance().getCostTitleCount(record);
            float value = (float)count[(GlobalUtil.getInstance().map.get(data))]/(float)sum;
            PieEntry temp = new PieEntry(value,data);
            mPie.add(temp);
        }
        PieDataSet dataSet = new PieDataSet(mPie,"");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        int[] MATERIAL_COLORS = {
                Color.rgb(200, 172, 255)
        };
        for (int c : MATERIAL_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(c);
        }
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        //设置半透明圆环的半径, 0为透明
        pieChart.setTransparentCircleRadius(0f);

        //设置初始旋转角度
        pieChart.setRotationAngle(-15);

        //数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1OffsetPercentage(80f);

        //设置连接线的颜色
        dataSet.setValueLineColor(Color.LTGRAY);
        // 连接线在饼状图外面
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        // 设置饼块之间的间隔
        dataSet.setSliceSpace(1f);
        dataSet.setHighlightEnabled(true);


        // 和四周相隔一段距离,显示数据
        pieChart.setExtraOffsets(26, 5, 26, 5);

        // 设置pieChart图表是否可以手动旋转
        pieChart.setRotationEnabled(false);
        // 设置piecahrt图表点击Item高亮是否可用
        pieChart.setHighlightPerTapEnabled(true);

        //设置pieChart是否只显示饼图上百分比不显示文字
        pieChart.setDrawEntryLabels(false);
        //是否绘制PieChart内部中心文本
        pieChart.setDrawCenterText(false);
        // 绘制内容value，设置字体颜色大小
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.DKGRAY);
        pieChart.setDescription(description);
        pieChart.setUsePercentValues(true);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }

    private void initData() {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setLabelCount(6,false);
        YAxis yLeft = lineChart.getAxisLeft();
        String descriptionStr = "过去七日消费曲线图";
        Description description = new Description();
        description.setText(descriptionStr);



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
        dataSet.setColor(Color.rgb(14,230,223));
        dataSet.setCircleColor(Color.rgb(23,19,214));
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
        LimitLine ll_high = new LimitLine(100f, "消费过高");
        ll_high.setLineColor(Color.RED);
        ll_high.setLineWidth(2f);
        ll_high.setTextColor(Color.RED);
        ll_high.setTextSize(12f);

        LimitLine ll_mid = new LimitLine(80f, "消费高");
        ll_mid.setLineColor(Color.rgb(245,190,8));
        ll_mid.setLineWidth(2f);
        ll_mid.setTextColor(Color.rgb(245,190,8));
        ll_mid.setTextSize(12f);

        LimitLine ll_low = new LimitLine(40f, "正常消费");
        ll_low.setLineColor(Color.GREEN);
        ll_low.setLineWidth(2f);
        ll_low.setTextColor(Color.GREEN);
        ll_low.setTextSize(12f);



        yLeft.addLimitLine(ll_high);
        yLeft.addLimitLine(ll_mid);
        yLeft.addLimitLine(ll_low);

        lineChart.setDescription(description);
        lineChart.setScaleEnabled(false);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
