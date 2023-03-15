package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.ItemsDS;
import com.lankahardwared.lankahw.model.TranSODet;

import java.util.ArrayList;

/**
 * Created by Dhanushika on 1/23/2018.
 */

public class PreSalesFreeItemAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<TranSODet> list;
    Context context;

    public PreSalesFreeItemAdapter(Context context, ArrayList<TranSODet> list) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }

    @Override
    public TranSODet getItem(int position) {
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
            convertView =inflater.inflate(R.layout.row_free_issue_details,parent,false);
            viewHolder.Itemcode=(TextView)convertView.findViewById(R.id.row_item);
            viewHolder.Itemname=(TextView)convertView.findViewById(R.id.row_piece);
            viewHolder.Qty =(TextView)convertView.findViewById(R.id.row_cases);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final TranSODet tranSODet=getItem(position);
        viewHolder.Itemcode.setText(tranSODet.getFTRANSODET_ITEMCODE());
        viewHolder.Itemname.setText(new ItemsDS(context).getItemNameByCode(tranSODet.getFTRANSODET_ITEMCODE()));
        viewHolder.Qty.setText(tranSODet.getFTRANSODET_QTY());

        return convertView;
    }

    private  static  class  ViewHolder{
        RelativeLayout relativeLayout;
        TextView Itemcode;
        TextView Itemname;
        TextView Qty;


    }
}
