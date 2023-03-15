package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.lankahardwared.lankahw.model.ItemPri;

import java.util.ArrayList;

public class ItemPriDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "ItemPriDS";

    public ItemPriDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateItemPri(ArrayList<ItemPri> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (ItemPri pri : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FITEMPRI + " WHERE " + DatabaseHelper.FITEMPRI_ITEM_CODE + "='" + pri.getFITEMPRI_ITEM_CODE() + "' AND " + DatabaseHelper.FITEMPRI_PRIL_CODE + "='" + pri.getFITEMPRI_PRIL_CODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(dbHelper.FITEMPRI_ADD_MACH, pri.getFITEMPRI_ADD_MACH());
                values.put(dbHelper.FITEMPRI_ADD_USER, pri.getFITEMPRI_ADD_USER());
                values.put(dbHelper.FITEMPRI_ITEM_CODE, pri.getFITEMPRI_ITEM_CODE());
                values.put(dbHelper.FITEMPRI_PRICE, pri.getFITEMPRI_PRICE());
                values.put(dbHelper.FITEMPRI_PRIL_CODE, pri.getFITEMPRI_PRIL_CODE());
                values.put(dbHelper.FITEMPRI_SKU_CODE, pri.getFITEMPRI_SKU_CODE());
                values.put(dbHelper.FITEMPRI_TXN_MACH, pri.getFITEMPRI_TXN_MACH());
                values.put(dbHelper.FITEMPRI_TXN_USER, pri.getFITEMPRI_TXN_USER());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FITEMPRI, values, DatabaseHelper.FITEMPRI_ITEM_CODE + "=? AND " + DatabaseHelper.FITEMPRI_PRIL_CODE + "=?", new String[]{pri.getFITEMPRI_ITEM_CODE().toString(), pri.getFITEMPRI_PRIL_CODE().toString()});
                    Log.v("FITEMPRI : ", "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FITEMPRI, null, values);
                    Log.v("FITEMPRI : ", "Inserted " + count);
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void InsertOrReplaceItemPri(ArrayList<ItemPri> list) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + DatabaseHelper.TABLE_FITEMPRI + " (AddMach,AddUser,ItemCode,Price,PrilCode,TxnMach,Txnuser) VALUES (?,?,?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (ItemPri itemPri : list) {

                stmt.bindString(1, itemPri.getFITEMPRI_ADD_MACH());
                stmt.bindString(2, itemPri.getFITEMPRI_ADD_USER());
                stmt.bindString(3, itemPri.getFITEMPRI_ITEM_CODE());
                stmt.bindString(4, itemPri.getFITEMPRI_PRICE());
                stmt.bindString(5, itemPri.getFITEMPRI_PRIL_CODE());
                stmt.bindString(6, itemPri.getFITEMPRI_TXN_MACH());
                stmt.bindString(7, itemPri.getFITEMPRI_TXN_USER());

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
	
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int deleteAllItemPri() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FITEMPRI, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FITEMPRI, null, null);
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
//#^^
    public String getProductPriceByCode(String code, String prilcode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FITEMPRI + " WHERE " + DatabaseHelper.FITEMPRI_ITEM_CODE + "='" + code + "' AND " + DatabaseHelper.FITEMPRI_PRIL_CODE + "='" + prilcode + "'";


            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMPRI_PRICE));

            }
        }catch (Exception e) {

               e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                dB.close();
            }
        return "";

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getPrilCodeByItemCode(String code) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FITEMPRI + " WHERE " + DatabaseHelper.FITEMPRI_ITEM_CODE + "='" + code + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMPRI_PRIL_CODE));

        }
        return "";

    }

    public String getProductPriceByCode(String code) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FITEMPRI + " WHERE " + dbHelper.FITEMPRI_ITEM_CODE + "='" + code + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(dbHelper.FITEMPRI_PRICE));

        }
        return "";

    }

}
