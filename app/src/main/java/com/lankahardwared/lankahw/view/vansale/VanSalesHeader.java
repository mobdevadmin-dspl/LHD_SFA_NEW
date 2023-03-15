package com.lankahardwared.lankahw.view.vansale;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.CustomerDebtAdapter;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.data.FDDbNoteDS;
import com.lankahardwared.lankahw.data.InvHedDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.data.SharedPreferencesClass;
import com.lankahardwared.lankahw.model.FDDbNote;
import com.lankahardwared.lankahw.model.InvHed;
import com.lankahardwared.lankahw.view.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VanSalesHeader extends Fragment {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    View view;
    SharedPref mSharedPref;
    MainActivity activity;
    TextView lblCustomerName, outStandingAmt, lastBillAmt;
    EditText lblInvRefno, currnentDate,txtManual,txtRemakrs;
    MyReceiver r;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_van_sales_header, container, false);
//        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        activity = (MainActivity) getActivity();
        mSharedPref = new SharedPref(getActivity());

        lblCustomerName = (TextView) view.findViewById(R.id.customerName);
        outStandingAmt = (TextView) view.findViewById(R.id.lbl_Inv_outstanding_amt);
        lastBillAmt = (TextView) view.findViewById(R.id.lbl_inv_lastbill);
        lblInvRefno = (EditText) view.findViewById(R.id.lbl_InvRefno);
        currnentDate = (EditText) view.findViewById(R.id.lbl_InvDate);
        txtManual = (EditText) view.findViewById(R.id.txt_InvManual);
        txtRemakrs = (EditText) view.findViewById(R.id.txt_InvRemarks);

       /*
       comment by dhanushika
       txtRemakrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputDialogbox(txtRemakrs.getText().toString(), "R");
            }
        });

        *//*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*//*

        txtManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputDialogbox(txtManual.getText().toString(), "M");
            }
        });*/

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        outStandingAmt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View promptView = layoutInflater.inflate(R.layout.customer_debtor, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Customer outstanding...");
                alertDialogBuilder.setView(promptView);

                final ListView listView = (ListView) promptView.findViewById(R.id.lvCusDebt);
                ArrayList<FDDbNote> list = new FDDbNoteDS(getActivity()).getDebtInfo(activity.selectedDebtor.getFDEBTOR_CODE());
                listView.setAdapter(new CustomerDebtAdapter(getActivity(), list));

                alertDialogBuilder.setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return view;
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mSaveInvoiceHeader() {

        if (lblInvRefno.getText().length() > 0) {

            InvHed hed = new InvHed();
            hed.setFINVHED_REFNO(lblInvRefno.getText().toString());
            hed.setFINVHED_ADDDATE(currnentDate.getText().toString());
            hed.setFINVHED_MANUREF(txtManual.getText().toString());
            String remark = txtRemakrs.getText().toString();
            remark = remark.replaceAll("[^a-zA-Z0-9]", " ");
            hed.setFINVHED_REMARKS(remark);
            hed.setFINVHED_ADDMACH(localSP.getString("MAC_Address", "No MAC Address").toString());
            hed.setFINVHED_ADDUSER(new SalRepDS(getActivity()).getCurrentRepCode());
            hed.setFINVHED_CURCODE("LKR");
            hed.setFINVHED_CURRATE("1.00");
            hed.setFINVHED_REPCODE(new SalRepDS(getActivity()).getCurrentRepCode());

            if (activity.selectedDebtor != null) {
                hed.setFINVHED_DEBCODE(activity.selectedDebtor.getFDEBTOR_CODE());
                hed.setFINVHED_CONTACT(activity.selectedDebtor.getFDEBTOR_MOB());
                hed.setFINVHED_CUSADD1(activity.selectedDebtor.getFDEBTOR_ADD1());
                hed.setFINVHED_CUSADD2(activity.selectedDebtor.getFDEBTOR_ADD2());
                hed.setFINVHED_CUSADD3(activity.selectedDebtor.getFDEBTOR_ADD3());
                hed.setFINVHED_CUSTELE(activity.selectedDebtor.getFDEBTOR_TELE());
                hed.setFINVHED_TAXREG(activity.selectedDebtor.getFDEBTOR_TAX_REG());
            }

            hed.setFINVHED_TXNTYPE("22");
            hed.setFINVHED_TXNDATE(currnentDate.getText().toString());
            hed.setFINVHED_IS_ACTIVE("1");
            hed.setFINVHED_IS_SYNCED("0");
            hed.setFINVHED_TOURCODE(new SharedPref(getActivity()).getGlobalVal("KeyTouRef"));
           // hed.setFINVHED_AREACODE(new SharedPref(getActivity()).getGlobalVal("KeyAreaCode"));
            hed.setFINVHED_AREACODE(activity.selectedDebtor.getFDEBTOR_AREA_CODE());
           // hed.setFINVHED_LOCCODE(new SharedPref(getActivity()).getGlobalVal("KeyLocCode"));
            hed.setFINVHED_LOCCODE(new SalRepDS(getActivity()).getCurrentLocCode());
            hed.setFINVHED_ROUTECODE(new SharedPref(getActivity()).getGlobalVal("KeyRouteCode"));
            hed.setFINVHED_COSTCODE("");

            activity.selectedInvHed = hed;
            SharedPreferencesClass.setLocalSharedPreference(activity, "Van_Start_Time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            ArrayList<InvHed> ordHedList = new ArrayList<>();
            ordHedList.add(activity.selectedInvHed);
            new InvHedDS(getActivity()).createOrUpdateInvHed(ordHedList);
        }
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mRefreshHeader() {

        if (mSharedPref.getGlobalVal("keyCustomer").equals("Y")) {

            lblCustomerName.setText(activity.selectedDebtor.getFDEBTOR_NAME());
            currnentDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            outStandingAmt.setText(String.format("%,.2f", new FDDbNoteDS(getActivity()).getDebtorBalance(activity.selectedDebtor.getFDEBTOR_CODE())));
            txtRemakrs.setEnabled(true);
            txtManual.setEnabled(true);

            /*already a header exist*/
            if (activity.selectedInvHed != null) {
                txtManual.setText(activity.selectedInvHed.getFINVHED_MANUREF());
                txtRemakrs.setText(activity.selectedInvHed.getFINVHED_REMARKS());
                lblInvRefno.setText(activity.selectedInvHed.getFINVHED_REFNO());
            } else { /*No header*/
                lblInvRefno.setText(new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.VanNumVal)));
                mSaveInvoiceHeader();
            }

        } else {
            Toast.makeText(getActivity(), "Select a customer to continue...", Toast.LENGTH_SHORT).show();
            txtRemakrs.setEnabled(false);
            txtManual.setEnabled(false);
        }
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

   	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_HEADER"));
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mInputDialogbox(String s, final String sType) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_dialogbox, null);
        final EditText enteredText = (EditText) promptView.findViewById(R.id.txtTextbox);
        enteredText.setText(s);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setCancelable(false).setPositiveButton("Save", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                if (sType.equals("R"))
                    txtRemakrs.setText(enteredText.getText().toString());
                else
                    txtManual.setText(enteredText.getText().toString());

                mSaveInvoiceHeader();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            VanSalesHeader.this.mRefreshHeader();
        }
    }


}
