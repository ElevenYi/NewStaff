package com.eleven.newstaff.functions.ui.activitydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.eleven.newstaff.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by eleven on 16/3/7.
 */
public class ActivityDemoActivity extends Activity {

    @OnClick(R.id.btn_activity_demo_launch)
    public void clickListener(){
        startActivity(new Intent(ActivityDemoActivity.this, ActivityBActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_demo);
        ButterKnife.bind(this);
    }
}
