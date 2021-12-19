package com.pedaily.yc.ycstatelayout.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.ycstatelib.StateLayoutManager;
import com.pedaily.yc.ycstatelayout.HhItemClickListener;
import com.pedaily.yc.ycstatelayout.MainAdapter;
import com.pedaily.yc.ycstatelayout.R;

import java.util.ArrayList;
import java.util.List;


public class Test3Activity extends AppCompatActivity implements View.OnClickListener {


    private StateLayoutManager statusLayoutManager;
    private List<String> lists = new ArrayList<>();
    private MainAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_view);
        initStatusLayout();
        initBaseView();
        initView();
        initToolBar();
        initData();
        initRecycleView();
    }

    private void initStatusLayout() {
        statusLayoutManager = StateLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_content)
                .emptyDataView(R.layout.custom_empty_view)
                .errorView(R.layout.activity_error)
                .loadingView(R.layout.activity_loading3)
                .netWorkErrorView(R.layout.custom_network_error)
                .build();
    }


    private void initBaseView() {
        LinearLayout ll_main = (LinearLayout) findViewById(R.id.ll_main);
        ll_main.addView(statusLayoutManager.getRootLayout());
        Toolbar toolbar = findViewById(R.id.tb_bar);
        toolbar.setTitle("测试3页面");
    }

    private void initView() {
        statusLayoutManager.showLoading();
        Button btn_empty = (Button) findViewById(R.id.btn_empty);
        Button btn_error = (Button) findViewById(R.id.btn_error);
        Button btn_network_error = (Button) findViewById(R.id.btn_network_error);


        btn_empty.setOnClickListener(this);
        btn_error.setOnClickListener(this);
        btn_network_error.setOnClickListener(this);
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
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(lists,this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new HhItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(lists.size()>position && position>-1){
                    Toast.makeText(Test3Activity.this,"条目"+position+"被点击呢",Toast.LENGTH_SHORT).show();
                }
            }
        });
        showContent();
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
        ImageView ivImage  = findViewById(R.id.iv_empty_bg);
        TextView tvContent = findViewById(R.id.tv_empty_name);
        ivImage.setBackgroundResource(R.drawable.icon_failed);
        tvContent.setText("自定义内容");
        ll_empty_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
