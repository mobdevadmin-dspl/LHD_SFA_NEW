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

public class fOrdDetSummaryDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FOrdHedSummaryDS";

    public fOrdDetSummaryDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int CreateOrUpdateDetData(ArrayList<fOrdDetSummary> detSummaryArrayList){
        int count=0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try{
            for( fOrdDetSummary ordDetSummary :detSummaryArrayList){

                cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.FORDDETTEMP + " WHERE " + DatabaseHelper.FORDDETTEMP_REFNO + "='" + ordDetSummary.getRefNo() + "' AND " + dbHelper.FORDDETTEMP_ITEMCODE + " = '" + ordDetSummary.getItemCode() + "' ", null);
                ContentValues values = new ContentValues();


                values.put(DatabaseHelper.FORDDETTEMP_REFNO, ordDetSummary.getRefNo());
                values.put(DatabaseHelper.FORDDETTEMP_TXNDATE, ordDetSummary.getTxnDate());
                values.put(DatabaseHelper.FORDDETTEMP_ITEMCODE, ordDetSummary.getItemCode());
                values.put(DatabaseHelper.FORDDETTEMP_QTY, ordDetSummary.getQty());
                values.put(DatabaseHelper.FORDDETTEMP_TAX, ordDetSummary.getTaxAmt());
                values.put(DatabaseHelper.FORDDETTEMP_Amt, ordDetSummary.getAmt());


                if (cursor.getCount() > 0) {
                    count = (int)  dB.update(DatabaseHelper.FORDDETTEMP, values, DatabaseHelper.FORDDETTEMP_REFNO + "='" + ordDetSummary.getRefNo() + "' AND " + dbHelper.FORDDETTEMP_ITEMCODE + " = '" + ordDetSummary.getItemCode() + "' ", null);
                } else {
                    count = (int)  dB.insert(DatabaseHelper.FORDDETTEMP, null, values);
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

            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.FORDDETTEMP, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.FORDDETTEMP, null, null);
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
    public  ArrayList<fOrdDetSummary>getPreSalesDetDetalis(String refno){
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<fOrdDetSummary>detSummaryArrayList=new ArrayList<>();
        String selectQurey="select * from " +DatabaseHelper.FORDDETTEMP + " WHERE " + dbHelper.FORDDETTEMP_REFNO + "='" + refno + "' ";
        Cursor cursor=null;
        try {
            cursor = dB.rawQuery(selectQurey, null);
            while (cursor.moveToNext()) {
                fOrdDetSummary   detSummary=new fOrdDetSummary();
                detSummary.setRefNo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORDDETTEMP_REFNO)));
                detSummary.setItemCode(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORDDETTEMP_ITEMCODE)));
                detSummary.setQty(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORDDETTEMP_QTY)));
                detSummary.setAmt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORDDETTEMP_Amt)));
                detSummary.setTxnDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORDDETTEMP_TXNDATE)));
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

}
