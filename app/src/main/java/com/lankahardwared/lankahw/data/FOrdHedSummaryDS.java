package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Dhanushika on 2/8/2018.
 */

public class FOrdHedSummaryDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FOrdHedSummaryDS";

    public FOrdHedSummaryDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }
    public int CreateOrUpdateHedData(ArrayList<fOrdhedSummary>fOrdhedSummaryArrayList){
        int count=0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
       try{
           for(fOrdhedSummary fOrdhedSummary :fOrdhedSummaryArrayList){
                 cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.FORDHEDTEMP + " WHERE " + DatabaseHelper.FORDHEDTEMP_refNo + "='" + fOrdhedSummary.getRefNo() +  "' AND " + dbHelper.FORDHEDTEMP_debcode + " = '" + fOrdhedSummary.getDebCode().trim() + "' " ,null);
               ContentValues values = new ContentValues();

               values.put(DatabaseHelper.FORDHEDTEMP_debcode, fOrdhedSummary.getDebCode().trim());
               values.put(DatabaseHelper.FORDHEDTEMP_refNo,fOrdhedSummary.getRefNo());
               values.put(DatabaseHelper.FORDHEDTEMP_totAmt, fOrdhedSummary.getTotalAmt());
               values.put(DatabaseHelper.FORDHEDTEMP_totDis, fOrdhedSummary.getTotalDis());
               values.put(DatabaseHelper.FORDHEDTEMP_txnDate, fOrdhedSummary.getTxnDate());

               if (cursor.getCount() > 0) {

                   count = (int) dB.update(DatabaseHelper.FORDHEDTEMP, values, DatabaseHelper.FORDHEDTEMP_refNo + "='" + fOrdhedSummary.getRefNo() +  "' AND " + dbHelper.FORDHEDTEMP_debcode + " = '" + fOrdhedSummary.getDebCode().trim()+ "' ", null);
               } else {
                   count = (int) dB.insert(DatabaseHelper.FORDHEDTEMP, null, values);
               }
           }



       }catch (Exception e){
           e.printStackTrace();
       }

        return count;
    }

    //--------------------------------------------------------------------------------------------------------------
    public  ArrayList<fOrdhedSummary>getPreSalseSummary(String debCode){
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        ArrayList<fOrdhedSummary>arrayList=new ArrayList<>();
        String selectQuery="select * from "+ DatabaseHelper.FORDHEDTEMP + " where "+DatabaseHelper.FORDHEDTEMP_debcode +"= '" + debCode + "'";
        Cursor cursor=null;
        try {
            cursor = dB.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                fOrdhedSummary summary=new fOrdhedSummary();
                summary.setRefNo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORDHEDTEMP_refNo)));
                summary.setDebCode(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORDHEDTEMP_debcode)));
                summary.setTotalAmt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORDHEDTEMP_totAmt)));
                summary.setTotalDis(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORDHEDTEMP_totDis)));
                summary.setTxnDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORDHEDTEMP_txnDate)));
                summary.setlistChildData(new  fOrdDetSummaryDS(context).getPreSalesDetDetalis(summary.getRefNo()));
                arrayList.add(summary);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
                dB.close();
            }
        }
            return arrayList;
    }

}
