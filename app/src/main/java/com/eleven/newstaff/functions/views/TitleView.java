package com.eleven.newstaff.functions.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.eleven.newstaff.R;

/**
 * Created by eleven on 16/2/23.
 */
public class TitleView extends FrameLayout {

    private Button leftButton;
    private TextView titleText;


    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //如果后面的参数是null会怎么样
        LayoutInflater.from(context).inflate(R.layout.layout_general_titlle,this);
        titleText = (TextView) findViewById(R.id.tv_title_text);
        leftButton = (Button) findViewById(R.id.btn_title_left);
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }

    public void setLeftButtonText(String text) {
        this.leftButton.setText(text);
    }

    public void setTitleText(TextView titleText) {
        this.titleText = titleText;
    }

    public void setLeftButtonListener(OnClickListener l){
        leftButton.setOnClickListener(l);
    }
}
