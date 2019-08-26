## YCStateLayout State Switching


### 0.Dependency
#### 0.1 Used
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


#### 0.2 Basic introduction
##### Function introduction
- State switching, so that the View state switch and Activity completely separate. Using builder mode to freely add the required state View, can set data, data is empty, load data error, network error, load and other states, and support the layout of custom state. At present has been used in other formal projects, strong expansion!


##### About language
- [Chinese中文文档](https://github.com/yangchong211/YCStateLayout/blob/master/README_CH.md)
- [English英文文档](https://github.com/yangchong211/YCStateLayout/blob/master/README.md)



#### 0.3 Case demonstration animation
![image](https://github.com/yangchong211/YCStateLayout/blob/master/image/status.gif)


### 1.An introduction to the switching State of Android Interface
#### 1.1 Problems encountered in development
- How to switch the interface state? 
- Some interfaces want to customize custom state?
- How does the state add a click event?


#### 1.2 You can set up more than 5 state View
- content interface view
- loading data view
- loading data error view
- no data after loading view
- no network view


### 2.Train of thought change, extract separate class management several kinds of state
#### 2.1 Previous practice：
- Directly include these interfaces into the main interface, then dynamically switch the interface, later found that this processing is not easy to reuse in other projects, and in activity to deal with the display and hiding of these states is messy
- Using the child class to inherit the parent property, write the switching state in the parent class, but what can some interfaces do if they do not inherit the parent class


#### 2.2 Present practice：
- The switching of View states is completely separated from Activity. The View of these states must be encapsulated in a management class and several methods are exposed to implement the switching between View.
- The View that can be required varies from project to project, so consider designing the management class as an builder schema to freely add the required state View


### 3.An Analysis of the advantages of this State switching tool
* Free to switch content, empty data, exception error, load, network error, etc.
* The parent class BaseActivity directly exposes the state in 5, making it easy for subclasses to uniformly manage state switching
* If some pages want to customize the state layout, they can also be implemented freely, simple



### 4.usage method
#### 4.1 Specific use
- As shown below, you can refer directly to the code, and more directly see the demo
    ```
    statusLayoutManager = StateLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_content)
                .emptyDataView(R.layout.activity_emptydata)
                .errorView(R.layout.activity_error)
                .loadingView(R.layout.activity_loading)
                .netWorkErrorView(R.layout.activity_networkerror)
                .build();
    
    
    /**
     * Click to refresh the data
     */
    private void initEmptyDataView() {
        statusLayoutManager.showEmptyData();
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
    
    /**
     * Click to refresh
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
        });Click to refresh
    }
    
    /**
     * Click set up the network
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



### 5.Realization effect
![](https://github.com/yangchong211/YCStateLayout/blob/master/image/125771775308836257.png)
![](https://github.com/yangchong211/YCStateLayout/blob/master/image/407442243542773132.jpg)
![](https://github.com/yangchong211/YCStateLayout/blob/master/image/54463227589674008.png)
![](https://github.com/yangchong211/YCStateLayout/blob/master/image/739964693513198374.jpg)
![](https://github.com/yangchong211/YCStateLayout/blob/master/image/75707536091894445.jpg)


###  6.About other
#### 6.1 Update
- v1.0 Updated on 28 March 2017
- v1.1 Updated on 3 December 2017
- v1.1.5 Updated on 25 April 2018
- v1.1.6 Updated on November 15, 2018, updating targetSdkVersion to 27


#### 6.2 about 支付宝
![image](https://upload-images.jianshu.io/upload_images/4432347-7100c8e5a455c3ee.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#### 6.3 About blog Summary links
- 1.[Technology blog summary](https://www.jianshu.com/p/614cb839182c)
- 2.[Open source project summary](https://blog.csdn.net/m0_37700275/article/details/80863574)
- 3.[Life blog summary](https://blog.csdn.net/m0_37700275/article/details/79832978)


#### 6.4 About recommend
- Great summary of blog notes, including Java basics and in-depth knowledge, Android technology blog, Python learning notes, and so on, but also includes the usual development of bug summary, of course, also collected a large number of interview questions after work. Long-term update, maintenance and revision, continuous improvement. Open source files are in markdown format!
- chained address ： https://github.com/yangchong211/YCBlogs
- If you feel good, you can star, thank you! Of course, you are also welcome to put forward suggestions, everything starts at a slight, quantitative change causes qualitative change!


#### 6.4 About LICENSE
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


