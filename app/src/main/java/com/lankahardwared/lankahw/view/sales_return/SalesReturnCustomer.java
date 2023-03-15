package com.lankahardwared.lankahw.view.sales_return;

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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
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
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.FInvRHedDS;
import com.lankahardwared.lankahw.data.TourHedDS;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.FInvRHed;
import com.lankahardwared.lankahw.model.TourHed;
import com.lankahardwared.lankahw.view.MainActivity;

import java.util.ArrayList;

public class SalesReturnCustomer extends Fragment {
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    View view;
    ListView lvCustomers;
    ArrayList<Debtor> customerList;
    Debtor debtor;
    CustomerAdapter customerAdapter;
    Spinner spnTour;
    SharedPref mSharedPref;
    GPSTracker gpsTracker;
    IResponseListener listener;
    ImageButton btnCust,btnSearch;
    ProgressDialog progressDialog;
    TextView txtCusName;
    ArrayList<TourHed> tourlist;
    String routeCode;
    String tourRefNo;
    String repCode, areaCode, driverCode, helperCode, lorryCode;

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_pre_sales_customer, container, false);
        setHasOptionsMenu(true);
        mSharedPref = new SharedPref(getActivity());
        MainActivity activity = (MainActivity) getActivity();
        spnTour = (Spinner) view.findViewById(R.id.spnTour);
        lvCustomers = (ListView) view.findViewById(R.id.cus_lv);
        gpsTracker = new GPSTracker(getActivity());
        btnCust = (ImageButton) view.findViewById(R.id.btnCust);
        btnSearch = (ImageButton) view.findViewById(R.id.btnsearch_cus);
        txtCusName = (TextView) view.findViewById(R.id.txtSelCust);

        tourlist = new TourHedDS(getActivity()).getTourDetails("");

        ArrayList<String> strList = new ArrayList<String>();
        strList.add("Select a Tour to continue ...");

        for (TourHed hed : tourlist)
            strList.add(hed.getTOURHED_REFNO() + " - " + hed.getTOURHED_ID());

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, strList);
//        {
//            public View getDropDownView(int position, View convertView,ViewGroup parent)
//            {
//                View v = super.getDropDownView(position, convertView,parent);
//                ((TextView) v).setGravity(Gravity.CENTER);
//                return v;
//            }
//        };
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTour.setAdapter(dataAdapter1);

        spnTour.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0)
                {
                    routeCode = tourlist.get(position - 1).getTOURHED_ROUTECODE();
                    tourRefNo = tourlist.get(position - 1).getTOURHED_REFNO();
                    repCode = tourlist.get(position - 1).getTOURHED_REPCODE();
                    areaCode = new TourHedDS(getActivity()).getAreaCode(tourRefNo);
                    driverCode = tourlist.get(position - 1).getTOURHED_DRIVERCODE();
                    helperCode = tourlist.get(position - 1).getTOURHED_HELPERCODE();
                    lorryCode = tourlist.get(position - 1).getTOURHED_LORRYCODE();
                    Log.d("SALES_RETURN_CUSTOMER", "ROUTE_CODE_IS: " + routeCode +", " + areaCode + ", " + driverCode + ", " + helperCode + " , " + lorryCode + " , " + repCode);

                    lvCustomers.setAdapter(null);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

//        localSP = activity.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = activity.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        ArrayList<FInvRHed> getReturnHed = new FInvRHedDS(getActivity()).getAllActiveInvrhed();

        if (activity.selectedReturnHed == null) {

            if (getReturnHed.size() >0) {
                activity.selectedReturnHed = getReturnHed.get(0);
                if (activity.selectedRetDebtor == null)
                    activity.selectedRetDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(getReturnHed.get(0).getFINVRHED_DEBCODE());
            }
        }

        if (activity.selectedRetDebtor != null)
            txtCusName.setText(activity.selectedRetDebtor.getFDEBTOR_NAME());

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
                        //customerList = new DebtorDS(getActivity()).getRouteCustomers("", "");
                        customerList = new DebtorDS(getActivity()).getRouteCustomersByCodeAndName(routeCode, "");
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

        lvCustomers.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {
                if (!(gpsTracker.canGetLocation())) {
                    gpsTracker.showSettingsAlert();
                }
                /* Check whether automatic date time option checked or not */
                int i = android.provider.Settings.Global.getInt(getActivity().getContentResolver(),
                        android.provider.Settings.Global.AUTO_TIME, 0);

                /* If option is selected */
                if (i > 0) {
                    debtor = customerList.get(position);
                    MainActivity activity = (MainActivity) getActivity();
                    activity.selectedRetDebtor = debtor;
                    new SharedPref(getActivity()).setGlobalVal("RetkeyCusCode",debtor.getFDEBTOR_CODE());
                    txtCusName.setText(debtor.getFDEBTOR_NAME());
                    navigateToHeader(position);
                } else {
                    Toast.makeText(getActivity(), "Please tick the 'Automatic Date and Time' option to continue..", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }
            }
        });

		/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lvCustomers.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CusInfoBox alertBox = new CusInfoBox();
                debtor = customerList.get(position);
                Log.d("SALES_RETURN_CUSTOMER", "DEBTOR_NAME" + debtor.getFDEBTOR_NAME() + debtor.getFDEBTOR_CODE());
                if(debtor!=null)
                alertBox.debtorDetailsDialogbox(getActivity(), debtor.getFDEBTOR_NAME(), debtor,false);

                return true;
            }
        });

        return view;
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

//        for (int i = 0; i < menu.size(); ++i) {
//            menu.removeItem(menu.getItem(i).getItemId());
//        }
//
//        inflater.inflate(R.menu.frag_nonprd_menu, menu);
//        SearchView searchView = (SearchView) menu.findItem(R.id.searchItems).getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                lvCustomers = (ListView) view.findViewById(R.id.cus_lv);
//                lvCustomers.clearTextFilter();
//                customerList = new DebtorDS(getActivity()).getRouteCustomers(routeCode, newText);
//                lvCustomers.setAdapter(new CustomerAdapter(getActivity(), customerList));
//                return false;
//            }
//        });
//        super.onCreateOptionsMenu(menu, inflater);
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void navigateToHeader(int position) {

        MainActivity activity = (MainActivity) getActivity();
        debtor = customerList.get(position);
        activity.selectedRetDebtor = debtor;
        mSharedPref.setGlobalVal("returnkeyCustomer", "1");
        mSharedPref.setGlobalVal("returnKeyTouRef",tourRefNo);
        mSharedPref.setGlobalVal("returnKeyRouteCode", routeCode);
        mSharedPref.setGlobalVal("returnKeyRepCode", repCode);
        mSharedPref.setGlobalVal("returnkeyAreaCode", areaCode);
        mSharedPref.setGlobalVal("returnKeyDriverCode",driverCode);
        mSharedPref.setGlobalVal("returnKeyHelperCode", helperCode);
        mSharedPref.setGlobalVal("returnKeyLorryCode", lorryCode);
        mSharedPref.setGlobalVal("returnKeyLorryCode", lorryCode);
        listener.moveNextFragment_Ret();
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
