package com.eleven.newstaff.functions.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eleven.newstaff.R;

import java.util.List;

/**
 * Created by eleven on 16/2/23.
 */
public class MyAdapter extends ArrayAdapter<String> {

    public MyAdapter(Context context,int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item_my_listview,null);
        }else{
            view = convertView;
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_my_listview_item_text);
        textView.setText(getItem(position));
        return view;
    }
}
