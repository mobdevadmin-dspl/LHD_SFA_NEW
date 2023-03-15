package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.Product;

import java.util.ArrayList;

public class SelectedProductAdapter extends ArrayAdapter<Product> {
    Context context;
    ArrayList<Product> list;

    public SelectedProductAdapter(Context context, ArrayList<Product> list) {

        super(context, R.layout.row_order_details, list);
        this.context = context;
        this.list = list;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_order_details, parent, false);

        TextView item = (TextView) row.findViewById(R.id.row_item);
        TextView Qty = (TextView) row.findViewById(R.id.row_cases);
        TextView Amt = (TextView) row.findViewById(R.id.row_piece);

        item.setText(list.get(position).getFPRODUCT_ITEMNAME());
        Qty.setText(list.get(position).getFPRODUCT_QTY());
        double amt = Double.parseDouble(list.get(position).getFPRODUCT_QTY()) * Double.parseDouble(list.get(position).getFPRODUCT_PRICE());
        ;
        Amt.setText(String.format("%.2f", amt));
        return row;
    }
}
