package com.lankahardwared.lankahw.control.print_preview;

import android.app.ActionBar.LayoutParams;
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
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.PrintReceiptAdapter;
import com.lankahardwared.lankahw.control.ListExpandHelper;
import com.lankahardwared.lankahw.data.ControlDS;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.RecDetDS;
import com.lankahardwared.lankahw.data.RecHedDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.model.Control;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.RecDet;
import com.lankahardwared.lankahw.model.RecHed;
import com.lankahardwared.lankahw.model.SalRep;
import com.lankahardwared.lankahw.model.VanSalPrintPre;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReceiptPreviewAlertBox {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Control control;
    SalRep salRep;
    Debtor debtor;
    VanSalPrintPre vansalprintpre;

    String Fdealadd3 = "";
    String Fdealmob = "";

    String printLineSeperator = "________________________________________________";
    String printSpaceName = "                    ";
    String printSpaceQty = "     ";
    String Heading_a = "";
    String Heading_bmh = "";
    String Heading_b = "";
    String buttomRaw = "";
    String Heading_d = "";

    String BILL;
    LinearLayout lnBank, lnCHQno, lnCHQdate;

    Dialog dialogProgress;

    ListView lvItemDetails;

    String PRefno = "";

    String printMainInvDiscount, printMainInvDiscountVal,
            PrintNetTotalValuePrintVal, printCaseQuantity, printPicesQuantity,
            TotalInvoiceDiscount;

    int countCountInv;

    BluetoothAdapter mBTAdapter;
    BluetoothSocket mBTSocket = null;

    String PRINTER_MAC_ID;
    public static final int RESULT_CANCELED = 0;

    public static final int RESULT_OK = -1;

    public static final String SETTING = "SETTINGS";
    Context context;

    public ReceiptPreviewAlertBox(Context context) {
        this.context = context;
    }

    public void PrintDetailsDialogbox(final Context context, String title, String refno) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View promptView = layoutInflater.inflate(R.layout.sales_management_receipt_print_view, null);
//        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
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
        final TextView DebRoute = (TextView) promptView.findViewById(R.id.headcusroute);
        final TextView DebTown = (TextView) promptView.findViewById(R.id.headcustown);

        final TextView SalOrdDate = (TextView) promptView.findViewById(R.id.printsalorddate);
        final TextView OrderNo = (TextView) promptView.findViewById(R.id.printrefno);
        final TextView Remarks = (TextView) promptView.findViewById(R.id.printremark);

        final TextView tvTotalAlloc = (TextView) promptView.findViewById(R.id.recTotalAlloc);
        final TextView tvPayMode = (TextView) promptView.findViewById(R.id.recPayMode);
        final TextView tvBank = (TextView) promptView.findViewById(R.id.recBank);
        final TextView tvChqNo = (TextView) promptView.findViewById(R.id.recChqNo);
        final TextView tvChqDate = (TextView) promptView.findViewById(R.id.recChqDate);

        lnBank = (LinearLayout) promptView.findViewById(R.id.linearBank);
        lnCHQno = (LinearLayout) promptView.findViewById(R.id.linearChqNo);
        lnCHQdate = (LinearLayout)promptView.findViewById(R.id.linearChqDate);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title.toUpperCase());

        alertDialogBuilder.setView(promptView);

        ArrayList<Control> controlList;
        controlList = new ControlDS(context).getAllControl();

        PRefno = refno;

        // Print Preview Company Details.
        Companyname.setText(controlList.get(0).getFCONTROL_COM_NAME());
        Companyaddress1.setText(controlList.get(0).getFCONTROL_COM_ADD1());
        Companyaddress2.setText(controlList.get(0).getFCONTROL_COM_ADD2());
        CompanyTele.setText(controlList.get(0).getFCONTROL_COM_TEL1());
        Companyweb.setText(controlList.get(0).getFCONTROL_COM_WEB());
        Companyemail.setText(controlList.get(0).getFCONTROL_COM_EMAIL());

        SalRep salrep = new SalRepDS(context).getSaleRep(new SalRepDS(context).getCurrentRepCode());
        SalesRepname.setText(salrep.getNAME());
        SalesRepPhone.setText(salrep.getMOBILE());

        RecHed recHed = new RecHedDS(context).getReceiptByRefno(refno);
        ArrayList<RecDet> list = new RecDetDS(context).GetReceiptByRefno(refno);
        Debtor debtor = new DebtorDS(context).getSelectedCustomerByCode(recHed.getFPRECHED_DEBCODE());

        Debname.setText(debtor.getFDEBTOR_NAME());
        Debaddress1.setText(debtor.getFDEBTOR_ADD1() + " ");
        Debaddress2.setText(debtor.getFDEBTOR_ADD2() + " " + debtor.getFDEBTOR_ADD3());
        DebTele.setText(debtor.getFDEBTOR_TELE());
        DebRoute.setText("Route : " + new DebtorDS(context).getRouteNameByCode(recHed.getFPRECHED_DEBCODE()));
        DebTown.setText("Town : " + new DebtorDS(context).getTownNameByCode(recHed.getFPRECHED_DEBCODE()));

        Log.d("RECEIPT_PREVIEW","DEBTOR_CREDIT_DETAILS:" + debtor.getFDEBTOR_CRD_LIMIT() + ", " + debtor.getFDEBTOR_CRD_PERIOD() );

        SalOrdDate.setText("Receipt Date: " + recHed.getFPRECHED_TXNDATE());
        Remarks.setText("Remarks: " + recHed.getFPRECHED_REMARKS());
        OrderNo.setText("Receipt No: " + refno);

        lvItemDetails = (ListView) promptView.findViewById(R.id.vansaleList);
        lvItemDetails.setAdapter(new PrintReceiptAdapter(context, list, refno));

//        String allocAmt="";
//
//        for (RecDet recDet:list)
//        {
//            allocAmt = recDet.getFPRECDET_ALOAMT();
//
//        }
//
//        if (!allocAmt.equals(""))
//        {
//            tvTotalAlloc.setText(String.format("%,.2f", Double.parseDouble(allocAmt)));
//        }
        tvTotalAlloc.setText(String.format("%,.2f", Double.parseDouble(recHed.getFPRECHED_TOTALAMT())));

        if (recHed.getFPRECHED_PAYTYPE().equals("CA"))
        {
            tvPayMode.setText("CASH");
        }
        else if (recHed.getFPRECHED_PAYTYPE().equals("CH"))
        {
            tvPayMode.setText("CHEQUE");
        }
        else
        {
            tvPayMode.setText("CARD");
        }

        tvChqNo.setText(recHed.getFPRECHED_CHQNO());
        tvChqDate.setText(recHed.getFPRECHED_CHQDATE());
//        tvBank.setText(recHed.getFPRECHED_BANKCODE() + " - " + recHed.getFPRECHED_BRANCHCODE().toString());
        tvBank.setText(recHed.getFPRECHED_CUSBANK());

        if (recHed.getFPRECHED_PAYTYPE().equals("CASH")) {
            lnBank.setVisibility(View.GONE);
            lnCHQno.setVisibility(View.GONE);
            lnCHQdate.setVisibility(View.GONE);
        } else if (recHed.getFPRECHED_PAYTYPE().equals("CHEQUE")) {
            lnBank.setVisibility(View.VISIBLE);
            lnCHQno.setVisibility(View.VISIBLE);
            lnCHQdate.setVisibility(View.VISIBLE);
        }

//        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        PRINTER_MAC_ID = localSP.getString("printer_mac_address", "").toString();
        Log.v("mac_id", PRINTER_MAC_ID);

        alertDialogBuilder.setCancelable(false).setPositiveButton("Print", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.v("", "***************************");
                PrintCurrentview();
            }
        });

        alertDialogBuilder.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });


        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        Window window = alertD.getWindow();
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ListExpandHelper.getListViewSize(lvItemDetails);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void printItems() {

        String printGapAdjustCom = "                        ";

        ArrayList<Control> controlList;
        controlList = new ControlDS(context).getAllControl();

        SalRep salrep = new SalRepDS(context).getSaleRep(new SalRepDS(context).getCurrentRepCode());

        int lengthDealACom = controlList.get(0).getFCONTROL_COM_NAME().length();
        int lengthDealABCom = (48 - lengthDealACom) / 2;
        String printGapAdjustACom = printGapAdjustCom.substring(0, Math.min(lengthDealABCom, printGapAdjustCom.length()));

        int lengthDealBCom = controlList.get(0).getFCONTROL_COM_ADD1().length();
        int lengthDealBBCom = (48 - lengthDealBCom) / 2;
        String printGapAdjustBCom = printGapAdjustCom.substring(0, Math.min(lengthDealBBCom, printGapAdjustCom.length()));

        String addressCCom = controlList.get(0).getFCONTROL_COM_ADD2().trim() + ", " + controlList.get(0).getFCONTROL_COM_ADD3().trim() + ".";
        int lengthDealCCom = addressCCom.length();
        int lengthDealCBCom = (48 - lengthDealCCom) / 2;
        String printGapAdjustCCom = printGapAdjustCom.substring(0, Math.min(lengthDealCBCom, printGapAdjustCom.length()));

        String TelCom = "Tel:" + controlList.get(0).getFCONTROL_COM_TEL1().trim();
        int lengthDealDCom = TelCom.length();
        int lengthDealDBCom = (48 - lengthDealDCom) / 2;
        String printGapAdjustDCom = printGapAdjustCom.substring(0, Math.min(lengthDealDBCom, printGapAdjustCom.length()));

        int lengthDealECom = controlList.get(0).getFCONTROL_COM_WEB().length();
        int lengthDealEBCom = (48 - lengthDealECom) / 2;
        String printGapAdjustECom = printGapAdjustCom.substring(0, Math.min(lengthDealEBCom, printGapAdjustCom.length()));

        int lengthDealFCom = controlList.get(0).getFCONTROL_COM_EMAIL().length();
        int lengthDealFBCom = (48 - lengthDealFCom) / 2;
        String printGapAdjustFCom = printGapAdjustCom.substring(0, Math.min(lengthDealFBCom, printGapAdjustCom.length()));

        String subTitleheadACom = printGapAdjustACom + controlList.get(0).getFCONTROL_COM_NAME();
        String subTitleheadBCom = printGapAdjustBCom + controlList.get(0).getFCONTROL_COM_ADD1();
        String subTitleheadCCom = printGapAdjustCCom + controlList.get(0).getFCONTROL_COM_ADD2() + ", " + controlList.get(0).getFCONTROL_COM_ADD3() + ".";
        String subTitleheadDCom = printGapAdjustDCom + controlList.get(0).getFCONTROL_COM_TEL1();
        String subTitleheadECom = printGapAdjustECom + controlList.get(0).getFCONTROL_COM_WEB();
        String subTitleheadFCom = printGapAdjustFCom + controlList.get(0).getFCONTROL_COM_EMAIL();
        String subTitleheadGCom = printLineSeperator;

        String title_Print_ACom = "\r\n" + subTitleheadACom;
        String title_Print_BCom = "\r\n" + subTitleheadBCom;
        String title_Print_CCom = "\r\n" + subTitleheadCCom;
        String title_Print_DCom = "\r\n" + subTitleheadDCom;
        String title_Print_ECom = "\r\n" + subTitleheadECom;
        String title_Print_FCom = "\r\n" + subTitleheadFCom;
        String title_Print_GCom = "\r\n" + subTitleheadGCom;

        Heading_a = title_Print_ACom + title_Print_BCom + title_Print_CCom + title_Print_DCom + title_Print_ECom + title_Print_FCom + title_Print_GCom;

        String printGapAdjust = "                        ";

        String SalesRepNamestr = "Sales Rep :" + salrep.getNAME().trim();

        int lengthDealE = SalesRepNamestr.length();
        int lengthDealEB = (48 - lengthDealE) / 2;
        String printGapAdjustE = printGapAdjust.substring(0, Math.min(lengthDealEB, printGapAdjust.length()));
        String subTitleheadF = printGapAdjustE + SalesRepNamestr;

        String SalesRepPhonestr = "Tele :" + salrep.getMOBILE().trim();
        int lengthDealF = SalesRepPhonestr.length();
        int lengthDealFB = (48 - lengthDealF) / 2;
        String printGapAdjustF = printGapAdjust.substring(0, Math.min(lengthDealFB, printGapAdjust.length()));
        String subTitleheadG = printGapAdjustF + SalesRepPhonestr;

        String subTitleheadH = printLineSeperator;

        RecHed recHed = new RecHedDS(context).getReceiptByRefno(PRefno);
        Debtor debtor = new DebtorDS(context).getSelectedCustomerByCode(recHed.getFPRECHED_DEBCODE());

        int lengthDealI = debtor.getFDEBTOR_NAME().length();
        int lengthDealIB = (48 - lengthDealI) / 2;
        String printGapAdjustI = printGapAdjust.substring(0, Math.min(lengthDealIB, printGapAdjust.length()));

        String customerAddressStr = debtor.getFDEBTOR_ADD1() + "," + debtor.getFDEBTOR_ADD2();
        int lengthDealJ = customerAddressStr.length();
        int lengthDealJB = (48 - lengthDealJ) / 2;
        String printGapAdjustJ = printGapAdjust.substring(0, Math.min(lengthDealJB, printGapAdjust.length()));

        int lengthDealK = debtor.getFDEBTOR_ADD3().length();
        int lengthDealKB = (48 - lengthDealK) / 2;
        String printGapAdjustK = printGapAdjust.substring(0, Math.min(lengthDealKB, printGapAdjust.length()));

        int lengthDealL = debtor.getFDEBTOR_TELE().length();
        int lengthDealLB = (48 - lengthDealL) / 2;
        String printGapAdjustL = printGapAdjust.substring(0, Math.min(lengthDealLB, printGapAdjust.length()));

        String subTitleheadI = printGapAdjustI + debtor.getFDEBTOR_NAME();
        String subTitleheadJ = printGapAdjustJ + debtor.getFDEBTOR_ADD1() + "," + debtor.getFDEBTOR_ADD2();
        String subTitleheadK = printGapAdjustK + debtor.getFDEBTOR_ADD3();
        String subTitleheadL = printGapAdjustL + debtor.getFDEBTOR_TELE();

        String subTitleheadO = printLineSeperator;

        String subTitleheadM = "Receipt Date : " + recHed.getFPRECHED_TXNDATE();
        int lengthDealM = subTitleheadM.length();
        int lengthDealMB = (48 - lengthDealM) / 2;
        String printGapAdjustM = printGapAdjust.substring(0, Math.min(lengthDealMB, printGapAdjust.length()));

        String subTitleheadN = "Receipt No : " + PRefno;
        int lengthDealN = subTitleheadN.length();
        int lengthDealNB = (48 - lengthDealN) / 2;
        String printGapAdjustN = printGapAdjust.substring(0, Math.min(lengthDealNB, printGapAdjust.length()));

        String subTitleheadR;

        if (recHed.getFPRECHED_REMARKS().equals(""))
            subTitleheadR = "Remarks : None";
        else
            subTitleheadR = "Remarks : " + recHed.getFPRECHED_REMARKS();

        int lengthDealR = subTitleheadR.length();
        int lengthDealRB = (48 - lengthDealR) / 2;
        String printGapAdjustR = printGapAdjust.substring(0, Math.min(lengthDealRB, printGapAdjust.length()));

        subTitleheadM = printGapAdjustM + subTitleheadM;
        // subTitleheadMD = printGapAdjustMD + subTitleheadMD;
        subTitleheadN = printGapAdjustN + subTitleheadN;
        subTitleheadR = printGapAdjustR + subTitleheadR;

        String title_Print_F = "\r\n" + subTitleheadF;
        String title_Print_G = "\r\n" + subTitleheadG;
        String title_Print_H = "\r\n" + subTitleheadH;

        String title_Print_I = "\r\n" + subTitleheadI;
        String title_Print_J = "\r\n" + subTitleheadJ;
        String title_Print_K = "\r\n" + subTitleheadK;
        String title_Print_L = "\r\n" + subTitleheadL;
        String title_Print_O = "\r\n" + subTitleheadO;

        String title_Print_M = "\r\n" + subTitleheadM;
        String title_Print_N = "\r\n" + subTitleheadN;
        String title_Print_R = "\r\n" + subTitleheadR;

        Heading_d = "";
        countCountInv = 0;

        if (subTitleheadK.toString().equalsIgnoreCase(" ")) {
            Heading_bmh = "\r" + title_Print_F + title_Print_G + title_Print_H + title_Print_I + title_Print_J + title_Print_O + title_Print_M + title_Print_N + title_Print_R;
        } else
            Heading_bmh = "\r" + title_Print_F + title_Print_G + title_Print_H + title_Print_I + title_Print_J + title_Print_K + title_Print_L + title_Print_O + title_Print_M + title_Print_N + title_Print_R;

        String title_cb = "\r\nINVNO INV DATE REP   DUE     PAID   DAYS";

        Heading_b = "\r\n" + printLineSeperator + title_cb + "\r\n" + printLineSeperator;

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*Item details*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        ArrayList<RecDet> list = new RecDetDS(context).GetReceiptByRefno(PRefno);

        String SPACE1, SPACE2;

        for (RecDet recDet : list) {
            String Refno = recDet.getFPRECDET_SALEREFNO();
            String BalAmt = formatDecimal((recDet.getFPRECDET_AMT()));
            String Amt = formatDecimal(recDet.getFPRECDET_ALOAMT());
            SPACE1 = String.format("%" + (30 - (Refno.length() + BalAmt.length())) + "s", " ");
            SPACE2 = String.format("%" + (18 - (Amt.length())) + "s", " ");
            Heading_d += "\r\n" + Refno + SPACE1 + BalAmt + SPACE2 + Amt;
        }

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String space;

        space = String.format("%" + (48 - ("Total Allocation".length() + formatDecimal(recHed.getFPRECHED_TOTALAMT()).length())) + "s", " ");
        String summaryTitle_c_Val = "Total Allocation" + space + formatDecimal(recHed.getFPRECHED_TOTALAMT());

        space = String.format("%" + (48 - ("Pay Mode".length() + recHed.getFPRECHED_PAYTYPE().length())) + "s", " ");
        String summaryTitle_e_Val = "Pay Mode" + space + recHed.getFPRECHED_PAYTYPE();

        space = String.format("%" + (48 - ("Cheque No".length() + recHed.getFPRECHED_CHQNO().length())) + "s", " ");
        String summeryCHqNo = "Cheque No" + space + recHed.getFPRECHED_CHQNO();

        space = String.format("%" + (48 - ("Cheque Date".length() + recHed.getFPRECHED_CHQDATE().length())) + "s", " ");
        String summeryCHqDate = "Cheque Date" + space + recHed.getFPRECHED_CHQDATE();


        space = String.format("%" + (48 - ("Bank".length() + recHed.getFPRECHED_CUSBANK().length())) + "s", " ");
        String summaryBank = "Bank" + space + recHed.getFPRECHED_CUSBANK();

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String summaryBottom_cpoyline1 = " Developed by Datamation Systems. ";
        int lengthsummarybottm = summaryBottom_cpoyline1.length();
        int lengthsummarybottmline1 = (48 - lengthsummarybottm) / 2;
        String printGapbottmline1 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline1, printGapAdjust.length()));

        String summaryBottom_cpoyline3 = "www.datamation.lk";
        int lengthsummarybotline3 = summaryBottom_cpoyline3.length();
        int lengthsummarybottmline3 = (48 - lengthsummarybotline3) / 2;
        String printGapbottmline3 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline3, printGapAdjust.length()));

        String summaryBottom_cpoyline2 = " +94 11 2 501202 / + 94 (0) 777 899899 ";
        int lengthsummarybotline2 = summaryBottom_cpoyline2.length();
        int lengthsummarybottmline2 = (48 - lengthsummarybotline2) / 2;
        String printGapbottmline2 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline2, printGapAdjust.length()));

        String bottomTitleString = "";

        if (recHed.getFPRECHED_PAYTYPE().equals("CHEQUE"))
            bottomTitleString = summaryTitle_c_Val + "\r\n" + summaryTitle_e_Val + "\r\n" + summeryCHqNo + "\r\n" + summaryBank + "\r\n" + summeryCHqDate;
        else
            bottomTitleString = summaryTitle_c_Val + "\r\n" + summaryTitle_e_Val;

        String buttomTitlef = "\r\n\n\n" + "Receipt Accepted................................";
        String buttomTitlefa = "\r\n\n\n" + "Please place The Rubber Stamp.";
        String buttomTitlecopyw = "\r\n\n\n" + printGapbottmline1 + summaryBottom_cpoyline1;
        String buttomTitlecopywbottom = "\r\n" + printGapbottmline2 + summaryBottom_cpoyline2;
        String buttomTitlecopywbottom3 = "\r\n" + printGapbottmline3 + summaryBottom_cpoyline3;

        buttomRaw = "\r\n" + printLineSeperator + "\r\n" + bottomTitleString + "\r\n" + printLineSeperator + "\r\n" + buttomTitlef + buttomTitlefa + "\r\n" + printLineSeperator + buttomTitlecopyw + buttomTitlecopywbottom + buttomTitlecopywbottom3 + "\r\n\n\n\n\n\n\n" + printLineSeperator + "\n";

        callPrintDevice();

    }

    public void PrintCurrentview() {
        checkPrinter();
        if (PRINTER_MAC_ID.equals("404")) {
            Log.v("", "No MAC Address Found.Enter Printer MAC Address.");
            Toast.makeText(context, "No MAC Address Found.Enter Printer MAC Address.", Toast.LENGTH_LONG).show();
        } else {
            printItems();
        }
    }


    public String formatDecimal(String sVal) {

        return String.format("%,.2f", Double.parseDouble(sVal));
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

            Toast.makeText(context, "Device has no bluetooth capability...", Toast.LENGTH_SHORT).show();

        } else {
            if (!mBTAdapter.isEnabled()) {
                Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // int REQUEST_ENABLE_BT = 1;
                // startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT);
            }
            printBillToDevice(PRINTER_MAC_ID);
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            // registerReceiver(mReceiver, filter); // Don't forget to
            // unregister
            // during
            // onDestroy

        }
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            try {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    System.out.println("***" + device.getName() + " : " + device.getAddress());

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

    public void printBillToDevice(final String address) {

        mBTAdapter.cancelDiscovery();
        try {
            System.out.println("**************************#****connecting");
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
            // setResult(RESULT_OK);
            // finish();
        } catch (Exception e) {
            Log.e("Class ", "fire 2 ", e);
            // toast.createToastErrorMessage("Device has no MacAddress.Please Enter the MAC Address..",
            // null);
            Toast.makeText(context, "Printer Device Disable Or Invalid MAC.Please Enable the Printer or MAC Address.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            this.PrintDetailsDialogbox(context, "", PRefno);
            // setResult(RESULT_CANCELED);
            // finish();

        }

    }

    // protected void onDestroy() {
    // Log.i("Dest ", "Checking Ddest");
    // //finish();
    // try {
    // if (dialogProgress != null)
    // dialogProgress.dismiss();
    // if (mBTAdapter != null)
    // mBTAdapter.cancelDiscovery();
    // this.unregisterReceiver(mReceiver);
    // } catch (Exception e) {
    // Log.e("Class ", "fire 3", e);
    // }
    // }

}
