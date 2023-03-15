package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.lankahardwared.lankahw.model.Achievement;
import com.lankahardwared.lankahw.model.Bank;

import java.util.ArrayList;


/*
****** Kaveesha - 30/07/2020
 */

public class AchievementDS {

    Context context;
   private DatabaseHelper dbHelper;
   private SQLiteDatabase dB;
   private  String TAG = "AchievementDs";

    public AchievementDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException
    {
        dB = dbHelper.getWritableDatabase();
    }

//    public void InsertOrReplaceAchievement(ArrayList<Achievement> list)
//    {
//        if(dB == null)
//        {
//            open();
//        }
//        else if(!dB.isOpen())
//        {
//            open();
//        }
//
//        try
//        {
//            dB.beginTransactionNonExclusive();
//
//            String sql = "INSERT OR REPLACE INTO " + DatabaseHelper.FACHIEVEMENT + " (CumInvCount,CumInvVal,DayInvCount,DayInvVal,RepCode,RepName) VALUES (?,?,?,?,?,?)";
//
//            SQLiteStatement stmt = dB.compileStatement(sql);
//
//            for(Achievement achieve : list)
//            {
//                stmt.bindDouble(1,achieve.getCumInvCount());
//                Log.d(">>acieve","A_count = "+achieve.getCumInvCount());
//                stmt.bindDouble(2,achieve.getCumInvVal());
//                stmt.bindDouble(3,achieve.getDayInvCount());
//                stmt.bindDouble(4,achieve.getDayInvVal());
//                stmt.bindString(5,achieve.getRepCode());
//                stmt.bindString(6,achieve.getRepName());
//
//                stmt.execute();
//                stmt.clearBindings();
//            }
//
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//        finally {
//           dB.setTransactionSuccessful();
//           dB.endTransaction();
//           dB.close();
//        }
//    }
public int createOrUpdateAcieve(ArrayList<Achievement> list) {

    int count = 0;

    if (dB == null) {
        open();
    } else if (!dB.isOpen()) {
        open();
    }

    try {

        for (Achievement achievement : list) {

            Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.FACHIEVEMENT + " WHERE " + DatabaseHelper.FACHIEVE_REPCODE + "='" + achievement.getRepCode() + "'", null);

            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.FACHIEVE_CUMINVCOUNT, achievement.getCumInvCount());
            values.put(DatabaseHelper.FACHIEVE_CUMINVVAL, achievement.getCumInvVal());
            values.put(DatabaseHelper.FACHIEVE_DAYINVCOUNT, achievement.getDayInvCount());
            values.put(DatabaseHelper.FACHIEVE_DAYINVVAL, achievement.getDayInvVal());
            values.put(DatabaseHelper.FACHIEVE_REPCODE, achievement.getRepCode());
            values.put(DatabaseHelper.FACHIEVE_REPNAME, achievement.getRepName());


            if (cursor.getCount() > 0) {
                dB.update(DatabaseHelper.FACHIEVEMENT, values, DatabaseHelper.FACHIEVE_REPCODE + "=?", new String[]{achievement.getRepCode().toString()});
                Log.v(TAG, "Updated");
            } else {
                count = (int) dB.insert(DatabaseHelper.FACHIEVEMENT, null, values);
                Log.v(TAG, "Inserted " + count);
            }
            cursor.close();
        }

    } catch (Exception e) {
        Log.v(TAG, e.toString());
    } finally {
        dB.close();
    }
    return count;

}

    public int deleteAll()
    {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try
        {
            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.FACHIEVEMENT,null);
            count = cursor.getCount();

            if(count > 0)
            {
                int success = dB.delete(DatabaseHelper.FACHIEVEMENT, null, null);
                Log.v("Success", success + "");
            }
        }
        catch (Exception e)
        {
            Log.v(TAG + " Exception", e.toString());
        }
        finally {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return count;
    }

}
