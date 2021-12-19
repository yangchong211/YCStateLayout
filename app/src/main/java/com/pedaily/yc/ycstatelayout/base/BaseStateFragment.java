package com.pedaily.yc.ycstatelayout.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.yc.ycstatelib.StateLayoutManager;
import com.pedaily.yc.ycstatelayout.R;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/7/20
 *     desc  : fragment的父类
 *     revise: 注意，该类具有懒加载
 * </pre>
 */
public abstract class BaseStateFragment extends BaseLazyFragment {


    protected StateLayoutManager statusLayoutManager;
    private View view;
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    /**
     * 异常崩溃后会再次走onCreate方法，这也就是为啥有时候fragment重叠，因为被创建多次
     * 发生Fragment重叠的根本原因在于FragmentState没有保存Fragment的显示状态，
     * 即mHidden，导致页面重启后，该值为默认的false，即show状态，所以导致了Fragment的重叠。
     * 两种方案：第一种在activity中处理，第二种在fragment中处理
     * @param savedInstanceState                bundle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            //异常启动
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = null;
            if (getFragmentManager() != null) {
                ft = getFragmentManager().beginTransaction();
                if (isSupportHidden) {
                    ft.hide(this);
                } else {
                    ft.show(this);
                }
                ft.commit();
            }
        } else {
            //正常启动
        }
    }

    /**
     * 异常崩溃，但是没有完全杀死app，内存重启，保存状态
     * @param outState                          bundle
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.base_state_view, container , false);
            view.setClickable(true);
            initStatusLayout();
            initBaseView(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 获取到子布局
     * @param view              view
     */
    private void initBaseView(View view) {
        FrameLayout flStateView = view.findViewById(R.id.fl_state_view);
        flStateView.addView(statusLayoutManager.getRootLayout());
    }


    /**
     * 初始化状态管理器相关操作
     */
    protected abstract void initStatusLayout();

    /**
     * 初始化View的代码写在这个方法中
     * @param view              view
     */
    public abstract void initView(View view);

    /**
     * 初始化监听器的代码写在这个方法中
     */
    public abstract void initListener();

    /**
     * 第一次可见状态时，showLoading操作，注意下拉刷新操作时不要用该全局loading
     */
    @Override
    protected void showFirstLoading() {
        super.showFirstLoading();
        showLoading();
    }

    /*protected void initStatusLayout() {
        statusLayoutManager = StateLayoutManager.newBuilder(activity)
                .contentView(R.layout.common_fragment_list)
                .emptyDataView(R.layout.view_custom_empty_data)
                .errorView(R.layout.view_custom_data_error)
                .loadingView(R.layout.view_custom_loading_data)
                .netWorkErrorView(R.layout.view_custom_network_error)
                .build();
    }*/


    /*---------------------------------下面是状态切换方法-----------------------------------------*/


    /**
     * 加载成功
     */
    protected void showContent() {
        if (statusLayoutManager!=null){
            statusLayoutManager.showContent();
        }
    }

    /**
     * 加载无数据
     */
    protected void showEmptyData() {
        if (statusLayoutManager!=null){
            statusLayoutManager.showEmptyData();
        }
    }

    /**
     * 加载异常
     */
    protected void showError() {
        if (statusLayoutManager!=null){
            statusLayoutManager.showError();
        }
    }

    /**
     * 加载网络异常
     */
    protected void showNetWorkError() {
        if (statusLayoutManager!=null){
            statusLayoutManager.showNetWorkError();
        }
    }

    /**
     * 加载loading
     */
    protected void showLoading() {
        if (statusLayoutManager!=null){
            statusLayoutManager.showLoading();
        }
    }

}
