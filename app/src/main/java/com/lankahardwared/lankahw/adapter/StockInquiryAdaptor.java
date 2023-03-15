package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.Locations;
import com.lankahardwared.lankahw.model.StockInfo;

import java.util.ArrayList;


public class StockInquiryAdaptor  extends BaseAdapter {
    private LayoutInflater inflater;
    ArrayList<StockInfo> list;


    public StockInquiryAdaptor (Context context, ArrayList<StockInfo> list){
        this.inflater = LayoutInflater.from(context);
        this.list = list;

    }
    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }
    @Override
    public StockInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView ==null) {
            viewHolder = new ViewHolder();
            convertView =inflater.inflate(R.layout.row_stock_inquiry,parent,false);

            viewHolder.itemcode = (TextView) convertView.findViewById(R.id.row_itemcode);
            viewHolder.itemname = (TextView) convertView.findViewById(R.id.row_itemname);
            viewHolder.qty = (TextView) convertView.findViewById(R.id.row_qty);

            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final StockInfo stockInfo=getItem(position);

        viewHolder.itemcode.setText(stockInfo.getStock_Itemcode());
        viewHolder.itemname.setText(stockInfo.getStock_Itemname());
        viewHolder.qty.setText(stockInfo.getStock_Qoh());

        return convertView;
    }

    private  static  class  ViewHolder{

        TextView itemcode;
        TextView itemname;
        TextView qty;
    }
}
