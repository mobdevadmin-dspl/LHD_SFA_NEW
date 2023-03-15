package com.lankahardwared.lankahw.view.dashboard;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.CreditBreakupAdapter;
import com.lankahardwared.lankahw.adapter.CustomerCreditAdapter;
import com.lankahardwared.lankahw.data.FDDbNoteDS;
import com.lankahardwared.lankahw.data.TourHedDS;
import com.lankahardwared.lankahw.model.FDDbNote;
import com.lankahardwared.lankahw.model.TourHed;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CreditDashBoardFragment extends Fragment {

    ListView lvCustomerCredit;
    ArrayList<TourHed> tourlist;
    ArrayList<String[]> list;
    Spinner spnTour;
    String routeCode;
    String kjk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.credit_dashboard, container, false);
        setHasOptionsMenu(true);
        lvCustomerCredit = (ListView) rootView.findViewById(R.id.lvCreditDash);
        spnTour = (Spinner) rootView.findViewById(R.id.spnTour);

        tourlist = new TourHedDS(getActivity()).getTourDetails("");

        ArrayList<String> strList = new ArrayList<String>();
        strList.add("Select a Tour to continue ...");

        for (TourHed hed : tourlist)
            strList.add(hed.getTOURHED_REFNO() + " - " + hed.getTOURHED_ID());

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, strList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTour.setAdapter(dataAdapter1);

        spnTour.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    routeCode = tourlist.get(position - 1).getTOURHED_ROUTECODE().toString();
                    list = new FDDbNoteDS(getActivity()).getCustomerCreditInfo(routeCode);

                    lvCustomerCredit.setAdapter(new CustomerCreditAdapter(getActivity(), list));
                } else
                    lvCustomerCredit.setAdapter(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        lvCustomerCredit.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                CreditBreakupDialog(list.get(position)[0], list.get(position)[1], list.get(position)[2], list.get(position)[5]);
                return true;
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }

        inflater.inflate(R.menu.dashboard_icons, menu);
        menu.findItem(R.id.dashboard_cus_credit).setVisible(false);
        getActivity().invalidateOptionsMenu();
        super.onCreateOptionsMenu(menu, inflater);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.dashboard_monitor) {

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, new SalesAchievementFragement());
            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();

        }

        return super.onOptionsItemSelected(item);
    }

    public void CreditBreakupDialog(String sName, String sTown, String sOutstanding, final String sDebCode) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.cus_credit_breakup, null);

        TextView lblName = (TextView) promptView.findViewById(R.id.lblCusName);
        TextView lblTown = (TextView) promptView.findViewById(R.id.lblTown);
        TextView lblTotOuts = (TextView) promptView.findViewById(R.id.lblTotOutstanding);
        ListView lvCreditDash = (ListView) promptView.findViewById(R.id.lvCreditDash);

        lblName.setText(sName);
        lblTown.setText(sTown);
        lblTotOuts.setText(String.format("%,.2f", Double.parseDouble(sOutstanding)));

        ArrayList<FDDbNote> breakupList = null;

        try {
            breakupList = new AsyncTask<String, String, ArrayList<FDDbNote>>() {

                @Override
                protected ArrayList<FDDbNote> doInBackground(String... params) {
                    return new FDDbNoteDS(getActivity()).getCreditBreakup(params[0]);
                }
            }.execute(sDebCode).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        lvCreditDash.setAdapter(new CreditBreakupAdapter(getActivity(), breakupList));

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Credit Breakup");
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

}
