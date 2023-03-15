package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.FDDbNote;

import java.util.ArrayList;

public class CreditBreakupAdapter extends ArrayAdapter<FDDbNote> {
    Context context;
    ArrayList<FDDbNote> list;

    public CreditBreakupAdapter(Context context, ArrayList<FDDbNote> list) {

        super(context, R.layout.row_cus_credit_breakup, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row = null;
        LayoutInflater inflater = null;
        row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_cus_credit_breakup, parent, false);

        TextView txtDAte = (TextView) row.findViewById(R.id.txtDate);
        TextView txtBill = (TextView) row.findViewById(R.id.txtBillNo);
        TextView txtAmount = (TextView) row.findViewById(R.id.txtAmount);
        TextView txtPaid = (TextView) row.findViewById(R.id.txtPaid);
        TextView txtBalance = (TextView) row.findViewById(R.id.txtBalance);
        TextView txtAge = (TextView) row.findViewById(R.id.txtAge);

        txtDAte.setText(list.get(position).getFDDBNOTE_TXN_DATE());
        txtBill.setText(list.get(position).getFDDBNOTE_REFNO());
        txtAmount.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFDDBNOTE_AMT())));
        txtPaid.setText(list.get(position).getFDDBNOTE_B_AMT());
        txtBalance.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFDDBNOTE_TOT_BAL())));
        txtAge.setText(list.get(position).getFDDBNOTE_ADD_DATE());

        return row;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/


}
