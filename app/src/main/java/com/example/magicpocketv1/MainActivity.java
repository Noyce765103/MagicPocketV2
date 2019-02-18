package com.example.magicpocketv1;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.magicpocketv1.Adapter.BottomAdapter;
import com.example.magicpocketv1.Utils.GlobalUtil;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
        GlobalUtil.getInstance().setmContext(getApplicationContext());
        GlobalUtil.getInstance().activity = this;
        initViews();


    }

    /**
     * 初始化界面里的所有控件
     */
    private void initViews() {
        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.account:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.statistics:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.personal:
                        viewPager.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });
        //数据填充
        setupViewPager(viewPager);

        //viewpager监听设置
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                bottomNavigationView.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //让Viewpager不可滑动




    }

    private void setupViewPager(ViewPager viewPager) {
        BottomAdapter adapter = new BottomAdapter(getSupportFragmentManager());
        adapter.addFragment(new AccountFragment());
        adapter.addFragment(new StatisticsFragment());
        adapter.addFragment(new PersonalFragment());
        viewPager.setAdapter(adapter);

    }
}
