package com.lankahardwared.lankahw.view.non_productive;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.lankahardwared.lankahw.adapter.NonPrdHeader;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.FDaynPrdHedDS;
import com.lankahardwared.lankahw.data.TourDS;
import com.lankahardwared.lankahw.data.fDaynPrdDetDS;
import com.lankahardwared.lankahw.model.FDaynPrdHed;
import com.lankahardwared.lankahw.model.Tour;
import com.lankahardwared.lankahw.view.IconPallet;
import com.lankahardwared.lankahw.view.MainActivity;
import com.lankahardwared.lankahw.view.tour.TourInfo;

import java.util.ArrayList;

public class NonProductiveMain extends Fragment {
    View view;
    ListView lv_invent_load;
    ArrayList<FDaynPrdHed> loadlist;
    ReferenceNum referenceNum;
    Tour oTour;
    String btnType = "N";
    FloatingActionButton fab;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.non_productive_main, container, false);
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        activity = getActivity();
        activity.setTitle("Non productive");
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        toolbar.setLogo(R.drawable.dm_logo_64);
        referenceNum = new ReferenceNum(getActivity());
        lv_invent_load = (ListView) view.findViewById(R.id.lvPhonenonphedlist);
        registerForContextMenu(lv_invent_load);
        //fatchData();
        dispalyNPHistory("N");
        oTour = new TourDS(getActivity()).getIncompleteRecord();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnType.equals("N")) {
                    activity.setTitle("UPLOAD DUE NON PRODUCTIVE");
                    fab.setImageResource(R.drawable.tick);
                    btnType = "U";
                    dispalyNPHistory("N");
                } else {
                    activity.setTitle("UPLOADED NON PRODUCTIVE");
                    fab.setImageResource(R.drawable.cross);
                    dispalyNPHistory("U");
                    btnType = "N";
                }
            }
        });

        return view;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

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
                lv_invent_load.clearTextFilter();
                loadlist = new FDaynPrdHedDS(getActivity()).getAllnonprdHedDetails(newText);
                lv_invent_load.setAdapter(new NonPrdHeader(getActivity(), loadlist));
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.enterNewItem)
        {
            if (oTour == null)
            {
                tourValidationDialogBox();
            }
            else
            {
                UtilityContainer.mLoadFragment(new NonProductiveManage(), getActivity());
            }
        }
        else if (item.getItemId() == R.id.exitExpence)
        {
            UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
        }
        return super.onOptionsItemSelected(item);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.lvPhonenonphedlist) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        FDaynPrdHed daynPrdHed = loadlist.get(info.position);
        switch (item.getItemId()) {
            case R.id.cancel:

                if (new FDaynPrdHedDS(getActivity()).isEntrySynced(daynPrdHed.getNONPRDHED_REFNO())) {
                    Toast.makeText(getActivity(), "Synced entry. Unable to delete.", Toast.LENGTH_LONG).show();
                } else {
                    int count = new FDaynPrdHedDS(getActivity()).undoOrdHedByID(daynPrdHed.getNONPRDHED_REFNO());
                    if (count > 0) {
                        new fDaynPrdDetDS(getActivity()).OrdDetByRefno(daynPrdHed.getNONPRDHED_REFNO());
                        fatchData();
                        Toast.makeText(getActivity(), "Deleted successfully.", Toast.LENGTH_LONG).show();
                    }
                }
                return true;

            case R.id.print:

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void fatchData() {
        try {
            lv_invent_load.setAdapter(null);
            loadlist = new FDaynPrdHedDS(getActivity()).getAllnonprdHedDetails("");
            if (loadlist.size() > 0)
                lv_invent_load.setAdapter(new NonPrdHeader(getActivity(), loadlist));

        } catch (NullPointerException e) {
            Log.v("Loading Error", e.toString());
        }
    }

    public void dispalyNPHistory(String params)
    {
        lv_invent_load.clearTextFilter();
        loadlist = new FDaynPrdHedDS(getActivity()).getAllUnsyncedNPHed("", params);
        lv_invent_load.setAdapter(new NonPrdHeader(getActivity(), loadlist));
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
