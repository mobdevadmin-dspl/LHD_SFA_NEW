package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.Merch;

import java.util.ArrayList;

public class MerchDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";


    public MerchDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFMerch(ArrayList<Merch> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (Merch merch : list) {

                ContentValues values = new ContentValues();
                values.put(dbHelper.FMERCH_CODE, merch.getFMERCH_CODE());
                values.put(dbHelper.FMERCH_NAME, merch.getFMERCH_NAME());
                values.put(dbHelper.FMERCH_ADD_USER, merch.getFMERCH_ADD_USER());
                values.put(dbHelper.FMERCH_ADD_DATE, merch.getFMERCH_ADD_DATE());
                values.put(dbHelper.FMERCH_ADD_MACH, merch.getFMERCH_ADD_MACH());
                values.put(dbHelper.FMERCH_TIMESTAMP_COLUMN, merch.getFMERCH_TIMESTAMP_COLUMN());

                count = (int) dB.insert(dbHelper.TABLE_FMERCH, null, values);

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;

    }

    @SuppressWarnings("static-access")
    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FMERCH, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.TABLE_FMERCH, null, null);
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
