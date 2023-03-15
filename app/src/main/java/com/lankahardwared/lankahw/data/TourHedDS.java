package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.TourHed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TourHedDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "DebtorDS ";

    public TourHedDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateTourHed(ArrayList<TourHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (TourHed hed : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FTOURHED + " WHERE " + DatabaseHelper.TOURHED_REFNO + "='" + hed.getTOURHED_REFNO() + "'", null);
                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.TOURHED_ADDMACH, hed.getTOURHED_ADDMACH());
                values.put(DatabaseHelper.TOURHED_ADDUSER, hed.getTOURHED_ADDUSER());
                values.put(DatabaseHelper.TOURHED_AREACODE, hed.getTOURHED_AREACODE());
                values.put(DatabaseHelper.TOURHED_CLSFLG, hed.getTOURHED_CLSFLG());
                values.put(DatabaseHelper.TOURHED_COSTCODE, hed.getTOURHED_COSTCODE());
                values.put(DatabaseHelper.TOURHED_DRIVERCODE, hed.getTOURHED_DRIVERCODE());
                values.put(DatabaseHelper.TOURHED_HELPERCODE, hed.getTOURHED_HELPERCODE());
//                values.put(DatabaseHelper.TOURHED_ID, hed.getTOURHED_ID());
                values.put(DatabaseHelper.TOURHED_LOCCODE, hed.getTOURHED_LOCCODE());
                values.put(DatabaseHelper.TOURHED_LOCCODEF, hed.getTOURHED_LOCCODEF());
                values.put(DatabaseHelper.TOURHED_LORRYCODE, hed.getTOURHED_LORRYCODE());
                values.put(DatabaseHelper.TOURHED_MANUREF, hed.getTOURHED_MANUREF());
                values.put(DatabaseHelper.TOURHED_REFNO, hed.getTOURHED_REFNO());
                values.put(DatabaseHelper.TOURHED_REMARKS, hed.getTOURHED_REMARKS());
                values.put(DatabaseHelper.TOURHED_REPCODE, hed.getTOURHED_REPCODE());
                values.put(DatabaseHelper.TOURHED_ROUTECODE, hed.getTOURHED_ROUTECODE());
                values.put(DatabaseHelper.TOURHED_TOURTYPE, hed.getTOURHED_TOURTYPE());
                values.put(DatabaseHelper.TOURHED_TXNDATE, hed.getTOURHED_TXNDATE());
                values.put(DatabaseHelper.TOURHED_VANLOADFLG, hed.getTOURHED_VANLOADFLG());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FTOURHED, values, DatabaseHelper.TOURHED_REFNO + "=?", new String[]{hed.getTOURHED_REFNO()});
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FTOURHED, null, values);
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

    public ArrayList<TourHed> getTourDetails(String sType) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<TourHed> list = new ArrayList<TourHed>();

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String selectQuery;

        if (sType.equals(""))
            //removed by pubudu 2017-10-31
            //selectQuery = "SELECT * FROM fTourHed a,froute b WHERE a.txndate='" + currentDate + "' AND a.routecode=b.routecode";
            selectQuery = "SELECT a.* ,b.* ,e.LocName FROM fTourHed a,froute b ,fLocations e WHERE a.routecode=b.routecode AND  a.LocCode=e.LocCode";
            //selectQuery = "SELECT * FROM fTourHed a,froute b WHERE a.routecode=b.routecode";
        else
            //removed by pubudu 2017-10-31
            //selectQuery = "SELECT * FROM fTourHed a,froute b WHERE a.txndate='" + currentDate + "' AND a.routecode=b.routecode AND tourtype='" + sType + "'";
            selectQuery = "SELECT a.* ,b.* ,e.LocName FROM fTourHed a,froute b ,fLocations e WHERE a.routecode=b.routecode AND  a.LocCode=e.LocCode AND tourtype='" + sType + "'";
            //selectQuery = "SELECT * FROM fTourHed a,froute b WHERE a.routecode=b.routecode AND tourtype='" + sType + "'";

        try {
            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                TourHed hed = new TourHed();


                hed.setTOURHED_LOCNAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FLOCATIONS_LOC_NAME)));

                hed.setTOURHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_ADDMACH)));
                hed.setTOURHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_ADDUSER)));
                hed.setTOURHED_AREACODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_AREACODE)));
                hed.setTOURHED_CLSFLG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_CLSFLG)));
                hed.setTOURHED_COSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_COSTCODE)));
                hed.setTOURHED_DRIVERCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_DRIVERCODE)));
                hed.setTOURHED_HELPERCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_HELPERCODE)));
                hed.setTOURHED_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FROUTE_ROUTE_NAME)));
                hed.setTOURHED_LOCCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_LOCCODE)));
                hed.setTOURHED_LOCCODEF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_LOCCODEF)));
                hed.setTOURHED_LORRYCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_LORRYCODE)));
                hed.setTOURHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_MANUREF)));
                hed.setTOURHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_REFNO)));
                hed.setTOURHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_REMARKS)));
                hed.setTOURHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_REPCODE)));
                hed.setTOURHED_ROUTECODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_ROUTECODE)));
                hed.setTOURHED_TOURTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_TOURTYPE)));
                hed.setTOURHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_TXNDATE)));
                hed.setTOURHED_VANLOADFLG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_VANLOADFLG)));


                list.add(hed);
            }

            cursor.close();

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());

        } finally {
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
            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FTOURHED, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.TABLE_FTOURHED, null, null);
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
    public String getAreaCode(String refNo)
    {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery;

        selectQuery = "SELECT * FROM fTourHed WHERE RefNo ='" + refNo + "'";

        try {

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext())
            {
                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_AREACODE));

            }
            cursor.close();

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }
        return "";
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/

    public ArrayList<String> getCostCodelist() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("SELECT DISTINCT CostCode FROM ftourhed", null);

        ArrayList<String> list = new ArrayList<String>();
        list.add("Select a Cost Code to continue ...");

        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex("CostCode")));

        }

        cursor.close();
        dB.close();
        return list;

    }

    // -------------------------------------- nuwan 21/08/2018 -------------------------------------

    public String getCostCode(String tourRefNo) {

        String costCode = "";

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("SELECT DISTINCT CostCode FROM ftourhed WHERE Refno ='" + tourRefNo + "'", null);

//        ArrayList<String> list = new ArrayList<String>();
//        list.add("Select a Cost Code to continue ...");

        while (cursor.moveToNext()) {
            costCode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOURHED_COSTCODE));
        }

        cursor.close();
        dB.close();
        return costCode;

    }

    // ---------------------------------------------------------------------------------------------

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getCurrentLocCodeFromTourHed() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT " + DatabaseHelper.TOURHED_LOCCODE + " FROM " + DatabaseHelper.TABLE_FTOURHED;

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSALREP_LOCCODE));

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

}
