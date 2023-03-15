package com.lankahardwared.lankahw.adapter;

/**
 * Created by Himas on 5/25/2017.
 */

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.lankahardwared.lankahw.model.InvHed;
import com.lankahardwared.lankahw.view.vansale.VanSaleCustomer;
import com.lankahardwared.lankahw.view.vansale.VanSalesHeader;
import com.lankahardwared.lankahw.view.vansale.VanSalesOrderDetails;
import com.lankahardwared.lankahw.view.vansale.VanSalesSummary;

public class VanPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs = 0;
    private InvHed tmpinvHed;


    public VanPagerAdapter(FragmentManager fm, int NumOfTabs, InvHed tmpinvHed) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.tmpinvHed=tmpinvHed;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                VanSaleCustomer frag1 = new VanSaleCustomer();
                Bundle bundle = new Bundle();
                bundle.putSerializable("order",tmpinvHed);
                frag1.setArguments(bundle);
                return frag1;
            case 1:
                VanSalesHeader frag2 = new VanSalesHeader();
                return frag2;
            case 2:
                VanSalesOrderDetails frag3 = new VanSalesOrderDetails();
                Bundle bundleF = new Bundle();
                bundleF.putSerializable("order",tmpinvHed);
                frag3.setArguments(bundleF);
                return frag3;
            case 3:
                VanSalesSummary frag4 = new VanSalesSummary();
                return frag4;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}