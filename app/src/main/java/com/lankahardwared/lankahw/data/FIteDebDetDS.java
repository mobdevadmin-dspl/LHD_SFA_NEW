package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.FIteDebDet;

import java.util.ArrayList;

public class FIteDebDetDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FIteDebDetDS";

    public FIteDebDetDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFIteDebDet(ArrayList<FIteDebDet> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (FIteDebDet fIteDebDet : list) {

                cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FITEDEBDET + " WHERE " + DatabaseHelper.FITEDEBDET_REF_NO + "='" + fIteDebDet.getFITEDEBDET_REF_NO() + "' AND " + DatabaseHelper.FITEDEBDET_DEB_CODE + "='" + fIteDebDet.getFITEDEBDET_DEB_CODE() + "' AND " + DatabaseHelper.FITEDEBDET_ROUTE_CODE + "='" + fIteDebDet.getFITEDEBDET_ROUTE_CODE() + "' AND " + DatabaseHelper.FITEDEBDET_TXN_DATE + "='" + fIteDebDet.getFITEDEBDET_TXN_DATE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(dbHelper.FITEDEBDET_DEB_CODE, fIteDebDet.getFITEDEBDET_DEB_CODE());
                values.put(dbHelper.FITEDEBDET_REF_NO, fIteDebDet.getFITEDEBDET_REF_NO());
                values.put(dbHelper.FITEDEBDET_ROUTE_CODE, fIteDebDet.getFITEDEBDET_ROUTE_CODE());
                values.put(dbHelper.FITEDEBDET_TXN_DATE, fIteDebDet.getFITEDEBDET_TXN_DATE());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FITEDEBDET, values, DatabaseHelper.FITEDEBDET_REF_NO + "=? AND " + DatabaseHelper.FITEDEBDET_DEB_CODE + "=? AND " + DatabaseHelper.FITEDEBDET_ROUTE_CODE + "=? AND " + DatabaseHelper.FITEDEBDET_TXN_DATE, new String[]{fIteDebDet.getFITEDEBDET_REF_NO().toString(), fIteDebDet.getFITEDEBDET_DEB_CODE().toString(), fIteDebDet.getFITEDEBDET_ROUTE_CODE().toString(), fIteDebDet.getFITEDEBDET_TXN_DATE().toString()});
                    Log.v("FITEDEBDET : ***", "Updated");
                } else {
                    count = (int) dB.insert(dbHelper.TABLE_FITEDEBDET, null, values);
                    Log.v("FITEDEBDET : ***", "Inserted " + count);
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

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
            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FITEDEBDET, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.TABLE_FITEDEBDET, null, null);
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
