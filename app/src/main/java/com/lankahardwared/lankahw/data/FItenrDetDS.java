package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.FItenrDet;

import java.util.ArrayList;

public class FItenrDetDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FItenrDetDS";

    public FItenrDetDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFItenrDet(ArrayList<FItenrDet> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (FItenrDet fItenrDet : list) {

                cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FITENRDET + " WHERE " + DatabaseHelper.FITENRDET_REF_NO + "='" + fItenrDet.getFITENRDET_REF_NO() + "' AND " + DatabaseHelper.FITENRDET_ROUTE_CODE + "='" + fItenrDet.getFITENRDET_ROUTE_CODE() + "' AND " + DatabaseHelper.FITENRDET_TXN_DATE + "='" + fItenrDet.getFITENRDET_TXN_DATE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(dbHelper.FITENRDET_NO_OUTLET, fItenrDet.getFITENRDET_NO_OUTLET());
                values.put(dbHelper.FITENRDET_NO_SHCUCAL, fItenrDet.getFITENRDET_NO_SHCUCAL());
                values.put(dbHelper.FITENRDET_RD_TARGET, fItenrDet.getFITENRDET_RD_TARGET());
                values.put(dbHelper.FITENRDET_REF_NO, fItenrDet.getFITENRDET_REF_NO());
                values.put(dbHelper.FITENRDET_REMARKS, fItenrDet.getFITENRDET_REMARKS());
                values.put(dbHelper.FITENRDET_ROUTE_CODE, fItenrDet.getFITENRDET_ROUTE_CODE());
                values.put(dbHelper.FITENRDET_TXN_DATE, fItenrDet.getFITENRDET_TXN_DATE());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FITENRDET, values, DatabaseHelper.FITENRDET_REF_NO + "=? AND " + DatabaseHelper.FITENRDET_ROUTE_CODE + "=? AND " + DatabaseHelper.FITENRDET_TXN_DATE + "=?", new String[]{fItenrDet.getFITENRDET_REF_NO().toString(), fItenrDet.getFITENRDET_ROUTE_CODE().toString(), fItenrDet.getFITENRDET_TXN_DATE().toString()});
                    Log.v("FITENRDET : ", "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FITENRDET, null, values);
                    Log.v("FITENRDET : ", "Inserted " + count);
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
            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FITENRDET, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.TABLE_FITENRDET, null, null);
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
