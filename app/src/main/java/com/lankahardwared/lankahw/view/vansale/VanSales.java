package com.lankahardwared.lankahw.view.vansale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.AvailbelFreeIsuueSchemaAdapter;
import com.lankahardwared.lankahw.adapter.CustomerDebtAdapter;
import com.lankahardwared.lankahw.adapter.InvoiceExpandableListAdapter;
import com.lankahardwared.lankahw.adapter.MustSalesAdapter;
import com.lankahardwared.lankahw.adapter.VanPagerAdapter;
import com.lankahardwared.lankahw.control.ConnectionDetector;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.data.FDDbNoteDS;
import com.lankahardwared.lankahw.data.FInvHedSummaryDS;
import com.lankahardwared.lankahw.data.FreeHedDS;
import com.lankahardwared.lankahw.data.InvDetDS;
import com.lankahardwared.lankahw.data.ItemsDS;
import com.lankahardwared.lankahw.data.fInvDetSummaryDS;
import com.lankahardwared.lankahw.data.finvDetSummary;
import com.lankahardwared.lankahw.data.finvhedSummary;
import com.lankahardwared.lankahw.model.FDDbNote;
import com.lankahardwared.lankahw.model.FreeHed;
import com.lankahardwared.lankahw.model.FreeIssue;
import com.lankahardwared.lankahw.model.InvDet;
import com.lankahardwared.lankahw.model.InvHed;
import com.lankahardwared.lankahw.model.Items;
import com.lankahardwared.lankahw.model.TranSOHed;
import com.lankahardwared.lankahw.view.stock_inquiry.StockInquiryDialog;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.LayoutParams;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VanSales extends Fragment {
    View view;
    TabLayout tabLayout;
    Activity activity;
    FloatingActionMenu fam;
    FloatingActionButton fab;
    ViewPager viewPager;
    VanPagerAdapter adapter;
    boolean status;
    private InvHed tmpinvHed=null;
    TranSOHed soHedTmp=null;
    private SweetAlertDialog pDialog;
    private   String sp_url;
    private String debCode="";
    public static SharedPreferences localSP;
    public static final String SETTINGS = "SETTINGS";
    ArrayList<finvhedSummary>finvHedLastBilArrayList;
    ArrayList<finvDetSummary>finvDetSummaryArrayList;
    private InvoiceExpandableListAdapter expandableListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sales_management_pre_sales, container, false);
        setHasOptionsMenu(true);
        activity = getActivity();
//        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        sp_url= localSP.getString("URL", "").toString();



        Bundle mBundle = getArguments();

        if (mBundle != null) {
            status = mBundle.getBoolean("Active");
            tmpinvHed= (InvHed) mBundle.getSerializable("order");
            if(tmpinvHed!=null)
                Log.d("<>******Ref No********","" +tmpinvHed.getFINVHED_REFNO());
        }

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Customer"));
        tabLayout.addTab(tabLayout.newTab().setText("Header"));
        tabLayout.addTab(tabLayout.newTab().setText("Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Summary"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                               @Override
                                               public void onTabSelected(TabLayout.Tab tab) {
                                                   viewPager.setCurrentItem(tab.getPosition());
                                               }

                                               @Override
                                               public void onTabUnselected(TabLayout.Tab tab) {
                                               }

                                               @Override
                                               public void onTabReselected(TabLayout.Tab tab) {
                                               }
                                           }

        );

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        adapter = new VanPagerAdapter(getChildFragmentManager(), 4,tmpinvHed);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                tabLayout.getTabAt(position).select();

                if (position == 3)
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_SUMMARY"));
                else if (position == 1)
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_HEADER"));
                else if (position == 2)
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_DETAILS"));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        SubActionButton.Builder builder = new SubActionButton.Builder(activity);

        //Must sales
        ImageView icon_1 = new ImageView(getActivity());
        icon_1.setImageResource(R.drawable.float_1);

        //Customer Oustranding   #dhanushika#
        ImageView icon_2 = new ImageView(getActivity());
        icon_2.setImageResource(R.drawable.float_2);

        //stock inquiry
        ImageView icon_3 = new ImageView(getActivity());
        icon_3.setImageResource(R.drawable.float_3);

        ImageView icon_4 = new ImageView(getActivity());
        icon_4.setImageResource(R.drawable.float_4);

        ImageView icon_5 = new ImageView(getActivity());
        icon_5.setImageResource(R.drawable.float_5);
        ImageView icon_6 = new ImageView(getActivity());
        icon_6.setImageResource(R.drawable.float_6);

        SubActionButton must_sales_btn = builder.setContentView(icon_1).build();
        SubActionButton cus_outstranding_btn = builder.setContentView(icon_2).build();
        SubActionButton stock_inquiry_btn = builder.setContentView(icon_3).build();
        SubActionButton button_4 = builder.setContentView(icon_4).build();
        SubActionButton lastBillDetail_btn = builder.setContentView(icon_5).build();
        SubActionButton btn_free_issue = builder.setContentView(icon_6).build();

        LayoutParams params = new LayoutParams(70, 70);

        must_sales_btn.setLayoutParams(params);
        cus_outstranding_btn.setLayoutParams(params);
        stock_inquiry_btn.setLayoutParams(params);
        button_4.setLayoutParams(params);
        lastBillDetail_btn.setLayoutParams(params);
        btn_free_issue.setLayoutParams(params);

        fam = new FloatingActionMenu.Builder(activity)
                .setStartAngle(-180)
                .setEndAngle(0)
                .setRadius(200)
                .addSubActionView(stock_inquiry_btn)        //stock inquiry
                .addSubActionView(cus_outstranding_btn)   //Customer Oustranding
                .addSubActionView(must_sales_btn)  //Must Sales
                .addSubActionView(button_4)
                .addSubActionView(lastBillDetail_btn)
                .addSubActionView(btn_free_issue)
                .attachTo(fab)
                .build();

        stock_inquiry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new StockInquiryDialog(activity);
                fam.close(true);
            }
        });

        cus_outstranding_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = new SharedPref(getActivity()).getGlobalVal("keyCusCode");

                if (s.equals(""))
                    mCustomerOutstandingDialog("");
                else
                    mCustomerOutstandingDialog(s);

                fam.close(true);
            }
        });

        must_sales_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     #dhanushika#
                showMustSalesDailog();
                //Toast.makeText(getActivity(), "Delete Clicked", Toast.LENGTH_SHORT).show();
                fam.close(true);
            }
        });

        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Delete Clicked", Toast.LENGTH_SHORT).show();
                fam.close(true);
            }
        });

        lastBillDetail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean connectionStatus = new ConnectionDetector(getActivity()).isConnectingToInternet();
                if (connectionStatus == true) {
                      new FinvHedLastBil().execute();

                } else {
                    Toast.makeText(getActivity(), "can't get data", Toast.LENGTH_LONG).show();
                }
                fam.close(true);
            }
        });

        btn_free_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowFreeIsueSechema();

              //  Toast.makeText(getActivity(), " Clicked", Toast.LENGTH_SHORT).show();
                fam.close(true);
            }
        });


        fam.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fam.close(true);
                    }
                }, 2000);
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {

            }
        });

        return view;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onStart() {
        super.onStart();
        if (status)
            viewPager.setCurrentItem(2);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mMoveToHeader() {
        viewPager.setCurrentItem(1);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fam.close(true);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

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

    //---------------------------------------Must Sales-----------#dhanushika#----------------------------------------------------
    public void showMustSalesDailog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.must_sales_product_daliog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Must Sales ");
        alertDialogBuilder.setView(promptView);
        final ListView listView = (ListView) promptView.findViewById(R.id.lv_mustSales_list);
        ArrayList<Items> itemsArrayList = new ItemsDS(getActivity()).getItemsForMustSales();
        listView.setAdapter(new MustSalesAdapter(getActivity(), itemsArrayList));
        alertDialogBuilder.setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
//--------------------------------------------------------free isue schema---------------------------------------------------------------------------------

    public  void ShowFreeIsueSechema() {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.availbale_free_issue_product_daliog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Free Issue Schema: ");
        alertDialogBuilder.setView(promptView);
        final ListView listView = (ListView) promptView.findViewById(R.id.lv_available_list);

        FreeHedDS freeHedDS = new FreeHedDS(getActivity());
        FreeHed freeHed=new FreeHed() ;
        FreeIssue issue = new FreeIssue(getActivity());
        ArrayList<FreeHed>freeHedArrayList=new ArrayList<FreeHed>();
        String RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.VanNumVal));

        ArrayList<InvDet> dets = new InvDetDS(getActivity()).getSAForFreeIssueCalc(RefNo);


        for (InvDet det : dets) {
            freeHed= freeHedDS.getFreeIssueItemSchema(det.getFINVDET_ITEM_CODE());

            if(freeHed.getFFREEHED_REFNO()!=null) {
                freeHedArrayList.add(freeHed);
            }

        }

        listView.setAdapter(new AvailbelFreeIsuueSchemaAdapter(getActivity(), freeHedArrayList));
        alertDialogBuilder.setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);




    }
    //----------------------------------------------------------------------------------------------------------------------------------------------


    public class  FinvHedLastBil extends AsyncTask<Object, Object, ArrayList<finvhedSummary>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Fetching invoice hed data from the server...");
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected ArrayList<finvhedSummary> doInBackground(Object... objects) {

            String UR = "http://" + sp_url;
            debCode= new SharedPref(getActivity()).getGlobalVal("keyCusCode");
            try {
                URL json = new URL(UR + getActivity().getResources().getString(R.string.ConnectionURL) + "/finvHedLastBill/mobile123/" + localSP.getString("Console_DB", "").toString() + "/GEN00022");
                URLConnection jc  = json.openConnection();


                BufferedReader readerfdblist = new BufferedReader(new InputStreamReader(jc.getInputStream()));
                String line = readerfdblist.readLine();
                JSONObject jsonResponse = new JSONObject(line);
                JSONArray jsonArray = jsonResponse.getJSONArray("finvHedLastBillResult");
                finvHedLastBilArrayList = new ArrayList<>();
                finvHedLastBilArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    finvhedSummary finvhedSummary = new finvhedSummary();
                    finvhedSummary.setRefNo(jsonObject.getString("RefNo"));
                    finvhedSummary.setDebCode(jsonObject.getString("DebCode"));
                    finvhedSummary.setTotalAmt(jsonObject.getString("TotalAmt"));
                    finvhedSummary.setTotalDis(jsonObject.getString("TotalDis"));
                    finvhedSummary.setTxnDate(jsonObject.getString("TxnDate"));
                    finvHedLastBilArrayList.add(finvhedSummary);
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return finvHedLastBilArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<finvhedSummary> finvhedSummaries) {
            super.onPostExecute(finvhedSummaries);
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (new FInvHedSummaryDS(getActivity()).CreateOrUpdateHedData(finvHedLastBilArrayList) > 0) ;
                new FOrdDetLastBill().execute();
            Log.v("createOrUpdate", "Result :  Data Inserted successfully");
        }
//------------------------------------------------get Last three bill infromation from sever------------------------------------------------------
    }
    public class FOrdDetLastBill extends AsyncTask<Object, Object, ArrayList<finvDetSummary>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Fetching  details from the server...");
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected ArrayList<finvDetSummary> doInBackground(Object... objects) {

            String URL = "http://" + sp_url;
            debCode= new SharedPref(getActivity()).getGlobalVal("keyCusCode");
            try {
                URL json = new URL(URL + getActivity().getResources().getString(R.string.ConnectionURL) + "/finvDetLastBill/mobile123/" + localSP.getString("Console_DB", "").toString() + "/GEN00022");
                URLConnection jc = json.openConnection();


                BufferedReader readerfdblist = new BufferedReader(new InputStreamReader(jc.getInputStream()));
                String line = readerfdblist.readLine();
                JSONObject jsonResponse = new JSONObject(line);
                JSONArray jsonArray = jsonResponse.getJSONArray("finvDetLastBillResult");
                finvDetSummaryArrayList = new ArrayList<>();
                finvDetSummaryArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    finvDetSummary ordDetSummary=new finvDetSummary();
                    ordDetSummary.setAmt(jsonObject.getString("Amt"));
                    ordDetSummary.setItemCode(jsonObject.getString("ItemCode").trim());
                    ordDetSummary.setQty(jsonObject.getString("Qty"));
                    ordDetSummary.setRefNo(jsonObject.getString("RefNo"));
                    ordDetSummary.setSeqNo(jsonObject.getString("SeqNo"));
                    ordDetSummary.setTaxAmt(jsonObject.getString("TaxAmt"));
                    ordDetSummary.setTaxComCode(jsonObject.getString("TaxComCode").trim());
                    ordDetSummary.setTxnDate(jsonObject.getString("TxnDate"));
                    ordDetSummary.setType(jsonObject.getString("type"));
                    finvDetSummaryArrayList.add(ordDetSummary);
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return finvDetSummaryArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<finvDetSummary> ordDetSummaryArrayList) {
            super.onPostExecute(ordDetSummaryArrayList);
            if (pDialog.isShowing()){
                pDialog.dismiss();
                if(ordDetSummaryArrayList!=null){
                    if (new fInvDetSummaryDS(getActivity()).CreateOrUpdateDetData(ordDetSummaryArrayList) > 0) ;

                    showLastThreeBillVanSales();

                    Log.v("createOrUpdate", "Result :  Data Inserted successfully");
                }
            }



        }

    }

//--------------------------------------------------dalog box popup

    public void showLastThreeBillVanSales(){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.dailog_last_bill, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Van Sales Summary ");
        alertDialogBuilder.setView(promptView);
        final ExpandableListView listView = (ExpandableListView) promptView.findViewById(R.id.vanSales_history_listview);


        debCode= new SharedPref(getActivity()).getGlobalVal("");
        //final ArrayList<finvhedSummary> ordhedSummaryArrayList=new FInvHedSummaryDS(getActivity()).getFinvSummary(debCode);
        final ArrayList<finvhedSummary> ordhedSummaryArrayList=new FInvHedSummaryDS(getActivity()).getFinvSummary("GEN00022");

        // create the adapter by passing your ArrayList data
        expandableListAdapter = new InvoiceExpandableListAdapter(getActivity(), ordhedSummaryArrayList);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroup(groupPosition);

                } else {
                    listView.expandGroup(groupPosition);
                }

                return true;
            }
        });

        // attach the adapter to the expandable list view
        listView.setAdapter(expandableListAdapter);
       // expandableListAdapter.setData(ordhedSummaryArrayList);

        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity(),
                        ordhedSummaryArrayList.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();
               // expandableListAdapter.setData(ordhedSummaryArrayList);

            }
        });

        alertDialogBuilder.setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }


}