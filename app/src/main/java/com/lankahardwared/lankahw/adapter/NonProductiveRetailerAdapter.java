package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.Debtor;

import java.util.ArrayList;

public class NonProductiveRetailerAdapter extends ArrayAdapter<Debtor> {

    Context context;
    ArrayList<Debtor> list;

    public NonProductiveRetailerAdapter(Context context, ArrayList<Debtor> list) {
        super(context, R.layout.row_nonprod_retailer_detail, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_nonprod_retailer_detail, parent, false);

        TextView Itemname = (TextView) row.findViewById(R.id.row_nonprd_custname);
        TextView Itemcode = (TextView) row.findViewById(R.id.row_nonprd_cuscode);

        Itemname.setText(list.get(position).getFDEBTOR_NAME());
        Itemcode.setText(list.get(position).getFDEBTOR_CODE());

        return row;
    }
}
