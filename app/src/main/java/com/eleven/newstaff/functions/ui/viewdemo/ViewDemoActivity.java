package com.eleven.newstaff.functions.ui.viewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.eleven.newstaff.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by eleven on 16/3/8.
 */
public class ViewDemoActivity extends Activity implements View.OnClickListener{

    public static final String TAG = "ViewDemoActivity";

    @Bind(R.id.rl_view_demo_activity)
    RelativeLayout relativeLayout_RL;

    @Bind(R.id.ll_view_demo_activity)
    LinearLayout linearLayout_LL;

    @Bind(R.id.btn_view_demo_activity)
    Button btn_BTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_demo);

        ButterKnife.bind(this);

        btn_BTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG,"按钮的点击事件被触发了");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,"ViewDemoActivity的dispatchTouchEvent被调用了");
        return super.dispatchTouchEvent(ev);
    }
}
