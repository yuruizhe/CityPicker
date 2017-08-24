package com.desmond.citypicker.bean;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.desmond.citypicker.R;
import com.desmond.citypicker.tools.PxConvertUtil;
import com.desmond.citypicker.tools.Res;

/**
 * @Todo
 * @Author desmond
 * @Date 2017/5/21
 * @Pacakge com.desmond.citypicker.bean
 */

public class Options implements  Parcelable
{
    /**
     * 是否需要显示当前城市
     */
    protected boolean useGpsCity;

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
    protected boolean useImmerseBar;

    private Context context;

    public boolean isUseGpsCity()
    {
        return useGpsCity;
    }

    public void setUseGpsCity(boolean useGpsCity)
    {
        this.useGpsCity = useGpsCity;
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
        return Res.drawable(context,titleBarDrawable);
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
        this.searchViewTextColor = Res.color(context,searchViewTextColor);
    }


    public Drawable getSearchViewDrawable()
    {
        return Res.drawable(context,searchViewDrawable);
    }

    public void setSearchViewDrawable(@DrawableRes int searchViewDrawable)
    {
        this.searchViewDrawable = searchViewDrawable;
    }

    public Drawable getTitleBarBackBtnDrawable()
    {
        return Res.drawable(context,titleBarBackBtnDrawable);
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
        this.indexBarTextSize = PxConvertUtil.sp2px(context,indexBarTextSize);
    }

    public int getIndexBarTextColor()
    {
        return indexBarTextColor;
    }

    public void setIndexBarTextColor(@ColorRes int indexBarTextColor)
    {
        this.indexBarTextColor = Res.color(context,indexBarTextColor);
    }

    public boolean isUseImmerseBar()
    {
        return useImmerseBar;
    }

    public void setUseImmerseBar(boolean useImmerseBar)
    {
        this.useImmerseBar = useImmerseBar;
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public Options(Context context)
    {
        setContext(context);
        setUseGpsCity(true);
        setHotCitiesId(null);
        setCustomDBName("city.sqlite");
        setMaxHistory(12);

        setTitleBarDrawable(R.color.theme_main_color);
        setSearchViewTextSize(15);

        setSearchViewTextColor(R.color.black);
        setSearchViewDrawable(R.drawable.header_city_bg);
        setTitleBarBackBtnDrawable(R.drawable.back_normal);

        setIndexBarTextSize(14);
        setIndexBarTextColor(R.color.theme_vice2_color);
        setUseImmerseBar(true);
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeByte(this.useGpsCity ? (byte) 1 : (byte) 0);
        dest.writeStringArray(this.hotCitiesId);
        dest.writeString(this.customDBName);
        dest.writeInt(this.maxHistory);
        dest.writeInt(this.titleBarDrawable);
        dest.writeInt(this.searchViewTextSize);
        dest.writeInt(this.searchViewTextColor);
        dest.writeInt(this.searchViewDrawable);
        dest.writeInt(this.titleBarBackBtnDrawable);
        dest.writeFloat(this.indexBarTextSize);
        dest.writeInt(this.indexBarTextColor);
        dest.writeByte(this.useImmerseBar ? (byte) 1 : (byte) 0);
    }

    protected Options(Parcel in)
    {
        this.useGpsCity =  in.readByte() != 0;
        this.hotCitiesId = in.createStringArray();
        this.customDBName = in.readString();
        this.maxHistory = in.readInt();
        this.titleBarDrawable = in.readInt();
        this.searchViewTextSize = in.readInt();
        this.searchViewTextColor = in.readInt();
        this.searchViewDrawable = in.readInt();
        this.titleBarBackBtnDrawable = in.readInt();
        this.indexBarTextSize = in.readFloat();
        this.indexBarTextColor = in.readInt();
        this.useImmerseBar = in.readByte() != 0;
        this.context = in.readParcelable(Context.class.getClassLoader());
    }

    public static final Creator<Options> CREATOR = new Creator<Options>()
    {
        @Override
        public Options createFromParcel(Parcel source)
        {
            return new Options(source);
        }

        @Override
        public Options[] newArray(int size)
        {
            return new Options[size];
        }
    };
}
