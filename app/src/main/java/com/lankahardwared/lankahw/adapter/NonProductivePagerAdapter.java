package com.lankahardwared.lankahw.adapter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.lankahardwared.lankahw.model.FDaynPrdHed;
import com.lankahardwared.lankahw.view.non_productive.NonProductiveCustomer;
import com.lankahardwared.lankahw.view.non_productive.NonProductiveDetail;


/**
 * Created by Rashmi on 4/27/2018.
 */

public class NonProductivePagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs = 0;
    private FDaynPrdHed nonProdHed;

    public NonProductivePagerAdapter(FragmentManager fm, int NumOfTabs, FDaynPrdHed nonProdHed) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.nonProdHed = nonProdHed;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                NonProductiveCustomer frag1 = new NonProductiveCustomer();
                return frag1;
            case 1:
                NonProductiveDetail frag2 = new NonProductiveDetail();
                Bundle bundlef = new Bundle();
                bundlef.putSerializable("nonPrdHed",nonProdHed);
                frag2.setArguments(bundlef);
                return frag2;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
