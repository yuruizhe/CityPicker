package com.desmond.citypicker.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 城市基础对象，如果需要自定义可以继承此对象，并为所有字段赋值
 * @Todo
 * @Author desmond
 * @Date 2017/5/17
 * @Pacakge com.desmond.citypicker
 */
public class BaseCity implements Parcelable
{
    /**
     *  城市code(baidu)
     */
    private String codeByBaidu;
    /**
     *  城市code(高德)
     */
    private String codeByAMap;
    /**
     *  城市名称
     */
    private String cityName;
    /**
     * 城市拼音全称
     */
    private String cityPinYin;
    /**
     * 城市拼音首字母
     */
    private String cityPYFirst;
    /**
     * 主键id
     */
    private String id;
    /**
     *  是否为热门城市
     */
    private boolean isHot;

    public String getCodeByBaidu()
    {
        return codeByBaidu;
    }

    public void setCodeByBaidu(String codeByBaidu)
    {
        this.codeByBaidu = codeByBaidu;
    }

    public String getCodeByAMap()
    {
        return codeByAMap;
    }

    public void setCodeByAMap(String codeByAMap)
    {
        this.codeByAMap = codeByAMap;
    }

    public boolean isHot()
    {
        return isHot;
    }

    public void setHot(boolean hot)
    {
        isHot = hot;
    }

    public String  getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCityPYFirst()
    {
        return cityPYFirst;
    }

    public void setCityPYFirst(String cityPYFirst)
    {
        this.cityPYFirst = cityPYFirst;
    }



    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getCityPinYin()
    {
        return cityPinYin;
    }

    public void setCityPinYin(String cityPinYin)
    {
        this.cityPinYin = cityPinYin;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.codeByBaidu);
        dest.writeString(this.codeByAMap);
        dest.writeString(this.cityName);
        dest.writeString(this.cityPinYin);
        dest.writeString(this.cityPYFirst);
        dest.writeString(this.id);
        dest.writeByte(this.isHot ? (byte) 1 : (byte) 0);
    }

    public BaseCity()
    {
    }

    protected BaseCity(Parcel in)
    {
        this.codeByBaidu = in.readString();
        this.codeByAMap = in.readString();
        this.cityName = in.readString();
        this.cityPinYin = in.readString();
        this.cityPYFirst = in.readString();
        this.id = in.readString();
        this.isHot = in.readByte() != 0;
    }

    public static final Parcelable.Creator<BaseCity> CREATOR = new Parcelable.Creator<BaseCity>()
    {
        @Override
        public BaseCity createFromParcel(Parcel source)
        {
            return new BaseCity(source);
        }

        @Override
        public BaseCity[] newArray(int size)
        {
            return new BaseCity[size];
        }
    };
}
