package com.lankahardwared.lankahw.view.deposit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.DepositInvoiceHistoryAdapter;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.DepositDetDS;
import com.lankahardwared.lankahw.data.DepositHedDS;
import com.lankahardwared.lankahw.data.RecHedDS;
import com.lankahardwared.lankahw.model.Depohed;
import com.lankahardwared.lankahw.model.mapper.ReceiptMapper;
import com.lankahardwared.lankahw.view.IconPallet;
import com.lankahardwared.lankahw.view.MainActivity;

import java.util.ArrayList;

/**
 * Created by Yasith on 3/1/2019.
 */

public class DepositInvoice extends Fragment {

    View view;

    ListView lvDepositList;
    ArrayList<Depohed> arrayList;
    FloatingActionButton fab;
    Activity activity;
    String btnType = "U";

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sales_management_deposit_invoice, container, false);

        lvDepositList = (ListView) view.findViewById(R.id.lvDepositList);
        registerForContextMenu(lvDepositList);
        setHasOptionsMenu(true);

        activity = getActivity();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.dm_logo_64);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        activity.setTitle("UPLOAD DUE DEPOSITS");
        displayDepositHistory("N");


        //region fab CLICK EVENT
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnType.equals("N")) {
                    activity.setTitle("UPLOAD DUE DEPOSITS");
                    fab.setImageResource(R.drawable.tick);
                    btnType = "U";
                    displayDepositHistory("N");
                } else {
                    activity.setTitle("UPLOADED DEPOSITS");
                    fab.setImageResource(R.drawable.cross);
                    displayDepositHistory("U");
                    btnType = "N";
                }
            }
        });
        //endregion

        return view;
    }


    //region displayDepositHistory FUNCTION
    private void displayDepositHistory(String param)
    {
        lvDepositList = (ListView) view.findViewById(R.id.lvDepositList);
        lvDepositList.clearTextFilter();
        DepositHedDS depoHedDS = new DepositHedDS(getActivity());
        arrayList = depoHedDS.getAllUnsyncedDepositHed("", param);
        lvDepositList.setAdapter(new DepositInvoiceHistoryAdapter(getActivity(), arrayList));
    }
    //endregion


    //region onCreateOptionsMenu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i)
        {
            menu.removeItem(menu.getItem(i).getItemId());
        }

        inflater.inflate(R.menu.frag_deposit_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchItems).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                lvDepositList = (ListView) view.findViewById(R.id.lvDepositList);
                lvDepositList.clearTextFilter();
                DepositHedDS depoHedDS = new DepositHedDS(getActivity());
                arrayList = depoHedDS.getAllUnsyncedDepositHed(newText, btnType.equals("N") ? "U" : "N");
                lvDepositList.setAdapter(new DepositInvoiceHistoryAdapter(getActivity(), arrayList));
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
    //endregion


    //region onOptionsItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.enterNewItem)
        {
            ArrayList<ReceiptMapper> unsyncedReceipts = new RecHedDS(getContext()).getAllUnsyncedRecHed();
            if(unsyncedReceipts.size() > 0)
            {
                String message = "Please upload completed receipts before add deposits..!";
                mActiveTransactionDialog(message);
            }
            else
            {
                UtilityContainer.mLoadFragment(new DepositDetail(), getActivity());
            }
        }
        else
        {
            UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion


    //region onCreateContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.lvDepositList)
        {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_deposit, menu);
        }
    }
    //endregion


    //region onContextItemSelected
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Depohed depoHed = arrayList.get(info.position);

        switch (item.getItemId())
        {
            case R.id.delete:
                deleteDeposit(getActivity(), depoHed.getFBANKDEPOHED_REFNO());
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
    //endregion


    //region deleteDeposit FUNCTION
    private void deleteDeposit(final Context context, final String refno)
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to cancel this deposit?");
        final DepositHedDS depositHedDS = new DepositHedDS(getActivity());
        final DepositDetDS depositDetDS = new DepositDetDS(getActivity());
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                try
                {
                    depositHedDS.resetData(refno);
                }
                catch (Exception e)
                {
                    Log.w("Cancel FBANKDEPOHED", e.toString());
                }

                try
                {
                    ArrayList<String> selectedReceptRefNoList = new ArrayList<String>();
                    int result = 0;
                    selectedReceptRefNoList = new DepositDetDS(getActivity()).getSelectedReceiptRefNoByDepositRefNo(refno);

                    if (selectedReceptRefNoList.size() > 0)
                    {
                        result = new RecHedDS(getActivity()).updateIsDepositStatus(false, selectedReceptRefNoList);
                        if (result > 0)
                        {
                            new DepositDetDS(getActivity()).resetData(refno);
                        }
                    }
                }
                catch (Exception e)
                {
                    Log.w("Cancel FBANKDEPODET", e.toString());
                }
                finally
                {
                    displayDepositHistory("");
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
    //endregion


    //region mActiveTransactionDialog FUNCTION
    public void mActiveTransactionDialog(String sMessage) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(sMessage);
        alertDialogBuilder.setTitle("Unsynced Receipts");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_info);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
    //endregion
}
