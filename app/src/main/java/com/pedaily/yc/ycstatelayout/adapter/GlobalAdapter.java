package com.pedaily.yc.ycstatelayout.adapter;

import android.view.View;

import com.yc.ycstatelib.StateViewLayout;


public class GlobalAdapter implements StateViewLayout.Adapter {

    @Override
    public View getView(StateViewLayout.Holder holder, View convertView, int status) {
        GlobalLoadingStatusView loadingStatusView = null;
        //reuse the old view, if possible
        if (convertView != null && convertView instanceof GlobalLoadingStatusView) {
            loadingStatusView = (GlobalLoadingStatusView) convertView;
        }
        if (loadingStatusView == null) {
            loadingStatusView = new GlobalLoadingStatusView(holder.getContext(), holder.getRetryTask());
        }
        loadingStatusView.setStatus(status);
        Object data = holder.getData();
        //show or not show msg view
        boolean hideMsgView = Constants.HIDE_LOADING_STATUS_MSG.equals(data);
        loadingStatusView.setMsgViewVisibility(!hideMsgView);
        return loadingStatusView;
    }

}
