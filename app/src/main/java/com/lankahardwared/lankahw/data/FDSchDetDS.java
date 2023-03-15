package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.fDSchDet;

import java.util.ArrayList;

public class FDSchDetDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FDSchDetDS ";

    public FDSchDetDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateFDSchDetDS(ArrayList<fDSchDet> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (fDSchDet sFDSchDet : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FDSCHDET + " WHERE " + DatabaseHelper.FDSCHDET_REFNO + " ='" + sFDSchDet.getFDSCHDET_REFNO() + "' AND " + DatabaseHelper.FDSCHDET_GROUPCODE + " ='" + sFDSchDet.getFDSCHDET_GROUPCODE() + "'", null);

                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.FDSCHDET_CADISPER, sFDSchDet.getFDSCHDET_CADISPER());
                values.put(DatabaseHelper.FDSCHDET_CAISSPER, sFDSchDet.getFDSCHDET_CAISSPER());
                values.put(DatabaseHelper.FDSCHDET_CRDISPER, sFDSchDet.getFDSCHDET_CRDISPER());
                values.put(DatabaseHelper.FDSCHDET_CRFISSPER, sFDSchDet.getFDSCHDET_CRFISSPER());
                values.put(DatabaseHelper.FDSCHDET_GROUPCODE, sFDSchDet.getFDSCHDET_GROUPCODE());
                values.put(DatabaseHelper.FDSCHDET_REFNO, sFDSchDet.getFDSCHDET_REFNO());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FDSCHDET, values, DatabaseHelper.FDSCHDET_GROUPCODE + " =? AND " + DatabaseHelper.FDSCHDET_REFNO + " =?", new String[]{String.valueOf(sFDSchDet.getFDSCHDET_GROUPCODE()), String.valueOf(sFDSchDet.getFDSCHDET_REFNO())});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FDSCHDET, null, values);
                    Log.v(TAG, "Inserted " + count);
                }
                cursor.close();
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
            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FDSCHDET, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FDSCHDET, null, null);
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

	/*-*-*-*-*-*-*-*-*-*-*Get discount scheme info-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public fDSchDet getSchemeInfoByGroupCode(String GroupCode, String DebCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT a.*,b.* from fdschhed a, fdschdet b WHERE a.RefNo=b.RefNo AND b.GroupCode='" + GroupCode + "' AND a.DebCode='" + DebCode + "' AND date('now') BETWEEN a.FromDate AND a.ToDate";

        fDSchDet mfDSchDet = new fDSchDet();
        Cursor cursor = null;

        try {
            cursor = dB.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    mfDSchDet.setFDSCHDET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDSCHDET_REFNO)));
                    mfDSchDet.setFDSCHDET_GROUPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDSCHDET_GROUPCODE)));
                    mfDSchDet.setFDSCHDET_CRFISSPER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDSCHDET_CRFISSPER)));
                    mfDSchDet.setFDSCHDET_CRDISPER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDSCHDET_CRDISPER)));
                    mfDSchDet.setFDSCHDET_CAISSPER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDSCHDET_CAISSPER)));
                    mfDSchDet.setFDSCHDET_CADISPER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDSCHDET_CADISPER)));

                }
                return mfDSchDet;
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return null;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

}
