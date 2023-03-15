package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.CrComb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CrCombDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbeHelper;
    private String TAG = "CrCombDS";

    public CrCombDS(Context context) {
        this.context = context;
        dbeHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbeHelper.getWritableDatabase();
    }

    public int createOrUpdateCrCombDS(ArrayList<CrComb> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (CrComb crComb : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FCRCOMB + " WHERE " + DatabaseHelper.FCRCOMB_CODE + " ='" + crComb.getFCRCOMB_CODE() + "' AND " + DatabaseHelper.FCRCOMB_DEBCODE + " ='" + crComb.getFCRCOMB_DEBCODE() + "' AND " + DatabaseHelper.FCRCOMB_GROUPCODE + " ='" + crComb.getFCRCOMB_GROUPCODE() + "' AND " + DatabaseHelper.FCRCOMB_REPCODE + " ='" + crComb.getFCRCOMB_REPCODE() + "' AND " + DatabaseHelper.FCRCOMB_TERMCODE + " ='" + crComb.getFCRCOMB_TERMCODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FCRCOMB_CHKCRDLMT, crComb.getFCRCOMB_CHKCRDLMT());
                values.put(DatabaseHelper.FCRCOMB_CHKCRDPRD, crComb.getFCRCOMB_CHKCRDPRD());
                values.put(DatabaseHelper.FCRCOMB_CODE, crComb.getFCRCOMB_CODE());
                values.put(DatabaseHelper.FCRCOMB_CRD_PERIOD, crComb.getFCRCOMB_CRD_PERIOD());
                values.put(DatabaseHelper.FCRCOMB_CRDLIMIT, crComb.getFCRCOMB_CRDLIMIT());
                values.put(DatabaseHelper.FCRCOMB_DEBCODE, crComb.getFCRCOMB_DEBCODE());
                values.put(DatabaseHelper.FCRCOMB_GROUPCODE, crComb.getFCRCOMB_GROUPCODE());
                values.put(DatabaseHelper.FCRCOMB_REPCODE, crComb.getFCRCOMB_REPCODE());
                values.put(DatabaseHelper.FCRCOMB_TERMCODE, crComb.getFCRCOMB_TERMCODE());
                values.put(DatabaseHelper.FCRCOMB_COM_DIS, crComb.getFCRCOMB_COM_DIS());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FCRCOMB, values, DatabaseHelper.FCRCOMB_CODE + " =? AND " + DatabaseHelper.FCRCOMB_DEBCODE + " =? AND " + DatabaseHelper.FCRCOMB_GROUPCODE + " =? AND " + DatabaseHelper.FCRCOMB_REPCODE + " =? AND " + DatabaseHelper.FCRCOMB_TERMCODE, new String[]{String.valueOf(crComb.getFCRCOMB_CODE()), String.valueOf(crComb.getFCRCOMB_DEBCODE()), String.valueOf(crComb.getFCRCOMB_GROUPCODE()), String.valueOf(crComb.getFCRCOMB_REPCODE()), String.valueOf(crComb.getFCRCOMB_TERMCODE())});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FCRCOMB, null, values);
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public List<String> getCurrentGroupCode(String repCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        List<String> list = new ArrayList<String>();

        String selectQuery = "SELECT " + DatabaseHelper.FCRCOMB_GROUPCODE + " FROM " + DatabaseHelper.TABLE_FCRCOMB + " Where " + DatabaseHelper.FCRCOMB_REPCODE + " ='" + repCode + "' group by groupcode";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            list.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FCRCOMB_GROUPCODE)));

        }

        return list;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public HashMap<String, String> getTermCodeByCode(String code, String groupCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        HashMap<String, String> value = new HashMap<String, String>();
        value.put("Code", "None");
        value.put("Disc", "0");

        String selectQuery = "select t.termdisper, t.termcode   FROM fterms t,  fCRComb c where c.debcode='" + code + "' AND c.groupcode='" + groupCode + "' AND t.termcode=c.termcode";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            value.put("Code", cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTERMS_CODE)));

            if (cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTERMS_DISPER)) == null)
                value.put("Disc", "0");
            else
                value.put("Disc", cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTERMS_DISPER)));

            return value;
        }

        return value;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public CrComb getDataByDebCode(String debCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("SELECT * FROM fCRComb WHERE debcode='" + debCode + "'", null);

        CrComb comb = new CrComb();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                comb.setFCRCOMB_CHKCRDLMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FCRCOMB_CHKCRDLMT)));
                comb.setFCRCOMB_CHKCRDPRD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FCRCOMB_CHKCRDPRD)));
                comb.setFCRCOMB_CRD_PERIOD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FCRCOMB_CRD_PERIOD)));
                comb.setFCRCOMB_CRDLIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FCRCOMB_CRDLIMIT)));

            }
            return comb;
        }

        cursor.close();
        dB.close();
        return null;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int deleteAll() {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FCRCOMB, null);
            if (cursor.getCount() > 0) {
                dB.delete(DatabaseHelper.TABLE_FCRCOMB, null, null);
            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            cursor.close();
            dB.close();
        }

        return count;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getCompanyDiscount(String debCode, String groupcode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("SELECT ComDis FROM " + DatabaseHelper.TABLE_FCRCOMB + " WHERE DebCode='" + debCode + "' AND GroupCode='" + groupcode + "'", null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FCRCOMB_COM_DIS));
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            cursor.close();
            dB.close();
        }
        return "0.00";
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public double getCreditLimitbyDebtor(String debCode, String groupCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("SELECT * FROM fCRComb WHERE debcode='" + debCode + "' AND groupcode='" + groupCode + "'", null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                return Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FCRCOMB_CRDLIMIT)));
            }
        }

        cursor.close();
        dB.close();
        return 0.00;
    }

}
