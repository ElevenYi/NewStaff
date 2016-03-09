package com.eleven.newstaff.functions.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.eleven.newstaff.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by eleven on 16/2/24.
 */
public class DispatchEventDemoActivity extends Activity {

    @Bind(R.id.btn_btn1)
    Button btn1;
    @Bind(R.id.btn_btn2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_event_demo);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ml_my_layout, R.id.btn_btn1, R.id.btn_btn2})
    public void clickListener(View v) {
        switch (v.getId()) {
            case R.id.ml_my_layout:
                Log.d("TAG", "myLayout on touch");
                break;
            case R.id.btn_btn1:
                Log.d("TAG", "You clicked button1");
                break;
            case R.id.btn_btn2:
                Log.d("TAG", "You clicked button2");
                break;
        }
    }
}
