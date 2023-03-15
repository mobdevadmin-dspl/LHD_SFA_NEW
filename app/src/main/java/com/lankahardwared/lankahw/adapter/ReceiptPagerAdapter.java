package com.lankahardwared.lankahw.adapter;

/**
 * Created by Himas on 5/25/2017.
 */

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.lankahardwared.lankahw.view.receipt.ReceiptCustomer;
import com.lankahardwared.lankahw.view.receipt.ReceiptHeader;
import com.lankahardwared.lankahw.view.receipt.ReceiptOrderDetails;
import com.lankahardwared.lankahw.view.receipt.ReceiptSummary;

public class ReceiptPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs = 0;


    public ReceiptPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ReceiptCustomer frag1 = new ReceiptCustomer();
                return frag1;
            case 1:
                ReceiptHeader frag2 = new ReceiptHeader();
                return frag2;
            case 2:
                ReceiptOrderDetails frag3 = new ReceiptOrderDetails();
                return frag3;
            case 3:
                ReceiptSummary frag4 = new ReceiptSummary();
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