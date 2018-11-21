package com.pedaily.yc.ycstatelayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ns.yc.ycstatelib.StateLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_empty)
    Button btnEmpty;
    @BindView(R.id.btn_error)
    Button btnError;
    @BindView(R.id.btn_network_error)
    Button btnNetworkError;
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    private StateLayoutManager statusLayoutManager;
    private List<String> lists = new ArrayList<>();
    private MainAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_view);
        initStatusLayout();
        initBaseView();
        ButterKnife.bind(this);
        initView();
        initToolBar();
        initRecycleView();
    }

    private void initStatusLayout() {
        statusLayoutManager = StateLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_content)
                .emptyDataView(R.layout.activity_emptydata)
                .errorView(R.layout.activity_error)
                .loadingView(R.layout.activity_loading)
                .netWorkErrorView(R.layout.activity_networkerror)
                .build();
    }


    private void initBaseView() {
        LinearLayout ll_main = (LinearLayout) findViewById(R.id.ll_main);
        ll_main.addView(statusLayoutManager.getRootLayout());
    }

    private void initView() {
        btnEmpty.setOnClickListener(this);
        btnError.setOnClickListener(this);
        btnNetworkError.setOnClickListener(this);
    }


    protected void initData() {
        lists.clear();
        for(int a=0 ; a<50 ; a++){
            lists.add("这是第"+a+"条数据");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_empty:
                initEmptyDataView();
                break;
            case R.id.btn_error:
                initErrorDataView();
                break;
            case R.id.btn_network_error:
                initSettingNetwork();
                break;
        }
    }

    private void initRecycleView() {
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(lists,this);
        recycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new HhItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(lists.size()>position && position>-1){
                    Toast.makeText(TestActivity.this,"条目"+position+"被点击呢",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("状态切换");
        toolbar.inflateMenu(R.menu.base_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.action_contents) {
                    showContent();
                }
                if(item.getItemId() == R.id.action_emptyData) {
                    initEmptyDataView();
                }
                if(item.getItemId() == R.id.action_error) {
                    initErrorDataView();
                }
                if(item.getItemId() == R.id.action_networkError) {
                    initSettingNetwork();
                }
                if(item.getItemId() == R.id.action_loading) {
                    statusLayoutManager.showLoading();
                }
                return true;
            }
        });
    }



    /**
     * 点击重新刷新数据
     */
    private void initEmptyDataView() {
        statusLayoutManager.showEmptyData();
        LinearLayout ll_empty_data = (LinearLayout) findViewById(R.id.ll_empty_data);
        ll_empty_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
                adapter.notifyDataSetChanged();
                showContent();
            }
        });
    }

    /**
     * 点击重新刷新
     */
    private void initErrorDataView() {
        statusLayoutManager.showError();
        LinearLayout ll_error_data = (LinearLayout) findViewById(R.id.ll_error_data);
        ll_error_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
                adapter.notifyDataSetChanged();
                showContent();
            }
        });
    }

    /**
     * 点击设置网络
     */
    private void initSettingNetwork() {
        statusLayoutManager.showNetWorkError();
        LinearLayout ll_set_network = (LinearLayout) findViewById(R.id.ll_set_network);
        ll_set_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
                startActivity(intent);
            }
        });
    }

    private void showContent() {
        statusLayoutManager.showContent();
    }


}
