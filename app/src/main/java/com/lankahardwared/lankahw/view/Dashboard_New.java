package com.lankahardwared.lankahw.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
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

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
//        toolbar = (Toolbar) rootView.findViewById(R.id.dashbard_toolbar);
//        back = (ImageView) rootView.findViewById(R.id.back);

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
        tvTMReturn = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_return);
        tvTMDiscount = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_discount);
        tvTMProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_productive_calls);
        tvTMNonProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_unproductive_calls);
        tvTMinvoicesale = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_invoice_sale_value);
        tvTMTarget = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_target_value);
        tvTMAchieve= (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_Achievement_calls);


        tvPMGross = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_gross_sale);
        tvPMNet = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_net_sale);
        tvPMReturn = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_return_value);
        tvPMDiscount = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_discount);
        tvPMProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_productive_calls);
        tvPMNonProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_unproductive_calls);
        tvPMinvoicesale = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_invoicesale_value);
        tvPMTarget = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_target_value);


        double dailyGross = new DashboardNewDS(getActivity()).getDailyGross();
        double dailyAchieve = new DashboardNewDS(getActivity()).getDailyAchieve();
        double dailyTarget = new DashboardNewDS(getActivity()).getRepTarget() / 30 ;
        double dailyDiscount = new DashboardNewDS(getActivity()).getDiscount();
        double dailyReturn = new DashboardNewDS(getActivity()).getTodayReturn();

        int nonprd = new DashboardNewDS(getActivity()).getNonProductiveCount();
        int ordcount = new DashboardNewDS(getActivity()).getProductivecount();

        double thisMonthDiscount = new DashboardNewDS(getActivity()).getTMDiscount();
        double preMonthDiscount = new DashboardNewDS(getActivity()).getPMDiscount();

        int tMordcount = new DashboardNewDS(getActivity()).getTMProductivecount();
        int pMordcount = new DashboardNewDS(getActivity()).getPMProductivecount();

        int tMNpcount = new DashboardNewDS(getActivity()).getTMNonProductiveCount();
        int pMNpcount = new DashboardNewDS(getActivity()).getPMNonProductiveCount();

        double thisMonthReturn  = new DashboardNewDS(getActivity()).getTMReturn();
        double preMonthReturn   = new DashboardNewDS(getActivity()).getPMReturn();

       double thisMonthAchieve = new DashboardNewDS(getActivity()).getTMAchieve();

        double thisMonthGross= new DashboardNewDS(getActivity()).getTMGross();
        double preMonthAchieve  = new DashboardNewDS(getActivity()).getpPMAchieve();

        double thisMonthTGross = (thisMonthGross + thisMonthDiscount);
        double preMonthGross  = (preMonthAchieve + preMonthDiscount);

        double thisMonthInvoiceSale = Double.parseDouble(SharedPref.getInstance(getActivity()).getInvoiceSaleTM());
       // double thisMonthInvoiceSale = new DashboardNewDS(getActivity()).getTMInvoiceSale();
       // double preMonthInvoiceSale = new DashboardNewDS(getActivity()).getPMInvoiceSale();
       double preMonthInvoiceSale = Double.parseDouble(SharedPref.getInstance(getActivity()).getInvoiceSalePM());

       double thisMonthTarget = new DashboardNewDS(getActivity()).getRepTarget();
        double preMonthTarget = new DashboardNewDS(getActivity()).getPMRepTarget();

        tvTMGross.setText(""+format.format(thisMonthTGross));
        tvTMNet.setText("" + format.format(thisMonthTGross - thisMonthDiscount + thisMonthReturn));
        tvTMReturn.setText(""+format.format(thisMonthReturn));
        tvTMDiscount.setText("" + format.format(thisMonthDiscount));
        tvTMinvoicesale.setText("" +format.format(thisMonthInvoiceSale));
        tvTMProductive.setText("" + tMordcount);
        tvTMNonProductive.setText("" + tMNpcount);
        tvTMTarget.setText("" + format.format(thisMonthTarget));
        tvTMAchieve.setText("" + format.format(thisMonthAchieve));


        tvPMGross.setText(""+format.format(preMonthGross));
        tvPMNet.setText("" + format.format(preMonthGross - preMonthDiscount + preMonthReturn));
        tvPMReturn.setText(""+format.format(preMonthReturn));
        tvPMDiscount.setText("" + format.format(preMonthDiscount));
        tvPMinvoicesale.setText("" +format.format(preMonthInvoiceSale));
        tvPMProductive.setText("" + pMordcount);
        tvPMNonProductive.setText("" + pMNpcount);
        tvPMTarget.setText("" + format.format(preMonthTarget));

        tvSalesGross.setText("" + format.format(dailyGross+dailyDiscount));
        tvAchieve.setText("" + format.format(dailyAchieve));
        tvNetValue.setText("" + format.format(dailyGross-dailyDiscount+dailyReturn));
        tvReturn.setText("" + format.format(dailyReturn));
        tvDiscount.setText("" + format.format(dailyDiscount));
        tvinvoicesale.setText("" +format.format(dailyGross));
        tvProductive.setText("" + ordcount);
        tvNonprdctive.setText("" + nonprd);
        tvTarget.setText("" + format.format(dailyTarget));

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
}











