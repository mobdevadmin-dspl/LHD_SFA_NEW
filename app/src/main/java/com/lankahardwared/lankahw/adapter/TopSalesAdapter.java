package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.TopSales;

import java.util.ArrayList;

/**
 * Created by Sathiyaraja on 2/14/2018.
 */

public class TopSalesAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    ArrayList<TopSales> list;


    public TopSalesAdapter(Context context, ArrayList<TopSales> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;

    }

    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }

    @Override
    public TopSales getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.row_top_sale, parent, false);

            viewHolder.Tdate = (TextView) convertView.findViewById(R.id.Tdate);
            viewHolder.Tcustomer = (TextView) convertView.findViewById(R.id.Tcustomer);
            viewHolder.Titem = (TextView) convertView.findViewById(R.id.Titem);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            convertView.setBackgroundColor(Color.parseColor("#F32104"));
        }

        final TopSales topSales = list.get(position);
        viewHolder.Tdate.setText(topSales.getTopDate());
        viewHolder.Tcustomer.setText(topSales.getTopCustomer());
        viewHolder.Titem.setText(topSales.getTopItem());


        return convertView;
    }

    private static class ViewHolder {

        TextView Tdate;
        TextView Tcustomer;
        TextView Titem;

    }

}