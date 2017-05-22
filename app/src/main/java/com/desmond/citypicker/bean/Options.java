package com.desmond.citypicker.bean;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.desmond.citypicker.R;
import com.desmond.citypicker.tools.PxConvertUtil;
import com.desmond.citypicker.tools.Res;

import java.io.Serializable;

/**
 * @Todo
 * @Author desmond
 * @Date 2017/5/21
 * @Pacakge com.desmond.citypicker.bean
 */

public class Options implements Serializable
{
    /**
     * 定位城市
     */
    protected BaseCity gpsCity;

    /**
     * 热门城市列表
     */
    protected String[] hotCitiesId;
    /**
     * 自定义的数据库名称,sqlite必须放在项目的assets
     */
    protected String customDBName;
    /**
     * 最大历史城市数量
     */
    protected int maxHistory;

    /**
     * 标题栏背景
     */
    protected int titleBarDrawable;

    /**
     * 搜索框字体大小（sp）
     */
    protected int searchViewTextSize;

    /**
     * 搜索框字体颜色
     */
    protected int searchViewTextColor;

    /**
     * 搜索框背景
     */
    protected int searchViewDrawable;

    /**
     * 返回按钮背景
     */
    protected int titleBarBackBtnDrawable;

    /**
     * 标题栏高度（dp）
     */
//    protected float titleBarHeight;

    /**
     * 检索栏字体大小（sp）
     */
    protected float indexBarTextSize;

    /**
     * 检索栏字体颜色
     */
    protected int indexBarTextColor;


    /**
     * 是否使用沉浸式状态栏
     */
    protected  boolean useImmerseBar;

    public BaseCity getGpsCity()
    {
        return gpsCity;
    }

    public void setGpsCity(BaseCity gpsCity)
    {
        this.gpsCity = gpsCity;
    }

    public String[] getHotCitiesId()
    {
        return hotCitiesId;
    }

    public void setHotCitiesId(String[] hotCitiesId)
    {
        this.hotCitiesId = hotCitiesId;
    }

    public String getCustomDBName()
    {
        return customDBName;
    }

    public void setCustomDBName(String customDBName)
    {
        this.customDBName = customDBName;
    }

    public int getMaxHistory()
    {
        return maxHistory;
    }

    public void setMaxHistory(int maxHistory)
    {
        this.maxHistory = maxHistory;
    }

    public Drawable getTitleBarDrawable()
    {
        return Res.drawable(titleBarDrawable);
    }


    public void setTitleBarDrawable(@DrawableRes int titleBarDrawable)
    {
        this.titleBarDrawable = titleBarDrawable;
    }

    public int getSearchViewTextSize()
    {
        return searchViewTextSize;
    }

    public void setSearchViewTextSize(int searchViewTextSize)
    {
        this.searchViewTextSize = searchViewTextSize;
    }

    public int getSearchViewTextColor()
    {
        return searchViewTextColor;
    }

    public void setSearchViewTextColor(@ColorRes int searchViewTextColor)
    {
        this.searchViewTextColor = Res.color(searchViewTextColor);
    }


    public Drawable getSearchViewDrawable()
    {
        return Res.drawable(searchViewDrawable);
    }

    public void setSearchViewDrawable(@DrawableRes int searchViewDrawable)
    {
        this.searchViewDrawable = searchViewDrawable;
    }

    public Drawable getTitleBarBackBtnDrawable()
    {
        return Res.drawable(titleBarBackBtnDrawable);
    }


    public void setTitleBarBackBtnDrawable(@DrawableRes int titleBarBackBtnDrawable)
    {
        this.titleBarBackBtnDrawable = titleBarBackBtnDrawable;
    }

    public float getIndexBarTextSize()
    {
        return indexBarTextSize;
    }

    public void setIndexBarTextSize(float indexBarTextSize)
    {
        this.indexBarTextSize = PxConvertUtil.sp2px(indexBarTextSize);
    }

    public int getIndexBarTextColor()
    {
        return indexBarTextColor;
    }

    public void setIndexBarTextColor(@ColorRes int indexBarTextColor)
    {
        this.indexBarTextColor = Res.color(indexBarTextColor);
    }

    public boolean isUseImmerseBar()
    {
        return useImmerseBar;
    }

    public void setUseImmerseBar(boolean useImmerseBar)
    {
        this.useImmerseBar = useImmerseBar;
    }

    public Options()
    {
        this.gpsCity = null;
        this.hotCitiesId = null;
        this.customDBName = "city.sqlite";
        this.maxHistory = 12;

        setTitleBarDrawable(R.color.theme_main_color);
        this.searchViewTextSize = 15;

        setSearchViewTextColor(R.color.black);
        setSearchViewDrawable(R.drawable.header_city_bg);
        setTitleBarBackBtnDrawable(R.drawable.press_def_bg);
//        this.titleBarHeight = Res.dimen(R.dimen.title_bar_height);
        setIndexBarTextSize(14);
        this.indexBarTextColor = Res.color(R.color.theme_vice2_color);
        this.useImmerseBar = true;
    }
}
