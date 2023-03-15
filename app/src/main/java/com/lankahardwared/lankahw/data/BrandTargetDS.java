package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.BrandTarget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BrandTargetDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;

    public BrandTargetDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateBrandTarget(ArrayList<BrandTarget> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (BrandTarget brand : list) {

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FBRANDTARGET_BRANDCODE, brand.getBRANDTARGET_BRANDCODE());
                values.put(DatabaseHelper.FBRANDTARGET_COSTCODE, brand.getBRANDTARGET_COSTCODE());
                values.put(DatabaseHelper.FBRANDTARGET_ID, brand.getBRANDTARGET_ID());
                values.put(DatabaseHelper.FBRANDTARGET_TARGET, brand.getBRANDTARGET_TARGET());
                count = (int) dB.insert(DatabaseHelper.TABLE_FBRANDTARGET, null, values);

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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*/

    public void deleteAll() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        dB.delete(DatabaseHelper.TABLE_FBRANDTARGET, null, null);
        dB.close();
    }

    public String getRepTarget(String costCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("SELECT sum(Target) as target from fbrandtarget where CostCode='" + costCode + "'", null);

        while (cursor.moveToNext()) {
            return String.valueOf(cursor.getInt(cursor.getColumnIndex("target")));
        }

        cursor.close();
        dB.close();
        return null;

    }


    /*get brand wise sold quantity ,target  of current month*/
    public ArrayList<String[]> getBrandTargetAchievement(String costCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<String[]> list = new ArrayList<String[]>();

        int iYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int iMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        try {
            Cursor cursor = dB.rawQuery("SELECT BrandCode,Target FROM fbrandtarget WHERE CostCode='" + costCode + "'", null);

            while (cursor.moveToNext()) {

                String[] arr = new String[4];

                String brandCode = cursor.getString(cursor.getColumnIndex("BrandCode"));
                int dTarget = cursor.getInt(cursor.getColumnIndex("Target"));

                arr[0] = brandCode;
                arr[1] = String.valueOf(dTarget);

                Cursor cursor1 = dB.rawQuery("select sum(a.qty) as totqty from finvdet a, fitem b,finvhed c where a.itemcode=b.itemcode and b.brandcode='" + arr[0] + "' and c.costcode='" + costCode + "' and c.refno=a.refno AND c.txndate LIKE '" + iYear + "-" + String.format("%02d", iMonth) + "-_%'", null);

                while (cursor1.moveToNext()) {
                    arr[2] = cursor1.getString(cursor1.getColumnIndex("totqty"));
                    arr[3] = String.valueOf((int) (dTarget - Double.parseDouble(arr[2])));
                }

                list.add(arr);
                cursor1.close();
            }

            cursor.close();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }

        return list;

    }

}
