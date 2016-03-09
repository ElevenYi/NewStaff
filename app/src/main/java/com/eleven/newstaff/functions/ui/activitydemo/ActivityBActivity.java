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
public class ActivityBActivity extends Activity {

    @OnClick(R.id.btn_activityb_launch_c)
    public void clickEvent(){
        startActivity(new Intent(ActivityBActivity.this, ActivityCActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        ButterKnife.bind(this);
    }
}
