package com.lankahardwared.lankahw.view.presale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.CustomerAdapter;
import com.lankahardwared.lankahw.control.CusInfoBox;
import com.lankahardwared.lankahw.control.GPSTracker;
import com.lankahardwared.lankahw.control.IResponseListener;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.FDDbNoteDS;
import com.lankahardwared.lankahw.data.RouteDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.data.TourHedDS;
import com.lankahardwared.lankahw.data.TranSOHedDS;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.TourHed;
import com.lankahardwared.lankahw.model.TranSOHed;
import com.lankahardwared.lankahw.view.MainActivity;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class PreSalesCustomer extends Fragment {
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    View view;
    ListView lvCustomers;
    ArrayList<Debtor> customerList;
    Debtor debtor;
    CustomerAdapter customerAdapter;
    SharedPref mSharedPref;
    IResponseListener listener;
    TextView txtCusName, btnTourCus, btnAllCus;
    ImageButton btnCust,btnSearch;
    SweetAlertDialog pDialog;
    private TranSOHed tmpsoHed=null;
    Spinner spnTour;
    ArrayList<TourHed> tourlist;
    String routeCode="";
    String tourRefNo;
    String areaCode;
    String locCode;
    String routeName="";
    GPSTracker gpsTracker;
    double lati = 0.0;
    double longi = 0.0;
    boolean isTourDebtor = true;
    private static final String TAG = "PreSalesCustomer";
    Activity mactivity;

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_pre_sales_customer, container, false);
        mSharedPref = new SharedPref(getActivity());
        final MainActivity activity = (MainActivity) getActivity();
        lvCustomers = (ListView) view.findViewById(R.id.cus_lv);
        gpsTracker = new GPSTracker(getActivity());
        localSP = activity.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

        btnCust = (ImageButton) view.findViewById(R.id.btnCust);
        txtCusName = (TextView) view.findViewById(R.id.txtSelCust);
        btnTourCus = (TextView) view.findViewById(R.id.btnTourDebtor);
        btnAllCus = (TextView) view.findViewById(R.id.btnAllDebtor);
        btnSearch = (ImageButton) view.findViewById(R.id.btnsearch_cus);
        spnTour = (Spinner) view.findViewById(R.id.spnTour);
        mactivity = getActivity();

        // These two button are only used for pre sales
        btnTourCus.setVisibility(View.VISIBLE);
        btnAllCus.setVisibility(View.VISIBLE);
        btnAllCus.setBackground(getResources().getDrawable(R.drawable.custom_label_disable));

        tourlist = new TourHedDS(getActivity()).getTourDetails("");

        ArrayList<String> strList = new ArrayList<String>();
        strList.add("Select a Tour to continue ...");

        for (TourHed hed : tourlist)
        {
            strList.add(hed.getTOURHED_REFNO() + " - " + hed.getTOURHED_ID() + " - (" + hed.getTOURHED_LOCNAME() + ") ");
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

                    lvCustomers.setAdapter(null);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        btnTourCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spnTour.setVisibility(View.VISIBLE);
                btnAllCus.setBackground(getResources().getDrawable(R.drawable.custom_label_disable));
                btnTourCus.setBackground(getResources().getDrawable(R.drawable.custom_label));

                //to blank the customer list view
                customerList = new DebtorDS(getActivity()).getRouteCustomersByCodeAndName("", "");
                customerAdapter = new CustomerAdapter(getActivity(), customerList);
                lvCustomers.setAdapter(customerAdapter);
                isTourDebtor = true;


            }
        });

        btnAllCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spnTour.setVisibility(View.GONE);
                btnTourCus.setBackground(getResources().getDrawable(R.drawable.custom_label_disable));
                btnAllCus.setBackground(getResources().getDrawable(R.drawable.custom_label));

                //to blank the customer list view
                customerList = new DebtorDS(getActivity()).getRouteCustomersByCodeAndName("", "");
                customerAdapter = new CustomerAdapter(getActivity(), customerList);
                lvCustomers.setAdapter(customerAdapter);
                isTourDebtor = false;
            }
        });

        //-----------------------------------from Reorder Only-----------------------------------------------------------------------------------
        Bundle mBundle = getArguments();

        if (mBundle != null) {
            tmpsoHed = (TranSOHed) mBundle.getSerializable("order");
            if (tmpsoHed != null) {
                activity.selectedDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(tmpsoHed.getFTRANSOHED_DEBCODE());
                Log.d("<>*Pre sales cus****", "" + tmpsoHed.getFTRANSOHED_REFNO());
                new SharedPref(getActivity()).setGlobalVal("PrekeyCusCode", activity.selectedDebtor.getFDEBTOR_CODE());
                mSharedPref.setGlobalVal("PrekeyCustomer", "Y");
            }

        }
//-----------------------------------------------------------------------------------------------------------------------------------
        if (activity.selectedSOHed == null) {
            TranSOHed SOHed = new TranSOHedDS(getActivity()).getActiveSOHed();
            if (SOHed != null) {
                if (activity.selectedDebtor == null)
                {
                    activity.selectedDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(SOHed.getFTRANSOHED_DEBCODE());
                }


            }
        }

        if(activity.selectedDebtor!=null)
            txtCusName.setText(activity.selectedDebtor.getFDEBTOR_NAME());

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
        btnCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isTourDebtor)
                {
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
                                pDialog.dismiss();
                                //lvCustomers.setAdapter(null);
                                lvCustomers.setAdapter(customerAdapter);
                            }
                        }.execute();
                    }
                    else
                    {
                        tourSelectionDialogBox();
                    }
                }
                else
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
                            customerList = new DebtorDS(getActivity()).getRouteCustomers("", "");
                            //customerList = new DebtorDS(getActivity()).getRouteCustomersByCodeAndName(routeCode, "");
                            customerAdapter = new CustomerAdapter(getActivity(), customerList);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            pDialog.dismiss();
                            lvCustomers.setAdapter(customerAdapter);
                        }
                    }.execute();
                }

            }
        });

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

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchDialogBox();

            }
        });
	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lvCustomers.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {

                if (gpsTracker.canGetLocation()) {
                    //2020-01-02 by rashmi

                    gpsTracker = new GPSTracker(getActivity());
                    debtor = customerList.get(position);
                    //if master file does not exist gps data
                    if(debtor.getFDEBTOR_LATITUDE().equals(null) || debtor.getFDEBTOR_LONGITUDE().equals(null) || !(Double.parseDouble(debtor.getFDEBTOR_LATITUDE())>0) || !(Double.parseDouble(debtor.getFDEBTOR_LONGITUDE())>0)) {

                        if (!mSharedPref.getGlobalVal("Latitude").equals("") && !mSharedPref.getGlobalVal("Longitude").equals("")) {
                            //get current gps data for update debtor coordinates
                            lati = Double.parseDouble(mSharedPref.getGlobalVal("Latitude"));
                            longi = Double.parseDouble(mSharedPref.getGlobalVal("Longitude"));
                            Log.d(">>>SELECT CUS", ">>>Lati" + lati + " >>Longi" + longi);
                            if (new DebtorDS(getActivity()).updateCustomerLocationByCurrentCordinates(debtor.getFDEBTOR_CODE(), lati, longi)>0)
                            {
                                new DebtorDS(getActivity()).updateIsSynced(debtor.getFDEBTOR_CODE(),"2");
                                Toast.makeText(getActivity(), "Current co-ordinates updated for " + debtor.getFDEBTOR_CODE() , Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(getActivity(),"Cannot get location, Please move to clear area",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getActivity(),"Debtor already has GPS data",Toast.LENGTH_LONG).show();
                    }

                }else{
                    gpsTracker.showSettingsAlert();
                }

                /* Check whether automatic date time option checked or not */
                int i = android.provider.Settings.Global.getInt(getActivity().getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0);
                /* If option is selected */
                if (i > 0) {
                    MainActivity activity = (MainActivity) getActivity();
                    debtor = customerList.get(position);
                    activity.selectedDebtor = debtor;
                    activity.cusPosition = position;
                    String nic = debtor.getFDEBTOR_NIC();
                    String br = debtor.getFDEBTOR_BIS_REG();

                    Log.d("PRE_SALES_CUSTOMER" , " NIC AND BR: " + nic +"," + br);
                    // un -commented due to menaka request.................. Nuwan..... 30.07.2019.......
                    if (!debtor.getFDEBTOR_NIC().equals("") && !debtor.getFDEBTOR_BIS_REG().equals(""))
                    {
                        txtCusName.setText(activity.selectedDebtor.getFDEBTOR_NAME());

                        //Log.d("PRE_SALES", "DEBTOR_CREDIT_DETAILS" + debtor.getFDEBTOR_CRD_LIMIT() + ", " + debtor.getFDEBTOR_CRD_PERIOD());

                        new SharedPref(getActivity()).setGlobalVal("PrekeyCusCode", debtor.getFDEBTOR_CODE());
                        routeName = new RouteDS(getActivity()).getRouteNameByCode(debtor.getFDEBTOR_CODE());
                        new SharedPref(getActivity()).setGlobalVal("PrekeyRouteName", routeName);

                        if (!routeCode.equals("")||!routeName.equals(""))
                        {
                            String limitFlag = activity.selectedDebtor.getFDEBTOR_CHK_CRD_PRD();
                            String period = activity.selectedDebtor.getFDEBTOR_CRD_PERIOD();
                            String status = activity.selectedDebtor.getFDEBTOR_STATUS();
                            int noOfDays  = new FDDbNoteDS(getActivity()).getOldestFDDBNoteDate(activity.selectedDebtor.getFDEBTOR_CODE());
                            int noOfOverDue = noOfDays - Integer.valueOf(period);

                            if (status.equals("A"))
                            {
                                if (limitFlag.equals("Y"))
                                {
                                    if (noOfOverDue>0)
                                    {
//                                    if (noOfDays > Integer.valueOf(period))
//                                    {
//                                        Log.d(TAG, "Exceed the credit dates" + noOfDue);
                                        creditDatesExceedDialog(String.valueOf(noOfOverDue));
//                                    }
//                                    else
//                                    {
                                        Log.d(TAG, "Exceed the credit dates");

//                                    }
                                    }else{
                                        Log.d(TAG, "credit dates OK" + noOfOverDue);
                                        navigateToHeader(position);
                                    }
                                }else{
                                    Log.d(TAG, "No need to check credit flag");
                                    navigateToHeader(position);
                                }
                            }
                            else
                            {
                                debtorStatusDialog();
                            }
                        }
                        else
                        {
                            routeValidateDialog();
                            //listener.moveBackToCustomer_pre(0);
                        }
                    }
                    else
                    {
                        isValidDebtor();
                    }

                } else {
                    Toast.makeText(getActivity(), "Please tick the 'Automatic Date and Time' option to continue..", Toast.LENGTH_LONG).show();
                    /* Show Date/time settings dialog */
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }
            }
        });

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lvCustomers.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                debtor = customerList.get(position);
                CusInfoBox alertBox = new CusInfoBox();
                Log.d("PRE_SALES_CUSTOMER", "DEBTOR_NAME" + debtor.getFDEBTOR_NAME() + ", " + debtor.getFDEBTOR_CODE());
                if(debtor!=null)
                alertBox.debtorDetailsDialogbox(getActivity(),debtor.getFDEBTOR_CODE(),debtor,true);

                return true;
            }
        });

        return view;
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void creditDatesExceedDialog(String noOfDays) {

        String message = "Selected customer is Over Due with " + noOfDays + " days from the given credit period. Please settle the oldest outstanding.";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Over Due Dates");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                UtilityContainer.PreClearSharedPref(getActivity());
                UtilityContainer.mLoadFragment(new PreSalesInvoice(), getActivity());

            }
        }).setNegativeButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();


            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

    }

    public void debtorStatusDialog() {

        String message = "Customer is NOT ACTIVE. Can not continue";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Customer Status");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                UtilityContainer.PreClearSharedPref(getActivity());
                UtilityContainer.mLoadFragment(new PreSalesInvoice(), getActivity());

            }
        }).setNegativeButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();


            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

    }

    public void routeValidateDialog() {

        String message = "No Route to Selected customer. Can not continue...";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Empty Route ");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                UtilityContainer.PreClearSharedPref(getActivity());
                UtilityContainer.mLoadFragment(new PreSalesInvoice(), getActivity());
                //listener.moveBackToCustomer_pre(0);

            }
        }).setNegativeButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();


            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

    }

    public void isValidDebtor() {

        String message = "No NIC and BR to Selected customer. Can not continue...";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Empty NIC / BR ");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                UtilityContainer.PreClearSharedPref(getActivity());
                UtilityContainer.mLoadFragment(new PreSalesInvoice(), getActivity());
                //listener.moveBackToCustomer_pre(0);

            }
        }).setNegativeButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();


            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

    }

    public void navigateToHeader(int position) {

        MainActivity activity = (MainActivity) getActivity();
        debtor = customerList.get(position);
        activity.selectedDebtor = debtor;
        Toast.makeText(getActivity(), debtor.getFDEBTOR_NAME() + " selected", Toast.LENGTH_LONG).show();
        mSharedPref.setGlobalVal("PrekeyCustomer", "Y");
        mSharedPref.setGlobalVal("preKeyTouRef",tourRefNo);
        mSharedPref.setGlobalVal("preKeyRouteCode", routeCode);
        mSharedPref.setGlobalVal("preKeyAreaCode", areaCode);
        mSharedPref.setGlobalVal("PrekeyRouteName", routeName);
        if (isTourDebtor)
        {
            locCode = new TourHedDS(getActivity()).getCurrentLocCodeFromTourHed().trim();
        }
        else
        {
            locCode = new SalRepDS(getActivity()).getCurrentLocCode().trim();
        }
        mSharedPref.setGlobalVal("PrekeyLocCode", locCode);
        Log.d("PRE_SALES_CUSTOMER", "LOC_CODE: " + locCode);

        listener.moveBackToCustomer_pre(1);
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onAttach(Activity activity) {
        this.mactivity = activity;
        super.onAttach(mactivity);
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
