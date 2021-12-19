package com.pedaily.yc.ycstatelayout;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.ycstatelib.OnNetworkListener;
import com.yc.ycstatelib.OnRetryListener;
import com.yc.ycstatelib.StateLayoutManager;
import com.pedaily.yc.ycstatelayout.activity.Test1Activity;
import com.pedaily.yc.ycstatelayout.activity.Test2Activity;
import com.pedaily.yc.ycstatelayout.activity.Test3Activity;
import com.pedaily.yc.ycstatelayout.activity.Test4Activity;
import com.pedaily.yc.ycstatelayout.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/7/6
 * 描    述：主页面
 * 修订历史：
 * ================================================
 */

public class MainActivity extends BaseActivity {

    private List<String> lists = new ArrayList<>();

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StateLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_main)
                .emptyDataView(R.layout.custom_empty_view)
                .errorView(R.layout.activity_error)
                .loadingView(R.layout.custom_app_loading)
                .netWorkErrorView(R.layout.custom_network_error)
                //设置空数据页面图片控件id
                .emptyDataIconImageId(R.id.image)
                //设置空数据页面文本控件id
                .emptyDataTextTipId(R.id.tv_content)
                //设置异常页面图片id
                .errorIconImageId(R.id.image)
                //设置异常页面文本id
                .errorTextTipId(R.id.tv_content)
                .onRetryListener(new OnRetryListener() {
                    @Override
                    public void onRetry() {
                        //点击重试
                        showContent();
                    }
                })
                .onNetworkListener(new OnNetworkListener() {
                    @Override
                    public void onNetwork() {
                        //网络异常，点击重试
                        showLoading();
                    }
                })
                .build();
    }

    @Override
    protected void initView() {
        showLoading();
        initViewContent();
        initData();
        initRecycleView();
    }

    private void initViewContent() {
        Button btn_empty = (Button) findViewById(R.id.btn_empty);
        Button btn_error = (Button) findViewById(R.id.btn_error);
        Button btn_network_error = (Button) findViewById(R.id.btn_network_error);
        Button btn_test = (Button) findViewById(R.id.btn_test);
        Button btn_test2 = (Button) findViewById(R.id.btn_test2);
        Button btn_test3 = (Button) findViewById(R.id.btn_test3);
        Button btn_test4 = findViewById(R.id.btn_test4);

        btn_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initEmptyDataView();
            }
        });
        btn_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initErrorDataView();
            }
        });
        btn_network_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSettingNetwork();
            }
        });
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Test1Activity.class));
            }
        });
        btn_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Test2Activity.class));
            }
        });
        btn_test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Test3Activity.class));
            }
        });
        btn_test4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Test4Activity.class));
            }
        });
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
                    Toast.makeText(MainActivity.this,"条目"+position+"被点击呢",Toast.LENGTH_SHORT).show();
                }
            }
        });
        showContent();
    }

    protected void initData() {
        lists.clear();
        for(int a=0 ; a<50 ; a++){
            lists.add("这是第"+a+"条数据");
        }
    }


    /**
     * 点击重新刷新数据
     */
    private void initEmptyDataView() {
        //statusLayoutManager.showEmptyData();
        statusLayoutManager.showEmptyData(R.drawable.icon_empty,"逗比，没有数据");
    }

    /**
     * 点击重新刷新
     */
    private void initErrorDataView() {
        //statusLayoutManager.showError();
        statusLayoutManager.showError(R.drawable.icon_network_error,"逗比，错误数据");
    }

    /**
     * 点击设置网络
     */
    private void initSettingNetwork() {
        statusLayoutManager.showNetWorkError();
    }

}
