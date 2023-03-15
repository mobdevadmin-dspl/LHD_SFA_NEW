package com.lankahardwared.lankahw.view.presale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.CustomerDebtAdapter;
import com.lankahardwared.lankahw.control.IResponseListener;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.FDDbNoteDS;
import com.lankahardwared.lankahw.data.LocationsDS;
import com.lankahardwared.lankahw.data.OrdFreeIssueDS;
import com.lankahardwared.lankahw.data.OrderDiscDS;
import com.lankahardwared.lankahw.data.PreProductDS;
import com.lankahardwared.lankahw.data.PreSaleTaxDTDS;
import com.lankahardwared.lankahw.data.PreSaleTaxRGDS;
import com.lankahardwared.lankahw.data.RouteDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.data.SharedPreferencesClass;
import com.lankahardwared.lankahw.data.TourHedDS;
import com.lankahardwared.lankahw.data.TranSODetDS;
import com.lankahardwared.lankahw.data.TranSOHedDS;
import com.lankahardwared.lankahw.model.FDDbNote;
import com.lankahardwared.lankahw.model.TranSOHed;
import com.lankahardwared.lankahw.view.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PreSalesHeader extends Fragment {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    View view;
    TextView customerName, outStandingAmt, lastBillAmt, txtDeliDate, txtRoute, lblCostCode;
    EditText txtManual, txtRemarks;
    EditText orderNo, currnentDate;
    ImageButton btnDeliDate;
    Spinner spnnervLoc;
    SharedPref mSharedPref;
    MainActivity activity;
    MyReceiver r;
    IResponseListener listener;
    String routeName,location;
    String LocPos, CosLocation;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_pre_sales_header, container, false);

        activity = (MainActivity) getActivity();
        //localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        mSharedPref = new SharedPref(getActivity());
        txtManual = (EditText) view.findViewById(R.id.sa_header_manual_no);
        txtRemarks = (EditText) view.findViewById(R.id.sa_header_remark);
        btnDeliDate = (ImageButton) view.findViewById(R.id.iBtnDeliDate);

        txtRoute = (EditText) view.findViewById(R.id.txtRoute);
        customerName = (TextView) view.findViewById(R.id.customerName);
        outStandingAmt = (TextView) view.findViewById(R.id.sa_header_outstanding_amt);
        lastBillAmt = (TextView) view.findViewById(R.id.sa_header_last_bill_amt);
        orderNo = (EditText) view.findViewById(R.id.sa_header_order_no);
        currnentDate = (EditText) view.findViewById(R.id.sa_header_date);
        txtDeliDate = (EditText) view.findViewById(R.id.sa_header_del_date);
        lblCostCode = (EditText) view.findViewById(R.id.txtCost);
        spnnervLoc = (Spinner) view.findViewById(R.id.spnnervLoc);
//-------------------------------------------------------------------------------------------------------------------------------

        ArrayList<String> LocDetails = new LocationsDS(getActivity()).getLocDetailsByLocCode();
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, LocDetails);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnnervLoc.setAdapter(dataAdapter2);

//        LocPos = mSharedPref.getGlobalVal("keyGroupPos");
//        if (!LocPos.equals("***"))
//            spnnervLoc.setSelection(Integer.parseInt(LocPos));

        spnnervLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                    new SharedPref(getActivity()).setGlobalVal("PrekeyLoc_Code", "");
                    //new PreProductDS(getActivity()).mClearTables();
                new TranSODetDS(getActivity()).restData(orderNo.getText().toString());
                new PreProductDS(getActivity()).mClearTables();
                new OrderDiscDS(getActivity()).clearData(orderNo.getText().toString());
                new OrdFreeIssueDS(getActivity()).ClearFreeIssues(orderNo.getText().toString());
                new PreSaleTaxDTDS(getActivity()).ClearTable(orderNo.getText().toString());
                new PreSaleTaxRGDS(getActivity()).ClearTable(orderNo.getText().toString());
                    new SharedPref(getActivity()).setGlobalVal("PrekeyLoc_Code", spnnervLoc.getSelectedItem().toString().split("-")[0].trim());
                SaveSalesHeader();
                    Log.v("LOCATION", spnnervLoc.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                new SharedPref(getActivity()).setGlobalVal("PrekeyLoc_Code", "");
                Log.v("LOCATION", spnnervLoc.getSelectedItem().toString());
            }

        });


        btnDeliDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DateTimePicker();
            }
        });


        if (activity.selectedSOHed == null) {
            TranSOHed SOHed = new TranSOHedDS(getActivity()).getActiveSOHed();
            if (SOHed != null) {
                if (activity.selectedDebtor == null)
                    activity.selectedDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(SOHed.getFTRANSOHED_DEBCODE());
            }
        }

        Log.d("PRE_SALES_HEADER", "LOC_CODE: " + mSharedPref.getGlobalVal("PrekeyLocCode"));
        txtRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SaveSalesHeader();

            }
        });
        txtManual.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SaveSalesHeader();

            }
        });
        return view;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void DateTimePicker() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.date_picker);
        final DatePicker dtp = (DatePicker) dialog.findViewById(R.id.dpResult);
        dialog.setCancelable(true);
        Button button = (Button) dialog.findViewById(R.id.btnok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int month = dtp.getMonth() + 1;
                int year = dtp.getYear();
                int date = dtp.getDayOfMonth();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date dateToday = null;
                Date date2 = null;

                try {
                    dateToday = sdf.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    date2 = sdf.parse(year + "-" + month + "-" + date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (date2.equals(dateToday) || date2.after(dateToday)) {
                    dialog.dismiss();
                    txtDeliDate.setText(year + "-" + String.format("%02d", month) + "-" + String.format("%02d", date));
                    //SaveSalesHeader();
                }

            }
        });

        dialog.show();
    }
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private String currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }


	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void SaveSalesHeader() {

        if (orderNo.getText().length() > 0) {

           TranSOHed hed = new TranSOHed();

            hed.setFTRANSOHED_REFNO(orderNo.getText().toString());
            hed.setFTRANSOHED_TXNDELDATE(txtDeliDate.getText().toString());
            hed.setFTRANSOHED_MANUREF(txtManual.getText().toString());
            String remark = txtRemarks.getText().toString();
            remark = remark.replaceAll("[^a-zA-Z0-9]", " ");
            hed.setFTRANSOHED_REMARKS(remark);
            hed.setFTRANSOHED_ADDMACH(localSP.getString("MAC_Address", "No MAC Address").toString());
            hed.setFTRANSOHED_CURCODE("LKR");
            hed.setFTRANSOHED_CURRATE("1.00");

            hed.setFTRANSOHED_REPCODE(new SalRepDS(getActivity()).getCurrentRepCode());


            hed.setFTRANSOHED_DEBCODE(activity.selectedDebtor.getFDEBTOR_CODE());
            hed.setFTRANSOHED_CONTACT(activity.selectedDebtor.getFDEBTOR_MOB());
            hed.setFTRANSOHED_CUSADD1(activity.selectedDebtor.getFDEBTOR_ADD1());
            hed.setFTRANSOHED_CUSADD2(activity.selectedDebtor.getFDEBTOR_ADD2());
            hed.setFTRANSOHED_CUSADD3(activity.selectedDebtor.getFDEBTOR_ADD3());
            hed.setFTRANSOHED_CUSTELE(activity.selectedDebtor.getFDEBTOR_TELE());
//            hed.setFTRANSOHED_TAXREG(new FDebTaxDS(getActivity()).getTaxRegNo(activity.selectedDebtor.getFDEBTOR_CODE()));
            hed.setFTRANSOHED_TAXREG(activity.selectedDebtor.getFDEBTOR_TAX_REG());

            hed.setFTRANSOHED_TXNTYPE("22");
            //hed.setFTRANSOHED_TXNTYPE("42");
            hed.setFTRANSOHED_TXNDATE(currnentDate.getText().toString());
            hed.setFTRANSOHED_IS_ACTIVE("1");
            hed.setfTRANSOHED_IS_SYNCED("0");
            //hed.setFTRANSOHED_TOURCODE(new SharedPref(getActivity()).getGlobalVal("preKeyTouRef"));
            hed.setFTRANSOHED_TOURCODE(new SharedPref(getActivity()).getGlobalVal("preKeyTouRef"));
            hed.setFTRANSOHED_AREACODE(new SharedPref(getActivity()).getGlobalVal("preKeyAreaCode"));
            //hed.setFTRANSOHED_AREACODE(activity.selectedDebtor.getFDEBTOR_AREA_CODE());
            //hed.setFTRANSOHED_LOCCODE(new SharedPref(getActivity()).getGlobalVal("PrekeyLocCode").trim());
            //hed.setFTRANSOHED_LOCCODE(spnnervLoc.getSelectedItem().toString().split("-")[0].trim());

            if(new SharedPref(getActivity()).getGlobalVal("PrekeyLoc_Code").equals("")){
                hed.setFTRANSOHED_LOCCODE(spnnervLoc.getSelectedItem().toString().split("-")[0].trim());
                new SharedPref(getActivity()).setGlobalVal("PrekeyLoc_Code",spnnervLoc.getSelectedItem().toString().split("-")[0].trim());
            }else{
                hed.setFTRANSOHED_LOCCODE(new SharedPref(getActivity()).getGlobalVal("PrekeyLoc_Code"));
            }
           //hed.setFTRANSOHED_LOCCODE(new SharedPref(getActivity()).getGlobalVal("PrekeyLoc_Code").trim());

            //hed.setFTRANSOHED_LOCCODE(new TourHedDS(getActivity()).getCurrentLocCodeFromTourHed());
            //hed.setFTRANSOHED_LOCCODE(new SalRepDS(getActivity()).getCurrentLocCode());
            hed.setFTRANSOHED_ROUTECODE(new SharedPref(getActivity()).getGlobalVal("preKeyRouteCode"));
            hed.setFTRANSOHED_COSTCODE(lblCostCode.getText().toString());
            hed.setFTRANSOHED_REFNO1(new SharedPref(getActivity()).getGlobalVal("preKeyTouRef"));
            Log.d("PRE_SALES_HEADER", "REF_NO1_IS: " + hed.getFTRANSOHED_REFNO1());

            activity.selectedSOHed = hed;
            SharedPreferencesClass.setLocalSharedPreference(activity, "SO_Start_Time", currentTime());
            ArrayList<TranSOHed> ordHedList = new ArrayList<>();
            ordHedList.add(activity.selectedSOHed);
            new TranSOHedDS(getActivity()).createOrUpdateTranSOHedDS(ordHedList);
        }
    }

   /* public void mInputDialogbox(String s, final String sType) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_dialogbox, null);
        final EditText enteredText = (EditText) promptView.findViewById(R.id.txtTextbox);
        enteredText.setText(s);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setCancelable(false).setPositiveButton("Save", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                if (sType.equals("R"))
                    txtRemarks.setText(enteredText.getText().toString());
                else
                    txtManual.setText(enteredText.getText().toString());

                SaveSalesHeader();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }*/

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

   	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();
        r = new PreSalesHeader.MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_HEADER"));
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mRefreshHeader() {

        if (mSharedPref.getGlobalVal("PrekeyCustomer").equals("Y")) {

            currnentDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            if (!new SharedPref(getActivity()).getGlobalVal("preKeyRouteCode").equals(""))
            {
                routeName = new RouteDS(getActivity()).getRouteNameByRouteCode(new SharedPref(getActivity()).getGlobalVal("preKeyRouteCode"));
            }
            else
            {
                routeName = mSharedPref.getGlobalVal("PrekeyRouteName");
            }

            txtRoute.setText(routeName);
            btnDeliDate.setEnabled(true);
            txtRemarks.setEnabled(true);
            txtManual.setEnabled(true);
            customerName.setText(activity.selectedDebtor.getFDEBTOR_NAME());
            String debCode= mSharedPref.getGlobalVal("PrekeyCusCode");
            outStandingAmt.setText(String.format("%,.2f", new FDDbNoteDS(getActivity()).getDebtorBalance(debCode)));
            String lastOrder = "";
//            if (debCode != null)
//            {
//                if (!new TranSOHedDS(getActivity()).getLastOrderByDebCode(debCode).equals(""))
//                {
//                    lastOrder = new TranSOHedDS(getActivity()).getLastOrderByDebCode(debCode);
//                }
//                else
//                {
//                    lastOrder = "0.00";
//                }
//            }

            Log.d("PRE_SALES_HEADER" , " LAST_ORDER_VALUE: " + lastOrder);
            lastBillAmt.setText(lastOrder);


            if (activity.selectedSOHed != null) {
                if (activity.selectedDebtor == null)
                    activity.selectedDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(activity.selectedSOHed.getFTRANSOHED_DEBCODE());

                customerName.setText(activity.selectedDebtor.getFDEBTOR_NAME());
                orderNo.setText(activity.selectedSOHed.getFTRANSOHED_REFNO());
                txtDeliDate.setText(activity.selectedSOHed.getFTRANSOHED_TXNDELDATE());
                txtManual.setText(activity.selectedSOHed.getFTRANSOHED_MANUREF());
                txtRemarks.setText(activity.selectedSOHed.getFTRANSOHED_REMARKS());
                lblCostCode.setText(activity.selectedSOHed.getFTRANSOHED_COSTCODE());
                outStandingAmt.setText(String.format("%,.2f", new FDDbNoteDS(getActivity()).getDebtorBalance(activity.selectedDebtor.getFDEBTOR_CODE())));
            } else {

                orderNo.setText(new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.NumVal)));
                String tourRef = mSharedPref.getGlobalVal("preKeyTouRef");
                //lblCostCode.setText(new SharedPref(getActivity()).getGlobalVal("preKeyCostCode"));
                if (!tourRef.equals(""))
                {
                    lblCostCode.setText(new TourHedDS(getActivity()).getCostCode(new SharedPref(getActivity()).getGlobalVal("preKeyTouRef")));
                    txtRoute.setText(routeName);
                }

                txtDeliDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                if (routeName.equals(""))
                {
                    Toast.makeText(getActivity(), "Without Route unable to continue...", Toast.LENGTH_SHORT).show();
                    listener.moveBackToCustomer_pre(0);
                }
                else
                {
                    SaveSalesHeader();
                }

            }

            outStandingAmt.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    String debCode= activity.selectedDebtor.getFDEBTOR_CODE();

                    mCustomerOutstandingDialog(debCode);

                    return true;
                }
            });

//            if (routeName.equals(""))
//            {
//                Toast.makeText(getActivity(), "Without Route unable to continue...", Toast.LENGTH_SHORT).show();
//                listener.moveBackToCustomer_pre(1);
//            }

        } else {
            Toast.makeText(getActivity(), "Select a customer to continue...", Toast.LENGTH_SHORT).show();
            txtRemarks.setEnabled(false);
            txtManual.setEnabled(false);
            btnDeliDate.setEnabled(false);
            listener.moveBackToCustomer_pre(0);
        }

    }

    public void mCustomerOutstandingDialog(String debCode) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.customer_debtor, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Customer Outstanding " + (debCode.equals("") ? debCode : "(" + debCode + ")"));
        alertDialogBuilder.setView(promptView);

        final ListView listView = (ListView) promptView.findViewById(R.id.lvCusDebt);
        ArrayList<FDDbNote> list = new FDDbNoteDS(getActivity()).getDebtInfo(debCode);
        listView.setAdapter(new CustomerDebtAdapter(getActivity(), list));

        alertDialogBuilder.setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            PreSalesHeader.this.mRefreshHeader();
        }
    }

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


    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

