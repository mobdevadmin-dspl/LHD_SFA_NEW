package com.lankahardwared.lankahw.view.receipt;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.IResponseListener;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.FDDbNoteDS;
import com.lankahardwared.lankahw.data.RecHedDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.data.SharedPreferencesClass;
import com.lankahardwared.lankahw.model.RecHed;
import com.lankahardwared.lankahw.view.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReceiptHeader extends Fragment {

    View view;
    TextView customerName, outStandingAmt, txtCompDisc;
    //EditText InvoiceNo, currnentDate, manual, remarks, txtReceAmt, txtCHQNO, txtCardNo, txtSlipNo, txtDraftNo;
    EditText InvoiceNo, currnentDate, manual, remarks;
    //TextView txtCHQDate, txtRecExpireDate;
    //TableRow chequeRow, cardRow, cardTypeRow, exDateRow, chequeNoRow, bankRow, depositRow, draftRow;
    //Spinner spnPayMode, spnBank, spnCardType;
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
//    String RefNo, payModePos;
    String RefNo;
    SharedPref mSharedPref;
    MainActivity mainActivity;
    MyReceiver r;
    FloatingActionButton fb;

    IResponseListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_receipt_header, container, false);
        mainActivity = (MainActivity) getActivity();
//        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        mSharedPref = new SharedPref(getActivity());
        customerName = (TextView) view.findViewById(R.id.customerName);
        outStandingAmt = (TextView) view.findViewById(R.id.rec_outstanding_amt);
        InvoiceNo = (EditText) view.findViewById(R.id.txtRecNo);
        currnentDate = (EditText) view.findViewById(R.id.txtRecDate);
        manual = (EditText) view.findViewById(R.id.txtRecManualNo);
        remarks = (EditText) view.findViewById(R.id.txtRecRemarks);
        fb = (FloatingActionButton) view.findViewById(R.id.fab1);

        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.RecNumVal));

        currentDate();
        InvoiceNo.setText(RefNo);

        fb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                SaveReceiptHeader();
                listener.moveToDetailsRece();
            }
        });

        if (mainActivity.selectedRecHed == null) {
            RecHed RECHed = new RecHedDS(getActivity()).getActiveRecHed();
            if (RECHed != null) {
                if (mainActivity.selectedDebtor == null)
                {
                    mainActivity.selectedDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(RECHed.getFPRECHED_DEBCODE());
                }
            }
        }

        if (new RecHedDS(getActivity()).isAnyActiveRecHed())
        {
            mainActivity.selectedDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(new RecHedDS(getActivity()).getActiveRecHed().getFPRECHED_DEBCODE());
            customerName.setText(mainActivity.selectedDebtor.getFDEBTOR_NAME());
            outStandingAmt.setText(String.format("%,.2f", new FDDbNoteDS(getActivity()).getDebtorBalance(new RecHedDS(getActivity()).getActiveRecHed().getFPRECHED_DEBCODE())));
            manual.setEnabled(true);
            remarks.setEnabled(true);
        }

        return view;
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    /* current date */
    private void currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        currnentDate.setText(dateFormat.format(date));
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    /* Current time */
    private String currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(cal.getTime());
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void SaveReceiptHeader() {

        MainActivity activity = (MainActivity) getActivity();
        mSharedPref.setGlobalVal("ReckeyRemnant", "0");

        new FDDbNoteDS(getActivity()).ClearFddbNoteData();
        RecHed recHed = new RecHed();

        if (activity.selectedRecHed != null) {
            recHed = activity.selectedRecHed;
        }

        recHed.setFPRECHED_REFNO(RefNo);
        recHed.setFPRECHED_DEBCODE(activity.selectedDebtor.getFDEBTOR_CODE());
        recHed.setFPRECHED_ADDDATE(currnentDate.getText().toString());
        recHed.setFPRECHED_MANUREF(manual.getText().toString());
        String str = remarks.getText().toString();
        str = str.replaceAll("[^a-zA-Z0-9]", " ");
        recHed.setFPRECHED_REMARKS(str);
        recHed.setFPRECHED_ADDMACH(localSP.getString("MAC_Address", "No MAC Address").toString());
        recHed.setFPRECHED_ADDUSER(new SalRepDS(activity).getCurrentRepCode());
        recHed.setFPRECHED_CURCODE("LKR");
        recHed.setFPRECHED_CURRATE("1.00");
        recHed.setFPRECHED_COSTCODE("1.00");
        recHed.setFPRECHED_ISACTIVE("1");
        recHed.setFPRECHED_ISDELETE("0");
        recHed.setFPRECHED_ISSYNCED("0");
        recHed.setFPRECHED_REPCODE(new SalRepDS(activity).getCurrentRepCode());
        recHed.setFPRECHED_TXNDATE(currnentDate.getText().toString());
        recHed.setFPRECHED_TXNTYPE("42");
        //recHed.setFPRECHED_TOTALAMT(String.valueOf(Double.parseDouble(txtReceAmt.getText().toString().replaceAll(",", ""))));
        //recHed.setFPRECHED_BTOTALAMT(String.valueOf(Double.parseDouble(txtReceAmt.getText().toString().replaceAll(",", ""))));
        recHed.setFPRECHED_SALEREFNO("");

//

        activity.selectedRecHed = recHed;
        //activity.ReceivedAmt = Double.parseDouble(txtReceAmt.getText().toString().replace(",", ""));
        SharedPreferencesClass.setLocalSharedPreference(activity, "Van_Start_Time", currentTime());
        ArrayList<RecHed> RecHedList = new ArrayList<RecHed>();
        RecHedList.add(activity.selectedRecHed);

        new RecHedDS(getActivity()).createOrUpdateRecHedS(RecHedList);
    }

    /*------------------------------------------------------------------------------------------*/
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ReceiptHeader.this.mRefreshHeader();
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

    public void mRefreshHeader() {

        if (mSharedPref.getGlobalVal("ReckeyCustomer").equals("1")) {

//            if (mainActivity.selectedRetDebtor != null)
            customerName.setText(mainActivity.selectedDebtor.getFDEBTOR_NAME());

            outStandingAmt.setText(String.format("%,.2f", new FDDbNoteDS(getActivity()).getDebtorBalance(mainActivity.selectedDebtor.getFDEBTOR_CODE())));
            manual.setEnabled(true);
            remarks.setEnabled(true);

            if (mainActivity.selectedRecHed != null)
            {
                manual.setText(mainActivity.selectedRecHed.getFPRECHED_MANUREF());
                remarks.setText(mainActivity.selectedRecHed.getFPRECHED_REMARKS());
            }
            else
            {
                InvoiceNo.setText(new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.RecNumVal)));
                SaveReceiptHeader();
            }
        }
        else
            {
            Toast.makeText(getActivity(), "Select a customer to continue...", Toast.LENGTH_SHORT).show();
            manual.setEnabled(false);
            remarks.setEnabled(false);
        }
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (IResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }
}
