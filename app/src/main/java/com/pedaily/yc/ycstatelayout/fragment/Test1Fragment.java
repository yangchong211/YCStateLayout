package com.pedaily.yc.ycstatelayout.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ns.yc.ycstatelib.OnNetworkListener;
import com.ns.yc.ycstatelib.OnRetryListener;
import com.ns.yc.ycstatelib.StateLayoutManager;
import com.pedaily.yc.ycstatelayout.base.BaseStateFragment;
import com.pedaily.yc.ycstatelayout.HhItemClickListener;
import com.pedaily.yc.ycstatelayout.MainAdapter;
import com.pedaily.yc.ycstatelayout.R;

import java.util.ArrayList;
import java.util.List;

public class Test1Fragment extends BaseStateFragment {

    private static final int LOADING = 1;
    private static final int CONTENT = 2;
    private List<String> lists = new ArrayList<>();
    private MainAdapter adapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case LOADING:
                    showLoading();
                    break;
                case CONTENT:
                    showContent();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StateLayoutManager.newBuilder(activity)
                .contentView(R.layout.base_recycler_view)
                .emptyDataView(R.layout.custom_empty_view)
                .errorView(R.layout.activity_error)
                .loadingView(R.layout.custom_app_loading)
                .netWorkErrorView(R.layout.custom_network_error)
                .onRetryListener(new OnRetryListener() {
                    @Override
                    public void onRetry() {
                        //点击重试
                        showLoading();
                        loadData();
                    }
                })
                .onNetworkListener(new OnNetworkListener() {
                    @Override
                    public void onNetwork() {
                        //网络异常，点击重试
                        showLoading();
                        loadData();
                    }
                })
                .build();
    }

    @Override
    public void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new MainAdapter(lists, activity);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new HhItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(lists.size()>position && position>-1){
                    Toast.makeText(activity,"条目"+position+"被点击呢",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void loadData() {
        lists.clear();
        for(int a=0 ; a<50 ; a++){
            lists.add("这是第"+a+"条数据");
        }
        adapter.notifyDataSetChanged();
        handler.sendEmptyMessageDelayed(CONTENT,3000);
    }

}
