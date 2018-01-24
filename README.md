CityPicker
===

[![API](https://img.shields.io/badge/API-14%2B-yellow.svg?style=flat)](https://android-arsenal.com/api?level=14)</br>
一个仿大众点评的城市快速选择器，
最少只需 **一行** 代码即可启动城市选择器，
支持页面样式修改，多元化自定义

ScreenShot
---

| ![](https://github.com/yuruizhe/CityPicker/blob/master/screenshot/Screenshot_2017-05-22-11-22-58.png) | ![](https://github.com/yuruizhe/CityPicker/blob/master/screenshot/Screenshot_2017-05-22-11-23-08.png) | ![](https://github.com/yuruizhe/CityPicker/blob/master/screenshot/Screenshot_2017-05-22-11-22-45.png) |
|---|----|:---:|


Version Log
---
* ``V0.4.6``
  * 优化地理位置设置时有时会设置不成功问题
  * 修复其他若干问题
  * 修改UI默认主题色
* ``V0.4.5``
  * 修改设置位置信息方式，由之前必须在打开页面之前获取位置信息改为允许用户在打开页面后设置位置信息，具体使用方式见 [Step3](#step3)
  * 简化配置项，不需要在AndroidManifest中再注册Activity，并默认隐藏titlebar
* ``V0.4.3``
  * 修复更新数据库表结构后第一次进入会闪退问题
* ``V0.4.0``
  * 数据库表结构修改，增加了高德地图citycode
  * 设置gps城市的api略有改动见  [Step3](#step3)
* ``V0.3.3``
  * 紧急修复一个可能导致内存泄漏问题
  * 优化提高滑动检索效率
  * 隐藏下拉刷新label
* ``V0.3.1``
  * 在搜索框后面添加一个清空搜索框按钮
  * 修复搜索框中输入空格会搜索出全部城市问题
  * 修复搜索结果弹出框中文字在不同theme下显示不同颜色问题，现在已统一为黑色
  * 其他调用时参数合法性校验
* ``V0.3.0``
  * 简化api调用形式，修改为Rx形式，见[操作步骤](#use)
* ``V0.2.2``
  * 修复进入页面会闪退问题
  * 修复修改右边滑动索引栏颜色时左边拼音标签颜色未修改问题
  * 启动城市选择页面时增加一个步骤见  [Step3](#step3)
* ``V0.1.0``
  * 初始导入

Import
---
###### Maven
``` xml
  <dependency>
  <groupId>com.desmond</groupId>
  <artifactId>CityPicker</artifactId>
  <version>xxx</version>
  <type>pom</type>
</dependency>
``` 
###### Gradle
``` gradle
compile 'com.desmond:CityPicker:xxx'
```
Wiki
---
### Functions
* 支持自定义基础城市列表（beta）
* 支持历史点击城市查询
* 支持自定义热门城市列表
* 支持选择城市返回对象（目前已经囊括：城市名称，城市code（baidu、高德）等）
* 提供方法支持页面样式轻度自定义。或继承CityPickerActivity重写部分UI样式
* 基础数据依赖sqlite提供高效的查询效率
* 与三方定位库解耦
* 支持沉浸式状态栏

### Use
##### Step1

对于Android 6.0需要配置动态权限</br>
``` java
Manifest.permission.WRITE_EXTERNAL_STORAGE
```

##### Step2
启动城市选择页面及相关自定义配置
``` java
        CityPicker.with(getContext())        
        
        //是否需要显示当前城市,如果为false那么就隐藏当前城市，并且调用setGpsCityByBaidu()或setGpsCityByAMap()都不会生效，非必选项,默认为true
        .setUseGpsCity(true)

        //自定义热门城市，输入数据库中的城市id（_id），非必选项，默认为数据库中的热门城市
        .setHotCitiesId("2", "9", "18", "11", "66", "1", "80", "49", "100");

        //设置最多显示历史点击城市数量，0为不显示历史城市
        .setMaxHistory(6);

        // 自定义城市基础数据列表，必须放在项目的assets文件夹下，并且表结构同citypicker项目下的assets中的数据库表结构相同
        // 该方法当前为beta版本，不推荐使用
        .setCustomDBName("xx.sqlite");

        // 设置标题栏背景，非必选项
        .setTitleBarDrawable(...);

        // 设置返回按钮图片，非必选项
        .setTitleBarBackBtnDrawable(...);

        // 设置搜索框背景，非必选项
        .setSearchViewDrawable(...);

        // 设置搜索框字体颜色，非必选项
        .setSearchViewTextColor(...);

        // 设置搜索框字体大小，非必选项
        .setSearchViewTextSize(...);

        // 设置右边检索栏字体颜色，非必选项
        .setIndexBarTextColor(...);

        // 设置右边检索栏字体大小，非必选项
        .setIndexBarTextSize(...);

        // 是否使用沉浸式状态栏，默认使用，非必选项
        .setUseImmerseBar(true);
        
        // 回调
        .setOnCityPickerCallBack(new IOnCityPickerCheckedCallBack()
        {
           @Override
           public void onCityPickerChecked(BaseCity baseCity)
           {
               //获取选择城市编码
               baseCity.getCodeByBaidu();  //baseCity.getCodeByAMap();//高德code
        
               //获取选择城市名称
               baseCity.getCityName();
        
               // 获取选择城市拼音全拼
               baseCity.getCityPinYin();
        
               //获取选择城市拼音首字母
               baseCity.getCityPYFirst();
           }
         })
         
        .open();
      
 ```
##### Step3
获取到位置信息后调用该静态方法可以在页面打开后设置当前城市</br>
百度定位或高德定位需要自行配置，在这个库中没有集成任何与定位相关的模块
``` java
//使用百度定位
CityPicker.setGpsCityByBaidu("南京市","315");

//高德定位
CityPicker.setGpsCityByAMap("南京市","025");
```
### Be careful
* 基础数据库名称定义为：**city.sqlite**。在引入的工程中千万不可创建**同名的数据库**，否则可能会发生异常！

* 自定义基础数据库必须重写``city.sqlite``中的``tb_city`` 和 ``tb_history``, 允许增加字段，但不可删除或修改字段

项目中使用了如下三方库，如与你项目中的库冲突，请及时排除</br>
```gradle
def supportLibraryVersion = '24.2.1'
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile "com.android.support:support-v4:$supportLibraryVersion"
    compile "com.android.support:appcompat-v7:$supportLibraryVersion"
    compile "com.android.support:recyclerview-v7:$supportLibraryVersion"
    compile "com.android.support:design:$supportLibraryVersion"
    compile "com.android.support:gridlayout-v7:$supportLibraryVersion"
    compile 'com.gjiazhe:wavesidebar:1.3'
    compile 'com.squareup.sqlbrite:sqlbrite:1.1.1'
    compile 'io.reactivex:rxjava:1.2.0'
    //rx系列
    compile 'io.reactivex:rxandroid:1.2.1'

    compile 'org.greenrobot:eventbus:3.0.0'

}
```
排除示例：
```gradle
compile ('com.desmond:CityPicker:0.3.0' ){
        exclude group: 'com.android.support'
        exclude group:'com.squareup.sqlbrite'
        exclude group:'io.reactivex'
        exclude group:'io.rxandroid'
        exclude group:'org.greenrobot'
    }
```

Demo
---
手机扫描下方二维码下载demo尝鲜</br>
![](https://www.pgyer.com/app/qrcode/ecVs)

Thanks
---
* https://github.com/gjiazhe/WaveSideBar
* https://github.com/square/sqlbrite

Contact author
---
QQ 350248823 添加时注明github-citypicker</br>
欢迎issues，作者看到后会第一时间回复
