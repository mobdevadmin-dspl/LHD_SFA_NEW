package com.lankahardwared.lankahw.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.DashboardNewDS;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Create by kaveesha - 10-03-2020
 */

public class Dashboard_New extends Fragment {

    private static final String LOG_TAG = Dashboard_New.class.getSimpleName();
    private TextView tvDate;
    private Toolbar toolbar;
    private ImageView back;

    private TextView tvSalesGross, tvDiscount, tvNetValue, tvProductive, tvNonprdctive, tvReturn,tvinvoicesale,tvTarget,tvAchieve;
    private TextView tvTMGross, tvTMNet, tvTMReturn, tvTMDiscount, tvTMTarget, tvTMProductive, tvTMNonProductive,tvTMinvoicesale,tvTMAchieve;
    private TextView tvPMGross, tvPMNet, tvPMReturn, tvPMDiscount, tvPMTarget, tvPMProductive, tvPMNonProductive,tvPMinvoicesale;
    private long timeInMillis;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private NumberFormat format = NumberFormat.getInstance();

    SharedPref pref;


   @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.testlayout_lhd, container, false);

        timeInMillis = System.currentTimeMillis();

        pref = SharedPref.getInstance(getContext());
        format.setGroupingUsed(true);
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(0);

        tvDate = (TextView) rootView.findViewById(R.id.fragment_day_summary_select_date);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle("Dashboard");
        toolbar.setLogo(R.drawable.dm_logo_64);
        setHasOptionsMenu(true);

        tvDate.setText(dateFormat.format(new Date(timeInMillis)));

        tvSalesGross = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_gross_sale);
        tvDiscount = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_discount);
        tvNetValue = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_net_sale);
        tvProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_today_productive_calls);
        tvNonprdctive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_today_unproductive_calls);
        tvReturn = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_return_value);
        tvinvoicesale = (TextView) rootView.findViewById(R.id.dashboard_tv_card_today_invoice_sale_value);
        tvTarget = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_target_value);
        tvAchieve = (TextView) rootView.findViewById(R.id.dashboard_tv_card_today_Achieve_calls);

        tvTMGross = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_gross_sale);
        tvTMNet = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_net_sale);
        tvTMReturn = (TextView) rootView.findViewById(R.id.textVidashboard_tv_card_this_month_return);
        tvTMDiscount = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_discount);
        tvTMProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_productive_calls);
        tvTMNonProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_unproductive_calls);
        tvTMinvoicesale = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_invoice_sale_value);
        tvTMTarget = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_target_value);
        tvTMAchieve= (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_Achievement_calls);

        tvPMGross = (TextView) rootView.findViewById(R.id.dashboard_tv_card_previous_month_gross_sale);
        tvPMNet = (TextView) rootView.findViewById(R.id.dashboard_tv_card_previous_month_net_sale);
        tvPMReturn = (TextView) rootView.findViewById(R.id.textVidashboard_tv_card_previous_month_return);
        tvPMDiscount = (TextView) rootView.findViewById(R.id.dashboard_tv_card_previous_month_discount);
        tvPMProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_previous_month_productive_calls);
        tvPMNonProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_previous_month_unproductive_calls);
        tvPMinvoicesale = (TextView) rootView.findViewById(R.id.dashboard_tv_card_previous_month_invoice_sale_value);
        tvPMTarget = (TextView) rootView.findViewById(R.id.dashboard_tv_card_previous_month_target_value);

       setCurrentMonthFigures();

        return rootView;
    }

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setCurrentMonthFigures()
    {
        double thisMonthDiscount = pref.getTransOrdDiscSumTM() != null ? Double.parseDouble(pref.getTransOrdDiscSumTM()) : 0.0;
        double thisMonthGross= pref.getTransOrdSumTM() != null ? Double.parseDouble(pref.getTransOrdSumTM()) : 0.0;
        double thisMonthReturn = pref.getMonthReturnSumTM() != null ? Double.parseDouble(pref.getMonthReturnSumTM()) : 0.0;
        double thisMonthTGross = (thisMonthGross + thisMonthDiscount);
        double thisMonthInvoiceSale = pref.getInvoiceSaleTM() != null ? Double.parseDouble(pref.getInvoiceSaleTM()) : 0.0;
        double thisMonthProductive = pref.getMonthProdSumTM() != null ? Double.parseDouble(pref.getMonthProdSumTM()) : 0.0;
        double thisMonthNonProd = pref.getMonthNonProdSumTM() != null ? Double.parseDouble(pref.getMonthNonProdSumTM()) : 0.0;

        tvTMGross.setText(""+format.format(thisMonthTGross));
        tvTMDiscount.setText("" + format.format(thisMonthDiscount));
        tvTMNet.setText("" + format.format(thisMonthTGross + thisMonthDiscount - thisMonthReturn));
        tvTMReturn.setText(""+format.format(thisMonthReturn));
        tvTMinvoicesale.setText("" +format.format(thisMonthInvoiceSale));
        tvTMProductive.setText("" + format.format(thisMonthProductive));
        tvTMNonProductive.setText("" + format.format(thisMonthNonProd));

        setPreviousMonthFigures();
    }

    private void setPreviousMonthFigures()
    {
        double preMonthDiscount = pref.getTransOrdDiscSumPM() != null ? Double.parseDouble(pref.getTransOrdDiscSumPM()) : 0.0;
        double preMonthGross= pref.getTransOrdSumPM() != null ? Double.parseDouble(pref.getTransOrdSumPM()) : 0.0;
        double preMonthReturn = pref.getMonthReturnSumPM() != null ? Double.parseDouble(pref.getMonthReturnSumPM()) : 0.0;
        double previousMonthTGross = (preMonthGross + preMonthDiscount);
        double preMonthInvoiceSale = pref.getInvoiceSalePM() != null ? Double.parseDouble(pref.getInvoiceSalePM()) : 0.0;
        double prevMonthProductive = pref.getMonthProdSumPM() != null ? Double.parseDouble(pref.getMonthProdSumPM()) : 0.0;
        double prevMonthNonProd = pref.getMonthNonProdSumPM() != null ? Double.parseDouble(pref.getMonthNonProdSumPM()) : 0.0;

        tvPMGross.setText(""+format.format(previousMonthTGross));
        tvPMDiscount.setText("" + format.format(preMonthDiscount));
        tvPMNet.setText("" + format.format(preMonthGross + preMonthDiscount - preMonthReturn));
        tvPMReturn.setText(""+format.format(preMonthReturn));
        tvPMinvoicesale.setText("" +format.format(preMonthInvoiceSale));
        tvPMProductive.setText("" + format.format(prevMonthProductive));
        tvPMNonProductive.setText("" + format.format(prevMonthNonProd));

        setDailyFigures();
    }

    private void setDailyFigures()
    {
        double ordSum = pref.getDayOrderSum() != null ? Double.parseDouble(pref.getDayOrderSum()) : 0.0;
        double disSum = pref.getDayDiscountSum() != null ? Double.parseDouble(pref.getDayDiscountSum()) : 0.0;
        double retSum = pref.getDayReturnSum() != null ? Double.parseDouble(pref.getDayReturnSum()) : 0.0;

        tvSalesGross.setText(""+format.format( ordSum + disSum));
        tvDiscount.setText(""+format.format(Double.parseDouble(pref.getDayDiscountSum())));
        tvReturn.setText(""+format.format(Double.parseDouble(pref.getDayReturnSum())));
        tvProductive.setText(""+format.format(Double.parseDouble(pref.getDayProdSum())));
        tvNonprdctive.setText(""+format.format(Double.parseDouble(pref.getDayNonProdSum())));
        tvinvoicesale.setText(""+format.format(Double.parseDouble(pref.getDayInvoiceSum())));
        tvNetValue.setText(""+format.format(ordSum + disSum - retSum));

        notConfirmedData();
    }

    private void notConfirmedData()
    {
        double dailyAchieve = new DashboardNewDS(getActivity()).getDailyAchieve();
        double dailyTarget = new DashboardNewDS(getActivity()).getRepTarget() / 30 ;
        double thisMonthAchieve = new DashboardNewDS(getActivity()).getTMAchieve();
        double thisMonthTarget = new DashboardNewDS(getActivity()).getRepTarget();
        double preMonthTarget = new DashboardNewDS(getActivity()).getPMRepTarget();

        tvTMTarget.setText("" + format.format(thisMonthTarget));
        tvTMAchieve.setText("" + format.format(thisMonthAchieve));
        tvPMTarget.setText("" + format.format(preMonthTarget));
        tvAchieve.setText("" + format.format(dailyAchieve));
        tvTarget.setText("" + format.format(dailyTarget));
    }
}











