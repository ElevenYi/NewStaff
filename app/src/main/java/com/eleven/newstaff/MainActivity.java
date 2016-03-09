package com.eleven.newstaff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.eleven.newstaff.functions.model.Function;
import com.eleven.newstaff.functions.ui.activitydemo.ActivityDemoActivity;
import com.eleven.newstaff.functions.ui.customerview.CusomterActivity;
import com.eleven.newstaff.functions.ui.customerview.CustomListViewActivity;
import com.eleven.newstaff.functions.ui.customerview.CustomerViewActivity;
import com.eleven.newstaff.functions.ui.DispatchEventDemoActivity;
import com.eleven.newstaff.functions.ui.PhotoWallDemoActivity;
import com.eleven.newstaff.functions.ui.SwipeMenuDemoActivity;
import com.eleven.newstaff.functions.ui.viewdemo.ViewDemoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.lv_main_activity_functions)
    ListView functionList_LV;

    List<Function> functions = new ArrayList<>();
    List<String> functionsNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_real);
        ButterKnife.bind(this);

        initParams();
        initListView();
    }

    private void initParams() {
        functions.add(new Function("CustomerView", CusomterActivity.class));
        functions.add(new Function("MyCustomView", CustomerViewActivity.class));
        functions.add(new Function("CustomListViewActivity", CustomListViewActivity.class));
        functions.add(new Function("DispatchEventDemoActivity", DispatchEventDemoActivity.class));
        functions.add(new Function("SwipeMenuDemoActivity", SwipeMenuDemoActivity.class));
        functions.add(new Function("PhotoWallDemoActivity", PhotoWallDemoActivity.class));
        functions.add(new Function("Activity生命周期及任务栈", ActivityDemoActivity.class));
        functions.add(new Function("View的事件体系", ViewDemoActivity.class));
    }

    private void initListView(){
        for (Function function : functions) {
            functionsNames.add(function.functionName);
        }
        functionList_LV.setAdapter(new ArrayAdapter<String>(this,R.layout.adapter_item_function_item,R.id.tv_adapter_item_function_name,functionsNames));
        functionList_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this,functions.get(position).className));
            }
        });
    }
}
