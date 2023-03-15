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

public class FInvHedSummaryDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FInvHedSummaryDS";

    public FInvHedSummaryDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }
    public int CreateOrUpdateHedData(ArrayList<finvhedSummary>finvhedSummaryArrayList){
        int count=0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
       try{
           for(finvhedSummary finvhedSummary :finvhedSummaryArrayList){
                 cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.FINVHEDTEMP + " WHERE " + DatabaseHelper.FINVHED_refNo + "='" + finvhedSummary.getRefNo() +  "' AND " + dbHelper.FINVHED_debcode + " = '" + finvhedSummary.getDebCode().trim() + "' " ,null);
               ContentValues values = new ContentValues();

               values.put(DatabaseHelper.FINVHED_debcode, finvhedSummary.getDebCode().trim());
               values.put(DatabaseHelper.FINVHED_refNo,finvhedSummary.getRefNo());
               values.put(DatabaseHelper.FINVHED_totAmt, finvhedSummary.getTotalAmt());
               values.put(DatabaseHelper.FINVHED_totDis, finvhedSummary.getTotalDis());
               values.put(DatabaseHelper.FINVHED_txnDate, finvhedSummary.getTxnDate());

               if (cursor.getCount() > 0) {

                   count = (int) dB.update(DatabaseHelper.FINVHEDTEMP, values, DatabaseHelper.FINVHED_refNo + "='" + finvhedSummary.getRefNo() +  "' AND " + dbHelper.FINVHED_debcode + " = '" + finvhedSummary.getDebCode().trim()+ "' ", null);
               } else {
                   count = (int) dB.insert(DatabaseHelper.FINVHEDTEMP, null, values);
               }
           }



       }catch (Exception e){
           e.printStackTrace();
       }

        return count;
    }

    //--------------------------------------------------------------------------------------------------------------
    public  ArrayList<finvhedSummary>getFinvSummary(String debCode){
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        ArrayList<finvhedSummary>arrayList=new ArrayList<>();
        String selectQuery="select * from "+ DatabaseHelper.FINVHEDTEMP + " where "+DatabaseHelper.FINVHED_debcode +"= '" + debCode + "'";
        Cursor cursor=null;
        try {
            cursor = dB.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                finvhedSummary summary=new finvhedSummary();
                summary.setRefNo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_refNo)));
                summary.setDebCode(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_debcode)));
                summary.setTotalAmt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_totAmt)));
                summary.setTotalDis(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_totDis)));
                summary.setTxnDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_txnDate)));
                summary.setlistChildData(new  fInvDetSummaryDS(context).getVansalesDetalis(summary.getRefNo()));
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
