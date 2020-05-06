package com.falkenapps.macgyver.main.adapters;

/*
Created by FalkenApps: Jorge Bareas on May 20016
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;


import java.util.List;


public class MenuDataAdapter extends ArrayAdapter<String> {

    private List<String> itemList;
    private final Context context;

    public MenuDataAdapter(List<String> itemList, Context ctx) {
        super(ctx, android.R.layout.simple_list_item_1, itemList);
        this.itemList = itemList;
        this.context = ctx;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.drawer_list_item, null);
        }

        String text = itemList.get(position);
        TextView text_menu_item = (TextView) v.findViewById(R.id.text_menu_item);
        text_menu_item.setText(text);

        ImageView img_menu_item = (ImageView) v.findViewById(R.id.img_menu_item);

        if (position == Constant.MENU_FIRST) {
            img_menu_item.setImageResource(R.mipmap.ic_launcher_round_small);

        } else if (position == Constant.MENU_SECOND) {
            img_menu_item.setImageResource(R.mipmap.ic_done_white_36dp);



        } else {
            img_menu_item.setImageResource(R.mipmap.ic_done_white_36dp);
        }


        return v;
    }

    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public String getItem(int position) {
        if (itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    public List<String> getItemList() {
        return itemList;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }
}
