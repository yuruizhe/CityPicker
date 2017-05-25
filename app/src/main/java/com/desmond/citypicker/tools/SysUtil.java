/*
 * 文 件 名:  SysUtil.java
 * 版    权:  Copyright (c) 2006-2014 ICS&S Inc,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Desmond
 * 修改时间:  2014-10-16
 * 修改版本号:  <版本编号>
 * 修改履历:  <修改内容>
 */
package com.desmond.citypicker.tools;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * 系统工具类
 *
 * @author Desmond
 * @version [版本号, 2014-10-16]
 */
public class SysUtil
{

    private static int statusBarHeight;

    private static int screenWidth;

    public static String getAppId(Context context)
    {
        return context.getApplicationInfo().packageName;
    }


    /**
     * 获取屏幕宽度
     *
     * @return int
     * @author Desmond 2014-11-10 上午10:34:51
     */
    public static int getScreenWidth(Context context)
    {
        if (screenWidth > 0) return screenWidth;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        return screenWidth;
    }


    /**
     * 隐藏键盘
     *
     * @author Desmond 2015-11-25 下午10:21:47
     */
    public static void hideInput(Activity activity)
    {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager == null || activity.getCurrentFocus() == null)
            return;
        manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 获取状态栏高度（px)
     *
     * @return
     */
    public static int getStatusBarHeight(Context context)
    {
        if (statusBarHeight <= 0)
        {
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0, sbar = 0;
            try
            {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e1)
            {
                e1.printStackTrace();
            }

        }
        return statusBarHeight;
    }

}
