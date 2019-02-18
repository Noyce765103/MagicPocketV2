package com.example.magicpocketv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.magicpocketv1.Adapter.ListViewAdapter;
import com.example.magicpocketv1.Adapter.ViewpagerAdapter;
import com.example.magicpocketv1.Bean.AccountBean;
import com.example.magicpocketv1.Utils.DateUtil;
import com.example.magicpocketv1.Utils.GlobalUtil;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.LinkedList;

/**
 * 记账界面,配置文件为fragment_account.xml
 */

public class AccountFragment extends Fragment implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;

    public ViewpagerAdapter getViewpagerAdapter() {
        return viewpagerAdapter;
    }

    private ViewpagerAdapter viewpagerAdapter;
    private TickerView tickerView;
    private TextView dateText;
    private int currentPagerPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,null);


        initView(view);


        return view;

    }

    private void initView(final View view) {
        tickerView = view.findViewById(R.id.money_text);
        tickerView.setCharacterLists(TickerUtils.provideNumberList());
        dateText = view.findViewById(R.id.data_text);
        FragmentManager fm = getChildFragmentManager();
        viewPager = view.findViewById(R.id.detail_view_pager);
        viewpagerAdapter = new ViewpagerAdapter(fm);
        viewpagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(viewpagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(viewpagerAdapter.getLastIndex());
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),AddRecordActivity.class);
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        viewpagerAdapter.reload();
        updateHeader();

    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        currentPagerPosition = i;
        String money = String.valueOf(viewpagerAdapter.getTotalCost(currentPagerPosition));
        tickerView.setText("-"+money);
        String date = viewpagerAdapter.getDateStr(currentPagerPosition);
        dateText.setText(DateUtil.getWeekDay(date));
    }
    public void updateHeader(){
        String money = String.valueOf(viewpagerAdapter.getTotalCost(currentPagerPosition));
        tickerView.setText("-"+money);
        String date = viewpagerAdapter.getDateStr(currentPagerPosition);
        dateText.setText(DateUtil.getWeekDay(date));
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
