package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;

import java.util.ArrayList;

public class CustomerCreditAdapter extends ArrayAdapter<String[]> {
    Context context;
    ArrayList<String[]> list;

    public CustomerCreditAdapter(Context context, ArrayList<String[]> list) {

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
        row = inflater.inflate(R.layout.row_credit_debtor, parent, false);

        TextView txtName = (TextView) row.findViewById(R.id.txtCusName);
        TextView txtTown = (TextView) row.findViewById(R.id.txtTown);
        TextView txtAmount = (TextView) row.findViewById(R.id.txtAmount);
        txtName.setText(list.get(position)[0]);
        txtTown.setText(list.get(position)[1]);
        txtAmount.setText(getBalance(list.get(position)[2], list.get(position)[3]));

        return row;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/

    public String getBalance(String totBal, String totBal1) {

        return String.format("%,.2f", (Double.parseDouble(totBal) - Double.parseDouble(totBal1)));
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/

}
