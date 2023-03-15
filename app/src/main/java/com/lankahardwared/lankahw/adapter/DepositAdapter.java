package com.lankahardwared.lankahw.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.data.DepositDetDS;
import com.lankahardwared.lankahw.data.RecHedDS;
import com.lankahardwared.lankahw.model.Depodet;
import com.lankahardwared.lankahw.model.RecHed;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DepositAdapter extends ArrayAdapter<RecHed> {
    Context context;
    ArrayList<RecHed> list;
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String depositAmount,RefNo;
    private String TAG = "DepositAdapter ";
    SharedPref mSharedPref;


    public DepositAdapter(Context context, ArrayList<RecHed> list , String depositAmount , String RefNo) {

        super(context, R.layout.row_receipt_details1, list);
        this.context = context;
        this.list = list;
        this.depositAmount = depositAmount;
        this.RefNo = RefNo;
        mSharedPref = new SharedPref(context);
        mSharedPref.setGlobalVal("DepoAllocAmt","0.00");
        mSharedPref.setGlobalVal("DepoRemainAmt",String.valueOf(depositAmount));

    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = convertView;
        final ViewHolder viewHolder;
        final RecHed recHed = list.get(position);

        if (row == null)
        {
            viewHolder = new ViewHolder();

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_receipt_details_in_deposit, parent, false);

            viewHolder.lblRefNo = (TextView) row.findViewById(R.id.row_refno);
            viewHolder.lblTxnDate = (TextView) row.findViewById(R.id.row_txndate);
            viewHolder.lblDebtor = (TextView) row.findViewById(R.id.row_debtor);
            viewHolder.lblCHqNo = (TextView) row.findViewById(R.id.row_chequeno);
            viewHolder.lblTotAmt = (TextView) row.findViewById(R.id.row_totamt);
            viewHolder.chkId = (CheckBox) row.findViewById(R.id.chkId);


            viewHolder.lblRefNo.setText(recHed.getFPRECHED_REFNO());
            viewHolder.lblDebtor.setText(recHed.getFPRECHED_DEBCODE());
            viewHolder.lblTxnDate.setText(recHed.getFPRECHED_TXNDATE());
            viewHolder.lblCHqNo.setText(recHed.getFPRECHED_CHQNO());
            viewHolder.lblTotAmt.setText(recHed.getFPRECHED_TOTALAMT());


            row.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.chkId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String selectedReceiptTotAmount = "",TotReminingAmt = "";
                if(isChecked)
                {
                    String receiptAmount = viewHolder.lblTotAmt.getText().toString();
                    String shrdPrefAmnt = mSharedPref.getGlobalVal("DepoAllocAmt");
                    String shrdPrefRemainingAmnt = mSharedPref.getGlobalVal("DepoRemainAmt");
                    selectedReceiptTotAmount = String.valueOf(Double.valueOf(shrdPrefAmnt) + Double.valueOf(receiptAmount));
                    TotReminingAmt = String.valueOf(Double.valueOf(shrdPrefRemainingAmnt) - Double.valueOf(receiptAmount));
                    if(Double.valueOf(shrdPrefRemainingAmnt) >= Double.valueOf(receiptAmount))
                    {
                        mSharedPref.setGlobalVal("DepoAllocAmt",selectedReceiptTotAmount);
                        mSharedPref.setGlobalVal("DepoRemainAmt",TotReminingAmt);
                        Depodet depodet =  new Depodet();
                        depodet.setFBANKDEPODET_REFNO(RefNo);
                        depodet.setFBANKDEPODET_RECEIPT_REFNO(viewHolder.lblRefNo.getText().toString().trim());
                        depodet.setFBANKDEPODET_DEBTOR_CODE(viewHolder.lblDebtor.getText().toString().trim());
                        depodet.setFBANKDEPODET_RECEIPT_TXNDATE(viewHolder.lblTxnDate.getText().toString().trim());
                        depodet.setFBANKDEPODET_RECEIPT_CHEQNO(viewHolder.lblCHqNo.getText().toString().trim());
                        depodet.setFBANKDEPODET_RECEIPT_AMT(viewHolder.lblTotAmt.getText().toString().trim());

                        int result = new DepositDetDS(getContext()).createOrUpdateDepositDet(depodet);
                        if(result > 0)
                        {
                            Log.v(TAG, " Record Inserted " + result);
                        }
                    }
                    else
                    {
                        String receiptAmount1 = viewHolder.lblTotAmt.getText().toString();
                        selectedReceiptTotAmount = selectedReceiptTotAmount + receiptAmount1;
                        viewHolder.chkId.setChecked(false);
                        return;
                    }
                }
                else
                {
                    String receiptAmount = viewHolder.lblTotAmt.getText().toString();
                    String shrdPrefAmnt = mSharedPref.getGlobalVal("DepoAllocAmt");
                    String shrdPrefRemainingAmnt = mSharedPref.getGlobalVal("DepoRemainAmt");
                    selectedReceiptTotAmount = String.valueOf(Double.valueOf(shrdPrefAmnt) - Double.valueOf(receiptAmount));
                    TotReminingAmt = String.valueOf(Double.valueOf(shrdPrefRemainingAmnt) + Double.valueOf(receiptAmount));
                    mSharedPref.setGlobalVal("DepoAllocAmt",selectedReceiptTotAmount);
                    mSharedPref.setGlobalVal("DepoRemainAmt",TotReminingAmt);
                }
            }
        });

        return row;
    }



    @Override
    public int getCount()
    {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public RecHed getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder
    {
        TextView lblRefNo;
        TextView lblTxnDate;
        TextView lblTotAmt;
        TextView lblDebtor;
        TextView lblCHqNo;
        CheckBox chkId;
    }


}
