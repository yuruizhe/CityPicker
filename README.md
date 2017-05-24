CityPicker
===

一个仿大众点评的城市快速选择器</br>
支持页面样式修改，多元化自定义

ScreenShot
---
![](https://github.com/yuruizhe/CityPicker/blob/master/screenshot/Screenshot_2017-05-22-11-22-58.png)
![](https://github.com/yuruizhe/CityPicker/blob/master/screenshot/Screenshot_2017-05-22-11-23-08.png)
![](https://github.com/yuruizhe/CityPicker/blob/master/screenshot/Screenshot_2017-05-22-11-22-45.png)

Version Log
---
v0.1.0
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
* 支持选择城市返回自定义对象（目前已经囊括：城市名称，城市code（baidu）等）
* 提供方法支持页面样式轻度自定义。或继承CityPickerActivity重写部分UI样式
* 基础数据依赖sqlite提供高效的查询效率
* 与三方定位库解耦
* 支持沉浸式状态栏

### Use
##### Step1
``` xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```
对于Android 6.0还需要配置动态权限</br>
``` java
Manifest.permission.WRITE_EXTERNAL_STORAGE
```
##### Step2
``` xml
 <activity android:name="com.desmond.citypicker.ui.CityPickerActivity"
           android:screenOrientation="portrait">
 </activity>
```
##### Step3
``` java
      BaseCity gpsCity = new BaseCity();
        gpsCity.setCityName("南京市");
        gpsCity.setCode("315");

        Intent intent = new Intent(MainActivity.this, CityPickerActivity.class);
        Options options = new Options();

        //自定义热门城市，输入数据库中的城市id（_id）
        options.setHotCitiesId(new String[]{"2", "9", "18", "11", "66", "1", "80", "49", "100"});

        //设置定位城市
        options.setGpsCity(gpsCity);

        //设置最多显示历史点击城市数量，0为不显示历史城市
        options.setMaxHistory(6);

        // 自定义城市基础数据列表，必须放在项目的assets文件夹下，并且表结构同citypicker项目下的assets中的数据库表结构相同
        // 该方法当前为beta版本，不推荐使用
        options.setCustomDBName("xx.sqlite");

        // 设置标题栏背景
        options.setTitleBarDrawable(...);

        // 设置返回按钮图片
        options.setTitleBarBackBtnDrawable(...);

        // 设置搜索框背景
        options.setSearchViewDrawable(...);

        // 设置搜索框字体颜色
        options.setSearchViewTextColor(...);

        // 设置搜索框字体大小
        options.setSearchViewTextSize(...);

        // 设置右边检索栏字体颜色
        options.setIndexBarTextColor(...);

        // 设置右边检索栏字体大小
        options.setIndexBarTextSize(...);

        // 是否使用沉浸式状态栏，默认使用
        options.setUseImmerseBar(true);

        intent.putExtra(KEYS.OPTIONS, options);
        
        //requestCode 自己定义
        startActivityForResult(intent, 20009);
 ```
##### Step4
接收选择结果
``` java
 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || requestCode != 20009) return;
        Toast.makeText(MainActivity.this, ((BaseCity) data.getSerializableExtra(KEYS.SELECTED_RESULT)).getCityName(), Toast.LENGTH_SHORT).show();
    }
```
Thanks
---
* https://github.com/gjiazhe/WaveSideBar
* https://github.com/square/sqlbrite

Contact author
---
QQ 350248823
欢迎issues，作者看到后会第一时间回复
