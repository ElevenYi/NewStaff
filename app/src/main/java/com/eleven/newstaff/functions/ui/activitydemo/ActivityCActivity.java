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
public class ActivityCActivity extends Activity{

    @OnClick(R.id.btn_activityc_launch_a)
    public void clickEvent(){
        startActivity(new Intent(ActivityCActivity.this,ActivityDemoActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        ButterKnife.bind(this);
    }
}
