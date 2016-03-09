package com.eleven.newstaff.basic;

import android.app.Application;

import com.eleven.newstaff.basic.interfaces.VolleyHttpUtil;

/**
 * Created by eleven on 16/3/2.
 */
public class MyApplication extends Application {

    public static final String TAG = "MyApplication";

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        VolleyHttpUtil.getInstance().setContext(getApplicationContext());
//        String processName =
//        Log.d(TAG, "application start, process name:" + );
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

}
