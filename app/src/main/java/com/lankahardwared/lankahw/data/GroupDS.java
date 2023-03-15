package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.Group;

import java.util.ArrayList;

public class GroupDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "GroupDS ";

    public GroupDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }


    @SuppressWarnings("static-access")
    public int createOrUpdateGroup(ArrayList<Group> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (Group group : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FGROUP + " WHERE " + DatabaseHelper.FGROUP_CODE + "='" + group.getFGROUP_CODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(dbHelper.FGROUP_ADD_DATE, group.getFGROUP_ADD_DATE());
                values.put(dbHelper.FGROUP_ADD_MACH, group.getFGROUP_ADD_MACH());
                values.put(dbHelper.FGROUP_ADD_USER, group.getFGROUP_ADD_USER());
                values.put(dbHelper.FGROUP_CODE, group.getFGROUP_CODE());
                values.put(dbHelper.FGROUP_NAME, group.getFGROUP_NAME());
                values.put(dbHelper.FGROUP_RECORDID, group.getFGROUP_RECORDID());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FGROUP, values, DatabaseHelper.FGROUP_CODE + "=?", new String[]{group.getFGROUP_CODE().toString()});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FGROUP, null, values);
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*/

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
            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FGROUP, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.TABLE_FGROUP, null, null);
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
