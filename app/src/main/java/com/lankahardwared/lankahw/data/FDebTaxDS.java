package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.FDebTax;

import java.util.ArrayList;

public class FDebTaxDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "ItemsDS ";

    public FDebTaxDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdatefDebTax(ArrayList<FDebTax> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        Cursor cursor1 = null;

        try {

            for (FDebTax fDebTax : list) {

                cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FDEBTAX , null);

                ContentValues values = new ContentValues();

                values.put(dbHelper.FDEBTAX_DEBCODE, fDebTax.getFDEBTAX_DEBCODE());
                values.put(dbHelper.FDEBTAX_TAXCODE, fDebTax.getFDEBTAX_TAXCODE());
                values.put(dbHelper.FDEBTAX_TAXREGNO, fDebTax.getFDEBTAX_TAXREGNO());
                values.put(dbHelper.FDEBTAX_RECORDID, fDebTax.getFDEBTAX_RECORDID());


                if (cursor.getCount() > 0) {

                    String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FDEBTAX + " WHERE " + dbHelper.FDEBTAX_DEBCODE + "='" + fDebTax.getFDEBTAX_DEBCODE() + "'";
                    cursor1 = dB.rawQuery(selectQuery, null);

                    if (cursor1.getCount() > 0) {
                        count = (int) dB.update(dbHelper.TABLE_FDEBTAX, values, dbHelper.FDEBTAX_DEBCODE + "='" + fDebTax.getFDEBTAX_DEBCODE() + "'", null);
                    } else {
                        count = (int) dB.insert(dbHelper.TABLE_FDEBTAX, null, values);
                    }

                    //dB.update(DatabaseHelper.TABLE_FDEBTAX, values, DatabaseHelper.FDEBTAX_DEBCODE + "=? " , new String[]{fDebTax.getFDEBTAX_DEBCODE().toString()});

                } else {
                    count = (int) dB.insert(dbHelper.TABLE_FDEBTAX, null, values);
                    Log.v("TABLE_FDEBTAX : ***", "Inserted " + count);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (cursor1 != null) {
                cursor1.close();
            }
            dB.close();
        }
        return count;
    }

    public void deleteAll() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        dB.delete(DatabaseHelper.TABLE_FDEBTAX, null, null);
        dB.close();
    }

    public String getTaxRegNo(String debtorCode)
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String query = "SELECT taxRegNo FROM " + DatabaseHelper.TABLE_FDEBTAX + " WHERE " + DatabaseHelper.FDEBTAX_DEBCODE + "='" + debtorCode + "'";

        Cursor cursor1 = dB.rawQuery(query, null);

        if (cursor1.getCount()>0) {

            while (cursor1.moveToNext())
            {
                return cursor1.getString(cursor1.getColumnIndex(DatabaseHelper.FDEBTAX_TAXREGNO));
            }
            cursor1.close();
        }


        return "";
    }

//    public String getTaxComCodeByItemCodeFromDebTax(String itemCode, String debtorCode) {
//
//        if (dB == null) {
//            open();
//        } else if (!dB.isOpen()) {
//            open();
//        }
//
//        String query = "SELECT * FROM " + DatabaseHelper.TABLE_FDEBTAX + " WHERE " + DatabaseHelper.FDEBTAX_DEBCODE + "='" + debtorCode + "'";
//
//        //String selectQuery1 = "SELECT * FROM " + dbHelper.TABLE_FDEBTAX + " WHERE " + dbHelper.FDEBTAX_DEBCODE + "='" + debtorCode + "'";
//
//        Cursor cursor1 = dB.rawQuery(query, null);
//
//        if (cursor1.getCount()>0) {
//
//            try {
//
//                while (cursor1.moveToNext()) {
//                    return cursor1.getString(cursor1.getColumnIndex(DatabaseHelper.FDEBTAX_TAXCODE));
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            } finally {
//                if (cursor1 != null) {
//                    cursor1.close();
//                }
//            }
//        }
////        else
////        {
////            String selectQuery = "SELECT TaxComCode FROM " + dbHelper.TABLE_FITEM + " WHERE " + dbHelper.FITEM_ITEM_CODE + "='" + itemCode + "'";
////
////            Cursor cursor = dB.rawQuery(selectQuery, null);
////            try {
////
////                while (cursor.moveToNext()) {
////                    return cursor.getString(cursor.getColumnIndex(dbHelper.FITEM_TAX_COM_CODE));
////                }
////
////            } catch (Exception e) {
////                e.printStackTrace();
////
////            } finally {
////                if (cursor != null) {
////                    cursor.close();
////                }
////                dB.close();
////            }
////        }
//
//        return "";
//    }

    public String isDebtorTax(String debtorCode)
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor1 = null;
        String taxcode = "";

        try
        {
            String query = "SELECT taxCode FROM " + DatabaseHelper.TABLE_FDEBTAX + " WHERE " + DatabaseHelper.FDEBTAX_DEBCODE + "='" + debtorCode + "'";
            cursor1 = dB.rawQuery(query, null);

            int cn = cursor1.getCount();
            if (cn > 0)
            {
                while (cursor1.moveToNext())
                {
                   taxcode = cursor1.getString(cursor1.getColumnIndex(DatabaseHelper.FDEBTAX_TAXCODE));

                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (cursor1 != null) {
                cursor1.close();
            }
            dB.close();
        }
        return taxcode;
    }
    public boolean isTaxCodeVAT(String debtorCode)
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor1 = null;

        try
        {
            String query = "SELECT taxCode FROM " + DatabaseHelper.TABLE_FDEBTAX + " WHERE " + DatabaseHelper.FDEBTAX_DEBCODE + "='" + debtorCode + "'";
            cursor1 = dB.rawQuery(query, null);


                while (cursor1.moveToNext())
                {
                    if (cursor1.getString(cursor1.getColumnIndex(DatabaseHelper.FDEBTAX_TAXCODE)).equalsIgnoreCase("VAT"))
                    {
                        return true;
                    }
                }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (cursor1 != null) {
                cursor1.close();
            }
            dB.close();
        }
        return false;
    }
}
