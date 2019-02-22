package com.example.magicpocketv1.Utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

public class MyValueFomatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        final ArrayList<String> xx = new ArrayList<>();
        for(String temp:GlobalUtil.getInstance().dataBaseHelper.getSevenDate()){
            String x = DateUtil.getXTitle(temp);
            xx.add(x);
        }
        axis.setGranularity(1f);
        if(GlobalUtil.getInstance().dataBaseHelper.getSevenDate().size() == 1){
            return xx.get(0);
        }
        return xx.get((int)value);
    }
}
