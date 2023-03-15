package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.Debtor;

import java.util.ArrayList;


public class CustomerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    ArrayList<Debtor> list;


    public CustomerAdapter(Context context, ArrayList<Debtor> list){
        this.inflater = LayoutInflater.from(context);
        this.list = list;

    }
    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }
    @Override
    public Debtor getItem(int position) {
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
            convertView =inflater.inflate(R.layout.row_customer_listview,parent,false);

            viewHolder.code = (TextView) convertView.findViewById(R.id.debCode);
            viewHolder.name = (TextView) convertView.findViewById(R.id.debName);
            viewHolder.address = (TextView) convertView.findViewById(R.id.debAddress);

            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Debtor debtor=getItem(position);

        viewHolder.code.setText(debtor.getFDEBTOR_CODE());
        viewHolder.name.setText(debtor.getFDEBTOR_NAME());
        viewHolder.address.setText(debtor.getFDEBTOR_ADD1() + " " + debtor.getFDEBTOR_ADD2() + " " + debtor.getFDEBTOR_ADD3());

        return convertView;
    }

    private  static  class  ViewHolder{

        TextView code;
        TextView name;
        TextView address;
    }
}
