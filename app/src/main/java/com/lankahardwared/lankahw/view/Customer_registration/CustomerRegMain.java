package com.lankahardwared.lankahw.view.Customer_registration;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.NewCustomerAdapter;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.NewCustomerDS;
import com.lankahardwared.lankahw.model.NewCustomer;
import com.lankahardwared.lankahw.view.IconPallet;
import com.lankahardwared.lankahw.view.MainActivity;

import java.util.ArrayList;

/**
 * Created by Dhanushika on 4/4/2018.
 */

public class CustomerRegMain extends Fragment {
    private ListView listView_cusDet;
    private View view;
    private ArrayList<NewCustomer> customerArrayList;
    private FloatingActionButton fab;
    String btnType = "N";
    Activity activity;
    ReferenceNum referenceNum;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cus_reg_main, container, false);

        listView_cusDet = (ListView) view.findViewById(R.id.lvCuslist);
        referenceNum = new ReferenceNum(getActivity());

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        activity = getActivity();
        activity.setTitle("Customer Registration");
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        toolbar.setLogo(R.drawable.dm_logo_64);
        setHasOptionsMenu(true);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        getActivity().setTitle("Customer Registration");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnType.equals("N")) {
                    activity.setTitle("UPLOAD DUE CUSTOMERS");
                    fab.setImageResource(R.drawable.tick);
                    btnType = "U";
                    fatchData("N");
                } else {
                    activity.setTitle("UPLOADED CUSTOMERS");
                    fab.setImageResource(R.drawable.cross);
                    fatchData("U");
                    btnType = "N";
                }
            }
        });
        fatchData("N");
         /* connect context menu with listview for long click */
        registerForContextMenu(listView_cusDet);

        listView_cusDet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewCustomer newCustomer = customerArrayList.get(position);
                deleteDialog(getActivity(), newCustomer.getCUSTOMER_ID());
            }
        });


        //DISABLED BACK NAVIGATION
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("", "keyCode: " + keyCode);


                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    Toast.makeText(getActivity(), "Back button disabled!", Toast.LENGTH_SHORT).show();
                    return true;
                }else if ((keyCode == KeyEvent.KEYCODE_HOME)) {

                    getActivity().finish();

                    return true;

                } else {
                    return false;
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }
        inflater.inflate(R.menu.frag_nonprd_menu, menu);

            SearchView searchView = (SearchView) menu.findItem(R.id.searchItems).getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    listView_cusDet.clearTextFilter();
                    customerArrayList=new NewCustomerDS(getActivity()).getAllNewCusDetails(newText,btnType.equals("N") ? "U" : "N");
                    listView_cusDet.setAdapter(new NewCustomerAdapter(getActivity(),customerArrayList));

                    return false;
                }
            });

        super.onCreateOptionsMenu(menu, inflater);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
//
            if (v.getId() == R.id.lvexpenselist) {
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.menu_list, menu);
            }
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            if (item.getItemId() == R.id.enterNewItem) {
//               UtilityContainer.mLoadFragment(new AddNewCusRegistration(), getActivity());
                Intent intent = new Intent(getActivity(),AddCustomerActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.exitExpence) {
                UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
            }
        return super.onOptionsItemSelected(item);
    }

    public void fatchData(String param) {

        listView_cusDet.clearTextFilter();
        customerArrayList = new NewCustomerDS(getActivity()).getAllNewCusDetails("", param);
        if(customerArrayList.size()==0){
            this.listView_cusDet.setEmptyView(view.findViewById(android.R.id.empty));
        }else{
            listView_cusDet.setAdapter(new NewCustomerAdapter(getActivity(), customerArrayList));
        }

    }


    private void deleteDialog(final Context context, final String refno) {

        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure you want to cancel this entry?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (new NewCustomerDS(getActivity()).isEntrySynced(refno))
                    Toast.makeText(getActivity(), "Synced entry. Unable to delete.", Toast.LENGTH_LONG).show();
                else {
                    int count = new NewCustomerDS(getActivity()).deleteRecord(refno);
                    if (count > 0) {
                        new NewCustomerDS(getActivity()).deleteRecord(refno);
                        fatchData("");
                    }
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        androidx.appcompat.app.AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }
}
