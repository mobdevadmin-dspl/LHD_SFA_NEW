package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.RecDet;

import java.util.ArrayList;

public class RecDetDS {

    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    private String TAG = "Kandana";

    public RecDetDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateRecDet(ArrayList<RecDet> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (RecDet recDet : list) {

                String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECDET + " WHERE " + DatabaseHelper.FPRECDET_ID + " = '" + recDet.getFPRECDET_ID() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FPRECDET_ALOAMT, recDet.getFPRECDET_ALOAMT());
                values.put(DatabaseHelper.FPRECDET_AMT, recDet.getFPRECDET_AMT());
                values.put(DatabaseHelper.FPRECDET_BAMT, recDet.getFPRECDET_BAMT());
                values.put(DatabaseHelper.FPRECDET_DCURCODE, recDet.getFPRECDET_DCURCODE());
                values.put(DatabaseHelper.FPRECDET_DCURRATE, recDet.getFPRECDET_DCURRATE());
                values.put(DatabaseHelper.FPRECDET_DTXNDATE, recDet.getFPRECDET_DTXNDATE());
                values.put(DatabaseHelper.FPRECDET_DTXNTYPE, recDet.getFPRECDET_DTXNTYPE());
                values.put(DatabaseHelper.FPRECDET_MANUREF, recDet.getFPRECDET_MANUREF());
                values.put(DatabaseHelper.FPRECDET_OCURRATE, recDet.getFPRECDET_OCURRATE());
                values.put(DatabaseHelper.FPRECDET_OVPAYAMT, recDet.getFPRECDET_OVPAYAMT());
                values.put(DatabaseHelper.FPRECDET_OVPAYBAL, recDet.getFPRECDET_OVPAYBAL());
                values.put(DatabaseHelper.FPRECDET_RECORDID, recDet.getFPRECDET_RECORDID());
                values.put(DatabaseHelper.FPRECDET_REFNO, recDet.getFPRECDET_REFNO());
                values.put(DatabaseHelper.FPRECDET_REFNO1, recDet.getFPRECDET_REFNO1());
                values.put(DatabaseHelper.FPRECDET_REPCODE, recDet.getFPRECDET_REPCODE());
                values.put(DatabaseHelper.FPRECDET_SALEREFNO, recDet.getFPRECDET_SALEREFNO());
                values.put(DatabaseHelper.FPRECDET_TIMESTAMP, recDet.getFPRECDET_TIMESTAMP());
                values.put(DatabaseHelper.FPRECDET_TXNDATE, recDet.getFPRECDET_TXNDATE());
                values.put(DatabaseHelper.FPRECDET_TXNTYPE, recDet.getFPRECDET_TXNTYPE());
                values.put(DatabaseHelper.FPRECDET_DEBCODE, recDet.getFPRECDET_DEBCODE());
                values.put(DatabaseHelper.FPRECDET_REFNO2, recDet.getFPRECDET_REFNO2());
                values.put(DatabaseHelper.FPRECDET_ISDELETE, recDet.getFPRECDET_ISDELETE());
                values.put(DatabaseHelper.FPRECDET_REMARK, recDet.getFPRECDET_REMARK());

                int cn = cursor.getCount();
                if (cn > 0) {
                    count = dB.update(DatabaseHelper.TABLE_FPRECDET, values, DatabaseHelper.FPRECDET_ID + " =?", new String[]{String.valueOf(recDet.getFPRECDET_ID())});
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FPRECDET, null, values);
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
        return count;

    }

	/*
    create for single receipt save
	 */


    public int createOrUpdateRecDetS(ArrayList<RecDet> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (RecDet recDet : list) {

                String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECDET + " WHERE " + DatabaseHelper.FPRECDET_ID + " = '" + recDet.getFPRECDET_ID() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FPRECDET_ALOAMT, recDet.getFPRECDET_ALOAMT());
                values.put(DatabaseHelper.FPRECDET_AMT, recDet.getFPRECDET_AMT());
                values.put(DatabaseHelper.FPRECDET_BAMT, recDet.getFPRECDET_BAMT());
                values.put(DatabaseHelper.FPRECDET_DCURCODE, recDet.getFPRECDET_DCURCODE());
                values.put(DatabaseHelper.FPRECDET_DCURRATE, recDet.getFPRECDET_DCURRATE());
                values.put(DatabaseHelper.FPRECDET_DTXNDATE, recDet.getFPRECDET_DTXNDATE());
                values.put(DatabaseHelper.FPRECDET_DTXNTYPE, recDet.getFPRECDET_DTXNTYPE());
                values.put(DatabaseHelper.FPRECDET_MANUREF, recDet.getFPRECDET_MANUREF());
                values.put(DatabaseHelper.FPRECDET_OCURRATE, recDet.getFPRECDET_OCURRATE());
                values.put(DatabaseHelper.FPRECDET_OVPAYAMT, recDet.getFPRECDET_OVPAYAMT());
                values.put(DatabaseHelper.FPRECDET_OVPAYBAL, recDet.getFPRECDET_OVPAYBAL());
                values.put(DatabaseHelper.FPRECDET_RECORDID, recDet.getFPRECDET_RECORDID());
                values.put(DatabaseHelper.FPRECDET_REFNO, recDet.getFPRECDET_REFNO());
                values.put(DatabaseHelper.FPRECDET_REFNO1, recDet.getFPRECDET_REFNO1());
                values.put(DatabaseHelper.FPRECDET_REPCODE, recDet.getFPRECDET_REPCODE());
                values.put(DatabaseHelper.FPRECDET_SALEREFNO, recDet.getFPRECDET_SALEREFNO());
                values.put(DatabaseHelper.FPRECDET_TIMESTAMP, recDet.getFPRECDET_TIMESTAMP());
                values.put(DatabaseHelper.FPRECDET_TXNDATE, recDet.getFPRECDET_TXNDATE());
                values.put(DatabaseHelper.FPRECDET_TXNTYPE, recDet.getFPRECDET_TXNTYPE());
                values.put(DatabaseHelper.FPRECDET_DEBCODE, recDet.getFPRECDET_DEBCODE());
                values.put(DatabaseHelper.FPRECDET_REFNO2, recDet.getFPRECDET_REFNO2());
                values.put(DatabaseHelper.FPRECDET_ISDELETE, recDet.getFPRECDET_ISDELETE());
                values.put(DatabaseHelper.FPRECDET_REMARK, recDet.getFPRECDET_REMARK());

                int cn = cursor.getCount();

                if (cn > 0) {
                    count = dB.update(DatabaseHelper.TABLE_FPRECDET, values, DatabaseHelper.FPRECDET_ID + " =?", new String[]{String.valueOf(recDet.getFPRECDET_ID())});
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FPRECDET, null, values);
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
        return count;

    }
	
	/*-----------------------------------------------------------------------------------*/

    //only can use for single receipt
    public ArrayList<RecDet> GetReceiptByRefno(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<RecDet> list = new ArrayList<RecDet>();

        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECDET + " WHERE " + DatabaseHelper.FPRECDET_REFNO + " = '" + Refno + "'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                RecDet recDet = new RecDet();

                recDet.setFPRECDET_ALOAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_ALOAMT)));
                recDet.setFPRECDET_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_AMT)));
                recDet.setFPRECDET_BAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_BAMT)));
                recDet.setFPRECDET_DCURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_DCURCODE)));
                recDet.setFPRECDET_DCURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_DCURRATE)));
                recDet.setFPRECDET_DTXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_DTXNDATE)));
                recDet.setFPRECDET_DTXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_DTXNTYPE)));
                recDet.setFPRECDET_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_ID)));
                recDet.setFPRECDET_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_MANUREF)));
                recDet.setFPRECDET_OCURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_OCURRATE)));
                recDet.setFPRECDET_OVPAYAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_OVPAYAMT)));
                recDet.setFPRECDET_OVPAYBAL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_OVPAYBAL)));
                recDet.setFPRECDET_RECORDID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_RECORDID)));
                recDet.setFPRECDET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REFNO)));
                recDet.setFPRECDET_REFNO1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REFNO1)));
                recDet.setFPRECDET_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REPCODE)));
                recDet.setFPRECDET_SALEREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_SALEREFNO)));
                recDet.setFPRECDET_TIMESTAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_TIMESTAMP)));
                recDet.setFPRECDET_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_TXNDATE)));
                recDet.setFPRECDET_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_TXNTYPE)));
                recDet.setFPRECDET_REMARK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REMARK)));
                recDet.setFPRECDET_REFNO2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REFNO2)));
                list.add(recDet);
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return list;

    }

    public ArrayList<RecDet> GetRecInvListByComRefNo(String comRefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<RecDet> list = new ArrayList<RecDet>();

        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECDET + " WHERE " + DatabaseHelper.FPRECDET_REFNO2 + " = '" + comRefNo + "'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                RecDet recDet = new RecDet();

                recDet.setFPRECDET_ALOAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_ALOAMT)));
                recDet.setFPRECDET_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_AMT)));
                recDet.setFPRECDET_BAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_BAMT)));
                recDet.setFPRECDET_DCURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_DCURCODE)));
                recDet.setFPRECDET_DCURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_DCURRATE)));
                recDet.setFPRECDET_DTXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_DTXNDATE)));
                recDet.setFPRECDET_DTXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_DTXNTYPE)));
                recDet.setFPRECDET_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_ID)));
                recDet.setFPRECDET_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_MANUREF)));
                recDet.setFPRECDET_OCURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_OCURRATE)));
                recDet.setFPRECDET_OVPAYAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_OVPAYAMT)));
                recDet.setFPRECDET_OVPAYBAL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_OVPAYBAL)));
                recDet.setFPRECDET_RECORDID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_RECORDID)));
                recDet.setFPRECDET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REFNO)));
                recDet.setFPRECDET_REFNO1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REFNO1)));
                recDet.setFPRECDET_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REPCODE)));
                recDet.setFPRECDET_SALEREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_SALEREFNO)));
                recDet.setFPRECDET_TIMESTAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_TIMESTAMP)));
                recDet.setFPRECDET_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_TXNDATE)));
                recDet.setFPRECDET_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_TXNTYPE)));
                recDet.setFPRECDET_REMARK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REMARK)));
                recDet.setFPRECDET_REFNO2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REFNO2)));
                list.add(recDet);
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return list;

    }

    public ArrayList<RecDet> GetMReceiptByRefno(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<RecDet> list = new ArrayList<RecDet>();

        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECDET + " WHERE " + DatabaseHelper.FPRECDET_REFNO + " = '" + Refno + "'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                RecDet recDet = new RecDet();

                recDet.setFPRECDET_ALOAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_ALOAMT)));
                recDet.setFPRECDET_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_AMT)));
                recDet.setFPRECDET_BAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_BAMT)));
                recDet.setFPRECDET_DCURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_DCURCODE)));
                recDet.setFPRECDET_DCURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_DCURRATE)));
                recDet.setFPRECDET_DTXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_DTXNDATE)));
                recDet.setFPRECDET_DTXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_DTXNTYPE)));
                recDet.setFPRECDET_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_ID)));
                recDet.setFPRECDET_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_MANUREF)));
                recDet.setFPRECDET_OCURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_OCURRATE)));
                recDet.setFPRECDET_OVPAYAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_OVPAYAMT)));
                recDet.setFPRECDET_OVPAYBAL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_OVPAYBAL)));
                recDet.setFPRECDET_RECORDID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_RECORDID)));
                recDet.setFPRECDET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REFNO)));
                recDet.setFPRECDET_REFNO1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REFNO1)));
                recDet.setFPRECDET_REFNO2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REFNO2)));
                recDet.setFPRECDET_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REPCODE)));
                recDet.setFPRECDET_SALEREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_SALEREFNO)));
                recDet.setFPRECDET_TIMESTAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_TIMESTAMP)));
                recDet.setFPRECDET_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_TXNDATE)));
                recDet.setFPRECDET_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_TXNTYPE)));
                recDet.setFPRECDET_REMARK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REMARK)));
                list.add(recDet);
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return list;

    }

    @SuppressWarnings("static-access")
    public int restDataForMR(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FPRECDET + " WHERE " + dbHelper.FPRECDET_REFNO2 + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);
            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.delete(dbHelper.TABLE_FPRECDET, dbHelper.FPRECDET_REFNO2 + " ='" + refno + "'", null);
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

    public int UpdateDeleteStatusMR(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FPRECDET + " WHERE " + dbHelper.FPRECDET_REFNO2 + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);


            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.FPRECDET_ISDELETE, "1");

            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.update(dbHelper.TABLE_FPRECDET, values, dbHelper.FPRECDET_REFNO2 + " ='" + refno + "'", null);
                Log.v("Success Stauts", count + "");
            } else {
                count = (int) dB.insert(DatabaseHelper.TABLE_FPRECDET, null, values);
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

    public int UpdateDeleteStatus(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FPRECDET + " WHERE " + dbHelper.FPRECDET_REFNO + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);


            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.FPRECDET_ISDELETE, "1");

            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.update(dbHelper.TABLE_FPRECDET, values, dbHelper.FPRECDET_REFNO + " ='" + refno + "'", null);
                Log.v("Success Stauts", count + "");
            } else {
                count = (int) dB.insert(DatabaseHelper.TABLE_FPRECDET, null, values);
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

    public String getTotal(String refNo) {

        String sum = null;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT SUM(" + DatabaseHelper.FPRECDET_ALOAMT + ") FROM " + DatabaseHelper.TABLE_FPRECDET + " WHERE "
                    + DatabaseHelper.FPRECDET_REFNO + " = '" + refNo + "'";

            cursor = dB.rawQuery(selectQuery, null);

            sum = cursor.getString(0);

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return sum;

    }

    public int clearRecDet(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        int result = 0;
        try {
            result = dB.delete(DatabaseHelper.TABLE_FPRECDET, DatabaseHelper.FPRECDET_REFNO + "=?",
                    new String[] { Refno });

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return result;
    }

    public int clearRecDetByComRefNo(String comRefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        int result = 0;
        try {
            result = dB.delete(DatabaseHelper.TABLE_FPRECDET, DatabaseHelper.FPRECDET_REFNO2 + "=?",
                    new String[] { comRefNo });

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return result;
    }

    public String getTotalREcDetByComRefNo(String refNo)
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int total =0;

        Cursor cursor = null;
        try {

            String selectQuery = "SELECT SUM(Amt) as Total FROM " + DatabaseHelper.TABLE_FPRECDET + " WHERE " + DatabaseHelper.FPRECDET_REFNO2 + " = '" + refNo + "'" ;
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.moveToFirst())
            {
                total = cursor.getInt(cursor.getColumnIndex("Total"));
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return String.valueOf(total);
    }

    public String getAllocAmtByReceiptRefNo(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECDET + " WHERE " + " RefNo ='" + refNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_ALOAMT));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    public String getComRefByRecRefNo(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECDET + " WHERE " + " RefNo ='" + refNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECDET_REFNO2));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

}
