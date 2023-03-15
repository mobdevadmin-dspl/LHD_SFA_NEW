package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.TranIss;

import java.util.ArrayList;

public class TranIssDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "TranIssDS";

    public TranIssDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateTranIss(ArrayList<TranIss> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (TranIss ftraniss : list) {

                cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FTRANISS + " WHERE " + DatabaseHelper.FTRANISS_ITEMCODE + " ='" + ftraniss.getITEMCODE() + "' AND " + DatabaseHelper.FTRANISS_REFNO + " ='" + ftraniss.getREFNO() + "' AND " + DatabaseHelper.FTRANISS_SIZECODE + " ='" + ftraniss.getSIZECODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FTRANISS_REFNO, ftraniss.getREFNO());
                values.put(DatabaseHelper.FTRANISS_SIZECODE, ftraniss.getSIZECODE());
                values.put(DatabaseHelper.FTRANISS_GROUPCODE, ftraniss.getGROUPCODE());
                values.put(DatabaseHelper.FTRANISS_ITEMCODE, ftraniss.getITEMCODE());
                values.put(DatabaseHelper.FTRANISS_QTY, String.valueOf(Integer.parseInt(ftraniss.getQTY())));
                values.put(DatabaseHelper.FTRANISS_QOH, ftraniss.getQOH());
                values.put(DatabaseHelper.FTRANISS_PRICE, ftraniss.getPRICE());
                values.put(DatabaseHelper.FTRANISS_AMT, String.format("%.2f", Double.parseDouble(ftraniss.getQTY()) * Double.parseDouble(ftraniss.getPRICE())));
                //values.put(DatabaseHelper.FTRANISS_BRAND, new StkIssDS(context).getBrand(ftraniss.getITEMCODE()));

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FTRANISS, values, DatabaseHelper.FTRANISS_ITEMCODE + " =? AND " + DatabaseHelper.FTRANISS_REFNO + " =? AND " + DatabaseHelper.FTRANISS_SIZECODE + "=?", new String[]{String.valueOf(ftraniss.getITEMCODE()), String.valueOf(ftraniss.getREFNO()), String.valueOf(ftraniss.getSIZECODE())});
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FTRANISS, null, values);
                }
            }

        } catch (Exception e) {

            Log.v("Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<TranIss> getAllSizeListByRefNo(String refNo, String itemCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        ArrayList<TranIss> list = new ArrayList<TranIss>();

        try {
            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FTRANISS + " WHERE " + DatabaseHelper.FTRANISS_ITEMCODE + " ='" + itemCode + "' AND " + DatabaseHelper.FTRANISS_REFNO + " ='" + refNo + "'", null);

            while (cursor.moveToNext()) {

                TranIss tranIss = new TranIss();

                tranIss.setID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_ID)));
                tranIss.setITEMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_ITEMCODE)));
                tranIss.setGROUPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_GROUPCODE)));
                tranIss.setSIZECODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_SIZECODE)));
                tranIss.setREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_REFNO)));
                tranIss.setQTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_QTY)));
                tranIss.setQOH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_QOH)));
                tranIss.setPRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_PRICE)));
                tranIss.setAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_AMT)));

                list.add(tranIss);
            }

            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        } finally {
            dB.close();
        }

        return list;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<TranIss> getItemListforQOH(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<TranIss> sizelist = new ArrayList<TranIss>();

        try {

            Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FTRANISS + " WHERE " + DatabaseHelper.FTRANDET_REFNO + " ='" + RefNo + "' AND Qty<>'0'", null);

            while (cursor.moveToNext()) {

                TranIss iss = new TranIss();
                iss.setITEMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_ITEMCODE)));
                iss.setSIZECODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_SIZECODE)));
                iss.setGROUPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_GROUPCODE)));
                iss.setQOH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_QOH)));
                iss.setQTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_QTY)));
                iss.setPRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_PRICE)));
                iss.setAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_AMT)));
                iss.setREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_REFNO)));
                sizelist.add(iss);
            }

            cursor.close();
        } catch (Exception e) {

            Log.v("Exception", e.toString());

        } finally {
            dB.close();
        }

        return sizelist;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int deleteRecords(String itemCode, String RefNo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FTRANISS + " WHERE " + DatabaseHelper.FTRANISS_ITEMCODE + "='" + itemCode + "' AND " + DatabaseHelper.FTRANISS_REFNO + "='" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FTRANISS, DatabaseHelper.FTRANISS_ITEMCODE + "='" + itemCode + "' AND " + DatabaseHelper.FTRANISS_REFNO + "='" + RefNo + "'", null);
                Log.v("OrdDet Deleted ", success + "");
            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            cursor.close();
            dB.close();
        }

        return count;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void ClearTable(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            dB.delete(DatabaseHelper.TABLE_FTRANISS, DatabaseHelper.FTRANISS_REFNO + "='" + RefNo + "'", null);

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getSizecodeString(String Itemcode, String price, String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("SELECT sizecode, qty FROM ftraniss WHERE refno='" + refno + "' AND itemcode= '" + Itemcode + "'  AND price='" + price + "' AND qty<>'0'  ORDER BY sizecode ASC", null);

        String s = "|";
        int len = 44, j = 1;
        try {
            while (cursor.moveToNext()) {

                String sKey = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_SIZECODE));
                String sVal = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_QTY));

                String temp = " " + sKey + "x" + sVal + " |";

                int y = (s.length() + temp.length());

                if (y > (len * j)) {
                    j++;
                    s += "\n" + "|";
                }

                s += " " + sKey + "x" + sVal + " |";
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }

        return s;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<TranIss> getSalesPreviewItemList(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<TranIss> sizelist = new ArrayList<TranIss>();

        try {

            Cursor cursor = dB.rawQuery("select itemcode,brand,sum(qty) as qty ,sum(amt) as amt ,price from ftraniss where refno='" + RefNo + "'  AND qty<>'0' GROUP BY itemcode,price", null);

            while (cursor.moveToNext()) {

                TranIss iss = new TranIss();
                iss.setITEMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_ITEMCODE)));
                iss.setQTY(cursor.getString(cursor.getColumnIndex("qty")));
                iss.setPRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_PRICE)));
                iss.setAMT(cursor.getString(cursor.getColumnIndex("amt")));
                iss.setBrand(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANISS_BRAND)));
                sizelist.add(iss);
            }

            cursor.close();
        } catch (Exception e) {

            Log.v("Exception", e.toString());

        } finally {
            dB.close();
        }

        return sizelist;
    }

}
