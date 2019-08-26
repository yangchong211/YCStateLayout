### YCStateLayout 状态切换
- 01.如何引用lib使用
- 02.关于中英文文档说明
- 03.案例演示动画
- 04.界面切换状态的介绍
- 05.项目设计思路




### 01.如何引用lib使用
- Add this in your root build.gradle file (not your module build.gradle file):
    ```
    allprojects {
        repositories {
            maven { url "https://jitpack.io" }
        }
    }
    ```
- Then, add the library to your module build.gradle
    ```
    implementation  'cn.yc:YCStateLib:1.2.0'
    ```



### 02.关于中英文文档说明
- 状态切换，让View状态的切换和Activity彻底分离开。用builder模式来自由的添加需要的状态View，可以设置有数据，数据为空，加载数据错误，网络错误，加载中等多种状态，并且支持自定义状态的布局。。目前已经用于新芽，投资界等正式项目中，拓展性强！！
- [Chinese中文文档](https://github.com/yangchong211/YCStateLayout/blob/master/README_CH.md)
- [English英文文档](https://github.com/yangchong211/YCStateLayout/blob/master/README.md)



### 03.案例演示动画
- ![image](https://github.com/yangchong211/YCStateLayout/blob/master/image/status.gif)



### 04.界面切换状态的介绍
#### 4.1 关于Android界面切换状态的介绍
* 怎样切换界面状态？有些界面想定制自定义状态？状态如何添加点击事件？下面就为解决这些问题！
	* 内容界面
	* 加载数据中
	* 加载数据错误
	* 加载后没有数据
	* 没有网络


#### 4.2 思路转变，抽取分离类管理几种状态
* 以前做法：
    * 直接把这些界面include到main界面中，然后动态去切换界面，后来发现这样处理不容易复用到其他项目中，而且在activity中处理这些状态的显示和隐藏比较乱
    * 利用子类继承父类特性，在父类中写切换状态，但有些界面如果没有继承父类，又该如何处理
* 现在做法：
    * 让View状态的切换和Activity彻底分离开，必须把这些状态View都封装到一个管理类中，然后暴露出几个方法来实现View之间的切换。
    * 在不同的项目中可以需要的View也不一样，所以考虑把管理类设计成builder模式来自由的添加需要的状态View
- 为何要这样？
    - 一般在需要用户等待的场景，显示一个Loading动画可以让用户知道App正在加载数据，而不是程序卡死，从而给用户较好的使用体验。
    - 当加载的数据为空时显示一个数据为空的视图、在数据加载失败时显示加载失败对应的UI并支持点击重试会比白屏的用户体验更好一些。
    - 加载中、加载失败、空数据的UI风格，一般来说在App内的所有页面中需要保持一致，也就是需要做到全局统一。
- 如何降低偶性和入侵性
    - 让View状态的切换和Activity彻底分离开，必须把这些状态View都封装到一个管理类中，然后暴露出几个方法来实现View之间的切换。
    在不同的项目中可以需要的View也不一样，所以考虑把管理类设计成builder模式来自由的添加需要的状态View。
    - 那么如何降低耦合性，让代码入侵性低。方便维护和修改，且移植性强呢？大概具备这样的条件……
        - 可以运用在activity或者fragment中
        - 不需要在布局中添加LoadingView，而是统一管理不同状态视图，同时暴露对外设置自定义状态视图方法，方便UI特定页面定制
        - 支持设置自定义不同状态视图，即使在BaseActivity统一处理状态视图管理，也支持单个页面定制
        - 在加载视图的时候像异常和空页面能否用ViewStub代替，这样减少绘制，只有等到出现异常和空页面时，才将视图给inflate出来
        - 当页面出现网络异常页面，空页面等，页面会有交互事件，这时候可以设置点击设置网络或者点击重新加载等等


#### 4.3 关于该状态切换工具优点分析
* 可以自由切换内容，空数据，异常错误，加载，网络错误等5种状态
* 父类BaseActivity直接暴露5中状态，方便子类统一管理状态切换
* 倘若有些页面想定制状态布局，也可以自由实现，很简单






### 05.项目设计思路
- StateFrameLayout是继承FrameLayout自定义布局，主要是存放不同的视图，以及隐藏和展示视图操作
- StateLayoutManager是状态管理器，主要是让开发者设置不同状态视图的view，以及切换视图状态操作
    - 几种异常状态要用ViewStub，因为在界面状态切换中loading和内容View都是一直需要加载显示的，但是其他的3个只有在没数据或者网络异常的情况下才会加载显示，所以用ViewStub来加载他们可以提高性能。
- OnRetryListener，为接口，主要是重试作用。比如加载失败了，点击视图需要重新刷新接口，则可以用到这个。开发者也可以自己设置点击事件
- 关于状态视图切换方案，目前市场有多种做法，具体可以看我的这篇博客：https://juejin.im/post/5d2f014d6fb9a07ea648a959






### 4.使用方法
* 如下所示，具体可以直接参考代码，更多可以直接查看demo
- 可以自由切换内容，空数据，异常错误，加载，网络错误等5种状态。父类BaseActivity直接暴露5中状态，方便子类统一管理状态切换，这里fragment的封装和activity差不多。
    ``` 
    /**
    * ================================================
    * 作    者：杨充
    * 版    本：1.0
    * 创建日期：2017/7/6
    * 描    述：抽取类
    * 修订历史：
    * ================================================
    */
    public abstract class BaseActivity extends AppCompatActivity {
    
        protected StatusLayoutManager statusLayoutManager;
    
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_base_view);
            initStatusLayout();
            initBaseView();
            initToolBar();
            initView();
        }
        
        //子类必须重写该方法
        protected abstract void initStatusLayout();
    
        protected abstract void initView();
    
        /**
        * 获取到布局
        */
        private void initBaseView() {
            LinearLayout ll_main = (LinearLayout) findViewById(R.id.ll_main);
            ll_main.addView(statusLayoutManager.getRootLayout());
        }
    
        //正常展示数据状态
        protected void showContent() {
            statusLayoutManager.showContent();
        }
    
        //加载数据为空时状态
        protected void showEmptyData() {
            statusLayoutManager.showEmptyData();
        }
    
        //加载数据错误时状态
        protected void showError() {
            statusLayoutManager.showError();
        }
    
        //网络错误时状态
        protected void showNetWorkError() {
            statusLayoutManager.showNetWorkError();
        }
    
        //正在加载中状态
        protected void showLoading() {
            statusLayoutManager.showLoading();
        }
    }
    ```
- 子类继承BaseActivity后，该如何操作呢？具体如下所示
    ```
    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StateLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_main)
                .emptyDataView(R.layout.activity_emptydata)
                .errorView(R.layout.activity_error)
                .loadingView(R.layout.activity_loading)
                .netWorkErrorView(R.layout.activity_networkerror)
                .build();
    }
    
    //或者添加上监听事件
    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StateLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_content_data)
                .emptyDataView(R.layout.activity_empty_data)
                .errorView(R.layout.activity_error_data)
                .loadingView(R.layout.activity_loading_data)
                .netWorkErrorView(R.layout.activity_networkerror)
                .onRetryListener(new OnRetryListener() {
                    @Override
                    public void onRetry() {
                        //为重试加载按钮的监听事件
                    }
                })
                .onShowHideViewListener(new OnShowHideViewListener() {
                    @Override
                    public void onShowView(View view, int id) {
                        //为状态View显示监听事件
                    }
    
                    @Override
                    public void onHideView(View view, int id) {
                        //为状态View隐藏监听事件
                    }
                })
                .build();
    }
    
    //如何切换状态呢？
    showContent();
    showEmptyData();
    showError();
    showLoading();
    showNetWorkError();
    
    //或者这样操作也可以
    statusLayoutManager.showLoading();
    statusLayoutManager.showContent();
    ```
- 那么如何设置状态页面的交互事件呢？当状态是加载数据失败时，点击可以刷新数据；当状态是无网络时，点击可以设置网络。代码如下所示：
    ```
    /**
    * 点击重新刷新
    */
    private void initErrorDataView() {
        statusLayoutManager.showError();
        LinearLayout ll_error_data = (LinearLayout) findViewById(R.id.ll_error_data);
        ll_error_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
                adapter.notifyDataSetChanged();
                showContent();
            }
        });
    }
    
    /**
    * 点击设置网络
    */
    private void initSettingNetwork() {
        statusLayoutManager.showNetWorkError();
        LinearLayout ll_set_network = (LinearLayout) findViewById(R.id.ll_set_network);
        ll_set_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
                startActivity(intent);
            }
        });
    }
    ```
- 那有些页面想要自定义指定的状态页面UI，又该如何操作呢？倘若有些页面想定制状态布局，也可以自由实现，很简单：
    ```
    /**
    * 自定义加载数据为空时的状态布局
    */
    private void initEmptyDataView() {
        statusLayoutManager.showEmptyData();
        //此处是自己定义的状态布局
        statusLayoutManager.showLayoutEmptyData(R.layout.activity_emptydata);
        LinearLayout ll_empty_data = (LinearLayout) findViewById(R.id.ll_empty_data);
        ll_empty_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
                adapter.notifyDataSetChanged();
                showContent();
            }
        });
    }
    ``` 


### 5.实现效果
![](https://github.com/yangchong211/YCStateLayout/blob/master/image/125771775308836257.png)
![](https://github.com/yangchong211/YCStateLayout/blob/master/image/407442243542773132.jpg)
![](https://github.com/yangchong211/YCStateLayout/blob/master/image/54463227589674008.png)
![](https://github.com/yangchong211/YCStateLayout/blob/master/image/739964693513198374.jpg)
![](https://github.com/yangchong211/YCStateLayout/blob/master/image/75707536091894445.jpg)


###  6.版本更新说明
- v1.0 更新于2017年3月28日
- v1.1 更新于2017年12月3日
- v1.1.5 更新于2018年4月25日
- v1.1.6 更新于2018年11月15日，更新targetSdkVersion为27


#### 关于其他内容介绍
![image](https://upload-images.jianshu.io/upload_images/4432347-7100c8e5a455c3ee.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#### 关于博客汇总链接
- 1.[技术博客汇总](https://www.jianshu.com/p/614cb839182c)
- 2.[开源项目汇总](https://blog.csdn.net/m0_37700275/article/details/80863574)
- 3.[生活博客汇总](https://blog.csdn.net/m0_37700275/article/details/79832978)
- 4.[喜马拉雅音频汇总](https://www.jianshu.com/p/f665de16d1eb)
- 5.[其他汇总](https://www.jianshu.com/p/53017c3fc75d)


#### 其他推荐
- 博客笔记大汇总【15年10月到至今】，包括Java基础及深入知识点，Android技术博客，Python学习笔记等等，还包括平时开发中遇到的bug汇总，当然也在工作之余收集了大量的面试题，长期更新维护并且修正，持续完善……开源的文件是markdown格式的！同时也开源了生活博客，从12年起，积累共计47篇[近20万字]，转载请注明出处，谢谢！
- 链接地址：https://github.com/yangchong211/YCBlogs
- 如果觉得好，可以star一下，谢谢！当然也欢迎提出建议，万事起于忽微，量变引起质变！



#### 关于LICENSE
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


