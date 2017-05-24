package com.desmond.citypicker.tools;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.desmond.citypicker.ApplicationContext;


/**
 * 获取资源文件帮助
 *
 * @Todo
 * @Author desmond
 * @Date 2017/4/25
 */

public class Res
{
    /**
     * 获取资源文件中的文本
     *
     * @param string 资源id
     * @return
     */
    public static String string(int string)
    {
        return getResources().getString(string);
    }

    /**
     * 获取资源文件中的文本
     *
     * @param string 资源id
     * @param args   填充占位符的参数
     * @return
     */
    public static String string(int string, Object... args)
    {
        return getResources().getString(string, args);
    }

    /**
     * 获取资源文件中的尺寸配置
     *
     * @param dimen 资源id
     * @return
     */
    public static float dimen(int dimen)
    {
        return PxConvertUtil.px2dip(dimenPx(dimen));
    }

    /**
     * 获取资源文件中尺寸配置，返回的是将dp转换为px的数值
     *
     * @param dimen 资源id
     * @return
     */
    public static int dimenPx(int dimen)
    {
        return getResources().getDimensionPixelSize(dimen);
    }

    /**
     * 获取颜色
     *
     * @param color 资源id
     * @return
     */
    public static int color(int color)
    {
        return getResources().getColor(color);
    }


    public static Drawable drawable(int drawable)
    {
        return getResources().getDrawable(drawable);
    }

    public static Resources getResources()
    {
        return ApplicationContext.getApplicationContext().getResources();
    }
}
