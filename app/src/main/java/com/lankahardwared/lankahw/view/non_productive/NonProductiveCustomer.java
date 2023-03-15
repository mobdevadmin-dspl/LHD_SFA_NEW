package com.lankahardwared.lankahw.view.non_productive;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.CustomerAdapter;
import com.lankahardwared.lankahw.control.CusInfoBox;
import com.lankahardwared.lankahw.control.GPSTracker;
import com.lankahardwared.lankahw.control.IResponseListener;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.FDaynPrdHedDS;
import com.lankahardwared.lankahw.data.TourHedDS;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.FDaynPrdHed;
import com.lankahardwared.lankahw.model.TourHed;
import com.lankahardwared.lankahw.view.MainActivity;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Rashmi on 4/27/2018.
 */

public class NonProductiveCustomer extends Fragment {
    View view;
    ListView lvCustomers;
    ArrayList<Debtor> customerList;
    Debtor debtor;
    CustomerAdapter customerAdapter;
    SharedPref mSharedPref;
    IResponseListener listener;
    ImageButton btnCust, btnSearch;
    SweetAlertDialog pDialog;
    TextView txtCusName;
    private FDaynPrdHed tmpNonPrdHed;
    Spinner spnTour;
    ArrayList<TourHed> tourlist;
    String routeCode;
    String tourRefNo;
    String areaCode;
    GPSTracker gpsTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sales_management_pre_sales_customer, container, false);
        mSharedPref = new SharedPref(getActivity());
        MainActivity activity = (MainActivity) getActivity();
        lvCustomers = (ListView) view.findViewById(R.id.cus_lv);
        btnCust = (ImageButton) view.findViewById(R.id.btnCust);
        txtCusName = (TextView) view.findViewById(R.id.txtSelCust);
        btnSearch = (ImageButton) view.findViewById(R.id.btnsearch_cus);
        spnTour = (Spinner) view.findViewById(R.id.spnTour);

        tourlist = new TourHedDS(getActivity()).getTourDetails("");

        ArrayList<String> strList = new ArrayList<String>();
        strList.add("Select a Tour to continue ...");

        for (TourHed hed : tourlist)
        {
            strList.add(hed.getTOURHED_REFNO() + " - " + hed.getTOURHED_ID());
        }


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, strList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTour.setAdapter(dataAdapter1);

        spnTour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0)
                {
                    routeCode = tourlist.get(position - 1).getTOURHED_ROUTECODE();
                    tourRefNo = tourlist.get(position - 1).getTOURHED_REFNO();
                    areaCode = new TourHedDS(getActivity()).getAreaCode(tourRefNo);
                    Log.d("PRE_SALES_CUSTOMER", "ROUTE_CODE_IS: " + routeCode + ", " + areaCode);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        //-----------------------------------from Re order Only-----------------------------------------------------------------------------------
        Bundle mBundle = getArguments();

        if (mBundle != null) {
            tmpNonPrdHed = (FDaynPrdHed) mBundle.getSerializable("nonPrdHed");
            if (tmpNonPrdHed != null) {
                activity.selectedDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(tmpNonPrdHed.getNONPRDHED_DEBCODE());
                Log.d("<>*non prod cus****", "" + tmpNonPrdHed.getNONPRDHED_DEBCODE());
                new SharedPref(getActivity()).setGlobalVal("NonkeyCustomer", "Y");
                new SharedPref(getActivity()).setGlobalVal("NonkeyCusCode",  activity.selectedDebtor.getFDEBTOR_CODE());

                Log.d("NON_RPODUCTIVE_CUS", "CUSTOMER_CREDIT_DETAILS: " + activity.selectedDebtor.getFDEBTOR_CRD_PERIOD() + ", " + activity.selectedDebtor.getFDEBTOR_CRD_LIMIT());
            }

        }
           /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        btnSearch.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        btnSearch.setBackground(getResources().getDrawable(R.drawable.search_cus_down));
                    }
                    break;

                    case MotionEvent.ACTION_UP: {
                        btnSearch.setBackground(getResources().getDrawable(R.drawable.search_cus));
                    }
                    break;
                }
                return false;
            }
        });

        /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        btnCust.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        btnCust.setBackground(getResources().getDrawable(R.drawable.cus_down));
                    }
                    break;

                    case MotionEvent.ACTION_UP: {
                        btnCust.setBackground(getResources().getDrawable(R.drawable.cus));
                    }
                    break;
                }
                return false;
            }
        });

           /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchDialogBox();

            }
        });
        /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
        ArrayList<FDaynPrdHed> getNonHed = new FDaynPrdHedDS(getActivity()).getAllActiveNonPHed();

        if (activity.selectednonHed == null) {

            if (getNonHed.size() >0) {
                activity.selectednonHed = getNonHed.get(0);
                if (activity.selectedRetDebtor == null)
                    activity.selectedRetDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(getNonHed.get(0).getNONPRDHED_DEBCODE());
            }
        }

        if (activity.selectedDebtor != null)
            txtCusName.setText(activity.selectedDebtor.getFDEBTOR_NAME());

        /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        btnCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (!spnTour.getSelectedItem().toString().equalsIgnoreCase("Select a Tour to continue ..."))
            {

                new AsyncTask<Void, Void, Void>() {

                    protected void onPreExecute() {
                        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("Retrieving customers..");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        super.onPreExecute();
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        //customerList = new DebtorDS(getActivity()).getRouteCustomers("", "");
                        customerList = new DebtorDS(getActivity()).getRouteCustomersByCodeAndName(routeCode, "");
                        customerAdapter = new CustomerAdapter(getActivity(), customerList);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        if(pDialog.isShowing()){
                            pDialog.dismiss();
                            lvCustomers.setAdapter(customerAdapter);
                        }


                    }
                }.execute();
            }
                else
            {
                tourSelectionDialogBox();
            }

            }
        });

        /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lvCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {

				/* Check whether automatic date time option checked or not */
                int x = android.provider.Settings.Global.getInt(getActivity().getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0);
                /* If option is selected */
                if (x > 0) {
                    MainActivity activity = (MainActivity) getActivity();
                    debtor = customerList.get(position);
                    activity.selectedDebtor = debtor;
                    activity.cusPosition = position;
                    txtCusName.setText(activity.selectedDebtor.getFDEBTOR_NAME());
                    lvCustomers.setAdapter(null);
                    new SharedPref(getActivity()).setGlobalVal("NonkeyCustomer", "Y");
                    new SharedPref(getActivity()).setGlobalVal("NonkeyCusCode", debtor.getFDEBTOR_CODE());

                    Log.d("NON_RPODUCTIVE", "DEBTOR_CREDIT_DETAILS" + debtor.getFDEBTOR_CRD_LIMIT() + ", " + debtor.getFDEBTOR_CRD_PERIOD());

                    Log.d("NONKEYCUSTOMER", new SharedPref(getActivity()).getGlobalVal("NonkeyCustomer"));
                    Log.d("NONKEYCUSTOMERCODE", new SharedPref(getActivity()).getGlobalVal("NonkeyCusCode"));
                    navigateToHeader(position);

					/* if not selected */
                } else {
                    android.widget.Toast.makeText(getActivity(), "Please tick the 'Automatic Date and Time' option to continue..", android.widget.Toast.LENGTH_SHORT).show();
                    /* Show Date/time settings dialog */
                    Log.d("NONKEYCUSTOMER", "???????");
                    Log.d("NONKEYCUSTOMERCODE", "????????");
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }
            }
        });

		/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lvCustomers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CusInfoBox alertBox = new CusInfoBox();
                debtor = customerList.get(position);
                Log.d("NON_PRODUCTIVE_CUSTOMER", "DEBTOR_NAME" + debtor.getFDEBTOR_NAME() + debtor.getFDEBTOR_CODE());
                if(debtor!=null)
                alertBox.debtorDetailsDialogbox(getActivity(), debtor.getFDEBTOR_NAME(), debtor,false);
                return true;
            }
        });

        return view;
    }
/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void navigateToHeader(int position) {

        MainActivity activity = (MainActivity) getActivity();
        debtor = customerList.get(position);
        activity.selectedDebtor = debtor;
        android.widget.Toast.makeText(getActivity(), debtor.getFDEBTOR_NAME() + " selected", android.widget.Toast.LENGTH_LONG).show();
        mSharedPref.setGlobalVal("NonkeyCustomer", "Y");
        listener.moveNextFragment_NonProd();
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

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mSearchDialogBox() {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_dialogbox, null);
        final EditText enteredText = (EditText) promptView.findViewById(R.id.txtTextbox);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setCancelable(false).setPositiveButton("Search", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                lvCustomers.setAdapter(null);
                customerList = new DebtorDS(getActivity()).getRouteCustomers("", enteredText.getText().toString());
                lvCustomers.setAdapter(new CustomerAdapter(getActivity(), customerList));

                //Log.d("NON_RPODUCTIVE_SEARCH", "DEBTOR_CREDIT_DETAILS" + customerList.get(5).getFDEBTOR_CRD_LIMIT() + ", " + customerList.get(5).getFDEBTOR_CRD_PERIOD());


            }

        }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.create().show();
    }

    public void tourSelectionDialogBox() {

        String message = "Please select tour to continue...";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Tour Selection");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();

            }
        }).setNegativeButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }
}
