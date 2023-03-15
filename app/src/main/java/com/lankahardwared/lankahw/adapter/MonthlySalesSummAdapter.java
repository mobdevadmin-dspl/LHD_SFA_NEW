package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.MonthSale;
import com.lankahardwared.lankahw.model.TargetVsAchivement;

import org.eazegraph.lib.charts.PieChart;

import java.util.ArrayList;

/**
 * Created by Sathiyaraja on 2/12/2018.
 */

public class MonthlySalesSummAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    ArrayList<MonthSale> list;


    public MonthlySalesSummAdapter(Context context, ArrayList<MonthSale> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;

    }

    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }

    @Override
    public MonthSale getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        MonthlySalesSummAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new MonthlySalesSummAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.row_month_sale, parent, false);
            viewHolder.monthName = (TextView) convertView.findViewById(R.id.monthName);
            viewHolder.monthTotalSale = (TextView) convertView.findViewById(R.id.monthTotalSale);
            viewHolder.mPieChart = (PieChart) convertView.findViewById(R.id.piechart);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MonthlySalesSummAdapter.ViewHolder) convertView.getTag();
        }
        final MonthSale monthSale = list.get(position);

        viewHolder.monthName.setText(monthSale.getMonth());
        viewHolder.monthTotalSale.setText("" + monthSale.getSalesAmount());

        loadMonthlyPieChart(monthSale.getTargetVsAchivements(), viewHolder.mPieChart);



        return convertView;
    }

    private static class ViewHolder {

        TextView monthName;
        TextView monthTotalSale;
        TextView address;
        PieChart mPieChart;


    }

    public void loadMonthlyPieChart(ArrayList<TargetVsAchivement> salesStats, PieChart pieChart) {

//        TargetVsAchivement achivement = salesStats.get(0);
//
//        pieChart.addPieSlice(new PieModel("Target", achivement.getTarget(), Color.parseColor("#FE6DA8")));
//        pieChart.addPieSlice(new PieModel("Achivement", achivement.getAchivment(), Color.parseColor("#56B7F1")));
//
//
//        pieChart.startAnimation();
    }
}