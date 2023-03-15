package com.lankahardwared.lankahw.view;


import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.DashboardDS;
import com.lankahardwared.lankahw.model.SalesStat;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;


/**
 * Created by Sathiyaraja on 1/17/2018.
 */

public class Dashboard extends Fragment {

    TextView cDate;
    View rootview;
    private Toolbar toolbar;
    private LineChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.dashboard, container, false);
        setHasOptionsMenu(true);

        toolbar = (Toolbar) rootview.findViewById(R.id.titleBar);
        cDate = (TextView) rootview.findViewById(R.id.sales_date);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.dm_logo_64);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            toolbar.setNavigationIcon(R.drawable.ic_apps);
//            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_settings));
//            toolbar.inflateMenu(R.menu.dashboard_menu);
//        }
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
//            }
//        });

//        long date = System.currentTimeMillis();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String currentDateandTime = sdf.format(date);
//        cDate.setText(currentDateandTime);

        DashboardDS dashboardDS = new DashboardDS(getActivity());
        //setHasOptionsMenu(true);
        ArrayList<SalesStat> salesStats = dashboardDS.GetsalesStats();
        loadMonthlyLineChart(salesStats);

        System.out.println("********" + salesStats.size());


        PieChart mPieChart = (PieChart) rootview.findViewById(R.id.piechart);

        mPieChart.addPieSlice(new PieModel("Freetime", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Sleep", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Work", 35, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));

        mPieChart.startAnimation();


        return rootview;
    }

    public void loadMonthlyLineChart(ArrayList<SalesStat> salesStats) {

        ValueLineChart mCubicValueLineChart = (ValueLineChart) rootview.findViewById(R.id.cubiclinechart);

        BarChart mBarChart = (BarChart) rootview.findViewById(R.id.barchart);

        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);

        LinearLayout tableRow = (LinearLayout) rootview.findViewById(R.id.rowDates);
        for (int i = 0; i < salesStats.size(); i++) {

            SalesStat stat = salesStats.get(i);
            //line chart
            series.addPoint(new ValueLinePoint(stat.getmDate(), stat.getTotalAmount()));

            //bar chart
            mBarChart.addBar(new BarModel(stat.getTotalAmount(), 0xFF1FF4AC));


            TextView textViewDate = new TextView(getActivity());
            textViewDate.setText(stat.getmDate());
            textViewDate.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p.weight = 1;
            textViewDate.setLayoutParams(p);
            textViewDate.setPadding(1, 1, 1, 1);
            tableRow.addView(textViewDate);
        }
        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();

        mBarChart.startAnimation();
        mBarChart.setEmptyDataText("test");


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }
        inflater.inflate(R.menu.mnu_close, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.close:
                UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
                //return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
