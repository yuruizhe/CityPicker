package com.desmond.citypicker;

import android.content.Context;

/**
 * @Todo
 * @Author desmond
 * @Date 2017/5/24
 * @Pacakge com.desmond.citypicker
 */

public class ApplicationContext
{
    public static Context applicationContext;

    public static Context getApplicationContext()
    {
        return applicationContext;
    }

    public static void setApplicationContext(Context applicationContext)
    {
        ApplicationContext.applicationContext = applicationContext;
    }
}
