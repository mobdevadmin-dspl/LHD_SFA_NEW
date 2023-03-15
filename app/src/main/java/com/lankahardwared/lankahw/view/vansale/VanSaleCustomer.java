package com.lankahardwared.lankahw.view.vansale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.CustomerAdapter;
import com.lankahardwared.lankahw.control.CusInfoBox;
import com.lankahardwared.lankahw.control.IResponseListener;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.InvHedDS;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.InvHed;
import com.lankahardwared.lankahw.view.MainActivity;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VanSaleCustomer extends Fragment {
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
    private InvHed tmpInvHed;

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_pre_sales_customer, container, false);
        mSharedPref = new SharedPref(getActivity());
        MainActivity activity = (MainActivity) getActivity();
        lvCustomers = (ListView) view.findViewById(R.id.cus_lv);
        btnCust = (ImageButton) view.findViewById(R.id.btnCust);
        txtCusName = (TextView) view.findViewById(R.id.txtSelCust);
        btnSearch = (ImageButton) view.findViewById(R.id.btnsearch_cus);

        //-----------------------------------from Re order Only-----------------------------------------------------------------------------------
        Bundle mBundle = getArguments();

        if (mBundle != null) {
            tmpInvHed = (InvHed) mBundle.getSerializable("order");
            if (tmpInvHed != null) {
                activity.selectedDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(tmpInvHed.getFINVHED_DEBCODE());
                Log.d("<>*van sales cus****", "" + tmpInvHed.getFINVHED_DEBCODE());
                new SharedPref(getActivity()).setGlobalVal("keyCustomer", "Y");
                new SharedPref(getActivity()).setGlobalVal("keyCusCode",  activity.selectedDebtor.getFDEBTOR_CODE());
            }

        }
//-----------------------------------------------------------------------------------------------------------------------------------


        if (activity.selectedInvHed == null) {
            InvHed invHed = new InvHedDS(getActivity()).getActiveInvhed();

            if (invHed != null) {
                activity.selectedInvHed = invHed;
                if (activity.selectedDebtor == null)
                    activity.selectedDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(invHed.getFINVHED_DEBCODE());
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

        if (activity.selectedDebtor != null)
            txtCusName.setText(activity.selectedDebtor.getFDEBTOR_NAME());

        /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        btnCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
        });

        /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lvCustomers.setOnItemClickListener(new OnItemClickListener() {

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
                    navigateToHeader(position);
                    new SharedPref(getActivity()).setGlobalVal("keyCustomer", "Y");
                    new SharedPref(getActivity()).setGlobalVal("keyCusCode", debtor.getFDEBTOR_CODE());

					/* if not selected */
                } else {
                    android.widget.Toast.makeText(getActivity(), "Please tick the 'Automatic Date and Time' option to continue..", android.widget.Toast.LENGTH_SHORT).show();
                    /* Show Date/time settings dialog */
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }
            }
        });

		/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lvCustomers.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CusInfoBox alertBox = new CusInfoBox();
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
        android.widget.Toast.makeText(getActivity(), debtor.getFDEBTOR_NAME() + " selected", android.widget.Toast.LENGTH_SHORT).show();
        listener.moveNextFragment_Van();
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

        alertDialogBuilder.create().show();
    }
}
