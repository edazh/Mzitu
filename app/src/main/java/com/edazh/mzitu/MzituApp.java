package com.edazh.mzitu;

import android.app.Application;

/**
 * Created by edazh on 2018/1/10 0010.
 */

public class MzituApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }
}
