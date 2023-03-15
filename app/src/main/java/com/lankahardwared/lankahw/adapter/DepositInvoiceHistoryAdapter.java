package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.model.Depohed;

import java.util.ArrayList;

/**
 * Created by Yasith on 3/1/2019.
 */

public class DepositInvoiceHistoryAdapter extends ArrayAdapter<Depohed> {
    Context context;
    ArrayList<Depohed> list;

    public DepositInvoiceHistoryAdapter(Context context, ArrayList<Depohed> list) {

        super(context, R.layout.row_invoice_history, list);
        this.context = context;
        this.list = list;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_deposit_history, parent, false);

        TextView refno = (TextView) row.findViewById(R.id.refno);
        TextView date = (TextView) row.findViewById(R.id.date);
        TextView amount = (TextView) row.findViewById(R.id.amount);
        TextView payType = (TextView) row.findViewById(R.id.payType);

        refno.setText(list.get(position).getFBANKDEPOHED_REFNO());
        date.setText(list.get(position).getFBANKDEPOHED_TXNDATE());
        amount.setText(list.get(position).getFBANKDEPOHED_DEPOAMT());

        if(list.get(position).getFBANKDEPOHED_PAYTYPE().equals("CA"))
        {
            payType.setText("CASH");
        }
        else
        {
            payType.setText("CHEQUE");
        }

        return row;
    }
}
