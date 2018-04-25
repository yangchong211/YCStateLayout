package com.ns.yc.ycstatelib;

import android.view.View;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/7/6
 *     desc  : 为状态View显示隐藏监听事件
 *     revise:
 * </pre>
 */
public interface OnShowHideViewListener {

    /**
     * show
     * @param view                  view
     * @param id                    view对应id
     */
    void onShowView(View view, int id);

    /**
     * hide
     * @param view                  view
     * @param id                    view对应id
     */
    void onHideView(View view, int id);

}
