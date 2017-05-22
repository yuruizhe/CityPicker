package com.desmond.citypicker;

import android.app.Application;

/**
 * @Todo
 * @Author desmond
 * @Date 2017/5/17
 * @Pacakge com.desmond.citypicker
 */

public class MyApplication extends Application
{
    protected static MyApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getDefault()
    {
        return instance;
    }
}
