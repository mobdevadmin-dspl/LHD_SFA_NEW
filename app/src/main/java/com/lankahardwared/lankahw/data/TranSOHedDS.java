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
import com.lankahardwared.lankahw.model.TranSOHed;
import com.lankahardwared.lankahw.model.mapper.PreSalesMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TranSOHedDS {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "TranSOHed";

    public TranSOHedDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateTranSOHedDS(ArrayList<TranSOHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (TranSOHed tranSOHed : list) {

                String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FTRANSOHED + " WHERE " + DatabaseHelper.FTRANSOHED_REFNO + " = '" + tranSOHed.getFTRANSOHED_REFNO() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FTRANSOHED_REFNO, tranSOHed.getFTRANSOHED_REFNO());
                values.put(DatabaseHelper.FTRANSOHED_TXNDATE, tranSOHed.getFTRANSOHED_TXNDATE());
                values.put(DatabaseHelper.FTRANSOHED_MANUREF, tranSOHed.getFTRANSOHED_MANUREF());
                values.put(DatabaseHelper.FTRANSOHED_COSTCODE, tranSOHed.getFTRANSOHED_COSTCODE());
                values.put(DatabaseHelper.FTRANSOHED_REMARKS, tranSOHed.getFTRANSOHED_REMARKS());
                values.put(DatabaseHelper.FTRANSOHED_TXNTYPE, tranSOHed.getFTRANSOHED_TXNTYPE());
                values.put(DatabaseHelper.FTRANSOHED_TOTALAMT, tranSOHed.getFTRANSOHED_TOTALAMT());
                values.put(DatabaseHelper.FTRANSOHED_CURCODE, tranSOHed.getFTRANSOHED_CURCODE());
                values.put(DatabaseHelper.FTRANSOHED_CURRATE, tranSOHed.getFTRANSOHED_CURRATE());
                values.put(DatabaseHelper.FTRANSOHED_DEBCODE, tranSOHed.getFTRANSOHED_DEBCODE());
                values.put(DatabaseHelper.FTRANSOHED_REPCODE, tranSOHed.getFTRANSOHED_REPCODE());
                values.put(DatabaseHelper.FTRANSOHED_BTOTALDIS, tranSOHed.getFTRANSOHED_BTOTALDIS());
                values.put(DatabaseHelper.FTRANSOHED_TOTALDIS, tranSOHed.getFTRANSOHED_TOTALDIS());
                values.put(DatabaseHelper.FTRANSOHED_BPTOTALDIS, tranSOHed.getFTRANSOHED_BPTOTALDIS());
                values.put(DatabaseHelper.FTRANSOHED_BTOTALTAX, tranSOHed.getFTRANSOHED_BTOTALTAX());
                values.put(DatabaseHelper.FTRANSOHED_TOTALTAX, tranSOHed.getFTRANSOHED_TOTALTAX());
                values.put(DatabaseHelper.FTRANSOHED_BTOTALAMT, tranSOHed.getFTRANSOHED_BTOTALAMT());
                values.put(DatabaseHelper.FTRANSOHED_TAXREG, tranSOHed.getFTRANSOHED_TAXREG());
                values.put(DatabaseHelper.FTRANSOHED_CONTACT, tranSOHed.getFTRANSOHED_CONTACT());
                values.put(DatabaseHelper.FTRANSOHED_CUSADD1, tranSOHed.getFTRANSOHED_CUSADD1());
                values.put(DatabaseHelper.FTRANSOHED_CUSADD2, tranSOHed.getFTRANSOHED_CUSADD2());
                values.put(DatabaseHelper.FTRANSOHED_CUSADD3, tranSOHed.getFTRANSOHED_CUSADD3());
                values.put(DatabaseHelper.FTRANSOHED_CUSTELE, tranSOHed.getFTRANSOHED_CUSTELE());
                values.put(DatabaseHelper.FTRANSOHED_ADDMACH, tranSOHed.getFTRANSOHED_ADDMACH());
                values.put(DatabaseHelper.FTRANSOHED_TXNDELDATE, tranSOHed.getFTRANSOHED_TXNDELDATE());
                values.put(DatabaseHelper.FTRANSOHED_IS_ACTIVE, tranSOHed.getFTRANSOHED_IS_ACTIVE());
                values.put(DatabaseHelper.FTRANSOHED_IS_SYNCED, tranSOHed.getfTRANSOHED_IS_SYNCED());
                values.put(DatabaseHelper.FTRANSOHED_LONGITUDE, tranSOHed.getFTRANSOHED_LONGITUDE());
                values.put(DatabaseHelper.FTRANSOHED_LATITUDE, tranSOHed.getFTRANSOHED_LATITUDE());
                values.put(DatabaseHelper.FTRANSOHED_PTOTALDIS, "0");
                values.put(DatabaseHelper.FTRANSOHED_REFNO1, tranSOHed.getFTRANSOHED_REFNO1());
                values.put(DatabaseHelper.FTRANSOHED_START_TIMESO, tranSOHed.getFTRANSOHED_START_TIMESO());
                values.put(DatabaseHelper.FTRANSOHED_END_TIMESO, tranSOHed.getFTRANSOHED_END_TIMESO());
                values.put(DatabaseHelper.FTRANSOHED_ADDRESS, "None");
                values.put(DatabaseHelper.FTRANSOHED_TOTQTY, tranSOHed.getFTRANSOHED_TOTQTY());
                values.put(DatabaseHelper.FTRANSOHED_TOTFREE, tranSOHed.getFTRANSOHED_TOTFREE());

                values.put(DatabaseHelper.FTRANSOHED_TOURCODE, tranSOHed.getFTRANSOHED_TOURCODE());
                values.put(DatabaseHelper.FTRANSOHED_LOCCODE, tranSOHed.getFTRANSOHED_LOCCODE());
                values.put(DatabaseHelper.FTRANSOHED_AREACODE, tranSOHed.getFTRANSOHED_AREACODE());
                values.put(DatabaseHelper.FTRANSOHED_ROUTECODE, tranSOHed.getFTRANSOHED_ROUTECODE());

                int cn = cursor.getCount();
                if (cn > 0) {
                    count = dB.update(DatabaseHelper.TABLE_FTRANSOHED, values, DatabaseHelper.FTRANSOHED_REFNO + " =?", new String[]{String.valueOf(tranSOHed.getFTRANSOHED_REFNO())});
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FTRANSOHED, null, values);
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<TranSOHed> getAllActiveSOOrdHed(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<TranSOHed> list = new ArrayList<TranSOHed>();

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FTRANSOHED + " Where " + DatabaseHelper.FTRANSOHED_IS_ACTIVE + "='1' and " + DatabaseHelper.FTRANSOHED_REFNO + "='" + refno + "' and " + DatabaseHelper.FTRANSOHED_IS_SYNCED + "='0'";

        Cursor cursor = null;

        try {

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                TranSOHed SOHed = new TranSOHed();

                SOHed.setFTRANSOHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_ADDMACH)));
                SOHed.setFTRANSOHED_BPTOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BPTOTALDIS)));
                SOHed.setFTRANSOHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BTOTALAMT)));
                SOHed.setFTRANSOHED_BTOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BTOTALDIS)));
                SOHed.setFTRANSOHED_BTOTALTAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BTOTALTAX)));
                SOHed.setFTRANSOHED_CONTACT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CONTACT)));
                SOHed.setFTRANSOHED_COSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_COSTCODE)));
                SOHed.setFTRANSOHED_CURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CURCODE)));
                SOHed.setFTRANSOHED_CURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CURRATE)));
                SOHed.setFTRANSOHED_CUSADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSADD1)));
                SOHed.setFTRANSOHED_CUSADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSADD2)));
                SOHed.setFTRANSOHED_CUSADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSADD3)));
                SOHed.setFTRANSOHED_CUSTELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSTELE)));
                SOHed.setFTRANSOHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_DEBCODE)));
                SOHed.setFTRANSOHED_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_ID)));
                SOHed.setFTRANSOHED_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_IS_ACTIVE)));
                SOHed.setFTRANSOHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_MANUREF)));
                SOHed.setFTRANSOHED_PTOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_PTOTALDIS)));
                SOHed.setFTRANSOHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO)));
                SOHed.setFTRANSOHED_REFNO1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO1)));
                SOHed.setFTRANSOHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REMARKS)));
                SOHed.setFTRANSOHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REPCODE)));
                SOHed.setFTRANSOHED_TAXREG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TAXREG)));
                SOHed.setFTRANSOHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALAMT)));
                SOHed.setFTRANSOHED_TOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALDIS)));
                SOHed.setFTRANSOHED_TOTALTAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALTAX)));
                SOHed.setFTRANSOHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNDATE)));
                SOHed.setFTRANSOHED_TXNDELDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNDELDATE)));
                SOHed.setFTRANSOHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNTYPE)));
                SOHed.setfTRANSOHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_IS_SYNCED)));

                list.add(SOHed);

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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public TranSOHed getActiveSOHed() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FTRANSOHED + " Where " + DatabaseHelper.FTRANSOHED_IS_ACTIVE + "='1' and " + DatabaseHelper.FTRANSOHED_IS_SYNCED + "='0'";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        TranSOHed SOHed = new TranSOHed();

        try {
            while (cursor.moveToNext()) {


                SOHed.setFTRANSOHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_ADDMACH)));
                SOHed.setFTRANSOHED_BPTOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BPTOTALDIS)));
                SOHed.setFTRANSOHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BTOTALAMT)));
                SOHed.setFTRANSOHED_BTOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BTOTALDIS)));
                SOHed.setFTRANSOHED_BTOTALTAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BTOTALTAX)));
                SOHed.setFTRANSOHED_CONTACT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CONTACT)));
                SOHed.setFTRANSOHED_COSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_COSTCODE)));
                SOHed.setFTRANSOHED_CURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CURCODE)));
                SOHed.setFTRANSOHED_CURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CURRATE)));
                SOHed.setFTRANSOHED_CUSADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSADD1)));
                SOHed.setFTRANSOHED_CUSADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSADD2)));
                SOHed.setFTRANSOHED_CUSADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSADD3)));
                SOHed.setFTRANSOHED_CUSTELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSTELE)));
                SOHed.setFTRANSOHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_DEBCODE)));
                SOHed.setFTRANSOHED_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_ID)));
                SOHed.setFTRANSOHED_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_IS_ACTIVE)));
                SOHed.setFTRANSOHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_MANUREF)));
                SOHed.setFTRANSOHED_PTOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_PTOTALDIS)));
                SOHed.setFTRANSOHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO)));
                SOHed.setFTRANSOHED_REFNO1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO1)));
                SOHed.setFTRANSOHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REMARKS)));
                SOHed.setFTRANSOHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REPCODE)));
                SOHed.setFTRANSOHED_TAXREG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TAXREG)));
                SOHed.setFTRANSOHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALAMT)));
                SOHed.setFTRANSOHED_TOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALDIS)));
                SOHed.setFTRANSOHED_TOTALTAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALTAX)));
                SOHed.setFTRANSOHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNDATE)));
                SOHed.setFTRANSOHED_TXNDELDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNDELDATE)));
                SOHed.setFTRANSOHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNTYPE)));
                SOHed.setfTRANSOHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_IS_SYNCED)));

                SOHed.setFTRANSOHED_TOURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOURCODE)));
                SOHed.setFTRANSOHED_LOCCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_LOCCODE)));
                SOHed.setFTRANSOHED_AREACODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_AREACODE)));
                SOHed.setFTRANSOHED_ROUTECODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_ROUTECODE)));


            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return SOHed;
    }

    public int InactiveStatusUpdate(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FTRANSOHED + " WHERE " + DatabaseHelper.FTRANSOHED_REFNO + " = '" + refno + "'";

            cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.FTRANSOHED_IS_ACTIVE, "0");

            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.update(DatabaseHelper.TABLE_FTRANSOHED, values, DatabaseHelper.FTRANSOHED_REFNO + " =?", new String[]{String.valueOf(refno)});
            } else {
                count = (int) dB.insert(DatabaseHelper.TABLE_FTRANSOHED, null, values);
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

    public TranSOHed getDetailsforPrint(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        TranSOHed SOHed = new TranSOHed();

        try {
            String selectQuery = "SELECT TxnDate,TxnDelDAte,TotalAmt,DebCode,Remarks,TaxReg,TotalTax,TotalDis,TotQty,TotFree FROM " + DatabaseHelper.TABLE_FTRANSOHED + " WHERE " + DatabaseHelper.FTRANSOHED_REFNO + " = '" + Refno + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                SOHed.setFTRANSOHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNDATE)));
                SOHed.setFTRANSOHED_TXNDELDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNDELDATE)));
                SOHed.setFTRANSOHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALAMT)));
                SOHed.setFTRANSOHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_DEBCODE)));
                SOHed.setFTRANSOHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REMARKS)));
                SOHed.setFTRANSOHED_TAXREG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TAXREG)));
                SOHed.setFTRANSOHED_TOTALTAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALTAX)));
                SOHed.setFTRANSOHED_TOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALDIS)));
                SOHed.setFTRANSOHED_TOTQTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTQTY)));
                SOHed.setFTRANSOHED_TOTFREE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTFREE)));
            }
            cursor.close();

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return SOHed;

    }

    public ArrayList<TranSOHed> getAllUnsyncedOrdHed(String newText, String param) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        ArrayList<TranSOHed> list = new ArrayList<TranSOHed>();
        Cursor cursor = null;
        try {

            String selectQuery;

            if (param.equals("U"))
                selectQuery = "select * from ftransohed sa, fdebtor cu where sa.isActive='0' AND sa.isSynced ='1' and sa.DebCode=cu.DebCode and sa.RefNo  || cu.DebName  like '%" + newText + "%' order by sa.refno desc";
            else
                selectQuery = "select * from ftransohed sa, fdebtor cu where sa.isActive='0' AND sa.isSynced ='0' and sa.DebCode=cu.DebCode and sa.RefNo  || cu.DebName  like '%" + newText + "%' order by sa.refno desc";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                TranSOHed SOHed = new TranSOHed();

                SOHed.setFTRANSOHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_ADDMACH)));
                SOHed.setFTRANSOHED_BPTOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BPTOTALDIS)));
                SOHed.setFTRANSOHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BTOTALAMT)));
                SOHed.setFTRANSOHED_BTOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BTOTALDIS)));
                SOHed.setFTRANSOHED_BTOTALTAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BTOTALTAX)));
                SOHed.setFTRANSOHED_CONTACT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CONTACT)));
                SOHed.setFTRANSOHED_COSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_COSTCODE)));
                SOHed.setFTRANSOHED_CURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CURCODE)));
                SOHed.setFTRANSOHED_CURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CURRATE)));
                SOHed.setFTRANSOHED_CUSADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSADD1)));
                SOHed.setFTRANSOHED_CUSADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSADD2)));
                SOHed.setFTRANSOHED_CUSADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSADD3)));
                SOHed.setFTRANSOHED_CUSTELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSTELE)));
                SOHed.setFTRANSOHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_DEBCODE)));
                SOHed.setFTRANSOHED_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_ID)));
                SOHed.setFTRANSOHED_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_IS_ACTIVE)));
                SOHed.setFTRANSOHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_MANUREF)));
                SOHed.setFTRANSOHED_PTOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_PTOTALDIS)));
                SOHed.setFTRANSOHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO)));
                SOHed.setFTRANSOHED_REFNO1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO1)));
                SOHed.setFTRANSOHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REMARKS)));
                SOHed.setFTRANSOHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REPCODE)));
                SOHed.setFTRANSOHED_TAXREG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TAXREG)));
                SOHed.setFTRANSOHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALAMT)));
                SOHed.setFTRANSOHED_TOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALDIS)));
                SOHed.setFTRANSOHED_TOTALTAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALTAX)));
                SOHed.setFTRANSOHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNDATE)));
                SOHed.setFTRANSOHED_TXNDELDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNDELDATE)));
                SOHed.setFTRANSOHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNTYPE)));
                SOHed.setfTRANSOHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_IS_SYNCED)));

                list.add(SOHed);

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

            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FTRANSOHED + " WHERE " + DatabaseHelper.FTRANSOHED_REFNO + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                String status = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_IS_SYNCED));
                /* if order already synced, can't delete */
                if (status.equals("1"))
                    Result = false;
                else {
                    int success = dB.delete(DatabaseHelper.TABLE_FTRANSOHED, DatabaseHelper.FTRANSOHED_REFNO + " ='" + refno + "'", null);
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

    /**
     * -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
     * *-*-*-*-*-*-*-*-*-*-*-*-
     */

    public ArrayList<PreSalesMapper> getAllUnSyncOrdHed() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PreSalesMapper> list = new ArrayList<PreSalesMapper>();

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FTRANSOHED + " Where " + DatabaseHelper.FTRANSOHED_IS_ACTIVE + "='0' and " + DatabaseHelper.FTRANSOHED_IS_SYNCED + "='0'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        //localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

        while (cursor.moveToNext()) {

            PreSalesMapper preSalesMapper = new PreSalesMapper();

            CompanyBranchDS branchDS = new CompanyBranchDS(context);
            preSalesMapper.setNextNumVal(branchDS.getCurrentNextNumVal(context.getResources().getString(R.string.NumVal)));

            preSalesMapper.setDistDB(localSP.getString("Dist_DB", ""));
            preSalesMapper.setConsoleDB(localSP.getString("Console_DB", ""));

            preSalesMapper.setFTRANSOHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_ADDMACH)));
            preSalesMapper.setFTRANSOHED_BPTOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BPTOTALDIS)));
            preSalesMapper.setFTRANSOHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BTOTALAMT)));
            preSalesMapper.setFTRANSOHED_BTOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BTOTALDIS)));
            preSalesMapper.setFTRANSOHED_BTOTALTAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_BTOTALTAX)));
            preSalesMapper.setFTRANSOHED_CONTACT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CONTACT)));
            preSalesMapper.setFTRANSOHED_COSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_COSTCODE)));
            preSalesMapper.setFTRANSOHED_CURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CURCODE)));
            preSalesMapper.setFTRANSOHED_CURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CURRATE)));
            preSalesMapper.setFTRANSOHED_CUSADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSADD1)));
            preSalesMapper.setFTRANSOHED_CUSADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSADD2)));
            preSalesMapper.setFTRANSOHED_CUSADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSADD3)));
            preSalesMapper.setFTRANSOHED_CUSTELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_CUSTELE)));
            preSalesMapper.setFTRANSOHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_DEBCODE)));
            preSalesMapper.setFTRANSOHED_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_ID)));
            preSalesMapper.setFTRANSOHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_MANUREF)));
            preSalesMapper.setFTRANSOHED_PTOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_PTOTALDIS)));
            preSalesMapper.setFTRANSOHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO)));
            preSalesMapper.setFTRANSOHED_REFNO1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO1)));
            preSalesMapper.setFTRANSOHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REMARKS)));
            preSalesMapper.setFTRANSOHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REPCODE)));
            preSalesMapper.setFTRANSOHED_TAXREG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TAXREG)));
            preSalesMapper.setFTRANSOHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALAMT)));
            preSalesMapper.setFTRANSOHED_TOTALDIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALDIS)));
            preSalesMapper.setFTRANSOHED_TOTALTAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOTALTAX)));
            preSalesMapper.setFTRANSOHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNDATE)));
            preSalesMapper.setFTRANSOHED_TXNDELDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNDELDATE)));
            preSalesMapper.setFTRANSOHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNTYPE)));
            preSalesMapper.setFTRANSOHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_IS_SYNCED)));
            preSalesMapper.setFTRANSOHED_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_IS_ACTIVE)));
            preSalesMapper.setFTRANSOHED_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_LATITUDE)));
            preSalesMapper.setFTRANSOHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_LONGITUDE)));
            preSalesMapper.setFTRANSOHED_START_TIMESO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_START_TIMESO)));
            preSalesMapper.setFTRANSOHED_END_TIMESO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_END_TIMESO)));
            preSalesMapper.setFTRANSOHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_ADDRESS)));
            preSalesMapper.setFTRANSOHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNDATE)));
            preSalesMapper.setFTRANSOHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REPCODE)));
            preSalesMapper.setFTRANSOHED_LOCCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_LOCCODE)));
            preSalesMapper.setFTRANSOHED_AREACODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_AREACODE)));
            preSalesMapper.setFTRANSOHED_ROUTECODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_ROUTECODE)));
            preSalesMapper.setFTRANSOHED_TOURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TOURCODE)));

            String RefNo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_REFNO));

            preSalesMapper.setOrdDet(new TranSODetDS(context).getAllUnSync(RefNo));
            preSalesMapper.setTaxDTs(new PreSaleTaxDTDS(context).getAllTaxDT(RefNo));
            preSalesMapper.setTaxRGs(new PreSaleTaxRGDS(context).getAllTaxRG(RefNo));
            preSalesMapper.setOrdDisc(new OrderDiscDS(context).getAllOrderDiscs(RefNo));
            preSalesMapper.setFreeIssues(new OrdFreeIssueDS(context).getAllFreeIssues(RefNo));

            list.add(preSalesMapper);

        }
        cursor.close();
        dB.close();
        return list;

    }

    /**
     * -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
     * *-*-*-*-*-*-*-*-*-*-*-*-
     */

    public int updateIsSynced(PreSalesMapper mapper) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.FTRANSOHED_IS_SYNCED, "1");

            if (mapper.isSynced())
            {
                count = dB.update(DatabaseHelper.TABLE_FTRANSOHED, values, DatabaseHelper.FTRANSOHED_REFNO + " =?", new String[]{String.valueOf(mapper.getFTRANSOHED_REFNO())});
            }
            else
            {
                //Log.d("TRANSOHEDDS", "MAPPER_NOT_SYNCED");
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

	/*-*-*-*--*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*--*-*-*-*-*-*-*/

    public boolean isAnyActive() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        boolean res = false;

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FTRANSOHED + " WHERE " + DatabaseHelper.FTRANSOHED_IS_ACTIVE + "='1'";
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

    public boolean validateActivePreSales()
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
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FTRANSOHED + " WHERE " + DatabaseHelper.FTRANSOHED_IS_ACTIVE + "='1'";
            cursor = dB.rawQuery(selectQuery, null);

            /*Active invoice available*/
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                String txndate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_TXNDATE));

                /*txndate is equal to current date*/
                if (txndate.equals(toDay))
                    res = true;
                /*if invoice is older, then reset data*/
                else {
                    String Refno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO));
                    restData(Refno);
                    new InvDetDS(context).restData(Refno);
                    new OrderDiscDS(context).clearData(Refno);
                    new OrdFreeIssueDS(context).ClearFreeIssues(Refno);
                    UtilityContainer.ClearVanSharedPref(context);
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

    public boolean isAllSynced() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        boolean res = false;

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FTRANSOHED + " WHERE " + DatabaseHelper.FTRANSOHED_IS_SYNCED + "='0'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0)
                res = false;
            else
                res = true;

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

    public String getActivePreSalesRef() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String res = null;

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FTRANSOHED + " WHERE " + DatabaseHelper.FTRANSOHED_IS_ACTIVE + "='1'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                cursor.moveToNext();
                res = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO));
                //res += " ( " + new DebtorDS(context).getDebNameByCode(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_DEBCODE))) + " )";
            } else
                res = "None";

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

    public String getActivePreSalesRefWithDebtor() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String res = null;

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FTRANSOHED + " WHERE " + DatabaseHelper.FTRANSOHED_IS_ACTIVE + "='1'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                cursor.moveToNext();
                res = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO));
                res += " ( " + new DebtorDS(context).getDebNameByCode(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_DEBCODE))) + " )";
            } else
                res = "None";

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

    public String getRefnoByDebcode(String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FTRANSOHED + " WHERE " + dbHelper.FTRANSOHED_REFNO + "='" + refno + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(dbHelper.FTRANSOHED_DEBCODE));
        }
        return "";

    }

    public String getLastOrderByDebCode(String debCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FTRANSOHED + " WHERE " + dbHelper.FTRANSOHED_DEBCODE + "='" + debCode + "'"  + " ORDER BY " + dbHelper.FTRANSOHED_DEBCODE + " DESC LIMIT 1";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(dbHelper.FTRANSOHED_TOTALAMT));
        }
        return "";

    }

}
