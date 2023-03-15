package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.FItTourDisc;

import java.util.ArrayList;

public class FItTourDiscDs {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "LHD";

    public FItTourDiscDs(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFitTourDisc(ArrayList<FItTourDisc> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        Cursor cursor_ini = null;

        try {

            cursor_ini = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FITTOURDISC, null);

            for (FItTourDisc fItTourDisc : list) {
                ContentValues values = new ContentValues();

                values.put(dbHelper.FITTOURDISC_PRILCODE, fItTourDisc.getFITTOURDISC_PRILCODE());
                values.put(dbHelper.FITTOURDISC_ITEMCODE, fItTourDisc.getFITTOURDISC_ITEMCODE());
                values.put(dbHelper.FITTOURDISC_QTYFROM, fItTourDisc.getFITTOURDISC_QTYFROM());
                values.put(dbHelper.FITTOURDISC_QTYTO, fItTourDisc.getFITTOURDISC_QTYTO());
                values.put(dbHelper.FITTOURDISC_PRILDISC, fItTourDisc.getFITTOURDISC_PRILDISC());
                values.put(dbHelper.FITTOURDISC_RECORDID, fItTourDisc.getFITTOURDISC_RECORDID());

                if (cursor_ini.getCount() > 0) {
                    String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FITTOURDISC + " WHERE " + dbHelper.FITTOURDISC_PRILCODE + "='" + fItTourDisc.getFITTOURDISC_PRILCODE() + "'";
                    cursor = dB.rawQuery(selectQuery, null);

                    if (cursor.getCount() > 0) {
                        count = (int) dB.update(dbHelper.TABLE_FITTOURDISC, values, dbHelper.FCOMPANYSETTING_SETTINGS_CODE + "='" + fItTourDisc.getFITTOURDISC_PRILCODE() + "'", null);
                    } else {
                        count = (int) dB.insert(dbHelper.TABLE_FITTOURDISC, null, values);
                    }

                } else {
                    count = (int) dB.insert(dbHelper.TABLE_FITTOURDISC, null, values);
                }

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (cursor_ini != null) {
                cursor_ini.close();
            }
            dB.close();
        }
        return count;
    }

    public ArrayList<FItTourDisc> getQtyBulkPrice(String _itemCode, String _prillcode ) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FItTourDisc> list = new ArrayList<FItTourDisc>();
        String selectQuery;
        selectQuery = "SELECT ftd.qtyFrom, ftd.qtyto, ftd.prilDisc, ftd.recordId FROM fItTourDisc ftd WHERE ftd.itemCode='" + _itemCode + "' AND ftd.prilCode='" + _prillcode + "'";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                FItTourDisc fItTourDisc = new FItTourDisc();
                fItTourDisc.setFITTOURDISC_QTYTO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITTOURDISC_QTYTO)));
                fItTourDisc.setFITTOURDISC_QTYFROM(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITTOURDISC_QTYFROM)));
                fItTourDisc.setFITTOURDISC_PRILDISC(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITTOURDISC_PRILDISC)));
                fItTourDisc.setFITTOURDISC_RECORDID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITTOURDISC_RECORDID)));

                list.add(fItTourDisc);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return list;
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

            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FITTOURDISC, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.TABLE_FITTOURDISC, null, null);
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
