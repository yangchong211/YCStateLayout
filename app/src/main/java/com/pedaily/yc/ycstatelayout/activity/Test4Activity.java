package com.pedaily.yc.ycstatelayout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ns.yc.ycstatelib.CommonPaddingView;
import com.pedaily.yc.ycstatelayout.HhItemClickListener;
import com.pedaily.yc.ycstatelayout.MainActivity;
import com.pedaily.yc.ycstatelayout.MainAdapter;
import com.pedaily.yc.ycstatelayout.R;
import com.pedaily.yc.ycstatelayout.base.BasePagerAdapter;
import com.pedaily.yc.ycstatelayout.fragment.Test1Fragment;
import com.pedaily.yc.ycstatelayout.fragment.Test2Fragment;
import com.pedaily.yc.ycstatelayout.fragment.Test3Fragment;

import java.util.ArrayList;
import java.util.List;

import static com.ns.yc.ycstatelib.CommonPaddingView.LOADING;


public class Test4Activity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnLoading;
    private Button mBtnEmpty;
    private Button mBtnError;
    private Button mBtnNetworkError;
    private Button mBtnNormal;
    private RecyclerView mRecycleView;
    private CommonPaddingView mPaddingView;
    private List<String> lists = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_test);
        initView();
        initListener();
        initData();
        initRecycleView();
    }

    private void initView() {
        mBtnLoading = findViewById(R.id.btn_loading);
        mBtnEmpty = findViewById(R.id.btn_empty);
        mBtnError = findViewById(R.id.btn_error);
        mBtnNetworkError = findViewById(R.id.btn_network_error);
        mBtnNormal = findViewById(R.id.btn_normal);
        mRecycleView = findViewById(R.id.recycleView);
        mPaddingView = findViewById(R.id.padding_view);

    }

    private void initListener() {
        mBtnLoading.setOnClickListener(this);
        mBtnEmpty.setOnClickListener(this);
        mBtnError.setOnClickListener(this);
        mBtnNetworkError.setOnClickListener(this);
        mBtnNormal.setOnClickListener(this);
    }

    private void initRecycleView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter adapter = new MainAdapter(lists, this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new HhItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(lists.size()>position && position>-1){
                    Toast.makeText(Test4Activity.this,"条目"+position+"被点击呢",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    protected void initData() {
        lists.clear();
        for(int a=0 ; a<50 ; a++){
            lists.add("这是第"+a+"条数据");
        }
    }


    @Override
    public void onClick(View v) {
        if (v == mBtnLoading){
            mPaddingView.setViewState(CommonPaddingView.LOADING);
        } else if (v == mBtnEmpty){
            mPaddingView.setViewState(CommonPaddingView.EMPTY);
        } else if (v == mBtnError){
            mPaddingView.setViewState(CommonPaddingView.NEW_STYLE);
        } else if (v == mBtnNetworkError){
            mPaddingView.setViewState(CommonPaddingView.NET);
        } else if (v == mBtnNormal){
            mPaddingView.setViewState(CommonPaddingView.HIDE);
        }
    }
}
