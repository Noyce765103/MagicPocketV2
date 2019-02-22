package com.example.magicpocketv1.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.magicpocketv1.DetailFragment;
import com.example.magicpocketv1.Utils.DateUtil;
import com.example.magicpocketv1.Utils.GlobalUtil;

import java.util.LinkedList;

public class ViewpagerAdapter extends FragmentPagerAdapter {

    LinkedList<DetailFragment> fragments = new LinkedList<>();
    LinkedList<String> dates = new LinkedList<>();
    public ViewpagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments(){
        dates = GlobalUtil.getInstance().dataBaseHelper.CountDate();
        if (!dates.contains(DateUtil.getFormattedDate())){
            dates.addLast(DateUtil.getFormattedDate());

        }
        for(String data:dates){
            DetailFragment fm = new DetailFragment(data);
            fragments.add(fm);
        }
    }

    public void reload(){
        for (DetailFragment fragment:fragments){
            fragment.reload();
        }
    }
    public int getLastIndex(){
        return (fragments.size()-1);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    public String getDateStr(int index){
        return dates.get(index);
    }

    public int getTotalCost(int index){
        return fragments.get(index).getTotalCost();
    }
}
