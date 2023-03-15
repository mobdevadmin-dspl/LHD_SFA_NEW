package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.Reference;

import java.util.ArrayList;
import java.util.Calendar;

public class ReferenceDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "ReferenceDS ";

    public ReferenceDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

	
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getNextNumVal(String cSettingsCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String nextNumVal = "";

        Calendar c = Calendar.getInstance();

        try {
            String query = "SELECT " + DatabaseHelper.FCOMPANYBRANCH_NNUM_VAL + " FROM " + DatabaseHelper.TABLE_FCOMPANYBRANCH + " WHERE " + DatabaseHelper.FCOMPANYBRANCH_BRANCH_CODE + " in (SELECT " + DatabaseHelper.FSALREP_REPCODE + " FROM " + DatabaseHelper.TABLE_FSALREP + ")  AND cSettingsCode='" + cSettingsCode + "' AND nYear='" + String.valueOf(c.get(Calendar.YEAR)) + "' AND nMonth='" + String.valueOf(c.get(Calendar.MONTH) + 1) + "'";
            Cursor cursor = dB.rawQuery(query, null);
            int count = cursor.getCount();
            if (count > 0) {
                while (cursor.moveToNext()) {
                    nextNumVal = cursor.getString(0);
                }
            } else {
                nextNumVal = "1";
            }
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        } finally {
            dB.close();
        }

        return nextNumVal;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<Reference> getCurrentPreFix(String cSettingsCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Reference> list = new ArrayList<Reference>();

        try {
            String selectRep = "select c.cCharVal, s.Prefix from fcompanySetting c, fsalRep s where c.cSettingscode='" + cSettingsCode + "'";

            Cursor cursor = null;
            cursor = dB.rawQuery(selectRep, null);

            while (cursor.moveToNext()) {

                Reference reference = new Reference();

                reference.setCharVal(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FCOMPANYSETTING_CHAR_VAL)));
                reference.setRepPrefix(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSALREP_PREFIX)));
                list.add(reference);

            }
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        } finally {
            dB.close();
        }
        return list;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int getCount(String cSettingsCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int count = 0;

        try {
            count = 0;

            String query = "SELECT " + DatabaseHelper.FCOMPANYBRANCH_NNUM_VAL + " FROM " + DatabaseHelper.TABLE_FCOMPANYBRANCH + " WHERE " + DatabaseHelper.FCOMPANYBRANCH_BRANCH_CODE + " in (SELECT " + DatabaseHelper.FSALREP_REPCODE + " FROM " + DatabaseHelper.TABLE_FSALREP + ") AND " + DatabaseHelper.FCOMPANYBRANCH_CSETTINGS_CODE + "='" + cSettingsCode + "'";
            Cursor cursor = dB.rawQuery(query, null);
            count = cursor.getCount();

        } catch (Exception e) {
            Log.v(TAG, e.toString());
        } finally {
            dB.close();
        }

        return count;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int InsetOrUpdate(String code, int nextNumVal) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            Calendar c = Calendar.getInstance();

            SalRepDS repDS = new SalRepDS(context);

            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.FCOMPANYBRANCH_NNUM_VAL, String.valueOf(nextNumVal));

            String query = "SELECT " + DatabaseHelper.FCOMPANYBRANCH_NNUM_VAL + " FROM " + DatabaseHelper.TABLE_FCOMPANYBRANCH + " WHERE " + DatabaseHelper.FCOMPANYBRANCH_BRANCH_CODE + "='" + repDS.getCurrentRepCode() + "' AND " + DatabaseHelper.FCOMPANYBRANCH_CSETTINGS_CODE + "='" + code + "' AND nYear='" + String.valueOf(c.get(Calendar.YEAR)) + "' AND nMonth='" + String.valueOf(c.get(Calendar.MONTH) + 1) + "'";
            Cursor cursor = dB.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                count = (int) dB.update(DatabaseHelper.TABLE_FCOMPANYBRANCH, values, DatabaseHelper.FCOMPANYBRANCH_BRANCH_CODE + "='" + repDS.getCurrentRepCode() + "' AND " + DatabaseHelper.FCOMPANYBRANCH_CSETTINGS_CODE + "='" + code + "' AND nYear='" + String.valueOf(c.get(Calendar.YEAR)) + "' AND nMonth='" + String.valueOf(c.get(Calendar.MONTH) + 1) + "'", null);
            } else {

                values.put(DatabaseHelper.FCOMPANYBRANCH_BRANCH_CODE, repDS.getCurrentRepCode());
                values.put(DatabaseHelper.FCOMPANYBRANCH_RECORD_ID, "");
                values.put(DatabaseHelper.FCOMPANYBRANCH_CSETTINGS_CODE, code);
                values.put(DatabaseHelper.FCOMPANYBRANCH_YEAR, String.valueOf(c.get(Calendar.YEAR)));
                values.put(DatabaseHelper.FCOMPANYBRANCH_MONTH, String.valueOf(c.get(Calendar.MONTH) + 1));

                count = (int) dB.insert(DatabaseHelper.TABLE_FCOMPANYBRANCH, null, values);
            }

        } catch (Exception e) {
            Log.v(TAG, e.toString());
        } finally {
            dB.close();
        }
        return count;

    }

}
