package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.ApprOrdHedDS;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.model.TranSOHed;

import java.util.ArrayList;

public class InvoiceHistoryAdapter extends ArrayAdapter<TranSOHed> {
    Context context;
    ArrayList<TranSOHed> list;

    public InvoiceHistoryAdapter(Context context, ArrayList<TranSOHed> list) {

        super(context, R.layout.row_invoice_history, list);
        this.context = context;
        this.list = list;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_invoice_history, parent, false);

        TextView refno = (TextView) row.findViewById(R.id.refno);
        TextView date = (TextView) row.findViewById(R.id.date);
        TextView amount = (TextView) row.findViewById(R.id.amount);
        TextView customer = (TextView) row.findViewById(R.id.customer);
        TextView qty = (TextView) row.findViewById(R.id.qty);
        ImageView appr = (ImageView) row.findViewById(R.id.approved);

        DebtorDS debtorDS = new DebtorDS(context);
        refno.setText(list.get(position).getFTRANSOHED_REFNO());
        date.setText(list.get(position).getFTRANSOHED_TXNDATE());
        qty.setText("");
        amount.setText(list.get(position).getFTRANSOHED_TOTALAMT());

        customer.setText(debtorDS.getCustNameByCode(list.get(position).getFTRANSOHED_DEBCODE()));

        if (new ApprOrdHedDS(context).isApproved(list.get(position).getFTRANSOHED_REFNO()))
        {
            appr.setVisibility(View.VISIBLE);
        }
        else
        {
            appr.setVisibility(View.GONE);
        }

        return row;
    }
}
