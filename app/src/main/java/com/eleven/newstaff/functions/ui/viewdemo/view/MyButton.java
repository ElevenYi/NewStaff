package com.eleven.newstaff.functions.ui.viewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import com.eleven.newstaff.functions.ui.viewdemo.ViewDemoActivity;

/**
 * Created by eleven on 16/3/8.
 */
public class MyButton extends Button {
    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(ViewDemoActivity.TAG, "MyButton的OnTouchEvent被调用了");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(ViewDemoActivity.TAG, "MyButton的dispatchTouchEvent被调用了");
        return super.dispatchTouchEvent(event);
    }
}
