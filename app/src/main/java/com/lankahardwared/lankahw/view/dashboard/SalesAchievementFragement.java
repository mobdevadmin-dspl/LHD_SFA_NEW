package com.lankahardwared.lankahw.view.dashboard;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.SalesAchievementAdapter;
import com.lankahardwared.lankahw.data.BrandTargetDS;
import com.lankahardwared.lankahw.data.InvHedDS;
import com.lankahardwared.lankahw.data.TourHedDS;

import java.util.ArrayList;

public class SalesAchievementFragement extends Fragment {

    Spinner spnCostCode;
    TextView lblMonthTarget, lblMonthAchieve, lblMonthVisit, lblMonthProd, lblDaySales, lblDayVisit, lblDayProductivity;
    ListView lvSalesAchievement;
    ArrayList<String> strList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sales_achievements, container, false);
        setHasOptionsMenu(true);

        lblMonthTarget = (TextView) rootView.findViewById(R.id.lblMonthTarget);
        lblMonthAchieve = (TextView) rootView.findViewById(R.id.lblMonthAchieve);
        lblMonthVisit = (TextView) rootView.findViewById(R.id.lblMonthVisit);
        lblMonthProd = (TextView) rootView.findViewById(R.id.lblMonthProd);
        lblDaySales = (TextView) rootView.findViewById(R.id.lblDaySales);
        lblDayVisit = (TextView) rootView.findViewById(R.id.lblDayVisit);
        lblDayProductivity = (TextView) rootView.findViewById(R.id.lblDayProductivity);
        spnCostCode = (Spinner) rootView.findViewById(R.id.spnCostCode);
        lvSalesAchievement = (ListView) rootView.findViewById(R.id.lvSalesAchievement);

        strList = new TourHedDS(getActivity()).getCostCodelist();

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, strList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCostCode.setAdapter(dataAdapter1);

        spnCostCode.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {

                    lblMonthTarget.setText(new BrandTargetDS(getActivity()).getRepTarget(strList.get(position)));
                    lblMonthAchieve.setText(new InvHedDS(getActivity()).getMonthlySales(strList.get(position)));
                    lblMonthVisit.setText(new InvHedDS(getActivity()).getMonthlyVisits(strList.get(position)));
                    lblMonthProd.setText(new InvHedDS(getActivity()).getCurrentMonthlySales(strList.get(position)));
                    lblDaySales.setText(new InvHedDS(getActivity()).getTodaySales(strList.get(position)));
                    lblDayVisit.setText(new InvHedDS(getActivity()).getTodaySalesVisit(strList.get(position)));
                    lblDayProductivity.setText(new InvHedDS(getActivity()).getTodayProductivity(strList.get(position)));
                    ArrayList<String[]> list = new BrandTargetDS(getActivity()).getBrandTargetAchievement(strList.get(position));
                    lvSalesAchievement.setAdapter(new SalesAchievementAdapter(getActivity(), list));

                } else
                    clearData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        return rootView;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void clearData() {
        lblMonthTarget.setText("");
        lblMonthAchieve.setText("");
        lblMonthVisit.setText("");
        lblMonthProd.setText("");
        lblDaySales.setText("");
        lblDayVisit.setText("");
        lblDayProductivity.setText("");
        lvSalesAchievement.setAdapter(null);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }

        inflater.inflate(R.menu.dashboard_icons, menu);
        menu.findItem(R.id.dashboard_monitor).setVisible(false);
        getActivity().invalidateOptionsMenu();
        super.onCreateOptionsMenu(menu, inflater);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.dashboard_cus_credit) {

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, new CreditDashBoardFragment());
            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();

        }

        return super.onOptionsItemSelected(item);
    }

}
