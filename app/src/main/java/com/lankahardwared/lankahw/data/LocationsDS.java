package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.Locations;

import java.util.ArrayList;

import static com.lankahardwared.lankahw.data.DatabaseHelper.FLOCATIONS_LOC_CODE;
import static com.lankahardwared.lankahw.data.DatabaseHelper.FLOCATIONS_LOC_NAME;

public class LocationsDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";

    public LocationsDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateFLocations(ArrayList<Locations> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (Locations locations : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FLOCATIONS + " WHERE " + FLOCATIONS_LOC_CODE + "='" + locations.getFLOCATIONS_LOC_CODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FLOCATIONS_ADD_MACH, locations.getFLOCATIONS_ADD_MACH());
                values.put(DatabaseHelper.FLOCATIONS_ADD_USER, locations.getFLOCATIONS_ADD_USER());
                values.put(FLOCATIONS_LOC_CODE, locations.getFLOCATIONS_LOC_CODE());
                values.put(FLOCATIONS_LOC_NAME, locations.getFLOCATIONS_LOC_NAME());
                values.put(DatabaseHelper.FLOCATIONS_LOC_T_CODE, locations.getFLOCATIONS_LOC_T_CODE());
                values.put(DatabaseHelper.FLOCATIONS_REP_CODE, locations.getFLOCATIONS_REP_CODE());

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FLOCATIONS, values, FLOCATIONS_LOC_CODE + "=?", new String[]{locations.getFLOCATIONS_LOC_CODE().toString()});
                    Log.v("TABLE FLOCATIONS : ", "Updated");
                } else {
                    count = (int) dB.insert(DatabaseHelper.TABLE_FLOCATIONS, null, values);
                    Log.v("TABLE FLOCATIONS : ", "Inserted " + count);
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

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<String> getlocDetails(String loctcode, String searchword) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<String> Itemname = new ArrayList<String>();

        String selectQuery = "select LocName,LocCode from fLocations where LoctCode = '" + loctcode + "' AND ( LocCode LIKE '%" + searchword + "%' OR LocName LIKE '%" + searchword + "%')";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            String Itemname1 = cursor.getString(0) + "-:-" + cursor.getString(1);
            Itemname.add(Itemname1);

        }

        return Itemname;
    }


    public ArrayList<Locations> getAllLocDetails() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Locations> locDetailsList = new ArrayList<Locations>();

        String selectQuery = "select * from fLocations";
        // String selectQuery = "select * from fLocations where itemcode_pre || itemname_pre LIKE '%\" + newText + \"%' ";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);


        while (cursor.moveToNext()) {
            Locations lc = new Locations();
//            String Itemname1 = cursor.getString(3) + "-:-" + cursor.getString(4);
            lc.setFLOCATIONS_LOC_CODE(cursor.getString(3));
            lc.setFLOCATIONS_LOC_NAME(cursor.getString(4));
            locDetailsList.add(lc);

        }

        return locDetailsList;
    }

    public ArrayList<Locations> getAllLocDetails(String search) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Locations> locDetailsList = new ArrayList<Locations>();

        // String selectQuery = "select * from fLocations";
        String selectQuery = "select * from fLocations where LocCode || LocName LIKE '%" + search + "%' ";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);


        while (cursor.moveToNext()) {
            Locations lc = new Locations();
//            String Itemname1 = cursor.getString(3) + "-:-" + cursor.getString(4);
            lc.setFLOCATIONS_LOC_CODE(cursor.getString(3));
            lc.setFLOCATIONS_LOC_NAME(cursor.getString(4));
            locDetailsList.add(lc);

        }

        return locDetailsList;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*/

    public String GetLocCodebyRepCode(String RepCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String LocCode = null;
        try {
            Cursor cursor = dB.rawQuery("SELECT LocCode FROM fLocations WHERE RepCode = '" + RepCode + "'", null);

            while (cursor.moveToNext()) {

                LocCode = cursor.getString(cursor.getColumnIndex(FLOCATIONS_LOC_CODE));

            }

            cursor.close();

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {

            dB.close();
        }

        return LocCode;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String GetReserveCode(String locCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            Cursor cursor = dB.rawQuery("SELECT LocCode FROM fLocations WHERE LoctCode = '" + locCode + "'", null);

            while (cursor.moveToNext()) {
                return cursor.getString(cursor.getColumnIndex(FLOCATIONS_LOC_CODE));
            }

            cursor.close();

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {

            dB.close();
        }

        return null;
    }





    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*/

    // public ArrayList<Locations> getlocDetails(String loctcode)
    // {
    // if (dB == null)
    // {
    // open();
    // } else if (!dB.isOpen())
    // {
    // open();
    // }
    //
    // ArrayList<Locations> list = new ArrayList<Locations>();
    //
    // String selectQuery =
    // "select LocName,LocCode from fLocation where LoctCode = '"+loctcode+"'";
    //
    // Cursor cursor = dB.rawQuery(selectQuery, null);
    // while(cursor.moveToNext()){
    //
    // Locations Locationsdata=new Locations();
    //
    // Locationsdata.setFLOCATIONS_LOC_NAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FLOCATIONS_LOC_NAME)));
    // Locationsdata.setFLOCATIONS_LOC_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FLOCATIONS_LOC_CODE)));
    //
    //
    // list.addLocationsdatat);
    //
    // }
    //
    // return list;
    // }

    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FLOCATIONS, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FLOCATIONS, null, null);
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

    public String getItemNameByCode(String code) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FLOCATIONS + " WHERE " + FLOCATIONS_LOC_CODE + "='" + code + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(FLOCATIONS_LOC_NAME));

        }

        return "";
    }

    //**********************kaveesha - 2020-08-12*******************************************************
    public ArrayList<String> getLocDetailsByLocCode() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<String> locDetailsList = new ArrayList<String>();

        String selectQuery = "select * from fLocations where LoctCode='LT3'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);


        while (cursor.moveToNext()) {
            String loccode = cursor.getString(3) + "-" + cursor.getString(4);
            locDetailsList.add(loccode);

        }
        return locDetailsList;
    }
}
