package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Dhanushika on 2/8/2018.
 */

public class fInvDetSummaryDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "fInvDetSummaryDS";

    public fInvDetSummaryDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int CreateOrUpdateDetData(ArrayList<finvDetSummary> finvDetSummaryArrayList){
        int count=0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try{
            for( com.lankahardwared.lankahw.data.finvDetSummary finvDetSummary :finvDetSummaryArrayList){

                cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.FINVDETTEMP + " WHERE " + DatabaseHelper.FINVDETTEMP_REFNO + "='" + finvDetSummary.getRefNo() + "' AND " + dbHelper.FINVDETTEMP_ITEMCODE + " = '" + finvDetSummary.getItemCode() + "' ", null);
                ContentValues values = new ContentValues();


                values.put(DatabaseHelper.FINVDETTEMP_REFNO, finvDetSummary.getRefNo());
                values.put(DatabaseHelper.FINVDETTEMP_TXNDATE, finvDetSummary.getTxnDate());
                values.put(DatabaseHelper.FINVDETTEMP_ITEMCODE, finvDetSummary.getItemCode());
                values.put(DatabaseHelper.FINVDETTEMP_QTY, finvDetSummary.getQty());
                values.put(DatabaseHelper.FINVDETTEMP_TAX, finvDetSummary.getTaxAmt());
                values.put(DatabaseHelper.FINVDETTEMP_Amt, finvDetSummary.getAmt());
                values.put(DatabaseHelper.FINVDETTEMP_Type,finvDetSummary.getType());


                if (cursor.getCount() > 0) {
                    count = (int)  dB.update(DatabaseHelper.FINVDETTEMP, values, DatabaseHelper.FINVDETTEMP_REFNO + "='" + finvDetSummary.getRefNo() + "' AND " + dbHelper.FINVDETTEMP_ITEMCODE + " = '" + finvDetSummary.getItemCode() + "' ", null);
                } else {
                    count = (int)  dB.insert(DatabaseHelper.FINVDETTEMP, null, values);
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }
    //----------------------------------------------------------------------------------------------------------------------------


    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.FINVHEDTEMP, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.FINVHEDTEMP, null, null);
                Log.v("Success", success + "");
            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return count;

    }
//--------------------------------------------------------------------------------------------------------------------------------
    public  ArrayList<finvDetSummary>getVansalesDetalis(String refno){
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<finvDetSummary>detSummaryArrayList=new ArrayList<>();
        String selectQurey="select * from " + DatabaseHelper.FINVDETTEMP + " WHERE " + dbHelper.FINVDETTEMP_REFNO + "='" + refno + "' ";
        Cursor cursor=null;
        try {
            cursor = dB.rawQuery(selectQurey, null);
            while (cursor.moveToNext()) {
                finvDetSummary detSummary=new finvDetSummary();
                detSummary.setRefNo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDETTEMP_REFNO)));
                detSummary.setItemCode(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDETTEMP_ITEMCODE)));
                detSummary.setQty(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDETTEMP_QTY)));
                detSummary.setAmt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDETTEMP_Amt)));
                detSummary.setTxnDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDETTEMP_TXNDATE)));
                detSummaryArrayList.add(detSummary);
            }



        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
                dB.close();
            }
        }

        return detSummaryArrayList;
    }
    //----------------------------------------------
    public ArrayList<finvDetSummary>finvDetSummaryArrayList(String refNo){
        if(dB ==null){
            open();
        }else  if(!dB.isOpen()){
            open();
        }
        ArrayList<finvDetSummary>finvDetSummaryArrayList=new ArrayList<>();
    //    String selectQuery="select * from " +DatabaseHelper.FINVDETTEMP + " WHERE " + dbHelper.FINVDETTEMP_REFNO + "='" + refno + "' ";


        return finvDetSummaryArrayList;
    }

}
