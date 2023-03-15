package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.FDayExpHed;
import com.lankahardwared.lankahw.model.SalRep;
import com.lankahardwared.lankahw.model.mapper.ExpenseMapper;

import java.util.ArrayList;

public class FDayExpHedDS {
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "Ebony";

    public FDayExpHedDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

	/*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*/

    public int createOrUpdateNonPrdHed(ArrayList<FDayExpHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (FDayExpHed exphed : list) {
                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FDAYEXPHED_REFNO, exphed.getEXPHED_REFNO());
                values.put(DatabaseHelper.FDAYEXPHED_TXNDATE, exphed.getEXPHED_TXNDATE());
                values.put(DatabaseHelper.FDAYEXPHED_DEALCODE, "");
                values.put(DatabaseHelper.FDAYEXPHED_REPCODE, exphed.getEXPHED_REPCODE());
                values.put(DatabaseHelper.FDAYEXPHED_REMARKS, exphed.getEXPHED_REMARK());
                values.put(DatabaseHelper.FDAYEXPHED_COSTCODE, "");
                values.put(DatabaseHelper.FDAYEXPHED_ADDUSER, exphed.getEXPHED_ADDUSER());
                values.put(DatabaseHelper.FDAYEXPHED_ADDDATE, exphed.getEXPHED_ADDDATE());
                values.put(DatabaseHelper.FDAYEXPHED_ADDMATCH, exphed.getEXPHED_ADDMACH());
                values.put(DatabaseHelper.FDAYEXPHED_ISSYNC, exphed.getEXPHED_IS_SYNCED());
                values.put(DatabaseHelper.FDAYEXPHED_TOTAMT, exphed.getEXPHED_TOTAMT());
                values.put(DatabaseHelper.FDAYEXPHED_ACTIVESTATE, exphed.getEXPHED_ACTIVESTATE());
                values.put(DatabaseHelper.FDAYEXPHED_LATITUDE, exphed.getEXPHED_LATITUDE());
                values.put(DatabaseHelper.FDAYEXPHED_LONGITUDE, exphed.getEXPHED_LONGITUDE());
                values.put(DatabaseHelper.FDAYEXPHED_ADDRESS, exphed.getEXPHED_ADDRESS());
                values.put(DatabaseHelper.FDAYEXPHED_AREACODE, "");

                ArrayList<SalRep> replist = new SalRepDS(context).getSaleRepDetails();

                values.put(DatabaseHelper.FDAYEXPHED_REPNAME, replist.get(0).getNAME());

                count = (int) dB.insert(DatabaseHelper.TABLE_FDAYEXPHED, null, values);

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

	/*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*/

    public ArrayList<FDayExpHed> getAllExpHedDetails(String newTExt) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FDayExpHed> list = new ArrayList<FDayExpHed>();
        String selectQuery = "select * from " + DatabaseHelper.TABLE_FDAYEXPHED + " WHERE TxnDate LIKE '%" + newTExt + "%'";
        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            FDayExpHed fdayexpset = new FDayExpHed();
            fdayexpset.setEXPHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_REFNO)));
            fdayexpset.setEXPHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_TXNDATE)));
            fdayexpset.setEXPHED_TOTAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_TOTAMT)));
            list.add(fdayexpset);
        }

        return list;
    }

	/*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*/

    public int undoExpHedByID(String RefNo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FDAYEXPHED + " WHERE " + DatabaseHelper.FDAYEXPHED_REFNO + "='" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FDAYEXPHED, DatabaseHelper.FDAYEXPHED_REFNO + "='" + RefNo + "'", null);

            }
        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return count;

    }

	/*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*/

    public ArrayList<ExpenseMapper> getUnSyncedData() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<ExpenseMapper> list = new ArrayList<ExpenseMapper>();

        try {

            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FDAYEXPHED + " WHERE " + DatabaseHelper.FDAYEXPHED_ACTIVESTATE + "='0' AND " + DatabaseHelper.FDAYEXPHED_ISSYNC + "='0'";
            Cursor cursor = dB.rawQuery(selectQuery, null);
            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

            while (cursor.moveToNext()) {

                ExpenseMapper mapper = new ExpenseMapper();
                mapper.setNextNumVal(new CompanyBranchDS(context).getCurrentNextNumVal(context.getResources().getString(R.string.ExpenseNumVal)));
                mapper.setConsoleDB(localSP.getString("Console_DB", "").toString());

                mapper.setEXP_ACTIVESTATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ACTIVESTATE)));
                mapper.setEXP_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ADDDATE)));
                mapper.setEXP_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ADDMATCH)));
                mapper.setEXP_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ADDRESS)));
                mapper.setEXP_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ADDUSER)));
                // mapper.setEXP_AREACODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_AREACODE)));
                // mapper.setEXP_DEALCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_DEALCODE)));
                mapper.setEXP_COSTCODE("000");
                mapper.setEXP_IS_SYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ISSYNC)));
                mapper.setEXP_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_LATITUDE)));
                mapper.setEXP_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_LONGITUDE)));
                mapper.setEXP_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_REFNO)));
                mapper.setEXP_REMARK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_REMARKS)));
                mapper.setEXP_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_REPCODE)));
                mapper.setEXP_REPNAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_REPNAME)));
                mapper.setEXP_TOTAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_TOTAMT)));
                mapper.setEXP_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_TXNDATE)));

                mapper.setExpnseDetList(new FDayExpDetDS(context).getAllExpDetails(mapper.getEXP_REFNO()));
                list.add(mapper);
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return list;

    }

	/*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*/

    public int restDataExp(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FDAYEXPHED + " WHERE " + DatabaseHelper.FDAYEXPHED_REFNO + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);
            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.delete(DatabaseHelper.TABLE_FDAYEXPHED, DatabaseHelper.FDAYEXPHED_REFNO + " ='" + refno + "'", null);
                Log.v("Success Stauts", count + "");
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

	/*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*/

    public int updateIsSynced(ExpenseMapper mapper) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FDAYEXPHED_ISSYNC, "1");
            if (mapper.isSynced()) {
                count = dB.update(DatabaseHelper.TABLE_FDAYEXPHED, values, DatabaseHelper.FDAYEXPHED_REFNO + " =?", new String[]{String.valueOf(mapper.getEXP_REFNO())});
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


    public boolean isEntrySynced(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("select issync from FDayExpHed where refno ='" + Refno + "'", null);

        while (cursor.moveToNext()) {

            String result = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ISSYNC));

            if (result.equals("1"))
                return true;

        }
        cursor.close();
        dB.close();
        return false;

    }

    public ArrayList<FDayExpHed> getAllUnsyncedExHed(String newText, String param) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        ArrayList<FDayExpHed> list = new ArrayList<FDayExpHed>();
        Cursor cursor = null;
        try {

            String selectQuery;

            if (param.equals("U"))
                selectQuery = "select * from FDayExpHed sa where sa.ActiveState='0' AND sa.ISsync ='1' and sa.RefNo  like '%" + newText + "%'";
            else
                selectQuery = "select * from FDayExpHed sa where sa.ActiveState='0' AND sa.ISsync ='0' and sa.RefNo  like '%" + newText + "%'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                FDayExpHed exHed = new FDayExpHed();

                exHed.setEXPHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_REFNO)));
                exHed.setEXPHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_TXNDATE)));
                exHed.setEXPHED_REPNAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_REPNAME)));
                exHed.setEXPHED_DEALCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_DEALCODE)));
                exHed.setEXPHED_COSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_COSTCODE)));
                exHed.setEXPHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_REPCODE)));
                exHed.setEXPHED_REMARK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_REMARKS)));
                exHed.setEXPHED_AREACODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_AREACODE)));
                exHed.setEXPHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ADDUSER)));
                exHed.setEXPHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ADDDATE)));
                exHed.setEXPHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ADDMATCH)));
                exHed.setEXPHED_TOTAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_TOTAMT)));
                exHed.setEXPHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ADDRESS)));
                list.add(exHed);

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




}
