package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.TERMS;

import java.util.ArrayList;

public class TermDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbeHelper;
    private String TAG = "CrCombDS";

    public TermDS(Context context) {
        this.context = context;
        dbeHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbeHelper.getWritableDatabase();
    }

    public int createOrUpdateTerms(ArrayList<TERMS> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (TERMS term : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FTERMS + " WHERE " + DatabaseHelper.FTERMS_CODE + " ='" + term.getTERMCODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FTERMS_ID, term.getID());
                values.put(DatabaseHelper.FTERMS_CODE, term.getTERMCODE());
                values.put(DatabaseHelper.FTERMS_DES, term.getTERMDES());
                values.put(DatabaseHelper.FTERMS_DISPER, term.getTERMDISPER());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FTERMS, values, DatabaseHelper.FTERMS_CODE + " =?", new String[]{String.valueOf(term.getTERMCODE())});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FTERMS, null, values);
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-**-*-*-*-*-**-*-*/

    public String getTermDetails(String termCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FTERMS + " WHERE " + DatabaseHelper.FTERMS_CODE + " ='" + termCode + "'", null);

            while (cursor.moveToNext()) {
                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTERMS_DES));
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return "None";

    }

}
