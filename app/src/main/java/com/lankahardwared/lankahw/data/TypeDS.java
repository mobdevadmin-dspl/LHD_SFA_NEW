package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.Type;

import java.util.ArrayList;

public class TypeDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "TypeDS ";

    public TypeDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateType(ArrayList<Type> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (Type type : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FTYPE + " WHERE " + DatabaseHelper.FTYPE_CODE + "='" + type.getFTYPE_CODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FTYPE_ADD_DATE, type.getFTYPE_ADD_DATE());
                values.put(DatabaseHelper.FTYPE_ADD_MACH, type.getFTYPE_ADD_MACH());
                values.put(DatabaseHelper.FTYPE_ADD_USER, type.getFTYPE_ADD_USER());
                values.put(DatabaseHelper.FTYPE_RECORDID, type.getFTYPE_RECORDID());
                values.put(DatabaseHelper.FTYPE_CODE, type.getFTYPE_CODE());
                values.put(DatabaseHelper.FTYPE_NAME, type.getFTYPE_NAME());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FTYPE, values, DatabaseHelper.FTYPE_CODE + "=?", new String[]{type.getFTYPE_CODE().toString()});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FTYPE, null, values);
                    Log.v(TAG, "Inserted " + count);
                }

                cursor.close();
            }
        } catch (Exception e) {

            Log.v("Exception", e.toString());

        } finally {
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

            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FTYPE, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FTYPE, null, null);
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
