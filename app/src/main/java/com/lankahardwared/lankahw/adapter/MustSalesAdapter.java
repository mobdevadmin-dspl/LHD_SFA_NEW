package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.Items;

import java.util.ArrayList;

/**
 * Created by Dhanushika on 1/17/2018.
 */

public class MustSalesAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    ArrayList<Items> list;

    public MustSalesAdapter(Context context, ArrayList<Items> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }



    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }

    @Override
    public Items getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView ==null){
            viewHolder =new ViewHolder();
            convertView =inflater.inflate(R.layout.row_must_sales_lv,parent,false);
            viewHolder.relativeLayout=(RelativeLayout)convertView.findViewById(R.id.rl_ly);
            viewHolder.Itemcode=(TextView)convertView.findViewById(R.id.txtItemCodeRow);
            viewHolder.Itemname=(TextView)convertView.findViewById(R.id.txtItemNameRow);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.Itemcode.setText(list.get(position).getFITEM_ITEM_CODE());
        viewHolder.Itemname.setText(list.get(position).getFITEM_ITEM_NAME());
        return convertView;
    }


    private  static  class  ViewHolder{
      RelativeLayout relativeLayout;
        TextView Itemcode;
        TextView Itemname;


    }
}
