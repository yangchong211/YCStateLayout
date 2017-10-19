package com.pedaily.yc.statelayoutlib;

import android.view.View;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/7/6
 * 描    述：为状态View显示隐藏监听事件
 * 修订历史：
 * ================================================
 */
public interface OnShowHideViewListener {

    void onShowView(View view, int id);

    void onHideView(View view, int id);
}
