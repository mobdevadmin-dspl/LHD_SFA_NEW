package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.PaymentAllocate;

import java.util.ArrayList;

public class PaymentAllocateDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = " PaymentAllocateDS";

    public PaymentAllocateDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdatePaymentAllocate(ArrayList<PaymentAllocate> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try
        {
            for (PaymentAllocate paymentAllocate : list)
            {
                ContentValues values = new ContentValues();

                String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + DatabaseHelper.FPAYMENT_ALLOCATE_ID + " = '" + paymentAllocate.getFPAYMENT_ALLOCATE_ID() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_ID, paymentAllocate.getFPAYMENT_ALLOCATE_ID());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_REFNO, paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_REFNO, paymentAllocate.getFPAYMENT_ALLOCATE_FDD_REFNO());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_TXN_DATE, paymentAllocate.getFPAYMENT_ALLOCATE_FDD_TXN_DATE());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_PAID_AMT, paymentAllocate.getFPAYMENT_ALLOCATE_FDD_PAID_AMT());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_TOTAL_BAL, paymentAllocate.getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_REF_NO, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_REF_NO());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_MODE, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_MODE());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_DATE, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_DATE());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_AMT, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_AMT());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_REM_AMT, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_REM_AMT());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_ALLO_AMT, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_ALLO_AMT());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_BANK, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_BANK());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_CHEQUE_NO, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_NO());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_SLIP_NO, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_SLIP_NO());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_DRAFT_NO, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_DRAFT_NO());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_COMMON_REFNO, paymentAllocate.getFPAYMENT_ALLOCATE_COMMON_REFNO());
                values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_CHEQUE_DATE, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE());

                int cn = cursor.getCount();

                if (cn > 0) {

                    count = dB.update(DatabaseHelper.TABLE_FPAYMENT_ALLOCATE, values, DatabaseHelper.FPAYMENT_ALLOCATE_ID + " =?", new String[]{String.valueOf(paymentAllocate.getFPAYMENT_ALLOCATE_ID())});

                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FPAYMENT_ALLOCATE, null, values);
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

    public ArrayList<PaymentAllocate> getAllPaidRecords(String refNo, String comRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocFddRefNo ='" + refNo + "'" + " AND " + " AllocComRefNo ='" + comRefNo + "'" ;

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TOTAL_BAL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_TOTAL_BAL)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_ALLO_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_ALLO_AMT)));

                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public String getAllocAmtByRefNo(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocRefNo ='" + refNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_ALLO_AMT));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    public Double getFDDTotBalAmtByAllRefNos(String fddRefNo, String comRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Double fddTotBal = 0.00;
        try {
            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + comRefNo + "'" + " AND " + " AllocFddRefNo ='" + fddRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount()>0)
            {
                cursor.moveToLast();
                for (int i=0; i<cursor.getCount();i++)
                {
//                    Double tot = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_TOTAL_BAL)).replaceAll(",",""));
                    //fddTotBal += tot;

                    fddTotBal = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_TOTAL_BAL)).replaceAll(",",""));

                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return fddTotBal;
    }

    public ArrayList<PaymentAllocate> getAllPaidRecordsByTwoRefNo(String fddRefNo, String payComRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocFddRefNo ='" + fddRefNo + "'" + " AND " + " AllocComRefNo ='" + payComRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_ID)));
                paymentAllocate.setFPAYMENT_ALLOCATE_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_COMMON_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_COMMON_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REF_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_REF_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_MODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_MODE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REM_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_REM_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_ALLO_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_ALLO_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TXN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_TXN_DATE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_PAID_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_PAID_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_DATE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_CHEQUE_DATE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_BANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_BANK)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_CHEQUE_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_SLIP_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_SLIP_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_DRAFT_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_DRAFT_NO)));

                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public ArrayList<PaymentAllocate> getPaidRecordsByCommonRef(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + refNo + "'" + " GROUP BY " + DatabaseHelper.FPAYMENT_ALLOCATE_FDD_REFNO;

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_PAID_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_PAID_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TOTAL_BAL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_TOTAL_BAL)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TXN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_TXN_DATE)));

                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public ArrayList<PaymentAllocate> getPaidModesByCommonRef(String comRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + comRefNo + "'" + " GROUP BY " + DatabaseHelper.FPAYMENT_ALLOCATE_PAY_REF_NO;

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_MODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_MODE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_CHEQUE_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_DATE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_CHEQUE_DATE)));

                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public ArrayList<PaymentAllocate> getPaidAllRecordsByCommonRef(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + refNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_COMMON_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_COMMON_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REF_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_REF_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_MODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_MODE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REM_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_REM_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_ALLO_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_ALLO_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_PAID_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_PAID_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TOTAL_BAL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_TOTAL_BAL)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TXN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_TXN_DATE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_CHEQUE_DATE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_CHEQUE_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_BANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_BANK)));


                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public ArrayList<PaymentAllocate> getDueAmtByCommonRef(String refNo, String comRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + comRefNo + "'" + " AND " + " AllocFddRefNo ='" + refNo + "'" ;

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext())
            {
                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TOTAL_BAL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_TOTAL_BAL)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_ALLO_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_ALLO_AMT)));

                list.add(paymentAllocate);
            }

            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public void updateAllocAmount(String allocRefNo, String allocAmt) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_ALLO_AMT, allocAmt);
//            values.put(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_PAID_AMT, allocAmt);
            dB.update(DatabaseHelper.TABLE_FPAYMENT_ALLOCATE, values, DatabaseHelper.FPAYMENT_ALLOCATE_REFNO + " =?", new String[]{String.valueOf(allocRefNo)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public void updateRemAmount(String refNo, String remAmt) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_REM_AMT, remAmt);
            dB.update(DatabaseHelper.TABLE_FPAYMENT_ALLOCATE, values, DatabaseHelper.FPAYMENT_ALLOCATE_REFNO + " =?", new String[]{String.valueOf(refNo)});

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
            values.put(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_PAID_AMT, paidAmt);
            dB.update(DatabaseHelper.TABLE_FPAYMENT_ALLOCATE, values, DatabaseHelper.FPAYMENT_ALLOCATE_FDD_REFNO + " =?", new String[]{String.valueOf(refNo)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public String getCurrentRemAmt(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocFddRefNo ='" + refNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_REM_AMT));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    public String getPayRefNoByAllocRefNo(String allocRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocRefNo ='" + allocRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_REF_NO));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    public String getFddRefNoByAllocRefNo(String allocRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocRefNo ='" + allocRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_REFNO));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    public String getRemAmtByAllocRefNo(String allocRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocRefNo ='" + allocRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_REM_AMT));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    public String getTotalPaidAmtByComRefNo(String comRefNo)
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int total =0;

        Cursor cursor = null;
        try {

            String selectQuery = "SELECT AllocPayAllocAmt FROM " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + comRefNo + "'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount()>0)
            {
                cursor.moveToFirst();
                for (int i=0; i<cursor.getCount();i++)
                {
                    Double tot = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_ALLO_AMT)).replaceAll(",",""));
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

    public ArrayList<PaymentAllocate> getRefNoByCommonRef(String comRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + comRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_REFNO)));

                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public ArrayList<PaymentAllocate> getPayRefNoByCommonRef(String comRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + DatabaseHelper.TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + comRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REF_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_PAY_REF_NO)));

                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public int clearPaymentAlloc(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        int result = 0;
        try {
            result = dB.delete(DatabaseHelper.TABLE_FPAYMENT_ALLOCATE, DatabaseHelper.FPAYMENT_ALLOCATE_REFNO + "=?",
                    new String[] { Refno });

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return result;
    }
}
