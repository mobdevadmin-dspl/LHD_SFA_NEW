package com.lankahardwared.lankahw.adapter;

/**
 * Created by Himas on 5/25/2017.
 */

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.lankahardwared.lankahw.model.FInvRHed;
import com.lankahardwared.lankahw.view.sales_return.SalesReturnCustomer;
import com.lankahardwared.lankahw.view.sales_return.SalesReturnDetails;
import com.lankahardwared.lankahw.view.sales_return.SalesReturnHeader;
import com.lankahardwared.lankahw.view.sales_return.SalesReturnSummary;

public class ReturnPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs = 0;
    private FInvRHed invRHed;

    public ReturnPagerAdapter(FragmentManager fm, int NumOfTabs, FInvRHed invRHed) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.invRHed = invRHed;

    }

    @Override
    public Fragment getItem(int position) {


            switch (position) {
                case 0:
                    SalesReturnCustomer frag1 = new SalesReturnCustomer();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("return",invRHed);
//                    frag1.setArguments(bundle);
                    return frag1;
                case 1:
                    SalesReturnHeader frag2 = new SalesReturnHeader();
                    return frag2;
                case 2:
                    SalesReturnDetails frag3 = new SalesReturnDetails();
                    Bundle bundleDet = new Bundle();
                    bundleDet.putSerializable("return",invRHed);
                    frag3.setArguments(bundleDet);
                    return frag3;
                case 3:
                    SalesReturnSummary frag4 = new SalesReturnSummary();
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