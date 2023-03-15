package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.FDDbNote;
import com.lankahardwared.lankahw.model.mapper.FOtherTransactions;

import java.util.ArrayList;

public class FOtherTransDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FotherTrans ";

    public FOtherTransDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int deleteAll() {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FOTHERTRANS, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FOTHERTRANS, null, null);
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

    public int createOrUpdateFDDbNote(ArrayList<FOtherTransactions> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            for (FOtherTransactions fotherTr : list) {

                cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FOTHERTRANS + " WHERE " + DatabaseHelper.FOTHERTRANS_REFNO + "='" + fotherTr.getRefno() + "'", null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FOTHERTRANS_AMOUNT, fotherTr.getAmount());
                values.put(DatabaseHelper.FOTHERTRANS_DEBCODE, fotherTr.getDebCode());
                values.put(DatabaseHelper.FOTHERTRANS_REFNO, fotherTr.getRefno());
                values.put(DatabaseHelper.FOTHERTRANS_REFNO1, fotherTr.getRefNo1());
                values.put(DatabaseHelper.FOTHERTRANS_TXNDATE, fotherTr.getTxnDate());
                values.put(DatabaseHelper.FOTHERTRANS_TXNTYPE, fotherTr.getTxnType());


                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FOTHERTRANS, values, DatabaseHelper.FOTHERTRANS_REFNO + "=?", new String[]{fotherTr.getRefno().toString()});
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FOTHERTRANS, null, values);
                }

            }

        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;
    }

    public ArrayList<FOtherTransactions> getTransDet(String refCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FOtherTransactions> list = new ArrayList<FOtherTransactions>();
        try {
            String selectQuery = "";

            if (!refCode.equals(""))
                selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FOTHERTRANS +" WHERE "+DatabaseHelper.FOTHERTRANS_REFNO1 +" LIKE '"+refCode.trim()+"'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                FOtherTransactions fotherTr = new FOtherTransactions();
                fotherTr.setAmount(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FOTHERTRANS_AMOUNT)));
                fotherTr.setDebCode(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FOTHERTRANS_DEBCODE)));
                fotherTr.setRefno(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FOTHERTRANS_REFNO)));
                fotherTr.setRefNo1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FOTHERTRANS_REFNO1)));
                fotherTr.setTxnDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FOTHERTRANS_TXNDATE)));
                fotherTr.setTxnType(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FOTHERTRANS_TXNTYPE)));

                list.add(fotherTr);
            }

            cursor.close();

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }
        return list;
    }
}
