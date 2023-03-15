package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.FinvDetL3;

import java.util.ArrayList;

public class FinvDetL3DS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FinvDetL3DS ";

    public FinvDetL3DS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateFinvDetL3(ArrayList<FinvDetL3> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            for (FinvDetL3 finvDetL3 : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FINVDETL3 + " WHERE " + DatabaseHelper.FINVDETL3_REF_NO + "='" + finvDetL3.getFINVDETL3_REF_NO() + "' AND " + DatabaseHelper.FINVDETL3_ITEM_CODE + "='" + finvDetL3.getFINVDETL3_ITEM_CODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FINVDETL3_AMT, finvDetL3.getFINVDETL3_AMT());
                values.put(DatabaseHelper.FINVDETL3_ITEM_CODE, finvDetL3.getFINVDETL3_ITEM_CODE());
                values.put(DatabaseHelper.FINVDETL3_QTY, finvDetL3.getFINVDETL3_QTY());
                values.put(DatabaseHelper.FINVDETL3_REF_NO, finvDetL3.getFINVDETL3_REF_NO());
                values.put(DatabaseHelper.FINVDETL3_SEQ_NO, finvDetL3.getFINVDETL3_SEQ_NO());
                values.put(DatabaseHelper.FINVDETL3_TAX_AMT, finvDetL3.getFINVDETL3_TAX_AMT());
                values.put(DatabaseHelper.FINVDETL3_TAX_COM_CODE, finvDetL3.getFINVDETL3_TAX_COM_CODE());
                values.put(DatabaseHelper.FINVDETL3_TXN_DATE, finvDetL3.getFINVDETL3_TXN_DATE());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FINVDETL3, values, DatabaseHelper.FINVDETL3_REF_NO + "=? AND " + DatabaseHelper.FINVDETL3_ITEM_CODE + "=?", new String[]{finvDetL3.getFINVDETL3_REF_NO().toString(), finvDetL3.getFINVDETL3_ITEM_CODE().toString()});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FINVDETL3, null, values);
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
            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FINVDETL3, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FINVDETL3, null, null);
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
