package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.lankahardwared.lankahw.model.SizeComb;

import java.util.ArrayList;

public class SizeCombDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "SizeCombDS";

    public SizeCombDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateSizeCombDS(ArrayList<SizeComb> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (SizeComb sizecomb : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FSIZECOMB + " WHERE " + DatabaseHelper.FSIZECOMB_GROUPCODE + " ='" + sizecomb.getGROUPCODE() + "' AND " + DatabaseHelper.FSIZECOMB_ITEMCODE + " ='" + sizecomb.getITEMCODE() + "' AND " + DatabaseHelper.FSIZECOMB_SIZECODE + " ='" + sizecomb.getSIZECODE() + "'", null);

                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.FSIZECOMB_DEG_PRICE, sizecomb.getDPRICE());
                values.put(DatabaseHelper.FSIZECOMB_GROUPCODE, sizecomb.getGROUPCODE());
                values.put(DatabaseHelper.FSIZECOMB_ITEMCODE, sizecomb.getITEMCODE());
                values.put(DatabaseHelper.FSIZECOMB_OBS_PRICE, sizecomb.getOBSPRICE());
                values.put(DatabaseHelper.FSIZECOMB_PRICE, sizecomb.getPRICE());
                values.put(DatabaseHelper.FSIZECOMB_SIZECODE, sizecomb.getSIZECODE());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FSIZECOMB, values, DatabaseHelper.FSIZECOMB_GROUPCODE + " =? AND " + DatabaseHelper.FSIZECOMB_ITEMCODE + " =? AND " + DatabaseHelper.FSIZECOMB_SIZECODE, new String[]{String.valueOf(sizecomb.getGROUPCODE()), String.valueOf(sizecomb.getITEMCODE()), String.valueOf(sizecomb.getSIZECODE())});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FSIZECOMB, null, values);
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void InsertOrReplace(ArrayList<SizeComb> list) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + DatabaseHelper.TABLE_FSIZECOMB + " (DPrice,GroupCode,ItemCode,ObsPrice,Price,SizeCode) VALUES (?,?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (SizeComb sizeComb : list) {

                stmt.bindString(1, sizeComb.getDPRICE());
                stmt.bindString(2, sizeComb.getGROUPCODE());
                stmt.bindString(3, sizeComb.getITEMCODE());
                stmt.bindString(4, sizeComb.getOBSPRICE());
                stmt.bindString(5, sizeComb.getPRICE());
                stmt.bindString(6, sizeComb.getSIZECODE());

                stmt.execute();
                stmt.clearBindings();
            }

        } catch (SQLException e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.setTransactionSuccessful();
            dB.endTransaction();
            dB.close();
        }

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getPriceBySizeCode(String itemCode, String sizeCode, String groupCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor1 = null;
        try {
            String selectQuery = "SELECT " + DatabaseHelper.FSIZECOMB_PRICE + " FROM " + DatabaseHelper.TABLE_FSIZECOMB + " WHERE " + DatabaseHelper.FSIZECOMB_ITEMCODE + "='" + itemCode + "' AND " + DatabaseHelper.FSIZECOMB_GROUPCODE + "='" + groupCode + "' AND " + DatabaseHelper.FSIZECOMB_SIZECODE + "='" + sizeCode + "'";
            cursor1 = dB.rawQuery(selectQuery, null);

            while (cursor1.moveToNext()) {
                return cursor1.getString(cursor1.getColumnIndex(DatabaseHelper.FSIZECOMB_PRICE));
            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            cursor1.close();
            dB.close();
        }

        return "";
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

}
