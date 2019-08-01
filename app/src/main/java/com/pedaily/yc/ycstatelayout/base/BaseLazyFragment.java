package com.pedaily.yc.ycstatelayout.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/7/22
 *     desc  : 懒加载
 *     revise: 懒加载时机：onCreateView()方法执行完毕 + setUserVisibleHint()方法返回true
 * </pre>
 */
public abstract class BaseLazyFragment extends Fragment {

    /*
     * 预加载页面回调的生命周期流程：
     * setUserVisibleHint() -->onAttach() --> onCreate()-->onCreateView()-->
     *              onActivityCreate() --> onStart() --> onResume()
     */

    /**
     * 懒加载过
     */
    protected boolean isLazyLoaded = false;
    /**
     * Fragment的View加载完毕的标记
     */
    private boolean isPrepared = false;

    /**
     * 第一步,改变isPrepared标记
     * 当onViewCreated()方法执行时,表明View已经加载完毕,此时改变isPrepared标记为true,并调用lazyLoad()方法
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        //只有Fragment onCreateView好了
        //另外这里调用一次lazyLoad(）
        lazyLoad();
    }


    /**
     * 第二步
     * 此方法会在onCreateView(）之前执行
     * 当viewPager中fragment改变可见状态时也会调用
     * 当fragment 从可见到不见，或者从不可见切换到可见，都会调用此方法
     * true表示当前页面可见，false表示不可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("setUserVisibleHint---",isVisibleToUser+"");
        //只有当fragment可见时，才进行加载数据
        if (isVisibleToUser){
            lazyLoad();
        }
    }

    /**
     * 调用懒加载
     * 第三步:在lazyLoad()方法中进行双重标记判断,通过后即可进行数据加载
     */
    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            showFirstLoading();
            loadData();
            isLazyLoaded = true;
        } else {
            //当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
            if (isLazyLoaded) {
                stopLoad();
            }
        }
    }

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLazyLoaded = false;
        isPrepared = false;
    }

    /**
     * 第一次可见时，操作该方法，可以用于showLoading操作，注意这个是全局加载loading
     */
    protected void showFirstLoading() {
        Log.i("showFirstLoading","第一次可见时show全局loading");
    }

    /**
     * 停止加载
     * 当视图已经对用户不可见并且加载过数据，但是没有加载完，而只是加载loading。
     * 如果需要在切换到其他页面时停止加载数据，可以覆写此方法。
     * 存在问题，如何停止加载网络
     */
    protected void stopLoad(){

    }

    /**
     * 第四步:定义抽象方法onLazyLoad(),具体加载数据的工作,交给子类去完成
     */
    @UiThread
    protected abstract void loadData();


}
