package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.FItenrHed;

import java.util.ArrayList;

public class FItenrHedDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FItenrHedDS";

    public FItenrHedDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }


    public int createOrUpdateFItenrHed(ArrayList<FItenrHed> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            Cursor mCursor = dB.rawQuery("SELECT Count(*) FROM " + DatabaseHelper.TABLE_FITENRHED, null);
            /* if Primary sync */
            if (mCursor.getCount() <= 0) {
                for (FItenrHed fItenrHed : list) {

                    ContentValues values = new ContentValues();

                    values.put(DatabaseHelper.FITENRHED_COST_CODE, fItenrHed.getFITENRHED_COST_CODE());
                    values.put(DatabaseHelper.FITENRHED_DEAL_CODE, fItenrHed.getFITENRHED_DEAL_CODE());
                    values.put(DatabaseHelper.FITENRHED_MONTH, fItenrHed.getFITENRHED_MONTH());
                    values.put(DatabaseHelper.FITENRHED_REF_NO, fItenrHed.getFITENRHED_REF_NO());
                    values.put(DatabaseHelper.FITENRHED_REMARKS1, fItenrHed.getFITENRHED_REMARKS1());
                    values.put(DatabaseHelper.FITENRHED_REP_CODE, fItenrHed.getFITENRHED_REP_CODE());
                    values.put(DatabaseHelper.FITENRHED_YEAR, fItenrHed.getFITENRHED_YEAR());

                    count = (int) dB.insert(DatabaseHelper.TABLE_FITENRHED, null, values);
                    Log.v("FITENRHED : ***", " Record Inserted " + count);

                }
				/*2RY Sync*/
            } else {

                for (FItenrHed fItenrHed : list) {

                    cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FITENRHED + " WHERE " + DatabaseHelper.FITENRHED_REF_NO + "='" + fItenrHed.getFITENRHED_REF_NO() + "'", null);

                    ContentValues values = new ContentValues();

                    values.put(DatabaseHelper.FITENRHED_COST_CODE, fItenrHed.getFITENRHED_COST_CODE());
                    values.put(DatabaseHelper.FITENRHED_DEAL_CODE, fItenrHed.getFITENRHED_DEAL_CODE());
                    values.put(DatabaseHelper.FITENRHED_MONTH, fItenrHed.getFITENRHED_MONTH());
                    values.put(DatabaseHelper.FITENRHED_REF_NO, fItenrHed.getFITENRHED_REF_NO());
                    values.put(DatabaseHelper.FITENRHED_REMARKS1, fItenrHed.getFITENRHED_REMARKS1());
                    values.put(DatabaseHelper.FITENRHED_REP_CODE, fItenrHed.getFITENRHED_REP_CODE());
                    values.put(DatabaseHelper.FITENRHED_YEAR, fItenrHed.getFITENRHED_YEAR());

                    if (cursor.getCount() > 0) {
                        dB.update(DatabaseHelper.TABLE_FITENRHED, values, DatabaseHelper.FITENRHED_REF_NO + "=?", new String[]{fItenrHed.getFITENRHED_REF_NO().toString()});
                        Log.v("2RY FITENRHED : ***", "Updated");
                    } else {
                        count = (int) dB.insert(DatabaseHelper.TABLE_FITENRHED, null, values);
                        Log.v("2RY FITENRHED : ***", "Inserted");
                    }

                }

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

    public int deleteAll() {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FITENRHED, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FITENRHED, null, null);
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
