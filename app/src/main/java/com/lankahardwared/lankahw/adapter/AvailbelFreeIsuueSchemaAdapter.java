package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.FreeHed;

import java.util.ArrayList;

/**
 * Created by Dhanushika on 1/17/2018.
 */

public class AvailbelFreeIsuueSchemaAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    ArrayList<FreeHed> list;

    public AvailbelFreeIsuueSchemaAdapter(Context context, ArrayList<FreeHed> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }



    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }

    @Override
    public FreeHed getItem(int position) {
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
            convertView =inflater.inflate(R.layout.row_free_schema_lv,parent,false);
            viewHolder.relativeLayout=(RelativeLayout)convertView.findViewById(R.id.rl_ly);
            viewHolder.Itemcode=(TextView)convertView.findViewById(R.id.txtItemNameRow);
            viewHolder.ItemQty=(TextView)convertView.findViewById(R.id.txtItemQtyRow);
            viewHolder.FreeQty=(TextView)convertView.findViewById(R.id.txtfreeqtyRow);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.Itemcode.setText(list.get(position).getFFREEHED_DISC_DESC());
        int itemQty = (int) Float.parseFloat(list.get(position).getFFREEHED_ITEM_QTY());
        int freeQty = (int) Float.parseFloat(list.get(position).getFFREEHED_FREE_IT_QTY());


      //  Log.d("<><><><><<<>>>>","" +freeQty);
        viewHolder.ItemQty.setText(itemQty +"");
        viewHolder.FreeQty.setText(freeQty +"");
        return convertView;
    }


    private  static  class  ViewHolder{
      RelativeLayout relativeLayout;
        TextView Itemcode;
        TextView ItemQty;
        TextView FreeQty;


    }
}
