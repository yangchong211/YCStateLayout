package com.pedaily.yc.statelayoutlib;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewStub;


/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/7/6
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class VLayout {

    protected ViewStub mLayoutVs;

    protected View mContentView;

    protected void initLayout(@LayoutRes int layoutResId, Context context) {
        mLayoutVs = new ViewStub(context);
        mLayoutVs.setLayoutResource(layoutResId);
    }

    protected ViewStub getLayoutVs() {
        return mLayoutVs;
    }

    protected void setView(View contentView) {
        mContentView = contentView;
    }

    protected abstract void setData(Object... objects);
}
