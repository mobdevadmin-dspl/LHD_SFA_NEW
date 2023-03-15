package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.FinvHedL3;

import java.util.ArrayList;

public class FinvHedL3DS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FinvHedL3DS ";

    public FinvHedL3DS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }


    public int createOrUpdateFinvHedL3(ArrayList<FinvHedL3> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            for (FinvHedL3 finvHedL3 : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FINVHEDL3 + " WHERE " + DatabaseHelper.FINVHEDL3_REF_NO + "='" + finvHedL3.getFINVHEDL3_REF_NO() + "'", null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FINVHEDL3_DEB_CODE, finvHedL3.getFINVHEDL3_DEB_CODE());
                values.put(DatabaseHelper.FINVHEDL3_REF_NO, finvHedL3.getFINVHEDL3_REF_NO());
                values.put(DatabaseHelper.FINVHEDL3_REF_NO1, finvHedL3.getFINVHEDL3_REF_NO1());
                values.put(DatabaseHelper.FINVHEDL3_TOTAL_AMT, finvHedL3.getFINVHEDL3_TOTAL_AMT());
                values.put(DatabaseHelper.FINVHEDL3_TOTAL_TAX, finvHedL3.getFINVHEDL3_TOTAL_TAX());
                values.put(DatabaseHelper.FINVHEDL3_TXN_DATE, finvHedL3.getFINVHEDL3_TXN_DATE());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FINVHEDL3, values, DatabaseHelper.FINVHEDL3_REF_NO + "=?", new String[]{finvHedL3.getFINVHEDL3_REF_NO().toString()});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FINVHEDL3, null, values);
                    Log.v(TAG, "Inserted " + count);
                }
                cursor.close();
            }

        } catch (Exception e) {
            Log.v(TAG, e.toString());
        } finally {
            dB.close();
        }
        return count;
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
            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FINVHEDL3, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FINVHEDL3, null, null);
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
}
