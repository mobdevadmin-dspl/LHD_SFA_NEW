package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;

import java.util.ArrayList;

public class SalesAchievementAdapter extends ArrayAdapter<String[]> {
    Context context;
    ArrayList<String[]> list;

    public SalesAchievementAdapter(Context context, ArrayList<String[]> list) {

        super(context, R.layout.row_cus_debt, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row = null;
        LayoutInflater inflater = null;
        row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_cus_debt, parent, false);

        TextView lblCateg = (TextView) row.findViewById(R.id.txtRefno);
        TextView lblTarget = (TextView) row.findViewById(R.id.txtDate);
        TextView lblAchiev = (TextView) row.findViewById(R.id.txtAge);
        TextView txtBal = (TextView) row.findViewById(R.id.txtAmount);

        lblCateg.setText(list.get(position)[0]);
        lblTarget.setText(list.get(position)[1]);
        lblAchiev.setText(list.get(position)[2]);
        txtBal.setText(list.get(position)[3]);

        return row;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/

}
