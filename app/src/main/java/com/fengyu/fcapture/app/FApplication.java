package com.fengyu.fcapture.app;

import android.app.Application;

import com.fengyu.fcapture.record.FileManger;

/**
 * Created by Administrator on 2017/2/9.
 */

public class FApplication extends Application {

    private static Application singleApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        singleApplication = this;
        FileManger.getInstance().init(this);
    }

    public static Application getApplication(){
        return singleApplication;
    }
}
