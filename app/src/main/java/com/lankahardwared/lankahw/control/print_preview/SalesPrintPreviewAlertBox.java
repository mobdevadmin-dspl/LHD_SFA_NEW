package com.lankahardwared.lankahw.control.print_preview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.PrintItemAdapter;
import com.lankahardwared.lankahw.adapter.PrintReturnItemAdapter;
import com.lankahardwared.lankahw.control.ListExpandHelper;
import com.lankahardwared.lankahw.data.ControlDS;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.FDebTaxDS;
import com.lankahardwared.lankahw.data.FInvRDetDS;
import com.lankahardwared.lankahw.data.FInvRHedDS;
import com.lankahardwared.lankahw.data.RouteDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.data.TranSODetDS;
import com.lankahardwared.lankahw.data.TranSOHedDS;
import com.lankahardwared.lankahw.model.Control;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.FInvRDet;
import com.lankahardwared.lankahw.model.FInvRHed;
import com.lankahardwared.lankahw.model.SalOrdPrintPre;
import com.lankahardwared.lankahw.model.SalRep;
import com.lankahardwared.lankahw.model.StkIss;
import com.lankahardwared.lankahw.model.TranSODet;
import com.lankahardwared.lankahw.model.TranSOHed;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SalesPrintPreviewAlertBox {

    public static final String SETTINGS = "SETTINGS";
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = -1;
    public static final String SETTING = "SETTINGS";
    public static SharedPreferences localSP;
    Control control;
    SalRep salRep;
    Debtor debtor;
    SalOrdPrintPre salordprintpre;
    String Fdealadd3 = "";
    String Fdealmob = "";
    String printLineSeperator = "____________________________________________";
    String printSpaceName = "                    ";
    String printSpaceQty = "     ";
    String Heading_a = "";
    String Heading_bmh = "";
    String Heading_b = "";
    String buttomRaw = "";
    String Heading_d = "";
    String BILL;
    Dialog dialogProgress;
    ListView lvItemDetails;
    ListView lvItemName;
    String PRefno = "";
    String TranType = "";
    String printMainInvDiscount, printMainInvDiscountVal, PrintNetTotalValuePrintVal, printCaseQuantity,
            printPicesQuantity, TotalInvoiceDiscount;
    BluetoothAdapter mBTAdapter;
    BluetoothSocket mBTSocket = null;
    String PRINTER_MAC_ID;
    Context context;
    private ControlDS dsControlDS;
    private SalRepDS dsSalRepDS;
    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            try {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    if (device.getAddress().equalsIgnoreCase(PRINTER_MAC_ID)) {
                        mBTAdapter.cancelDiscovery();
                        dialogProgress.dismiss();
                        printBillToDevice(PRINTER_MAC_ID);
                    }
                }
            } catch (Exception e) {
                Log.e("Class  ", "fire 1 ", e);

            }
        }
    };

    public SalesPrintPreviewAlertBox(Context context) {
        this.context = context;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    @SuppressWarnings("unused")
    public void PrintDetailsDialogbox(final Context context, String title, String refno, boolean isSalesReturn) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (!isSalesReturn)
        {

            View promptView = layoutInflater.inflate(R.layout.sales_management_presales_print_view, null);
            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
            //localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);

            final TextView Companyname = (TextView) promptView.findViewById(R.id.headcompanyname);
            final TextView Companyaddress1 = (TextView) promptView.findViewById(R.id.headaddress1);
            final TextView Companyaddress2 = (TextView) promptView.findViewById(R.id.headaddress2);
            final TextView CompanyTele = (TextView) promptView.findViewById(R.id.headteleno);
            final TextView Companyweb = (TextView) promptView.findViewById(R.id.headwebsite);
            final TextView Companyemail = (TextView) promptView.findViewById(R.id.heademail);

            final TextView SalesRepname = (TextView) promptView.findViewById(R.id.salesrepname);
            final TextView SalesRepPhone = (TextView) promptView.findViewById(R.id.salesrepphone);

            final TextView Debname = (TextView) promptView.findViewById(R.id.headcusname);
            final TextView Debaddress1 = (TextView) promptView.findViewById(R.id.headcusaddress1);
            final TextView Debaddress2 = (TextView) promptView.findViewById(R.id.headcusaddress2);
            final TextView DebTele = (TextView) promptView.findViewById(R.id.headcustele);

            final TextView SalOrdDate = (TextView) promptView.findViewById(R.id.printsalorddate);
            final TextView OrderNo = (TextView) promptView.findViewById(R.id.printrefno);
            final TextView Remarks = (TextView) promptView.findViewById(R.id.printremark);

            final TextView GrossValue = (TextView) promptView.findViewById(R.id.printgrosssales);
            final TextView TotalDiscount = (TextView) promptView.findViewById(R.id.printtotaldisamt);
            final TextView TotalNetValue = (TextView) promptView.findViewById(R.id.printnettotal);
            final TextView TotfreeQty = (TextView) promptView.findViewById(R.id.preTotFreeQty);

            final TextView compTin = (TextView) promptView.findViewById(R.id.preCompTIn);
            final TextView cusTin = (TextView) promptView.findViewById(R.id.preCusTIN);

            final TextView tvTaxLabel = (TextView) promptView.findViewById(R.id.presaleTaxLabel);
            final TextView tvTaxAmt = (TextView) promptView.findViewById(R.id.presaleTax);

            final TextView TotalPieceQty = (TextView) promptView.findViewById(R.id.printpiecesqty);
            final LinearLayout taxLayout = (LinearLayout)promptView.findViewById(R.id.presalestaxLayout);


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(title.toUpperCase());

            alertDialogBuilder.setView(promptView);

            RouteDS routeDS = new RouteDS(context);
            dsControlDS = new ControlDS(context);

            ArrayList<Control> controlList;
            controlList = dsControlDS.getAllControl();

            PRefno = refno;

            if (controlList.size() > 0) {
                Companyname.setText(controlList.get(0).getFCONTROL_COM_NAME());
                Companyaddress1.setText(controlList.get(0).getFCONTROL_COM_ADD1());
                Companyaddress2.setText(controlList.get(0).getFCONTROL_COM_ADD2());
                CompanyTele.setText(controlList.get(0).getFCONTROL_COM_TEL1());
                Companyweb.setText(controlList.get(0).getFCONTROL_COM_WEB());
                Companyemail.setText(controlList.get(0).getFCONTROL_COM_EMAIL());
                compTin.setText("TIN No: " + controlList.get(0).getFCONTROL_VATCMTAXNO());
            }

            dsSalRepDS = new SalRepDS(context);
            SalRep salrep = dsSalRepDS.getSaleRep(dsSalRepDS.getCurrentRepCode());

            SalesRepname.setText(salrep.getNAME());
            SalesRepPhone.setText(salrep.getTELE());


            TranSOHed SOHed = new TranSOHedDS(context).getDetailsforPrint(refno);
            ArrayList<TranSODet> itemList = new TranSODetDS(context).getAllItemsforPrint(refno);
//            TranSODet freeItemDet = new TranSODetDS(context).getAllFreeIssueDet(refno);
            ArrayList<TranSODet> freeList = new TranSODetDS(context).getAllFreeIssueDet(refno);

            Debtor debtor = new DebtorDS(context).getSelectedCustomerByCode(SOHed.getFTRANSOHED_DEBCODE());

            Debname.setText(debtor.getFDEBTOR_NAME());
            Debaddress1.setText(debtor.getFDEBTOR_ADD1() + ", " + debtor.getFDEBTOR_ADD2());
            Debaddress2.setText(debtor.getFDEBTOR_ADD3());
            DebTele.setText(debtor.getFDEBTOR_TELE());
            cusTin.setText("TIN No: " + debtor.getFDEBTOR_CUS_VATNO());

            SalOrdDate.setText("Sales Order Date: " + SOHed.getFTRANSOHED_TXNDATE());
            Remarks.setText("Delivery Date: " + SOHed.getFTRANSOHED_TXNDELDATE());
            OrderNo.setText("Sales Order Number: " + refno);

            int qty = 0;

            lvItemDetails = (ListView) promptView.findViewById(R.id.preSalePrintList);

            lvItemDetails.setAdapter(new PrintItemAdapter(context, itemList, refno, debtor.getFDEBTOR_CODE()));

            double totdis = 0;
            double totTax = 0;

            for (TranSODet det : itemList)
            {
                totdis += Double.parseDouble(det.getFTRANSODET_SCHDISC());
                totTax += Double.parseDouble(det.getFTRANSODET_TAXAMT());
            }

            int totFreeQty = 0;
            for (TranSODet free : freeList)
            {
                if (free.getFTRANSODET_FREEQTY() != null)
                {
                    totFreeQty += Integer.parseInt(free.getFTRANSODET_FREEQTY());
                }
            }
            TotalPieceQty.setText(SOHed.getFTRANSOHED_TOTQTY());
            TotfreeQty.setText(String.valueOf(totFreeQty));
            TotalDiscount.setText(String.format("%.2f", totdis));

            if (new FDebTaxDS(context).isDebtorTax(debtor.getFDEBTOR_CODE()).equalsIgnoreCase("VAT"))
            {
                taxLayout.setVisibility(View.VISIBLE);
                tvTaxAmt.setText(String.format("%.2f", totTax));
                GrossValue.setText(String.format("%.2f", Double.parseDouble(SOHed.getFTRANSOHED_TOTALAMT()) + totdis - totTax));
            }
            else
            {
                taxLayout.setVisibility(View.GONE);
                GrossValue.setText(String.format("%.2f", Double.parseDouble(SOHed.getFTRANSOHED_TOTALAMT()) + totdis));
            }

            TotalNetValue.setText(SOHed.getFTRANSOHED_TOTALAMT());
            //TotalNetValue.setText(String.format("%.2f",Double.parseDouble(SOHed.getFTRANSOHED_TOTALAMT())-totTax));
            //GrossValue.setText(String.format("%.2f", Double.parseDouble(SOHed.getFTRANSOHED_TOTALAMT()) + totdis));

            //localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

            PRINTER_MAC_ID = localSP.getString("printer_mac_address", "").toString();

            alertDialogBuilder.setCancelable(false).setPositiveButton("Print", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //PrintCurrentview();
                }
            });

            alertDialogBuilder.setCancelable(false).setNegativeButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alertD = alertDialogBuilder.create();
            alertD.show();
            ListExpandHelper.getListViewSize(lvItemDetails);
        }
        else
        {
            View promptView = layoutInflater.inflate(R.layout.sales_management_return_sales_print_view, null);
//            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

            final TextView Companyname = (TextView) promptView.findViewById(R.id.headcompanyname);
            final TextView Companyaddress1 = (TextView) promptView.findViewById(R.id.headaddress1);
            final TextView Companyaddress2 = (TextView) promptView.findViewById(R.id.headaddress2);
            final TextView CompanyTele = (TextView) promptView.findViewById(R.id.headteleno);
            final TextView Companyweb = (TextView) promptView.findViewById(R.id.headwebsite);
            final TextView Companyemail = (TextView) promptView.findViewById(R.id.heademail);

            final TextView SalesRepname = (TextView) promptView.findViewById(R.id.salesrepname);
            final TextView SalesRepPhone = (TextView) promptView.findViewById(R.id.salesrepphone);

            final TextView Debname = (TextView) promptView.findViewById(R.id.headcusname);
            final TextView Debaddress1 = (TextView) promptView.findViewById(R.id.headcusaddress1);
            final TextView Debaddress2 = (TextView) promptView.findViewById(R.id.headcusaddress2);
            final TextView DebTele = (TextView) promptView.findViewById(R.id.headcustele);

            final TextView SalOrdDate = (TextView) promptView.findViewById(R.id.printsalorddate);
            final TextView OrderNo = (TextView) promptView.findViewById(R.id.printrefno);
            final TextView Remarks = (TextView) promptView.findViewById(R.id.printremark);

            final TextView GrossValue = (TextView) promptView.findViewById(R.id.printgrosssales);
            final TextView TotalDiscount = (TextView) promptView.findViewById(R.id.printtotaldisamt);
            final TextView TotalNetValue = (TextView) promptView.findViewById(R.id.printnettotal);
            //final TextView TotfreeQty = (TextView) promptView.findViewById(R.id.preTotFreeQty);

            final TextView compTin = (TextView) promptView.findViewById(R.id.preCompTIn);
            final TextView cusTin = (TextView) promptView.findViewById(R.id.preCusTIN);

            final TextView tvTaxLabel = (TextView) promptView.findViewById(R.id.saleReturnTaxLabel);
            final TextView tvTaxAmt = (TextView) promptView.findViewById(R.id.saleReturnTax);
            final LinearLayout taxLayout = (LinearLayout)promptView.findViewById(R.id.returnTaxLayout);

            final TextView TotalPieceQty = (TextView) promptView.findViewById(R.id.printpiecesqty);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(title.toUpperCase());

            alertDialogBuilder.setView(promptView);

            RouteDS routeDS = new RouteDS(context);
            dsControlDS = new ControlDS(context);

            ArrayList<Control> controlList;
            controlList = dsControlDS.getAllControl();

            PRefno = refno;

            if (controlList.size() > 0) {
                Companyname.setText(controlList.get(0).getFCONTROL_COM_NAME());
                Companyaddress1.setText(controlList.get(0).getFCONTROL_COM_ADD1());
                Companyaddress2.setText(controlList.get(0).getFCONTROL_COM_ADD2());
                CompanyTele.setText(controlList.get(0).getFCONTROL_COM_TEL1());
                Companyweb.setText(controlList.get(0).getFCONTROL_COM_WEB());
                Companyemail.setText(controlList.get(0).getFCONTROL_COM_EMAIL());
                compTin.setText("TIN No: " + controlList.get(0).getFCONTROL_VATCMTAXNO());
            }

            dsSalRepDS = new SalRepDS(context);
            SalRep salrep = dsSalRepDS.getSaleRep(dsSalRepDS.getCurrentRepCode());

            SalesRepname.setText(salrep.getNAME());
            SalesRepPhone.setText(salrep.getTELE());


            FInvRHed fInvRHed = new FInvRHedDS(context).getReturnDetailsForPrint(refno);
            ArrayList<FInvRDet> itemList = new FInvRDetDS(context).getReturnItemsforPrint(refno);

            FInvRDet fInvRDet = new FInvRDetDS(context).getReturnItemDetailsForPrint(refno);

            Debtor debtor = new DebtorDS(context).getSelectedCustomerByCode(fInvRHed.getFINVRHED_DEBCODE());

            Debname.setText(debtor.getFDEBTOR_NAME());
            Debaddress1.setText(debtor.getFDEBTOR_ADD1() + ", " + debtor.getFDEBTOR_ADD2());
            Debaddress2.setText(debtor.getFDEBTOR_ADD3());
            DebTele.setText(debtor.getFDEBTOR_TELE());
            cusTin.setText("TIN No: " + debtor.getFDEBTOR_CUS_VATNO());

            SalOrdDate.setText("Sales Return Date: " + fInvRHed.getFINVRHED_TXN_DATE());
            //Remarks.setText("Delivery Date: " + SOHed.getFTRANSOHED_TXNDELDATE());
            Remarks.setVisibility(View.GONE);
            OrderNo.setText("Sales Return Number: " + refno);

            int qty = 0;

            lvItemDetails = (ListView) promptView.findViewById(R.id.preSalePrintList);
            lvItemDetails.setAdapter(new PrintReturnItemAdapter(context, itemList, refno, debtor.getFDEBTOR_CODE()));

            double totdis = 0;
            double totQty = 0;
            double totAmt = 0;
            double totFrQty = 0;
            double totTax = 0;

            for (FInvRDet det : itemList){

                totdis += Double.parseDouble(det.getFINVRDET_DIS_AMT());
                totAmt += Double.parseDouble(det.getFINVRDET_AMT());
                totQty += Double.parseDouble(det.getFINVRDET_QTY());
                totTax += Double.parseDouble(det.getFINVRDET_TAX_AMT());

            }

            if (new FDebTaxDS(context).isDebtorTax(debtor.getFDEBTOR_CODE()).equalsIgnoreCase("VAT"))
            {
                taxLayout.setVisibility(View.VISIBLE);
                tvTaxAmt.setText(String.format("%.2f", totTax));
                GrossValue.setText(String.format("%.2f", Double.parseDouble(fInvRHed.getFINVRHED_TOTAL_AMT()) + totdis - totTax));
            }
            else
            {
                taxLayout.setVisibility(View.GONE);
                GrossValue.setText(String.format("%.2f", Double.parseDouble(fInvRHed.getFINVRHED_TOTAL_AMT()) + totdis));
            }

            //TotalPieceQty.setText(fInvRDet.getFINVRDET_QTY());
            TotalPieceQty.setText(String.format("%.2f", totQty));
            //TotfreeQty.setText(String.format("%.2f", totFrQty));
            TotalDiscount.setText(String.format("%.2f",totdis));
            //TotalNetValue.setText(String.format("%.2f", Double.parseDouble(fInvRHed.getFINVRHED_TOTAL_AMT())));
            //GrossValue.setText(String.format("%.2f", Double.parseDouble(fInvRHed.getFINVRHED_TOTAL_AMT())));
            TotalNetValue.setText(String.format("%.2f", Double.parseDouble(fInvRHed.getFINVRHED_TOTAL_AMT())));


            //GrossValue.setText(String.format("%.2f", totAmt));

//            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

            PRINTER_MAC_ID = localSP.getString("printer_mac_address", "").toString();

            alertDialogBuilder.setCancelable(false).setPositiveButton("Print", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //PrintCurrentview();
                }
            });

            alertDialogBuilder.setCancelable(false).setNegativeButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alertD = alertDialogBuilder.create();
            alertD.show();
            ListExpandHelper.getListViewSize(lvItemDetails);
        }


    }

    public void printItems() {
        final int LINECHAR = 44;
        String printGapAdjustCom = "                        ";

        ArrayList<Control> controlList;
        controlList = dsControlDS.getAllControl();

        SalRep salrep = dsSalRepDS.getSaleRep(dsSalRepDS.getCurrentRepCode());

        int lengthDealACom = controlList.get(0).getFCONTROL_COM_NAME().length();
        int lengthDealABCom = (LINECHAR - lengthDealACom) / 2;
        String printGapAdjustACom = printGapAdjustCom.substring(0, Math.min(lengthDealABCom, printGapAdjustCom.length()));

        int lengthDealBCom = controlList.get(0).getFCONTROL_COM_ADD1().length();
        int lengthDealBBCom = (LINECHAR - lengthDealBCom) / 2;
        String printGapAdjustBCom = printGapAdjustCom.substring(0, Math.min(lengthDealBBCom, printGapAdjustCom.length()));

        String addressCCom = controlList.get(0).getFCONTROL_COM_ADD2().trim() + ", " + controlList.get(0).getFCONTROL_COM_ADD3().trim() + ".";
        int lengthDealCCom = addressCCom.length();
        int lengthDealCBCom = (LINECHAR - lengthDealCCom) / 2;
        String printGapAdjustCCom = printGapAdjustCom.substring(0, Math.min(lengthDealCBCom, printGapAdjustCom.length()));

        String TelCom = "Tel:" + controlList.get(0).getFCONTROL_COM_TEL1().trim();
        int lengthDealDCom = TelCom.length();
        int lengthDealDBCom = (LINECHAR - lengthDealDCom) / 2;
        String printGapAdjustDCom = printGapAdjustCom.substring(0, Math.min(lengthDealDBCom, printGapAdjustCom.length()));

        int lengthDealECom = controlList.get(0).getFCONTROL_COM_WEB().length();
        int lengthDealEBCom = (LINECHAR - lengthDealECom) / 2;
        String printGapAdjustECom = printGapAdjustCom.substring(0, Math.min(lengthDealEBCom, printGapAdjustCom.length()));

        int lengthDealFCom = controlList.get(0).getFCONTROL_COM_EMAIL().length();
        int lengthDealFBCom = (LINECHAR - lengthDealFCom) / 2;
        String printGapAdjustFCom = printGapAdjustCom.substring(0, Math.min(lengthDealFBCom, printGapAdjustCom.length()));

        int lengthTIN = ("TIN No: " + controlList.get(0).getFCONTROL_VATCMTAXNO()).length();
        int lengthTINCom = (LINECHAR - lengthTIN) / 2;
        String printGapAdjustGCom = printGapAdjustCom.substring(0, Math.min(lengthTINCom, printGapAdjustCom.length()));

        String subTitleheadACom = printGapAdjustACom + controlList.get(0).getFCONTROL_COM_NAME();
        String subTitleheadBCom = printGapAdjustBCom + controlList.get(0).getFCONTROL_COM_ADD1();
        String subTitleheadCCom = printGapAdjustCCom + controlList.get(0).getFCONTROL_COM_ADD2() + ", " + controlList.get(0).getFCONTROL_COM_ADD3() + ".";
        String subTitleheadDCom = printGapAdjustDCom + controlList.get(0).getFCONTROL_COM_TEL1();
        String subTitleheadECom = printGapAdjustECom + controlList.get(0).getFCONTROL_COM_WEB();
        String subTitleheadFCom = printGapAdjustFCom + controlList.get(0).getFCONTROL_COM_EMAIL();
        String subTitleheadHCom = printGapAdjustGCom + "TIN No: " + controlList.get(0).getFCONTROL_VATCMTAXNO();
        String subTitleheadGCom = printLineSeperator;

        String title_Print_ACom = "\r\n" + subTitleheadACom;
        String title_Print_BCom = "\r\n" + subTitleheadBCom;
        String title_Print_CCom = "\r\n" + subTitleheadCCom;
        String title_Print_DCom = "\r\n" + subTitleheadDCom;
        String title_Print_ECom = "\r\n" + subTitleheadECom;
        String title_Print_FCom = "\r\n" + subTitleheadFCom;
        String title_Print_HCom = "\r\n" + subTitleheadHCom;

        String title_Print_GCom = "\r\n" + subTitleheadGCom;

        Heading_a = title_Print_ACom + title_Print_BCom + title_Print_CCom + title_Print_DCom + title_Print_ECom + title_Print_FCom + title_Print_HCom + title_Print_GCom;

        String printGapAdjust = "                        ";

        String SalesRepNamestr = "Sales Rep :" + salrep.getNAME().trim();

        int lengthDealE = SalesRepNamestr.length();
        int lengthDealEB = (LINECHAR - lengthDealE) / 2;
        String printGapAdjustE = printGapAdjust.substring(0, Math.min(lengthDealEB, printGapAdjust.length()));
        String subTitleheadF = printGapAdjustE + SalesRepNamestr;

        String SalesRepPhonestr = "Tele :" + salrep.getTELE().trim();
        int lengthDealF = SalesRepPhonestr.length();
        int lengthDealFB = (LINECHAR - lengthDealF) / 2;
        String printGapAdjustF = printGapAdjust.substring(0, Math.min(lengthDealFB, printGapAdjust.length()));
        String subTitleheadG = printGapAdjustF + SalesRepPhonestr;

        String subTitleheadH = printLineSeperator;

        TranSOHed SOHed = new TranSOHedDS(context).getDetailsforPrint(PRefno);
        Debtor debtor = new DebtorDS(context).getSelectedCustomerByCode(SOHed.getFTRANSOHED_DEBCODE());

        int lengthDealI = debtor.getFDEBTOR_NAME().length();
        int lengthDealIB = (LINECHAR - lengthDealI) / 2;
        String printGapAdjustI = printGapAdjust.substring(0, Math.min(lengthDealIB, printGapAdjust.length()));

        String customerAddressStr = debtor.getFDEBTOR_ADD1() + "," + debtor.getFDEBTOR_ADD2();
        int lengthDealJ = customerAddressStr.length();
        int lengthDealJB = (LINECHAR - lengthDealJ) / 2;
        String printGapAdjustJ = printGapAdjust.substring(0, Math.min(lengthDealJB, printGapAdjust.length()));

        int lengthDealK = debtor.getFDEBTOR_ADD3().length();
        int lengthDealKB = (LINECHAR - lengthDealK) / 2;
        String printGapAdjustK = printGapAdjust.substring(0, Math.min(lengthDealKB, printGapAdjust.length()));

        int lengthDealL = debtor.getFDEBTOR_TELE().length();
        int lengthDealLB = (LINECHAR - lengthDealL) / 2;
        String printGapAdjustL = printGapAdjust.substring(0, Math.min(lengthDealLB, printGapAdjust.length()));

        int cusVatNo = "TIN No: ".length() + debtor.getFDEBTOR_CUS_VATNO().length();
        int lengthCusTIN = (LINECHAR - cusVatNo) / 2;
        String printGapCusTIn = printGapAdjust.substring(0, Math.min(lengthCusTIN, printGapAdjust.length()));

        String subTitleheadI = printGapAdjustI + debtor.getFDEBTOR_NAME();
        String subTitleheadJ = printGapAdjustJ + debtor.getFDEBTOR_ADD1() + "," + debtor.getFDEBTOR_ADD2();
        String subTitleheadK = printGapAdjustK + debtor.getFDEBTOR_ADD3();
        String subTitleheadL = printGapAdjustL + debtor.getFDEBTOR_TELE();
        String subTitleheadTIN = printGapCusTIn + "TIN No: " + debtor.getFDEBTOR_CUS_VATNO();

        String subTitleheadO = printLineSeperator;

        String subTitleheadM = "Sales Order Date :" + SOHed.getFTRANSOHED_TXNDATE();
        int lengthDealM = subTitleheadM.length();
        int lengthDealMB = (LINECHAR - lengthDealM) / 2;
        String printGapAdjustM = printGapAdjust.substring(0, Math.min(lengthDealMB, printGapAdjust.length()));

        String subTitleheadMD = "Delivery Date :" + SOHed.getFTRANSOHED_TXNDELDATE();
        int lengthDealMD = subTitleheadMD.length();
        int lengthDealMBD = (LINECHAR - lengthDealMD) / 2;
        String printGapAdjustMD = printGapAdjust.substring(0, Math.min(lengthDealMBD, printGapAdjust.length()));

        String subTitleheadN = "Sales Order Number :" + PRefno;
        int lengthDealN = subTitleheadN.length();
        int lengthDealNB = (LINECHAR - lengthDealN) / 2;
        String printGapAdjustN = printGapAdjust.substring(0, Math.min(lengthDealNB, printGapAdjust.length()));

        String subTitleheadR;

        if (SOHed.getFTRANSOHED_REMARKS().equals(""))
            subTitleheadR = "Remarks : None" + SOHed.getFTRANSOHED_REMARKS();
        else
            subTitleheadR = "Remarks : " + SOHed.getFTRANSOHED_REMARKS();

        int lengthDealR = subTitleheadR.length();
        int lengthDealRB = (LINECHAR - lengthDealR) / 2;
        String printGapAdjustR = printGapAdjust.substring(0, Math.min(lengthDealRB, printGapAdjust.length()));

        subTitleheadM = printGapAdjustM + subTitleheadM;
        subTitleheadMD = printGapAdjustMD + subTitleheadMD;
        subTitleheadN = printGapAdjustN + subTitleheadN;
        subTitleheadR = printGapAdjustR + subTitleheadR;

        String title_Print_F = "\r\n" + subTitleheadF;
        String title_Print_G = "\r\n" + subTitleheadG;
        String title_Print_H = "\r\n" + subTitleheadH;

        String title_Print_I = "\r\n" + subTitleheadI;
        String title_Print_J = "\r\n" + subTitleheadJ;
        String title_Print_K = "\r\n" + subTitleheadK;
        String title_Print_L = "\r\n" + subTitleheadL + "\r\n" + subTitleheadTIN;
        String title_Print_O = "\r\n" + subTitleheadO;

        String title_Print_M = "\r\n" + subTitleheadM;
        String title_Print_MD = "\r\n" + subTitleheadMD;
        String title_Print_N = "\r\n" + subTitleheadN;
        String title_Print_R = "\r\n" + subTitleheadR;

        ArrayList<TranSODet> itemList = new TranSODetDS(context).getAllItemsforPrint(PRefno);

        Heading_d = "";

        if (subTitleheadK.toString().equalsIgnoreCase(" ")) {
            Heading_bmh = "\r" + title_Print_F + title_Print_G + title_Print_H + title_Print_I + title_Print_J + title_Print_O + title_Print_M + title_Print_MD + title_Print_N + title_Print_R;
        } else
//            Heading_bmh = "\r" + title_Print_F + title_Print_G + title_Print_H + title_Print_I + title_Print_J + title_Print_K + title_Print_O + title_Print_M + title_Print_MD + title_Print_N + title_Print_R;
        Heading_bmh = "\r" + title_Print_F + title_Print_G + title_Print_H + title_Print_I + title_Print_J + title_Print_K + title_Print_L + title_Print_O + title_Print_M + title_Print_MD + title_Print_N + title_Print_R;

        String title_cb = "\r\nPRODUCT     BRAND   QTY     MRP       AMOUNT";
        String title_cc = "\r\n(SIZECODE x QTY)                                ";

        Heading_b = "\r\n" + printLineSeperator + title_cb + title_cc + "\r\n" + printLineSeperator;

        int totQty = 0;
        ArrayList<StkIss> list = new ArrayList<StkIss>();

        for (TranSODet det : itemList) {
            totQty += Integer.parseInt(det.getFTRANSODET_QTY());
            // list.addAll(new StkIssDS(context).getItemlist(PRefno,
            // det.getFTRANSODET_ITEMCODE()));
        }

        int nos = 1;
        String SPACE1, SPACE2, SPACE3, SPACE4;

        for (StkIss iss : list) {

            String sItemcode = iss.getITEMCODE();
            String sQty = iss.getQTY();
            String sPrice, sTotal, sBrand;

            sPrice = "";// iss.getPRICE();
            sTotal = "";// iss.getAMT();
            sBrand = "";// iss.getBrand();

            SPACE1 = String.format("%" + (18 - (sItemcode.length() + sQty.length() + (String.valueOf(nos).length() + 2))) + "s", " ");
            SPACE2 = String.format("%" + (5 - (sBrand.length())) + "s", " ");
            SPACE3 = String.format("%" + (9 - (sPrice.length())) + "s", " ");
            SPACE4 = String.format("%" + (12 - (sTotal.length())) + "s", " ");

            Heading_d += "\r\n" + nos + ". " + sItemcode + SPACE1 + sBrand + SPACE2 + sQty + SPACE3 + sPrice + SPACE4 + sTotal;

            // Heading_d += "\r\n" + new
            // StkIssDS(context).getSizecodeString(sItemcode, iss.getPRICE(),
            // PRefno);
            nos++;
        }

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String printSpaceSumName = "                    ";
        String summaryTitle_a = "Total Quantity" + printSpaceSumName;
        summaryTitle_a = summaryTitle_a.substring(0, Math.min(20, summaryTitle_a.length()));

        String space = String.format("%" + (LINECHAR - ("Total Quantity".length() + String.valueOf(totQty).length())) + "s", " ");
        String buttomTitlea = "\r\n\n\n" + "Total Quantity" + space + String.valueOf(totQty);
        String sNetTot = String.format("%,.2f", Double.parseDouble(SOHed.getFTRANSOHED_TOTALAMT()));
        String sGross = String.format("%,.2f", Double.parseDouble(SOHed.getFTRANSOHED_TOTALAMT()));

		/* print gross amount */
        space = String.format("%" + (LINECHAR - ("Total Value".length() + sGross.length())) + "s", " ");
        String summaryTitle_c_Val = "Total Value" + space + sGross;

		/* print net total */
        space = String.format("%" + (LINECHAR - ("Net Total".length() + sNetTot.length())) + "s", " ");
        String summaryTitle_e_Val = "Net Total" + space + sNetTot;

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String summaryBottom_cpoyline1 = " Developed by Datamation Systems. ";
        int lengthsummarybottm = summaryBottom_cpoyline1.length();
        int lengthsummarybottmline1 = (LINECHAR - lengthsummarybottm) / 2;
        String printGapbottmline1 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline1, printGapAdjust.length()));

        String summaryBottom_cpoyline3 = "www.datamation.lk";
        int lengthsummarybotline3 = summaryBottom_cpoyline3.length();
        int lengthsummarybottmline3 = (LINECHAR - lengthsummarybotline3) / 2;
        String printGapbottmline3 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline3, printGapAdjust.length()));

        String summaryBottom_cpoyline2 = " +94 11 2 501202 / + 94 (0) 777 899899 ";
        int lengthsummarybotline2 = summaryBottom_cpoyline2.length();
        int lengthsummarybottmline2 = (LINECHAR - lengthsummarybotline2) / 2;
        String printGapbottmline2 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline2, printGapAdjust.length()));

        String buttomTitlec = "\r\n" + summaryTitle_c_Val;
        String buttomTitlee = "\r\n" + summaryTitle_e_Val;
        String buttomTitlef = "\r\n\n\n" + "Order Accept................................";
        String buttomTitlefa = "\r\n\n\n" + "Please place The Rubber Stamp.";
        String buttomTitlecopyw = "\r\n\n\n" + printGapbottmline1 + summaryBottom_cpoyline1;
        String buttomTitlecopywbottom = "\r\n" + printGapbottmline2 + summaryBottom_cpoyline2;
        String buttomTitlecopywbottom3 = "\r\n" + printGapbottmline3 + summaryBottom_cpoyline3;

        buttomRaw = "\r\n" + printLineSeperator + buttomTitlea + buttomTitlec + "\r\n" + printLineSeperator + buttomTitlee + "\r\n" + printLineSeperator + "\r\n" + buttomTitlef + buttomTitlefa + "\r\n" + printLineSeperator + buttomTitlecopyw + buttomTitlecopywbottom + buttomTitlecopywbottom3 + "\r\n\n\n\n\n\n\n" + printLineSeperator + "\n";

        callPrintDevice();

    }

    public void PrintCurrentview() {
        // checkPrinter();
        // if (PRINTER_MAC_ID.equals("404")) {
        // Log.v("", "No MAC Address Found.Enter Printer MAC Address.");
        // android.widget.Toast.makeText(context, "No MAC Address Found.Enter
        // Printer MAC Address.", android.widget.Toast.LENGTH_LONG).show();
        // } else {
        printItems();
        // }
    }

    private void checkPrinter() {

        if (PRINTER_MAC_ID.trim().length() == 0) {
            PRINTER_MAC_ID = "404";
        } else {
            PRINTER_MAC_ID = PRINTER_MAC_ID;
        }
    }

    private void callPrintDevice() {
        BILL = " ";

        BILL = Heading_a + Heading_bmh + Heading_b + Heading_d + buttomRaw;
        Log.v("", "BILL :" + BILL);

        mBTAdapter = BluetoothAdapter.getDefaultAdapter();

        try {
            if (mBTAdapter.isDiscovering())
                mBTAdapter.cancelDiscovery();
            else
                mBTAdapter.startDiscovery();
        } catch (Exception e) {
            Log.e("Class ", "fire 4", e);
        }
        System.out.println("BT Searching status :" + mBTAdapter.isDiscovering());

        if (mBTAdapter == null) {
            android.widget.Toast.makeText(context, "Device has no bluetooth capability...", android.widget.Toast.LENGTH_SHORT).show();
        } else {
            if (!mBTAdapter.isEnabled()) {
                Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            }
            printBillToDevice(PRINTER_MAC_ID);
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

        }
    }

    public void printBillToDevice(final String address) {

        mBTAdapter.cancelDiscovery();
        try {
            BluetoothDevice mdevice = mBTAdapter.getRemoteDevice(address);
            Method m = mdevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
            mBTSocket = (BluetoothSocket) m.invoke(mdevice, 1);

            mBTSocket.connect();
            OutputStream os = mBTSocket.getOutputStream();
            os.flush();

            os.write(BILL.getBytes());
            System.out.println(BILL);

            if (mBTAdapter != null)
                mBTAdapter.cancelDiscovery();
        } catch (Exception e) {
            Log.e("Class ", "fire 2 ", e);
            android.widget.Toast.makeText(context, "Printer Device Disable Or Invalid MAC.Please Enable the Printer or MAC Address.", android.widget.Toast.LENGTH_LONG).show();
            e.printStackTrace();
            this.PrintDetailsDialogbox(context, "", PRefno, false);

        }
    }
}
