package com.eleven.newstaff.functions.ui.viewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.eleven.newstaff.functions.ui.viewdemo.ViewDemoActivity;

/**
 * Created by eleven on 16/3/8.
 */
public class MyLinearLayout extends LinearLayout {


    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(ViewDemoActivity.TAG, "MyLinearLayout的OnTouchEvent被调用了");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(ViewDemoActivity.TAG, "MyLinearLayout的dispatchTouchEvent被调用了");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(ViewDemoActivity.TAG, "MyLinearLayout的onInterceptTouchEvent被调用了");
        return super.onInterceptTouchEvent(ev);
    }
}
