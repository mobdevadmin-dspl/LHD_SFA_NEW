package com.lankahardwared.lankahw.view.non_productive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.NonProductivePagerAdapter;
import com.lankahardwared.lankahw.model.FDaynPrdHed;


/**
 * Created by Rashmi on 4/27/2018.
 */

public class NonProductiveManage extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    NonProductivePagerAdapter adapter;
    Activity activity;
    boolean status;
    private   String sp_url;
    FDaynPrdHed tempNonProdHed = null;
    public static SharedPreferences localSP;
    public static final String SETTINGS = "SETTINGS";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nonproductive_management, container, false);

        activity = getActivity();
//        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        sp_url= localSP.getString("URL", "").toString();

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Customer"));  //0
        tabLayout.addTab(tabLayout.newTab().setText("Detail"));  //1
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                               @Override
                                               public void onTabSelected(TabLayout.Tab tab) {
                                                   viewPager.setCurrentItem(tab.getPosition());
                                               }

                                               @Override
                                               public void onTabUnselected(TabLayout.Tab tab) {
                                               }

                                               @Override
                                               public void onTabReselected(TabLayout.Tab tab) {
                                               }
                                           }

        );

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        Bundle mBundle = getArguments();

        if (mBundle != null) {
            status = mBundle.getBoolean("Active");
            tempNonProdHed= (FDaynPrdHed) mBundle.getSerializable("nonPrdHed");

            if(tempNonProdHed!=null)
                Log.d("<>********hh********","" +tempNonProdHed.getNONPRDHED_REFNO());

        }
        adapter = new NonProductivePagerAdapter(getChildFragmentManager(), 2,tempNonProdHed);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                tabLayout.getTabAt(position).select();
                  Log.d("<======>","click position "+ position);

                  if(position == 1){
                      LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_HEADER"));
                  }else{
                      Toast.makeText(getActivity(),"SELECT CUSTOMER",Toast.LENGTH_LONG).show();
                      Log.d(NonProductiveManage.class.getSimpleName(),"I'm here");
                  }
                //if status =1 show image Details Tab bar
//                if (ImageViewStatus == 1) {
//                    if (position == 4)
//                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_SUMMARY"));
//                    else if (position == 1)
//                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_HEADER"));
//                    else if (position == 2)
//                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_ImG_DETAILS"));
//                    else if (position == 3)
//                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_DETAILS"));
//                } else {         //if status =0 hide image Details Tab bar
//                    if (position == 3)
//                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_SUMMARY"));
//                    else if (position == 1)
//                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_HEADER"));
//                    else if (position == 2)
//                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_DETAILS"));
//
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        return view;
    }
        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mMoveToHeader() {
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (status)
            viewPager.setCurrentItem(1);

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
}
