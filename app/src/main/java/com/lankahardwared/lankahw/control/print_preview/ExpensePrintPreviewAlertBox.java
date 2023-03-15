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
import android.widget.ListView;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.PrintExpenseItemAdapter;
import com.lankahardwared.lankahw.control.ListExpandHelper;
import com.lankahardwared.lankahw.data.ControlDS;
import com.lankahardwared.lankahw.data.ExpensePrintDS;
import com.lankahardwared.lankahw.data.LocationsDS;
import com.lankahardwared.lankahw.data.RouteDS;
import com.lankahardwared.lankahw.model.Control;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.ExpesePrintPre;
import com.lankahardwared.lankahw.model.SalRep;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ExpensePrintPreviewAlertBox {

    public static final String SETTINGS = "SETTINGS";
    /**
     * Standard activity result: operation canceled.
     */
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = -1;
    /**
     * Start of user-defined activity results.
     */

    // Shared Preferences variables
    public static final String SETTING = "SETTINGS";
    public static SharedPreferences localSP;
    Control control;
    SalRep salRep;
    Debtor debtor;
    ExpesePrintPre expesePrintPre;
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
    String Heading_c = "";
    String FinalLine = "";
    String BILL;
    Dialog dialogProgress;
    ListView lvItemDetails;
    String PRefno = "";
    String printMainInvDiscount, printMainInvDiscountVal,
            PrintNetTotalValuePrintVal, printCaseQuantity, printPicesQuantity,
            printTotalAmount;
    int countCountInv;
    BluetoothAdapter mBTAdapter;
    BluetoothSocket mBTSocket = null;
    String PRINTER_MAC_ID;// ="00:22:58:3A:C6:49";
    Context context;
    private ControlDS dsControlDS;
    private ExpensePrintDS dsExpensePrintDS;
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

    public ExpensePrintPreviewAlertBox(Context context) {

        this.context = context;

    }

    @SuppressWarnings("unused")
    public void PrintDetailsDialogbox(final Context context, String title, String refno) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View promptView = layoutInflater.inflate(R.layout.sales_management_expense_print_view, null);
//        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

        final TextView Companyname = (TextView) promptView.findViewById(R.id.headcompanyname);
        final TextView Companyaddress1 = (TextView) promptView.findViewById(R.id.headaddress1);
        final TextView Companyaddress2 = (TextView) promptView.findViewById(R.id.headaddress2);
        final TextView CompanyTele = (TextView) promptView.findViewById(R.id.headteleno);
        final TextView Companyweb = (TextView) promptView.findViewById(R.id.headwebsite);
        final TextView Companyemail = (TextView) promptView.findViewById(R.id.heademail);

        final TextView RefNo = (TextView) promptView.findViewById(R.id.printrefno);
        final TextView LoadDate = (TextView) promptView.findViewById(R.id.printdate);

        final TextView TitleRow = (TextView) promptView.findViewById(R.id.docheadtile);

        final TextView TotalNetValue = (TextView) promptView.findViewById(R.id.printtotvalue);

        final TextView TotalCaseQty = (TextView) promptView.findViewById(R.id.printtotcases);
        final TextView TotalPieceQty = (TextView) promptView.findViewById(R.id.printtotpieces);
        final TextView TotalValue = (TextView) promptView.findViewById(R.id.printtotvalue);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title.toUpperCase());

        alertDialogBuilder.setView(promptView);

        RouteDS routeDS = new RouteDS(context);
        dsControlDS = new ControlDS(context);

        ArrayList<Control> controlList;
        controlList = dsControlDS.getAllControl();

        PRefno = refno;

        // Print Preview Company Details.
        Companyname.setText(controlList.get(0).getFCONTROL_COM_NAME());
        Companyaddress1.setText(controlList.get(0).getFCONTROL_COM_ADD1());
        Companyaddress2.setText(controlList.get(0).getFCONTROL_COM_ADD2());
        CompanyTele.setText(controlList.get(0).getFCONTROL_COM_TEL1());
        Companyweb.setText(controlList.get(0).getFCONTROL_COM_WEB());
        Companyemail.setText(controlList.get(0).getFCONTROL_COM_EMAIL());

        dsExpensePrintDS = new ExpensePrintDS(context);
        ArrayList<ExpesePrintPre> expenseprintList;
        expenseprintList = dsExpensePrintDS.getAllExpenseListPreview(refno);

        LocationsDS LOCF = new LocationsDS(context);

        // Print Sales Order header Details.
        RefNo.setText(expenseprintList.get(0).getEXPENSE_PRINT_REF_NO());
        LoadDate.setText(expenseprintList.get(0).getEXPENSE_PRINT_DATE());

        TitleRow.setText("Expense");

        // Print Calculation Summary Details.

        // Print Sales Order body Details.
        lvItemDetails = (ListView) promptView.findViewById(R.id.dmstockloadingdetlist);
        lvItemDetails.setAdapter(new PrintExpenseItemAdapter(context, expenseprintList));

        // Print Calculation Summary Details.

        TotalValue.setText(dsExpensePrintDS.getTotalAmountReturns(refno));

        // oncreate
//        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

        // Printer MAC Address
        PRINTER_MAC_ID = localSP.getString("printer_mac_address", "").toString();
        Log.v("mac_id", PRINTER_MAC_ID);

        // alertDialogBuilder.setCancelable(false).setPositiveButton("Print",
        // new DialogInterface.OnClickListener() {
        // public void onClick(DialogInterface dialog, int id) {
        // Log.v("", "***************************");
        // PrintCurrentview();
        //
        //
        // }
        // });

        alertDialogBuilder.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();

        ListExpandHelper.getListViewSize(lvItemDetails);
    }

    public void printItems() {

        // // Print Company Information
        String printGapAdjustCom = "                        ";

        ArrayList<Control> controlList;
        controlList = dsControlDS.getAllControl();

        ArrayList<ExpesePrintPre> expenseprintList;
        expenseprintList = dsExpensePrintDS.getAllExpenseListPreview(PRefno);

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

        String HeadName = "";

        HeadName = "Expense";

        int lengthDealHCom = HeadName.length();
        int lengthDealHBCom = (48 - lengthDealHCom) / 2;
        String printGapAdjustHCom = printGapAdjustCom.substring(0, Math.min(lengthDealHBCom, printGapAdjustCom.length()));

        String subTitleheadACom = printGapAdjustACom + controlList.get(0).getFCONTROL_COM_NAME();
        String subTitleheadBCom = printGapAdjustBCom + controlList.get(0).getFCONTROL_COM_ADD1();
        String subTitleheadCCom = printGapAdjustCCom + controlList.get(0).getFCONTROL_COM_ADD2() + ", " + controlList.get(0).getFCONTROL_COM_ADD3() + ".";
        String subTitleheadDCom = printGapAdjustDCom + controlList.get(0).getFCONTROL_COM_TEL1();
        String subTitleheadECom = printGapAdjustECom + controlList.get(0).getFCONTROL_COM_WEB();
        String subTitleheadFCom = printGapAdjustFCom + controlList.get(0).getFCONTROL_COM_EMAIL();
        String subTitleheadGCom = printLineSeperator;
        String subTitleheadHCom = printGapAdjustHCom + HeadName;

        String title_Print_ACom = /* "\r\n" + */subTitleheadACom;
        String title_Print_BCom = "\r\n" + subTitleheadBCom;
        String title_Print_CCom = "\r\n" + subTitleheadCCom;
        String title_Print_DCom = "\r\n" + subTitleheadDCom;
        String title_Print_ECom = "\r\n" + subTitleheadECom;
        String title_Print_FCom = "\r\n" + subTitleheadFCom;
        String title_Print_GCom = "\r\n" + subTitleheadGCom;
        String title_Print_HCom = subTitleheadHCom;

        Heading_a = title_Print_ACom + title_Print_BCom + title_Print_CCom + title_Print_DCom + title_Print_ECom + title_Print_FCom + title_Print_GCom + title_Print_HCom;

        String printGapAdjust = "                        ";

        // **********Printing**********
        String printFormatSpaceHeading = "                    ";

        String StrPrintRefNo = expenseprintList.get(0).getEXPENSE_PRINT_REF_NO();
        String StrPrintTotalAmt = expenseprintList.get(0).getEXPENSE_PRINT_TOTAMT();

        // **********Printing**********
        String subTitlea = "Ref No" + printFormatSpaceHeading;
        String subTitleaPrint = subTitlea.substring(0, Math.min(16, subTitlea.length()));
        String subTitleb = "Date" + printFormatSpaceHeading;
        String subTitlebPrint = subTitleb.substring(0, Math.min(16, subTitleb.length()));
        // String subTitlec = "Loading From" + printFormatSpaceHeading;
        // String subTitlecPrint = subTitlec.substring(0,Math.min(16,
        // subTitlec.length()));
        // String subTitled = "Loading To"+ printFormatSpaceHeading;
        // String subTitledPrint = subTitled.substring(0,Math.min(16,
        // subTitled.length()));

        LocationsDS LOCF = new LocationsDS(context);

        String title_a = "\r\n" + subTitleaPrint + ":" + StrPrintRefNo;
        String title_b = "\r\n" + subTitlebPrint + ":" + expenseprintList.get(0).getEXPENSE_PRINT_DATE();
        // String title_c = "\r\n" + subTitlecPrint + ":"+
        // LOCF.getItemNameByCode(expenseprintList.get(0).getEXPENSE_PRINT_DATE());
        // String title_d = "\r\n" + subTitledPrint + ":"+
        // LOCF.getItemNameByCode(expenseprintList.get(0).getEXPENSE_PRINT_DATE());
        String title_e = "\r\n--------------------------------------------";
        String title_f = "  Product Name            (CS/PS)   (Rs)";
        String title_g = "\r\n    " + title_f;
        String title_h = "\r\n--------------------------------------------";

        Heading_b = "\r\n\n" + title_a + title_b + title_e + title_g + title_h;

        ArrayList<ExpesePrintPre> getloadHed = dsExpensePrintDS.getAllExpenseListPreview(PRefno);

        if (!getloadHed.isEmpty()) {
            for (ExpesePrintPre expesePrintPre : getloadHed) {

                countCountInv = countCountInv + 1;
                String indexcountinv = Integer.toString(countCountInv);

                String printSpaceName = "                    ";
                String StrPrintLitemNameIn = expesePrintPre.getEXPENSE_PRINT_EXPNAME() + printSpaceName;
                String PrintStrPrintLitemName = StrPrintLitemNameIn.substring(0, Math.min(24, StrPrintLitemNameIn.length()));

                String itemCasePieces = printSpaceName + expesePrintPre.getEXPENSE_PRINT_AMT() + "/" + expesePrintPre.getEXPENSE_PRINT_AMT();
                String PrintitemCasePiecesQty = itemCasePieces.substring(Math.max(itemCasePieces.length() - 8, 0));

                StrPrintTotalAmt = printSpaceName + expesePrintPre.getEXPENSE_PRINT_TOTAMT();
                String PrintStrPrintTotalAmt = StrPrintTotalAmt.substring(Math.max(StrPrintTotalAmt.length() - 12, 0));

                String StrSeqNo = indexcountinv + printSpaceName;
                StrSeqNo = StrSeqNo.substring(0, Math.min(2, StrSeqNo.length()));
                String title_i = "\r\n" + StrSeqNo + "." + PrintStrPrintLitemName + PrintitemCasePiecesQty + PrintStrPrintTotalAmt;

                Heading_c = Heading_c.concat(title_i);

            }
        }

        // Print Invoice Heading

        String buttomTitlehed = "\r\n____________________________________________";

        String printSpaceSumName = "                    ";
        String summaryTitle_a = "Total Quantity" + printSpaceSumName;
        summaryTitle_a = summaryTitle_a.substring(0, Math.min(20, summaryTitle_a.length()));

        printCaseQuantity = dsExpensePrintDS.getTotalCaseQtyReturns(PRefno);
        printPicesQuantity = dsExpensePrintDS.getTotalPieceQtyReturns(PRefno);
        printTotalAmount = dsExpensePrintDS.getTotalAmountReturns(PRefno);

        String buttomTitlea = "\r\n\n" + summaryTitle_a + "Cases  : " + printCaseQuantity;
        String buttomTitleab = "\r\n" + printSpaceSumName + "Pieces : " + printPicesQuantity;

        String title_i = "\r\n--------------------------------------------";
        Heading_d = "\r\nTotal Quantity :" + "       " + printCaseQuantity + "     " + printPicesQuantity + "    " + printTotalAmount;
        String title_j = "\r\n\n\nSignature    		: 				";
        buttomRaw = title_i + Heading_d + title_j;

        callPrintDevice();

    }

    public void PrintCurrentview() {
        checkPrinter();
        if (PRINTER_MAC_ID.equals("404")) {
            Log.v("", "No MAC Address Found.Enter Printer MAC Address.");
            android.widget.Toast.makeText(context, "No MAC Address Found.Enter Printer MAC Address.", android.widget.Toast.LENGTH_LONG).show();

        } else {

            printItems();
        }
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

        BILL = Heading_a + Heading_b + Heading_c + buttomRaw;
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
            // finish();
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
        } catch (Exception e) {
            Log.e("Class ", "fire 2 ", e);
            android.widget.Toast.makeText(context, "Printer Device Disable Or Invalid MAC.Please Enable the Printer or MAC Address.", android.widget.Toast.LENGTH_LONG).show();
            e.printStackTrace();
            this.PrintDetailsDialogbox(context, "", PRefno);
            BILL = "";

        }

    }

}
