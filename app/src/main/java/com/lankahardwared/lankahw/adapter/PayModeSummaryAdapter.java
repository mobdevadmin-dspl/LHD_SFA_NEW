package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.PayMode;

import java.util.ArrayList;


public class PayModeSummaryAdapter extends ArrayAdapter<PayMode> {

    Context context;
    ArrayList<PayMode> list;

    public PayModeSummaryAdapter(Context context, ArrayList<PayMode> list ) {
        super(context, R.layout.row_pay_mode_summary, list);
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public PayMode getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = convertView;
        final Holder holder;
        final PayMode payMode = list.get(position);

        if (row == null)
        {
            holder = new Holder();

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_pay_mode_summary, parent, false);

            holder.mode = (TextView) row.findViewById(R.id.txtPayMode);
            holder.number = (TextView) row.findViewById(R.id.txtChqNo);
            holder.date = (TextView)row.findViewById(R.id.txtDate);
            holder.amt = (TextView)row.findViewById(R.id.txtAmount);

            row.setTag(holder);
        }
        else
        {
            holder = (Holder)row.getTag();
        }

        holder.mode.setText(payMode.getFPAYMODE_PAID_TYPE());
        holder.amt.setText(payMode.getFPAYMODE_PAID_AMOUNT());

        if (payMode.getFPAYMODE_PAID_TYPE().equalsIgnoreCase("CA"))
        {
            holder.number.setText("N/A");
            holder.date.setText(payMode.getFPAYMODE_PAID_DATE());
        }
        else if (payMode.getFPAYMODE_PAID_TYPE().equalsIgnoreCase("CH"))
        {
            holder.number.setText(payMode.getFPAYMODE_PAID_CHEQUE_NO());
            holder.date.setText(payMode.getFPAYMODE_PAID_CHEQUE_DATE());
        }
        else if (payMode.getFPAYMODE_PAID_TYPE().equalsIgnoreCase("CC"))
        {
            holder.number.setText(payMode.getFPAYMODE_PAID_CREDIT_CARD_NO());
            holder.date.setText(payMode.getFPAYMODE_PAID_DATE());
        }
        else if (payMode.getFPAYMODE_PAID_TYPE().equalsIgnoreCase("DD"))
        {
            holder.number.setText(payMode.getFPAYMODE_PAID_SLIP_NO());
            holder.date.setText(payMode.getFPAYMODE_PAID_DATE());
        }
        else if (payMode.getFPAYMODE_PAID_TYPE().equalsIgnoreCase("BD"))
        {
            holder.number.setText(payMode.getFPAYMODE_PAID_DRAFT_NO());
            holder.date.setText(payMode.getFPAYMODE_PAID_DATE());
        }

        return row;
    }

    public static class Holder {
        TextView mode;
        TextView number;
        TextView date;
        TextView amt;
    }
}
