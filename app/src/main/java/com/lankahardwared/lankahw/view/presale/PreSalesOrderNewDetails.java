package com.lankahardwared.lankahw.view.presale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.FreeIssueAdapter;
import com.lankahardwared.lankahw.adapter.NewPreProductAdapter;
import com.lankahardwared.lankahw.adapter.OrderDetailsAdapter;
import com.lankahardwared.lankahw.adapter.PreSalesFreeItemAdapter;
import com.lankahardwared.lankahw.control.IResponseListener;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.ControlDS;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.FDDbNoteDS;
import com.lankahardwared.lankahw.data.FItTourDiscDs;
import com.lankahardwared.lankahw.data.ItemPriDS;
import com.lankahardwared.lankahw.data.ItemsDS;
import com.lankahardwared.lankahw.data.OrdFreeIssueDS;
import com.lankahardwared.lankahw.data.OrderDiscDS;
import com.lankahardwared.lankahw.data.PreProductDS;
import com.lankahardwared.lankahw.data.PreSaleTaxDTDS;
import com.lankahardwared.lankahw.data.PreSaleTaxRGDS;
import com.lankahardwared.lankahw.data.TaxDetDS;
import com.lankahardwared.lankahw.data.TranSODetDS;
import com.lankahardwared.lankahw.data.TranSOHedDS;
import com.lankahardwared.lankahw.model.FItTourDisc;
import com.lankahardwared.lankahw.model.FreeIssue;
import com.lankahardwared.lankahw.model.FreeItemDetails;
import com.lankahardwared.lankahw.model.ItemFreeIssue;
import com.lankahardwared.lankahw.model.OrdFreeIssue;
import com.lankahardwared.lankahw.model.PreProduct;
import com.lankahardwared.lankahw.model.TranSODet;
import com.lankahardwared.lankahw.model.TranSOHed;
import com.lankahardwared.lankahw.view.MainActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Dhanushika on 1/17/2018.
 */

public class PreSalesOrderNewDetails extends Fragment {
    private static final String TAG = "PreSalesOrderNewDetails";
    public View view;
    public SharedPref mSharedPref;
    private ListView lv_pre_order_detlv, lv_pre_Free;
    private ImageButton pre_Product_btn, pre_Discount_btn;
    private  String RefNo;
    public MainActivity mainActivity;
    int seqno = 0, index_id = 0;
    ArrayList<PreProduct> PreproductList = null, selectedItemList = null;
    ArrayList<TranSODet> orderList;
    SweetAlertDialog pDialog;
    private  MyReceiver r;
    private TranSOHed tmpsoHed=null;  //from re oder creation
    MainActivity activity;
    ArrayList<FItTourDisc>ftdList = null;
    IResponseListener listener;
    Activity mactivity;
    int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.sales_management_pre_sales_details_new, container, false);
        lv_pre_order_detlv = (ListView) view.findViewById(R.id.lvProducts_Pre);
        lv_pre_Free = (ListView) view.findViewById(R.id.lvFreeIssue_Pre);
        pre_Product_btn = (ImageButton) view.findViewById(R.id.btnPreSalesProduct);
        pre_Discount_btn = (ImageButton) view.findViewById(R.id.btnPreSalesDisc);
        seqno = 0;
        mSharedPref = new SharedPref(getActivity());
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.NumVal));
        mainActivity = (MainActivity) getActivity();
        tmpsoHed=new TranSOHed();
        mactivity = getActivity();

        mSharedPref.setGlobalVal("preKeyIsFreeClicked", "0");
        count = 0;
        //------------------------------------------------------------------------------------------------------------------------

        showData();

        pre_Product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              new LoardingPreProductFromDB().execute();
                mSharedPref.setGlobalVal("preKeyIsFreeClicked", "0");
                count = 0;
            }
        });
        pre_Product_btn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        pre_Product_btn.setBackground(getResources().getDrawable(R.drawable.product_down));
                    }
                    break;

                    case MotionEvent.ACTION_UP: {
                        pre_Product_btn.setBackground(getResources().getDrawable(R.drawable.product));
                    }
                    break;
                }
                return false;
            }
        });
//------------------------------------------------------------------------------------------------------------------------------------
        lv_pre_order_detlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new TranSODetDS(getActivity()).restFreeIssueData(RefNo);
                new OrdFreeIssueDS(getActivity()).ClearFreeIssues(RefNo);
                TranSODet tranSODet=orderList.get(position);
                //String price = new PreProductDS(getActivity()).getPriceByItemCode(tranSODet.getFTRANSODET_ITEMCODE());
                //tranSODet.setFTRANSODET_PRICE(price);
                mSharedPref.setGlobalVal("preKeyIsFreeClicked", "0");
                count = 0;
                FreeIssue issue = new FreeIssue(getActivity());
                ArrayList<FreeItemDetails> list=issue.getEligibleFreeItemsBySalesItem(orderList.get(position),new SharedPref(getActivity()).getGlobalVal("KeyCostCode"));
                popEditDialogBox(tranSODet,list);

            }
        });
        //---------------------------------------------------------------------------------------------------------------------------------
        lv_pre_order_detlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new TranSODetDS(getActivity()).restFreeIssueData(RefNo);
                new OrdFreeIssueDS(getActivity()).ClearFreeIssues(RefNo);
                mSharedPref.setGlobalVal("preKeyIsFreeClicked", "0");
                count = 0;
                newDeleteOrderDialog(position);
                return true;
            }
        });

        //----------------------------------------------discountFreeissue-------------------------------------------------------------------------

        pre_Discount_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        pre_Discount_btn.setBackground(getResources().getDrawable(R.drawable.discount_down));
                    }
                    break;

                    case MotionEvent.ACTION_UP: {
                        pre_Discount_btn.setBackground(getResources().getDrawable(R.drawable.discount));
                    }
                    break;
                }
                return false;
            }
        });

        //-----------------------------------------pre Discount calculation-----------------------------------------------------

        pre_Discount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                mSharedPref.setGlobalVal("preKeyIsFreeClicked", ""+count);
                if(mSharedPref.getGlobalVal("preKeyIsFreeClicked").equals("1")) {

                    calculateFreeIssue(mainActivity.selectedDebtor.getFDEBTOR_CODE());
                }else{
                    Toast.makeText(getActivity(),"Already clicked",Toast.LENGTH_LONG).show();
                    Log.v("Freeclick Count", mSharedPref.getGlobalVal("preKeyIsFreeClicked"));
                }
            }
        });

        return view;
    }


    //-------------------------------------- show pre product dialog----------------------------------------------------------------------

    public void PreProductDialogBox() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.product_dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final ListView lvProducts = (ListView) promptView.findViewById(R.id.lv_product_list);
        final SearchView search = (SearchView) promptView.findViewById(R.id.et_search);

        lvProducts.clearTextFilter();
        PreproductList = new PreProductDS(getActivity()).getAllItems("");
        lvProducts.setAdapter(new NewPreProductAdapter(getActivity(), PreproductList));

        alertDialogBuilder.setCancelable(false).setNegativeButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                selectedItemList = new PreProductDS(getActivity()).getSelectedItems();

                for (int i=0; i<selectedItemList.size(); i++)
                {
                    Log.d("SELECTED_ITEM_LIST", "SIZE_IS: " + selectedItemList.size() + ", PRICE " + selectedItemList.get(i).getPREPRODUCT_PRICE() + ", QTY" + selectedItemList.get(i).getPREPRODUCT_QTY());
                }

                updateSOeDet(selectedItemList);
                showData();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        final Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        positiveButton.setTextColor(getResources().getColor(R.color.counter_text_bg));
//        positiveButton.setBackground(getResources().getDrawable(R.drawable.custom_textbox));
        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
        positiveButton.setLayoutParams(positiveButtonLL);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                PreproductList = new PreProductDS(getActivity()).getAllItems(query);
                lvProducts.setAdapter(new NewPreProductAdapter(getActivity(), PreproductList));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                PreproductList.clear();
                PreproductList = new PreProductDS(getActivity()).getAllItems(newText);
                lvProducts.setAdapter(new NewPreProductAdapter(getActivity(), PreproductList));

                return false;
            }
        });

    }
    //------------------------------------------------------------------------------------------------------------------------------------------------
    public void updateSOeDet(final ArrayList<PreProduct> list) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Fetch Data Please Wait.");
                pDialog.setCancelable(false);
                pDialog.show();

                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {

                int i = 0;
                 new TranSODetDS(getActivity()).restData(RefNo);

                for (PreProduct product : list) {
                    Log.d("UPDATE_SOE_DET", "LIST_SIZE: " + list.size());
                    i++;
                    mUpdatePrsSales("0", product.getPREPRODUCT_ITEMCODE(), product.getPREPRODUCT_QTY(), product.getPREPRODUCT_PRICE(), i + "", product.getPREPRODUCT_QOH());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(pDialog.isShowing()){
                    pDialog.dismiss();
                }

                showData();
            }

        }.execute();
    }
    //---------------------------------------------Calculate Free isuee-------------------------------------------------------------------------------

    public void calculateFreeIssue(String debCode) {
        /* GET CURRENT ORDER DETAILS FROM TABLE */
        ArrayList<TranSODet> dets = new TranSODetDS(getActivity()).getSAForFreeIssueCalc(RefNo);
        /* CLEAR ORDERDET TABLE RECORD IF FREE ITEMS ARE ALREADY ADDED. */
        new TranSODetDS(getActivity()).restFreeIssueData(RefNo);
        /* Clear free issues in OrdFreeIss */
        new OrdFreeIssueDS(getActivity()).ClearFreeIssues(RefNo);

        FreeIssue issue = new FreeIssue(getActivity());

        /* Get discounts for assorted items */
        ArrayList<ArrayList<TranSODet>> metaOrdList = issue.SortDiscount(dets, debCode);

        Log.d("PRE_SALES_ORDER_DETAILS", "LIST_SIZE: " + metaOrdList.size());

        /* Iterate through for discounts for items */
        for (ArrayList<TranSODet> OrderList : metaOrdList) {

            double totAmt = 0;
            String discPer,discType,discRef;
            double freeVal = Double.parseDouble(OrderList.get(0).getFTRANSODET_BAMT());
            if(OrderList.get(0).getFTRANSODET_SCHDISPER() != null)
                discPer = OrderList.get(0).getFTRANSODET_SCHDISPER();
            else
                discPer = "";
            if(OrderList.get(0).getFTRANSODET_DISCTYPE() != null)
                discType = OrderList.get(0).getFTRANSODET_DISCTYPE();
            else
                discType = "";
            if(OrderList.get(0).getFTRANSODET_DISC_REF() != null)
                discRef = OrderList.get(0).getFTRANSODET_DISC_REF();
            else
                discRef = "";


            OrderList.get(0).setFTRANSODET_BAMT("0");

            for (TranSODet det : OrderList)
                totAmt += Double.parseDouble(det.getFTRANSODET_BSELLPRICE()) * (Double.parseDouble(det.getFTRANSODET_QTY()));
            // commented cue to getFTRANSODET_PRICE() is not set
            //totAmt += Double.parseDouble(det.getFTRANSODET_PRICE()) * (Double.parseDouble(det.getFTRANSODET_QTY()));

            for (TranSODet det : OrderList) {
                det.setFTRANSODET_SCHDISPER(discPer);
                det.setFTRANSODET_DISCTYPE(discType);
                det.setFTRANSODET_DISC_REF(discRef);

                double disc;
                /*
                 * For value, calculate amount portion & for percentage ,
                 * calculate percentage portion
                 */
                disc = (freeVal / totAmt) * Double.parseDouble(det.getFTRANSODET_BSELLPRICE()) * (Double.parseDouble(det.getFTRANSODET_QTY()));

                //commented due to
                // disc = (Double.parseDouble(det.getFTRANSODET_AMT()) / 100) * disc not correct

                /* Calculate discount amount from disc percentage portion */
//					if (discType != null)
//                    {
//                        if (discType.equals("P"))
////                            disc = (Double.parseDouble(det.getFTRANSODET_AMT()) / 100) * disc;
//                            disc = (Double.parseDouble(det.getFTRANSODET_AMT()) / 100);
//                    }

                new TranSODetDS(getActivity()).updateDiscount(det, disc, det.getFTRANSODET_DISCTYPE());
            }
        }

        // GET ARRAY OF FREE ITEMS BY PASSING IN ORDER DETAILS
        //ArrayList<FreeItemDetails> list = issue.getFreeItemsBySalesItem(dets);
        ArrayList<FreeItemDetails> list = issue.getFreeItemsBySalesItem(dets, "");
        //ArrayList<FreeItemDetails> list = issue.getFreeItemsBySalesItem(dets, new SharedPref(getActivity()).getGlobalVal("preKeyCostCode"));
        // PASS EACH ITEM IN TO DIALOG BOX FOR USER SELECTION
 //       if(count == 1) {
           // Log.v("Click count before loop", ">>>"+count);
            for (FreeItemDetails freeItemDetails : list) {
                freeIssueDialogBox(freeItemDetails);

            }
//            Log.v("Click count after loop", ">>>"+count);
//        }

    }
    //------------------------------------------------------------show ---------------Free issue Dailog box--------------------------------------------------------------------------

    private boolean freeIssueDialogBox(final FreeItemDetails itemDetails) {

        final ArrayList<ItemFreeIssue> itemFreeIssues;
        final String FIRefNo = itemDetails.getRefno();
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.free_issues_items_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Free Issue Schemes");
        alertDialogBuilder.setView(promptView);

        final ListView listView = (ListView) promptView.findViewById(R.id.lv_free_issue);
        final TextView itemName = (TextView) promptView.findViewById(R.id.tv_free_issue_item_name);
        final TextView freeQty = (TextView) promptView.findViewById(R.id.tv_free_qty);

        freeQty.setText("Free Quantity : " + itemDetails.getFreeQty());
        itemName.setText("Product : " + new ItemsDS(getActivity()).getItemNameByCode(itemDetails.getFreeIssueSelectedItem()));

        final ItemsDS itemsDS = new ItemsDS(getActivity());
        itemFreeIssues = itemsDS.getAllFreeItemNameByRefno(itemDetails.getRefno());
        listView.setAdapter(new FreeIssueAdapter(getActivity(), itemFreeIssues));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            int avaliableQty = 0;

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, final int position, long id) {

                if (itemDetails.getFreeQty() > 0) {

                    ItemFreeIssue freeIssue = itemFreeIssues.get(position);
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

                    View promptView = layoutInflater.inflate(R.layout.set_free_issue_dialog, null);

                    final TextView leftQty = (TextView) promptView.findViewById(R.id.tv_free_item_left_qty);
                    final EditText enteredQty = (EditText) promptView.findViewById(R.id.et_free_qty);

                    leftQty.setText("Free Items Left = " + itemDetails.getFreeQty());

                    enteredQty.addTextChangedListener(new TextWatcher() {
                        public void afterTextChanged(Editable s) {

                            if (enteredQty.getText().toString().equals("")) {

                                leftQty.setText("Free Items Left = " + itemDetails.getFreeQty());

                            } else {
                                avaliableQty = itemDetails.getFreeQty() - Integer.parseInt(enteredQty.getText().toString());

                                if (avaliableQty < 0) {
                                    enteredQty.setText("");
                                    leftQty.setText("Free Items Left = " + itemDetails.getFreeQty());
                                    Toast.makeText(getActivity(), "You don't have enough sufficient quantity to order", Toast.LENGTH_SHORT).show();
                                } else {
                                    leftQty.setText("Free Items Left = " + avaliableQty);
                                }
                            }
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                    });

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle(freeIssue.getItems().getFITEM_ITEM_NAME());
                    alertDialogBuilder.setView(promptView);

                    alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            itemDetails.setFreeQty(avaliableQty);
                            freeQty.setText("Free Qty you have : " + itemDetails.getFreeQty());

                            itemFreeIssues.get(position).setAlloc(enteredQty.getText().toString());
                            listView.clearTextFilter();
                            listView.setAdapter(new FreeIssueAdapter(getActivity(), itemFreeIssues));
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertD = alertDialogBuilder.create();
                    alertD.show();
                } else {
                    Toast.makeText(getActivity(), "You don't have enough sufficient quantity to order", Toast.LENGTH_SHORT).show();
                }

            }
        });

        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                for (ItemFreeIssue itemFreeIssue : itemFreeIssues) {

                    if (Integer.parseInt(itemFreeIssue.getAlloc()) > 0) {

                        seqno++;
                        TranSODet ordDet = new TranSODet();
                        TranSODetDS detDS = new TranSODetDS(getActivity());
                        ArrayList<TranSODet> ordList = new ArrayList<TranSODet>();

                        ordDet.setFTRANSODET_ID("0");
                        ordDet.setFTRANSODET_AMT("0");
                        ordDet.setFTRANSODET_BALQTY(itemFreeIssue.getAlloc());
                        ordDet.setFTRANSODET_BAMT("0");
                        ordDet.setFTRANSODET_BDISAMT("0");
                        ordDet.setFTRANSODET_BPDISAMT("0");
                        String unitPrice = new ItemPriDS(getActivity()).getProductPriceByCode(itemFreeIssue.getItems().getFITEM_ITEM_CODE(), mainActivity.selectedDebtor.getFDEBTOR_PRILLCODE());
                        ordDet.setFTRANSODET_BSELLPRICE("0");
                        ordDet.setFTRANSODET_BTSELLPRICE("0.00");
                        ordDet.setFTRANSODET_DISAMT("0");
                        ordDet.setFTRANSODET_SCHDISPER("0");
                        ordDet.setFTRANSODET_FREEQTY("0");
                        ordDet.setFTRANSODET_ITEMCODE(itemFreeIssue.getItems().getFITEM_ITEM_CODE());
                        ordDet.setFTRANSODET_PDISAMT("0");
                        ordDet.setFTRANSODET_PRILCODE(mainActivity.selectedDebtor.getFDEBTOR_PRILLCODE());
                        ordDet.setFTRANSODET_QTY(itemFreeIssue.getAlloc());
                        ordDet.setFTRANSODET_PICE_QTY(itemFreeIssue.getAlloc());
                        ordDet.setFTRANSODET_TYPE("FI");
                        ordDet.setFTRANSODET_RECORDID("");
                        ordDet.setFTRANSODET_REFNO(RefNo);
                        ordDet.setFTRANSODET_SELLPRICE("0");
                        ordDet.setFTRANSODET_SEQNO(seqno + "");
                        ordDet.setFTRANSODET_TAXAMT("0");
                        ordDet.setFTRANSODET_TAXCOMCODE(new ItemsDS(getActivity()).getTaxComCodeByItemCode(itemFreeIssue.getItems().getFITEM_ITEM_CODE()));
                        //ordDet.setFTRANSODET_TAXCOMCODE(new ItemsDS(getActivity()).getTaxComCodeByItemCodeFromDebTax(itemFreeIssue.getItems().getFITEM_ITEM_CODE(), mainActivity.selectedDebtor.getFDEBTOR_CODE()));
                        ordDet.setFTRANSODET_TIMESTAMP_COLUMN("");
                        ordDet.setFTRANSODET_TSELLPRICE("0.00");
                        ordDet.setFTRANSODET_TXNDATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        ordDet.setFTRANSODET_TXNTYPE("27");
                        ordDet.setFTRANSODET_IS_ACTIVE("1");
                        ordDet.setFTRANSODET_LOCCODE(mSharedPref.getGlobalVal("PrekeyLocCode").trim());
                        ordDet.setFTRANSODET_COSTPRICE(new ItemsDS(getActivity()).getCostPriceItemCode(itemFreeIssue.getItems().getFITEM_ITEM_CODE()));
                        ordDet.setFTRANSODET_BTAXAMT("0");
                        ordDet.setFTRANSODET_IS_SYNCED("0");
                        ordDet.setFTRANSODET_QOH("0");
                        ordDet.setFTRANSODET_SCHDISC("0");
                        ordDet.setFTRANSODET_BRAND_DISC("0");
                        ordDet.setFTRANSODET_BRAND_DISPER("0");
                        ordDet.setFTRANSODET_COMP_DISC("0");
                        ordDet.setFTRANSODET_COMP_DISPER("0");
                        ordDet.setFTRANSODET_DISCTYPE("");
                        ordDet.setFTRANSODET_PRICE("0.00");
                        ordDet.setFTRANSODET_ORG_PRICE("0");
                        ordDet.setFTRANSODET_DISFLAG("0");

						/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*OrdFreeIssue table update*-*-*-*-*-*-*-*-*-*-*-*-*-*/

                        OrdFreeIssue ordFreeIssue = new OrdFreeIssue();
                        ordFreeIssue.setOrdFreeIssue_ItemCode(itemFreeIssue.getItems().getFITEM_ITEM_CODE());
                        ordFreeIssue.setOrdFreeIssue_Qty(itemFreeIssue.getAlloc());
                        ordFreeIssue.setOrdFreeIssue_RefNo(FIRefNo);
                        ordFreeIssue.setOrdFreeIssue_RefNo1(RefNo);
                        ordFreeIssue.setOrdFreeIssue_TxnDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        new OrdFreeIssueDS(getActivity()).UpdateOrderFreeIssue(ordFreeIssue);

						/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*/

                        ordList.add(ordDet);

                        if (detDS.createOrUpdateSODet(ordList) > 0) {
                            Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_SHORT).show();
                            showData();
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
        return true;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    public void showData() {
        try {
            lv_pre_order_detlv.setAdapter(null);
            orderList = new TranSODetDS(getActivity()).getAllOrderDetails(RefNo);

//            for (int i=0; i<orderList.size(); i++)
//            {
//                Log.d("SHOW_DATA", "ORDER_LIST_DETAILS " + orderList.size() +", AMT" + orderList.get(i).getFTRANSODET_AMT()
//                        + ", BTAXAMT" + orderList.get(i).getFTRANSODET_BTAXAMT()
//                        + ", TAXAMT" + orderList.get(i).getFTRANSODET_TAXAMT());
//            }
            ArrayList<TranSODet> freeList=new TranSODetDS(getActivity()).getAllFreeIssue(RefNo);

            lv_pre_order_detlv.setAdapter(new OrderDetailsAdapter(getActivity(), orderList, mainActivity.selectedSOHed.getFTRANSOHED_DEBCODE()));

            lv_pre_Free.setAdapter(new PreSalesFreeItemAdapter(getActivity(), freeList));


            if (mainActivity.selectedSOHed == null) {
                TranSOHed SOHed = new TranSOHedDS(getActivity()).getActiveSOHed();
                if (SOHed != null) {
                    if (mainActivity.selectedDebtor == null)
                        mainActivity.selectedDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(SOHed.getFTRANSOHED_DEBCODE());

                    creditValidationPreSales(lv_pre_order_detlv);
                }
            }
            else
            {
                creditValidationPreSales(lv_pre_order_detlv);
            }

        } catch (NullPointerException e) {
          e.printStackTrace();
        }
    }
//------------------------------------------------------update TranSODet tbl------------------------------------------------------------------------------------

    public void mUpdatePrsSales(String id, String itemCode, String Qty, String price, String seqno, String qoh) {
        TranSODet SODet = new TranSODet();
        ArrayList<TranSODet> SOList = new ArrayList<TranSODet>();

        String prilCode = mainActivity.selectedDebtor.getFDEBTOR_PRILLCODE();
        // FITTourDisc is not a discount for an item. It is the selling price of an item.

        ftdList = new FItTourDiscDs(getActivity()).getQtyBulkPrice(itemCode, prilCode);

        double oneItemPrice = 0;
        int listSize = ftdList.size();
        double fromQty = 0;
        double toQty = 0;
        double orderQty  = Double.parseDouble(Qty);

        for (int i=0; i<ftdList.size(); i++)
        {
            fromQty = Double.parseDouble(ftdList.get(i).getFITTOURDISC_QTYFROM());
            toQty = Double.parseDouble(ftdList.get(i).getFITTOURDISC_QTYTO());
            if (orderQty>=fromQty && orderQty<=toQty)
            {
                oneItemPrice = Double.parseDouble(ftdList.get(i).getFITTOURDISC_PRILDISC());

            }
            else if (orderQty>toQty)
            {
                oneItemPrice = Double.parseDouble(ftdList.get(listSize-1).getFITTOURDISC_PRILDISC());
            }
        }

        // ----------------------------------- Nuwan ------ 14/09/2018 ----------------- due to tax reverse unit price before calculate the discount

        String reverseUnitPrice;
        double tax;
        double taxDe =0.0;
        double amt = 0.0;
        if (listSize==0) // if tourdisc is null should use the price
        {
//            tax = Double.parseDouble(new TaxDetDS(getActivity()).calculateReverseTaxFromDebTax(mainActivity.selectedDebtor.getFDEBTOR_CODE(),itemCode, new BigDecimal(price)));
//            taxDe = Math.floor(tax * 100) / 100;
            reverseUnitPrice = new TaxDetDS(getActivity()).calculateReverseTaxFromDebTax(mainActivity.selectedDebtor.getFDEBTOR_CODE(),itemCode, new BigDecimal(price));
            //reverseUnitPrice = String.valueOf(Double.parseDouble(price) - taxDe);
            //oneItemPrice = Double.parseDouble(price);
            //amt = Double.parseDouble(price) * Double.parseDouble(Qty);
        }
        else
        {
//            tax = Double.parseDouble(new TaxDetDS(getActivity()).calculateReverseTaxFromDebTax(mainActivity.selectedDebtor.getFDEBTOR_CODE(),itemCode, new BigDecimal(oneItemPrice)));
//            taxDe = Math.floor(tax * 100) / 100;
//            reverseUnitPrice = String.valueOf(Double.parseDouble(price) - Double.parseDouble(new TaxDetDS(getActivity()).calculateReverseTaxFromDebTax(mainActivity.selectedDebtor.getFDEBTOR_CODE(),itemCode, new BigDecimal(price))));
            //reverseUnitPrice = String.valueOf(Double.parseDouble(price) - taxDe);
            reverseUnitPrice = new TaxDetDS(getActivity()).calculateReverseTaxFromDebTax(mainActivity.selectedDebtor.getFDEBTOR_CODE(),itemCode, new BigDecimal(oneItemPrice));
            //amt = oneItemPrice * Double.parseDouble(Qty);
        }

        amt = Double.parseDouble(reverseUnitPrice) * Double.parseDouble(Qty);

        // ------------------------------------------------------------------------------------------------------------------------------------------


        //String oneItemAmt = new TaxDetDS(getActivity()).calculateReverseTaxFromDebTax(mainActivity.selectedDebtor.getFDEBTOR_CODE(),itemCode, new BigDecimal(price));


        //String oneItemTaxAmt = new TaxDetDS(getActivity()).calculateTax(itemCode, new BigDecimal(price));

        // commented due to Tour Discount calculation
        //double totalDiscountPerItem = oneItemDisc * orderQty;
        //Log.d("TOUR_DISCOUNT" , "SINGLE_ITEM_DISCOUNT_IS: " + oneItemDisc + ", TOTAL_DISCOUNT_IS: " + totalDiscountPerItem);
        //double amt = Double.parseDouble(price) * Double.parseDouble(Qty) - totalDiscountPerItem;

        // commented due to if TourDisc list in null
        //double amt = oneItemPrice * Double.parseDouble(Qty);

        // commented due to tax exclude to amt for different TaxComCode
        //String TaxedAmt = new TaxDetDS(getActivity()).calculateTaxFromDebTax(mainActivity.selectedDebtor.getFDEBTOR_CODE(),itemCode, new BigDecimal(amt));
        //String TaxedAmt = new TaxDetDS(getActivity()).calculateTax(itemCode, new BigDecimal(amt));


        String TaxedAmt = "0.00";
        SODet.setFTRANSODET_ID(id + "");
        //SODet.setFTRANSODET_AMT(String.format("%.2f", amt - Double.parseDouble(TaxedAmt)));

        SODet.setFTRANSODET_AMT(String.valueOf(amt));
        //SODet.setFTRANSODET_AMT(String.format("%.2f", amt ));

        SODet.setFTRANSODET_BALQTY(Qty);
        SODet.setFTRANSODET_QTY(Qty);
        //SODet.setFTRANSODET_BAMT(String.format("%.2f", amt - Double.parseDouble(TaxedAmt)));

//        SODet.setFTRANSODET_BAMT(String.format("%.2f", amt ));
        SODet.setFTRANSODET_BAMT(String.valueOf(amt));

        SODet.setFTRANSODET_BDISAMT("0");
        SODet.setFTRANSODET_BPDISAMT("0");
        //SODet.setFTRANSODET_BTAXAMT("0");
        SODet.setFTRANSODET_BTAXAMT(TaxedAmt);
        SODet.setFTRANSODET_TAXAMT(TaxedAmt);
        SODet.setFTRANSODET_DISAMT("0");
        SODet.setFTRANSODET_SCHDISPER("0");
        SODet.setFTRANSODET_COMP_DISPER(new ControlDS(getActivity()).getCompanyDisc() + "");
       /* SODet.setFTRANSODET_BRAND_DISPER(brandDisPer + "");
        SODet.setFTRANSODET_BRAND_DISC(String.format("%.2f", brandDis));
        SODet.setFTRANSODET_COMP_DISC(String.format("%.2f", CompDis));*/
        SODet.setFTRANSODET_BRAND_DISPER("0");
        SODet.setFTRANSODET_BRAND_DISC("0");
        SODet.setFTRANSODET_COMP_DISC("0");
        SODet.setFTRANSODET_COSTPRICE(new ItemsDS(getActivity()).getCostPriceItemCode(itemCode));
        SODet.setFTRANSODET_ITEMCODE(itemCode);
        SODet.setFTRANSODET_PRILCODE(mainActivity.selectedDebtor.getFDEBTOR_PRILLCODE());
        SODet.setFTRANSODET_PICE_QTY(Qty);
        SODet.setFTRANSODET_REFNO(RefNo);
        //SODet.setFTRANSODET_SELLPRICE(String.format("%.2f", (amt - Double.parseDouble(TaxedAmt)) / Double.parseDouble(SODet.getFTRANSODET_QTY())));


//        SODet.setFTRANSODET_SELLPRICE(String.format("%.2f", (amt ) / Double.parseDouble(SODet.getFTRANSODET_QTY())));
//        SODet.setFTRANSODET_BSELLPRICE(String.format("%.2f", (amt ) / Double.parseDouble(SODet.getFTRANSODET_QTY())));
        SODet.setFTRANSODET_SELLPRICE(String.valueOf((amt ) / Double.parseDouble(SODet.getFTRANSODET_QTY())));
        SODet.setFTRANSODET_BSELLPRICE(String.valueOf((amt ) / Double.parseDouble(SODet.getFTRANSODET_QTY())));



        //SODet.setFTRANSODET_BSELLPRICE(String.format("%.2f", (amt - Double.parseDouble(TaxedAmt)) / Double.parseDouble(SODet.getFTRANSODET_QTY())));
        SODet.setFTRANSODET_SEQNO(new TranSODetDS(getActivity()).getLastSequnenceNo(RefNo));

        String taxcode = "";
        //SODet.setFTRANSODET_TAXCOMCODE(new ItemsDS(getActivity()).getTaxComCodeByItemCode(itemCode));
if(new ItemsDS(getActivity()).checkTaxCodeOnItemTbl(itemCode)> 0){//get taxcomcode from item table
   taxcode = new ItemsDS(getActivity()).getTaxCodeFromItem(itemCode);
    SODet.setFTRANSODET_TAXCOMCODE(taxcode);
}else{//get taxcomcode from debtax table
    taxcode = new ItemsDS(getActivity()).getTaxCodeFromDebtax(mainActivity.selectedDebtor.getFDEBTOR_CODE());
    SODet.setFTRANSODET_TAXCOMCODE(taxcode);
}

        //SODet.setFTRANSODET_BTSELLPRICE(String.format("%.2f", (amt + Double.parseDouble(TaxedAmt)) / Double.parseDouble(SODet.getFTRANSODET_QTY())));
        SODet.setFTRANSODET_BTSELLPRICE(String.valueOf((amt + Double.parseDouble(TaxedAmt)) / Double.parseDouble(SODet.getFTRANSODET_QTY())));

        //SODet.setFTRANSODET_TSELLPRICE(String.format("%.2f", (amt + Double.parseDouble(TaxedAmt)) / Double.parseDouble(SODet.getFTRANSODET_QTY())));
        SODet.setFTRANSODET_TSELLPRICE(String.valueOf((amt + Double.parseDouble(TaxedAmt)) / Double.parseDouble(SODet.getFTRANSODET_QTY())));

        SODet.setFTRANSODET_TXNTYPE("21");
//        SODet.setFTRANSODET_TXNTYPE("22");
        SODet.setFTRANSODET_LOCCODE(mSharedPref.getGlobalVal("PrekeyLoc_Code").trim());
        //SODet.setFTRANSODET_LOCCODE(new SalRepDS(getActivity()).getCurrentLocCode());
        SODet.setFTRANSODET_TXNDATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        SODet.setFTRANSODET_IS_ACTIVE("1");
        SODet.setFTRANSODET_RECORDID("");
        SODet.setFTRANSODET_PDISAMT("0");
        SODet.setFTRANSODET_IS_SYNCED("0");
        SODet.setFTRANSODET_QOH(qoh);
        SODet.setFTRANSODET_TYPE("SA");
        SODet.setFTRANSODET_SCHDISC("0");
        //SODet.setFTRANSODET_PRICE(price);
        SODet.setFTRANSODET_PRICE("0.0");
        SODet.setFTRANSODET_DISCTYPE("");
        SODet.setFTRANSODET_SEQNO(seqno + "");
        //SODet.setFTRANSODET_QTY_SLAB_DISC(String.format("%.2f",totalDiscountPerItem ));
        SODet.setFTRANSODET_QTY_SLAB_DISC("0");
        SODet.setFTRANSODET_ORG_PRICE(price);
        SODet.setFTRANSODET_DISFLAG("0");
        SOList.add(SODet);

        Log.d("PRE_SALES_DETAILS", "TAX_AMT: " + TaxedAmt);
        new TranSODetDS(getActivity()).createOrUpdateSODet(SOList);


    }

    //----------------------------------------show popup dialog------------------------------------------------------------------------

    public void popEditDialogBox(final TranSODet tranSODet, ArrayList<FreeItemDetails>itemDetailsArrayList) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Enter Quantity");
        alertDialogBuilder.setView(promptView);

        final EditText txtInputBox = (EditText) promptView.findViewById(R.id.txtInputBox);
        final TextView lblQoh = (TextView) promptView.findViewById(R.id.lblQOH);

        final TextView itemName = (TextView) promptView.findViewById(R.id.tv_free_issue_item_name);
        final TextView freeQty = (TextView) promptView.findViewById(R.id.tv_free_qty);

        lblQoh.setText(tranSODet.getFTRANSODET_QOH());
        txtInputBox.setText(tranSODet.getFTRANSODET_QTY());
        txtInputBox.selectAll();

        if(itemDetailsArrayList==null){
            freeQty.setVisibility(View.GONE);
            itemName.setVisibility(View.GONE);
        }else{
            for(FreeItemDetails itemDetails :itemDetailsArrayList){
                freeQty.setText("Free Quantity : " + itemDetails.getFreeQty());
                itemName.setText("Product : " + new ItemsDS(getActivity()).getItemNameByCode(itemDetails.getFreeIssueSelectedItem()));
            }
        }


        txtInputBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (txtInputBox.length() > 0) {
                    int enteredQty = Integer.parseInt(txtInputBox.getText().toString());
                    lblQoh.setText((int) Double.parseDouble(tranSODet.getFTRANSODET_QOH()) - enteredQty + "");
                 /*   int enteredQty = Integer.parseInt(txtInputBox.getText().toString());

                    if (enteredQty > Double.parseDouble(tranSODet.getFTRANSODET_QOH())) {
                        Toast.makeText(getActivity(), "Quantity exceeds QOH !", Toast.LENGTH_SHORT).show();
                        txtInputBox.setText("0");
                        txtInputBox.selectAll();
                    } else
                        ;*/
                } else {
                    txtInputBox.setText("0");
                    txtInputBox.selectAll();
                }
            }
        });

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                if (Integer.parseInt(txtInputBox.getText().toString()) > 0) {
                    new PreProductDS(getActivity()).updateProductQty(tranSODet.getFTRANSODET_ITEMCODE(), txtInputBox.getText().toString());
                    mUpdatePrsSales(tranSODet.getFTRANSODET_ID(), tranSODet.getFTRANSODET_ITEMCODE(), txtInputBox.getText().toString(), tranSODet.getFTRANSODET_ORG_PRICE(), tranSODet.getFTRANSODET_SEQNO(), tranSODet.getFTRANSODET_QOH());

                } else
                    Toast.makeText(getActivity(), "Enter Qty above Zero !", Toast.LENGTH_SHORT).show();

                showData();
            }

        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

    }

    //-------------------------------------------------------------------------------------------------------------------------
    public void newDeleteOrderDialog(final int position) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Confirm Deletion !");
        alertDialogBuilder.setMessage("Do you want to delete this item ?");
        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                new PreProductDS(getActivity()).updateProductQty(orderList.get(position).getFTRANSODET_ITEMCODE(), "0");

                int count = new TranSODetDS(getActivity()).deleteOrdDetItemCode(orderList.get(position).getFTRANSODET_ITEMCODE(), orderList.get(position).getFTRANSODET_TYPE());
                if (count > 0) {

                  //  new TranSODetDS(getActivity()).restFreeIssueData(RefNo);
                    //#
                    /*new OrdFreeIssueDS(getActivity()).ClearFreeIssues(RefNo);
                    new OrderDiscDS(getActivity()).RemoveOrderDiscRecord(RefNo, itemCode);*/

                    Toast.makeText(getActivity(), "Deleted successfully", Toast.LENGTH_LONG).show();

                    showData();
                }
                Toast.makeText(getActivity(), "Deleted successfully!", Toast.LENGTH_SHORT).show();


            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();


    }


//---------------------------------LoardingPreProductFromDB----------------------------------------------------------------------------------------
    public class LoardingPreProductFromDB extends AsyncTask<Object, Object, ArrayList<PreProduct>> {
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
        protected ArrayList<PreProduct> doInBackground(Object... objects) {

            if (new PreProductDS(getActivity()).tableHasRecords()) {
                PreproductList = new PreProductDS(getActivity()).getAllItems("");

                for (int i=0; i<PreproductList.size(); i++)
                {
                    if (PreproductList.get(i).getPREPRODUCT_ITEMCODE().equalsIgnoreCase("89LKK"))
                    {
                        Log.d("SEARCH_PRODUCT_BUTTON", "PRICE_IN_BACKGROUND_OLD: " + PreproductList.get(i).getPREPRODUCT_PRICE());
                    }
                }

            } else {
                Log.d("PRESLAES_ORDER", "TOUR_LOC_CODE_IS: " + mSharedPref.getGlobalVal("PrekeyLocCode").trim() + ", " + mainActivity.selectedDebtor.getFDEBTOR_PRILLCODE());
//                PreproductList =new ItemsDS(getActivity()).getAllItemForPreSales("","txntype ='21'",RefNo, new TourHedDS(getActivity()).getCurrentLocCodeFromTourHed(),mainActivity.selectedDebtor.getFDEBTOR_PRILLCODE());
                  PreproductList =new ItemsDS(getActivity()).getAllItemForPreSales("","txntype ='21'",RefNo, mSharedPref.getGlobalVal("PrekeyLoc_Code").trim(),mainActivity.selectedDebtor.getFDEBTOR_PRILLCODE());
                 // PreproductList =new ItemsDS(getActivity()).getAllItemForPreSales("","txntype ='21'",RefNo, mSharedPref.getGlobalVal("PrekeyLocCode").trim(),mainActivity.selectedDebtor.getFDEBTOR_PRILLCODE());
                new PreProductDS(getActivity()).insertOrUpdateProducts(PreproductList);
                //---------re Order Temp product  list added for  fProducts_pre table-----------------dhanushika-------------------------------
                if(tmpsoHed!=null) {
                    ArrayList<TranSODet> detArrayList = tmpsoHed.getSoDetArrayList();
                    if (detArrayList != null) {
                        for (int i = 0; i < detArrayList.size(); i++) {
                            String tmpItemName = detArrayList.get(i).getFTRANSODET_ITEMCODE();
                            String tmpQty = detArrayList.get(i).getFTRANSODET_QTY();
                            //Update Qty in  fProducts_pre table
                            int count = new PreProductDS(getActivity()).updateProductQtyFor(tmpItemName, tmpQty);
                            if (count > 0) {

                               // Log.d("InsertOrUpdate", "success");
                            } else {
                               // Log.d("InsertOrUpdate", "Failed");
                            }

                        }
                    }
                }

                for (int i=0; i<PreproductList.size(); i++)
                {
                    if (PreproductList.get(i).getPREPRODUCT_ITEMCODE().equalsIgnoreCase("89LKK"))
                    {
                        Log.d("SEARCH_PRODUCT_BUTTON", "PRICE_IN_BACKGROUND_NEW: " + PreproductList.get(i).getPREPRODUCT_PRICE());
                    }
                }
                //----------------------------------------------------------------------------

            }
            return PreproductList;
        }


        @Override
        protected void onPostExecute(ArrayList<PreProduct> preProducts) {
            super.onPostExecute(preProducts);

            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
            PreProductDialogBox();

        }
    }
//------------------------------------------------------------------------------------------------------------------------

    @Override
    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_DETAILS"));
    }
    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }



    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            PreSalesOrderNewDetails.this.mToggleTextbox();
        }
    }

    public void mToggleTextbox() {

        if (mSharedPref.getGlobalVal("PrekeyCustomer").equals("Y")) {

            pre_Discount_btn.setEnabled(true);
            pre_Product_btn.setEnabled(true);

            // from PreSalesAdapter----- for re oder creation
            Bundle mBundle = getArguments();
            if (mBundle != null) {
                tmpsoHed = (TranSOHed) mBundle.getSerializable("order");
            }
            showData();

            selectedItemList = new PreProductDS(getActivity()).getSelectedItems();
            if(selectedItemList !=null &&  !selectedItemList.isEmpty()){
                updateSOeDet(selectedItemList);
            }

        } else {
            Toast.makeText(getActivity(), "Select a customer to continue...", Toast.LENGTH_SHORT).show();
            pre_Discount_btn.setEnabled(false);
            pre_Product_btn.setEnabled(false);
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------


    // Nuwan ----------------------------------------------------------------------

    private void creditValidationPreSales(ListView orderDetailListView)
    {
        double total = 0.0;
        View view = null;
        TextView amount;

        for (int i=0; i<orderDetailListView.getAdapter().getCount(); i++)
        {
            view = orderDetailListView.getAdapter().getView(i, view, orderDetailListView);
            amount = (TextView)view.findViewById(R.id.row_piece);
            total += Double.parseDouble(amount.getText().toString().trim());
        }

        Log.d(TAG, "TOTAL_IS: " + total);

        Double outstanding  = new FDDbNoteDS(getActivity()).getDebtorBalance(mainActivity.selectedDebtor.getFDEBTOR_CODE());
        Double creditLimit = Double.parseDouble(mainActivity.selectedDebtor.getFDEBTOR_CRD_LIMIT());
        String creditFlag = mainActivity.selectedDebtor.getFDEBTOR_CHK_CRD_LIMIT();

        Double availableCredit = creditLimit - outstanding;


        if (creditFlag.equals("Y"))
        {
            if (creditLimit>0.00)
            {
                if (total>availableCredit)
                {
                    Log.d(TAG, "Exceed the credit limit" + total);
                    creditLimitExceedDialog(String.valueOf(availableCredit));
                }
                else
                {
                    Log.d(TAG, "Credit limit OK" + total);
                }
            }
        }
    }

    public void creditLimitExceedDialog(String crLimit) {

        String message = "Customer available credit Rs. "+ crLimit + " that exceed by this sales order. Do you want to remove items from this sales order?";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Over Credit Limit");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                new LoardingPreProductFromDB().execute();

            }
        }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                MainActivity activity = (MainActivity) getActivity();
                boolean result = new TranSOHedDS(getActivity()).restData(RefNo);

                if (result) {
                    new TranSODetDS(getActivity()).restData(RefNo);
                    new PreProductDS(getActivity()).mClearTables();
                    new OrderDiscDS(getActivity()).clearData(RefNo);
                    new OrdFreeIssueDS(getActivity()).ClearFreeIssues(RefNo);
                    new PreSaleTaxDTDS(getActivity()).ClearTable(RefNo);
                    new PreSaleTaxRGDS(getActivity()).ClearTable(RefNo);
                }
                activity.cusPosition = 0;
                activity.selectedDebtor = null;
                activity.selectedSOHed = null;
                Toast.makeText(getActivity(), "Order discarded successfully..!", Toast.LENGTH_LONG).show();
                UtilityContainer.PreClearSharedPref(getActivity());
                UtilityContainer.mLoadFragment(new PreSalesInvoice(), activity);
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

    }

    @Override
    public void onAttach(Activity activity) {
        this.mactivity = activity;
        super.onAttach(mactivity);
        try {
            listener = (IResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }


}
