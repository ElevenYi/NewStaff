package com.eleven.newstaff.functions.ui.customerview;

import android.app.Activity;
import android.os.Bundle;

import com.eleven.newstaff.R;
import com.eleven.newstaff.functions.manager.HttpRequest;

/**
 * Created by eleven on 16/2/22.
 */
public class CusomterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);

        HttpRequest.loginTest(this);
    }
}
