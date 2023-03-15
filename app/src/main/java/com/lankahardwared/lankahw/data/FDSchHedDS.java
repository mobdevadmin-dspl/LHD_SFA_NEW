package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.FDSchHed;

import java.util.ArrayList;

public class FDSchHedDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FDSchHedDS ";

    public FDSchHedDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateFDSchHedDS(ArrayList<FDSchHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (FDSchHed sFDSchHed : list) {

                ContentValues values = new ContentValues();

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FDSCHHED + " WHERE " + DatabaseHelper.FDSCHHED_REFNO + " = '" + sFDSchHed.getFDSCHHED_REFNO() + "'", null);

                values.put(DatabaseHelper.FDSCHHED_F_DATE, sFDSchHed.getFDSCHHED_F_DATE());
                values.put(DatabaseHelper.FDSCHHED_REFNO, sFDSchHed.getFDSCHHED_REFNO());
                values.put(DatabaseHelper.FDSCHHED_DEBCODE, sFDSchHed.getFDSCHHED_DEBCODE());
                values.put(DatabaseHelper.FDSCHHED_REMARKS, sFDSchHed.getFDSCHHED_REMARKS());
                values.put(DatabaseHelper.FDSCHHED_SCHSTATUS, sFDSchHed.getFDSCHHED_SCHSTATUS());
                values.put(DatabaseHelper.FDSCHHED_T_DATE, sFDSchHed.getFDSCHHED_T_DATE());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FDSCHHED, values, DatabaseHelper.FDSCHHED_REFNO + " =?", new String[]{String.valueOf(sFDSchHed.getFDSCHHED_REFNO())});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FDSCHHED, null, values);
                    Log.v(TAG, "Inserted " + count);
                }

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }
        return count;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int deleteAll() {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FDSCHHED, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FDSCHHED, null, null);
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