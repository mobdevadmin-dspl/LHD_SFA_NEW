package com.lankahardwared.lankahw.view.presale;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.DiscountAdapter;
import com.lankahardwared.lankahw.adapter.FreeIssueAdapter;
import com.lankahardwared.lankahw.adapter.OrderDetailsAdapter;
import com.lankahardwared.lankahw.adapter.ProductAdapter;
import com.lankahardwared.lankahw.control.DefaultTaskListener;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.ControlDS;
import com.lankahardwared.lankahw.data.DebItemPriDS;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.ItemPriDS;
import com.lankahardwared.lankahw.data.ItemsDS;
import com.lankahardwared.lankahw.data.OrdFreeIssueDS;
import com.lankahardwared.lankahw.data.OrderDiscDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.data.SharedPreferencesClass;
import com.lankahardwared.lankahw.data.TaxDetDS;
import com.lankahardwared.lankahw.data.TranSODetDS;
import com.lankahardwared.lankahw.data.TranSOHedDS;
import com.lankahardwared.lankahw.model.FreeIssue;
import com.lankahardwared.lankahw.model.FreeItemDetails;
import com.lankahardwared.lankahw.model.ItemFreeIssue;
import com.lankahardwared.lankahw.model.Items;
import com.lankahardwared.lankahw.model.OrdFreeIssue;
import com.lankahardwared.lankahw.model.TranSODet;
import com.lankahardwared.lankahw.model.TranSOHed;
import com.lankahardwared.lankahw.view.MainActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PreSalesOrderDetails extends Fragment implements OnClickListener, DefaultTaskListener {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    public String RefNo = "";
    View view;
    Button itemSearch, bAdd, bFreeIssue;
    EditText lblItemName, txtQty;
    int totPieces = 0;
    double values = 0.00, iQoh;
    TextView lblAmount, lblTotDisc, lblNou, lblPrice, lblTotal;
    Items selectedItem = null;
    int seqno = 0, index_id = 0;
    ListView lv_order_det;
    ArrayList<TranSODet> orderList;
    SharedPref mSharedPref;
    boolean hasChanged = false;
    MainActivity activity;
    double brandDisPer, CompDis, brandDis;
    MyReceiver r;
    ArrayList<Items> list = null;

	/*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        seqno = 0;
        totPieces = 0;
        view = inflater.inflate(R.layout.sales_management_pre_sales_details, container, false);
        mSharedPref = new SharedPref(getActivity());
        itemSearch = (Button) view.findViewById(R.id.btn_item_search);
        bAdd = (Button) view.findViewById(R.id.btn_add);
        bFreeIssue = (Button) view.findViewById(R.id.btn_free);
        txtQty = (EditText) view.findViewById(R.id.et_pieces);
//        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        lblItemName = (EditText) view.findViewById(R.id.et_item);
        lblAmount = (TextView) view.findViewById(R.id.lblTotalVal);
        lblTotal = (TextView) view.findViewById(R.id.lblTotal);
        activity = (MainActivity) getActivity();
        lv_order_det = (ListView) view.findViewById(R.id.lv_order_det);
        lblTotDisc = (TextView) view.findViewById(R.id.lblTotalDisc);
        lblNou = (TextView) view.findViewById(R.id.tv_unit);
        lblPrice = (TextView) view.findViewById(R.id.tv_price);
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.NumVal));
        itemSearch.setOnClickListener(this);
        bAdd.setOnClickListener(this);
        bFreeIssue.setOnClickListener(this);




        /*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        txtQty.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if ((txtQty.length() > 0)) {

                    totPieces = Integer.parseInt(txtQty.getText().toString());

                    if (totPieces <= iQoh) {
                        hasChanged = true;
                        double amt = Double.parseDouble(txtQty.getText().toString()) * Double.parseDouble(lblPrice.getText().toString());
                        double disc = (amt / 100) * (new ControlDS(getActivity()).getCompanyDisc());
                        CompDis = disc;
                        amt -= disc;
                        disc += (amt / 100) * (brandDisPer);
                        brandDis = (amt / 100) * (brandDisPer);
                        amt -= (amt / 100) * (brandDisPer);
                        lblTotDisc.setText(String.format("%.2f", disc));
                        lblAmount.setText(String.format("%.2f", amt));
                    } else {
                        txtQty.setText("0");
                        Toast.makeText(getActivity(), "Entered Qty exceeds Qoh ..!", Toast.LENGTH_SHORT).show();
                    }
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

        /*-*-*-*-*-*-*-*-*-*-*-*-*Listview DELETE-*-*-*-*-*-*-*-*-*-*-*-*/

        lv_order_det.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TranSODet ordDet = orderList.get(position);
                deleteOrderDialog(getActivity(), "Order Details (SA)", ordDet.getFTRANSODET_TYPE(), ordDet.getFTRANSODET_ITEMCODE());
                return true;
            }
        });

        /*-*-*-*-*-*-*-*-*-*-*-*-*Listview Edit*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lv_order_det.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {

                TranSODet ordDet = orderList.get(position);

                if (!ordDet.getFTRANSODET_TYPE().equals("FI")) {
                    new TranSODetDS(getActivity()).restFreeIssueData(RefNo);
                    new OrdFreeIssueDS(getActivity()).ClearFreeIssues(RefNo);
                    new OrderDiscDS(getActivity()).RemoveOrderDiscRecord(RefNo, ordDet.getFTRANSODET_ITEMCODE());

                    FetchData();
                    bAdd.setText("EDIT");
                    selectedItem = new Items();
                    selectedItem.setFITEM_ITEM_CODE(ordDet.getFTRANSODET_ITEMCODE());
                    index_id = Integer.parseInt(ordDet.getFTRANSODET_ID());
                    lblItemName.setText(new ItemsDS(getActivity()).getItemNameByCode(ordDet.getFTRANSODET_ITEMCODE()));

                    iQoh = Double.parseDouble(ordDet.getFTRANSODET_QOH());
                    txtQty.setEnabled(true);
                    txtQty.setText(ordDet.getFTRANSODET_QTY());
                    txtQty.requestFocus();

                    lblPrice.setText(new ItemPriDS(getActivity()).getProductPriceByCode(selectedItem.getFITEM_ITEM_CODE(), activity.selectedDebtor.getFDEBTOR_PRILLCODE()));
                    hasChanged = false;

                    if (ordDet.getFTRANSODET_SCHDISC().length() > 0)
                        lblTotDisc.setText(String.format("%.2f", Double.parseDouble(ordDet.getFTRANSODET_DISAMT()) + Double.parseDouble(ordDet.getFTRANSODET_SCHDISC())));
                    else
                        lblTotDisc.setText(ordDet.getFTRANSODET_DISAMT());

                    lblAmount.setText(ordDet.getFTRANSODET_AMT());
                    UtilityContainer.showKeyboard(getActivity());
                }
            }
        });

        FetchData();
        return view;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_item_search:
                clearTextFields();
                prodcutDetailsDialogbox();
                break;

            case R.id.btn_add: {

                if (!lblItemName.getText().toString().equals("")) {

                    if (values >= 0.0 && totPieces > 0) {

                        TranSODet SODet = new TranSODet();
                        ArrayList<TranSODet> SOList = new ArrayList<TranSODet>();
                        MainActivity activity = (MainActivity) getActivity();
                        String TaxedAmt = new TaxDetDS(getActivity()).calculateTax(selectedItem.getFITEM_ITEM_CODE(), new BigDecimal(lblAmount.getText().toString()));

                        seqno++;
                        SODet.setFTRANSODET_ID(index_id + "");
                        SODet.setFTRANSODET_AMT(String.format("%.2f", Double.parseDouble(lblAmount.getText().toString()) - Double.parseDouble(TaxedAmt)));
                        SODet.setFTRANSODET_BALQTY(totPieces + "");
                        SODet.setFTRANSODET_QTY(totPieces + "");
                        SODet.setFTRANSODET_BAMT(String.format("%.2f", Double.parseDouble(lblAmount.getText().toString()) - Double.parseDouble(TaxedAmt)));
                        SODet.setFTRANSODET_BDISAMT("0");
                        SODet.setFTRANSODET_BPDISAMT("0");
                        SODet.setFTRANSODET_BTAXAMT("0");
                        SODet.setFTRANSODET_TAXAMT(TaxedAmt);
                        SODet.setFTRANSODET_DISAMT(lblTotDisc.getText().toString());
                        SODet.setFTRANSODET_SCHDISPER("0");
                        SODet.setFTRANSODET_COMP_DISPER(new ControlDS(getActivity()).getCompanyDisc() + "");
                        SODet.setFTRANSODET_BRAND_DISPER(brandDisPer + "");
                        SODet.setFTRANSODET_BRAND_DISC(String.format("%.2f", brandDis));
                        SODet.setFTRANSODET_COMP_DISC(String.format("%.2f", CompDis));
                        SODet.setFTRANSODET_COSTPRICE(selectedItem.getFITEM_AVGPRICE());
                        SODet.setFTRANSODET_ITEMCODE(selectedItem.getFITEM_ITEM_CODE());
                        SODet.setFTRANSODET_PRILCODE(activity.selectedDebtor.getFDEBTOR_PRILLCODE());
                        SODet.setFTRANSODET_PICE_QTY(totPieces + "");
                       // SODet.setFTRANSODET_REFNO(activity.selectedSOHed.getFTRANSOHED_REFNO());
                        SODet.setFTRANSODET_REFNO(RefNo);
                        SODet.setFTRANSODET_SELLPRICE(String.format("%.2f", (Double.parseDouble(lblAmount.getText().toString()) - Double.parseDouble(TaxedAmt)) / Double.parseDouble(SODet.getFTRANSODET_QTY())));
                        SODet.setFTRANSODET_BSELLPRICE(SODet.getFTRANSODET_SELLPRICE());
                        SODet.setFTRANSODET_SEQNO(new TranSODetDS(getActivity()).getLastSequnenceNo(RefNo));
                        SODet.setFTRANSODET_TAXCOMCODE(new ItemsDS(getActivity()).getTaxComCodeByItemCode(selectedItem.getFITEM_ITEM_CODE()));
                        SODet.setFTRANSODET_TIMESTAMP_COLUMN("");
                        SODet.setFTRANSODET_BTSELLPRICE(String.format("%.2f", Double.parseDouble(lblAmount.getText().toString()) / Double.parseDouble(SODet.getFTRANSODET_QTY())));
                        SODet.setFTRANSODET_TSELLPRICE(String.format("%.2f", Double.parseDouble(lblAmount.getText().toString()) / Double.parseDouble(SODet.getFTRANSODET_QTY())));
                        SODet.setFTRANSODET_TXNTYPE("21");
                        SODet.setFTRANSODET_LOCCODE(new SalRepDS(getActivity()).getCurrentLocCode());
                        SODet.setFTRANSODET_TXNDATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        SODet.setFTRANSODET_IS_ACTIVE("1");
                        SODet.setFTRANSODET_RECORDID("");
                        SODet.setFTRANSODET_PDISAMT("0");
                        SODet.setFTRANSODET_IS_SYNCED("0");
                        SODet.setFTRANSODET_QOH(iQoh + "");
                        SODet.setFTRANSODET_TYPE("SA");
                        SODet.setFTRANSODET_SCHDISC("0");
                        SODet.setFTRANSODET_PRICE(lblPrice.getText().toString());
                        SODet.setFTRANSODET_DISCTYPE("");

                        SOList.add(SODet);

                        if (!(bAdd.getText().equals("EDIT") && hasChanged == false)) {
                            new TranSODetDS(getActivity()).createOrUpdateSODet(SOList);
                        }

                        if (bAdd.getText().equals("EDIT"))
                            Toast.makeText(getActivity(), "Edited successfully !", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(), "Added successfully !", Toast.LENGTH_LONG).show();

                        txtQty.setEnabled(false);
                        clearTextFields();
                        FetchData();
                        UtilityContainer.hideKeyboard(getActivity());
                    }
                }
            }

            break;

            case R.id.btn_free: {

			/* GET CURRENT ORDER DETAILS FROM TABLE */
                ArrayList<TranSODet> dets = new TranSODetDS(getActivity()).getSAForFreeIssueCalc(RefNo);
            /* CLEAR ORDERDET TABLE RECORD IF FREE ITEMS ARE ALREADY ADDED. */
                new TranSODetDS(getActivity()).restFreeIssueData(RefNo);
            /* Clear free issues in OrdFreeIss */
                new OrdFreeIssueDS(getActivity()).ClearFreeIssues(RefNo);
            /*
             * JUST LOAD ORDER ITEMS TO LISTVIEW & CLEAR FREE ITEM FROM LISTVIEW
			 * USER ALREADY RETREIVED FREE ISSUES
			 */
                FetchData();

                FreeIssue issue = new FreeIssue(getActivity());

			/* Get discounts for assorted items */
                ArrayList<ArrayList<TranSODet>> metaOrdList = issue.SortDiscount(dets,"");

			/* Iterate through for discounts for items */
                for (ArrayList<TranSODet> OrderList : metaOrdList) {

                    double totAmt = 0;
                    double freeVal = Double.parseDouble(OrderList.get(0).getFTRANSODET_BAMT());
                    String discPer = OrderList.get(0).getFTRANSODET_SCHDISPER();
                    String discType = OrderList.get(0).getFTRANSODET_DISCTYPE();
                    String discRef = OrderList.get(0).getFTRANSODET_DISC_REF();
                    OrderList.get(0).setFTRANSODET_BAMT("0");

                    for (TranSODet det : OrderList)
                        totAmt += Double.parseDouble(det.getFTRANSODET_PRICE()) * (Double.parseDouble(det.getFTRANSODET_QTY()));

                    for (TranSODet det : OrderList) {
                        det.setFTRANSODET_SCHDISPER(discPer);
                        det.setFTRANSODET_DISCTYPE(discType);
                        det.setFTRANSODET_DISC_REF(discRef);

                        double disc;

					/*
					 * For value, calculate amount portion & for percentage ,
					 * calculate percentage portion
					 */
                        disc = (freeVal / totAmt) * Double.parseDouble(det.getFTRANSODET_PRICE()) * (Double.parseDouble(det.getFTRANSODET_QTY()));

					/* Calculate discount amount from disc percentage portion */
                        if (discType.equals("P"))
                            disc = (Double.parseDouble(det.getFTRANSODET_AMT()) / 100) * disc;

                        new TranSODetDS(getActivity()).updateDiscount(det, disc, det.getFTRANSODET_DISCTYPE());
                    }
                }

                // GET ARRAY OF FREE ITEMS BY PASSING IN ORDER DETAILS
                //ArrayList<FreeItemDetails> list = issue.getFreeItemsBySalesItem(dets);
                ArrayList<FreeItemDetails> list = issue.getFreeItemsBySalesItem(dets, new SharedPref(getActivity()).getGlobalVal("preKeyCostCode"));
                // PASS EACH ITEM IN TO DIALOG BOX FOR USER SELECTION
                for (FreeItemDetails freeItemDetails : list) {
                    freeIssueDialogBox(freeItemDetails);
                }

            }

            FetchData();
            break;

            default:
                break;
        }
    }

	/*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-Get remaining discount*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getTotalSale(ArrayList<TranSODet> Orderlist) {
        double d = 0;

        if (Orderlist.size() > 0) {
            for (TranSODet ordDet : Orderlist) {
                d = d + Double.parseDouble(ordDet.getFTRANSODET_AMT());
            }
        }
        return String.format("%.2f", d);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-grab data from table-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private void deleteOrderDialog(final Context context, String title, final String type, final String itemCode) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to delete this entry?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                int count = new TranSODetDS(context).deleteOrdDetItemCode(itemCode, type);

                if (count > 0) {

                    new TranSODetDS(getActivity()).restFreeIssueData(RefNo);
                    new OrdFreeIssueDS(getActivity()).ClearFreeIssues(RefNo);
                    new OrderDiscDS(getActivity()).RemoveOrderDiscRecord(RefNo, itemCode);

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

	/*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void FetchData() {
        try {
            lv_order_det.setAdapter(null);
            orderList = new TranSODetDS(getActivity()).getAllOrderDetails(RefNo);
            lv_order_det.setAdapter(new OrderDetailsAdapter(getActivity(), orderList,""));
            lblTotal.setText(getTotalSale(orderList));
        } catch (NullPointerException e) {
            Log.v("SA Error", e.toString());
        }
    }

    private void clearTextFields() {
        values = 0.0;
        index_id = 0;
        totPieces = 0;
        lblItemName.setText("");
        txtQty.setText("0");
        lblAmount.setText("0.00");
        txtQty.clearFocus();
        lblNou.setText("0");
        lblPrice.setText("0.00");
        lblTotDisc.setText("0.00");
        bAdd.setText("ADD");
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void prodcutDetailsDialogbox() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_details_search_item);

        final SearchView search = (SearchView) dialog.findViewById(R.id.et_search);
        final ListView productList = (ListView) dialog.findViewById(R.id.lv_product_list);

        clearTextFields();
        dialog.setCancelable(true);
        productList.clearTextFilter();
        list = new ItemsDS(getActivity()).getAllItem("", "txntype ='21'", RefNo, new SalRepDS(getActivity()).getCurrentLocCode());
        productList.setAdapter(new ProductAdapter(getActivity(), list));

        productList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = list.get(position);
                if (activity.selectedSOHed == null) {
                    TranSOHed SOHed = new TranSOHedDS(getActivity()).getActiveSOHed();
                    if (SOHed != null) {
                        if (activity.selectedDebtor == null)
                            activity.selectedDebtor = new DebtorDS(getActivity()).getSelectedCustomerByCode(SOHed.getFTRANSOHED_DEBCODE());
                    }
                    brandDisPer = new DebItemPriDS(getActivity()).getBrandDiscount(selectedItem.getFITEM_BRANDCODE(), activity.selectedDebtor.getFDEBTOR_CODE());
                }else{
                    brandDisPer = new DebItemPriDS(getActivity()).getBrandDiscount(selectedItem.getFITEM_BRANDCODE(), activity.selectedDebtor.getFDEBTOR_CODE());
                }

                lblItemName.setText(selectedItem.getFITEM_ITEM_NAME());
                lblNou.setText(selectedItem.getFITEM_NOUCASE());
                lblPrice.setText(new ItemPriDS(getActivity()).getProductPriceByCode(selectedItem.getFITEM_ITEM_CODE(), activity.selectedDebtor.getFDEBTOR_PRILLCODE()));
                iQoh = Double.parseDouble(selectedItem.getFITEM_QOH());
                txtQty.setEnabled(true);
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
                list = new ItemsDS(getActivity()).getAllItem(newText, "txntype ='21'", RefNo, new SalRepDS(getActivity()).getCurrentLocCode());

                productList.clearTextFilter();
                productList.setAdapter(new ProductAdapter(getActivity(), list));

                return false;
            }
        });

        dialog.show();

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onTaskCompleted(String result) {
        try {
            if (!result.equals("") || !result.equals("No Address")) {

                MainActivity activity = (MainActivity) getActivity();

                if (activity.selectedSOHed.getFTRANSOHED_ADDRESS() == null) {
                    SharedPreferencesClass.setLocalSharedPreference(getActivity(), "GPS_Address", result);
                    activity.selectedSOHed.setFTRANSOHED_ADDRESS(result);
                }

            }
        } catch (Exception e) {
            Log.v("Selected OrdHed ", e.toString());
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

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

        listView.setOnItemClickListener(new OnItemClickListener() {

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
                        String unitPrice = new ItemPriDS(getActivity()).getProductPriceByCode(itemFreeIssue.getItems().getFITEM_ITEM_CODE(), activity.selectedDebtor.getFDEBTOR_PRILLCODE());
                        ordDet.setFTRANSODET_BSELLPRICE("0");
                        ordDet.setFTRANSODET_BTSELLPRICE(unitPrice);
                        ordDet.setFTRANSODET_DISAMT("0");
                        ordDet.setFTRANSODET_SCHDISPER("0");
                        ordDet.setFTRANSODET_FREEQTY("0");
                        ordDet.setFTRANSODET_ITEMCODE(itemFreeIssue.getItems().getFITEM_ITEM_CODE());
                        ordDet.setFTRANSODET_PDISAMT("0");
                        ordDet.setFTRANSODET_PRILCODE(activity.selectedDebtor.getFDEBTOR_PRILLCODE());
                        ordDet.setFTRANSODET_QTY(itemFreeIssue.getAlloc());
                        ordDet.setFTRANSODET_PICE_QTY(itemFreeIssue.getAlloc());
                        ordDet.setFTRANSODET_TYPE("FI");
                        ordDet.setFTRANSODET_RECORDID("");
                        ordDet.setFTRANSODET_REFNO(RefNo);
                        ordDet.setFTRANSODET_SELLPRICE("0");
                        ordDet.setFTRANSODET_SEQNO(seqno + "");
                        ordDet.setFTRANSODET_TAXAMT("0");
                        ordDet.setFTRANSODET_TAXCOMCODE(new ItemsDS(getActivity()).getTaxComCodeByItemCode(itemFreeIssue.getItems().getFITEM_ITEM_CODE()));
                        ordDet.setFTRANSODET_TIMESTAMP_COLUMN("");
                        ordDet.setFTRANSODET_TSELLPRICE(unitPrice);
                        ordDet.setFTRANSODET_TXNDATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        ordDet.setFTRANSODET_TXNTYPE("27");
                        ordDet.setFTRANSODET_IS_ACTIVE("1");
                        ordDet.setFTRANSODET_LOCCODE(new SalRepDS(getActivity()).getCurrentLocCode());
                        ordDet.setFTRANSODET_COSTPRICE("0");
                        ordDet.setFTRANSODET_BTAXAMT("0");
                        ordDet.setFTRANSODET_IS_SYNCED("0");
                        ordDet.setFTRANSODET_QOH("0");
                        ordDet.setFTRANSODET_SCHDISC("0");
                        ordDet.setFTRANSODET_BRAND_DISC("0");
                        ordDet.setFTRANSODET_BRAND_DISPER("0");
                        ordDet.setFTRANSODET_COMP_DISC("0");
                        ordDet.setFTRANSODET_COMP_DISPER("0");
                        ordDet.setFTRANSODET_DISCTYPE("");
                        ordDet.setFTRANSODET_PRICE(unitPrice);

						/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*OrdFreeIssue table update*-*-*-*-*-*-*-*-*-*-*-*-*-*/

                        OrdFreeIssue ordFreeIssue = new OrdFreeIssue();
                        ordFreeIssue.setOrdFreeIssue_ItemCode(itemFreeIssue.getItems().getFITEM_ITEM_CODE());
                        ordFreeIssue.setOrdFreeIssue_Qty(itemFreeIssue.getAlloc());
                        ordFreeIssue.setOrdFreeIssue_RefNo(FIRefNo);
                       // ordFreeIssue.setOrdFreeIssue_RefNo1(activity.selectedSOHed.getFTRANSOHED_REFNO());
                        ordFreeIssue.setOrdFreeIssue_RefNo1(RefNo);
                        ordFreeIssue.setOrdFreeIssue_TxnDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        new OrdFreeIssueDS(getActivity()).UpdateOrderFreeIssue(ordFreeIssue);

						/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*/

                        ordList.add(ordDet);

                        if (detDS.createOrUpdateSODet(ordList) > 0) {
                            clearTextFields();
                            Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_SHORT).show();
                            FetchData();
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private boolean DiscountDialogBox(final ArrayList<TranSODet> OrderList) {

        final double DiscValue = Double.parseDouble(OrderList.get(0).getFTRANSODET_BAMT());
        OrderList.get(0).setFTRANSODET_BAMT("0.00");
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.free_issues_items_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Please allocate discounts...");
        alertDialogBuilder.setView(promptView);

        final ListView listView = (ListView) promptView.findViewById(R.id.lv_free_issue);
        final TextView itemName = (TextView) promptView.findViewById(R.id.tv_free_issue_item_name);
        final TextView freeQty = (TextView) promptView.findViewById(R.id.tv_free_qty);

        freeQty.setText("Discount you have : " + DiscValue);

        listView.setAdapter(new DiscountAdapter(getActivity(), OrderList));
        // When user select item to enter discount value
        listView.setOnItemClickListener(new OnItemClickListener() {

            // double avaliableQty = getAvailableTotal(OrderList);

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, final int position, long id) {

                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

                View promptView = layoutInflater.inflate(R.layout.set_free_issue_dialog, null);

                final TextView leftQty = (TextView) promptView.findViewById(R.id.tv_free_item_left_qty);
                final EditText enteredQty = (EditText) promptView.findViewById(R.id.et_free_qty);

                leftQty.setText("Discount : " + getAvailableTotal(DiscValue, OrderList));
                // when user is entering value
                enteredQty.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        // user didn't enter any value
                        if (enteredQty.getText().toString().equals("")) {

                        } else {
                            // show left out discount values accordingly
                            if ((getAvailableTotal(DiscValue, OrderList) - (Double.parseDouble(enteredQty.getText().toString()))) < 0) {
                                enteredQty.setText("");
                                leftQty.setText("Discount : " + (getAvailableTotal(DiscValue, OrderList)));
                                Toast.makeText(getActivity(), "No more discounts remaining ..!", Toast.LENGTH_SHORT).show();
                            } else {
                                leftQty.setText("Discount left : " + (getAvailableTotal(DiscValue, OrderList) - (Double.parseDouble(enteredQty.getText().toString()))));
                            }

                        }
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                });

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setView(promptView);

                alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if user entered values for discount

                        if (!(enteredQty.getText().toString().equals(""))) {
                            OrderList.get(position).setFTRANSODET_SCHDISC(enteredQty.getText().toString());
                            freeQty.setText("Discount left : " + getAvailableTotal(DiscValue, OrderList));
                        }

                        listView.clearTextFilter();
                        listView.setAdapter(new DiscountAdapter(getActivity(), OrderList));

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();

            }
        });

        // if customer chose to update discount values
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // update discount values to database
                new TranSODetDS(getActivity()).UpdateArrayDiscount(OrderList);
                Toast.makeText(getActivity(), "Discount updated successfully..", Toast.LENGTH_LONG).show();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return true;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*- Method to calculate remaining discount values -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public double getAvailableTotal(double discVal, ArrayList<TranSODet> OrderList) {
        double avQTY = 0;

        for (TranSODet mOrdDet : OrderList) {
            avQTY = avQTY + Double.parseDouble(mOrdDet.getFTRANSODET_DISAMT());
            System.out.println(mOrdDet.getFTRANSODET_DISAMT() + " - " + mOrdDet.getFTRANSODET_ITEMCODE());
        }
        return (discVal - avQTY);
    }


    public void mToggleTextbox() {

        if (mSharedPref.getGlobalVal("PrekeyCustomer").equals("Y")) {
            bFreeIssue.setEnabled(true);
            bAdd.setEnabled(true);
            itemSearch.setEnabled(true);
            FetchData();
        } else {
            bFreeIssue.setEnabled(false);
            bAdd.setEnabled(false);
            itemSearch.setEnabled(false);
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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_DETAILS"));
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            PreSalesOrderDetails.this.mToggleTextbox();
        }
    }


}

