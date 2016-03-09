package com.eleven.newstaff.functions.ui.viewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.eleven.newstaff.functions.ui.viewdemo.ViewDemoActivity;

/**
 * Created by eleven on 16/3/8.
 */
public class MyRelativeLayout extends RelativeLayout {


    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(ViewDemoActivity.TAG,"MyRelatviveLayout的OnTouchEvent被调用了");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(ViewDemoActivity.TAG,"MyRelatviveLayout的onInterceptTouchEvent被调用了");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(ViewDemoActivity.TAG,"MyRelatviveLayout的dispatchTouchEvent被调用了");
        return super.dispatchTouchEvent(ev);
    }
}
