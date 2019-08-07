package com.pedaily.yc.ycstatelayout.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ns.yc.ycstatelib.StateLayoutManager;
import com.ns.yc.ycstatelib.StateViewLayout;
import com.pedaily.yc.ycstatelayout.BuildConfig;
import com.pedaily.yc.ycstatelayout.R;
import com.pedaily.yc.ycstatelayout.adapter.Constants;
import com.pedaily.yc.ycstatelayout.adapter.GlobalAdapter;
import com.pedaily.yc.ycstatelayout.base.BaseStateFragment;

import java.util.ArrayList;
import java.util.List;

import static com.pedaily.yc.ycstatelayout.adapter.Util.getErrorImage;
import static com.pedaily.yc.ycstatelayout.adapter.Util.getRandomImage;

public class Test3Fragment extends BaseStateFragment {

    private static final int LOADING = 1;
    private static final int CONTENT = 2;
    private List<String> lists = new ArrayList<>();
    private RecyclerAdapter adapter;
    private int size;

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
        statusLayoutManager = StateLayoutManager.newBuilder(activity,true)
                .contentView(R.layout.base_recycler_view)
                .build();
        showContent();
        StateViewLayout.debug(BuildConfig.DEBUG);
        StateViewLayout.initDefault(new GlobalAdapter());
        DisplayMetrics dm = getResources().getDisplayMetrics();
        size = dm.widthPixels >> 1;
    }

    @Override
    public void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecyclerAdapter(initData());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {

    }

    private List<String> initData() {
        int size = 20;
        List<String> list = new ArrayList<>(size + 4);
        list.add("");
        list.add(getRandomImage());
        list.add(getRandomImage());
        list.add(getErrorImage());
        for (int i = 0; i < size; i++) {
            list.add(getRandomImage());
        }
        return list;
    }


    class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
        List<String> list;

        RecyclerAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(size,size));
            StateViewLayout.Holder holder = StateViewLayout.getDefault().wrap(imageView);
            holder.withData(Constants.HIDE_LOADING_STATUS_MSG);
            return new ViewHolder(holder, imageView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.showImage(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements Runnable {
        private StateViewLayout.Holder holder;
        ImageView imageView;
        private String curUrl;


        ViewHolder(StateViewLayout.Holder holder, ImageView imageView) {
            super(holder.getWrapper());
            this.imageView = imageView;
            this.holder = holder;
            this.holder.withRetry(this);
        }

        void showImage(String url) {
            curUrl = url;
            holder.showLoading();
            Glide.with(activity)
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target, boolean isFirstResource) {
                            holder.showLoadFailed();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model,
                                                       Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                            holder.showLoadSuccess();
                            return false;
                        }
                    })
                    .into(imageView);
        }

        @Override
        public void run() {
            showImage(curUrl);
        }
    }


    @Override
    protected void loadData() {

    }

}
