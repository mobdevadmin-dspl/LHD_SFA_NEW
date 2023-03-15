package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.PayMode;

import java.util.ArrayList;

public class PayModeDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = " PayModeDS";

    public PayModeDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdatePayMode(ArrayList<PayMode> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (PayMode payMode : list)
            {

                ContentValues values = new ContentValues();

                String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPAYMODE + " WHERE " + DatabaseHelper.FPAYMODE_PAID_ID + " = '" + payMode.getFPAYMODE_PAID_ID() + "'";
                //String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPAYMODE;

                cursor = dB.rawQuery(selectQuery, null);

                values.put(DatabaseHelper.FPAYMODE_PAID_ID, payMode.getFPAYMODE_PAID_ID());
                values.put(DatabaseHelper.FPAYMODE_PAID_REF_NO, payMode.getFPAYMODE_REF_NO());
                values.put(DatabaseHelper.FPAYMODE_PAID_TYPE, payMode.getFPAYMODE_PAID_TYPE());
                values.put(DatabaseHelper.FPAYMODE_PAID_DATE, payMode.getFPAYMODE_PAID_DATE());
                values.put(DatabaseHelper.FPAYMODE_PAID_BANK, payMode.getFPAYMODE_PAID_BANK());
                values.put(DatabaseHelper.FPAYMODE_PAID_CREDIT_CARD_TYPE, payMode.getFPAYMODE_PAID_CREDIT_CARD_TYPE());
                values.put(DatabaseHelper.FPAYMODE_PAID_CHEQUE_DATE, payMode.getFPAYMODE_PAID_CHEQUE_DATE());
                values.put(DatabaseHelper.FPAYMODE_PAID_CARD_EXP_DATE, payMode.getFPAYMODE_PAID_CARD_EXP_DATE());
                values.put(DatabaseHelper.FPAYMODE_PAID_AMOUNT, payMode.getFPAYMODE_PAID_AMOUNT());
                values.put(DatabaseHelper.FPAYMODE_PAID_CHEQUE_NO, payMode.getFPAYMODE_PAID_CHEQUE_NO());
                values.put(DatabaseHelper.FPAYMODE_PAID_CREDIT_CARD_NO, payMode.getFPAYMODE_PAID_CREDIT_CARD_NO());
                values.put(DatabaseHelper.FPAYMODE_PAID_SLIP_NO, payMode.getFPAYMODE_PAID_SLIP_NO());
                values.put(DatabaseHelper.FPAYMODE_PAID_DRAFT_NO, payMode.getFPAYMODE_PAID_DRAFT_NO());
                values.put(DatabaseHelper.FPAYMODE_PAID_ALLOAMT, payMode.getFPAYMODE_PAID_ALLOAMT());
                values.put(DatabaseHelper.FPAYMODE_PAID_REMAMT, payMode.getFPAYMODE_PAID_REMAMT());

                int cn = cursor.getCount();

                if (cn > 0) {

                    count = dB.update(DatabaseHelper.TABLE_FPAYMODE, values, DatabaseHelper.FPAYMODE_PAID_ID + " =?", new String[]{String.valueOf(payMode.getFPAYMODE_PAID_ID())});

                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FPAYMODE, null, values);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;

    }

    public ArrayList<PayMode> getAllPayModeDetails() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        ArrayList<PayMode> list = new ArrayList<PayMode>();

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMODE;

        try {

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PayMode payMode = new PayMode();

                payMode.setFPAYMODE_PAID_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_ID)));
                payMode.setFPAYMODE_PAID_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_TYPE)));
                payMode.setFPAYMODE_PAID_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_DATE)));
                payMode.setFPAYMODE_PAID_AMOUNT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_AMOUNT)));
                payMode.setFPAYMODE_PAID_REMAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_REMAMT)));
                payMode.setFPAYMODE_PAID_ALLOAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_ALLOAMT)));
                payMode.setFPAYMODE_REF_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_REF_NO)));
                //payMode.setFPAYMODE_PAID_CREDIT_CARD_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_CREDIT_CARD_TYPE)));
//                payMode.setFTRANSODET_BPDISAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_BPDISAMT)));
//                payMode.setFTRANSODET_BSELLPRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_BSELLPRICE)));
//                payMode.setFTRANSODET_BTAXAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_BTAXAMT)));
//                payMode.setFTRANSODET_BTSELLPRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_BTSELLPRICE)));
//                payMode.setFTRANSODET_COSTPRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_COSTPRICE)));
//                payMode.setFTRANSODET_DISAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_DISAMT)));
//                payMode.setFTRANSODET_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_ID)));
//                ordDet.setFTRANSODET_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_IS_ACTIVE)));
//                ordDet.setFTRANSODET_IS_SYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_IS_SYNCED)));
//                ordDet.setFTRANSODET_ITEMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_ITEMCODE)));
//                ordDet.setFTRANSODET_LOCCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_LOCCODE)));
//                ordDet.setFTRANSODET_PDISAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_PDISAMT)));
//                ordDet.setFTRANSODET_PICE_QTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_PICE_QTY)));
//                ordDet.setFTRANSODET_PRILCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_PRILCODE)));
//                ordDet.setFTRANSODET_QTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_QTY)));
//                ordDet.setFTRANSODET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_REFNO)));
//                ordDet.setFTRANSODET_SELLPRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_SELLPRICE)));
//                ordDet.setFTRANSODET_SEQNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_SEQNO)));
//                ordDet.setFTRANSODET_TAXAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_TAXAMT)));
//                ordDet.setFTRANSODET_TAXCOMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_TAXCOMCODE)));
//                ordDet.setFTRANSODET_TSELLPRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_TSELLPRICE)));
//                ordDet.setFTRANSODET_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_TXNDATE)));
//                ordDet.setFTRANSODET_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_TXNTYPE)));
//                ordDet.setFTRANSODET_QOH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_QOH)));
//                ordDet.setFTRANSODET_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_TYPE)));
//                ordDet.setFTRANSODET_SCHDISC(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_DISVALAMT)));
//                ordDet.setFTRANSODET_PRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_PRICE)));
//                ordDet.setFTRANSODET_QTY_SLAB_DISC(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_QTY_SLAB_DISC)));
//                ordDet.setFTRANSODET_ORG_PRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_ORG_PRICE)));

                list.add(payMode);

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

    public ArrayList<PayMode> getPaidDetails() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        ArrayList<PayMode> list = new ArrayList<PayMode>();

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMODE + " WHERE " + DatabaseHelper.FPAYMODE_PAID_ALLOAMT + ">1";

        try {

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PayMode payMode = new PayMode();

                payMode.setFPAYMODE_PAID_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_ID)));
                payMode.setFPAYMODE_REF_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_REF_NO)));
                payMode.setFPAYMODE_PAID_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_TYPE)));
                payMode.setFPAYMODE_PAID_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_DATE)));
                payMode.setFPAYMODE_PAID_AMOUNT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_AMOUNT)));
                payMode.setFPAYMODE_PAID_REMAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_REMAMT)));
                payMode.setFPAYMODE_PAID_ALLOAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_ALLOAMT)));
                payMode.setFPAYMODE_PAID_BANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_BANK)));
                payMode.setFPAYMODE_PAID_CREDIT_CARD_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_CREDIT_CARD_TYPE)));
                payMode.setFPAYMODE_PAID_CREDIT_CARD_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_CREDIT_CARD_NO)));
                payMode.setFPAYMODE_PAID_CARD_EXP_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_CARD_EXP_DATE)));
                payMode.setFPAYMODE_PAID_CHEQUE_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_CHEQUE_DATE)));
                payMode.setFPAYMODE_PAID_CHEQUE_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_CHEQUE_NO)));
                payMode.setFPAYMODE_PAID_SLIP_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_SLIP_NO)));
                payMode.setFPAYMODE_PAID_DRAFT_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_DRAFT_NO)));

                list.add(payMode);

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

    public ArrayList<PayMode> getPayModeByRefNo(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        ArrayList<PayMode> list = new ArrayList<PayMode>();

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMODE + " WHERE " + " RefNo ='" + refNo + "'";

        try {

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PayMode payMode = new PayMode();

                payMode.setFPAYMODE_PAID_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_ID)));
                payMode.setFPAYMODE_REF_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_REF_NO)));
                payMode.setFPAYMODE_PAID_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_TYPE)));
                payMode.setFPAYMODE_PAID_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_DATE)));
                payMode.setFPAYMODE_PAID_AMOUNT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_AMOUNT)));
                payMode.setFPAYMODE_PAID_REMAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_REMAMT)));
                payMode.setFPAYMODE_PAID_ALLOAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_ALLOAMT)));

                list.add(payMode);
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

    public String getPayModeRemAmtByRefNo(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMODE + " WHERE " + " RefNo ='" + refNo + "'";

        try {

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_REMAMT));

            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return "";
    }

    /*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*set RESERVE location records*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-**-*-*-*/

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*/

    public int deleteOrdDetItemCode(String itemCode, String type) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FTRANSODET + " WHERE " + DatabaseHelper.FTRANSODET_ITEMCODE + "='" + itemCode + "' AND type='" + type + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FTRANSODET, DatabaseHelper.FTRANSODET_ITEMCODE + "='" + itemCode + "' AND type='" + type + "'", null);
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

    public void updateAllocateAmt(String id, String alAmt) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FPAYMODE_PAID_ALLOAMT, alAmt);
            dB.update(DatabaseHelper.TABLE_FPAYMODE, values, DatabaseHelper.FPAYMODE_PAID_ID + " =?", new String[]{String.valueOf(id)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public void updateRemainAmount(String id, String reAmt) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FPAYMODE_PAID_REMAMT, reAmt);
            dB.update(DatabaseHelper.TABLE_FPAYMODE, values, DatabaseHelper.FPAYMODE_PAID_ID + " =?", new String[]{String.valueOf(id)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public void updatePaidAmount(String refNo, String paidAmt) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FPAYMODE_PAID_REMAMT, paidAmt);
            values.put(DatabaseHelper.FPAYMODE_PAID_AMOUNT, paidAmt);
            dB.update(DatabaseHelper.TABLE_FPAYMODE, values, DatabaseHelper.FPAYMODE_PAID_REF_NO + " =?", new String[]{String.valueOf(refNo)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public String getTotalAllocAmt()
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int total =0;

        Cursor cursor = null;
        try {

            String selectQuery = "SELECT SUM(AlloAmt) as Total FROM " + DatabaseHelper.TABLE_FPAYMODE + " WHERE " + DatabaseHelper.FPAYMODE_PAID_ALLOAMT + ">1";
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

    public String getTotalRemainAmt()
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int total =0;

        Cursor cursor = null;
        try {

            String selectQuery = "SELECT SUM(RemAmt) as Total FROM " + DatabaseHelper.TABLE_FPAYMODE;
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

    public String getTotalPaidAmt()
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int total =0;

        Cursor cursor = null;
        try {

            String selectQuery = "SELECT PayAmount FROM " + DatabaseHelper.TABLE_FPAYMODE;
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount()>0)
            {
                cursor.moveToFirst();
                for (int i=0; i<cursor.getCount();i++)
                {
                    Double tot = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_AMOUNT)).replaceAll(",",""));
                    total += tot;

                    cursor.moveToNext();
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
        return String.valueOf(total);
    }

    public void updatePayMode(String refNo, String remAmt) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FPAYMODE_PAID_REMAMT, remAmt);
            dB.update(DatabaseHelper.TABLE_FPAYMODE, values, DatabaseHelper.FPAYMODE_PAID_REF_NO + " =?", new String[]{String.valueOf(refNo)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public String getRemAmtByPayRefNo(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMODE + " WHERE " + " RefNo ='" + refNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMODE_PAID_REMAMT));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    public int clearPayMode(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        int result = 0;
        try {
            result = dB.delete(DatabaseHelper.TABLE_FPAYMODE, DatabaseHelper.FPAYMODE_PAID_REF_NO + "=?",
                    new String[] { Refno });

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return result;
    }

    public int clearAllPayModeS() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        int result = 0;
        try {

            result = dB.delete(DatabaseHelper.TABLE_FPAYMODE, null, null);

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return result;
    }
}
