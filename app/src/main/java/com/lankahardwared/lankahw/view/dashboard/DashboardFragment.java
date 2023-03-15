package com.lankahardwared.lankahw.view.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.InvHedDS;
import com.lankahardwared.lankahw.listviewitems.ChartItem;
import com.lankahardwared.lankahw.listviewitems.LineChartItem;
import com.lankahardwared.lankahw.listviewitems.PieChartItem;
import com.lankahardwared.lankahw.view.IconPallet;
import com.lankahardwared.lankahw.view.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("NewApi")
public class DashboardFragment extends Fragment {

    ListView lv_dashboard;
    ChartDataAdapter cda;
    ArrayList<ChartItem> list;
    List<String> monthList;
    MenuItem refreshMenuItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dash_board, container, false);
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle("Dashboard");
        toolbar.setLogo(R.drawable.dm_logo_64);

        lv_dashboard = (ListView) rootView.findViewById(R.id.listViewDsah);
        list = new ArrayList<ChartItem>();
        monthList = getTestmonths(Integer.parseInt(new SimpleDateFormat("MM").format(new Date())));
        new RefreshData().execute();

        return rootView;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }
        inflater.inflate(R.menu.mnu_close, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.close:
                UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private LineData generateDataLine(ArrayList<Entry> salesList) {

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < 3; i++)// month
        {
            e1.add(new Entry((int) (Math.random() * 100000) / 1000, i));
        }

        ArrayList<Entry> li = new
                InvHedDS(getActivity()).getMonthlySales(Integer.parseInt(new SimpleDateFormat("MM").format(new Date())));

        LineDataSet vanSale = new LineDataSet(salesList, "VAN SALES (x1000)");
        vanSale.setLineWidth(5f);
        vanSale.setCircleSize(5f);
        vanSale.setHighLightColor(Color.parseColor("#336699"));

        ArrayList<Entry> e2 = new ArrayList<Entry>();

		 /* Data grab for Actual sale */
         /* 30 less than target */

        for (int i = 0; i < 3; i++) {
            e2.add(new Entry(e1.get(i).getVal() - 30, i));
        }

        LineDataSet d2 = new LineDataSet(e2, "Actual sales");
        d2.setLineWidth(5f);
        d2.setCircleSize(5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(vanSale);/* target line */
        // sets.add(d2);/* actual line */

        LineData cd = new LineData(getTestmonths(Integer.parseInt(new SimpleDateFormat("MM").format(new Date()))), sets);
        return cd;
    }

    ;

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private ArrayList<String> getMonths(int i) {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan"); // 0
        m.add("Feb"); // 1
        m.add("Mar"); // 2
        m.add("Apr"); // 3
        m.add("May"); // 4
        m.add("Jun"); // 5
        m.add("Jul"); // 6
        m.add("Aug"); // 7
        m.add("Sep"); // 8
        m.add("Oct"); // 9
        m.add("Nov"); // 10
        m.add("Dec"); // 11

        ArrayList<String> monthList = new ArrayList<String>();

        if (i == 1) {
            monthList.add(m.get(11));
            monthList.add(m.get(10));
            monthList.add(m.get(9));
            monthList.add(m.get(8));

        } else if (i == 2) {
            monthList.add(m.get(0));
            monthList.add(m.get(11));
            monthList.add(m.get(10));
            monthList.add(m.get(9));

        } else if (i == 3) {
            monthList.add(m.get(1));
            monthList.add(m.get(0));
            monthList.add(m.get(11));
            monthList.add(m.get(10));

        } else {
            monthList.add(m.get(i - 4));
            monthList.add(m.get(i - 3));
            monthList.add(m.get(i - 2));
            monthList.add(m.get(i - 1));
        }

        return monthList;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<String> getTestmonths(int i) {

        ArrayList<String> m = new ArrayList<String>();

        m.add("Oct"); // 0
        m.add("Nov"); // 1
        m.add("Dec"); // 2
        m.add("Jan"); // 3
        m.add("Feb"); // 4
        m.add("Mar"); // 5
        m.add("Apr"); // 6
        m.add("May"); // 7
        m.add("Jun"); // 8
        m.add("Jul"); // 9
        m.add("Aug"); // 10
        m.add("Sep"); // 11
        m.add("Oct"); // 12
        m.add("Nov"); // 13
        m.add("Dec"); // 14

        ArrayList<String> monthList = new ArrayList<String>();
        monthList.add(m.get(i - 1));
        monthList.add(m.get(i));
        monthList.add(m.get(i + 1));
        monthList.add(m.get(i + 2));

        return monthList;
    }

    private PieData generateDataPie(ArrayList<Entry> salesList) {

        // ArrayList<Entry> entries = new ArrayList<Entry>();
        //
        // for (int i = 0; i < 3; i++) {
        // entries.add(new Entry((int) (Math.random() * 70) + 30, i));
        // }

        PieDataSet d = new PieDataSet(salesList, "");
        d.setSliceSpace(5f);
        d.setColors(ColorTemplate.LIBERTY_COLORS);

        PieData cd = new PieData(getQuarters(), d);
        return cd;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private ArrayList<String> getQuarters() {
        ArrayList<String> q = new ArrayList<String>();
        q.add(monthList.get(0));
        q.add(monthList.get(1));
        q.add(monthList.get(2));
        return q;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public class RefreshData extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {

            if (refreshMenuItem != null) {
                refreshMenuItem.setActionView(R.layout.action_progressbar);
                refreshMenuItem.expandActionView();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<Entry> salesList = new InvHedDS(getActivity()).getMonthlySales(Integer.parseInt(new SimpleDateFormat("MM").format(new Date())));
                list.clear();
                list.add(new LineChartItem(generateDataLine(salesList), getActivity().getApplicationContext()));
                list.add(new PieChartItem(generateDataPie(salesList), getActivity().getApplicationContext()));
                cda = new ChartDataAdapter(getActivity().getApplicationContext(), list);
                Thread.sleep(500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            lv_dashboard.clearTextFilter();
            lv_dashboard.setAdapter(cda);

            if (refreshMenuItem != null) {
                refreshMenuItem.collapseActionView();
                refreshMenuItem.setActionView(null);
            }
        }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).getItemType();
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }
    }

}
