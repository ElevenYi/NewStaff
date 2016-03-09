package com.eleven.newstaff.functions.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.eleven.newstaff.R;
import com.eleven.newstaff.functions.adapter.PhotoWallAdapter;
import com.eleven.newstaff.functions.model.Images;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by eleven on 16/3/1.
 */
public class PhotoWallDemoActivity extends Activity {

    @Bind(R.id.gv_photo_wall)
    GridView photoWall_GV;

    private PhotoWallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall_demo);
        ButterKnife.bind(this);
        adapter = new PhotoWallAdapter(this,0, Images.imageThumbUrls,photoWall_GV);
        photoWall_GV.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cancelAllTask();
    }
}
