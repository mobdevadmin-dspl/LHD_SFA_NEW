package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.ApprOrdHed;
import com.lankahardwared.lankahw.model.TranSOHed;

import java.util.ArrayList;

public class ApprOrdHedDS {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "ApprOrdHed";

    public ApprOrdHedDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateApprOrdHedDS(ArrayList<ApprOrdHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (ApprOrdHed apprOrdHed : list) {

                String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FAPPRORDHED + " WHERE " + DatabaseHelper.FAPPRORDHED_REFNO + " = '" + apprOrdHed.getFAPPRORDHED_REFNO() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FAPPRORDHED_REFNO, apprOrdHed.getFAPPRORDHED_REFNO());
                values.put(DatabaseHelper.FAPPRORDHED_TOTALAMT, apprOrdHed.getFAPPRORDHED_TOTALAMT());
                values.put(DatabaseHelper.FAPPRORDHED_DEBCODE, apprOrdHed.getFAPPRORDHED_DEBCODE());

                int cn = cursor.getCount();
                if (cn > 0) {
                    count = dB.update(DatabaseHelper.TABLE_FAPPRORDHED, values, DatabaseHelper.FAPPRORDHED_REFNO + " =?", new String[]{String.valueOf(apprOrdHed.getFAPPRORDHED_REFNO())});
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FAPPRORDHED, null, values);
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

    public void deleteAllAppr() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        dB.delete(DatabaseHelper.TABLE_FAPPRORDHED, null, null);
        dB.close();
    }

    public boolean isApproved(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        boolean res = false;

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FAPPRORDHED + " where " + DatabaseHelper.FTRANSOHED_REFNO + " = '" + RefNo + "'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0)
                res = true;
            else
                res = false;

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return res;

    }
}
