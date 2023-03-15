package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.Depohed;
import com.lankahardwared.lankahw.model.mapper.DepositMapper;
import com.lankahardwared.lankahw.model.mapper.VanSalesMapper;

import java.util.ArrayList;

/**
 * Created by Yasith on 3/1/2019.
 */

public class DepositHedDS {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "DepoHedDS ";

    public DepositHedDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }


    public int createOrUpdateDepositHed(ArrayList<Depohed> depohedList) {

        int serverdbID = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (Depohed depohed : depohedList) {

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FBANKDEPOHED_REFNO, depohed.getFBANKDEPOHED_REFNO());
                values.put(DatabaseHelper.FBANKDEPOHED_TXNDATE, depohed.getFBANKDEPOHED_TXNDATE());
                values.put(DatabaseHelper.FBANKDEPOHED_REMARKS, depohed.getFBANKDEPOHED_REMARKS());
                values.put(DatabaseHelper.FBANKDEPOHED_PAYTYPE, depohed.getFBANKDEPOHED_PAYTYPE());
                values.put(DatabaseHelper.FBANKDEPOHED_SLIPNO, depohed.getFBANKDEPOHED_SLIPNO());
                values.put(DatabaseHelper.FBANKDEPOHED_DEPOAMT, depohed.getFBANKDEPOHED_DEPOAMT());
                values.put(DatabaseHelper.FBANKDEPOHED_ADDDATE, depohed.getFBANKDEPOHED_ADDDATE());
                values.put(DatabaseHelper.FBANKDEPOHED_ADDUSER, depohed.getFBANKDEPOHED_ADDUSER());
                values.put(DatabaseHelper.FBANKDEPOHED_ADDMACH, depohed.getFBANKDEPOHED_ADDMACH());
                values.put(DatabaseHelper.FBANKDEPOHED_ISACTIVE,depohed.getFBANKDEPOHED_ISACTIVE());
                values.put(DatabaseHelper.FBANKDEPOHED_ISSYNCED,depohed.getFBANKDEPOHED_ISSYNCED());

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.FBANKDEPOHED + " WHERE " + DatabaseHelper.FBANKDEPOHED_REFNO + " = '" + depohed.getFBANKDEPOHED_REFNO() + "'", null);

                if (cursor.getCount() > 0) {
                    serverdbID = (int) dB.update(DatabaseHelper.FBANKDEPOHED, values, DatabaseHelper.FBANKDEPOHED_REFNO + " =?", new String[]{String.valueOf(depohed.getFBANKDEPOHED_REFNO())});
                    Log.v(TAG, " Record updated");
                } else {
                    serverdbID = (int) dB.insert(DatabaseHelper.FBANKDEPOHED, null, values);
                    Log.v(TAG, " Record Inserted " + serverdbID);
                }
                cursor.close();
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }
        return serverdbID;

    }


    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<Depohed> getAllUnsyncedDepositHed(String newText, String uploaded) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Depohed> list = new ArrayList<Depohed>();
        Cursor cursor = null;
        try {

            String selectQuery;

            if (uploaded.equals("U"))
                selectQuery = "select * from '"+dbHelper.FBANKDEPOHED+"' where isActive='0' AND isSynced ='1' and RefNo like '%" + newText + "%';";
            else
                selectQuery = "select * from '"+dbHelper.FBANKDEPOHED+"' where isActive='0' AND isSynced ='0' and RefNo like '%" + newText + "%';";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                Depohed depoHed = new Depohed();


                depoHed.setFBANKDEPOHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_REFNO)));
                depoHed.setFBANKDEPOHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_TXNDATE)));
                depoHed.setFBANKDEPOHED_DEPOAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_DEPOAMT)));
                depoHed.setFBANKDEPOHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_PAYTYPE)));


                list.add(depoHed);

            }

        } catch (Exception e) {
            Log.v("Erorr :- ", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return list;
    }


    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Reset deposit headers-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int resetData(String refno)
    {
        int result = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        String locCode = "";

        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.FBANKDEPOHED + " WHERE " + DatabaseHelper.FBANKDEPOHED_REFNO + "='" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                result = dB.delete(DatabaseHelper.FBANKDEPOHED, DatabaseHelper.FBANKDEPOHED_REFNO + "='" + refno + "'", null);
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return result;
    }


    public ArrayList<DepositMapper> getAllUnsynced() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<DepositMapper> list = new ArrayList<DepositMapper>();

        String selectQuery = "select * from " + DatabaseHelper.FBANKDEPOHED + " Where " + DatabaseHelper.FBANKDEPOHED_ISACTIVE + "='0' AND " + DatabaseHelper.FBANKDEPOHED_ISSYNCED + "='0'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

        while (cursor.moveToNext()) {

            DepositMapper depositMapper = new DepositMapper();
            depositMapper.setNextNumVal(new CompanyBranchDS(context).getCurrentNextNumVal(context.getResources().getString(R.string.DepoNumVal)));
            depositMapper.setDistDB(localSP.getString("Dist_DB", "").toString());
            depositMapper.setConsoleDB(localSP.getString("Console_DB", "").toString());

            depositMapper.setFBANKDEPOHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_REFNO)));
            depositMapper.setFBANKDEPOHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_TXNDATE)));
            depositMapper.setFBANKDEPOHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_PAYTYPE)));
            depositMapper.setFBANKDEPOHED_SLIPNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_SLIPNO)));
            depositMapper.setFBANKDEPOHED_DEPOAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_DEPOAMT)));
            depositMapper.setFBANKDEPOHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_REMARKS)));
            depositMapper.setFBANKDEPOHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_ADDDATE)));
            depositMapper.setFBANKDEPOHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_ADDUSER)));
            depositMapper.setFBANKDEPOHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_ADDMACH)));

            String RefNo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FBANKDEPOHED_REFNO));

            depositMapper.setFbankdepodetList(new DepositDetDS(context).getAllDetByRefNo(RefNo));

            list.add(depositMapper);

        }

        cursor.close();
        dB.close();
        return list;
    }



    public int updateIsSynced(DepositMapper mapper) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.FBANKDEPOHED_ISSYNCED, "1");

            if (mapper.getIS_SYNCED()) {
                count = dB.update(DatabaseHelper.FBANKDEPOHED, values, DatabaseHelper.FBANKDEPOHED_REFNO + " =?", new String[]{String.valueOf(mapper.getFBANKDEPOHED_REFNO())});
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
