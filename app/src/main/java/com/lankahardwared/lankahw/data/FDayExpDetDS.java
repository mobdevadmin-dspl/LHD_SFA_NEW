package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.FDayExpDet;

import java.util.ArrayList;

public class FDayExpDetDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "Ebony";

    public FDayExpDetDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    /*
     * insert code
     */
    @SuppressWarnings("static-access")
    public int createOrUpdateExpenseDet(ArrayList<FDayExpDet> list) {

        // int count =0;
        int serverdbID = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (FDayExpDet expdet : list) {
                ContentValues values = new ContentValues();

                String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FDAYEXPDET + " WHERE " + dbHelper.FDAYEXPDET_REFNO + " = '" + expdet.getEXPDET_REFNO() + "'" + " AND " + dbHelper.FDAYEXPDET_EXPCODE + " = '" + expdet.getEXPDET_EXPCODE() + "'";
                cursor = dB.rawQuery(selectQuery, null);

                // values.put(dbHelper.NONPRDDET_ID, nondet.getNONPRDDET_ID());
                values.put(dbHelper.FDAYEXPDET_REFNO, expdet.getEXPDET_REFNO());
                values.put(dbHelper.FDAYEXPDET_TXNDATE, expdet.getEXPDET_TXNDATE());
                values.put(dbHelper.FDAYEXPDET_EXPCODE, expdet.getEXPDET_EXPCODE());
                values.put(dbHelper.FDAYEXPDET_SEQNO, expdet.getEXPDET_SEQNO());
                values.put(dbHelper.FDAYEXPDET_AMT, expdet.getEXPDET_AMOUNT());

                int count = cursor.getCount();
                if (count > 0) {
                    serverdbID = dB.update(dbHelper.TABLE_FDAYEXPDET, values, dbHelper.FDAYEXPDET_REFNO + " =?" + " AND " + dbHelper.FDAYEXPDET_EXPCODE + " =?", new String[]{String.valueOf(expdet.getEXPDET_REFNO()), String.valueOf(expdet.getEXPDET_EXPCODE())});

                } else {
                    serverdbID = (int) dB.insert(dbHelper.TABLE_FDAYEXPDET, null, values);
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
        return serverdbID;

    }

    // Load Detail records of FdayExpdet for selected Reference number.
    public ArrayList<FDayExpDet> getAllExpDetails(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FDayExpDet> list = new ArrayList<FDayExpDet>();

        String selectQuery = "select * from " + dbHelper.TABLE_FDAYEXPDET + " WHERE " + dbHelper.FDAYEXPDET_REFNO + "='" + refno + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            FDayExpDet fdayexpset = new FDayExpDet();

            fdayexpset.setEXPDET_ID(cursor.getString(cursor.getColumnIndex(dbHelper.FDAYEXPDET_ID)));
            fdayexpset.setEXPDET_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.FDAYEXPDET_REFNO)));
            fdayexpset.setEXPDET_EXPCODE(cursor.getString(cursor.getColumnIndex(dbHelper.FDAYEXPDET_EXPCODE)));
            fdayexpset.setEXPDET_TXNDATE(cursor.getString(cursor.getColumnIndex(dbHelper.FDAYEXPDET_TXNDATE)));
            fdayexpset.setEXPDET_AMOUNT(cursor.getString(cursor.getColumnIndex(dbHelper.FDAYEXPDET_AMT)));
            fdayexpset.setEXPDET_SEQNO(cursor.getString(cursor.getColumnIndex(dbHelper.FDAYEXPDET_SEQNO)));

            list.add(fdayexpset);

        }

        return list;
    }

    public String getDuplicate(String code, String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FDAYEXPDET + " WHERE " + dbHelper.FDAYEXPDET_EXPCODE + "='" + code + "' AND " + dbHelper.FDAYEXPDET_REFNO + "='" + RefNo + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(dbHelper.FDAYEXPDET_EXPCODE));

        }

        return "";
    }

    @SuppressWarnings("static-access")
    public int deleteOrdDetByID(String id) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FDAYEXPDET + " WHERE " + dbHelper.FDAYEXPDET_ID + "='" + id + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.TABLE_FDAYEXPDET, dbHelper.FDAYEXPDET_ID + "='" + id + "'", null);
                Log.v("FExpDet Deleted ", success + "");
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
    public int getExpenceCount(String refNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            String selectQuery = "SELECT count(RefNo) as RefNo FROM " + dbHelper.TABLE_FDAYEXPDET +  " WHERE  " + dbHelper.FDAYEXPDET_REFNO + "='" + refNo + "'";
            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                return Integer.parseInt(cursor.getString(cursor.getColumnIndex("RefNo")));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dB.close();
        }
        return 0;

    }
    @SuppressWarnings("static-access")
    public int getAllExpDetail(String RefNo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FDAYEXPDET + " WHERE " + dbHelper.FDAYEXPDET_REFNO + "='" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.TABLE_FDAYEXPDET, dbHelper.FDAYEXPDET_REFNO + "='" + RefNo + "'", null);
                Log.v("FtranDet Deleted ", success + "");
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

    @SuppressWarnings("static-access")
    public int ExpDetByID(String RefNo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FDAYEXPDET + " WHERE " + dbHelper.FDAYEXPDET_REFNO + "='" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.TABLE_FDAYEXPDET, dbHelper.FDAYEXPDET_REFNO + "='" + RefNo + "'", null);
                Log.v("FtranDet Deleted ", success + "");
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

    public String getTotalExpenseSumReturns(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        // String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FREASON
        // +" WHERE "+dbHelper.FREASON_NAME+"='"+name+"'";
        String selectQuery = "select sum(FD.Amt) as TotSum from fdayexpdet FD where FD.refno = '" + refno + "'";
        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex("TotSum")) != null)
                return cursor.getString(cursor.getColumnIndex("TotSum"));
            else
                return "0";

        }

        return "0";
    }

    // Delete Record from Fdayexpdet
    @SuppressWarnings("static-access")
    public int DeleteRec(String RefNo, String expcode) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FDAYEXPDET + " WHERE " + dbHelper.FDAYEXPDET_REFNO + "='" + RefNo + "' AND " + dbHelper.FDAYEXPDET_EXPCODE + "='" + expcode + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.TABLE_FDAYEXPDET, dbHelper.FDAYEXPDET_REFNO + "='" + RefNo + "' AND " + dbHelper.FDAYEXPDET_EXPCODE + "='" + expcode + "' ", null);
                Log.v("FtranDet Deleted ", success + "");
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

    @SuppressWarnings("static-access")
    public int restDataExp(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FDAYEXPDET + " WHERE " + dbHelper.FDAYEXPDET_REFNO + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);
            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.delete(dbHelper.TABLE_FDAYEXPDET, dbHelper.FDAYEXPDET_REFNO + " ='" + refno + "'", null);
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

}
