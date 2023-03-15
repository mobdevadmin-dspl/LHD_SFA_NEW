package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.lankahardwared.lankahw.model.SizeIn;
import com.lankahardwared.lankahw.model.TranIss;

import java.util.ArrayList;

public class SizeInDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbeHelper;
    private String TAG = "SizeInDS";

    public SizeInDS(Context context) {
        this.context = context;
        dbeHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbeHelper.getWritableDatabase();
    }

    public int createOrUpdateSizeInDS(ArrayList<SizeIn> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (SizeIn sizeIn : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FSIZEIN + " WHERE " + DatabaseHelper.FSIZEIN_REFNO + " ='" + sizeIn.getREFNO() + "' AND " + DatabaseHelper.FSIZEIN_ITEMCODE + " ='" + sizeIn.getITEMCODE() + "' AND " + DatabaseHelper.FSIZEIN_SIZECODE + "='" + sizeIn.getSIZECODE() + "' AND " + DatabaseHelper.FSIZEIN_STKRECNO + "='" + sizeIn.getSTKRECNO() + "'", null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FSIZEIN_ADDDATE, sizeIn.getADDDATE());
                values.put(DatabaseHelper.FSIZEIN_APPSTAT, sizeIn.getAPPSTAT());
                values.put(DatabaseHelper.FSIZEIN_BALQTY, sizeIn.getBALQTY());
                values.put(DatabaseHelper.FSIZEIN_COSTPRICE, sizeIn.getCOSTPRICE());
                values.put(DatabaseHelper.FSIZEIN_GROUPCODE, sizeIn.getGROUPCODE());
                values.put(DatabaseHelper.FSIZEIN_ITEMCODE, sizeIn.getITEMCODE());
                values.put(DatabaseHelper.FSIZEIN_LOCCODE, sizeIn.getLOCCODE());
                values.put(DatabaseHelper.FSIZEIN_MRPPRICE, sizeIn.getMRPPRICE());
                values.put(DatabaseHelper.FSIZEIN_OTHCOST, sizeIn.getOTHCOST());
                values.put(DatabaseHelper.FSIZEIN_PRICE, sizeIn.getPRICE());
                values.put(DatabaseHelper.FSIZEIN_QTY, sizeIn.getQTY());
                values.put(DatabaseHelper.FSIZEIN_REFNO, sizeIn.getREFNO());
                values.put(DatabaseHelper.FSIZEIN_SIZECODE, sizeIn.getSIZECODE());
                values.put(DatabaseHelper.FSIZEIN_STKRECDATE, sizeIn.getSTKRECDATE());
                values.put(DatabaseHelper.FSIZEIN_STKRECNO, sizeIn.getSTKRECNO());
                values.put(DatabaseHelper.FSIZEIN_TXNDATE, sizeIn.getTXNDATE());
                values.put(DatabaseHelper.FSIZEIN_TXNTYPE, sizeIn.getTXNTYPE());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FSIZEIN, values, DatabaseHelper.FSIZEIN_REFNO + " =? AND " + DatabaseHelper.FSIZEIN_ITEMCODE + " =? AND " + DatabaseHelper.FSIZEIN_SIZECODE + " =? AND " + DatabaseHelper.FSIZEIN_STKRECNO + "=?", new String[]{String.valueOf(sizeIn.getREFNO()), String.valueOf(sizeIn.getITEMCODE()), String.valueOf(sizeIn.getSIZECODE()), String.valueOf(sizeIn.getSTKRECNO())});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FSIZEIN, null, values);
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void InsertOrReplaceSizeIn(ArrayList<SizeIn> list) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + DatabaseHelper.TABLE_FSIZEIN + " (RefNo,TxnDate,STKRecDate,LocCode,TxnType,STKRecNo,ItemCode,SizeCode,GroupCode,Qty,BalQty,CostPrice,Price,OthCost,AppStat,AddDate,MRPPrice) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (SizeIn sizeIn : list) {

                stmt.bindString(1, sizeIn.getREFNO());
                stmt.bindString(2, sizeIn.getTXNDATE());
                stmt.bindString(3, sizeIn.getSTKRECDATE());
                stmt.bindString(4, sizeIn.getLOCCODE());
                stmt.bindString(5, sizeIn.getTXNTYPE());
                stmt.bindString(6, sizeIn.getSTKRECNO());
                stmt.bindString(7, sizeIn.getITEMCODE());
                stmt.bindString(8, sizeIn.getSIZECODE());
                stmt.bindString(9, sizeIn.getGROUPCODE());
                stmt.bindString(10, sizeIn.getQTY());
                stmt.bindString(11, sizeIn.getBALQTY());
                stmt.bindString(12, sizeIn.getCOSTPRICE());
                stmt.bindString(13, sizeIn.getPRICE());
                stmt.bindString(14, sizeIn.getOTHCOST());
                stmt.bindString(15, sizeIn.getAPPSTAT());
                stmt.bindString(16, sizeIn.getADDDATE());
                stmt.bindString(17, sizeIn.getMRPPRICE());

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

	/*-*-*-*-*-*-*-*-*-*-*-*Get available size list with total BalQty as QOH-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<TranIss> getSizeList(String ItemCode, String RefNo, String LocCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<TranIss> SizeList = new ArrayList<TranIss>();

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT ItemCode,SizeCode,GroupCode,SUM(BalQty) AS QOH FROM fSizeIn  WHERE ItemCode ='" + ItemCode + "' AND LocCode='" + LocCode + "' GROUP BY SizeCode,ItemCode,GroupCode HAVING SUM(BalQty)>0";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                TranIss tranIss = new TranIss();
                tranIss.setITEMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_ITEMCODE)));
                tranIss.setGROUPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_GROUPCODE)));
                tranIss.setSIZECODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_SIZECODE)));
                tranIss.setQOH(cursor.getString(cursor.getColumnIndex("QOH")));
                tranIss.setPRICE(new SizeCombDS(context).getPriceBySizeCode(tranIss.getITEMCODE(), tranIss.getSIZECODE(), tranIss.getGROUPCODE()));
                tranIss.setREFNO(RefNo);
                tranIss.setQTY("0");

                if ((tranIss.getPRICE().trim().length() > 0) && (Double.parseDouble(tranIss.getPRICE().toString()) > 0))
                    SizeList.add(tranIss);
            }

        } catch (Exception e) {
            Log.v(TAG, e.toString());
        } finally {
            cursor.close();
            dB.close();
        }

        return SizeList;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-for printing purposes-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	/* break string into predefined sized pieces of strings */

    public String getSizeString(String itemCode, String locCode, int len) {

        ArrayList<TranIss> sizeinfo = new SizeInDS(context).getSizeList(itemCode, "", locCode);

        String s = "|";
        int j = 1;

        for (TranIss i : sizeinfo) {

            String temp = " " + i.getSIZECODE() + "x" + i.getQOH() + " |";

            int y = (s.length() + temp.length());
            if (y > (len * j)) {
                j++;
                s += "\n" + "|";
            }

            s += " " + i.getSIZECODE() + "x" + i.getQOH() + " |";
        }

        s = (s.length() > 1) ? s : "";

        return s;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Get list of GRN for each sizecode of each itemcode-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<SizeIn> getAscendingGRNList(String ItemCode, String SizeCode, String LocCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<SizeIn> GRNList = new ArrayList<SizeIn>();

        try {

            String selectQuery = "SELECT *  FROM fSizeIn  WHERE ItemCode ='" + ItemCode + "' AND SizeCode ='" + SizeCode + "' AND LocCode='" + LocCode + "' ORDER BY STKRecDate ASC";
            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                SizeIn sizeIn = new SizeIn();

                sizeIn.setID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_ID)));
                sizeIn.setBALQTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_BALQTY)));
                sizeIn.setITEMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_ITEMCODE)));
                sizeIn.setPRICE(new SizeCombDS(context).getPriceBySizeCode(ItemCode, SizeCode, cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_GROUPCODE))));
                sizeIn.setSIZECODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_SIZECODE)));
                sizeIn.setSTKRECNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_STKRECNO)));
                sizeIn.setSTKRECDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_STKRECDATE)));
                sizeIn.setREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_REFNO)));
                sizeIn.setADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_ADDDATE)));
                sizeIn.setAPPSTAT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_APPSTAT)));
                sizeIn.setCOSTPRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_COSTPRICE)));
                sizeIn.setGROUPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_GROUPCODE)));
                sizeIn.setLOCCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_LOCCODE)));
                sizeIn.setMRPPRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_MRPPRICE)));
                sizeIn.setOTHCOST(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_OTHCOST)));
                sizeIn.setQTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_QTY)));
                sizeIn.setTXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_TXNDATE)));
                sizeIn.setTXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FSIZEIN_TXNTYPE)));

                GRNList.add(sizeIn);

            }
            cursor.close();

        } catch (Exception e) {
            Log.v(TAG, e.toString());
        } finally {
            dB.close();
        }

        return GRNList;

    }

	/*-*-*-*-*-*-*-*-*-**-*-*-*-*-*Update balQty-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int UpdateBalQtyByFIFO(ArrayList<SizeIn> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (SizeIn sizeIn : list) {

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FSIZEIN_ADDDATE, sizeIn.getADDDATE());
                values.put(DatabaseHelper.FSIZEIN_APPSTAT, sizeIn.getAPPSTAT());
                values.put(DatabaseHelper.FSIZEIN_BALQTY, sizeIn.getBALQTY());
                values.put(DatabaseHelper.FSIZEIN_COSTPRICE, sizeIn.getCOSTPRICE());
                values.put(DatabaseHelper.FSIZEIN_GROUPCODE, sizeIn.getGROUPCODE());
                values.put(DatabaseHelper.FSIZEIN_ITEMCODE, sizeIn.getITEMCODE());
                values.put(DatabaseHelper.FSIZEIN_LOCCODE, sizeIn.getLOCCODE());
                values.put(DatabaseHelper.FSIZEIN_MRPPRICE, sizeIn.getMRPPRICE());
                values.put(DatabaseHelper.FSIZEIN_OTHCOST, sizeIn.getOTHCOST());
                values.put(DatabaseHelper.FSIZEIN_PRICE, sizeIn.getPRICE());
                values.put(DatabaseHelper.FSIZEIN_QTY, sizeIn.getQTY());
                values.put(DatabaseHelper.FSIZEIN_REFNO, sizeIn.getREFNO());
                values.put(DatabaseHelper.FSIZEIN_SIZECODE, sizeIn.getSIZECODE());
                values.put(DatabaseHelper.FSIZEIN_STKRECDATE, sizeIn.getSTKRECDATE());
                values.put(DatabaseHelper.FSIZEIN_STKRECNO, sizeIn.getSTKRECNO());
                values.put(DatabaseHelper.FSIZEIN_TXNDATE, sizeIn.getTXNDATE());
                values.put(DatabaseHelper.FSIZEIN_TXNTYPE, sizeIn.getTXNTYPE());

                dB.update(DatabaseHelper.TABLE_FSIZEIN, values, DatabaseHelper.FSIZEIN_ID + "=?", new String[]{sizeIn.getID().toString()});

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }
        return count;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public boolean SetReserveRecords(SizeIn sizeIn, String BalQty, String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.FSIZEIN_ADDDATE, sizeIn.getADDDATE());
            values.put(DatabaseHelper.FSIZEIN_APPSTAT, sizeIn.getAPPSTAT());
            values.put(DatabaseHelper.FSIZEIN_BALQTY, BalQty);
            values.put(DatabaseHelper.FSIZEIN_COSTPRICE, sizeIn.getCOSTPRICE());
            values.put(DatabaseHelper.FSIZEIN_GROUPCODE, sizeIn.getGROUPCODE());
            values.put(DatabaseHelper.FSIZEIN_ITEMCODE, sizeIn.getITEMCODE());
            values.put(DatabaseHelper.FSIZEIN_LOCCODE, new LocationsDS(context).GetReserveCode("LT4"));
            values.put(DatabaseHelper.FSIZEIN_MRPPRICE, sizeIn.getMRPPRICE());
            values.put(DatabaseHelper.FSIZEIN_OTHCOST, sizeIn.getOTHCOST());
            values.put(DatabaseHelper.FSIZEIN_PRICE, sizeIn.getPRICE());
            values.put(DatabaseHelper.FSIZEIN_QTY, BalQty);
            values.put(DatabaseHelper.FSIZEIN_REFNO, sizeIn.getREFNO());
            values.put(DatabaseHelper.FSIZEIN_SIZECODE, sizeIn.getSIZECODE());
            values.put(DatabaseHelper.FSIZEIN_STKRECDATE, sizeIn.getSTKRECDATE());
            values.put(DatabaseHelper.FSIZEIN_STKRECNO, sizeIn.getSTKRECNO());
            values.put(DatabaseHelper.FSIZEIN_TXNDATE, sizeIn.getTXNDATE());
            values.put(DatabaseHelper.FSIZEIN_TXNTYPE, sizeIn.getTXNTYPE());
            values.put(DatabaseHelper.FSIZEIN_ORDREFNO, RefNo);

            long i = dB.insert(DatabaseHelper.TABLE_FSIZEIN, null, values);

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
            return false;
        } finally {
            dB.close();
        }
        return true;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<SizeIn> UnsyncedSizeInRecords(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<SizeIn> list = new ArrayList<SizeIn>();

        try {
            Cursor curSizeIn = dB.rawQuery("SELECT *  FROM fsizein  WHERE OrdRefno='" + RefNo + "'", null);

            while (curSizeIn.moveToNext()) {

                SizeIn sizeIn = new SizeIn();

                sizeIn.setID(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_ID)));
                sizeIn.setBALQTY(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_BALQTY)));
                sizeIn.setITEMCODE(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_ITEMCODE)));
                sizeIn.setPRICE(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_PRICE)));
                sizeIn.setSIZECODE(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_SIZECODE)));
                sizeIn.setSTKRECNO(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_STKRECNO)));
                sizeIn.setSTKRECDATE(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_STKRECDATE)));
                sizeIn.setREFNO(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_REFNO)));
                sizeIn.setADDDATE(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_ADDDATE)));
                sizeIn.setAPPSTAT(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_APPSTAT)));
                sizeIn.setCOSTPRICE(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_COSTPRICE)));
                sizeIn.setGROUPCODE(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_GROUPCODE)));
                sizeIn.setLOCCODE(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_LOCCODE)));
                sizeIn.setMRPPRICE(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_MRPPRICE)));
                sizeIn.setOTHCOST(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_OTHCOST)));
                sizeIn.setQTY(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_QTY)));
                sizeIn.setTXNDATE(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_TXNDATE)));
                sizeIn.setTXNTYPE(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_TXNTYPE)));
                sizeIn.setORDREFNO(curSizeIn.getString(curSizeIn.getColumnIndex(DatabaseHelper.FSIZEIN_ORDREFNO)));

                list.add(sizeIn);
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }
        return list;
    }

	/*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int deleteAll() {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FSIZEIN, null);
            if (cursor.getCount() > 0) {
                dB.delete(DatabaseHelper.TABLE_FSIZEIN, null, null);
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
