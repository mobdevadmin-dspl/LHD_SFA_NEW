package com.lankahardwared.lankahw.view.receipt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.RecHedDS;
import com.lankahardwared.lankahw.data.TourHedDS;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.RecHed;
import com.lankahardwared.lankahw.model.TourHed;
import com.lankahardwared.lankahw.view.MainActivity;

import java.util.ArrayList;

public class ReceiptCustomer extends Fragment {
    View view;
    ListView lvCustomers;
    ArrayList<Debtor> customerList;
    Debtor debtor;
    CustomerAdapter customerAdapter;
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    SharedPref mSharedPref;
    TextView txtCusName;
    ImageButton btnCust, btnSearch;
    ProgressDialog progressDialog;
    IResponseListener listener;
    Spinner spnTour;
    ArrayList<TourHed> tourlist;
    String routeCode;
    String tourRefNo;
    String repCode;
    MainActivity mainActivity;
    String tourHedRefNo;
    GPSTracker gpsTracker;

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sales_management_pre_sales_customer, container, false);
        setHasOptionsMenu(true);
        mSharedPref = new SharedPref(getActivity());
        MainActivity activity = (MainActivity) getActivity();
        lvCustomers = (ListView) view.findViewById(R.id.cus_lv);
        gpsTracker = new GPSTracker(getActivity());

//        localSP = activity.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = activity.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

        btnCust = (ImageButton) view.findViewById(R.id.btnCust);
        btnSearch = (ImageButton) view.findViewById(R.id.btnsearch_cus);
        spnTour = (Spinner)view.findViewById(R.id.spnTour);
        mainActivity = (MainActivity) getActivity();

        txtCusName = (TextView) view.findViewById(R.id.txtSelCust);
        ReferenceNum referenceNum = new ReferenceNum(getActivity());
        ArrayList<RecHed> getRecHed = new RecHedDS(getActivity()).getAllCompletedRecHedS(referenceNum.getCurrentRefNo(getResources().getString(R.string.RecNumVal)));

        tourlist = new TourHedDS(getActivity()).getTourDetails("");
        ArrayList<String> strList = new ArrayList<String>();
        strList.add("Select a Tour to continue ...");

        for (TourHed hed : tourlist)
            strList.add(hed.getTOURHED_REFNO() + " - " + hed.getTOURHED_ID());

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, strList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTour.setAdapter(dataAdapter1);

        spnTour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0)
                {
                    routeCode = tourlist.get(position - 1).getTOURHED_ROUTECODE().toString();
                    tourRefNo = tourlist.get(position - 1).getTOURHED_REFNO().toString();
                    repCode = tourlist.get(position - 1).getTOURHED_REPCODE().toString();
                    mSharedPref.setGlobalVal("ReckeyTour", String.valueOf(position-1));
                    Log.d("PRE_SALES_CUSTOMER", "ROUTE_CODE_IS: " + routeCode);

                    lvCustomers.setAdapter(null);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        if (!getRecHed.isEmpty()) {

            for (RecHed recHed : getRecHed) {

                if (activity.selectedDebtor == null) {
                    DebtorDS debtorDS = new DebtorDS(getActivity());
                    activity.selectedDebtor = debtorDS.getSelectedCustomerByCode(recHed.getFPRECHED_DEBCODE());
                    activity.cusPosition = Integer.parseInt(localSP.getString("Van_Sales_Cus_Position", "0").toString());
                }
            }
        }

//        if (mainActivity.selectedRecHed == null) {
//            RecHed RECHed = new RecHedDS(getActivity()).getActiveRecHed();
//            if (RECHed != null) {
//                if (mainActivity.selectedDebtor == null)
//                {
//                    mainActivity.selectedDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(RECHed.getFPRECHED_DEBCODE());
////                    tourHedRefNo = mainActivity.sele
////                    spnTour.setSelection();
//                }
//
//            }
//        }

//        if (new RecHedDS(getActivity()).isAnyActiveRecHed()){
//
//            double index = Double.parseDouble(mSharedPref.getGlobalVal("ReckeyTour"));
//            int in = (int)index;
//            spnTour.setSelection(in);
//        }

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

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


        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//        lvCustomers = (ListView) view.findViewById(R.id.cus_lv);
//        lvCustomers.clearTextFilter();
//        dsDebtorDS = new DebtorDS(getActivity());
//        customerList = dsDebtorDS.getOutstandingCustomersForReceipt();
//        customerAdapter = new CustomerAdapter(getActivity(), customerList);
//        lvCustomers.setAdapter(customerAdapter);
//
//        if (activity.selectedDebtor != null) {
//            lvCustomers.setSelection(activity.cusPosition);
//        }

        if (activity.selectedDebtor != null)
            txtCusName.setText(activity.selectedDebtor.getFDEBTOR_NAME());

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        btnCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AsyncTask<Void, Void, Void>() {

                    protected void onPreExecute() {
                        String s = "Retrieving customers...";
                        SpannableString ss2 = new SpannableString(s);
                        ss2.setSpan(new RelativeSizeSpan(1.5f), 0, ss2.length(), 0);
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage(ss2);
                        progressDialog.show();
                        super.onPreExecute();
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        customerList = new DebtorDS(getActivity()).getOutstandingCustomersForReceipt(routeCode);
                        //customerList = new DebtorDS(getActivity()).getRouteCustomersByCodeAndName(routeCode, "");
                        customerAdapter = new CustomerAdapter(getActivity(), customerList);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        progressDialog.dismiss();
                        lvCustomers.setAdapter(customerAdapter);

                    }
                }.execute();

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchDialogBox();

            }
        });

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lvCustomers.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {

                if (!(gpsTracker.canGetLocation())) {
                    gpsTracker.showSettingsAlert();
                }

				/* Check whether automatic date time option checked or not */
                int i = android.provider.Settings.Global.getInt(getActivity().getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0);
                /* If option is selected */
                if (i > 0) {

                    MainActivity activity = (MainActivity) getActivity();
                    debtor = customerList.get(position);
                    activity.selectedDebtor = debtor;

                    mSharedPref.setGlobalVal("ReckeyCustomer", "1");
                    mSharedPref.setGlobalVal("ReckeyCusCode", debtor.getFDEBTOR_CODE());
                    txtCusName.setText(activity.selectedDebtor.getFDEBTOR_NAME());
                    lvCustomers.setAdapter(null);
                    if (txtCusName.getText().toString().equalsIgnoreCase(activity.selectedDebtor.getFDEBTOR_NAME()))
                    {
                        listener.moveNextFragmentRece();
                    }
					/* if not selected */
                } else {
                    android.widget.Toast.makeText(getActivity(), "Please tick the 'Automatic Date and Time' option to continue..", android.widget.Toast.LENGTH_LONG).show();
                    /* Show Date/time settings dialog */
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }
            }
        });

        lvCustomers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CusInfoBox alertBox = new CusInfoBox();
                debtor = customerList.get(position);
                Log.d("RECEIPT_CUSTOMER", "DEBTOR_NAME" + debtor.getFDEBTOR_NAME() + debtor.getFDEBTOR_CODE());
                if(debtor!=null)
                alertBox.debtorDetailsDialogbox(getActivity(), debtor.getFDEBTOR_NAME(), debtor,false);

                return true;
            }
        });

        return view;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (IResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }

        inflater.inflate(R.menu.frag_customer_phone_menu, menu);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_customer_search_phone).getActionView();
//        searchView.setOnQueryTextListener(new OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                lvCustomers = (ListView) view.findViewById(R.id.cus_lv);
//                lvCustomers.clearTextFilter();
//                dsDebtorDS = new DebtorDS(getActivity());
//                customerList = dsDebtorDS.getCustomerByCodeAndNameForReceipt(newText);
//                lvCustomers.setAdapter(new CustomerAdapter(getActivity(), customerList));
//                return false;
//            }
//        });
//
        super.onCreateOptionsMenu(menu, inflater);
    }

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


            }
        }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }


}
