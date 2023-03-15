package com.lankahardwared.lankahw.view.sales_return;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.ProductAdapter;
import com.lankahardwared.lankahw.adapter.ReturnDetailsAdapter;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.data.DebItemPriDS;
import com.lankahardwared.lankahw.data.FInvRDetDS;
import com.lankahardwared.lankahw.data.FInvRHedDS;
import com.lankahardwared.lankahw.data.ItemPriDS;
import com.lankahardwared.lankahw.data.ItemsDS;
import com.lankahardwared.lankahw.data.ReasonDS;
import com.lankahardwared.lankahw.data.TaxDetDS;
import com.lankahardwared.lankahw.data.TourHedDS;
import com.lankahardwared.lankahw.listviewitems.CustomKeypadDialogPrice;
import com.lankahardwared.lankahw.model.FInvRDet;
import com.lankahardwared.lankahw.model.FInvRHed;
import com.lankahardwared.lankahw.model.Items;
import com.lankahardwared.lankahw.model.Reason;
import com.lankahardwared.lankahw.view.MainActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SalesReturnDetails extends Fragment implements OnClickListener {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    public String RefNo = "";
    View view;
    Button itemSearch, bAdd, bFreeIssue;
    EditText lblItemName, txtQty, editTotDisc,lblNou;
    TextView lblPrice;
    int totPieces = 0;
    double amount = 0.00, changedPrice = 0.0, price = 0.0;
    double values = 0.00, iQoh;
   // TextView lblNou, lblPrice;
    Items selectedItem = null;
    Reason selectedReason = null;
    int seqno = 0, index_id = 0;
    ListView lv_return_det;
    ArrayList<FInvRDet> returnList;
    SharedPref mSharedPref;
    boolean hasChanged = false;
    String locCode;
    double brandDisPer;
    Spinner returnType;
    ArrayList<Items> list = null;
    ArrayList<Reason> reasonList = null;
    MainActivity activity;
    SweetAlertDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_return_details, container, false);
        mSharedPref = new SharedPref(getActivity());

        seqno = 0;
        totPieces = 0;
//        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        itemSearch = (Button) view.findViewById(R.id.btn_item_search);
        //reasonSearch = (Button) view.findViewById(R.id.btn_reason_search);
        bAdd = (Button) view.findViewById(R.id.btn_add);
        bFreeIssue = (Button) view.findViewById(R.id.btn_free);
        lblItemName = (EditText) view.findViewById(R.id.et_item);
        //lblReason = (EditText) view.findViewById(R.id.et_reason);
        activity = (MainActivity) getActivity();
        lv_return_det = (ListView) view.findViewById(R.id.lv_return_det);

        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.VanReturnNumVal));

        editTotDisc = (EditText) view.findViewById(R.id.et_TotalDisc);
        lblNou = (EditText) view.findViewById(R.id.tv_unit);
        lblPrice = (TextView) view.findViewById(R.id.tv_price);
        txtQty = (EditText) view.findViewById(R.id.et_pieces);
        returnType = (Spinner) view.findViewById(R.id.spinner_return_Type);

        // ------------------------------- Load return products ----------------- Nuwan ------------ 28/09/2018 -------------------
        itemSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //itemSearch.setEnabled(false);
                new LoardingReturnProductFromDB().execute();
            }
        });

        // -------------------------------------------------------------------------------------------------------------------------
        //reasonSearch.setOnClickListener(this);
        bAdd.setOnClickListener(this);
        bFreeIssue.setOnClickListener(this);
        lblPrice.setOnClickListener(this);

//            ArrayList<FInvRHed> getReturnHed = new FInvRHedDS(getActivity()).getAllActiveInvrhed();
//
//            if (!getReturnHed.isEmpty()) {
//
//                for (FInvRHed returnHed : getReturnHed) {
//                    activity.selectedReturnHed = returnHed;
//
//                    if (activity.selectedRetDebtor == null) {
//                        DebtorDS debtorDS = new DebtorDS(getActivity());
//                        activity.selectedRetDebtor = debtorDS.getSelectedCustomerByCode(returnHed.getFINVRHED_DEBCODE());
//                    }
//                }
//            }

        ArrayList<String> strList = new ArrayList<String>();
        strList.add("Select Return type to continue ...");
//        strList.add("MR");
//        strList.add("UR");
        strList.add("SA");
        strList.add("FR");

        final ArrayAdapter<String> returnTypeAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.return_spinner_item, strList);
        returnTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        returnType.setAdapter(returnTypeAdapter);
        FetchData();

			/*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        txtQty.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if ((txtQty.length() > 0)) {
                    totPieces = Integer.parseInt(txtQty.getText().toString());
                    hasChanged = true;
                    amount = Double.parseDouble(txtQty.getText().toString())
                            * Double.parseDouble(lblPrice.getText().toString());
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

            /*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        txtQty.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                txtQty.setText("");
            }
        });

            /*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lv_return_det.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FInvRDet returnDet = returnList.get(position);
                deleteOrderDialog(getActivity(), "Return Details ", returnDet.getFINVRDET_ID());
                return true;
            }
        });

            /*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lv_return_det.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {
                FInvRDet returnDet = returnList.get(position);
                FetchData();
                bAdd.setText("EDIT");
                selectedItem = new Items();
                selectedItem.setFITEM_ITEM_CODE(returnDet.getFINVRDET_ITEMCODE());
                index_id = Integer.parseInt(returnDet.getFINVRDET_ID());
                lblItemName.setText(new ItemsDS(getActivity()).getItemNameByCode(returnDet.getFINVRDET_ITEMCODE()));
                txtQty.setText(returnDet.getFINVRDET_QTY());
//
                if (returnType.getSelectedItem().toString().equalsIgnoreCase("FR"))
                {
                    lblPrice.setText("0.00");
                }
                else
                {
                    //lblPrice.setText("10000.00");
                    lblPrice.setText(new ItemPriDS(getActivity()).getProductPriceByCode(selectedItem.getFITEM_ITEM_CODE(), activity.selectedRetDebtor.getFDEBTOR_PRILLCODE()));

                }
                hasChanged = false;
                editTotDisc.setText(returnDet.getFINVRDET_DIS_AMT());
                //lblReason.setText(returnDet.getFINVRDET_RETURN_REASON());
            }
        });
        lblPrice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomKeypadDialogPrice keypadPrice = new CustomKeypadDialogPrice(getActivity(), true, new CustomKeypadDialogPrice.IOnOkClickListener() {
                    @Override
                    public void okClicked(double value) {
                        //price cannot be changed less than gross profit
                        //changedPrice = price;
                        //validation removed from return 2019/04/01 - said menaka
                        // if(minPrice <=value && value <= maxPrice) {
                        //  save changed price
                        new FInvRDetDS(getActivity()).updateProductPrice(selectedItem.getFITEM_ITEM_CODE(), String.valueOf(price));
                        //  value should be set for another variable in preProduct
                        //  preProduct.setPREPRODUCT_PRICE(String.valueOf(value));
                        changedPrice = value;
                        lblPrice.setText(""+changedPrice);
//                            }else{
//                                //changedPrice = price;
//                                Toast.makeText(getActivity(),"Price cannot be change..",Toast.LENGTH_LONG).show();
//                            }
                    }
                });
                keypadPrice.show();

                keypadPrice.setHeader("CHANGE PRICE");
//                if(preProduct.getPREPRODUCT_CHANGED_PRICE().equals("0")){
                keypadPrice.loadValue(changedPrice);
            }
        });
        //Log.d("SALES_RETURN_DETAILS", "REASON_CODE: " + activity.selectedDebtor.getFDEBTOR_CODE());
        return view;
    }


    //---------------------------------Loading Return Product FromDB ---------------- Nuwan --------------------------- 28/09/2018 ---------------------------------------------

    public class LoardingReturnProductFromDB extends AsyncTask<Object, Object, ArrayList<Items>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Fetch Data Please Wait.");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected ArrayList<Items> doInBackground(Object... objects) {

            //list = new ItemsDS(getActivity()).getAllItemForSalesReturn("","txntype ='21'",RefNo, "LOC01","MWP");
            list = new ItemsDS(getActivity()).getAllItemForSalesReturn("","txntype ='21'",RefNo, new TourHedDS(getActivity()).getCurrentLocCodeFromTourHed(),activity.selectedRetDebtor.getFDEBTOR_PRILLCODE());
            Log.v("Return Item count", ">>>>>"+list.size());
            return list;
        }


        @Override
        protected void onPostExecute(ArrayList<Items> items) {
            super.onPostExecute(items);

            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
            prodcutDetailsDialogbox();

        }
    }
//------------------------------------------------------------------------------------------------------------------------


    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-grab data from table-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_item_search:
                clearTextFields();
                //prodcutDetailsDialogbox();
                //new LoardingReturnProductFromDB().execute();
                break;
//            case R.id.btn_reason_search:
//                //reasonsDialogbox();
//                break;
            case R.id.btn_add: {

                if (!(lblItemName.getText().toString().equals(""))) {

                    if (values >= 0.0 && totPieces > 0) {

                        FInvRDet ReturnDet = new FInvRDet();
                        ArrayList<FInvRDet> ReturnList = new ArrayList<FInvRDet>();

                        ArrayList<FInvRHed> returnHedList = new ArrayList<FInvRHed>();


//                        String TaxedAmt = new TaxDetDS(getActivity()).calculateTax(selectedItem.getFITEM_ITEM_CODE(),
//                                new BigDecimal(amount - Double.parseDouble(editTotDisc.getText().toString())));

                        String TaxedAmt = "0.0";


//                        String TaxedAmt = new TaxDetDS(getActivity()).calculateTaxFromDebTax(activity.selectedReturnHed.getFINVRHED_DEBCODE(),
//                                selectedItem.getFITEM_ITEM_CODE(),new BigDecimal(amount - Double.parseDouble(editTotDisc.getText().toString())));

                        //Log.d("SALES_RETURN_DETAILS", "REF-NO_IS: " + RefNo + " GLOBAL_REF_IS: " + mSharedPref.getGlobalVal("returnKeyTouRef"));

                        activity.selectedReturnHed.setFINVRHED_COSTCODE(new TourHedDS(getActivity()).getCostCode(mSharedPref.getGlobalVal("returnKeyTouRef")));
                        activity.selectedReturnHed.setFINVRHED_LOCCODE(new TourHedDS(getActivity()).getCurrentLocCodeFromTourHed());
                        activity.selectedReturnHed.setFINVRHED_RETURN_TYPE(returnType.getSelectedItem().toString());
                        activity.selectedReturnHed.setFINVRHED_REASON_CODE(new ReasonDS(getActivity()).getReaCodeByName(mSharedPref.getGlobalVal("returnKeyReasonCode")));
                        activity.selectedReturnHed.setFINVRHED_TOTAL_TAX(TaxedAmt);


                        returnHedList.add(activity.selectedReturnHed);

                        if (new FInvRHedDS(getActivity()).createOrUpdateInvRHed(returnHedList) > 0) {
                            seqno++;
                            ReturnDet.setFINVRDET_ID(index_id + "");
                            ReturnDet.setFINVRDET_SEQNO(seqno + "");
                            ReturnDet.setFINVRDET_COST_PRICE(new ItemsDS(getActivity()).getCostPriceItemCode(selectedItem.getFITEM_ITEM_CODE()));
                            ReturnDet.setFINVRDET_SELL_PRICE(lblPrice.getText().toString());
                            //ReturnDet.setFINVRDET_T_SELL_PRICE(lblPrice.getText().toString());
                            double price = Double.parseDouble(lblPrice.getText().toString());
                            double disc = Double.parseDouble(editTotDisc.getText().toString());
                            double qty = Double.parseDouble(txtQty.getText().toString());
                            double tax = Double.parseDouble(TaxedAmt);
                            String unitPrice = new TaxDetDS(getActivity()).calculateReverseTaxFromDebTax(activity.selectedReturnHed.getFINVRHED_DEBCODE(),selectedItem.getFITEM_ITEM_CODE(), new BigDecimal(changedPrice));
                            double amt = Double.parseDouble(unitPrice)* qty;
                            String sellPrice = String.format("%.2f",Double.valueOf(unitPrice));

//                            String tSellPrice = String.format("%.2f",(price + (tax/qty)));
                            String tSellPrice = String.format("%.2f",amt);
                            //commented by rashmi because this is old code
//                            ReturnDet.setFINVRDET_SELL_PRICE(sellPrice);

//                            ReturnDet.setFINVRDET_T_SELL_PRICE(String.format("%.2f", amt + Double.parseDouble(TaxedAmt)));
////                            ReturnDet.setFINVRDET_T_SELL_PRICE(tSellPrice);
//                            ReturnDet.setFINVRDET_DIS_AMT(editTotDisc.getText().toString());
//                            String discount = new TaxDetDS(getActivity()).calculateReverseTaxFromDebTax(activity.selectedReturnHed.getFINVRHED_DEBCODE(),selectedItem.getFITEM_ITEM_CODE(), new BigDecimal(disc));
////                            ReturnDet.setFINVRDET_AMT(String.format("%.2f", amount + Double.parseDouble(TaxedAmt) - Double.parseDouble(editTotDisc.getText().toString())));
//                            ReturnDet.setFINVRDET_AMT(String.valueOf(amt - Double.parseDouble(discount)));
//added 2019-05-17 as mega return.
                            ReturnDet.setFINVRDET_CHANGED_PRICE(String.format("%.2f",changedPrice));
                            ReturnDet.setFINVRDET_COST_PRICE(lblPrice.getText().toString());
                            ReturnDet.setFINVRDET_SELL_PRICE(""+price);
                            ReturnDet.setFINVRDET_T_SELL_PRICE(""+price);
                            ReturnDet.setFINVRDET_DIS_AMT(editTotDisc.getText().toString());
                            ReturnDet.setFINVRDET_AMT(
                                    String.format("%.2f", Double.parseDouble(txtQty.getText().toString())
                                            * Double.parseDouble(unitPrice)));
                            ReturnDet.setFINVRDET_TAX_AMT(TaxedAmt);
                            ReturnDet.setFINVRDET_QTY(totPieces + "");
                            ReturnDet.setFINVRDET_BAL_QTY(totPieces + "");
                            ReturnDet.setFINVRDET_RETURN_REASON(mSharedPref.getGlobalVal("returnKeyReasonCode"));
                            ReturnDet.setFINVRDET_RETURN_REASON_CODE(new ReasonDS(getActivity()).getReaCodeByName(mSharedPref.getGlobalVal("returnKeyReasonCode")));
                            ReturnDet.setFINVRDET_REFNO(RefNo);
                            ReturnDet.setFINVRDET_ITEMCODE(selectedItem.getFITEM_ITEM_CODE());
                            ReturnDet.setFINVRDET_PRILCODE(activity.selectedRetDebtor.getFDEBTOR_PRILLCODE());
                            ReturnDet.setFINVRDET_IS_ACTIVE("1");
                            //ReturnDet.setFINVRDET_TAXCOMCODE(new ItemsDS(getActivity()).getTaxComCodeByItemCode(selectedItem.getFITEM_ITEM_CODE()));
                            ReturnDet.setFINVRDET_TAXCOMCODE(new ItemsDS(getActivity()).
                                    getTaxComCodeByItemCodeBeforeDebTax(selectedItem.getFITEM_ITEM_CODE(), activity.selectedRetDebtor.getFDEBTOR_CODE()));
                            ReturnDet.setFINVRDET_TXN_DATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                            //ReturnDet.setFINVRDET_TXN_TYPE("21");
                            ReturnDet.setFINVRDET_TXN_TYPE("25");
                            ReturnDet.setFINVRDET_RETURN_TYPE(returnType.getSelectedItem().toString());


                            ReturnList.add(ReturnDet);

                            Log.d("SALES_RETURN_DETAILS", "DET_DETAILS_ARE: " + "COST: " + ReturnDet.getFINVRDET_COST_PRICE());

                            //	if (!(bAdd.getText().equals("EDIT") && hasChanged == false)) {
                            new FInvRDetDS(getActivity()).createOrUpdateInvRDet(ReturnList);
                            //	}

                            if (bAdd.getText().equals("EDIT"))
                                Toast
                                        .makeText(getActivity(), "Edited successfully !", Toast.LENGTH_LONG)
                                        .show();
                            else
                                Toast
                                        .makeText(getActivity(), "Added successfully !", Toast.LENGTH_LONG)
                                        .show();
                            FetchData();
                            clearTextFields();

                        }
                    }
                }
            }
            break;
            default:
                break;
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private void deleteOrderDialog(final Context context, String title, final String Id) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to delete this entry?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                int count = new FInvRDetDS(context).deleteRetDetByID(Id);

                if (count > 0) {
                    Toast.makeText(getActivity(), "Deleted successfully", Toast.LENGTH_LONG).show();
                    clearTextFields();
                    FetchData();
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

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void FetchData() {
        try {
            lv_return_det.setAdapter(null);
            returnList = new FInvRDetDS(getActivity()).getAllInvRDet(activity.selectedReturnHed.getFINVRHED_REFNO());
            lv_return_det.setAdapter(new ReturnDetailsAdapter(getActivity(), returnList));

        } catch (NullPointerException e) {
            Log.v(" Error", e.toString());
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private void clearTextFields() {
        values = 0.0;
        index_id = 0;
        totPieces = 0;
        lblItemName.setText("");
        txtQty.setText("0");
        txtQty.clearFocus();
        lblNou.setText("0");
        lblPrice.setText("0.00");
        editTotDisc.setText("0.00");
        bAdd.setText("ADD");
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void prodcutDetailsDialogbox() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_details_search_item);

        final SearchView search = (SearchView) dialog.findViewById(R.id.et_search);
        final ListView productList = (ListView) dialog.findViewById(R.id.lv_product_list);

        clearTextFields();
        dialog.setCancelable(true);
        productList.clearTextFilter();

        //Log.d("SALES_RETURN_DETAILS", "PRIL_CODE_IS: " + activity.selectedRetDebtor.getFDEBTOR_PRILLCODE());

//        list = new ItemsDS(getActivity()).getAllItemForSalesReturn("","txntype ='21'",RefNo, new TourHedDS(getActivity()).getCurrentLocCodeFromTourHed(),activity.selectedRetDebtor.getFDEBTOR_PRILLCODE());
        //list = new ItemsDS(getActivity()).getAllItem("", "txntype ='21'", RefNo, new TourHedDS(getActivity()).getCurrentLocCodeFromTourHed());
        //list = new ItemsDS(getActivity()).getAllItem("", "txntype ='21'", RefNo, new SalRepDS(getActivity()).getCurrentLocCode());
        //list = new ItemsDS(getActivity()).getAllItemForSalesReturn("","txntype ='21'",RefNo, "LOC01","MWP");
        Log.v("Return Itms bfr adapter", ">>>>>"+list.size());
        productList.setAdapter(new ProductAdapter(getActivity(), list));
        Log.v("Return Itms aftr adptr", ">>>>>"+list.size());
        productList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = list.get(position);
                brandDisPer = new DebItemPriDS(getActivity()).getBrandDiscount(selectedItem.getFITEM_BRANDCODE(),
                        activity.selectedRetDebtor.getFDEBTOR_CODE());
                lblItemName.setText(selectedItem.getFITEM_ITEM_NAME());
                lblNou.setText(selectedItem.getFITEM_NOUCASE());
//
                if (returnType.getSelectedItem().toString().equalsIgnoreCase("FR"))
                {
                    lblPrice.setText("0.00");
                }
                else
                {
                    //lblPrice.setText("10000.00");
                    if(new ItemPriDS(getActivity()).getProductPriceByCode(selectedItem.getFITEM_ITEM_CODE(), activity.selectedRetDebtor.getFDEBTOR_PRILLCODE()).equals("") || new ItemPriDS(getActivity()).getProductPriceByCode(selectedItem.getFITEM_ITEM_CODE(), activity.selectedRetDebtor.getFDEBTOR_PRILLCODE()).equals(null)) {
                        price = 0.0;
                    }else{
                        price = Double.parseDouble(new ItemPriDS(getActivity()).getProductPriceByCode(selectedItem.getFITEM_ITEM_CODE(), activity.selectedRetDebtor.getFDEBTOR_PRILLCODE()));
                        changedPrice = price;
                    }
                        lblPrice.setText(""+price);
                }
                //lblPrice.setText(selectedItem.getFITEM_AVGPRICE());

                //lblPrice.setText("10000.00");
                iQoh = Double.parseDouble(selectedItem.getFITEM_QOH());
                txtQty.requestFocus();
                dialog.dismiss();

            }
        });

        search.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                list.clear();
                list = new ItemsDS(getActivity()).getAllItem(newText, "txntype ='21'", RefNo, new TourHedDS(getActivity()).getCurrentLocCodeFromTourHed());

                productList.clearTextFilter();
                productList.setAdapter(new ProductAdapter(getActivity(), list));

                return false;
            }
        });
        dialog.show();
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//    public void reasonsDialogbox() {
//
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.return_reason_item);
//        final ListView reasonListView = (ListView) dialog.findViewById(R.id.lv_reason_list);
//        dialog.setCancelable(true);
//        reasonListView.clearTextFilter();
//
//        reasonList = new ReasonDS(getActivity()).getAllReasonsByRtCode("RT02");
//
//        reasonListView.setAdapter(new ReturnReasonAdapter(getActivity(), reasonList));
//
//        reasonListView.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                selectedReason = reasonList.get(position);
//                //lblReason.setText(selectedReason.getFREASON_NAME());
//                lblItemName.requestFocus();
//                dialog.dismiss();
//
//            }
//        });
//
//        dialog.show();
//
//    }


}
