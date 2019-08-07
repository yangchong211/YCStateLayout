package com.pedaily.yc.ycstatelayout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedaily.yc.ycstatelayout.HhItemClickListener;
import com.pedaily.yc.ycstatelayout.R;

import java.util.List;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/7/6
 * 描    述：适配器
 * 修订历史：
 * ================================================
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private final List<String> list;
    private final Context context;
    HhItemClickListener mItemClickListener;

    public RecyclerAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(HhItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false);
        MyViewHolder holder = new MyViewHolder(inflate, mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(list!=null && list.size()>0){
            holder.tv_item.setText(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_item;
        private HhItemClickListener mListener;

        public MyViewHolder(View view, HhItemClickListener listener) {
            super(view);
            tv_item = view.findViewById(R.id.tv_item);
            this.mListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getPosition());
            }
        }
    }

}
