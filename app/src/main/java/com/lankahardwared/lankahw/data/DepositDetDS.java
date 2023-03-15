package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.Depodet;
import com.lankahardwared.lankahw.model.InvDet;

import java.util.ArrayList;

import static com.lankahardwared.lankahw.data.DatabaseHelper.FBANKDEPODET_RECEIPT_REFNO;

/**
 * Created by Yasith on 3/1/2019.
 */

public class DepositDetDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "DepoDetDS ";

    public DepositDetDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }



    public int createOrUpdateDepositDet(Depodet depoDet)
    {

        int serverdbID = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            if(depoDet != null)
            {

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FBANKDEPODET_REFNO, depoDet.getFBANKDEPODET_REFNO());
                values.put(FBANKDEPODET_RECEIPT_REFNO, depoDet.getFBANKDEPODET_RECEIPT_REFNO());
                values.put(DatabaseHelper.FBANKDEPODET_DEBTOR_CODE, depoDet.getFBANKDEPODET_DEBTOR_CODE());
                values.put(DatabaseHelper.FBANKDEPODET_RECEIPT_TXNDATE, depoDet.getFBANKDEPODET_RECEIPT_TXNDATE());
                values.put(DatabaseHelper.FBANKDEPODET_RECEIPT_CHEQNO, depoDet.getFBANKDEPODET_RECEIPT_CHEQNO());
                values.put(DatabaseHelper.FBANKDEPODET_RECEIPT_AMT, depoDet.getFBANKDEPODET_RECEIPT_AMT());


                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.FBANKDEPODET + " WHERE " + DatabaseHelper.FBANKDEPODET_REFNO + " = '" + depoDet.getFBANKDEPODET_REFNO() + "' and "+DatabaseHelper.FBANKDEPODET_RECEIPT_REFNO+" = '"+depoDet.getFBANKDEPODET_RECEIPT_REFNO()+"'", null);

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.FBANKDEPODET, values, DatabaseHelper.FBANKDEPODET_REFNO + " =? and "+DatabaseHelper.FBANKDEPODET_RECEIPT_REFNO +" =?", new String[]{String.valueOf(depoDet.getFBANKDEPODET_REFNO()),String.valueOf(depoDet.getFBANKDEPODET_RECEIPT_REFNO())});
                    Log.v(TAG, " Record updated");
                } else {
                    serverdbID = (int) dB.insert(DatabaseHelper.FBANKDEPODET, null, values);
                    Log.v(TAG, " Record Inserted " + serverdbID);
                }
                cursor.close();
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return serverdbID;

    }



    public int getItemCount(String refNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            String selectQuery = "SELECT count(RefNo) as RefNo FROM " + DatabaseHelper.FBANKDEPODET + " WHERE  " + DatabaseHelper.FBANKDEPODET_REFNO + "='" + refNo + "'";
            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                return Integer.parseInt(cursor.getString(cursor.getColumnIndex("RefNo")));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dB.close();
        }
        return 0;

    }


    //Get allocated receipts RefNo to update RecHed IsDeposit Column
    public ArrayList<String> getSelectedReceiptRefNoByDepositRefNo(String refNo)
    {
        ArrayList<String> selectedRefNoList = new ArrayList<String>();

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            String selectQuery = "SELECT "+FBANKDEPODET_RECEIPT_REFNO+" as ReceiptRefNo FROM " + DatabaseHelper.FBANKDEPODET + " WHERE  " + DatabaseHelper.FBANKDEPODET_REFNO + "='" + refNo + "'";
            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                selectedRefNoList.add(cursor.getString(cursor.getColumnIndex("ReceiptRefNo")));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dB.close();
        }

        return selectedRefNoList;
    }



    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Reset deposit details*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int resetData(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + DatabaseHelper.FBANKDEPODET + " WHERE " + DatabaseHelper.FBANKDEPODET_REFNO + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                count = dB.delete(DatabaseHelper.FBANKDEPODET, DatabaseHelper.FBANKDEPODET_REFNO + " ='" + refno + "'", null);
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
    
    
    public ArrayList<Depodet> getAllDetByRefNo(String refNo)
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Depodet> list = new ArrayList<Depodet>();

        String selectQuery = "select * from " + DatabaseHelper.FBANKDEPODET + " WHERE " + DatabaseHelper.FINVDET_REFNO + "='" + refNo + "';";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {

            while (cursor.moveToNext()) {

                Depodet depodet = new Depodet();

                depodet.setFBANKDEPODET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPODET_REFNO)));
                depodet.setFBANKDEPODET_RECEIPT_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPODET_RECEIPT_REFNO)));
                depodet.setFBANKDEPODET_DEBTOR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPODET_DEBTOR_CODE)));
                depodet.setFBANKDEPODET_RECEIPT_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPODET_RECEIPT_TXNDATE)));
                depodet.setFBANKDEPODET_RECEIPT_CHEQNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPODET_RECEIPT_CHEQNO)));
                depodet.setFBANKDEPODET_RECEIPT_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPODET_RECEIPT_AMT)));

                list.add(depodet);

            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        
        
        return list;
    }

}
