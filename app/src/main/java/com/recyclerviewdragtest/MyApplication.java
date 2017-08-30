package com.recyclerviewdragtest;

import android.app.Application;
import android.content.Context;

import com.zxy.tiny.Tiny;

/**
 * Created by hyc on 2017/8/29 17:38
 */

public class MyApplication extends Application {


    private static MyApplication app;
    private Context mContext;

    public static MyApplication getInstance() {
        return app;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mContext = getApplicationContext();
        Tiny.getInstance().init(this);
    }

}
