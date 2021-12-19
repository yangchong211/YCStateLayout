package com.pedaily.yc.ycstatelayout.activity;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pedaily.yc.ycstatelayout.R;
import com.pedaily.yc.ycstatelayout.base.BasePagerAdapter;
import com.pedaily.yc.ycstatelayout.fragment.Test1Fragment;
import com.pedaily.yc.ycstatelayout.fragment.Test2Fragment;
import com.pedaily.yc.ycstatelayout.fragment.Test3Fragment;

import java.util.ArrayList;


public class Test2Activity extends AppCompatActivity {


    private TabLayout mTab;
    private ViewPager mVp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout2);
        initView();
        initTabLayout();
    }


    private void initView() {
        mTab = (TabLayout) findViewById(R.id.tab);
        mVp = (ViewPager) findViewById(R.id.vp);
    }


    private void initTabLayout() {
        ArrayList<String> mTitleList = new ArrayList<>();
        ArrayList<Fragment> mFragments = new ArrayList<>();
        mTitleList.add("综合");
        mTitleList.add("文学");
        mTitleList.add("文化");
        mTitleList.add("生活");
        mTitleList.add("励志");
        mFragments.add(new Test2Fragment());
        mFragments.add(new Test1Fragment());
        mFragments.add(new Test2Fragment());
        mFragments.add(new Test3Fragment());
        mFragments.add(new Test2Fragment());

        /*
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻2个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        BasePagerAdapter myAdapter = new BasePagerAdapter(supportFragmentManager,
                mFragments, mTitleList);
        mVp.setAdapter(myAdapter);
        // 左右预加载页面的个数
        mVp.setOffscreenPageLimit(mFragments.size());
        myAdapter.notifyDataSetChanged();
        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setupWithViewPager(mVp);
    }

}
