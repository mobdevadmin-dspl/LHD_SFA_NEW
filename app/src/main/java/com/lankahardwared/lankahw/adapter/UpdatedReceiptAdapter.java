package com.lankahardwared.lankahw.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.RecHedDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.model.PaymentAllocate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdatedReceiptAdapter extends ArrayAdapter<PaymentAllocate> {
    Context context;
    ArrayList<PaymentAllocate> list;
    boolean isSummery;
    String refno;
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public UpdatedReceiptAdapter(Context context, ArrayList<PaymentAllocate> list) {
        super(context, R.layout.row_receipt_details1, list);
        this.context = context;
        this.list = list;

    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;
        long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;


        RecHedDS reched = new RecHedDS(context);

        Date date,cDate;
        long txn = 0;
        long current =0;
        try {
            date = (Date)formatter.parse(list.get(position).getFPAYMENT_ALLOCATE_FDD_TXN_DATE());
            System.out.println("receipt date is " +date.getTime());
            txn = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        //String curDate =	dateFormat.format(reched.getChequeDate(refno));

        try {

            if (reched.getChequeDate(refno)!= null)
            {
                cDate =(Date)formatter.parse(reched.getChequeDate(refno));
                current = cDate.getTime();
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_receipt_details1, parent, false);

        int numOfDays =   (int) ((System.currentTimeMillis()  - txn) / DAY_IN_MILLIS);

        int datediff = 0;

        if (reched.getChequeDate(refno)!= null)
        {
            if (reched.getChequeDate(refno).length()>0){
                datediff = (int) ((current  - txn) / DAY_IN_MILLIS);
            }else{
                datediff = numOfDays;
            }
        }

        SalRepDS rep = new SalRepDS(context);

        TextView lblRefNo = (TextView) row.findViewById(R.id.row_refno);
        TextView lblTxnDate = (TextView) row.findViewById(R.id.row_txndate);
        TextView lblDueAmt = (TextView) row.findViewById(R.id.row_dueAmt);
        TextView lblAmt = (TextView) row.findViewById(R.id.row_Amt);
        TextView lblDays = (TextView) row.findViewById(R.id.days);

        lblRefNo.setText(list.get(position).getFPAYMENT_ALLOCATE_FDD_REFNO());

        lblTxnDate.setText(list.get(position).getFPAYMENT_ALLOCATE_FDD_TXN_DATE().toString());

        lblDays.setText(""+numOfDays);

//        if (list.get(position).getFPAYMENT_ALLOCATE_FDD_PAID_AMT() != null) {
//            lblDueAmt.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL())
//                    - Double.parseDouble(list.get(position).getFPAYMENT_ALLOCATE_FDD_PAID_AMT())));
//            lblAmt.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFPAYMENT_ALLOCATE_FDD_PAID_AMT())));
//        }
//        else
//        {
//            lblDueAmt.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL())));
//        }

        return row;
    }
}