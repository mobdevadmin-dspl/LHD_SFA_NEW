package com.lankahardwared.lankahw.view.vansale;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.control.print_preview.VanSalePrintPreviewAlertBox;
import com.lankahardwared.lankahw.data.ControlDS;
import com.lankahardwared.lankahw.data.DebItemPriDS;
import com.lankahardwared.lankahw.data.DispDetDS;
import com.lankahardwared.lankahw.data.DispHedDS;
import com.lankahardwared.lankahw.data.DispIssDS;
import com.lankahardwared.lankahw.data.InvDetDS;
import com.lankahardwared.lankahw.data.InvHedDS;
import com.lankahardwared.lankahw.data.InvTaxDTDS;
import com.lankahardwared.lankahw.data.InvTaxRGDS;
import com.lankahardwared.lankahw.data.ItemLocDS;
import com.lankahardwared.lankahw.data.ItemsDS;
import com.lankahardwared.lankahw.data.OrdFreeIssueDS;
import com.lankahardwared.lankahw.data.OrderDiscDS;
import com.lankahardwared.lankahw.data.ProductDS;
import com.lankahardwared.lankahw.data.STKInDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.data.StkIssDS;
import com.lankahardwared.lankahw.data.TaxDetDS;
import com.lankahardwared.lankahw.model.InvDet;
import com.lankahardwared.lankahw.model.InvHed;
import com.lankahardwared.lankahw.model.Product;
import com.lankahardwared.lankahw.model.StkIn;
import com.lankahardwared.lankahw.view.IconPallet;
import com.lankahardwared.lankahw.view.MainActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class VanSalesSummary extends Fragment {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    View view;
    TextView lblGross, lblSchDisc, lblDisc, lblNetVal, lblLineDisc, lblQty;
    SharedPref mSharedPref;
    String RefNo = null;
    ArrayList<InvDet> list;
    Activity activity;
    String locCode;
    FloatingActionButton fabPause, fabDiscard, fabSave;
    FloatingActionMenu fam;
    MyReceiver r;
    int iTotFreeQty = 0;

    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        } else if (!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        return true;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Cancel order*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_van_sales_summary, container, false);

        mSharedPref = new SharedPref(getActivity());
//        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.VanNumVal));

        fabPause = (FloatingActionButton) view.findViewById(R.id.fab2);
        fabDiscard = (FloatingActionButton) view.findViewById(R.id.fab3);
        fabSave = (FloatingActionButton) view.findViewById(R.id.fab1);
        fam = (FloatingActionMenu) view.findViewById(R.id.fab_menu);

        lblNetVal = (TextView) view.findViewById(R.id.lblNetVal_Inv);
        lblDisc = (TextView) view.findViewById(R.id.lblDisc_Inv);
        lblSchDisc = (TextView) view.findViewById(R.id.lblSchDisc_Inv);
        lblLineDisc = (TextView) view.findViewById(R.id.lblLineDisc_Inv);
        lblGross = (TextView) view.findViewById(R.id.lblGross_Inv);
        lblQty = (TextView) view.findViewById(R.id.lblQty_Inv);

        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });

        fabPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPauseinvoice();
            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSummaryDialog();
            }
        });

        fabDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoEditingData();
            }
        });

        return view;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Clear Shared preference-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void undoEditingData() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Do you want to discard the invoice ?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                MainActivity activity = (MainActivity) getActivity();
                String result = new InvHedDS(getActivity()).restData(RefNo);

                if (!result.equals("")) {
                    new InvDetDS(getActivity()).restData(RefNo);
                    new OrderDiscDS(getActivity()).clearData(RefNo);
                    new OrdFreeIssueDS(getActivity()).ClearFreeIssues(RefNo);
                    new ProductDS(getActivity()).mClearTables();
                }

                activity.cusPosition = 0;
                activity.selectedDebtor = null;
                activity.selectedInvHed = null;
                Toast.makeText(getActivity(), "Invoice discarded successfully..!", Toast.LENGTH_SHORT).show();
                UtilityContainer.ClearVanSharedPref(getActivity());

                UtilityContainer.mLoadFragment(new VanSaleInvoice(),getActivity());

            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Save primary & secondary invoice-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*/

    public void mRefreshData() {

        int ftotQty = 0, fTotFree = 0;
        double ftotAmt = 0, fTotLineDisc = 0, fTotSchDisc = 0;

        locCode = new SharedPref(getActivity()).getGlobalVal("KeyLocCode");

        list = new InvDetDS(getActivity()).getAllInvDet(RefNo);

        for (InvDet ordDet : list) {
            ftotAmt += Double.parseDouble(ordDet.getFINVDET_AMT());

            if (ordDet.getFINVDET_TYPE().equals("SA"))
                ftotQty += Integer.parseInt(ordDet.getFINVDET_QTY());
            else
                fTotFree += Integer.parseInt(ordDet.getFINVDET_QTY());

            fTotLineDisc += Double.parseDouble(ordDet.getFINVDET_DIS_AMT());
            fTotSchDisc += Double.parseDouble(ordDet.getFINVDET_DISVALAMT());
        }

        iTotFreeQty = fTotFree;
        lblQty.setText(String.valueOf(ftotQty + fTotFree));
        lblGross.setText(String.format("%.2f", ftotAmt + fTotSchDisc + fTotLineDisc));
        lblDisc.setText(String.format("%.2f", fTotSchDisc + fTotLineDisc));
        lblNetVal.setText(String.format("%.2f", ftotAmt));
        lblSchDisc.setText(String.format("%.2f", fTotSchDisc));
        lblLineDisc.setText(String.format("%.2f", fTotLineDisc));

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void saveSummaryDialog() {

        if (new InvDetDS(getActivity()).getItemCount(RefNo) > 0) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Do you want to save the invoice ?");
            alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(final DialogInterface dialog, int id) {

                    InvHed sHed = new InvHed();
                    ArrayList<InvHed> invHedList = new ArrayList<InvHed>();

                    InvHed invHed = new InvHedDS(getActivity()).getActiveInvhed();

                    sHed.setFINVHED_REFNO(RefNo);
                    sHed.setFINVHED_DEBCODE(invHed.getFINVHED_DEBCODE());
                    sHed.setFINVHED_ADDDATE(invHed.getFINVHED_ADDDATE());
                    sHed.setFINVHED_MANUREF(invHed.getFINVHED_MANUREF());
                    sHed.setFINVHED_REMARKS(invHed.getFINVHED_REMARKS());
                    sHed.setFINVHED_ADDMACH(invHed.getFINVHED_ADDMACH());
                    sHed.setFINVHED_ADDUSER(invHed.getFINVHED_ADDUSER());
                    sHed.setFINVHED_CURCODE(invHed.getFINVHED_CURCODE());
                    sHed.setFINVHED_CURRATE(invHed.getFINVHED_CURRATE());
                    sHed.setFINVHED_LOCCODE(invHed.getFINVHED_LOCCODE());

                    sHed.setFINVHED_CUSTELE(invHed.getFINVHED_CUSTELE());
                    sHed.setFINVHED_CONTACT(invHed.getFINVHED_CONTACT());
                    sHed.setFINVHED_CUSADD1(invHed.getFINVHED_CUSADD1());
                    sHed.setFINVHED_CUSADD2(invHed.getFINVHED_CUSADD2());
                    sHed.setFINVHED_CUSADD3(invHed.getFINVHED_CUSADD3());
                    sHed.setFINVHED_TXNTYPE(invHed.getFINVHED_TXNTYPE());
                    sHed.setFINVHED_IS_ACTIVE(invHed.getFINVHED_IS_ACTIVE());
                    sHed.setFINVHED_IS_SYNCED(invHed.getFINVHED_IS_SYNCED());
                    sHed.setFINVHED_LOCCODE(invHed.getFINVHED_LOCCODE());
                    sHed.setFINVHED_AREACODE(invHed.getFINVHED_AREACODE());
                    sHed.setFINVHED_ROUTECODE(invHed.getFINVHED_ROUTECODE());
                    sHed.setFINVHED_COSTCODE(invHed.getFINVHED_COSTCODE());
                    sHed.setFINVHED_TAXREG(invHed.getFINVHED_TAXREG());
                    sHed.setFINVHED_TOURCODE(invHed.getFINVHED_TOURCODE());

                    sHed.setFINVHED_BPTOTALDIS("0");
                    sHed.setFINVHED_BTOTALAMT("0");
                    sHed.setFINVHED_BTOTALDIS("0");
                    sHed.setFINVHED_BTOTALTAX("0");
                    sHed.setFINVHED_END_TIME_SO(currentTime());
                    sHed.setFINVHED_START_TIME_SO(localSP.getString("Van_Start_Time", "").toString());
                    sHed.setFINVHED_LATITUDE(mSharedPref.getGlobalVal("Latitude").equals("") ? "0.00" : mSharedPref.getGlobalVal("Latitude"));
                    sHed.setFINVHED_LONGITUDE(mSharedPref.getGlobalVal("Longitude").equals("") ? "0.00" : mSharedPref.getGlobalVal("Longitude"));
                    sHed.setFINVHED_ADDRESS(localSP.getString("GPS_Address", "").toString());
                    sHed.setFINVHED_TOTALTAX("0");
                    sHed.setFINVHED_TOTALDIS(lblDisc.getText().toString());
                    sHed.setFINVHED_TOTALAMT(lblNetVal.getText().toString());
                    sHed.setFINVHED_TXNDATE(invHed.getFINVHED_TXNDATE());
                    sHed.setFINVHED_REPCODE(new SalRepDS(getActivity()).getCurrentRepCode());
                    sHed.setFINVHED_REFNO1("");
                    sHed.setFINVHED_TOTQTY(lblQty.getText().toString());
                    sHed.setFINVHED_TOTFREEQTY(iTotFreeQty + "");

                    invHedList.add(sHed);

                    if (new InvHedDS(getActivity()).createOrUpdateInvHed(invHedList) > 0) {
                        new InvHedDS(getActivity()).InactiveStatusUpdate(RefNo);
                        new InvDetDS(getActivity()).InactiveStatusUpdate(RefNo);
                        new ProductDS(getActivity()).mClearTables();

                        final MainActivity activity = (MainActivity) getActivity();

                        activity.cusPosition = 0;
                        activity.selectedDebtor = null;
                        activity.selectedInvHed = null;

						/*-*-*-*-*-*-*-*-*-*-QOH update-*-*-*-*-*-*-*-*-*/

                        UpdateTaxDetails(RefNo);
                        UpdateQOH_FIFO();
                        new ItemLocDS(getActivity()).UpdateInvoiceQOH(RefNo, "-", locCode);
                        updateDispTables(sHed);

                        new VanSalePrintPreviewAlertBox(getActivity()).PrintDetailsDialogbox(getActivity(), "Print preview", RefNo);
                        new ReferenceNum(getActivity()).nNumValueInsertOrUpdate(getResources().getString(R.string.VanNumVal));
                        Toast.makeText(getActivity(), "Invoice saved successfully..!", Toast.LENGTH_SHORT).show();
                        UtilityContainer.ClearVanSharedPref(getActivity());
                        loadFragment(new VanSaleInvoice());

                    } else {
                        Toast.makeText(getActivity(), "Failed..", Toast.LENGTH_SHORT).show();
                    }

                }

            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alertD = alertDialogBuilder.create();
            alertD.show();
        } else
            Toast.makeText(activity, "Add items before save ...!", Toast.LENGTH_SHORT).show();


    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void UpdateTaxDetails(String refNo) {
        ArrayList<InvDet> list = new InvDetDS(activity).getAllInvDet(refNo);
        new InvDetDS(activity).UpdateItemTaxInfo(list);
        new InvTaxRGDS(activity).UpdateInvTaxRG(list);
        new InvTaxDTDS(activity).UpdateInvTaxDT(list);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    private String currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    private void UpdateQOH_FIFO() {

        ArrayList<InvDet> list = new InvDetDS(getActivity()).getAllInvDet(RefNo);

		/*-*-*-*-*-*-*-*-*-*-*-*-each itemcode has multiple sizecodes*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/
        for (InvDet item : list) {

            int Qty = (int) Double.parseDouble(item.getFINVDET_QTY());

            ArrayList<StkIn> GRNList = new STKInDS(activity).getAscendingGRNList(item.getFINVDET_ITEM_CODE(), locCode);

			/*-*-*-*-*-*-*-*-*-*-multiple GRN for each sizecode-*-*-*-*-*-*-*-*-*-*-*/
            for (StkIn size : GRNList) {
                int balQty = (int) Double.parseDouble(size.getBALQTY());

                if (balQty > 0) {
                    if (Qty > balQty) {
                        Qty = Qty - balQty;
                        size.setBALQTY("0");
                        new StkIssDS(activity).InsertSTKIssData(size, RefNo, String.valueOf(balQty), locCode);

                    } else {
                        size.setBALQTY(String.valueOf(balQty - Qty));
                        new StkIssDS(activity).InsertSTKIssData(size, RefNo, String.valueOf(Qty), locCode);
                        break;
                    }
                }
            }
            new STKInDS(activity).UpdateBalQtyByFIFO(GRNList);
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void updateDispTables(InvHed invHed) {

        String dispREfno = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.DispVal));

        int res = new DispHedDS(getActivity()).updateHeader(invHed, dispREfno);

        if (res > 0) {
            ArrayList<InvDet> list = new InvDetDS(getActivity()).getAllInvDet(invHed.getFINVHED_REFNO());
            new DispDetDS(getActivity()).updateDispDet(list, dispREfno);
            new DispIssDS(getActivity()).updateDispIss(new StkIssDS(getActivity()).getUploadData(invHed.getFINVHED_REFNO()), dispREfno);
            new ReferenceNum(getActivity()).nNumValueInsertOrUpdate(getResources().getString(R.string.DispVal));
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void mPauseinvoice() {

        if (new InvDetDS(getActivity()).getItemCount(RefNo) > 0) {
            loadFragment(new IconPallet());
        } else
            Toast.makeText(activity, "Add items before pause ...!", Toast.LENGTH_SHORT).show();
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_SUMMARY"));
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void loadFragment(Fragment fragment) {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
        ft.replace(R.id.main_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void updateInvoice() {

        ArrayList<Product> list = new ProductDS(getActivity()).getSelectedItems();

        int i = 0;

        for (Product product : list) {

            MainActivity activity = (MainActivity) getActivity();
            double totAmt = Double.parseDouble(product.getFPRODUCT_PRICE()) * Double.parseDouble(product.getFPRODUCT_QTY());
            String TaxedAmt = new TaxDetDS(getActivity()).calculateTax(product.getFPRODUCT_ITEMCODE(), new BigDecimal(totAmt));

            double brandDiscPer = new DebItemPriDS(getActivity()).getBrandDiscount(new ItemsDS(getActivity()).getBrandCode(product.getFPRODUCT_ITEMCODE()), activity.selectedDebtor.getFDEBTOR_CODE());
            double compDiscPer = new ControlDS(getActivity()).getCompanyDisc();

            double Disc = (totAmt / 100) * compDiscPer;
            double compDisc = Disc;
            totAmt -= Disc;

            Disc = (totAmt / 100) * brandDiscPer;
            double brandDisc = Disc;
            totAmt -= Disc;

            InvDet invDet = new InvDet();

            invDet.setFINVDET_ID(String.valueOf(i++));
            invDet.setFINVDET_AMT(String.format("%.2f", totAmt - Double.parseDouble(TaxedAmt)));
            invDet.setFINVDET_BAL_QTY(product.getFPRODUCT_QTY());
            invDet.setFINVDET_B_AMT(invDet.getFINVDET_AMT());
            invDet.setFINVDET_B_SELL_PRICE(product.getFPRODUCT_PRICE());
            invDet.setFINVDET_BT_SELL_PRICE(product.getFPRODUCT_PRICE());
            invDet.setFINVDET_DIS_AMT(String.format("%.2f", compDisc + brandDisc));
            invDet.setFINVDET_DIS_PER("0");
            invDet.setFINVDET_ITEM_CODE(product.getFPRODUCT_ITEMCODE());
            invDet.setFINVDET_PRIL_CODE(activity.selectedDebtor.getFDEBTOR_PRILLCODE());
            invDet.setFINVDET_QTY(product.getFPRODUCT_QTY());
            invDet.setFINVDET_PICE_QTY(product.getFPRODUCT_QTY());
            invDet.setFINVDET_TYPE("SA");
            invDet.setFINVDET_BT_TAX_AMT("");
            invDet.setFINVDET_RECORD_ID("");


        }
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            VanSalesSummary.this.mRefreshData();
        }
    }


}
