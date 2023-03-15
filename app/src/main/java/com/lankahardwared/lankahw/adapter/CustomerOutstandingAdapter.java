package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;

import com.lankahardwared.lankahw.model.mapper.FOtherTransactions;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CustomerOutstandingAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    ArrayList<FOtherTransactions> list;


    public CustomerOutstandingAdapter(Context context, ArrayList<FOtherTransactions> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;

    }

    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }

    @Override
    public FOtherTransactions getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        CustomerOutstandingAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new CustomerOutstandingAdapter.ViewHolder();

            convertView = inflater.inflate(R.layout.row_cus_debt, parent, false);
            viewHolder.layout = (RelativeLayout) convertView.findViewById(R.id.linearLayout);
            viewHolder.txtRefno = (TextView) convertView.findViewById(R.id.txtRefno);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            viewHolder.txtAge = (TextView) convertView.findViewById(R.id.txtAge);
            viewHolder.txtAmount = (TextView) convertView.findViewById(R.id.txtAmount);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (CustomerOutstandingAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.txtRefno.setText(list.get(position).getRefno());
        viewHolder.txtDate.setText(list.get(position).getTxnDate());
        viewHolder.txtAge.setText(list.get(position).getTxnType());
        DecimalFormat decim = new DecimalFormat("#,###.##");
        viewHolder.txtAmount.setText("("+decim.format(Double.parseDouble(list.get(position).getAmount()))+")");

        return convertView;
    }

    private static class ViewHolder {
        RelativeLayout layout;
        TextView txtRefno;
        TextView txtDate;
        TextView txtAge;
        TextView txtAmount;
    }
}
