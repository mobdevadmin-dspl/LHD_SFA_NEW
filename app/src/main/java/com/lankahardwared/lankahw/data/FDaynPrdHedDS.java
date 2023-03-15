package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.model.FDaynPrdHed;
import com.lankahardwared.lankahw.model.mapper.NonProdMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FDaynPrdHedDS {
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "NONPROD DS";

    public FDaynPrdHedDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateNonPrdHed(ArrayList<FDaynPrdHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (FDaynPrdHed nonhed : list) {
                ContentValues values = new ContentValues();

                values.put(dbHelper.NONPRDHED_REFNO, nonhed.getNONPRDHED_REFNO());
                values.put(dbHelper.NONPRDHED_TXNDAET, nonhed.getNONPRDHED_TXNDATE());
                values.put(dbHelper.NONPRDHED_DEALCODE, nonhed.getNONPRDHED_DEALCODE());
                values.put(dbHelper.NONPRDHED_REPCODE, nonhed.getNONPRDHED_REPCODE());
                values.put(dbHelper.NONPRDHED_REMARK, nonhed.getNONPRDHED_REMARKS());
                values.put(dbHelper.NONPRDHED_COSTCODE, nonhed.getNONPRDHED_COSTCODE());
                values.put(dbHelper.NONPRDHED_ADDUSER, nonhed.getNONPRDHED_ADDUSER());
                values.put(dbHelper.NONPRDHED_ADDDATE, nonhed.getNONPRDHED_ADDDATE());
                values.put(dbHelper.NONPRDHED_ADDMACH, nonhed.getNONPRDHED_ADDMACH());
                values.put(dbHelper.NONPRDHED_TRANSBATCH, nonhed.getNONPRDHED_TRANSBATCH());
                values.put(dbHelper.NONPRDHED_IS_SYNCED, nonhed.getNONPRDHED_IS_SYNCED());
                values.put(dbHelper.NONPRDHED_ADDRESS, nonhed.getNONPRDHED_ADDRESS());
                values.put(dbHelper.NONPRDHED_LONGITUDE,nonhed.getNONPRDHED_LONGITUDE());
                values.put(dbHelper.NONPRDHED_LATITUDE,nonhed.getNONPRDHED_LATITUDE());
                values.put(dbHelper.NONPRDHED_DEBCODE,nonhed.getNONPRDHED_DEBCODE());
                values.put(dbHelper.NONPRDHED_IS_ACTIVE,nonhed.getNONPRDHED_IS_ACTIVE());

                count = (int) dB.insert(dbHelper.TABLE_NONPRDHED, null, values);

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
    public boolean validateActiveNonPrd()
    {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        boolean res = false;

        String toDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_NONPRDHED + " WHERE " + DatabaseHelper.NONPRDHED_IS_ACTIVE+ "='1'";
            cursor = dB.rawQuery(selectQuery, null);

            /*Active invoice available*/
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                String txndate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_TXNDAET));

                /*txndate is equal to current date*/
                if (txndate.equals(toDay))
                    res = true;
                /*if invoice is older, then reset data*/
                else {
                    String Refno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO));
                    restData(Refno);
//                    new InvDetDS(context).restData(Refno);
//                    new OrderDiscDS(context).clearData(Refno);
//                    new OrdFreeIssueDS(context).ClearFreeIssues(Refno);
                    UtilityContainer.ClearNonSharedPref(context);
                }

            } else
                res = false;

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return res;

    }

	/*-*-*-*--*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*--*-*-*-*-*-*-*/

    /**
     * -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
     * *-*-*-*-*-*-*-*-*-*-*-*-
     */

    public boolean restData(String refno) {

        boolean Result = false;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_NONPRDHED + " WHERE " + DatabaseHelper.NONPRDHED_REFNO + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                String status = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_IS_SYNCED));
                /* if order already synced, can't delete */
                if (status.equals("1"))
                    Result = false;
                else {
                    int success = dB.delete(DatabaseHelper.TABLE_NONPRDHED, DatabaseHelper.NONPRDHED_REFNO + " ='" + refno + "'", null);
                    Log.v("Success", success + "");
                    Result = true;
                }
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return Result;

    }
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*/

    public ArrayList<FDaynPrdHed> getAllnonprdHedDetails(String newText) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FDaynPrdHed> list = new ArrayList<FDaynPrdHed>();

        String selectQuery = "select * from " + dbHelper.TABLE_NONPRDHED + " WHERE AddDate LIKE '%" + newText + "%'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            FDaynPrdHed fnonset = new FDaynPrdHed();
            fnonset.setNONPRDHED_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.NONPRDHED_REFNO)));
            fnonset.setNONPRDHED_ADDDATE(cursor.getString(cursor.getColumnIndex(dbHelper.NONPRDHED_ADDDATE)));

            list.add(fnonset);

        }

        return list;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*/

    @SuppressWarnings("static-access")
    public int undoOrdHedByID(String RefNo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_NONPRDHED + " WHERE " + dbHelper.NONPRDHED_REFNO + "='" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.TABLE_NONPRDHED, dbHelper.NONPRDHED_REFNO + "='" + RefNo + "'", null);

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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*/

    public ArrayList<NonProdMapper> getUnSyncedData() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<NonProdMapper> list = new ArrayList<NonProdMapper>();

        try {

            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_NONPRDHED + " WHERE " + DatabaseHelper.NONPRDHED_IS_SYNCED + "='0'";
            Cursor cursor = dB.rawQuery(selectQuery, null);
//            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

            while (cursor.moveToNext()) {

                NonProdMapper mapper = new NonProdMapper();
                mapper.setNextNumVal(new CompanyBranchDS(context).getCurrentNextNumVal(context.getResources().getString(R.string.NonProd)));
                mapper.setConsoleDB(localSP.getString("Console_DB", "").toString());

                mapper.setNONPRDHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_ADDDATE)));
                mapper.setNONPRDHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_ADDMACH)));
                mapper.setNONPRDHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_ADDRESS)));
                mapper.setNONPRDHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_ADDUSER)));
                mapper.setNONPRDHED_COSTCODE("000");
                // mapper.setNONPRDHED_DEALCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_DEALCODE)));
                mapper.setNONPRDHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_IS_SYNCED)));
                mapper.setNONPRDHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_REFNO)));
                mapper.setNONPRDHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_REMARK)));
                mapper.setNONPRDHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_REPCODE)));
                mapper.setNONPRDHED_TRANSBATCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_TRANSBATCH)));
                mapper.setNONPRDHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_TXNDAET)));
                mapper.setNONPRDHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_DEBCODE)));
                mapper.setNONPRDHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_LONGITUDE)));
                mapper.setNONPRDHED_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_LATITUDE)));
                mapper.setNonPrdDet(new fDaynPrdDetDS(context).getAllnonprdDetails(mapper.getNONPRDHED_REFNO()));

                list.add(mapper);
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return list;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*/

    public int updateIsSynced(NonProdMapper mapper) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.NONPRDHED_IS_SYNCED, "1");
            if (mapper.isSynced()) {
                count = dB.update(DatabaseHelper.TABLE_NONPRDHED, values, DatabaseHelper.NONPRDHED_REFNO + " =?", new String[]{String.valueOf(mapper.getNONPRDHED_REFNO())});
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

        Cursor cursor = dB.rawQuery("select ISsync from FDaynPrdHed where refno ='" + Refno + "'", null);

        while (cursor.moveToNext()) {

            String result = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_IS_SYNCED));

            if (result.equals("1"))
                return true;

        }
        cursor.close();
        dB.close();
        return false;

    }

    public ArrayList<FDaynPrdHed> getAllActiveNonPHed() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FDaynPrdHed> list = new ArrayList<FDaynPrdHed>();

        @SuppressWarnings("static-access")
        String selectQuery = "select * from " + dbHelper.TABLE_NONPRDHED + " Where " + dbHelper.NONPRDHED_IS_ACTIVE
                + "='1' and " + dbHelper.NONPRDHED_IS_SYNCED + "='0'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            FDaynPrdHed nonPHed = new FDaynPrdHed();

            // invHed.setFINVHED_ID(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRHED_ID)));
            nonPHed.setNONPRDHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_REFNO)));
            nonPHed.setNONPRDHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_TXNDAET)));
            nonPHed.setNONPRDHED_DEALCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_DEALCODE)));
            nonPHed.setNONPRDHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_REPCODE)));
            nonPHed.setNONPRDHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_REMARK)));
            nonPHed.setNONPRDHED_COSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_COSTCODE)));
            nonPHed.setNONPRDHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_ADDUSER)));
            nonPHed.setNONPRDHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_ADDDATE)));
            nonPHed.setNONPRDHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_ADDMACH)));
            nonPHed.setNONPRDHED_TRANSBATCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_TRANSBATCH)));
            nonPHed.setNONPRDHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_ADDRESS)));
            nonPHed.setNONPRDHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_DEBCODE)));
            nonPHed.setNONPRDHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_LONGITUDE)));
            nonPHed.setNONPRDHED_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_LATITUDE)));

            list.add(nonPHed);
        }
        return list;
    }

    public boolean isAnyActiveNonPHed() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        boolean res = false;

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_NONPRDHED + " WHERE " + DatabaseHelper.NONPRDHED_IS_ACTIVE + "='1'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0)
                res = true;
            else
                res = false;

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return res;
    }

    public ArrayList<FDaynPrdHed> getAllUnsyncedNPHed(String newText, String param) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        ArrayList<FDaynPrdHed> list = new ArrayList<FDaynPrdHed>();
        Cursor cursor = null;
        try {

            String selectQuery;

            if (param.equals("U"))
                selectQuery = "select * from FDaynPrdHed sa, fdebtor cu where sa.isActive='0' AND sa.ISsync ='1' and sa.DebCode=cu.DebCode and sa.RefNo  || cu.DebName  like '%" + newText + "%'";
            else
                selectQuery = "select * from FDaynPrdHed sa, fdebtor cu where sa.isActive='0' AND sa.ISsync ='0' and sa.DebCode=cu.DebCode and sa.RefNo  || cu.DebName  like '%" + newText + "%'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                FDaynPrdHed nonPHed = new FDaynPrdHed();

                nonPHed.setNONPRDHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_REFNO)));
                nonPHed.setNONPRDHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_TXNDAET)));
                nonPHed.setNONPRDHED_DEALCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_DEALCODE)));
                nonPHed.setNONPRDHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_REPCODE)));
                nonPHed.setNONPRDHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_REMARK)));
                nonPHed.setNONPRDHED_COSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_COSTCODE)));
                nonPHed.setNONPRDHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_ADDUSER)));
                nonPHed.setNONPRDHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_ADDDATE)));
                nonPHed.setNONPRDHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_ADDMACH)));
                nonPHed.setNONPRDHED_TRANSBATCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_TRANSBATCH)));
                nonPHed.setNONPRDHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_ADDRESS)));
                nonPHed.setNONPRDHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_DEBCODE)));
                nonPHed.setNONPRDHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_LONGITUDE)));
                nonPHed.setNONPRDHED_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NONPRDHED_LATITUDE)));

                list.add(nonPHed);

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
