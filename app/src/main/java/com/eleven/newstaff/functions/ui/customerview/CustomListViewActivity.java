package com.eleven.newstaff.functions.ui.customerview;

import android.app.Activity;
import android.os.Bundle;

import com.eleven.newstaff.R;
import com.eleven.newstaff.functions.adapter.MyAdapter;
import com.eleven.newstaff.functions.views.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by eleven on 16/2/23.
 */
public class CustomListViewActivity extends Activity {

    @Bind(R.id.mlv_clva)
    MyListView lv_CLVA;

    private List<String> contentList = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view);
        ButterKnife.bind(this);

        initListView();
        lv_CLVA.setListener(new MyListView.OnDeleteListener() {
            @Override
            public void onDelete(int x) {
                contentList.remove(x);
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new MyAdapter(this,0,contentList);
        lv_CLVA.setAdapter(adapter);
    }

    private void initListView() {
        contentList.add("Content Item 1");
        contentList.add("Content Item 2");
        contentList.add("Content Item 3");
        contentList.add("Content Item 4");
        contentList.add("Content Item 5");
        contentList.add("Content Item 6");
        contentList.add("Content Item 7");
        contentList.add("Content Item 8");
        contentList.add("Content Item 9");
        contentList.add("Content Item 10");
        contentList.add("Content Item 11");
        contentList.add("Content Item 12");
        contentList.add("Content Item 13");
        contentList.add("Content Item 14");
        contentList.add("Content Item 15");
        contentList.add("Content Item 16");
        contentList.add("Content Item 17");
        contentList.add("Content Item 18");
        contentList.add("Content Item 19");
        contentList.add("Content Item 20");
    }
}
