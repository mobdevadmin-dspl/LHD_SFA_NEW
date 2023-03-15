package com.lankahardwared.lankahw.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.lankahardwared.lankahw.model.TargetDet;

import java.util.ArrayList;

/*
*******  Kaveesha - 29/07/2020  ********
 */

public class TargetDetDS {

   Context context;
   private SQLiteDatabase dB;
   private DatabaseHelper dbHelper;
   private String TAG = "TargetDetDS";


    public TargetDetDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException
    {
        dB = dbHelper.getWritableDatabase();
    }


    public void InsertOrReplaceTarget(ArrayList<TargetDet> list) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + DatabaseHelper.FTARGETDET + " (Monthn,RefNo,TrAmt,YearT,txndate) VALUES (?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (TargetDet tarD : list) {

                stmt.bindString(1, tarD.getMonthn());
               // Log.d(">>tar","month"+tarD.getMonthn());
                stmt.bindString(2, tarD.getRefNo());
                stmt.bindString(3, tarD.getTrAmt());
                stmt.bindString(4, tarD.getYearT());
                stmt.bindString(5, tarD.getTxndate());


                stmt.execute();
                stmt.clearBindings();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dB.setTransactionSuccessful();
            dB.endTransaction();
            dB.close();
        }

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

            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.FTARGETDET, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.FTARGETDET, null, null);
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
