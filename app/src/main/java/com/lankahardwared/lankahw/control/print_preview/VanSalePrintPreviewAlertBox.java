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
import com.lankahardwared.lankahw.adapter.PrintVanSaleItemAdapter;
import com.lankahardwared.lankahw.control.ListExpandHelper;
import com.lankahardwared.lankahw.data.ControlDS;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.InvDetDS;
import com.lankahardwared.lankahw.data.InvHedDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.model.Control;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.InvDet;
import com.lankahardwared.lankahw.model.InvHed;
import com.lankahardwared.lankahw.model.SalRep;
import com.lankahardwared.lankahw.model.StkIss;
import com.lankahardwared.lankahw.model.VanSalPrintPre;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class VanSalePrintPreviewAlertBox {

    public static final String SETTINGS = "SETTINGS";
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = -1;
    public static final String SETTING = "SETTINGS";
    public static SharedPreferences localSP;
    Control control;
    SalRep salRep;
    Debtor debtor;
    VanSalPrintPre vansalprintpre;
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
    LinearLayout lnComp, lnCus, lnTerm;
    Dialog dialogProgress;
    ListView lvItemDetails;
    String PRefno = "";
    String printMainInvDiscount, printMainInvDiscountVal, PrintNetTotalValuePrintVal, printCaseQuantity,
            printPicesQuantity, TotalInvoiceDiscount;
    int countCountInv;
    BluetoothAdapter mBTAdapter;
    BluetoothSocket mBTSocket = null;
    String PRINTER_MAC_ID;
    Context context;
    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            try {
                String action = intent.getAction();

                if (BluetoothDevice.ACTION_FOUND.equals(action)) {

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

    public VanSalePrintPreviewAlertBox(Context context) {
        this.context = context;
    }

	/*-*-*-*-*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-**-*-**-*-*-*--*/

    public void PrintDetailsDialogbox(final Context context, String title, String refno) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View promptView = layoutInflater.inflate(R.layout.sales_management_vansales_print_view, null);
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

        final TextView SalOrdDate = (TextView) promptView.findViewById(R.id.printsalorddate);
        final TextView OrderNo = (TextView) promptView.findViewById(R.id.printrefno);
        final TextView Remarks = (TextView) promptView.findViewById(R.id.printremark);

        final TextView txtfiQty = (TextView) promptView.findViewById(R.id.printFiQty);
        final TextView TotalDiscount = (TextView) promptView.findViewById(R.id.printtotaldisamt);
        final TextView TotalNetValue = (TextView) promptView.findViewById(R.id.printnettotal);

        final TextView txtTotVal = (TextView) promptView.findViewById(R.id.printTotalVal);
        final TextView TotalPieceQty = (TextView) promptView.findViewById(R.id.printpiecesqty);
        final TextView txtRoute = (TextView) promptView.findViewById(R.id.printRoute);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title.toUpperCase());

        alertDialogBuilder.setView(promptView);

        ArrayList<Control> controlList;
        controlList = new ControlDS(context).getAllControl();

        PRefno = refno;

        Companyname.setText(controlList.get(0).getFCONTROL_COM_NAME());
        Companyaddress1.setText(controlList.get(0).getFCONTROL_COM_ADD1());
        Companyaddress2.setText(controlList.get(0).getFCONTROL_COM_ADD2());
        CompanyTele.setText("Tele: " + controlList.get(0).getFCONTROL_COM_TEL1() + " / Fax: " + controlList.get(0).getFCONTROL_COM_FAX());
        Companyweb.setText(controlList.get(0).getFCONTROL_COM_WEB());
        Companyemail.setText(controlList.get(0).getFCONTROL_COM_EMAIL());

        SalRep salrep = new SalRepDS(context).getSaleRep(new SalRepDS(context).getCurrentRepCode());
        SalesRepname.setText(salrep.getREPCODE() + "/ " + salrep.getNAME());
        SalesRepPhone.setText("Tele: " + salrep.getMOBILE());

        InvHed invhed = new InvHedDS(context).getDetailsforPrint(refno);

        ArrayList<InvDet> list = new InvDetDS(context).getAllItemsforPrint(refno);

        Debtor debtor = new DebtorDS(context).getSelectedCustomerByCode(invhed.getFINVHED_DEBCODE());

        Debname.setText(debtor.getFDEBTOR_CODE() + "-" + debtor.getFDEBTOR_NAME());
        Debaddress1.setText(debtor.getFDEBTOR_ADD1() + ", " + debtor.getFDEBTOR_ADD2());
        Debaddress2.setText(debtor.getFDEBTOR_ADD3());
        DebTele.setText(debtor.getFDEBTOR_TELE());

        SalOrdDate.setText("Date: " + invhed.getFINVHED_TXNDATE() + " " + currentTime());
        Remarks.setText("Remarks: " + invhed.getFINVHED_REMARKS());
        OrderNo.setText("Ref No: " + refno);
        txtRoute.setText(invhed.getFINVHED_TOURCODE() + " / " + invhed.getFINVHED_ROUTECODE());

        int qty = 0, fiQty = 0;
        double dDisc = 0, dTotAmt = 0;

        for (InvDet det : list) {

            if (det.getFINVDET_TYPE().equals("SA"))
                qty += Integer.parseInt(det.getFINVDET_QTY());
            else
                fiQty += Integer.parseInt(det.getFINVDET_QTY());

            dDisc += Double.parseDouble(det.getFINVDET_DISVALAMT());
            dTotAmt += Double.parseDouble(det.getFINVDET_AMT());
        }

        lvItemDetails = (ListView) promptView.findViewById(R.id.vansaleList);
        lvItemDetails.setAdapter(new PrintVanSaleItemAdapter(context, list));

		/*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-Gross/Net values*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        TotalPieceQty.setText(String.valueOf(qty));
        TotalNetValue.setText(String.format("%,.2f", dTotAmt));
        TotalDiscount.setText(String.format("%,.2f", (dDisc)));
        txtfiQty.setText(String.valueOf(fiQty));
        txtTotVal.setText(String.format("%,.2f", (dTotAmt + dDisc)));

//        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        PRINTER_MAC_ID = localSP.getString("printer_mac_address", "").toString();

        alertDialogBuilder.setCancelable(false).setPositiveButton("Print", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
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
        ListExpandHelper.getListViewSize(lvItemDetails);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public double getNonTaxTotal(ArrayList<InvDet> list) {

        double totAmt = 0;

        for (InvDet det : list) {
            double sellPrice = Double.parseDouble(det.getFINVDET_SELL_PRICE()) - (Double.parseDouble(det.getFINVDET_TAX_AMT()) / Double.parseDouble(det.getFINVDET_QTY()));
            totAmt += Double.parseDouble(det.getFINVDET_QTY()) * sellPrice;
        }

        return totAmt;
    }

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

    public void printItems() {
        final int LINECHAR = 44;
        String printGapAdjustCom = "                      ";

        ArrayList<Control> controlList;
        controlList = new ControlDS(context).getAllControl();

        SalRep salrep = new SalRepDS(context).getSaleRep(new SalRepDS(context).getCurrentRepCode());

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

        String TelCom = "Tel: " + controlList.get(0).getFCONTROL_COM_TEL1().trim() + " / Fax: " + controlList.get(0).getFCONTROL_COM_FAX().trim();
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
        String subTitleheadDCom = printGapAdjustDCom + "Tel: " + controlList.get(0).getFCONTROL_COM_TEL1() + " / Fax: " + controlList.get(0).getFCONTROL_COM_FAX().trim();
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

        String SalesRepNamestr = "Sales Rep: " + salrep.getREPCODE() + "/ " + salrep.getNAME().trim();// +
        // "/
        // "
        // +
        // salrep.getLOCCODE();

        int lengthDealE = SalesRepNamestr.length();
        int lengthDealEB = (LINECHAR - lengthDealE) / 2;
        String printGapAdjustE = printGapAdjust.substring(0, Math.min(lengthDealEB, printGapAdjust.length()));
        String subTitleheadF = printGapAdjustE + SalesRepNamestr;

        String SalesRepPhonestr = "Tele: " + salrep.getTELE().trim();
        int lengthDealF = SalesRepPhonestr.length();
        int lengthDealFB = (LINECHAR - lengthDealF) / 2;
        String printGapAdjustF = printGapAdjust.substring(0, Math.min(lengthDealFB, printGapAdjust.length()));
        String subTitleheadG = printGapAdjustF + SalesRepPhonestr;

        String subTitleheadH = printLineSeperator;

        InvHed invHed = new InvHedDS(context).getDetailsforPrint(PRefno);
        Debtor debtor = new DebtorDS(context).getSelectedCustomerByCode(invHed.getFINVHED_DEBCODE());

        int lengthDealI = debtor.getFDEBTOR_CODE().length() + "-".length() + debtor.getFDEBTOR_NAME().length();
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

        String subTitleheadI = printGapAdjustI + debtor.getFDEBTOR_CODE() + "-" + debtor.getFDEBTOR_NAME();
        String subTitleheadJ = printGapAdjustJ + debtor.getFDEBTOR_ADD1() + "," + debtor.getFDEBTOR_ADD2();
        String subTitleheadK = printGapAdjustK + debtor.getFDEBTOR_ADD3();
        String subTitleheadL = printGapAdjustL + debtor.getFDEBTOR_TELE();
        String subTitleheadTIN = printGapCusTIn + "TIN No: " + debtor.getFDEBTOR_CUS_VATNO();

        String subTitleheadO = printLineSeperator;

        String subTitleheadM = "VJO Date: " + invHed.getFINVHED_TXNDATE() + " " + currentTime();
        int lengthDealM = subTitleheadM.length();
        int lengthDealMB = (LINECHAR - lengthDealM) / 2;
        String printGapAdjustM = printGapAdjust.substring(0, Math.min(lengthDealMB, printGapAdjust.length()));

        String subTitleheadN = "VJO Number: " + PRefno;
        int lengthDealN = subTitleheadN.length();
        int lengthDealNB = (LINECHAR - lengthDealN) / 2;
        String printGapAdjustN = printGapAdjust.substring(0, Math.min(lengthDealNB, printGapAdjust.length()));

        // String TempsubTermCode = "Terms: " + invHed.getFINVHED_TERMCODE() +
        // "/" + new
        // TermDS(context).getTermDetails(invHed.getFINVHED_TERMCODE());
        // int lenTerm = TempsubTermCode.length();
        // String sp = String.format("%" + ((LINECHAR - lenTerm) / 2) + "s", "
        // ");
        // TempsubTermCode = sp + "Terms: " + invHed.getFINVHED_TERMCODE() + "/"
        // + new TermDS(context).getTermDetails(invHed.getFINVHED_TERMCODE());

        String subTitleheadR;

        if (invHed.getFINVHED_REMARKS().equals(""))
            subTitleheadR = "Remarks : None";
        else
            subTitleheadR = "Remarks : " + invHed.getFINVHED_REMARKS();

        int lengthDealR = subTitleheadR.length();
        int lengthDealRB = (LINECHAR - lengthDealR) / 2;
        String printGapAdjustR = printGapAdjust.substring(0, Math.min(lengthDealRB, printGapAdjust.length()));

        subTitleheadM = printGapAdjustM + subTitleheadM;
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
        String title_Print_N = "\r\n" + subTitleheadN;
        String title_Print_R = "\r\n";// + TempsubTermCode + "\r\n" +
        // subTitleheadR;

        ArrayList<InvDet> itemList = new InvDetDS(context).getAllItemsforPrint(PRefno);

        BigDecimal compDisc = BigDecimal.ZERO;// new
        // BigDecimal(itemList.get(0).getFINVDET_COMDISPER().toString());
        BigDecimal cusDisc = BigDecimal.ZERO;// new
        // BigDecimal(itemList.get(0).getFINVDET_CUSDISPER().toString());
        BigDecimal termDisc = BigDecimal.ZERO;// new
        // BigDecimal(itemList.get(0).getFINVDET_TERM_DISPER().toString());

        Heading_d = "";
        countCountInv = 0;

        if (subTitleheadK.toString().equalsIgnoreCase(" ")) {
            Heading_bmh = "\r" + title_Print_F + title_Print_G + title_Print_H + title_Print_I + title_Print_J + title_Print_O + title_Print_M + title_Print_N + title_Print_R;
        } else
            Heading_bmh = "\r" + title_Print_F + title_Print_G + title_Print_H + title_Print_I + title_Print_J + title_Print_K + title_Print_L + title_Print_O + title_Print_M + title_Print_N + title_Print_R;

        String title_cb = "\r\nPRODUCT  MRP  BRAND  QTY    COST      AMOUNT";
        String title_cc = "\r\n(SIZECODE x QTY)                                ";

        Heading_b = "\r\n" + printLineSeperator + title_cb + title_cc + "\r\n" + printLineSeperator + "\r\n";

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*Individual Item details*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        int totQty = 0;
        ArrayList<StkIss> list = new ArrayList<StkIss>();

        for (InvDet det : itemList) {
            totQty += Integer.parseInt(det.getFINVDET_QTY());
            // list.addAll(new StkIssDS(context).getItemlist(PRefno,
            // det.getFINVDET_ITEM_CODE()));
        }

        int nos = 1;
        String SPACE1, SPACE2, SPACE3, SPACE4, SPACE5;

        for (StkIss iss : list) {

            String sItemcode = iss.getITEMCODE();
            String sQty = iss.getQTY();
            // String sMRP = iss.getPRICE().substring(0, iss.getPRICE().length()
            // - 3);

            String sPrice = "", sTotal = "";

            // sPrice = String.format(Locale.US, "%.2f",
            // (Double.parseDouble(iss.getPRICE()) / 100) * (100 -
            // compDisc.doubleValue()));
            // sTotal = String.format(Locale.US, "%.2f",
            // Double.parseDouble(sPrice.replace(",", "")) *
            // Double.parseDouble(iss.getQTY()));

            // SPACE1 = String.format("%" + (14 - (sItemcode.length() +
            // sMRP.length() + (String.valueOf(nos).length() + 2))) + "s", " ");
            // SPACE2 = String.format("%" + (5 - (iss.getBrand().length())) +
            // "s", " ");
            SPACE3 = String.format("%" + (5 - (sQty.length())) + "s", " ");
            SPACE4 = String.format("%" + (9 - (sPrice.length())) + "s", " ");
            SPACE5 = String.format("%" + (12 - (sTotal.length())) + "s", " ");

            Heading_d += ""; // "\r\n" + nos + ")" + sItemcode + SPACE1 + sMRP +
            // SPACE2 + iss.getBrand() + SPACE3 + sQty +
            // SPACE4 + sPrice + SPACE5 + sTotal;

            // Heading_d += "\r\n" + new
            // StkIssDS(context).getSizecodeString(sItemcode, iss.getPRICE(),
            // PRefno) + "\r\n";
            nos++;
        }

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String space = "";
        String sNetTot = "", sGross = "";

        // if (invHed.getFINVHED_INV_TYPE().equals("NON")) {
        sNetTot = String.format(Locale.US, "%,.2f", Double.parseDouble(invHed.getFINVHED_TOTALAMT()));
        // sGross = String.format(Locale.US, "%,.2f",
        // Double.parseDouble(invHed.getFINVHED_TOTALAMT()) +
        // Double.parseDouble(invHed.getFINVHED_TOTALDIS()));
        // } else {
        // sGross = String.format(Locale.US, "%,.2f", getNonTaxTotal(itemList));
        // sNetTot = String.format(Locale.US, "%,.2f", (getNonTaxTotal(itemList)
        // + Double.parseDouble(invHed.getFINVHED_TOTALTAX())) -
        // Double.parseDouble(invHed.getFINVHED_TOTALDIS()));
        // }

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Discounts*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        BigDecimal TotalAmt = new BigDecimal(Double.parseDouble(invHed.getFINVHED_TOTALAMT()) + Double.parseDouble(invHed.getFINVHED_TOTALDIS()));

        String sComDisc, sCusdisc = "0", sTermDisc = "0";
        String fullDisc_String = "";

        if (compDisc.doubleValue() > 0) {
            // sComDisc = String.format(Locale.US, "%,.2f", (TotalAmt.divide(new
            // BigDecimal("100"))).multiply(compDisc));
            TotalAmt = TotalAmt.divide(new BigDecimal("100")).multiply(new BigDecimal("100").subtract(compDisc));
            sGross = String.format(Locale.US, "%,.2f", TotalAmt);
            // space = String.format("%" + (LINECHAR -
            // (" Company Discount @ ".length() + compDisc.toString().length()
            // + "%".length() + sComDisc.length())) + "s", " ");
            // fullDisc_String += " Company Discount @ " + compDisc.toString()
            // + "%" + space + sComDisc + "\r\n";
        }

        if (cusDisc.doubleValue() > 0) {
            sCusdisc = String.format(Locale.US, "%,.2f", (TotalAmt.divide(new BigDecimal("100"))).multiply(cusDisc));
            TotalAmt = TotalAmt.divide(new BigDecimal("100")).multiply(new BigDecimal("100").subtract(cusDisc));
            space = String.format("%" + (LINECHAR - ("   Customer Discount @ ".length() + cusDisc.toString().length() + "%".length() + sCusdisc.length())) + "s", " ");
            fullDisc_String += "   Customer Discount @ " + cusDisc.toString() + "%" + space + sCusdisc + "\r\n";
        }

        if (termDisc.doubleValue() > 0) {
            sTermDisc = String.format(Locale.US, "%,.2f", (TotalAmt.divide(new BigDecimal("100"))).multiply(termDisc));
            TotalAmt = TotalAmt.divide(new BigDecimal("100")).multiply(new BigDecimal("100").subtract(termDisc));
            space = String.format("%" + (LINECHAR - ("   Term Discount @ ".length() + termDisc.toString().length() + "%".length() + sTermDisc.length())) + "s", " ");
            fullDisc_String += "   Term Discount @ " + termDisc.toString() + "%" + space + sTermDisc + "\r\n";
        }

        String sDisc = String.format(Locale.US, "%,.2f", Double.parseDouble(sTermDisc.replace(",", "")) + Double.parseDouble(sCusdisc.replace(",", "")));

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Gross Net values-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String printSpaceSumName = "                    ";
        String summaryTitle_a = "Total Quantity" + printSpaceSumName;
        summaryTitle_a = summaryTitle_a.substring(0, Math.min(20, summaryTitle_a.length()));

        space = String.format("%" + (LINECHAR - ("Total Quantity".length() + String.valueOf(totQty).length())) + "s", " ");
        String buttomTitlea = "\r\n\n\n" + "Total Quantity" + space + String.valueOf(totQty);

		/* print gross amount */
        space = String.format("%" + (LINECHAR - ("Total Value".length() + sGross.length())) + "s", " ");
        String summaryTitle_c_Val = "Total Value" + space + sGross;

        space = String.format("%" + (LINECHAR - ("Total Discount".length() + sDisc.length())) + "s", " ");
        String summaryDiscount = "Total Discount" + space + sDisc;

		/* print net total */
        space = String.format("%" + (LINECHAR - ("Net Total".length() + sNetTot.length())) + "s", " ");
        String summaryTitle_e_Val = "Net Total" + space + sNetTot;

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String summaryBottom_cpoyline1 = "by Datamation Systems / www.datamation.lk";
        int lengthsummarybottm = summaryBottom_cpoyline1.length();
        int lengthsummarybottmline1 = (LINECHAR - lengthsummarybottm) / 2;
        String printGapbottmline1 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline1, printGapAdjust.length()));

        // String summaryBottom_cpoyline3 = "www.datamation.lk";
        // int lengthsummarybotline3 = summaryBottom_cpoyline3.length();
        // int lengthsummarybottmline3 = (LINECHAR - lengthsummarybotline3) / 2;
        // String printGapbottmline3 = printGapAdjust.substring(0,
        // Math.min(lengthsummarybottmline3, printGapAdjust.length()));

        // String summaryBottom_cpoyline2 = " +94 11 2 501202 / + 94 (0) 777
        // 899899 ";
        // int lengthsummarybotline2 = summaryBottom_cpoyline2.length();
        // int lengthsummarybottmline2 = (LINECHAR - lengthsummarybotline2) / 2;
        // String printGapbottmline2 = printGapAdjust.substring(0,
        // Math.min(lengthsummarybottmline2, printGapAdjust.length()));

        String buttomTitlec = "\r\n" + summaryTitle_c_Val + "\r\n" + fullDisc_String + summaryDiscount;
        String buttomTitlee = "\r\n" + summaryTitle_e_Val;

        String buttomTitlef = "\r\n\n\n" + "------------------        ------------------" + "\r\n" + "     Customer               Sales Executive";

        String buttomTitlefa = "\r\n\n\n" + "Please place the rubber stamp.";
        String buttomTitlecopyw = "\r\n" + printGapbottmline1 + summaryBottom_cpoyline1;
        // String buttomTitlecopywbottom = "\r\n" + printGapbottmline2 +
        // summaryBottom_cpoyline2;
        // String buttomTitlecopywbottom3 = "\r\n" + printGapbottmline3 +
        // summaryBottom_cpoyline3;
        buttomRaw = printLineSeperator + buttomTitlea + buttomTitlec + "\r\n" + printLineSeperator + buttomTitlee + "\r\n" + printLineSeperator + "\r\n" + buttomTitlef + buttomTitlefa + "\r\n" + printLineSeperator + buttomTitlecopyw + "\r\n" + printLineSeperator + "\n";
        callPrintDevice();
    }

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

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

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

    private void checkPrinter() {

        if (PRINTER_MAC_ID.trim().length() == 0) {
            PRINTER_MAC_ID = "404";
        } else {
            PRINTER_MAC_ID = PRINTER_MAC_ID;
        }
    }

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

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
            android.widget.Toast.makeText(context, "Device has no bluetooth		 capability...", android.widget.Toast.LENGTH_SHORT).show();
        } else {
            if (!mBTAdapter.isEnabled()) {
                Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            }
            printBillToDevice(PRINTER_MAC_ID);
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        }
    }

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

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
            android.widget.Toast.makeText(context, "Printer Device Disable Or Invalid MAC.Please Enable the Printer or MAC Address.", android.widget.Toast.LENGTH_LONG).show();
            e.printStackTrace();
            this.PrintDetailsDialogbox(context, "", PRefno);
        }
    }

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

    private String currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}
