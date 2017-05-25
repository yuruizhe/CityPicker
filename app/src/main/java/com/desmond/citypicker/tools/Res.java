package com.desmond.citypicker.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;


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
    public static String string(Context context,int string)
    {
        return context.getResources().getString(string);
    }

    /**
     * 获取资源文件中的文本
     *
     * @param string 资源id
     * @param args   填充占位符的参数
     * @return
     */
    public static String string(Context context,int string, Object... args)
    {
        return context.getResources().getString(string, args);
    }

    /**
     * 获取资源文件中的尺寸配置
     *
     * @param dimen 资源id
     * @return
     */
    public static float dimen(Context context,int dimen)
    {
        return PxConvertUtil.px2dip(context,dimenPx(context,dimen));
    }

    /**
     * 获取资源文件中尺寸配置，返回的是将dp转换为px的数值
     *
     * @param dimen 资源id
     * @return
     */
    public static int dimenPx(Context context,int dimen)
    {
        return context.getResources().getDimensionPixelSize(dimen);
    }

    /**
     * 获取颜色
     *
     * @param color 资源id
     * @return
     */
    public static int color(Context context,int color)
    {
        return context.getResources().getColor(color);
    }


    public static Drawable drawable(Context context,int drawable)
    {
        return context.getResources().getDrawable(drawable);
    }

}
