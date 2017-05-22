package com.desmond.citypicker.bean;

import java.io.Serializable;

/**
 * 城市基础对象，如果需要自定义可以继承此对象，并为所有字段赋值
 * @Todo
 * @Author desmond
 * @Date 2017/5/17
 * @Pacakge com.desmond.citypicker
 */

public class BaseCity implements Serializable
{
    /**
     *  城市code
     */
    private String code;
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

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
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
}
