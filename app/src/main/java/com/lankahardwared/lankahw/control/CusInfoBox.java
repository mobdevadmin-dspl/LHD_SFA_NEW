package com.lankahardwared.lankahw.control;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.CustomerDebtAdapter;
import com.lankahardwared.lankahw.data.FDDbNoteDS;
import com.lankahardwared.lankahw.data.RouteDS;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.FDDbNote;

import java.util.ArrayList;

public class CusInfoBox {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;

    public void debtorDetailsDialogbox(final Context context, String title, final Debtor debtor, final boolean isPreSale) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View promptView = layoutInflater.inflate(R.layout.selected_debtor_details, null);
//        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        final EditText debCode = (EditText) promptView.findViewById(R.id.EditText1);
        final EditText debName = (EditText) promptView.findViewById(R.id.EditText01);
        final EditText debAddress = (EditText) promptView.findViewById(R.id.EditText02);
        final EditText debTele = (EditText) promptView.findViewById(R.id.EditText03);
        final EditText debMobile = (EditText) promptView.findViewById(R.id.EditText04);
        final EditText DebRoute = (EditText) promptView.findViewById(R.id.EditText05);
        final EditText debCrLimit = (EditText) promptView.findViewById(R.id.EditText08);
        final EditText debCrPeriod = (EditText) promptView.findViewById(R.id.EditText09);
        final EditText debnic = (EditText) promptView.findViewById(R.id.EditText10);
        final EditText debbr = (EditText) promptView.findViewById(R.id.EditText11);
        //final Button viewMore = (Button)promptView.findViewById(R.id.btn_view_more);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title.toUpperCase());

        alertDialogBuilder.setView(promptView);

        RouteDS routeDS = new RouteDS(context);

        debCode.setText(debtor.getFDEBTOR_CODE());
        debName.setText(debtor.getFDEBTOR_NAME());
        debAddress.setText(debtor.getFDEBTOR_ADD1() + " " + debtor.getFDEBTOR_ADD2() + " " + debtor.getFDEBTOR_ADD3());
        debTele.setText(debtor.getFDEBTOR_TELE());
        debMobile.setText(debtor.getFDEBTOR_MOB());
        DebRoute.setText(routeDS.getRouteNameByCode(debtor.getFDEBTOR_CODE()));
        debCrLimit.setText(debtor.getFDEBTOR_CRD_LIMIT());
        debCrPeriod.setText(debtor.getFDEBTOR_CRD_PERIOD());
        debnic.setText(debtor.getFDEBTOR_NIC());
        debbr.setText(debtor.getFDEBTOR_BIS_REG());

//        viewMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                outstandingDialog(debtor.getFDEBTOR_CODE(),context);
//            }
//        });

        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();


                            }
                        })
                .setPositiveButton("View More",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                    outstandingDialog(debtor.getFDEBTOR_CODE(), context);
                                    dialog.cancel();
                            }
                        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();

        if(isPreSale)
        {
            alertD.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        }
        else
        {
            alertD.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }
    }

    public void outstandingDialog(String debCode, Context context)
    {
        Log.d("CLICK_ME", "VIEW:" + debCode);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.customer_debtor, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Customer Outstanding " + (debCode.equals("") ? debCode : "(" + debCode + ")"));
        alertDialogBuilder.setView(promptView);

        final ListView listView = (ListView) promptView.findViewById(R.id.lvCusDebt);
        ArrayList<FDDbNote> list = new FDDbNoteDS(context).getDebtInfo(debCode);
        listView.setAdapter(new CustomerDebtAdapter(context, list));

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
