package com.lankahardwared.lankahw.view.receipt;


import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.ReceiptInvoiceHistoryAdapter;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.control.print_preview.ReceiptPreviewAlertBox;
import com.lankahardwared.lankahw.data.FDDbNoteDS;
import com.lankahardwared.lankahw.data.RecDetDS;
import com.lankahardwared.lankahw.data.RecHedDS;
import com.lankahardwared.lankahw.data.TourDS;
import com.lankahardwared.lankahw.model.RecDet;
import com.lankahardwared.lankahw.model.RecHed;
import com.lankahardwared.lankahw.model.Tour;
import com.lankahardwared.lankahw.view.IconPallet;
import com.lankahardwared.lankahw.view.MainActivity;
import com.lankahardwared.lankahw.view.tour.TourInfo;

import java.util.ArrayList;

public class ReceiptInvoice extends Fragment {
    View view;
    ListView lvInvoiceList;
    ArrayList<RecHed> arrayList;
    FloatingActionButton fab;
    Activity activity;
    String btnType = "U";
    Tour rTour;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_mangemet_van_sales_invoice, container, false);
        lvInvoiceList = (ListView) view.findViewById(R.id.lvPhoneInvoiceList);
        registerForContextMenu(lvInvoiceList);
        setHasOptionsMenu(true);
        activity = getActivity();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.dm_logo_64);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        activity.setTitle("UPLOAD DUE RECEIPTS");
        rTour = new TourDS(getActivity()).getIncompleteRecord();
        displayInvoiceHistory("N");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnType.equals("N")) {
                    activity.setTitle("UPLOAD DUE RECEIPTS");
                    fab.setImageResource(R.drawable.tick);
                    btnType = "U";
                    displayInvoiceHistory("N");
                } else {
                    activity.setTitle("UPLOADED RECEIPTS");
                    fab.setImageResource(R.drawable.cross);
                    displayInvoiceHistory("U");
                    btnType = "N";
                }
            }
        });

        return view;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.lvPhoneInvoiceList) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_receipt, menu);
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        RecHed recHed = arrayList.get(info.position);

        switch (item.getItemId()) {

            case R.id.print:
                setBluetooth(true);
                new ReceiptPreviewAlertBox(getActivity()).PrintDetailsDialogbox(getActivity(), "Print preview", recHed.getFPRECHED_REFNO());
                return true;

            case R.id.delete:
                //Toast.makeText(getActivity(), "Delete UnAuthorised!", Toast.LENGTH_SHORT).show();
                deleteReceipt(getActivity(), recHed.getFPRECHED_REFNO());
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private void deleteReceipt(final Context context, final String refno) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to cancel this receipt?");
        final RecHedDS recHedDS = new RecHedDS(getActivity());
        final RecDetDS detDS = new RecDetDS(getActivity());
        final String comRefNo = detDS.getComRefByRecRefNo(refno);
//        final ArrayList<RecDet> delmrdet = detDS.GetReceiptByRefno(refno);
        final ArrayList<RecDet> delReceiptList = detDS.GetRecInvListByComRefNo(comRefNo);
        //final ArrayList<RecDet> delmrdet = detDS.GetMReceiptByRefno(refno);
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                try {
                    //recHedDS.DeleteStatusUpdateForEceipt(refno);
                    recHedDS.clearRecHedByComRefNo(comRefNo);
                } catch (Exception e) {
                    Log.w("Cancel FORDHED", e.toString());
                }

                try {

                    //new FDDbNoteDS(getActivity()).UpdateFddbNoteBalanceForReceipt(delmrdet);
                    new FDDbNoteDS(getActivity()).UpdateFddbNoteBalanceForReceipt(delReceiptList);
                    //detDS.UpdateDeleteStatus(refno);
                    detDS.clearRecDetByComRefNo(comRefNo);
                } catch (Exception e) {
                    Log.w("Cancel FRECDET", e.toString());
                } finally {
                    displayInvoiceHistory("");
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
	
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private void displayInvoiceHistory(String params) {
        lvInvoiceList = (ListView) view.findViewById(R.id.lvPhoneInvoiceList);
        lvInvoiceList.clearTextFilter();
        arrayList = new RecHedDS(getActivity()).getAllCompletedRecHedS(params);
        lvInvoiceList.setAdapter(new ReceiptInvoiceHistoryAdapter(getActivity(), arrayList));
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }

        menu.clear();
        inflater.inflate(R.menu.frag_nonprd_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchItems).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.enterNewItem)
        {
            if (rTour == null)
            {
                Log.d("RECEIPT_INVOICE", "TOUR_DATE_IS_NULL");
                tourValidationDialogBox();
            }
            else
            {
                Log.d("RECEIPT_INVOICE", "TOUR_DATE_IS_NOT_NULL" +    rTour.getFTOUR_DATE());
                UtilityContainer.mLoadFragment(new Receipt(), getActivity());
            }
        }
        else
        {
            UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
        }

//        if (item.getItemId() == R.id.enterNewItem) {
//            UtilityContainer.mLoadFragment(new Receipt(), getActivity());
//        } else {
//            UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
//        }

        return super.onOptionsItemSelected(item);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

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

    public void tourValidationDialogBox() {

        String message = "You did not start a tour. Do you want to start a tour now?";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Tour");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                UtilityContainer.mLoadFragment(new TourInfo(), getActivity());

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

    }

}
