package com.lankahardwared.lankahw.view;

import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.TopSalesAdapter;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.DashboardDS;
import com.lankahardwared.lankahw.model.TopSales;

import java.util.ArrayList;

/**
 * Created by Sathiyaraja on 2/13/2018.
 */

public class DashboardSummerySub extends Fragment {
    View rootview;
    private Toolbar toolbar;

    TextView topDate, topCustomer, topItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.dashboard_summery_sub, container, false);
        toolbar = (Toolbar) rootview.findViewById(R.id.titleBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.ic_apps);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityContainer.mLoadFragment(new DashboardSummery(), getActivity());
            }
        });


        //initialize views
        topDate = (TextView) rootview.findViewById(R.id.topDate);
        topCustomer = (TextView) rootview.findViewById(R.id.topCustomer);
        topItem = (TextView) rootview.findViewById(R.id.topItem);

        String monthSelected = DashboardSummery.selectedMonth;
        System.out.println("monthSelected" + monthSelected);

        String month = null;
        if (monthSelected.equalsIgnoreCase("JANUARY")) {
            month = "01";
        } else if (monthSelected.equalsIgnoreCase("FEBRUARY")) {
            month = "02";
        } else if (monthSelected.equalsIgnoreCase("MARCH")) {
            month = "03";
        } else if (monthSelected.equalsIgnoreCase("APRIL")) {
            month = "04";
        } else if (monthSelected.equalsIgnoreCase("MAY")) {
            month = "05";
        } else if (monthSelected.equalsIgnoreCase("JUNE")) {
            month = "06";
        } else if (monthSelected.equalsIgnoreCase("JULY")) {
            month = "07";
        } else if (monthSelected.equalsIgnoreCase("AUGUST")) {
            month = "08";
        } else if (monthSelected.equalsIgnoreCase("SEPTEMBER")) {
            month = "09";
        } else if (monthSelected.equalsIgnoreCase("OCTOBER")) {
            month = "10";
        } else if (monthSelected.equalsIgnoreCase("NOVEMBER")) {
            month = "11";
        } else if (monthSelected.equalsIgnoreCase("DECEMBER")) {
            month = "12";
        }

        //get top sales summery
        DashboardDS dashboardDS = new DashboardDS(getActivity());
        ArrayList<TopSales> topSales = dashboardDS.GetTopSales(month);

        TopSales sales = topSales.get(0); // get the top one
        topDate.setText(sales.getTopDate());
        topCustomer.setText(sales.getTopCustomer());
        topItem.setText(sales.getTopItem());

        ListView topsaleList = (ListView) rootview.findViewById(R.id.topsalesList);
        TopSalesAdapter topSalesAdapter = new TopSalesAdapter(getActivity(), topSales);
        topsaleList.setAdapter(topSalesAdapter);

        return rootview;
    }
}
