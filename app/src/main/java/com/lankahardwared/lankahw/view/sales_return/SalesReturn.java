package com.lankahardwared.lankahw.view.sales_return;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.CustomerDebtAdapter;
import com.lankahardwared.lankahw.adapter.ReturnPagerAdapter;
import com.lankahardwared.lankahw.control.IResponseListener;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.data.ControlDS;
import com.lankahardwared.lankahw.data.FDDbNoteDS;
import com.lankahardwared.lankahw.model.FDDbNote;
import com.lankahardwared.lankahw.model.FInvRHed;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.LayoutParams;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class SalesReturn extends Fragment {

    View view;
    Activity activity;
    TabLayout tabLayout;
    FloatingActionMenu fam;
    IResponseListener listener;
    int tabId;
    ViewPager viewPager;
    ReturnPagerAdapter adapter;
    boolean status;
    FInvRHed reHedTmp=null;
    private ControlDS controlDS;
    private int ImageViewStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_pre_sales, container, false);
        setHasOptionsMenu(true);
        activity = getActivity();
        controlDS = new ControlDS(getActivity());


        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Customer"));
        tabLayout.addTab(tabLayout.newTab().setText("Header"));
        tabLayout.addTab(tabLayout.newTab().setText("Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Summary"));

        viewPager = (ViewPager) view.findViewById(R.id.pager);

        Bundle mBundle = getArguments();
        if (mBundle != null) {
            status = mBundle.getBoolean("Active");
            reHedTmp= (FInvRHed) mBundle.getSerializable("return");

            if(reHedTmp!=null)
                Log.d("SALES_RETURN","" +reHedTmp.getFINVRHED_REFNO());

        }

        adapter = new ReturnPagerAdapter(getChildFragmentManager(), 4, reHedTmp);

        viewPager.setAdapter(adapter);





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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                tabLayout.getTabAt(position).select();

                if (position == 3)
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_RET_SUMMARY"));
                else if (position == 1)
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_RET_HEADER"));
                else if (position == 2)
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("TAG_RET_DETAILS"));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });



        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        SubActionButton.Builder builder = new SubActionButton.Builder(activity);

        ImageView icon_1 = new ImageView(getActivity());
        icon_1.setImageResource(R.drawable.float_1);

        ImageView icon_2 = new ImageView(getActivity());
        icon_2.setImageResource(R.drawable.float_2);

        ImageView icon_3 = new ImageView(getActivity());
        icon_3.setImageResource(R.drawable.float_3);

        ImageView icon_4 = new ImageView(getActivity());
        icon_4.setImageResource(R.drawable.float_4);

        ImageView icon_5 = new ImageView(getActivity());
        icon_5.setImageResource(R.drawable.float_5);

        ImageView icon_6 = new ImageView(getActivity());
        icon_6.setImageResource(R.drawable.float_6);

        SubActionButton button_1 = builder.setContentView(icon_1).build();
        SubActionButton button_2 = builder.setContentView(icon_2).build();
        SubActionButton button_3 = builder.setContentView(icon_3).build();
        SubActionButton button_4 = builder.setContentView(icon_4).build();
        SubActionButton button_5 = builder.setContentView(icon_5).build();
        SubActionButton button_6 = builder.setContentView(icon_6).build();

        LayoutParams params = new LayoutParams(70, 70);

        button_1.setLayoutParams(params);
        button_2.setLayoutParams(params);
        button_3.setLayoutParams(params);
        button_4.setLayoutParams(params);
        button_5.setLayoutParams(params);
        button_6.setLayoutParams(params);

        fam = new FloatingActionMenu.Builder(activity)
                .setStartAngle(0)
                .setEndAngle(-180)
                .setRadius(200)
                .addSubActionView(button_3)
                .addSubActionView(button_2)
                .addSubActionView(button_1)
                .addSubActionView(button_4)
                .addSubActionView(button_5)
                .addSubActionView(button_6)
                .attachTo(fab)
                .build();

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Add Clicked", Toast.LENGTH_SHORT).show();
                fam.close(true);
            }
        });

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = new SharedPref(getActivity()).getGlobalVal("RetkeyCusCode");

                if (s.equals(""))
                    mCustomerOutstandingDialog("");
                else
                    mCustomerOutstandingDialog(s);
                fam.close(true);
            }
        });

        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Delete Clicked", Toast.LENGTH_SHORT).show();
                fam.close(true);
            }
        });




        fam.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fam.close(true);
                    }
                }, 2000);
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {

            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }

        if (tabId == 3)
            inflater.inflate(R.menu.menu_sales, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (IResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        if (view.findViewById(R.id.container) != null)
//            mLoadTabFragment(new SalesReturnCustomer());
//    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.mnuDiscard)
            listener.whenDiscardClicked_Ret();
        else if (id == R.id.mnuSave)
            listener.whenSaveClicked_Ret();
        else if (id == R.id.mnuPause)
            listener.whenPauseClicked_Ret();

        return super.onOptionsItemSelected(item);
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void moveNextTab() {
        viewPager.setCurrentItem(1);    }

//    public void moveToDetails() {
//        viewPager.setCurrentItem(2);    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fam.close(true);
    }



    public void mCustomerOutstandingDialog(String debCode) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.customer_debtor, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Customer Outstanding " + (debCode.equals("") ? debCode : "(" + debCode + ")"));
        alertDialogBuilder.setView(promptView);

        final ListView listView = (ListView) promptView.findViewById(R.id.lvCusDebt);
        ArrayList<FDDbNote> list = new FDDbNoteDS(getActivity()).getDebtInfo(debCode);
        listView.setAdapter(new CustomerDebtAdapter(getActivity(), list));

        alertDialogBuilder.setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }



}





