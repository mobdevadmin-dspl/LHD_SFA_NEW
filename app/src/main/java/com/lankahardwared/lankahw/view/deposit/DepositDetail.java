package com.lankahardwared.lankahw.view.deposit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.DepositAdapter;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.DepositDetDS;
import com.lankahardwared.lankahw.data.DepositHedDS;
import com.lankahardwared.lankahw.data.RecHedDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.model.Depohed;
import com.lankahardwared.lankahw.model.RecHed;
import com.lankahardwared.lankahw.view.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Yasith on 3/1/2019.
 */

public class DepositDetail extends Fragment {

    View view;
    EditText txtDepoRefNo,txtCurrentDate,txtDepoSlipNo,txtDepoAmount,txtRemark;
    Spinner spnDepoType;
    Button btnLoadReceipts;
    ListView lv_recpt_list;
   // FloatingActionMenu fam;
    FloatingActionButton fabDiscard,fabSave;
    private ArrayList<Depohed>list;
    private ArrayList<RecHed>recHedList;
    SharedPref mSharedPref;
    DepositAdapter depositAdapter;

    String RefNo = "",PayType = "";

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;

    private SweetAlertDialog pDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_deposit_header, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle("Deposit Details");
        toolbar.setLogo(R.drawable.dm_logo_64);
        setHasOptionsMenu(true);

        mSharedPref = new SharedPref(getContext());
        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        depositAdapter =  new DepositAdapter(getContext(),recHedList,"0.00","0");

        //region INITIALIZE CONTROLLERS
        txtDepoRefNo = (EditText) view.findViewById(R.id.txtDepoRefNo);
        txtCurrentDate = (EditText) view.findViewById(R.id.txtDepoDate);
        txtDepoSlipNo = (EditText) view.findViewById(R.id.txtDepoSlipNo);
        txtDepoAmount = (EditText) view.findViewById(R.id.txtDepoAmount);
        txtRemark = (EditText) view.findViewById(R.id.txtDepoRemarks);
        spnDepoType = (Spinner) view.findViewById(R.id.spnDepoType);
        lv_recpt_list = (ListView) view.findViewById(R.id.lv_depo_det);
        btnLoadReceipts = (Button) view.findViewById(R.id.btnLoadReceipts);
       // fam = (com.github.clans.fab.FloatingActionMenu) view.findViewById(R.id.fab_menu);
        fabDiscard = (FloatingActionButton) view.findViewById(R.id.btn_undo);
        fabSave = (FloatingActionButton) view.findViewById(R.id.btn_save);
        //endregion

        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.DepoNumVal));
        txtDepoRefNo.setText(RefNo);
        currentDate();
        setDepoTypeSpinnerValues();
        txtDepoAmount.selectAll();
        txtDepoAmount.setSelectAllOnFocus(true);

        //region fam CLICK EVENT
        //endregion


        //region btnLoadReceipts CLICK EVENT
        btnLoadReceipts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if(spnDepoType.getSelectedItem().toString().contains("-SELE"))
                {
                    Toast.makeText(getActivity(),"Select a Deposit Type",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(txtDepoSlipNo.getText().toString()))
                {
                    Toast.makeText(getActivity(),"Enter Deposit Slip No",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(txtDepoAmount.getText().toString()))
                {
                    Toast.makeText(getActivity(),"Enter Deposit Amount",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    String depositValue = txtDepoAmount.getText().toString();

                    if(Double.valueOf(depositValue) <= 0)
                    {
                        Toast.makeText(getActivity(),"Enter A Valid Deposit Amount",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                saveDepositOnLoadReceipt();

                if(spnDepoType.getSelectedItem().toString().equals("CASH"))
                {
                    UtilityContainer.ClearDepositSharedPref(getActivity());
                    int result = new DepositHedDS(getActivity()).resetData(RefNo);
                    if (result > 0)
                    {
                        result = 0;
                        result = new DepositDetDS(getActivity()).resetData(RefNo);
                    }
                    mSharedPref.setGlobalVal("DepoAllocAmt","0.00");
                    mSharedPref.setGlobalVal("DepoRemainAmt","0.00");
                    loadReceipts("CA");

                }
                else if(spnDepoType.getSelectedItem().toString().equals("CHEQUE"))
                {
                    UtilityContainer.ClearDepositSharedPref(getActivity());
                    int result = new DepositHedDS(getActivity()).resetData(RefNo);
                    if (result > 0)
                    {
                        result = 0;
                        result = new DepositDetDS(getActivity()).resetData(RefNo);
                    }
                    mSharedPref.setGlobalVal("DepoAllocAmt","0.00");
                    mSharedPref.setGlobalVal("DepoRemainAmt","0.00");
                    loadReceipts("CH");
                }
            }
        });
        //endregion


        //region fabSave CLICK EVENT
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDepositInSummary();
            }
        });
        //endregion


        //region fabDiscard CLICK EVENT
        fabDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoEditingData();
            }
        });
        //endregion


        //region txtDepoAmount TEXT CHANGED LISTENER
        txtDepoAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                lv_recpt_list.setAdapter(null);
                UtilityContainer.ClearDepositSharedPref(getActivity());
                int result = new DepositHedDS(getActivity()).resetData(RefNo);
                if (result > 0)
                {
                    result = 0;
                    result = new DepositDetDS(getActivity()).resetData(RefNo);
                }
                mSharedPref.setGlobalVal("DepoAllocAmt","0.00");
                mSharedPref.setGlobalVal("DepoRemainAmt","0.00");
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                lv_recpt_list.setAdapter(null);
                UtilityContainer.ClearDepositSharedPref(getActivity());
                int result = new DepositHedDS(getActivity()).resetData(RefNo);
                if (result > 0)
                {
                    result = 0;
                    result = new DepositDetDS(getActivity()).resetData(RefNo);
                }
                mSharedPref.setGlobalVal("DepoAllocAmt","0.00");
                mSharedPref.setGlobalVal("DepoRemainAmt","0.00");
            }
        });
        //endregion


        //region txtDepoAmount CLICK EVENT
//        txtDepoAmount.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//
//            }
//        });
        //endregion

        //region spnDepoType ON ITEM CLICK
        spnDepoType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                lv_recpt_list.setAdapter(null);
                UtilityContainer.ClearDepositSharedPref(getActivity());
                int result = new DepositHedDS(getActivity()).resetData(RefNo);
                if (result > 0)
                {
                    result = 0;
                    result = new DepositDetDS(getActivity()).resetData(RefNo);
                }
                mSharedPref.setGlobalVal("DepoAllocAmt","0.00");
                mSharedPref.setGlobalVal("DepoRemainAmt","0.00");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        //endregion

        return view;
    }


    //region saveDepositOnLoadReceipt FUNCTION
    public void saveDepositOnLoadReceipt()
    {
        Depohed depohed = new Depohed();
        list = new ArrayList<Depohed>();

        depohed.setFBANKDEPOHED_REFNO(txtDepoRefNo.getText().toString());
        depohed.setFBANKDEPOHED_TXNDATE(txtCurrentDate.getText().toString());
        String remark = txtRemark.getText().toString();
        remark = remark.replaceAll("[^a-zA-Z0-9]", " ");
        depohed.setFBANKDEPOHED_REMARKS(remark);

        if (spnDepoType.getSelectedItem().toString().equals("CASH")) {
            depohed.setFBANKDEPOHED_PAYTYPE("CA");
        } else if (spnDepoType.getSelectedItem().toString().equals("CHEQUE")) {
            depohed.setFBANKDEPOHED_PAYTYPE("CH");
        }

        depohed.setFBANKDEPOHED_SLIPNO(txtDepoSlipNo.getText().toString());
        depohed.setFBANKDEPOHED_DEPOAMT(txtDepoAmount.getText().toString());
        depohed.setFBANKDEPOHED_ADDDATE(txtCurrentDate.getText().toString());
        depohed.setFBANKDEPOHED_ADDUSER(new SalRepDS(getActivity()).getCurrentRepCode());
        depohed.setFBANKDEPOHED_ADDMACH(localSP.getString("MAC_Address", "No MAC Address").toString());
        depohed.setFBANKDEPOHED_ISSYNCED("0");
        depohed.setFBANKDEPOHED_ISACTIVE("1");
        list.add(depohed);

        int result = new DepositHedDS(getActivity()).createOrUpdateDepositHed(list);
        if (result > 0)
        {
            Log.d("Deposit", "Saved");

            loadReceipts(spnDepoType.getSelectedItem().toString());
        }

    }
    //endregion

    //region currentDate FUNCTION
    private void currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        txtCurrentDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
    //endregion


    //region setDepoTypeSpinnerValues FUNCTION
    private void setDepoTypeSpinnerValues()
    {
        try
        {

            List<String> depositTypeList = new ArrayList<String>();
            depositTypeList.add("-SELECT-");
            depositTypeList.add("CASH");
            depositTypeList.add("CHEQUE");

            ArrayAdapter<String> cardListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, depositTypeList);
            cardListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnDepoType.setAdapter(cardListAdapter);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //endregion


    //region loadReceipts FUNCTION
    private void loadReceipts(String payType)
    {
        try
        {
            lv_recpt_list.setClickable(false);

            if (payType.equals("CA"))
            {
                PayType = "CA";
                new loadReceiptListAsyncTask().execute();
            }
            else if(payType.equals("CH"))
            {
                PayType = "CH";
                new loadReceiptListAsyncTask().execute();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //endregion


    //region saveDepositInSummary FUNCTION
    private void saveDepositInSummary()
    {
        try
        {
            String depoAllocatedAmt = mSharedPref.getGlobalVal("DepoAllocAmt");
            String depositAmt = txtDepoAmount.getText().toString();

            if(! TextUtils.isEmpty(depositAmt))
            {
                if (Double.valueOf(depoAllocatedAmt).equals(Double.valueOf(depositAmt)))
                {
                    if (new DepositDetDS(getActivity()).getItemCount(RefNo) > 0)
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setMessage("Do you want to save the deposit ?");
                        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(final DialogInterface dialog, int id) {

                                Depohed depohed = new Depohed();
                                list = new ArrayList<Depohed>();

                                depohed.setFBANKDEPOHED_REFNO(RefNo);
                                depohed.setFBANKDEPOHED_TXNDATE(txtCurrentDate.getText().toString());
                                depohed.setFBANKDEPOHED_REMARKS(txtRemark.getText().toString());

                                if (spnDepoType.getSelectedItem().toString().equals("CASH"))
                                {
                                    depohed.setFBANKDEPOHED_PAYTYPE("CA");
                                }
                                else if (spnDepoType.getSelectedItem().toString().equals("CHEQUE"))
                                {
                                    depohed.setFBANKDEPOHED_PAYTYPE("CH");
                                }

                                depohed.setFBANKDEPOHED_ADDDATE(txtCurrentDate.getText().toString());
                                depohed.setFBANKDEPOHED_ADDUSER(new SalRepDS(getActivity()).getCurrentRepCode());
                                depohed.setFBANKDEPOHED_ADDMACH(localSP.getString("MAC_Address", "No MAC Address").toString());
                                depohed.setFBANKDEPOHED_SLIPNO(txtDepoSlipNo.getText().toString());
                                depohed.setFBANKDEPOHED_DEPOAMT(txtDepoAmount.getText().toString());
                                depohed.setFBANKDEPOHED_ISSYNCED("0");
                                depohed.setFBANKDEPOHED_ISACTIVE("0");
                                list.add(depohed);

                                int result = new DepositHedDS(getActivity()).createOrUpdateDepositHed(list);
                                if (result > 0) {
                                    result = 0;
                                    ArrayList<String> selectedReceptRefNoList = new ArrayList<String>();
                                    selectedReceptRefNoList = new DepositDetDS(getActivity()).getSelectedReceiptRefNoByDepositRefNo(RefNo);

                                    if (selectedReceptRefNoList.size() > 0)
                                    {
                                        result = new RecHedDS(getActivity()).updateIsDepositStatus(true, selectedReceptRefNoList);
                                        if (result > 0)
                                        {
                                            UtilityContainer.ClearDepositSharedPref(getActivity());
                                            new ReferenceNum(getActivity()).nNumValueInsertOrUpdate(getResources().getString(R.string.DepoNumVal));
                                            Toast.makeText(getActivity(), "Deposit saved successfully..!", Toast.LENGTH_SHORT).show();
                                            UtilityContainer.mLoadFragment(new DepositInvoice(), getActivity());
                                        }
                                    }
                                }
                            }

                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alertD = alertDialogBuilder.create();
                        alertD.show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Select Receipts before save ...!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "You should allocate total deposit to receipts ...!", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getActivity(), "Enter Deposit Amount", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //endregion


    //region undoEditingData FUNCTION
    public void undoEditingData() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Do you want to discard the deposit ?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                int result = new DepositHedDS(getActivity()).resetData(RefNo);

                if (result > 0)
                {
                    result = 0;
                    result = new DepositDetDS(getActivity()).resetData(RefNo);

                    Toast.makeText(getActivity(), "Deposit discarded successfully..!", Toast.LENGTH_SHORT).show();
                    UtilityContainer.ClearDepositSharedPref(getActivity());
                    UtilityContainer.mLoadFragment(new DepositInvoice(),getActivity());

                }
                else
                {
                    Toast.makeText(getActivity(), "Deposit discarded successfully..!", Toast.LENGTH_SHORT).show();
                    UtilityContainer.ClearDepositSharedPref(getActivity());
                    UtilityContainer.mLoadFragment(new DepositInvoice(),getActivity());
                }


            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }
    //endregion


    //region loadReceiptListAsyncTask CLASS
    public class loadReceiptListAsyncTask extends AsyncTask<Object, Object, ArrayList<RecHed>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected ArrayList<RecHed> doInBackground(Object... objects)
        {
            if(PayType.equals("CA"))
            {
                recHedList = new RecHedDS(getActivity()).getRecHedDataByType("CA");
            }
            else
            {
                recHedList = new RecHedDS(getActivity()).getRecHedDataByType("CH");
            }
            return recHedList;
        }


        @Override
        protected void onPostExecute(ArrayList<RecHed> recHed) {
            super.onPostExecute(recHed);

            if (!recHedList.isEmpty())
            {
                depositAdapter = new DepositAdapter(getActivity(), recHedList, txtDepoAmount.getText().toString(), new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.DepoNumVal)));
                lv_recpt_list.setAdapter(depositAdapter);
                lv_recpt_list.setClickable(true);

                if (pDialog.isShowing())
                {
                    pDialog.dismiss();
                }
            }
            else
            {
                Toast.makeText(getActivity(), "No Receipts Available", Toast.LENGTH_SHORT).show();
                if (pDialog.isShowing())
                {
                    pDialog.dismiss();
                }
            }
         }
    }
    //endregion

}
