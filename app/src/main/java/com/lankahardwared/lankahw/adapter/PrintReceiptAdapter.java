package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.RecHedDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.model.RecDet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrintReceiptAdapter extends ArrayAdapter<RecDet> {
    Context context;
    ArrayList<RecDet> list;
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String Refno;

    public PrintReceiptAdapter(Context context, ArrayList<RecDet> list, String RefNo) {

        super(context, R.layout.row_print_receipt, list);
        this.context = context;
        this.list = list;
        this.Refno = RefNo;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        RecHedDS reched = new RecHedDS(context);
        Date date, cDate = new Date();
        String chqno = "";
        long txn = 0;
        long current = 0;
        try {
            date = (Date) formatter.parse(list.get(position).getFPRECDET_TXNDATE());
            System.out.println("receipt date is " + date.getTime());
            txn = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String curDate = dateFormat.format(currentDate);

//        try {
//            //cDate = (Date) formatter.parse(reched.getChequeDate(Refno));
//            chqno = reched.getChequeNo(Refno);
//            current = cDate.getTime();
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = mInflater.inflate(R.layout.row_print_receipt, parent, false);

        int numOfDays = (int) ((System.currentTimeMillis() - txn) / DAY_IN_MILLIS);

        TextView Refno = (TextView) row.findViewById(R.id.row_refno);
        TextView TxnDate = (TextView) row.findViewById(R.id.row_txndate);
        TextView dueAmt = (TextView) row.findViewById(R.id.row_dueAmt);
        TextView Amt = (TextView) row.findViewById(R.id.row_Amt);
        TextView balDue = (TextView)row.findViewById(R.id.row_bal_due_Amt);
        TextView Days = (TextView) row.findViewById(R.id.days);

//        TextView Refno = (TextView) row.findViewById(R.id.row_refno);
//        TextView TxnDate = (TextView) row.findViewById(R.id.row_txndate);
//        TextView RepName = (TextView) row.findViewById(R.id.repName);
//        TextView DateDiff = (TextView) row.findViewById(R.id.dateDiff);
//        TextView dueAmt = (TextView) row.findViewById(R.id.row_dueAmt);
//        TextView Amt = (TextView) row.findViewById(R.id.row_Amt);
//        TextView Days = (TextView) row.findViewById(R.id.days);

        SalRepDS rep = new SalRepDS(context);

//        int datediff = 0;
//
//        if (chqno.length() > 0) {
//            datediff = (int) ((current - txn) / DAY_IN_MILLIS);
//        } else {
//            datediff = numOfDays;
//        }

        //Refno.setText(list.get(position).getFPRECDET_REFNO());
        Refno.setText(list.get(position).getFPRECDET_REFNO1());
        TxnDate.setText(list.get(position).getFPRECDET_TXNDATE());
//        if (rep.getSaleRep(list.get(position).getFPRECDET_REPCODE()).equals(null)) {
//            RepName.setText("Not Set");
//        } else {
//            RepName.setText("" + rep.getSaleRep(list.get(position).getFPRECDET_REPCODE()));
//        }
//        DateDiff.setText("" + datediff);
        dueAmt.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFPRECDET_BAMT())+ Double.parseDouble(list.get(position).getFPRECDET_ALOAMT())));
        Amt.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFPRECDET_ALOAMT())));
        balDue.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFPRECDET_BAMT())));
        Days.setText("" + numOfDays);

        return row;

    }
}