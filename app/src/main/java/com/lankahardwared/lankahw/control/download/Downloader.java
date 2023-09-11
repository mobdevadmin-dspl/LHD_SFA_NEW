package com.lankahardwared.lankahw.control.download;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.control.TaskType;
import com.lankahardwared.lankahw.data.AchievementDS;
import com.lankahardwared.lankahw.data.ApprOrdHedDS;
import com.lankahardwared.lankahw.data.AreaDS;
import com.lankahardwared.lankahw.data.BankDS;
import com.lankahardwared.lankahw.data.BrandDS;
import com.lankahardwared.lankahw.data.BrandTargetDS;
import com.lankahardwared.lankahw.data.CompanyBranchDS;
import com.lankahardwared.lankahw.data.CompanySettingDS;
import com.lankahardwared.lankahw.data.ControlDS;
import com.lankahardwared.lankahw.data.DebItemPriDS;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.DiscdebDS;
import com.lankahardwared.lankahw.data.DiscdetDS;
import com.lankahardwared.lankahw.data.DischedDS;
import com.lankahardwared.lankahw.data.DiscslabDS;
import com.lankahardwared.lankahw.data.ExpenseDS;
import com.lankahardwared.lankahw.data.FDDbNoteDS;
import com.lankahardwared.lankahw.data.FDebTaxDS;
import com.lankahardwared.lankahw.data.FItTourDiscDs;
import com.lankahardwared.lankahw.data.FIteDebDetDS;
import com.lankahardwared.lankahw.data.FItenrDetDS;
import com.lankahardwared.lankahw.data.FItenrHedDS;
import com.lankahardwared.lankahw.data.FOtherTransDS;
import com.lankahardwared.lankahw.data.FinvDetL3DS;
import com.lankahardwared.lankahw.data.FreeDebDS;
import com.lankahardwared.lankahw.data.FreeDetDS;
import com.lankahardwared.lankahw.data.FreeHedDS;
import com.lankahardwared.lankahw.data.FreeItemDS;
import com.lankahardwared.lankahw.data.FreeMslabDS;
import com.lankahardwared.lankahw.data.FreeSlabDS;
import com.lankahardwared.lankahw.data.GroupDS;
import com.lankahardwared.lankahw.data.ItemLocDS;
import com.lankahardwared.lankahw.data.ItemPriDS;
import com.lankahardwared.lankahw.data.ItemsDS;
import com.lankahardwared.lankahw.data.LocationsDS;
import com.lankahardwared.lankahw.data.MerchDS;
import com.lankahardwared.lankahw.data.ReasonDS;
import com.lankahardwared.lankahw.data.RouteDS;
import com.lankahardwared.lankahw.data.RouteDetDS;
import com.lankahardwared.lankahw.data.STKInDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.data.SkuDS;
import com.lankahardwared.lankahw.data.SubBrandDS;
import com.lankahardwared.lankahw.data.TargetDetDS;
import com.lankahardwared.lankahw.data.TaxDS;
import com.lankahardwared.lankahw.data.TaxDetDS;
import com.lankahardwared.lankahw.data.TaxHedDS;
import com.lankahardwared.lankahw.data.TermDS;
import com.lankahardwared.lankahw.data.TourHedDS;
import com.lankahardwared.lankahw.data.TownDS;
import com.lankahardwared.lankahw.data.TypeDS;
import com.lankahardwared.lankahw.data.fDistrictDS;
import com.lankahardwared.lankahw.listviewitems.CustomProgressDialog;
import com.lankahardwared.lankahw.model.Achievement;
import com.lankahardwared.lankahw.model.ApprOrdHed;
import com.lankahardwared.lankahw.model.Area;
import com.lankahardwared.lankahw.model.Bank;
import com.lankahardwared.lankahw.model.Brand;
import com.lankahardwared.lankahw.model.BrandTarget;
import com.lankahardwared.lankahw.model.CompanyBranch;
import com.lankahardwared.lankahw.model.CompanySetting;
import com.lankahardwared.lankahw.model.Control;
import com.lankahardwared.lankahw.model.DebItemPri;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.Discdeb;
import com.lankahardwared.lankahw.model.Discdet;
import com.lankahardwared.lankahw.model.Disched;
import com.lankahardwared.lankahw.model.Discslab;
import com.lankahardwared.lankahw.model.Expense;
import com.lankahardwared.lankahw.model.FDDbNote;
import com.lankahardwared.lankahw.model.FDebTax;
import com.lankahardwared.lankahw.model.FItTourDisc;
import com.lankahardwared.lankahw.model.FIteDebDet;
import com.lankahardwared.lankahw.model.FItenrDet;
import com.lankahardwared.lankahw.model.FItenrHed;
import com.lankahardwared.lankahw.model.FinvDetL3;
import com.lankahardwared.lankahw.model.FreeDeb;
import com.lankahardwared.lankahw.model.FreeDet;
import com.lankahardwared.lankahw.model.FreeHed;
import com.lankahardwared.lankahw.model.FreeItem;
import com.lankahardwared.lankahw.model.FreeMslab;
import com.lankahardwared.lankahw.model.FreeSlab;
import com.lankahardwared.lankahw.model.Group;
import com.lankahardwared.lankahw.model.ItemLoc;
import com.lankahardwared.lankahw.model.ItemPri;
import com.lankahardwared.lankahw.model.Items;
import com.lankahardwared.lankahw.model.Locations;
import com.lankahardwared.lankahw.model.Merch;
import com.lankahardwared.lankahw.model.Reason;
import com.lankahardwared.lankahw.model.Route;
import com.lankahardwared.lankahw.model.RouteDet;
import com.lankahardwared.lankahw.model.SKU;
import com.lankahardwared.lankahw.model.SalRep;
import com.lankahardwared.lankahw.model.StkIn;
import com.lankahardwared.lankahw.model.SubBrand;
import com.lankahardwared.lankahw.model.TERMS;
import com.lankahardwared.lankahw.model.TargetDet;
import com.lankahardwared.lankahw.model.Tax;
import com.lankahardwared.lankahw.model.TaxDet;
import com.lankahardwared.lankahw.model.TaxHed;
import com.lankahardwared.lankahw.model.TourHed;
import com.lankahardwared.lankahw.model.Town;
import com.lankahardwared.lankahw.model.Type;
import com.lankahardwared.lankahw.model.fDistrict;
import com.lankahardwared.lankahw.model.mapper.FOtherTransactions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

//import com.datamation.finacnew.data.fDistrictDS;
//import com.datamation.finacnew.model.fDistrict;

public class Downloader extends AsyncTask<Void, Integer, String> {

    Context context;
    DownloadTaskListener taskListener;
    String ConnectionURL, downLoadURL;
    TaskType taskType;
    String downloadingDataType = "";
    ArrayList<String> list = new ArrayList<>();
    String TAG = "Downloader ";
    int totalRecords = 0;
    CustomProgressDialog progressDialog;

    public Downloader(Context context, DownloadTaskListener taskListener, TaskType taskType, String ConnURL, String downLoadURL) {

        this.context = context;
        this.taskListener = taskListener;
        this.ConnectionURL = ConnURL;
        this.taskType = taskType;
        this.downLoadURL = downLoadURL;

        this.totalRecords = 0;

        switch (taskType) {
            case DATABASENAME:
                downloadingDataType = " Master ";
                break;

            case FITEMLOC:
                downloadingDataType = " Item Locations";
                break;

            case FFREESLAB:
                downloadingDataType = " Free issue ";
                break;

            case FFREEHED:
                downloadingDataType = "Free issue ";
                break;

            case FFREEDET:
                downloadingDataType = "Free issue ";
                break;

            case FFREEDEB:
                downloadingDataType = "Free issue ";
                break;

            case FITENRDET:
                downloadingDataType = "Itinerary ";
                break;

            case FITENRHED:
                downloadingDataType = "Itinerary";
                break;

            case FITEDEBDET:
                downloadingDataType = "Itinerary";
                break;

            case FITEMPRI:
                downloadingDataType = "Item Price";
                break;

            case FITEMS:
                downloadingDataType = " Item ";
                break;

            case FDEBTOR:
                downloadingDataType = " Customer ";
                break;

            case FCONTROL:
                downloadingDataType = " Company Details";
                break;

            case FCOMPANYSETTING:
                downloadingDataType = " Company Details";
                break;

            case FAREA:
                downloadingDataType = "Area";
                break;

            case FLOCATIONS:
                downloadingDataType = "Locations";
                break;

            case FCOMPANYBRANCH:
                downloadingDataType = "111";
                break;

            case FSALREP:
                downloadingDataType = " Sales Representative";
                break;

            case FREASON:
                downloadingDataType = " Reason ";
                break;

            case FROUTE:
                downloadingDataType = "Route";
                break;

            case FBANK:
                downloadingDataType = "111";
                break;

            case FDDBNOTE:
                downloadingDataType = "Outstanding";
                break;

            case FEXPENSE:
                downloadingDataType = "Expense";
                break;

            case FTOWN:
                downloadingDataType = "111";
                break;

            case FMERCH:
                downloadingDataType = "111";
                break;

            case FROUTEDET:
                downloadingDataType = "111";
                break;

            case FTRGCAPUL:
                downloadingDataType = "111";
                break;

            case FTYPE:
                downloadingDataType = "111";
                break;

            case FSUBBRAND:
                downloadingDataType = "111";
                break;

            case FGROUP:
                downloadingDataType = "111";
                break;

            case FSKU:
                downloadingDataType = "111";
                break;

            case FBRAND:
                downloadingDataType = "111";
                break;

            case FDEALER:
                downloadingDataType = "111";
                break;

            case FDISCDEB:
                downloadingDataType = "111";
                break;

            case FDISCDET:
                downloadingDataType = "111";
                break;

            case FDISCSLAB:
                downloadingDataType = "111";
                break;

            case FDISCHED:
                downloadingDataType = "111";
                break;

            case FFREEITEM:
                downloadingDataType = "111";
                break;

            case FFREEMSLAB:
                downloadingDataType = "111";
                break;

            case FDISCVHED:
                downloadingDataType = "111";
                break;

            case FDISCVDET:
                downloadingDataType = "111";
                break;
            case FDISCVDEB:
                downloadingDataType = "111";
                break;

            case FINVHEDL3:
                downloadingDataType = "111";
                break;

            case FINVDETL3:
                downloadingDataType = "111";
                break;
            case FDSCHHED:
                downloadingDataType = "111";
                break;
            case FDSCHDET:
                downloadingDataType = "111";
                break;
            case FSIZECOMB:
                downloadingDataType = "111";
                break;
            case FSIZEIN:
                downloadingDataType = "111";
                break;
            case FCRCOMB:
                downloadingDataType = "111";
                break;
            case FTERMS:
                downloadingDataType = "111";
                break;
            case FTAX:
                downloadingDataType = "111";
                break;
            case FTAXHED:
                downloadingDataType = "111";
                break;
            case FTAXDET:
                downloadingDataType = "111";
                break;

            case FSTKIN:
                downloadingDataType = "111";
                break;

            case FDEBTAX:
                downloadingDataType = "111";
                break;

            // ---------------------------------------- nuwan 18/08/2018 --------------------------------

            case FITTOURDISC:
                downloadingDataType = "111";
                break;


            case FAPPRORDHED:
                downloadingDataType = "222";
                break;


            case FDISTRICT:
                downloadingDataType = "111";
                break;

            // ---------------------------------------- MMS 06/11/2019 --------------------------------
            case FOTHERTRANS:
                downloadingDataType = "OtherTransactions";
                break;
            // ---------------------------------------- Kaveesha 29/07-2020 --------------------------------
            case FTARGETDET:
                downloadingDataType = "111";
                break;
            case FACHIEVEMENT:
                downloadingDataType = "111";
                break;
            case INVOICESALETM:
                downloadingDataType = "111";
                break;
            case INVOICESALEPM:
                downloadingDataType = "111";
                break;

            default:
                break;
        }
    }

    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new CustomProgressDialog(context);
        progressDialog.setCancelable(false);
        // progressDialog.setTitle("Downloading...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        String resultStr = "";

        int recordCount = 0;
        publishProgress(recordCount);

        try {

            URL json = new URL(ConnectionURL + downLoadURL);

            URLConnection jc = json.openConnection();
            BufferedReader readerfdblist = new BufferedReader(new InputStreamReader(jc.getInputStream()));

            String line = readerfdblist.readLine();
            resultStr = line;
            JSONObject jsonResponse = new JSONObject(line);

            switch (taskType) {
                case DATABASENAME:
                    // your work here
                    break;

                case FTARGETDET: {

                    JSONArray jsonArray = jsonResponse.getJSONArray("FRepTrgDetResult");
                    ArrayList<TargetDet> list = new ArrayList<TargetDet>();
                    Log.v("FT", "Array Length Target Db :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    new TargetDetDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObject = (JSONObject) jsonArray.get(i);
                        TargetDet tDet = new TargetDet();

                        tDet.setMonthn(jObject.getString("Monthn"));
                        tDet.setRefNo(jObject.getString("RefNo"));
                        tDet.setTrAmt(jObject.getString("TrAmt"));
                        tDet.setYearT(jObject.getString("YearT"));
                        tDet.setTxndate(jObject.getString("txndate"));

                        list.add(tDet);
                        ++recordCount;
                        publishProgress(recordCount);
                    }
                    new TargetDetDS(context).InsertOrReplaceTarget(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Target Det Info");
                }
                break;
                case INVOICESALETM: {

                    String invoiceSaleTM = jsonResponse.getString("invoiceSaleResult");
                    SharedPref.getInstance(context).setInvoiceSaleTM(invoiceSaleTM);
                }
                break;
                case INVOICESALEPM: {

                    String invoiceSalePM = jsonResponse.getString("invoiceSaleResult");
                    SharedPref.getInstance(context).setInvoiceSalePM(invoiceSalePM);
                }
                break;
                case FITEMLOC: {
                    JSONArray jsonArray = jsonResponse.getJSONArray("fItemLocResult");
                    ArrayList<ItemLoc> list = new ArrayList<ItemLoc>();

                    totalRecords = jsonArray.length();

                    new ItemLocDS(context).deleteAllItemLoc();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObject = (JSONObject) jsonArray.get(i);
                        ItemLoc loc = new ItemLoc();

                        loc.setFITEMLOC_ITEM_CODE(jObject.getString("ItemCode"));
                        loc.setFITEMLOC_LOC_CODE(jObject.getString("LocCode"));
                        loc.setFITEMLOC_QOH(jObject.getString("QOH"));
                        loc.setFITEMLOC_RECORD_ID(jObject.getString("RecordId"));

                        list.add(loc);
                        ++recordCount;
                        publishProgress(recordCount);
                    }
                    new ItemLocDS(context).InsertOrReplaceItemLoc(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Item Locations");
                }

                break;

                case FFREESLAB: {
                    JSONArray jsonArray = jsonResponse.getJSONArray("FfreeslabResult");
                    ArrayList<FreeSlab> list = new ArrayList<FreeSlab>();
                    totalRecords = jsonArray.length();

                    new FreeSlabDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObject = (JSONObject) jsonArray.get(i);
                        FreeSlab slab = new FreeSlab();

                        slab.setFFREESLAB_FITEM_CODE(jObject.getString("Fitemcode"));
                        slab.setFFREESLAB_REFNO(jObject.getString("Refno"));
                        slab.setFFREESLAB_QTY_F(jObject.getString("Qtyf"));
                        slab.setFFREESLAB_QTY_T(jObject.getString("Qtyt"));
                        slab.setFFREESLAB_FREE_QTY(jObject.getString("Freeqty"));

                        list.add(slab);
                        ++recordCount;
                        publishProgress(recordCount);

                    }

                    new FreeSlabDS(context).createOrUpdateFreeSlab(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Free Issue slab info");
                }
                break;

                case FFREEHED: {
                    JSONArray jsonArray = jsonResponse.getJSONArray("FfreehedResult");
                    ArrayList<FreeHed> list = new ArrayList<FreeHed>();
                    totalRecords = jsonArray.length();
                    Log.v(TAG, "Array Length : FreeHed " + jsonArray.length());

                    new FreeHedDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        FreeHed hed = new FreeHed();

                        hed.setFFREEHED_REFNO(jObject.getString("Refno"));
                        hed.setFFREEHED_TXNDATE(jObject.getString("Txndate"));
                        hed.setFFREEHED_DISC_DESC(jObject.getString("DiscDesc"));
                        hed.setFFREEHED_PRIORITY(jObject.getString("Priority"));
                        hed.setFFREEHED_VDATEF(jObject.getString("Vdatef"));
                        hed.setFFREEHED_VDATET(jObject.getString("Vdatet"));
                        hed.setFFREEHED_REMARKS(jObject.getString("Remarks"));
                        hed.setFFREEHED_ITEM_QTY(jObject.getString("ItemQty"));
                        hed.setFFREEHED_FREE_IT_QTY(jObject.getString("FreeItQty"));
                        hed.setFFREEHED_FTYPE(jObject.getString("Ftype"));
                        hed.setFFREEHED_COSTCODE(jObject.getString("CostCode"));

                        list.add(hed);
                        ++recordCount;
                        publishProgress(recordCount);

                    }

                    new FreeHedDS(context).createOrUpdateFreeHed(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Free Issue header info");

                }
                break;

                case FFREEDET: {
                    JSONArray jsonArray = jsonResponse.getJSONArray("FfreedetResult");
                    ArrayList<FreeDet> list = new ArrayList<FreeDet>();
                    totalRecords = jsonArray.length();
                    Log.v(TAG, "Array Length : FreeDet " + jsonArray.length());

                    new FreeDetDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObject = (JSONObject) jsonArray.get(i);
                        FreeDet det = new FreeDet();
                        det.setFFREEDET_REFNO(jObject.getString("Refno"));
                        det.setFFREEDET_ITEM_CODE(jObject.getString("Itemcode"));
                        list.add(det);
                        ++recordCount;
                        publishProgress(recordCount);
                    }
                    new FreeDetDS(context).createOrUpdateFreeDet(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Free Issue det info");

                }
                break;

                case FFREEDEB:
                    // downloadingDataType = "Ffreedeb";
                {
                    JSONArray jsonArray = jsonResponse.getJSONArray("FfreedebResult");
                    ArrayList<FreeDeb> list = new ArrayList<FreeDeb>();
                    totalRecords = jsonArray.length();
                    Log.v(TAG, "Array Length : FreeDeb " + jsonArray.length());

                    new FreeDebDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        FreeDeb deb = new FreeDeb();

                        deb.setFFREEDEB_REFNO(jObject.getString("Refno"));
                        deb.setFFREEDEB_DEB_CODE(jObject.getString("Debcode"));
                        deb.setFFREEDEB_ADD_USER("");
                        deb.setFFREEDEB_ADD_DATE("");
                        deb.setFFREEDEB_ADD_MACH("");
                        deb.setFFREEDEB_TIMESTAMP_COLUMN("");
                        list.add(deb);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new FreeDebDS(context).createOrUpdateFreeDeb(list) > 0) {
                        Log.v("createOrUpdateFreeDeb", "Result : FreeDeb Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Free Issue debtor info");

                }
                break;

                case FITENRDET:
                    // downloadingDataType = "FItenrDet";
                {
                    ArrayList<FItenrDet> list = new ArrayList<FItenrDet>();

                    JSONArray jsonArray = jsonResponse.getJSONArray("fItenrDetResult");

                    Log.v(TAG, "Array Length FItenrDet DB :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    new FItenrDetDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        FItenrDet fItenrDet = new FItenrDet();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);
                        fItenrDet.setFITENRDET_NO_OUTLET(jObject.getString("NoOutlet"));
                        fItenrDet.setFITENRDET_NO_SHCUCAL(jObject.getString("NoShcuCal"));
                        fItenrDet.setFITENRDET_RD_TARGET(jObject.getString("RDTarget"));
                        fItenrDet.setFITENRDET_REF_NO(jObject.getString("RefNo"));
                        fItenrDet.setFITENRDET_REMARKS(jObject.getString("Remarks"));
                        fItenrDet.setFITENRDET_ROUTE_CODE(jObject.getString("RouteCode"));
                        fItenrDet.setFITENRDET_TXN_DATE(jObject.getString("TxnDate"));

                        list.add(fItenrDet);
                        ++recordCount;
                        publishProgress(recordCount);
                    }
                    new FItenrDetDS(context).createOrUpdateFItenrDet(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Iteanery info");

                }
                break;

                case FITENRHED:
                    // downloadingDataType = "fItenrHed";
                {
                    ArrayList<FItenrHed> list = new ArrayList<FItenrHed>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fItenrHedResult");
                    Log.v(TAG, "Array Length FItenrHed DB :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    new FItenrHedDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        FItenrHed fItenrHed = new FItenrHed();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);
                        fItenrHed.setFITENRHED_COST_CODE(jObject.getString("CostCode"));
                        fItenrHed.setFITENRHED_DEAL_CODE(jObject.getString("DealCode"));
                        fItenrHed.setFITENRHED_MONTH(jObject.getString("Month"));
                        fItenrHed.setFITENRHED_REF_NO(jObject.getString("RefNo"));
                        fItenrHed.setFITENRHED_REMARKS1(jObject.getString("Remarks1"));
                        fItenrHed.setFITENRHED_REP_CODE(jObject.getString("RepCode"));
                        fItenrHed.setFITENRHED_YEAR(jObject.getString("Year"));
                        list.add(fItenrHed);
                        ++recordCount;
                        publishProgress(recordCount);
                    }
                    new FItenrHedDS(context).createOrUpdateFItenrHed(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Iteanery header info");

                }
                break;

                case FITEDEBDET:
                    // downloadingDataType = "fIteDebDet";
                {
                    ArrayList<FIteDebDet> list = new ArrayList<FIteDebDet>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fIteDebDetResult");
                    Log.v(TAG, "Array Length FIteDebDet DB :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    new FIteDebDetDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        FIteDebDet fIteDebDet = new FIteDebDet();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        fIteDebDet.setFITEDEBDET_DEB_CODE(jObject.getString("DebCode"));
                        fIteDebDet.setFITEDEBDET_REF_NO(jObject.getString("RefNo"));
                        fIteDebDet.setFITEDEBDET_ROUTE_CODE(jObject.getString("RouteCode"));
                        fIteDebDet.setFITEDEBDET_TXN_DATE(jObject.getString("TxnDate"));

                        list.add(fIteDebDet);
                        ++recordCount;
                        publishProgress(recordCount);
                    }
                    new FIteDebDetDS(context).createOrUpdateFIteDebDet(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Iteanary debtor info");

                }
                break;

                case FITEMPRI: {
                    ArrayList<ItemPri> list = new ArrayList<ItemPri>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fItemPriResult");
                    Log.v(TAG, "Array Length ItemPri DB :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    new ItemPriDS(context).deleteAllItemPri();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        ItemPri pri = new ItemPri();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        pri.setFITEMPRI_ADD_MACH(jObject.getString("AddMach"));
                        pri.setFITEMPRI_ADD_USER(jObject.getString("AddUser"));
                        pri.setFITEMPRI_ITEM_CODE(jObject.getString("ItemCode"));
                        pri.setFITEMPRI_PRICE(jObject.getString("Price"));
                        pri.setFITEMPRI_PRIL_CODE(jObject.getString("PrilCode"));
                        pri.setFITEMPRI_TXN_MACH(jObject.getString("TxnMach"));
                        pri.setFITEMPRI_TXN_USER(jObject.getString("Txnuser"));

                        list.add(pri);
                        ++recordCount;
                        publishProgress(recordCount);

//                        Log.d("FITEM_PRI", "PRODUCT_PRICE" + jObject.getString("Price") + ", " + jObject.getString("ItemCode"));

                    }
                    new ItemPriDS(context).InsertOrReplaceItemPri(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Item price info");

                }
                break;

                case FITEMS:
                    // downloadingDataType ="fItems";
                {
                    ArrayList<Items> list = new ArrayList<Items>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fItemsResult");
                    Log.v(TAG, "Array Length Items DB :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    new ItemsDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        Items itm = new Items();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        itm.setFITEM_AVGPRICE(jObject.getString("AvgPrice"));
                        itm.setFITEM_BRANDCODE(jObject.getString("BrandCode"));
                        itm.setFITEM_GROUPCODE(jObject.getString("GroupCode"));
                        itm.setFITEM_ITEM_CODE(jObject.getString("ItemCode"));
                        itm.setFITEM_ITEM_NAME(jObject.getString("ItemName"));
                        itm.setFITEM_ITEMSTATUS(jObject.getString("ItemStatus"));
                        itm.setFITEM_PRILCODE(jObject.getString("PrilCode"));
                        itm.setFITEM_TYPECODE(jObject.getString("TypeCode"));
                        itm.setFITEM_UNITCODE(jObject.getString("UnitCode"));
                        itm.setFITEM_VENPCODE(jObject.getString("VenPcode"));
                        itm.setFITEM_NOUCASE(jObject.getString("NOUCase"));
                        itm.setFITEM_REORDER_LVL(jObject.getString("ReOrderLvl"));
                        itm.setFITEM_REORDER_QTY(jObject.getString("ReOrderQty"));
                        itm.setFITEM_TAXCOMCODE(jObject.getString("TaxComCode"));

                        list.add(itm);
                        ++recordCount;
                        publishProgress(recordCount);

//                        Log.d("FITEMs", "PRODUCT_PRICE" + jObject.getString("AvgPrice") + ", " + jObject.getString("ItemCode"));
                    }

                    new ItemsDS(context).InsertOrReplaceItems(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Items info");

                }
                break;

                case FDEBTOR: {
                    ArrayList<Debtor> list = new ArrayList<Debtor>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FdebtorResult");
                    Log.v(TAG, "Array Length Debtor DB :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    new DebtorDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        Debtor aDebtor = new Debtor();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        aDebtor.setFDEBTOR_ADD1(jObject.getString("DebAdd1"));
                        aDebtor.setFDEBTOR_ADD2(jObject.getString("DebAdd2"));
                        aDebtor.setFDEBTOR_ADD3(jObject.getString("DebAdd3"));
                        aDebtor.setFDEBTOR_AREA_CODE(jObject.getString("AreaCode"));
                        aDebtor.setFDEBTOR_CHK_CRD_LIMIT(jObject.getString("ChkCrdLmt"));
                        aDebtor.setFDEBTOR_CHK_CRD_PRD(jObject.getString("ChkCrdPrd"));
                        aDebtor.setFDEBTOR_CODE(jObject.getString("DebCode"));
                        aDebtor.setFDEBTOR_CRD_LIMIT(jObject.getString("CrdLimit"));
                        aDebtor.setFDEBTOR_CRD_PERIOD(jObject.getString("CrdPeriod"));
                        aDebtor.setFDEBTOR_DBGR_CODE(jObject.getString("DbGrCode"));
                        aDebtor.setFDEBTOR_EMAIL(jObject.getString("DebEMail"));
                        aDebtor.setFDEBTOR_MOB(jObject.getString("DebMob"));
                        aDebtor.setFDEBTOR_NAME(jObject.getString("DebName"));
                        aDebtor.setFDEBTOR_PRILLCODE(jObject.getString("PrilCode"));
                        aDebtor.setFDEBTOR_RANK_CODE(jObject.getString("RankCode"));
                        aDebtor.setFDEBTOR_STATUS(jObject.getString("Status"));
                        aDebtor.setFDEBTOR_TAX_REG(jObject.getString("TaxReg"));
                        aDebtor.setFDEBTOR_TELE(jObject.getString("DebTele"));
                        aDebtor.setFDEBTOR_TOWN_CODE(jObject.getString("TownCode"));
                        aDebtor.setFDEBTOR_REPCODE(jObject.getString("RepCode"));
                        aDebtor.setFDEBTOR_NIC(jObject.getString("NIC"));
                        aDebtor.setFDEBTOR_BIS_REG(jObject.getString("BisRegNo"));
                        aDebtor.setFDEBTOR_LONGITUDE(jObject.getString("Longitude"));
                        aDebtor.setFDEBTOR_LATITUDE(jObject.getString("Latitude"));

                        list.add(aDebtor);
                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    /* Update to database */
                    new DebtorDS(context).InsertOrReplaceDebtor(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Debtor info");

                }
                break;

                case FCONTROL: {
                    ArrayList<Control> list = new ArrayList<Control>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fControlResult");
                    Log.v(TAG, "Array Length Control DB :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    String Type = "0";
                    for (int i = 0; i < jsonArray.length(); i++) {

                        Control ctrl = new Control();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        ctrl.setFCONTROL_COM_ADD1(jObject.getString("ComAdd1"));
                        ctrl.setFCONTROL_COM_ADD2(jObject.getString("ComAdd2"));
                        ctrl.setFCONTROL_COM_ADD3(jObject.getString("ComAdd3"));
                        ctrl.setFCONTROL_COM_NAME(jObject.getString("ComName"));
                        ctrl.setFCONTROL_BASECUR(jObject.getString("basecur"));
                        ctrl.setFCONTROL_COM_EMAIL(jObject.getString("comemail"));
                        ctrl.setFCONTROL_COM_TEL1(jObject.getString("comtel1"));
                        ctrl.setFCONTROL_COM_TEL2(jObject.getString("comtel2"));
                        ctrl.setFCONTROL_COM_FAX(jObject.getString("comfax1"));
                        ctrl.setFCONTROL_COM_WEB(jObject.getString("comweb"));
                        ctrl.setFCONTROL_CONAGE1(jObject.getString("conage1"));
                        ctrl.setFCONTROL_CONAGE2(jObject.getString("conage2"));
                        ctrl.setFCONTROL_CONAGE3(jObject.getString("conage3"));
                        ctrl.setFCONTROL_CONAGE4(jObject.getString("conage4"));
                        ctrl.setFCONTROL_CONAGE5(jObject.getString("conage5"));
                        ctrl.setFCONTROL_CONAGE6(jObject.getString("conage6"));
                        ctrl.setFCONTROL_CONAGE7(jObject.getString("conage7"));
                        ctrl.setFCONTROL_CONAGE8(jObject.getString("conage8"));
                        ctrl.setFCONTROL_CONAGE9(jObject.getString("conage9"));
                        ctrl.setFCONTROL_CONAGE10(jObject.getString("conage10"));
                        ctrl.setFCONTROL_CONAGE11(jObject.getString("conage11"));
                        ctrl.setFCONTROL_CONAGE12(jObject.getString("conage12"));
                        ctrl.setFCONTROL_CONAGE13(jObject.getString("conage13"));
                        ctrl.setFCONTROL_CONAGE14(jObject.getString("conage14"));
                        ctrl.setFCONTROL_COM_REGNO(jObject.getString("comRegNo"));
                        ctrl.setFCONTROL_SYSTYPE("");
                        ctrl.setFCONTROL_COMDISPER(jObject.getString("DisPer"));
                        // ctrl.setFCONTROL_VATCMTAXNO(jObject.getString("vatcmtaxno"));

                        // Type = jObject.getString("SysType");

                        list.add(ctrl);

                        ++recordCount;
                        publishProgress(recordCount);
                    }

                    ControlDS ds = new ControlDS(context);
                    if (ds.createOrUpdateFControl(list) > 0) {

                        Log.v("createOrUpdateControl", "Result : Control Data Inserted successfully");

                        return "" + Type;
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Company info");

                }
                break;

                case FCOMPANYSETTING: {

                    ArrayList<CompanySetting> list = new ArrayList<CompanySetting>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fCompanySettingResult");
                    Log.v(TAG, "Array Length CompanySetting DB :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    new CompanySettingDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        CompanySetting setting = new CompanySetting();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        setting.setFCOMPANYSETTING_CHAR_VAL(jObject.getString("cCharVal"));
                        setting.setFCOMPANYSETTING_COMPANY_CODE(jObject.getString("cCompanyCode"));
                        setting.setFCOMPANYSETTING_LOCATION_CHAR(jObject.getString("cLocationChar"));
                        setting.setFCOMPANYSETTING_REMARKS(jObject.getString("cRemarks"));
                        setting.setFCOMPANYSETTING_GRP(jObject.getString("cSettingGrp"));
                        setting.setFCOMPANYSETTING_SETTINGS_CODE(jObject.getString("cSettingsCode"));
                        setting.setFCOMPANYSETTING_NUM_VAL(jObject.getString("nNumVal"));
                        setting.setFCOMPANYSETTING_TYPE(jObject.getString("nType"));

                        list.add(setting);
                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    new CompanySettingDS(context).createOrUpdateFCompanySetting(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Setting info");

                }

                break;

                case FAREA: {
                    ArrayList<Area> list = new ArrayList<Area>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fAreaResult");
                    Log.v(TAG, "Array Length Area DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new AreaDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Area area = new Area();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        area.setFAREA_ADD_MACH(jObject.getString("AddMach"));
                        area.setFAREA_ADD_USER(jObject.getString("AddUser"));
                        area.setFAREA_AREA_CODE(jObject.getString("AreaCode"));
                        area.setFAREA_AREA_NAME(jObject.getString("AreaName"));
                        area.setFAREA_DEAL_CODE(jObject.getString("DealCode"));
                        area.setFAREA_REQ_CODE(jObject.getString("RegCode"));

                        list.add(area);
                        ++recordCount;
                        publishProgress(recordCount);
                    }
                    new AreaDS(context).createOrUpdateArea(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Area info");


                }
                break;

                case FLOCATIONS: {
                    ArrayList<Locations> list = new ArrayList<Locations>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fLocationsResult");
                    Log.v(TAG, "Array Length fLocations DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new LocationsDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Locations locations = new Locations();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        locations.setFLOCATIONS_ADD_MACH(jObject.getString("AddMach"));
                        locations.setFLOCATIONS_ADD_USER(jObject.getString("AddUser"));
                        locations.setFLOCATIONS_LOC_CODE(jObject.getString("LocCode"));
                        locations.setFLOCATIONS_LOC_NAME(jObject.getString("LocName"));
                        locations.setFLOCATIONS_LOC_T_CODE(jObject.getString("LoctCode"));
                        locations.setFLOCATIONS_REP_CODE(jObject.getString("RepCode"));

                        list.add(locations);
                        ++recordCount;
                        publishProgress(recordCount);
                    }
                    new LocationsDS(context).createOrUpdateFLocations(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Location info");

                }
                break;

                case FCOMPANYBRANCH: {
                    ArrayList<CompanyBranch> list = new ArrayList<CompanyBranch>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FCompanyBranchResult");
                    Log.v(TAG, "Array Length CompanyBranch DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new CompanyBranchDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        CompanyBranch branch = new CompanyBranch();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        branch.setFCOMPANYBRANCH_BRANCH_CODE(jObject.getString("BranchCode"));
                        branch.setFCOMPANYBRANCH_CSETTINGS_CODE(jObject.getString("cSettingsCode"));
                        branch.setFCOMPANYBRANCH_NNUM_VAL(jObject.getString("nNumVal"));
                        branch.setNYEAR(jObject.getString("nYear"));
                        branch.setNMONTH(jObject.getString("nMonth"));

                        list.add(branch);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    new CompanyBranchDS(context).createOrUpdateFCompanyBranch(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Reference no info");

                }
                break;

                case FSALREP: {
                    ArrayList<SalRep> list = new ArrayList<SalRep>();
                    JSONArray jsonArrayfSalRep = jsonResponse.getJSONArray("fSalRepResult");
                    Log.v(TAG, "Array Length fSalRep :" + jsonArrayfSalRep.length());
                    totalRecords = jsonArrayfSalRep.length();

                    for (int i = 0; i < jsonArrayfSalRep.length(); i++) {

                        SalRep rep = new SalRep();

                        JSONObject jObject = (JSONObject) jsonArrayfSalRep.get(i);

                        rep.setADDMACH(jObject.getString("AddMach"));
                        rep.setADDUSER(jObject.getString("AddUser"));
                        rep.setEMAIL(jObject.getString("RepEMail"));
                        rep.setMACID(jObject.getString("macid"));
                        rep.setMOBILE(jObject.getString("RepMobil"));
                        rep.setNAME(jObject.getString("RepName"));
                        rep.setPASSWORD(jObject.getString("Password"));
                        rep.setPREFIX(jObject.getString("RepPrefix"));
                        rep.setRECORDID(jObject.getString("RecordId"));
                        rep.setREPCODE(jObject.getString("RepCode"));
                        rep.setREPID(jObject.getString("RepIdNo"));
                        rep.setSTATUS(jObject.getString("Status"));
                        rep.setTELE(jObject.getString("RepTele"));

                        // log code get from root header for the lanka hardware 09/08/2018 ---------------------- nuwan --------------------------

//                        rep.setLOCCODE(jObject.getString("LocCode"));

                        // fSalRep loc code required for all customer in pre sales

                        rep.setLOCCODE(jObject.getString("LocCode"));

                        list.add(rep);
                        ++recordCount;
                        publishProgress(recordCount);
                    }

                    new SalRepDS(context).createOrUpdateSalRep(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Sales representative info");

                    /*
                     * return salrep array length to check if MAC is registered to
                     * SettingsActivity OnTaskCompleted
                     */
                    return String.valueOf(jsonArrayfSalRep.length());

                }
                case FREASON: {
                    ArrayList<Reason> list = new ArrayList<Reason>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fReasonResult");
                    Log.v(TAG, "Array Length Reason DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new ReasonDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Reason reason = new Reason();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        reason.setFREASON_ADD_DATE(jObject.getString("AddDate"));
                        reason.setFREASON_ADD_MACH(jObject.getString("AddMach"));
                        reason.setFREASON_ADD_USER(jObject.getString("AddUser"));
                        reason.setFREASON_CODE(jObject.getString("ReaCode"));
                        reason.setFREASON_NAME(jObject.getString("ReaName"));
                        reason.setFREASON_REATCODE(jObject.getString("ReaTcode"));

                        list.add(reason);

                        ++recordCount;
                        publishProgress(recordCount);

                    }

                    new ReasonDS(context).createOrUpdateFReason(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Reason info");

                }
                break;

                case FROUTE: {
                    ArrayList<Route> list = new ArrayList<Route>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fRouteResult");
                    Log.v(TAG, "Array Length Route DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new RouteDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Route route = new Route();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        route.setFROUTE_ADDDATE(jObject.getString("AddDate"));
                        route.setFROUTE_ADD_MACH(jObject.getString("AddMach"));
                        route.setFROUTE_ADD_USER(jObject.getString("AddUser"));
                        route.setFROUTE_AREACODE(jObject.getString("AreaCode"));
                        // route.setFROUTE_DEALCODE(jObject.getString("DealCode"));
                        route.setFROUTE_FREQNO(jObject.getString("FreqNo"));
                        route.setFROUTE_KM(jObject.getString("Km"));
                        route.setFROUTE_MINPROCALL(jObject.getString("MinProcall"));
                        route.setFROUTE_RDALORATE(jObject.getString("RDAloRate"));
                        route.setFROUTE_RDTARGET(jObject.getString("RDTarget"));
                        route.setFROUTE_REMARKS(jObject.getString("Remarks"));
                        route.setFROUTE_REPCODE(jObject.getString("RepCode"));
                        route.setFROUTE_ROUTECODE(jObject.getString("RouteCode"));
                        route.setFROUTE_ROUTE_NAME(jObject.getString("RouteName"));
                        route.setFROUTE_STATUS(jObject.getString("Status"));
                        route.setFROUTE_TONNAGE(jObject.getString("Tonnage"));

                        list.add(route);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new RouteDS(context).createOrUpdateFRoute(list) > 0) {
                        Log.v("CreateOrUpdateFRoute", "Result : Route Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Route info");

                }
                break;

                case FBANK:
                    // downloadingDataType="fBank";
                {
                    ArrayList<Bank> list = new ArrayList<Bank>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fbankResult");
                    Log.v(TAG, "Array Length Bank DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new BankDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Bank bank = new Bank();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        bank.setFBANK_BANK_CODE(jObject.getString("Bankcode"));
                        bank.setFBANK_BANK_NAME(jObject.getString("Bankname"));
                        bank.setFBANK_BANK_ACC_NO(jObject.getString("Bankaccno"));
                        bank.setFBANK_BRANCH(jObject.getString("Branch"));
                        bank.setFBANK_ADD1(jObject.getString("Bankadd1"));
                        bank.setFBANK_ADD2(jObject.getString("Bankadd2"));
                        bank.setFBANK_ADD_DATE(jObject.getString("AddDate"));
                        bank.setFBANK_ADD_MACH(jObject.getString("AddMach"));
                        bank.setFBANK_ADD_USER(jObject.getString("AddUser"));

                        list.add(bank);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new BankDS(context).createOrUpdateBank(list) > 0) {
                        Log.v("CreateOrUpdateFbank", "Result : bank Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Bank info");

                }

                break;

                case FDDBNOTE:
                    // downloadingDataType="fDdbNote";
                {
                    ArrayList<FDDbNote> list = new ArrayList<FDDbNote>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fDdbNoteWithConditionResult");
                    Log.v(TAG, "Array Length fDdbNote DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();
                    new FDDbNoteDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FDDbNote fdDbNote = new FDDbNote();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        fdDbNote.setFDDBNOTE_ADD_DATE(jObject.getString("AddDate"));
                        fdDbNote.setFDDBNOTE_ADD_MACH(jObject.getString("AddMach"));
                        fdDbNote.setFDDBNOTE_ADD_USER(jObject.getString("AddUser"));
                        fdDbNote.setFDDBNOTE_AMT(jObject.getString("Amt"));
                        fdDbNote.setFDDBNOTE_B_AMT(jObject.getString("BAmt"));
                        fdDbNote.setFDDBNOTE_B_TAX_AMT(jObject.getString("BTaxAmt"));
                        fdDbNote.setFDDBNOTE_CUR_CODE(jObject.getString("CurCode"));
                        fdDbNote.setFDDBNOTE_CUR_RATE(jObject.getString("CurRate"));
                        fdDbNote.setFDDBNOTE_DEB_CODE(jObject.getString("DebCode"));
                        fdDbNote.setFDDBNOTE_MANU_REF(jObject.getString("ManuRef"));
                        fdDbNote.setFDDBNOTE_OV_PAY_AMT(jObject.getString("OvPayAmt"));
                        fdDbNote.setFDDBNOTE_REF_INV(jObject.getString("RefInv"));
                        fdDbNote.setFDDBNOTE_REFNO(jObject.getString("RefNo"));
                        fdDbNote.setFDDBNOTE_REFNO1(jObject.getString("RefNo1"));
                        fdDbNote.setFDDBNOTE_REMARKS(jObject.getString("Remarks"));
                        fdDbNote.setFDDBNOTE_REP_CODE(jObject.getString("RepCode"));
                        fdDbNote.setFDDBNOTE_SALE_REF_NO(jObject.getString("SaleRefNo"));
                        fdDbNote.setFDDBNOTE_TAX_AMT(jObject.getString("TaxAmt"));
                        fdDbNote.setFDDBNOTE_TAX_COM_CODE(jObject.getString("TaxComCode"));
                        fdDbNote.setFDDBNOTE_TOT_BAL(jObject.getString("TotBal"));
                        fdDbNote.setFDDBNOTE_TOT_BAL1(jObject.getString("TotBal1"));
                        fdDbNote.setFDDBNOTE_TXN_DATE(jObject.getString("TxnDate"));
                        fdDbNote.setFDDBNOTE_TXN_TYPE(jObject.getString("TxnType"));

                        list.add(fdDbNote);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new FDDbNoteDS(context).createOrUpdateFDDbNote(list) > 0) {
                        Log.v("createOrUpdateFDDbNote", "Result : FDDBnote Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Outstanding info");

                }
                break;

                case FEXPENSE:
                    // downloadingDataType="fExpense";
                {
                    ArrayList<Expense> list = new ArrayList<Expense>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fExpenseResult");
                    Log.v(TAG, "Array Length Expense DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new ExpenseDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Expense expense = new Expense();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        expense.setFEXPENSE_ADD_DATE(jObject.getString("AddDate"));
                        expense.setFEXPENSE_ADD_MACH(jObject.getString("AddMach"));
                        expense.setFEXPENSE_ADD_USER(jObject.getString("AddUser"));
                        expense.setFEXPENSE_CODE(jObject.getString("ExpCode"));
                        // expense.setFEXPENSE_GRP_CODE(jObject.getString("ExpGrpCode"));
                        expense.setFEXPENSE_NAME(jObject.getString("ExpName"));
                        expense.setFEXPENSE_STATUS(jObject.getString("Status"));

                        list.add(expense);

                        ++recordCount;
                        publishProgress(recordCount);

                    }

                    if (new ExpenseDS(context).createOrUpdateFExpense(list) > 0) {
                        Log.v("CreateOrUpdateFexpense", "Result : expense Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Expense info");

                }
                break;

                case FTOWN:
                    // downloadingDataType="fTown";
                {
                    ArrayList<Town> list = new ArrayList<Town>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fTownResult");
                    Log.v(TAG, "Array Length Route DB :" + jsonArray.length());

                    new TownDS(context).deleteAll();

                    totalRecords = jsonArray.length();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Town town = new Town();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        town.setFTOWN_ADDDATE(jObject.getString("AddDate"));
                        town.setFTOWN_ADD_MACH(jObject.getString("AddMach"));
                        town.setFTOWN_ADD_USER(jObject.getString("AddUser"));
                        town.setFTOWN_DISTR_CODE(jObject.getString("DistrCode"));
                        town.setFTOWN_CODE(jObject.getString("TownCode"));
                        town.setFTOWN_NAME(jObject.getString("TownName"));

                        list.add(town);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new TownDS(context).createOrUpdateTown(list) > 0) {
                        Log.v("CreateOrUpdateFRoute", "Result : Route Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Town info");

                }
                break;

                case FMERCH:
                    // downloadingDataType="FMerch";
                {
                    ArrayList<Merch> list = new ArrayList<Merch>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FMerchResult");
                    Log.v(TAG, "Array Length Merch DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new MerchDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Merch merch = new Merch();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        merch.setFMERCH_CODE(jObject.getString("MerchCode"));
                        merch.setFMERCH_NAME(jObject.getString("MerchName"));
                        merch.setFMERCH_ADD_USER(jObject.getString("AddUser"));
                        merch.setFMERCH_ADD_DATE(jObject.getString("AddDate"));
                        merch.setFMERCH_ADD_MACH(jObject.getString("AddMach"));
                        merch.setFMERCH_TIMESTAMP_COLUMN(jObject.getString("timestamp_column"));
                        list.add(merch);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new MerchDS(context).createOrUpdateFMerch(list) > 0) {
                        Log.v("CreateOrUpdateFmerch", "Result : merch Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Merch info");

                }
                break;

                case FROUTEDET:
                    // downloadingDataType ="fRouteDet";
                {
                    new RouteDetDS(context).deleteAll();
                    ArrayList<RouteDet> list = new ArrayList<RouteDet>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fRouteDetResult");
                    Log.v(TAG, "Array Length RouteDet DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        RouteDet routeDet = new RouteDet();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        routeDet.setFROUTEDET_DEB_CODE(jObject.getString("DebCode"));
                        routeDet.setFROUTEDET_ROUTE_CODE(jObject.getString("RouteCode"));
                        list.add(routeDet);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
//                    if (new RouteDetDS(context).InsertOrReplaceRouteDet(list) > 0) {
//                        Log.v("CreateOrUpdatefRouteDet", "Result : Route Data Inserted successfully");
//                    }
                    new RouteDetDS(context).InsertOrReplaceRouteDet(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Route det info");

                }
                break;

                case FTRGCAPUL:
                    downloadingDataType = "FTrgCapUL";
                    break;

                case FTYPE: {
                    ArrayList<Type> list = new ArrayList<Type>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fTypeResult");
                    Log.v(TAG, "Array Length Type DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new TypeDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Type type = new Type();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        type.setFTYPE_ADD_DATE(jObject.getString("AddDate"));
                        type.setFTYPE_ADD_MACH(jObject.getString("AddMach"));
                        type.setFTYPE_ADD_USER(jObject.getString("AddUser"));
                        type.setFTYPE_CODE(jObject.getString("TypeCode"));
                        type.setFTYPE_NAME(jObject.getString("TypeName"));
                        list.add(type);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new TypeDS(context).createOrUpdateType(list) > 0) {
                        Log.v("CreateOrUpdateFRoute", "Result : Route Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Type info");

                }
                break;

                case FSUBBRAND:
                    // downloadingDataType="fSubBrand";
                {
                    ArrayList<SubBrand> list = new ArrayList<SubBrand>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fSubBrandResult");
                    Log.v(TAG, "Array Length SubBrand DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new SubBrandDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        SubBrand subBrand = new SubBrand();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        subBrand.setFSUBBRAND_ADD_DATE(jObject.getString("AddDate"));
                        subBrand.setFSUBBRAND_ADD_MACH(jObject.getString("AddMach"));
                        subBrand.setFSUBBRAND_ADD_USER(jObject.getString("AddUser"));
                        subBrand.setFSUBBRAND_CODE(jObject.getString("SBrandCode"));
                        subBrand.setFSUBBRAND_NAME(jObject.getString("SBrandName"));

                        list.add(subBrand);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new SubBrandDS(context).createOrUpdateSubBrand(list) > 0) {
                        Log.v("CreateOrUpdateFsubBrand", "Result : subBrand Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Sub brand info");

                }
                break;

                case FGROUP:
                    // downloadingDataType="fGroup";
                {
                    ArrayList<Group> list = new ArrayList<Group>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fGroupResult");
                    Log.v(TAG, "Array Length Group DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new GroupDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Group group = new Group();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        group.setFGROUP_ADD_DATE(jObject.getString("AddDate"));
                        group.setFGROUP_ADD_MACH(jObject.getString("AddMach"));
                        group.setFGROUP_ADD_USER(jObject.getString("AddUser"));
                        group.setFGROUP_CODE(jObject.getString("GroupCode"));
                        group.setFGROUP_NAME(jObject.getString("GroupName"));

                        list.add(group);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new GroupDS(context).createOrUpdateGroup(list) > 0) {
                        Log.v("createOrUpdateGroup", "Result : group Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Group info");

                }
                break;

                case FSKU:
                    // downloadingDataType="FSKU";
                {
                    ArrayList<SKU> list = new ArrayList<SKU>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FSKUResult");
                    Log.v(TAG, "Array Length SKU DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new SkuDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        SKU sku = new SKU();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        sku.setFSKU_ADD_DATE(jObject.getString("AddDate"));
                        sku.setFSKU_ADD_MACH(jObject.getString("AddMach"));
                        sku.setFSKU_ADD_USER(jObject.getString("AddUser"));
                        sku.setFSKU_BRAND_CODE(jObject.getString("BrandCode"));
                        sku.setFSKU_GROUP_CODE(jObject.getString("GroupCode"));
                        sku.setFSKU_ITEM_STATUS(jObject.getString("ItemStatus"));
                        sku.setFSKU_MUST_SALE(jObject.getString("MustSale"));
                        sku.setFSKU_NOUCASE(jObject.getString("NOUCase"));
                        sku.setFSKU_ORDSEQ(jObject.getString("OrdSeq"));
                        sku.setFSKU_SUB_BRAND_CODE(jObject.getString("SBrandCode"));
                        sku.setFSKU_CODE(jObject.getString("SKUCode"));
                        sku.setFSKU_NAME(jObject.getString("SkuName"));
                        sku.setFSKU_SIZE_CODE(jObject.getString("SkuSizCode"));
                        sku.setFSKU_TONNAGE(jObject.getString("Tonnage"));
                        sku.setFSKU_TYPE_CODE(jObject.getString("TypeCode"));
                        sku.setFSKU_UNIT(jObject.getString("Unit"));

                        list.add(sku);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new SkuDS(context).createOrUpdateSku(list) > 0) {
                        Log.v("createOrUpdateSku", "Result : sku Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "SKU info");

                }
                break;

                case FBRAND:
                    // downloadingDataType="fbrand";
                {
                    ArrayList<Brand> list = new ArrayList<Brand>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FbrandResult");
                    Log.v(TAG, "Array Length Brand DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new BrandDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Brand brand = new Brand();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        brand.setFBRAND_ADD_MACH(jObject.getString("AddMach"));
                        brand.setFBRAND_ADD_USER(jObject.getString("AddUser"));
                        brand.setFBRAND_CODE(jObject.getString("BrandCode"));
                        brand.setFBRAND_NAME(jObject.getString("BrandName"));

                        list.add(brand);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new BrandDS(context).createOrUpdateBrand(list) > 0) {
                        Log.v("createOrUpdateBrand", "Result : brand Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Brand info");

                }
                break;

                case FDISCDEB: {
                    ArrayList<Discdeb> list = new ArrayList<Discdeb>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FdiscdebResult");
                    Log.v(TAG, "Array Length discdeb DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new DiscdebDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Discdeb discdeb = new Discdeb();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        discdeb.setFDISCDEB_REF_NO(jObject.getString("Refno"));
                        discdeb.setFDISCDEB_DEB_CODE(jObject.getString("Debcode"));

                        list.add(discdeb);

                        ++recordCount;
                        publishProgress(recordCount);
                    }
                    if (new DiscdebDS(context).createOrUpdateDiscdeb(list) > 0) {
                        Log.v("createOrUpdateDiscdeb", "Result : discdeb Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Discount debtor info");

                }
                break;

                case FDISCDET:
                    // downloadingDataType="Fdiscdet";
                {
                    ArrayList<Discdet> list = new ArrayList<Discdet>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FdiscdetResult");
                    Log.v(TAG, "Array Length Disched DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new DiscdetDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Discdet discdet = new Discdet();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        discdet.setFDISCDET_REF_NO(jObject.getString("Refno"));
                        discdet.setFDISCDET_ITEM_CODE(jObject.getString("Itemcode"));
                        discdet.setFDISCHED_TIEMSTAMP_COLUMN("");

                        list.add(discdet);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new DiscdetDS(context).createOrUpdateDiscdet(list) > 0) {
                        Log.v("createOrUpdateDiscdet", "Result : discdet Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Discount det info");

                }
                break;

                case FDISCSLAB:
                    // downloadingDataType ="Fdiscslab";
                {
                    ArrayList<Discslab> list = new ArrayList<Discslab>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FdiscslabResult");
                    Log.v(TAG, "Array Length Discslab DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new DiscslabDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Discslab discslab = new Discslab();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        discslab.setFDISCSLAB_REF_NO(jObject.getString("Refno"));
                        discslab.setFDISCSLAB_SEQ_NO("");
                        discslab.setFDISCSLAB_QTY_F(jObject.getString("Qtyf"));
                        discslab.setFDISCSLAB_QTY_T(jObject.getString("Qtyt"));
                        discslab.setFDISCSLAB_DIS_PER(jObject.getString("Disper"));
                        discslab.setFDISCSLAB_DIS_AMUT(jObject.getString("Disamt"));
                        discslab.setFDISCSLAB_TIMESTAMP_COLUMN("");

                        list.add(discslab);

                        ++recordCount;
                        publishProgress(recordCount);
                    }

                    if (new DiscslabDS(context).createOrUpdateDiscslab(list) > 0) {
                        Log.v("createOrUpdateDiscslab", "Result : Discslab Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Discount slab info");

                }
                break;

                case FDISCHED:
                    // downloadingDataType ="FDisched";
                {
                    ArrayList<Disched> list = new ArrayList<Disched>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FDischedResult");
                    Log.v(TAG, "Array Length Disched DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new DischedDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Disched disched = new Disched();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        disched.setFDISCHED_REF_NO(jObject.getString("Refno"));
                        disched.setFDISCHED_TXN_DATE(jObject.getString("Txndate"));
                        disched.setFDISCHED_DISC_DESC(jObject.getString("DiscDesc"));
                        disched.setFDISCHED_PRIORITY(jObject.getString("Priority"));
                        disched.setFDISCHED_DIS_TYPE(jObject.getString("DisType"));
                        disched.setFDISCHED_V_DATE_F(jObject.getString("Vdatef"));
                        disched.setFDISCHED_V_DATE_T(jObject.getString("Vdatet"));
                        disched.setFDISCHED_REMARK("");
                        disched.setFDISCHED_ADD_USER("");
                        disched.setFDISCHED_ADD_DATE("");
                        disched.setFDISCHED_ADD_MACH("");
                        disched.setFDISCHED_RECORD_ID("");
                        disched.setFDISCHED_TIMESTAMP_COLUMN("");

                        list.add(disched);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new DischedDS(context).createOrUpdateDisched(list) > 0) {
                        Log.v("createOrUpdateDisched", "Result : Disched Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Discount header info");


                }
                break;

                case FFREEITEM: {
                    ArrayList<FreeItem> list = new ArrayList<FreeItem>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fFreeItemResult");
                    Log.v(TAG, "Array Length FreeItem DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new FreeItemDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FreeItem freeItem = new FreeItem();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        freeItem.setFFREEITEM_ITEMCODE(jObject.getString("Itemcode"));
                        freeItem.setFFREEITEM_REFNO(jObject.getString("Refno"));
                        list.add(freeItem);
                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new FreeItemDS(context).createOrUpdateFreeItem(list) > 0) {
                        Log.v("createOrUpdateFreeItem", "Result : FreeItem Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Free item info");

                }
                break;

                case FFREEMSLAB:
                    // downloadingDataType="FfreeMslab";
                {
                    ArrayList<FreeMslab> list = new ArrayList<FreeMslab>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fFreeMslabResult");
                    Log.v(TAG, "Array Length FreeMslab DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new FreeMslabDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FreeMslab freeMslab = new FreeMslab();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        freeMslab.setFFREEMSLAB_REFNO(jObject.getString("Refno"));
                        freeMslab.setFFREEMSLAB_QTY_F(jObject.getString("Qtyf"));
                        freeMslab.setFFREEMSLAB_QTY_T(jObject.getString("Qtyt"));
                        freeMslab.setFFREEMSLAB_ITEM_QTY(jObject.getString("ItemQty"));
                        freeMslab.setFFREEMSLAB_FREE_IT_QTY(jObject.getString("FreeItQty"));
                        freeMslab.setFFREEMSLAB_ADD_USER(jObject.getString("AddUser"));
                        freeMslab.setFFREEMSLAB_ADD_DATE(jObject.getString("AddDate"));
                        freeMslab.setFFREEMSLAB_ADD_MACH(jObject.getString("AddMach"));
                        freeMslab.setFFREEMSLAB_SEQ_NO(jObject.getString("Seqno"));

                        list.add(freeMslab);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new FreeMslabDS(context).createOrUpdateFreeMslab(list) > 0) {
                        Log.v("createOrUpdatefreeMslab", "Result : freeMslab Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Free mslab info");

                }
                break;

                case FDISCVHED: {
                    // ArrayList<Discvhed> list = new ArrayList<Discvhed>();
                    // JSONArray jsonArray =
                    // jsonResponse.getJSONArray("FDiscvhedResult");
                    // Log.v(TAG, "Array Length Discvhed DB :" +
                    // jsonArray.length());

                    // totalRecords = jsonArray.length();

                    // for (int i = 0; i < jsonArray.length(); i++) {
                    // Discvhed discvhed = new Discvhed();
                    //
                    // JSONObject jObject = (JSONObject) jsonArray.get(i);
                    //
                    // discvhed.setFDISCVHED_REF_NO(jObject.getString("Refno"));
                    // discvhed.setFDISCVHED_TXN_DATE(jObject.getString("Txndate"));
                    // discvhed.setFDISCVHED_DISC_DESC(jObject.getString("DiscDesc"));
                    // discvhed.setFDISCVHED_PRIORITY(jObject.getString("Priority"));
                    // discvhed.setFDISCVHED_DIS_TYPE(jObject.getString("DisType"));
                    // discvhed.setFDISCVHED_V_DATE_F(jObject.getString("Vdatef"));
                    // discvhed.setFDISCVHED_V_DATE_T(jObject.getString("Vdatet"));
                    // discvhed.setFDISCVHED_REMARKS(jObject.getString("Remarks"));
                    //
                    // list.add(discvhed);
                    //
                    // ++recordCount;
                    // publishProgress(recordCount);
                    //
                    // }
                    // if (new DiscvhedDS(context).createOrUpdateDiscvhed(list) > 0)
                    // {
                    // Log.v("createOrUpdateDiscvhed", "Result : Discvhed Data
                    // Inserted successfully");
                    // }
                }
                break;
                case FDISCVDET: {
                    // ArrayList<Discvdet> list = new ArrayList<Discvdet>();
                    // JSONArray jsonArray =
                    // jsonResponse.getJSONArray("FdiscvdetResult");
                    // Log.v(TAG, "Array Length Discvhed DB :" +
                    // jsonArray.length());
                    //
                    // totalRecords = jsonArray.length();
                    //
                    // for (int i = 0; i < jsonArray.length(); i++) {
                    // Discvdet discvdet = new Discvdet();
                    //
                    // JSONObject jObject = (JSONObject) jsonArray.get(i);
                    //
                    // discvdet.setFDISCVDET_REF_NO(jObject.getString("Refno"));
                    // discvdet.setFDISCVDET_VALUE_F(jObject.getString("Valuef"));
                    // discvdet.setFDISCVDET_VALUE_T(jObject.getString("Valuet"));
                    // discvdet.setFDISCVDET_DISPER(jObject.getString("Disper"));
                    // discvdet.setFDISCVDET_DIS_AMT(jObject.getString("Disamt"));
                    //
                    // list.add(discvdet);
                    //
                    // ++recordCount;
                    // publishProgress(recordCount);
                    //
                    // }
                    // if (new DiscvdetDS(context).createOrUpdateDiscvdet(list) > 0)
                    // {
                    // Log.v("createOrUpdateDiscvdet", "Result : Discvdet Data
                    // Inserted successfully");
                    // }
                }
                break;
                case FDISCVDEB: {
                    // ArrayList<Discvdeb> list = new ArrayList<Discvdeb>();
                    // JSONArray jsonArray =
                    // jsonResponse.getJSONArray("FDiscvdebResult");
                    // Log.v(TAG, "Array Length Discvdeb DB :" +
                    // jsonArray.length());
                    //
                    // totalRecords = jsonArray.length();
                    //
                    // for (int i = 0; i < jsonArray.length(); i++) {
                    // Discvdeb discvdeb = new Discvdeb();
                    //
                    // JSONObject jObject = (JSONObject) jsonArray.get(i);
                    //
                    // discvdeb.setFDISCVDEB_REF_NO(jObject.getString("Refno"));
                    // discvdeb.setFDISCVDED_DEB_CODE(jObject.getString("Debcode"));
                    //
                    // list.add(discvdeb);
                    //
                    // ++recordCount;
                    // publishProgress(recordCount);
                    //
                    // }
                    // if (new DiscvdebDS(context).createOrUpdateDiscvdeb(list) > 0)
                    // {
                    // Log.v("createOrUpdateDiscvdeb", "Result : Discvdeb Data
                    // Inserted successfully");
                    // }
                }
                break;
                case FINVHEDL3:
                    // downloadingDataType="FinvHedL3";
                {
                    // ArrayList<FinvHedL3> list = new ArrayList<FinvHedL3>();
                    // JSONArray jsonArray =
                    // jsonResponse.getJSONArray("RepLastThreeInvHedResult");
                    // Log.v(TAG, "Array Length FinvHedL3 DB :" +
                    // jsonArray.length());
                    //
                    // totalRecords = jsonArray.length();
                    //
                    // for (int i = 0; i < jsonArray.length(); i++) {
                    // FinvHedL3 finvHedL3 = new FinvHedL3();
                    //
                    // JSONObject jObject = (JSONObject) jsonArray.get(i);
                    //
                    // finvHedL3.setFINVHEDL3_DEB_CODE(jObject.getString("DebCode"));
                    // finvHedL3.setFINVHEDL3_REF_NO(jObject.getString("RefNo"));
                    // finvHedL3.setFINVHEDL3_REF_NO1(jObject.getString("RefNo1"));
                    // finvHedL3.setFINVHEDL3_TOTAL_AMT(jObject.getString("TotalAmt"));
                    // finvHedL3.setFINVHEDL3_TOTAL_TAX(jObject.getString("TotalTax"));
                    // finvHedL3.setFINVHEDL3_TXN_DATE(jObject.getString("TxnDate"));
                    //
                    // list.add(finvHedL3);
                    //
                    // ++recordCount;
                    // publishProgress(recordCount);
                    //
                    // }
                    // if (new FinvHedL3DS(context).createOrUpdateFinvHedL3(list) >
                    // 0) {
                    // Log.v("createOrUpdatefinvHedL3", "Result : finvHedL3 Data
                    // Inserted successfully");
                    // }
                }
                break;
                case FINVDETL3: {
                    ArrayList<FinvDetL3> list = new ArrayList<FinvDetL3>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("RepLastThreeInvDetResult");
                    Log.v(TAG, "Array Length FinvDetL3 DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();

                    new FinvDetL3DS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FinvDetL3 finvDetL3 = new FinvDetL3();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        finvDetL3.setFINVDETL3_AMT(jObject.getString("Amt"));
                        finvDetL3.setFINVDETL3_ITEM_CODE(jObject.getString("ItemCode"));
                        finvDetL3.setFINVDETL3_QTY(jObject.getString("Qty"));
                        finvDetL3.setFINVDETL3_REF_NO(jObject.getString("RefNo"));
                        finvDetL3.setFINVDETL3_SEQ_NO(jObject.getString("SeqNo"));
                        finvDetL3.setFINVDETL3_TAX_AMT(jObject.getString("TaxAmt"));
                        finvDetL3.setFINVDETL3_TAX_COM_CODE(jObject.getString("TaxComCode"));
                        finvDetL3.setFINVDETL3_TXN_DATE(jObject.getString("TxnDate"));

                        list.add(finvDetL3);
                        ++recordCount;
                        publishProgress(recordCount);
                    }

                    if (new FinvDetL3DS(context).createOrUpdateFinvDetL3(list) > 0) {
                        Log.v("createOrUpdatefinvDetL3", "Result : finvDetL3 Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Invoice last three info");

                }
                break;

                case FTERMS: {

                    ArrayList<TERMS> list = new ArrayList<TERMS>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FtermResult");
                    Log.v(TAG, "Array Length TermDS DB :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        TERMS term = new TERMS();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        term.setTERMCODE(jObject.getString("termcode"));
                        term.setTERMDES(jObject.getString("termdes"));
                        term.setTERMDISPER(jObject.getString("termdisper"));

                        list.add(term);
                        ++recordCount;
                        publishProgress(recordCount);
                    }

                    if (new TermDS(context).createOrUpdateTerms(list) > 0) {
                        Log.v("TermDS ", "Result : TermDS Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Free Fterms info");

                }

                case FTAX: {
                    ArrayList<Tax> list = new ArrayList<Tax>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FTaxResult");
                    Log.v(TAG, "Array Length FTaxResult DB :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        Tax tax = new Tax();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        tax.setTAXCODE(jObject.getString("TaxCode"));
                        tax.setTAXNAME(jObject.getString("TaxName"));
                        tax.setTAXPER(jObject.getString("TaxPer"));
                        tax.setTAXREGNO(jObject.getString("TaxRegNo"));

                        list.add(tax);
                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new TaxDS(context).createOrUpdateTaxHed(list) > 0) {
                        Log.v("TaxDS", "Result : fDSchDet Data TaxDS successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Tax info");


                }
                break;

                case FTAXHED: {
                    ArrayList<TaxHed> list = new ArrayList<TaxHed>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fTaxHedResult");
                    Log.v(TAG, "Array Length FTAXHED DB :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        TaxHed taxHed = new TaxHed();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        taxHed.setACTIVE(jObject.getString("Active"));
                        taxHed.setTAXCOMCODE(jObject.getString("TaxComCode"));
                        taxHed.setTAXCOMNAME(jObject.getString("TaxComName"));

                        list.add(taxHed);
                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new TaxHedDS(context).createOrUpdateTaxHed(list) > 0) {
                        Log.v("TaxHedDS", "Result : TaxHedDS Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Tax header info");

                }
                break;

                case FTAXDET: {
                    ArrayList<TaxDet> list = new ArrayList<TaxDet>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fTaxDetResult");
                    Log.v(TAG, "Array Length FTAXDET DB :" + jsonArray.length());
                    totalRecords = jsonArray.length();

                    TaxDetDS taxDetDS = new TaxDetDS(context);
                    taxDetDS.deleteAllTaxDets();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        TaxDet taxDet = new TaxDet();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        taxDet.setTAXVAL(jObject.getString("TaxRate"));
                        taxDet.setSEQ(jObject.getString("TaxSeq"));
                        taxDet.setTAXCODE(jObject.getString("TaxCode"));
                        taxDet.setTAXCOMCODE(jObject.getString("TaxComCode"));
                        taxDet.setMODE(jObject.getString("TaxMode"));
                        taxDet.setRATE(jObject.getString("TaxPer"));
                        // taxDet.setTAXTYPE(jObject.getString("TaxName"));

                        list.add(taxDet);
                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (taxDetDS.createOrUpdateTaxDet(list) > 0) {
                        Log.v("TaxDetDS", "Result : TaxDetDS Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Tax det info");


                }
                break;

                case FTOURHED: {

                    ArrayList<TourHed> list = new ArrayList<TourHed>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FTourHedResult");
                    totalRecords = jsonArray.length();
                    new TourHedDS(context).deleteAll();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        TourHed hed = new TourHed();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        hed.setTOURHED_ADDMACH(jObject.getString("AddMach"));
                        hed.setTOURHED_ADDUSER(jObject.getString("AddUser"));
                        hed.setTOURHED_AREACODE(jObject.getString("AreaCode"));
                        hed.setTOURHED_CLSFLG(jObject.getString("Clsflg"));
                        hed.setTOURHED_COSTCODE(jObject.getString("CostCode"));
                        hed.setTOURHED_DRIVERCODE(jObject.getString("DriverCode"));
                        hed.setTOURHED_HELPERCODE(jObject.getString("HelperCode"));
                        hed.setTOURHED_LOCCODE(jObject.getString("LocCode"));
                        hed.setTOURHED_LOCCODEF(jObject.getString("LocCodeF"));
                        hed.setTOURHED_LORRYCODE(jObject.getString("LorryCode"));
                        hed.setTOURHED_MANUREF(jObject.getString("ManuRef"));
                        hed.setTOURHED_REFNO(jObject.getString("RefNo"));
                        hed.setTOURHED_REMARKS(jObject.getString("Remarks"));
                        hed.setTOURHED_REPCODE(jObject.getString("RepCode"));
                        hed.setTOURHED_ROUTECODE(jObject.getString("RouteCode"));
                        hed.setTOURHED_TOURTYPE(jObject.getString("TourType"));
                        hed.setTOURHED_TXNDATE(jObject.getString("TxnDate"));
                        hed.setTOURHED_VANLOADFLG(jObject.getString("VanLoadFlg"));

                        list.add(hed);
                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new TourHedDS(context).createOrUpdateTourHed(list) > 0) {
                        Log.v("TourHedDS", "Result : TourHedDS Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Tour hed info");

                }
                break;

                case FSTKIN: {


                    new STKInDS(context).deleteAll();
                    ArrayList<StkIn> list = new ArrayList<StkIn>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FStkInResult");
                    totalRecords = jsonArray.length();

                    new STKInDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        StkIn hed = new StkIn();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        hed.setBALQTY((jObject.getString("BalQty")));
                        hed.setCOSTPRICE((jObject.getString("CostPrice")));
                        hed.setINQTY((jObject.getString("InQty")));
                        hed.setITEMCODE((jObject.getString("ItemCode")));
                        hed.setLOCCODE((jObject.getString("LocCode")));
                        hed.setOTHCOST((jObject.getString("OthCost")));
                        hed.setREFNO((jObject.getString("RefNo")));
                        hed.setSTKRecDate((jObject.getString("StkRecDate")));
                        hed.setSTKRECNO((jObject.getString("StkRecNo")));
                        hed.setTXNDATE((jObject.getString("TxnDate")));
                        hed.setTXNTYPE((jObject.getString("TxnType")));

                        list.add(hed);
                        ++recordCount;
                        publishProgress(recordCount);

//                        Log.d("FSTKIN", "PRODUCT_PRICE" + jObject.getString("CostPrice") + ", " + jObject.getString("ItemCode"));

                    }
                    new STKInDS(context).createUpdateSTKIn(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Stock info");

                }

                case FDEBITEMPRI: {

                    ArrayList<DebItemPri> list = new ArrayList<DebItemPri>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fDebItemPriResult");
                    totalRecords = jsonArray.length();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        DebItemPri hed = new DebItemPri();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        hed.setBRANDCODE(jObject.getString("BrandCode"));
                        hed.setDEBCODE(jObject.getString("DebCode"));
                        hed.setDISPER(jObject.getString("Disper"));

                        list.add(hed);
                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new DebItemPriDS(context).createOrUpdateDebItemPri(list) > 0) {
                        Log.v("TourHedDS", "Result : TourHedDS Data Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Debtor item price info");

                }

                case FBRANDTARGET: {

                    //new BrandTargetDS(context).deleteAll();

                    ArrayList<BrandTarget> list = new ArrayList<BrandTarget>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FBrandTargetResult");
                    totalRecords = jsonArray.length();
                    new BrandTargetDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        BrandTarget hed = new BrandTarget();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        hed.setBRANDTARGET_BRANDCODE(jObject.getString("BrandCode"));
                        hed.setBRANDTARGET_COSTCODE(jObject.getString("CostCode"));
                        hed.setBRANDTARGET_TARGET(jObject.getString("Target"));

                        list.add(hed);
                        ++recordCount;
                        publishProgress(recordCount);
                    }

                    new BrandTargetDS(context).createOrUpdateBrandTarget(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Target info");


                }

                // ------------------------------------------ nuwan 14/08/2018 -------------------------------------------------

                case FITTOURDISC: {

                    new FItTourDiscDs(context).deleteAll();
                    ArrayList<FItTourDisc> list = new ArrayList<FItTourDisc>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fItTourDiscResult");
                    totalRecords = jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        FItTourDisc hed = new FItTourDisc();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        hed.setFITTOURDISC_PRILCODE(jObject.getString("PrilCode"));
                        hed.setFITTOURDISC_ITEMCODE(jObject.getString("ItemCode"));
                        hed.setFITTOURDISC_QTYFROM(jObject.getString("QtyFrom"));
                        hed.setFITTOURDISC_QTYTO(jObject.getString("QtyTo"));
                        hed.setFITTOURDISC_PRILDISC(jObject.getString("PriDisc"));
                        hed.setFITTOURDISC_RECORDID(jObject.getString("RecordId"));
//                        hed.setFITTOURDISC_ADDUSER(jObject.getString("AddUser"));
//                        hed.setFITTOURDISC_ADDDATE(jObject.getString("AddDate"));

                        list.add(hed);
                        ++recordCount;
                        publishProgress(recordCount);
                    }

                    new FItTourDiscDs(context).createOrUpdateFitTourDisc(list);
                    //new ControlDS(context).createOrUpdateDownload(""+list.size(),""+totalRecords,"Tour discount info");

                }

                break;
                case FDEBTAX: {
                    new FDebTaxDS(context).deleteAll();
                    ArrayList<FDebTax> list = new ArrayList<FDebTax>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FdebtaxResult");
                    totalRecords = jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        FDebTax hed = new FDebTax();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        hed.setFDEBTAX_DEBCODE(jObject.getString("DebCode"));
                        hed.setFDEBTAX_TAXCODE(jObject.getString("TaxCode"));
                        hed.setFDEBTAX_TAXREGNO(jObject.getString("TaxRegNo"));
                        hed.setFDEBTAX_RECORDID(jObject.getString("RecordId"));

                        list.add(hed);
                        ++recordCount;
                        publishProgress(recordCount);
                    }

                    new FDebTaxDS(context).createOrUpdatefDebTax(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Debtor tax info");


                }

                break;
                case FDISTRICT: {
                    new fDistrictDS(context).deleteAll();
                    ArrayList<fDistrict> list = new ArrayList<fDistrict>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("fDistrictResult");
                    totalRecords = jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        fDistrict hed = new fDistrict();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        hed.setDISTRICT_CODE(jObject.getString("DistrCode"));
                        hed.setDISTRICT_NAME(jObject.getString("DistrName"));
                        hed.setDISTRICT_PROVECODE(jObject.getString("ProvCode"));

                        list.add(hed);
                        ++recordCount;
                        publishProgress(recordCount);
                    }

                    new fDistrictDS(context).createOrUpdatefDistrict(list);
                    new ControlDS(context).createOrUpdateDownload("" + totalRecords, "" + totalRecords, "District info");

                }

                break;
                case FAPPRORDHED: {
                    new ApprOrdHedDS(context).deleteAllAppr();
                    ArrayList<ApprOrdHed> list = new ArrayList<ApprOrdHed>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FApprOrdHedResult");
//
                    for (int i = 0; i < jsonArray.length(); i++) {

                        ApprOrdHed hed = new ApprOrdHed();
                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        hed.setFAPPRORDHED_REFNO(jObject.getString("RefNo"));
                        hed.setFAPPRORDHED_TOTALAMT(jObject.getString("TotAmt"));
                        hed.setFAPPRORDHED_DEBCODE(jObject.getString("DebCode"));

                        list.add(hed);
                        ++recordCount;
                        publishProgress(recordCount);
                    }

                    new ApprOrdHedDS(context).createOrUpdateApprOrdHedDS(list);
                    new ControlDS(context).createOrUpdateDownload("" + totalRecords, "" + totalRecords, "Approve header info");

                }

                break;

                case FOTHERTRANS:
                    // downloadingDataType="fOtherTransactions";
                {
                    ArrayList<FOtherTransactions> list = new ArrayList<FOtherTransactions>();
                    JSONArray jsonArray = jsonResponse.getJSONArray("FOtherTransResult");
                    Log.v(TAG, "Array Length fOtherTransactions DB :" + jsonArray.length());

                    totalRecords = jsonArray.length();
                    new FOtherTransDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FOtherTransactions fother = new FOtherTransactions();

                        JSONObject jObject = (JSONObject) jsonArray.get(i);

                        fother.setAmount(jObject.getString("Amt"));
                        fother.setDebCode(jObject.getString("DebCode"));
                        fother.setRefno(jObject.getString("RefNo"));
                        fother.setRefNo1(jObject.getString("Refno1"));
                        fother.setTxnDate(jObject.getString("TxnDate"));
                        fother.setTxnType(jObject.getString("TxnType"));

                        list.add(fother);

                        ++recordCount;
                        publishProgress(recordCount);

                    }
                    if (new FOtherTransDS(context).createOrUpdateFDDbNote(list) > 0) {
                        Log.v("fOtherTransactions", "Result : fOtherTransactions Inserted successfully");
                    }
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "fOtherTransactions info");

                }
                break;

                case FACHIEVEMENT: {

                    JSONArray jsonArray = jsonResponse.getJSONArray("getRepAchiResult");
                    ArrayList<Achievement> list = new ArrayList<Achievement>();

                    totalRecords = jsonArray.length();
                    new AchievementDS(context).deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObject = (JSONObject) jsonArray.get(i);
                        Achievement achieve = new Achievement();

                        achieve.setCumInvCount(jObject.getInt("CumInvCount"));
                        achieve.setCumInvVal(jObject.getDouble("CumInvVal"));
                        achieve.setDayInvCount(jObject.getInt("DayInvCount"));
                        achieve.setDayInvVal(jObject.getDouble("DayInvVal"));
                        achieve.setRepCode(jObject.getString("RepCode"));
                        achieve.setRepName(jObject.getString("RepName"));

                        list.add(achieve);
                        ++recordCount;
                        publishProgress(recordCount);
                    }
                    new AchievementDS(context).createOrUpdateAcieve(list);
                    new ControlDS(context).createOrUpdateDownload("" + list.size(), "" + totalRecords, "Achievement Info");
                }
                break;

                case MONTHORDERSUM:
                {
                    String orderSumTM = jsonResponse.getString("monOrdTotResult");
                    SharedPref.getInstance(context).setTransOrdSumTM(orderSumTM);
                }
                break;

                case MONTHORDERDISCSUM:
                {
                    String orderDiscSumTM = jsonResponse.getString("monOrdDisTotResult");
                    SharedPref.getInstance(context).setTransOrdDiscSumTM(orderDiscSumTM);
                }
                break;

                case MONTHORDERSUMPM:
                {
                    String orderSumPM = jsonResponse.getString("monOrdTotResult");
                    SharedPref.getInstance(context).setTransOrdSumPM(orderSumPM);
                }
                break;

                case MONTHORDERDISCSUMPM:
                {
                    String orderDiscSumPM = jsonResponse.getString("monOrdDisTotResult");
                    SharedPref.getInstance(context).setTransOrdDiscSumPM(orderDiscSumPM);
                }
                break;

                case DAYORDERSUM:
                {
                    String dayOrdTot = jsonResponse.getString("dayOrdTotResult");
                    SharedPref.getInstance(context).setDayOrderSum(dayOrdTot);
                }
                break;

                case DAYDISCOUNTSSUM:
                {
                    String dayOrdDisTot = jsonResponse.getString("dayOrdDisTotResult");
                    SharedPref.getInstance(context).setDayDiscountSum(dayOrdDisTot);
                }
                break;

                case DAYRETURNSUM:
                {
                    String dayRtnTot = jsonResponse.getString("dayRtnTotResult");
                    SharedPref.getInstance(context).setDayReturnSum(dayRtnTot);
                }
                break;

                case DAYPRODUCTIVESUM:
                {
                    String dayProd = jsonResponse.getString("dayProdResult");
                    SharedPref.getInstance(context).setDayProdSum(dayProd);
                }
                break;

                case DAYNONPRODUCTIVESUM:
                {
                    String dayNonProd = jsonResponse.getString("dayNonProdResult");
                    SharedPref.getInstance(context).setDayNonProdSum(dayNonProd);
                }
                break;

                case DAYINVOICESUM:
                {
                    String dayInvSale = jsonResponse.getString("dayInvSaleResult");
                    SharedPref.getInstance(context).setDayInvoiceSum(dayInvSale);
                }
                break;

                case MONTHRETURNSUMTM:
                {
                    String monRtnTotTM = jsonResponse.getString("monRtnTotResult");
                    SharedPref.getInstance(context).setMonthReturnSumTM(monRtnTotTM);
                }
                break;

                case MONTHRETURNSUMPM:
                {
                    String monRtnTotPM = jsonResponse.getString("monRtnTotResult");
                    SharedPref.getInstance(context).setMonthReturnSumPM(monRtnTotPM);
                }
                break;

                case MONTHPRODSUMTM:
                {
                    String monProdTm = jsonResponse.getString("monProdResult");
                    SharedPref.getInstance(context).setMonthProdSumTM(monProdTm);
                }
                break;

                case MONTHPRODSUMPM:
                {
                    String monProdPm = jsonResponse.getString("monProdResult");
                    SharedPref.getInstance(context).setMonthProdSumPM(monProdPm);
                }
                break;

                case MONTHNONPROSUMTM:
                {
                    String monNonProdTM = jsonResponse.getString("monNonProdResult");
                    SharedPref.getInstance(context).setMonthNonProdSumTM(monNonProdTM);
                }
                break;

                case MONTHNONPROSUMPM:
                {
                    String monNonProdPM = jsonResponse.getString("monNonProdResult");
                    SharedPref.getInstance(context).setMonthNonProdSumPM(monNonProdPM);
                }
                break;


                default:
                    break;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "FileNotFound";
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return JSON String
        return resultStr;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // String titleMsg = "Downloading " + downloadingDataType + " data";
        String titleMsg = "Downloading master data. Please Wait...";

        progressDialog.setTitle("--Download Master Data--");
        if (downloadingDataType.equals("111")) {
            // list.add(downloadingDataType+" n");
            progressDialog.setMessage("Processing Data..");
        } else if (downloadingDataType.equals("222")) {
            progressDialog.setMessage("Download Complete..");
        } else {
            progressDialog.setMessage(titleMsg);
            // progressDialog.setMessage(titleMsg + " " + progress[0] + "/" + totalRecords);
            // list.add(downloadingDataType+" - "+progress[0] + "/" + totalRecords);
            // progressDialog.setMessage(list);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
//        new ControlDS(context).getAllDownload();
//
//
//        int i = 1;
//        for (Control c : new ControlDS(context).getAllDownload()) {
//          list.add(i + ". " + c.getFCONTROL_COM_NAME()+" --> "+c.getFCONTROL_COM_ADD1()+"\\"+c.getFCONTROL_COM_ADD2()+"\n");
//          i++;
//        }

        taskListener.onTaskCompleted(taskType, result);
    }

}
