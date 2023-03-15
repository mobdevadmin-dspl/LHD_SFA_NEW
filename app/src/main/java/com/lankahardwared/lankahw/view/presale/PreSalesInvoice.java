package com.lankahardwared.lankahw.view.presale;

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
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.InvoiceHistoryAdapter;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.control.print_preview.SalesPrintPreviewAlertBox;
import com.lankahardwared.lankahw.data.ItemLocDS;
import com.lankahardwared.lankahw.data.OrdFreeIssueDS;
import com.lankahardwared.lankahw.data.OrderDiscDS;
import com.lankahardwared.lankahw.data.PreSaleTaxDTDS;
import com.lankahardwared.lankahw.data.PreSaleTaxRGDS;
import com.lankahardwared.lankahw.data.TourDS;
import com.lankahardwared.lankahw.data.TranSODetDS;
import com.lankahardwared.lankahw.data.TranSOHedDS;
import com.lankahardwared.lankahw.model.Tour;
import com.lankahardwared.lankahw.model.TranSODet;
import com.lankahardwared.lankahw.model.TranSOHed;
import com.lankahardwared.lankahw.view.IconPallet;
import com.lankahardwared.lankahw.view.MainActivity;
import com.lankahardwared.lankahw.view.tour.TourInfo;

import java.util.ArrayList;

public class PreSalesInvoice extends Fragment {
    View view;
    ListView lvInvoiceList;
    ArrayList<TranSOHed> arrayList;
    String btnType = "N";
    Activity activity;
    FloatingActionButton fab;
    Tour oTour;

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

    	/*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sales_mangement_pre_sales_invoice, container, false);

        lvInvoiceList = (ListView) view.findViewById(R.id.lvPhoneInvoiceList);
        registerForContextMenu(lvInvoiceList);
        activity = getActivity();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.dm_logo_64);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        activity.setTitle("UPLOAD DUE ORDERS");
        displayInvoiceHistory("N");
        setHasOptionsMenu(true);
        oTour = new TourDS(getActivity()).getIncompleteRecord();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnType.equals("N")) {
                    activity.setTitle("UPLOAD DUE ORDERS");
                    fab.setImageResource(R.drawable.tick);
                    btnType = "U";
                    displayInvoiceHistory("N");
                } else {
                    activity.setTitle("UPLOADED ORDERS");
                    fab.setImageResource(R.drawable.cross);
                    displayInvoiceHistory("U");
                    btnType = "N";
                }
            }
        });

        return view;
    }

    	/*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.lvPhoneInvoiceList) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    	/*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*/

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        TranSOHed ordHed = arrayList.get(info.position);
        ArrayList<TranSODet> itemList = new TranSODetDS(getActivity()).getAllOrderDetails(ordHed.getFTRANSOHED_REFNO());
        ordHed.setSoDetArrayList(itemList);
        switch (item.getItemId()) {
            case R.id.cancel:
                delete(getActivity(), ordHed.getFTRANSOHED_REFNO());
                return true;
            case R.id.print:
                setBluetooth(true);
                new SalesPrintPreviewAlertBox(getActivity()).PrintDetailsDialogbox(getActivity(), "Print preview", ordHed.getFTRANSOHED_REFNO(), false);
                return true;
            case R.id.reOrde:
                if (!(new TranSOHedDS(getActivity()).restData(ordHed.getFTRANSOHED_REFNO())))
                {
                    Toast.makeText(getActivity(), "UPLOADED order re_ordering not possible..!", Toast.LENGTH_LONG).show();
                    displayInvoiceHistory("U");
                }
                else
                    {
                    PreSales preSales = new PreSales();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("Active", false);
                    bundle.putSerializable("order",ordHed);
                    preSales.setArguments(bundle);
                    UtilityContainer.mLoadFragment(preSales, activity);
                }
            default:
                return super.onContextItemSelected(item);
        }
    }

    	/*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*/

    private void delete(final Context context, final String refno) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Delete Order");
        alertDialogBuilder.setMessage("Are you sure you want to cancel this entry?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (!(new TranSOHedDS(getActivity()).restData(refno)))
                {
                    Toast.makeText(getActivity(), "UPLOADED order deletion not possible..!", Toast.LENGTH_LONG).show();
                    displayInvoiceHistory("U");
                }
                else {
                    new ItemLocDS(getActivity()).UpdateInvoiceQOH(refno, "+", new TranSODetDS(getActivity()).getLocCodeByRefNo(refno));
                    new OrderDiscDS(getActivity()).clearData(refno);
                    new OrdFreeIssueDS(getActivity()).ClearFreeIssues(refno);
                    new PreSaleTaxDTDS(getActivity()).ClearTable(refno);
                    new PreSaleTaxRGDS(getActivity()).ClearTable(refno);
                    new TranSODetDS(getActivity()).restData(refno);
                    Toast.makeText(getActivity(), "Deleted successfully..!", Toast.LENGTH_LONG).show();

                    displayInvoiceHistory("N");
                }
                //

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

	    /*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*/

    private void displayInvoiceHistory(String param) {
        lvInvoiceList = (ListView) view.findViewById(R.id.lvPhoneInvoiceList);
        lvInvoiceList.clearTextFilter();
        arrayList = new TranSOHedDS(getActivity()).getAllUnsyncedOrdHed("", param);
        lvInvoiceList.setAdapter(new InvoiceHistoryAdapter(getActivity(), arrayList));
    }

    	/*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }

        inflater.inflate(R.menu.frag_nonprd_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchItems).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                lvInvoiceList = (ListView) view.findViewById(R.id.lvPhoneInvoiceList);
                lvInvoiceList.clearTextFilter();
                arrayList = new TranSOHedDS(getActivity()).getAllUnsyncedOrdHed(newText, btnType.equals("N") ? "U" : "N");
                lvInvoiceList.setAdapter(new InvoiceHistoryAdapter(getActivity(), arrayList));
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    	/*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.enterNewItem) {

            if (oTour == null)
            {
                Log.d("PRE_SALES_INVOICE", "TOUR_DATE_IS_NULL");
                tourValidationDialogBox();
            }
            else
            {
                Log.d("PRE_SALES_INVOICE", "TOUR_DATE_IS_NOT_NULL" +    oTour.getFTOUR_DATE());
                UtilityContainer.mLoadFragment(new PreSales(), getActivity());
            }

        } else {
            UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
        }

        return super.onOptionsItemSelected(item);

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
