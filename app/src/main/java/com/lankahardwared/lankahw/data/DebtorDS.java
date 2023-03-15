package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.lankahardwared.lankahw.model.Debtor;

import java.util.ArrayList;

public class DebtorDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "DebtorDS ";
    public static SharedPreferences localSP;
    public static final String SETTINGS = "SETTINGS";
    public DebtorDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateDebtor(ArrayList<Debtor> debtors) {

        int serverdbID = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (Debtor debtor : debtors) {

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.FDEBTOR_CODE, debtor.getFDEBTOR_CODE());
                values.put(DatabaseHelper.FDEBTOR_NAME, debtor.getFDEBTOR_NAME());
                values.put(DatabaseHelper.FDEBTOR_ADD1, debtor.getFDEBTOR_ADD1());
                values.put(DatabaseHelper.FDEBTOR_ADD2, debtor.getFDEBTOR_ADD2());
                values.put(DatabaseHelper.FDEBTOR_ADD3, debtor.getFDEBTOR_ADD3());
                values.put(DatabaseHelper.FDEBTOR_TELE, debtor.getFDEBTOR_TELE());
                values.put(DatabaseHelper.FDEBTOR_MOB, debtor.getFDEBTOR_MOB());
                values.put(DatabaseHelper.FDEBTOR_EMAIL, debtor.getFDEBTOR_EMAIL());
                values.put(DatabaseHelper.FDEBTOR_CREATEDATE, debtor.getFDEBTOR_CREATEDATE());
                values.put(DatabaseHelper.FDEBTOR_REM_DIS, debtor.getFDEBTOR_REM_DIS());
                values.put(DatabaseHelper.FDEBTOR_TOWN_CODE, debtor.getFDEBTOR_TOWN_CODE());
                values.put(DatabaseHelper.FDEBTOR_AREA_CODE, debtor.getFDEBTOR_AREA_CODE());
                values.put(DatabaseHelper.FDEBTOR_DEB_CAT_CODE, debtor.getFDEBTOR_DEB_CAT_CODE());
                values.put(DatabaseHelper.FDEBTOR_DBGR_CODE, debtor.getFDEBTOR_DBGR_CODE());
                values.put(DatabaseHelper.FDEBTOR_DEB_CLS_CODE, debtor.getFDEBTOR_DEB_CLS_CODE());
                values.put(DatabaseHelper.FDEBTOR_STATUS, debtor.getFDEBTOR_STATUS());
                values.put(DatabaseHelper.FDEBTOR_LYLTY, debtor.getFDEBTOR_LYLTY());
                values.put(DatabaseHelper.FDEBTOR_DEAL_CODE, debtor.getFDEBTOR_DEAL_CODE());
                values.put(DatabaseHelper.FDEBTOR_ADD_USER, debtor.getFDEBTOR_ADD_USER());
                values.put(DatabaseHelper.FDEBTOR_ADD_DATE_DEB, debtor.getFDEBTOR_ADD_DATE_DEB());
                values.put(DatabaseHelper.FDEBTOR_ADD_MACH, debtor.getFDEBTOR_ADD_MACH());
                values.put(DatabaseHelper.FDEBTOR_RECORD_ID, debtor.getFDEBTOR_RECORD_ID());
                values.put(DatabaseHelper.FDEBTOR_TIME_STAMP, debtor.getFDEBTOR_TIME_STAMP());
                values.put(DatabaseHelper.FDEBTOR_CRD_PERIOD, debtor.getFDEBTOR_CRD_PERIOD());
                values.put(DatabaseHelper.FDEBTOR_CHK_CRD_PRD, debtor.getFDEBTOR_CHK_CRD_PRD());
                values.put(DatabaseHelper.FDEBTOR_CRD_LIMIT, debtor.getFDEBTOR_CRD_LIMIT());
                values.put(DatabaseHelper.FDEBTOR_CHK_CRD_LIMIT, debtor.getFDEBTOR_CHK_CRD_LIMIT());
                values.put(DatabaseHelper.FDEBTOR_REP_CODE, debtor.getFDEBTOR_REPCODE());
                values.put(DatabaseHelper.FDEBTOR_RANK_CODE, debtor.getFDEBTOR_RANK_CODE());
                values.put(DatabaseHelper.FDEBTOR_TRAN_DATE, debtor.getFDEBTOR_TRAN_DATE());
                values.put(DatabaseHelper.FDEBTOR_TRAN_BATCH, debtor.getFDEBTOR_TRAN_BATCH());
                values.put(DatabaseHelper.FDEBTOR_SUMMARY, debtor.getFDEBTOR_SUMMARY());
                values.put(DatabaseHelper.FDEBTOR_OUT_DIS, debtor.getFDEBTOR_OUT_DIS());
                values.put(DatabaseHelper.FDEBTOR_DEB_FAX, debtor.getFDEBTOR_DEB_FAX());
                values.put(DatabaseHelper.FDEBTOR_DEB_WEB, debtor.getFDEBTOR_DEB_WEB());
                values.put(DatabaseHelper.FDEBTOR_DEBCT_NAM, debtor.getFDEBTOR_DEBCT_NAM());
                values.put(DatabaseHelper.FDEBTOR_DEBCT_ADD1, debtor.getFDEBTOR_DEBCT_ADD1());
                values.put(DatabaseHelper.FDEBTOR_DEBCT_ADD2, debtor.getFDEBTOR_DEBCT_ADD2());
                values.put(DatabaseHelper.FDEBTOR_DEBCT_ADD3, debtor.getFDEBTOR_DEBCT_ADD3());
                values.put(DatabaseHelper.FDEBTOR_DEBCT_TELE, debtor.getFDEBTOR_DEBCT_TELE());
                values.put(DatabaseHelper.FDEBTOR_DEBCT_FAX, debtor.getFDEBTOR_DEBCT_FAX());
                values.put(DatabaseHelper.FDEBTOR_DEBCT_EMAIL, debtor.getFDEBTOR_EMAIL());
                values.put(DatabaseHelper.FDEBTOR_DEL_PERSN, debtor.getFDEBTOR_DEL_PERSN());
                values.put(DatabaseHelper.FDEBTOR_DEL_ADD1, debtor.getFDEBTOR_DEL_ADD1());
                values.put(DatabaseHelper.FDEBTOR_DEL_ADD2, debtor.getFDEBTOR_DEL_ADD1());
                values.put(DatabaseHelper.FDEBTOR_DEL_ADD3, debtor.getFDEBTOR_DEL_ADD3());
                values.put(DatabaseHelper.FDEBTOR_DEL_TELE, debtor.getFDEBTOR_DEL_TELE());
                values.put(DatabaseHelper.FDEBTOR_DEL_FAX, debtor.getFDEBTOR_DEL_FAX());
                values.put(DatabaseHelper.FDEBTOR_DEL_EMAIL, debtor.getFDEBTOR_DEL_EMAIL());
                values.put(DatabaseHelper.FDEBTOR_DATE_OFB, debtor.getFDEBTOR_DATE_OFB());
                values.put(DatabaseHelper.FDEBTOR_TAX_REG, debtor.getFDEBTOR_TAX_REG());
                values.put(DatabaseHelper.FDEBTOR_CUSDISPER, debtor.getFDEBTOR_CUSDISPER());
                values.put(DatabaseHelper.FDEBTOR_PRILLCODE, debtor.getFDEBTOR_PRILLCODE());
                values.put(DatabaseHelper.FDEBTOR_CUSDISSTAT, debtor.getFDEBTOR_CUSDISSTAT());
                values.put(DatabaseHelper.FDEBTOR_BUS_RGNO, debtor.getFDEBTOR_BUS_RGNO());
                values.put(DatabaseHelper.FDEBTOR_POSTCODE, debtor.getFDEBTOR_POSTCODE());
                values.put(DatabaseHelper.FDEBTOR_GEN_REMARKS, debtor.getFDEBTOR_GEN_REMARKS());
                values.put(DatabaseHelper.FDEBTOR_BRANCODE, debtor.getFDEBTOR_BRANCODE());
                values.put(DatabaseHelper.FDEBTOR_BANK, debtor.getFDEBTOR_BANK());
                values.put(DatabaseHelper.FDEBTOR_BRANCH, debtor.getFDEBTOR_BRANCH());
                values.put(DatabaseHelper.FDEBTOR_ACCTNO, debtor.getFDEBTOR_ACCTNO());
                values.put(DatabaseHelper.FDEBTOR_CUS_VATNO, debtor.getFDEBTOR_CUS_VATNO());

                values.put(DatabaseHelper.FDEBTOR_LATITUDE, debtor.getFDEBTOR_LATITUDE());
                values.put(DatabaseHelper.FDEBTOR_LONGITUDE, debtor.getFDEBTOR_LONGITUDE());

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FDEBTOR + " WHERE " + DatabaseHelper.FDEBTOR_CODE + " = '" + debtor.getFDEBTOR_CODE() + "'", null);

                if (cursor.getCount() > 0) {
                    dB.update(DatabaseHelper.TABLE_FDEBTOR, values, DatabaseHelper.FDEBTOR_CODE + " =?", new String[]{String.valueOf(debtor.getFDEBTOR_CODE())});
                    Log.v(TAG, " Record updated");
                } else {
                    serverdbID = (int) dB.insert(DatabaseHelper.TABLE_FDEBTOR, null, values);
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
    public ArrayList<Debtor> getAllDebtorsToCordinatesUpdate() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Debtor> list = new ArrayList<Debtor>();
        Cursor cursor = null;
        try {
            String selectQuery = "select * from " + DatabaseHelper.TABLE_FDEBTOR + " where " + DatabaseHelper.FDEBTOR_IS_SYNC + " = '2'";

            cursor = dB.rawQuery(selectQuery, null);
            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
            while (cursor.moveToNext()) {

                Debtor debtor = new Debtor();
                debtor.setConsoleDB(localSP.getString("Console_DB", "").toString());
                debtor.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CODE)));
                debtor.setFDEBTOR_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LATITUDE)));
                debtor.setFDEBTOR_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LONGITUDE)));
                debtor.setFDEBTOR_IS_SYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_IS_SYNC)));

                list.add(debtor);

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
    public void InsertOrReplaceDebtor(ArrayList<Debtor> list) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            // String sql = "INSERT OR REPLACE INTO " +
            // DatabaseHelper.TABLE_FDEBTOR + "
            // (DebCode,DebName,DebAdd1,DebAdd2,DebAdd3,DebTele,DebMob,DebEMail,CretDate,RemDis,TownCode,AreaCode,DebCatCode,DbGrCode,DebClsCode,Status,DebLylty,DealCode,AddUser,AddDateDEB,AddMach,RecordId,timestamp_column,"
            // +
            // "CrdPeriod,ChkCrdPrd,CrdLimit,ChkCrdLmt,RepCode,RankCode,txndate,TranBatch,DebSumary,OutDis,DebFax,DebWeb,DebCTNam,DebCTAdd1,DebCTAdd2,DebCTAdd3,DebCTTele,DebCTFax,DebCTEmail,"
            // +
            // "DelPersn,DelAdd1,DelAdd2,DelAdd3,DelTele,DelFax,DelEmail,DateOfB,TaxReg,CusDIsPer,PrillCode,CusDisStat,BusRgNo,PostCode,GenRemarks,BranCode,Bank,Branch,AcctNo,CusVatNo)
            // " + " VALUES
            // (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String sql = "INSERT OR REPLACE INTO " + DatabaseHelper.TABLE_FDEBTOR + " (DebCode,DebName,DebAdd1,DebAdd2,DebAdd3,DebTele,DebMob,DebEMail,TownCode,AreaCode,DbGrCode,Status,CrdPeriod,ChkCrdPrd,CrdLimit,ChkCrdLmt,RepCode,PrillCode,TaxReg,RankCode,NIC,BisRegNo,Latitude,Longitude) " + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (Debtor debtor : list) {

                stmt.bindString(1, debtor.getFDEBTOR_CODE());
                stmt.bindString(2, debtor.getFDEBTOR_NAME());
                stmt.bindString(3, debtor.getFDEBTOR_ADD1());
                stmt.bindString(4, debtor.getFDEBTOR_ADD2());
                stmt.bindString(5, debtor.getFDEBTOR_ADD3());
                stmt.bindString(6, debtor.getFDEBTOR_TELE());
                stmt.bindString(7, debtor.getFDEBTOR_MOB());
                stmt.bindString(8, debtor.getFDEBTOR_EMAIL());
                stmt.bindString(9, debtor.getFDEBTOR_TOWN_CODE());
                stmt.bindString(10, debtor.getFDEBTOR_AREA_CODE());
                stmt.bindString(11, debtor.getFDEBTOR_DBGR_CODE());
                stmt.bindString(12, debtor.getFDEBTOR_STATUS());
                stmt.bindString(13, debtor.getFDEBTOR_CRD_PERIOD());
                stmt.bindString(14, debtor.getFDEBTOR_CHK_CRD_PRD());
                stmt.bindString(15, debtor.getFDEBTOR_CRD_LIMIT());
                stmt.bindString(16, debtor.getFDEBTOR_CHK_CRD_LIMIT());
                stmt.bindString(17, debtor.getFDEBTOR_REPCODE());
                stmt.bindString(18, debtor.getFDEBTOR_PRILLCODE());
                stmt.bindString(19, debtor.getFDEBTOR_TAX_REG());
                stmt.bindString(20, debtor.getFDEBTOR_RANK_CODE());
                stmt.bindString(21, debtor.getFDEBTOR_NIC());
                stmt.bindString(22, debtor.getFDEBTOR_BIS_REG());
                stmt.bindString(23, debtor.getFDEBTOR_LATITUDE());
                stmt.bindString(24, debtor.getFDEBTOR_LONGITUDE());
                stmt.execute();
                stmt.clearBindings();
                // stmt.bindString(15, debtor.getFDEBTOR_DEB_CLS_CODE());
                // stmt.bindString(10, debtor.getFDEBTOR_REM_DIS());
                // stmt.bindString(13, debtor.getFDEBTOR_DEB_CAT_CODE());
                // stmt.bindString(9, debtor.getFDEBTOR_CREATEDATE());
                // stmt.bindString(17, debtor.getFDEBTOR_LYLTY());
                // stmt.bindString(18, debtor.getFDEBTOR_DEAL_CODE());
                // stmt.bindString(19, debtor.getFDEBTOR_ADD_USER());
                // stmt.bindString(20, debtor.getFDEBTOR_ADD_DATE_DEB());
                // stmt.bindString(21, debtor.getFDEBTOR_ADD_MACH());
                // stmt.bindString(22, debtor.getFDEBTOR_RECORD_ID());
                // stmt.bindString(23, debtor.getFDEBTOR_TIME_STAMP());
                // stmt.bindString(30, debtor.getFDEBTOR_TRAN_DATE());
                // stmt.bindString(31, debtor.getFDEBTOR_TRAN_BATCH());
                // stmt.bindString(32, debtor.getFDEBTOR_SUMMARY());
                // stmt.bindString(33, debtor.getFDEBTOR_OUT_DIS());
                // stmt.bindString(34, debtor.getFDEBTOR_DEB_FAX());
                // stmt.bindString(35, debtor.getFDEBTOR_DEB_WEB());
                // stmt.bindString(36, debtor.getFDEBTOR_DEBCT_NAM());
                // stmt.bindString(37, debtor.getFDEBTOR_DEBCT_ADD1());
                // stmt.bindString(38, debtor.getFDEBTOR_DEBCT_ADD2());
                // stmt.bindString(39, debtor.getFDEBTOR_DEBCT_ADD3());
                // stmt.bindString(40, debtor.getFDEBTOR_DEBCT_TELE());
                // stmt.bindString(41, debtor.getFDEBTOR_DEBCT_FAX());
                // stmt.bindString(42, debtor.getFDEBTOR_DEBCT_EMAIL());
                // stmt.bindString(43, debtor.getFDEBTOR_DEL_PERSN());
                // stmt.bindString(44, debtor.getFDEBTOR_DEL_ADD1());
                // stmt.bindString(45, debtor.getFDEBTOR_DEL_ADD2());
                // stmt.bindString(46, debtor.getFDEBTOR_DEL_ADD3());
                // stmt.bindString(47, debtor.getFDEBTOR_DEL_TELE());
                // stmt.bindString(48, debtor.getFDEBTOR_DEL_FAX());
                // stmt.bindString(49, debtor.getFDEBTOR_DEL_EMAIL());
                // stmt.bindString(50, debtor.getFDEBTOR_DATE_OFB());
                // stmt.bindString(52, debtor.getFDEBTOR_CUSDISPER());
                // stmt.bindString(54, debtor.getFDEBTOR_CUSDISSTAT());
                // stmt.bindString(55, debtor.getFDEBTOR_BUS_RGNO());
                // stmt.bindString(56, debtor.getFDEBTOR_POSTCODE());
                // stmt.bindString(57, debtor.getFDEBTOR_GEN_REMARKS());
                // stmt.bindString(58, debtor.getFDEBTOR_BRANCODE());
                // stmt.bindString(59, debtor.getFDEBTOR_BANK());
                // stmt.bindString(60, debtor.getFDEBTOR_BRANCH());
                // stmt.bindString(61, debtor.getFDEBTOR_ACCTNO());
                // stmt.bindString(62, debtor.getFDEBTOR_CUS_VATNO());


            }

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("<<debtor excep","<<debtor : -> "+e.toString());
        } finally {
            dB.setTransactionSuccessful();
            dB.endTransaction();
            dB.close();
        }

    }
    public int updateIsSynced(Debtor mapper) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.FDEBTOR_IS_SYNC, mapper.getFDEBTOR_IS_SYNCED());

            //if (mapper.getFDEBTOR_IS_SYNCED().equals("2")) {
                count = dB.update(DatabaseHelper.TABLE_FDEBTOR, values, DatabaseHelper.FDEBTOR_CODE + " =?", new String[]{String.valueOf(mapper.getFDEBTOR_CODE())});
           // }

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
    public int updateIsSynced(String debCode, String res) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FDEBTOR_IS_SYNC, res);

                count = dB.update(DatabaseHelper.TABLE_FDEBTOR, values, DatabaseHelper.FDEBTOR_CODE + " =?", new String[]{String.valueOf(debCode)});


        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            dB.close();
        }
        return count;
    }
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public int updateCustomerLocationByCurrentCordinates(String debCode, Double lat, Double lon) {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FDEBTOR_LATITUDE, lat);
            values.put(DatabaseHelper.FDEBTOR_LONGITUDE, lon);
//            values.put(DatabaseHelper.FDEBTOR_IS_CORDINATE_UPDATE, "1");
//            values.put(DatabaseHelper.FDEBTOR_IS_SYNC, "3");
            count = (int) dB.update(DatabaseHelper.TABLE_FDEBTOR, values, DatabaseHelper.FDEBTOR_CODE + " =?", new String[]{String.valueOf(debCode)});
            Log.d("", "");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
        return count;
    }
    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FDEBTOR, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FDEBTOR, null, null);
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<Debtor> getAllCustomers() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Debtor> list = new ArrayList<Debtor>();

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FDEBTOR;

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            Debtor aDebtor = new Debtor();

            aDebtor.setFDEBTOR_ACCTNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ACCTNO)));
            aDebtor.setFDEBTOR_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD1)));
            aDebtor.setFDEBTOR_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD2)));
            aDebtor.setFDEBTOR_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD3)));
            aDebtor.setFDEBTOR_ADD_DATE_DEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_DATE_DEB)));
            aDebtor.setFDEBTOR_ADD_MACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_MACH)));
            aDebtor.setFDEBTOR_ADD_USER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_USER)));
            aDebtor.setFDEBTOR_AREA_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_AREA_CODE)));
            aDebtor.setFDEBTOR_BANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BANK)));
            aDebtor.setFDEBTOR_BRANCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCH)));
            aDebtor.setFDEBTOR_BRANCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCODE)));
            aDebtor.setFDEBTOR_BUS_RGNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BUS_RGNO)));
            aDebtor.setFDEBTOR_CHK_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_LIMIT)));
            aDebtor.setFDEBTOR_CHK_CRD_PRD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_PRD)));
            aDebtor.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CODE)));
            aDebtor.setFDEBTOR_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_LIMIT)));
            aDebtor.setFDEBTOR_CRD_PERIOD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_PERIOD)));
            aDebtor.setFDEBTOR_CREATEDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CREATEDATE)));
            aDebtor.setFDEBTOR_CUS_VATNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUS_VATNO)));
            aDebtor.setFDEBTOR_CUSDISPER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISPER)));
            aDebtor.setFDEBTOR_CUSDISSTAT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISSTAT)));
            aDebtor.setFDEBTOR_DATE_OFB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DATE_OFB)));
            aDebtor.setFDEBTOR_DBGR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DBGR_CODE)));
            aDebtor.setFDEBTOR_DEAL_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEAL_CODE)));
            aDebtor.setFDEBTOR_DEB_CAT_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CAT_CODE)));
            aDebtor.setFDEBTOR_DEB_CLS_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CLS_CODE)));
            aDebtor.setFDEBTOR_DEB_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
            aDebtor.setFDEBTOR_DEB_WEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
            aDebtor.setFDEBTOR_DEBCT_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD1)));
            aDebtor.setFDEBTOR_DEBCT_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD2)));
            aDebtor.setFDEBTOR_DEBCT_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD3)));
            aDebtor.setFDEBTOR_DEBCT_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_EMAIL)));
            aDebtor.setFDEBTOR_DEBCT_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_FAX)));
            aDebtor.setFDEBTOR_DEBCT_NAM(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_NAM)));
            aDebtor.setFDEBTOR_DEBCT_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_TELE)));
            aDebtor.setFDEBTOR_DEL_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD1)));
            aDebtor.setFDEBTOR_DEL_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD2)));
            aDebtor.setFDEBTOR_DEL_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD3)));
            aDebtor.setFDEBTOR_DEL_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_EMAIL)));
            aDebtor.setFDEBTOR_DEL_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_FAX)));
            aDebtor.setFDEBTOR_DEL_PERSN(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_PERSN)));
            aDebtor.setFDEBTOR_DEL_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_TELE)));
            aDebtor.setFDEBTOR_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_EMAIL)));
            aDebtor.setFDEBTOR_GEN_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_GEN_REMARKS)));
            aDebtor.setFDEBTOR_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ID)));
            aDebtor.setFDEBTOR_LYLTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LYLTY)));
            aDebtor.setFDEBTOR_MOB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_MOB)));
            aDebtor.setFDEBTOR_NAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NAME)));
            aDebtor.setFDEBTOR_OUT_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_OUT_DIS)));
            aDebtor.setFDEBTOR_POSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_POSTCODE)));
            aDebtor.setFDEBTOR_PRILLCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_PRILLCODE)));
            aDebtor.setFDEBTOR_RANK_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RANK_CODE)));
            aDebtor.setFDEBTOR_RECORD_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RECORD_ID)));
            aDebtor.setFDEBTOR_REM_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REM_DIS)));
            aDebtor.setFDEBTOR_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REP_CODE)));
            aDebtor.setFDEBTOR_STATUS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_STATUS)));
            aDebtor.setFDEBTOR_SUMMARY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_SUMMARY)));
            aDebtor.setFDEBTOR_TAX_REG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TAX_REG)));
            aDebtor.setFDEBTOR_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TELE)));
            aDebtor.setFDEBTOR_TIME_STAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TIME_STAMP)));
            aDebtor.setFDEBTOR_TOWN_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TOWN_CODE)));
            aDebtor.setFDEBTOR_TRAN_BATCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_BATCH)));
            aDebtor.setFDEBTOR_TRAN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_DATE)));

            list.add(aDebtor);

        }

        return list;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<Debtor> getCustomerByCodeAndName(String newText) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Debtor> list = new ArrayList<Debtor>();

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FDEBTOR + " where debcode || debname like '%" + newText + "%'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            Debtor aDebtor = new Debtor();

            aDebtor.setFDEBTOR_ACCTNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ACCTNO)));
            aDebtor.setFDEBTOR_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD1)));
            aDebtor.setFDEBTOR_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD2)));
            aDebtor.setFDEBTOR_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD3)));
            aDebtor.setFDEBTOR_ADD_DATE_DEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_DATE_DEB)));
            aDebtor.setFDEBTOR_ADD_MACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_MACH)));
            aDebtor.setFDEBTOR_ADD_USER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_USER)));
            aDebtor.setFDEBTOR_AREA_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_AREA_CODE)));
            aDebtor.setFDEBTOR_BANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BANK)));
            aDebtor.setFDEBTOR_BRANCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCH)));
            aDebtor.setFDEBTOR_BRANCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCODE)));
            aDebtor.setFDEBTOR_BUS_RGNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BUS_RGNO)));
            aDebtor.setFDEBTOR_CHK_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_LIMIT)));
            aDebtor.setFDEBTOR_CHK_CRD_PRD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_PRD)));
            aDebtor.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CODE)));
            aDebtor.setFDEBTOR_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_LIMIT)));
            aDebtor.setFDEBTOR_CRD_PERIOD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_PERIOD)));
            aDebtor.setFDEBTOR_CREATEDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CREATEDATE)));
            aDebtor.setFDEBTOR_CUS_VATNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUS_VATNO)));
            aDebtor.setFDEBTOR_CUSDISPER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISPER)));
            aDebtor.setFDEBTOR_CUSDISSTAT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISSTAT)));
            aDebtor.setFDEBTOR_DATE_OFB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DATE_OFB)));
            aDebtor.setFDEBTOR_DBGR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DBGR_CODE)));
            aDebtor.setFDEBTOR_DEAL_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEAL_CODE)));
            aDebtor.setFDEBTOR_DEB_CAT_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CAT_CODE)));
            aDebtor.setFDEBTOR_DEB_CLS_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CLS_CODE)));
            aDebtor.setFDEBTOR_DEB_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
            aDebtor.setFDEBTOR_DEB_WEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
            aDebtor.setFDEBTOR_DEBCT_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD1)));
            aDebtor.setFDEBTOR_DEBCT_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD2)));
            aDebtor.setFDEBTOR_DEBCT_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD3)));
            aDebtor.setFDEBTOR_DEBCT_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_EMAIL)));
            aDebtor.setFDEBTOR_DEBCT_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_FAX)));
            aDebtor.setFDEBTOR_DEBCT_NAM(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_NAM)));
            aDebtor.setFDEBTOR_DEBCT_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_TELE)));
            aDebtor.setFDEBTOR_DEL_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD1)));
            aDebtor.setFDEBTOR_DEL_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD2)));
            aDebtor.setFDEBTOR_DEL_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD3)));
            aDebtor.setFDEBTOR_DEL_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_EMAIL)));
            aDebtor.setFDEBTOR_DEL_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_FAX)));
            aDebtor.setFDEBTOR_DEL_PERSN(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_PERSN)));
            aDebtor.setFDEBTOR_DEL_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_TELE)));
            aDebtor.setFDEBTOR_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_EMAIL)));
            aDebtor.setFDEBTOR_GEN_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_GEN_REMARKS)));
            aDebtor.setFDEBTOR_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ID)));
            aDebtor.setFDEBTOR_LYLTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LYLTY)));
            aDebtor.setFDEBTOR_MOB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_MOB)));
            aDebtor.setFDEBTOR_NAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NAME)));
            aDebtor.setFDEBTOR_OUT_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_OUT_DIS)));
            aDebtor.setFDEBTOR_POSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_POSTCODE)));
            aDebtor.setFDEBTOR_PRILLCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_PRILLCODE)));
            aDebtor.setFDEBTOR_RANK_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RANK_CODE)));
            aDebtor.setFDEBTOR_RECORD_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RECORD_ID)));
            aDebtor.setFDEBTOR_REM_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REM_DIS)));
            aDebtor.setFDEBTOR_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REP_CODE)));
            aDebtor.setFDEBTOR_STATUS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_STATUS)));
            aDebtor.setFDEBTOR_SUMMARY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_SUMMARY)));
            aDebtor.setFDEBTOR_TAX_REG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TAX_REG)));
            aDebtor.setFDEBTOR_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TELE)));
            aDebtor.setFDEBTOR_TIME_STAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TIME_STAMP)));
            aDebtor.setFDEBTOR_TOWN_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TOWN_CODE)));
            aDebtor.setFDEBTOR_TRAN_BATCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_BATCH)));
            aDebtor.setFDEBTOR_TRAN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_DATE)));

            list.add(aDebtor);

        }

        return list;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<Debtor> getRouteCustomers(String RouteCode, String newText) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Debtor> list = new ArrayList<Debtor>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FDEBTOR + " WHERE debcode || debname like '%" + newText + "%'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            Debtor aDebtor = new Debtor();

            aDebtor.setFDEBTOR_ACCTNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ACCTNO)));
            aDebtor.setFDEBTOR_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD1)));
            aDebtor.setFDEBTOR_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD2)));
            aDebtor.setFDEBTOR_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD3)));
            aDebtor.setFDEBTOR_ADD_DATE_DEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_DATE_DEB)));
            aDebtor.setFDEBTOR_ADD_MACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_MACH)));
            aDebtor.setFDEBTOR_ADD_USER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_USER)));
            aDebtor.setFDEBTOR_AREA_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_AREA_CODE)));
            aDebtor.setFDEBTOR_BANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BANK)));
            aDebtor.setFDEBTOR_BRANCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCH)));
            aDebtor.setFDEBTOR_BRANCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCODE)));
            aDebtor.setFDEBTOR_BUS_RGNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BUS_RGNO)));
            aDebtor.setFDEBTOR_CHK_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_LIMIT)));
            aDebtor.setFDEBTOR_CHK_CRD_PRD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_PRD)));
            aDebtor.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CODE)));
            aDebtor.setFDEBTOR_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_LIMIT)));
            aDebtor.setFDEBTOR_CRD_PERIOD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_PERIOD)));

//            aDebtor.setFDEBTOR_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_LIMIT)));
//            aDebtor.setFDEBTOR_CRD_PERIOD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_PERIOD)));
            aDebtor.setFDEBTOR_CREATEDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CREATEDATE)));
            aDebtor.setFDEBTOR_CUS_VATNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUS_VATNO)));
            aDebtor.setFDEBTOR_CUSDISPER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISPER)));
            aDebtor.setFDEBTOR_CUSDISSTAT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISSTAT)));
            aDebtor.setFDEBTOR_DATE_OFB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DATE_OFB)));
            aDebtor.setFDEBTOR_DBGR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DBGR_CODE)));
            aDebtor.setFDEBTOR_DEAL_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEAL_CODE)));
            aDebtor.setFDEBTOR_DEB_CAT_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CAT_CODE)));
            aDebtor.setFDEBTOR_DEB_CLS_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CLS_CODE)));
            aDebtor.setFDEBTOR_DEB_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
            aDebtor.setFDEBTOR_DEB_WEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
            aDebtor.setFDEBTOR_DEBCT_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD1)));
            aDebtor.setFDEBTOR_DEBCT_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD2)));
            aDebtor.setFDEBTOR_DEBCT_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD3)));
            aDebtor.setFDEBTOR_DEBCT_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_EMAIL)));
            aDebtor.setFDEBTOR_DEBCT_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_FAX)));
            aDebtor.setFDEBTOR_DEBCT_NAM(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_NAM)));
            aDebtor.setFDEBTOR_DEBCT_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_TELE)));
            aDebtor.setFDEBTOR_DEL_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD1)));
            aDebtor.setFDEBTOR_DEL_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD2)));
            aDebtor.setFDEBTOR_DEL_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD3)));
            aDebtor.setFDEBTOR_DEL_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_EMAIL)));
            aDebtor.setFDEBTOR_DEL_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_FAX)));
            aDebtor.setFDEBTOR_DEL_PERSN(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_PERSN)));
            aDebtor.setFDEBTOR_DEL_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_TELE)));
            aDebtor.setFDEBTOR_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_EMAIL)));
            aDebtor.setFDEBTOR_GEN_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_GEN_REMARKS)));
            aDebtor.setFDEBTOR_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ID)));
            aDebtor.setFDEBTOR_LYLTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LYLTY)));
            aDebtor.setFDEBTOR_MOB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_MOB)));
            aDebtor.setFDEBTOR_NAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NAME)));
            aDebtor.setFDEBTOR_OUT_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_OUT_DIS)));
            aDebtor.setFDEBTOR_POSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_POSTCODE)));
            aDebtor.setFDEBTOR_PRILLCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_PRILLCODE)));
            aDebtor.setFDEBTOR_RANK_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RANK_CODE)));
            aDebtor.setFDEBTOR_RECORD_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RECORD_ID)));
            aDebtor.setFDEBTOR_REM_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REM_DIS)));
            aDebtor.setFDEBTOR_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REP_CODE)));
            aDebtor.setFDEBTOR_STATUS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_STATUS)));
            aDebtor.setFDEBTOR_SUMMARY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_SUMMARY)));
            aDebtor.setFDEBTOR_TAX_REG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TAX_REG)));
            aDebtor.setFDEBTOR_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TELE)));
            aDebtor.setFDEBTOR_TIME_STAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TIME_STAMP)));
            aDebtor.setFDEBTOR_TOWN_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TOWN_CODE)));
            aDebtor.setFDEBTOR_TRAN_BATCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_BATCH)));
            aDebtor.setFDEBTOR_TRAN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_DATE)));
            aDebtor.setFDEBTOR_NIC(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NIC)));
            aDebtor.setFDEBTOR_BIS_REG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BIS_REG)));
            aDebtor.setFDEBTOR_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LATITUDE)));
            aDebtor.setFDEBTOR_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LONGITUDE)));

            list.add(aDebtor);

        }

        return list;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*nuwan 17/08/2018 *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<Debtor> getCustomersForSelectedRoute(String RepCode, String newText) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Debtor> list = new ArrayList<Debtor>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FDEBTOR + " WHERE debcode || debname like '%" + newText + "%'" + " AND " + DatabaseHelper.FDEBTOR_REP_CODE + " = '" + RepCode + "'" ;

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            Debtor aDebtor = new Debtor();

            aDebtor.setFDEBTOR_ACCTNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ACCTNO)));
            aDebtor.setFDEBTOR_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD1)));
            aDebtor.setFDEBTOR_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD2)));
            aDebtor.setFDEBTOR_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD3)));
            aDebtor.setFDEBTOR_ADD_DATE_DEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_DATE_DEB)));
            aDebtor.setFDEBTOR_ADD_MACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_MACH)));
            aDebtor.setFDEBTOR_ADD_USER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_USER)));
            aDebtor.setFDEBTOR_AREA_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_AREA_CODE)));
            aDebtor.setFDEBTOR_BANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BANK)));
            aDebtor.setFDEBTOR_BRANCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCH)));
            aDebtor.setFDEBTOR_BRANCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCODE)));
            aDebtor.setFDEBTOR_BUS_RGNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BUS_RGNO)));
            aDebtor.setFDEBTOR_CHK_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_LIMIT)));
            aDebtor.setFDEBTOR_CHK_CRD_PRD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_PRD)));
            aDebtor.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CODE)));
            aDebtor.setFDEBTOR_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_LIMIT)));
            aDebtor.setFDEBTOR_CRD_PERIOD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_PERIOD)));

//            aDebtor.setFDEBTOR_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_LIMIT)));
//            aDebtor.setFDEBTOR_CRD_PERIOD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_PERIOD)));
            aDebtor.setFDEBTOR_CREATEDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CREATEDATE)));
            aDebtor.setFDEBTOR_CUS_VATNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUS_VATNO)));
            aDebtor.setFDEBTOR_CUSDISPER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISPER)));
            aDebtor.setFDEBTOR_CUSDISSTAT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISSTAT)));
            aDebtor.setFDEBTOR_DATE_OFB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DATE_OFB)));
            aDebtor.setFDEBTOR_DBGR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DBGR_CODE)));
            aDebtor.setFDEBTOR_DEAL_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEAL_CODE)));
            aDebtor.setFDEBTOR_DEB_CAT_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CAT_CODE)));
            aDebtor.setFDEBTOR_DEB_CLS_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CLS_CODE)));
            aDebtor.setFDEBTOR_DEB_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
            aDebtor.setFDEBTOR_DEB_WEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
            aDebtor.setFDEBTOR_DEBCT_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD1)));
            aDebtor.setFDEBTOR_DEBCT_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD2)));
            aDebtor.setFDEBTOR_DEBCT_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD3)));
            aDebtor.setFDEBTOR_DEBCT_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_EMAIL)));
            aDebtor.setFDEBTOR_DEBCT_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_FAX)));
            aDebtor.setFDEBTOR_DEBCT_NAM(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_NAM)));
            aDebtor.setFDEBTOR_DEBCT_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_TELE)));
            aDebtor.setFDEBTOR_DEL_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD1)));
            aDebtor.setFDEBTOR_DEL_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD2)));
            aDebtor.setFDEBTOR_DEL_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD3)));
            aDebtor.setFDEBTOR_DEL_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_EMAIL)));
            aDebtor.setFDEBTOR_DEL_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_FAX)));
            aDebtor.setFDEBTOR_DEL_PERSN(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_PERSN)));
            aDebtor.setFDEBTOR_DEL_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_TELE)));
            aDebtor.setFDEBTOR_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_EMAIL)));
            aDebtor.setFDEBTOR_GEN_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_GEN_REMARKS)));
            aDebtor.setFDEBTOR_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ID)));
            aDebtor.setFDEBTOR_LYLTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LYLTY)));
            aDebtor.setFDEBTOR_MOB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_MOB)));
            aDebtor.setFDEBTOR_NAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NAME)));
            aDebtor.setFDEBTOR_OUT_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_OUT_DIS)));
            aDebtor.setFDEBTOR_POSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_POSTCODE)));
            aDebtor.setFDEBTOR_PRILLCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_PRILLCODE)));
            aDebtor.setFDEBTOR_RANK_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RANK_CODE)));
            aDebtor.setFDEBTOR_RECORD_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RECORD_ID)));
            aDebtor.setFDEBTOR_REM_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REM_DIS)));
            aDebtor.setFDEBTOR_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REP_CODE)));
            aDebtor.setFDEBTOR_STATUS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_STATUS)));
            aDebtor.setFDEBTOR_SUMMARY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_SUMMARY)));
            aDebtor.setFDEBTOR_TAX_REG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TAX_REG)));
            aDebtor.setFDEBTOR_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TELE)));
            aDebtor.setFDEBTOR_TIME_STAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TIME_STAMP)));
            aDebtor.setFDEBTOR_TOWN_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TOWN_CODE)));
            aDebtor.setFDEBTOR_TRAN_BATCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_BATCH)));
            aDebtor.setFDEBTOR_TRAN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_DATE)));

            list.add(aDebtor);

        }

        return list;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<Debtor> getRouteCustomersByCodeAndName(String RouteCode, String newText) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Debtor> list = new ArrayList<Debtor>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FROUTEDET + " RD, " + DatabaseHelper.TABLE_FDEBTOR + " D WHERE RD." + DatabaseHelper.FROUTEDET_DEB_CODE + "=D." + DatabaseHelper.FDEBTOR_CODE + " AND RD." + DatabaseHelper.FROUTEDET_ROUTE_CODE + "='" + RouteCode + "' AND D." + DatabaseHelper.FDEBTOR_CODE + " || D." + DatabaseHelper.FDEBTOR_NAME + " like '%" + newText + "%'";
        ;

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            Debtor aDebtor = new Debtor();

            aDebtor.setFDEBTOR_ACCTNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ACCTNO)));
            aDebtor.setFDEBTOR_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD1)));
            aDebtor.setFDEBTOR_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD2)));
            aDebtor.setFDEBTOR_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD3)));
            aDebtor.setFDEBTOR_ADD_DATE_DEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_DATE_DEB)));
            aDebtor.setFDEBTOR_ADD_MACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_MACH)));
            aDebtor.setFDEBTOR_ADD_USER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_USER)));
            aDebtor.setFDEBTOR_AREA_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_AREA_CODE)));
            aDebtor.setFDEBTOR_BANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BANK)));
            aDebtor.setFDEBTOR_BRANCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCH)));
            aDebtor.setFDEBTOR_BRANCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCODE)));
            aDebtor.setFDEBTOR_BUS_RGNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BUS_RGNO)));
            aDebtor.setFDEBTOR_CHK_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_LIMIT)));
            aDebtor.setFDEBTOR_CHK_CRD_PRD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_PRD)));
            aDebtor.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CODE)));
            aDebtor.setFDEBTOR_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_LIMIT)));
            aDebtor.setFDEBTOR_CRD_PERIOD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_PERIOD)));
            aDebtor.setFDEBTOR_CREATEDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CREATEDATE)));
            aDebtor.setFDEBTOR_CUS_VATNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUS_VATNO)));
            aDebtor.setFDEBTOR_CUSDISPER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISPER)));
            aDebtor.setFDEBTOR_CUSDISSTAT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISSTAT)));
            aDebtor.setFDEBTOR_DATE_OFB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DATE_OFB)));
            aDebtor.setFDEBTOR_DBGR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DBGR_CODE)));
            aDebtor.setFDEBTOR_DEAL_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEAL_CODE)));
            aDebtor.setFDEBTOR_DEB_CAT_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CAT_CODE)));
            aDebtor.setFDEBTOR_DEB_CLS_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CLS_CODE)));
            aDebtor.setFDEBTOR_DEB_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
            aDebtor.setFDEBTOR_DEB_WEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
            aDebtor.setFDEBTOR_DEBCT_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD1)));
            aDebtor.setFDEBTOR_DEBCT_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD2)));
            aDebtor.setFDEBTOR_DEBCT_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD3)));
            aDebtor.setFDEBTOR_DEBCT_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_EMAIL)));
            aDebtor.setFDEBTOR_DEBCT_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_FAX)));
            aDebtor.setFDEBTOR_DEBCT_NAM(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_NAM)));
            aDebtor.setFDEBTOR_DEBCT_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_TELE)));
            aDebtor.setFDEBTOR_DEL_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD1)));
            aDebtor.setFDEBTOR_DEL_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD2)));
            aDebtor.setFDEBTOR_DEL_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD3)));
            aDebtor.setFDEBTOR_DEL_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_EMAIL)));
            aDebtor.setFDEBTOR_DEL_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_FAX)));
            aDebtor.setFDEBTOR_DEL_PERSN(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_PERSN)));
            aDebtor.setFDEBTOR_DEL_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_TELE)));
            aDebtor.setFDEBTOR_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_EMAIL)));
            aDebtor.setFDEBTOR_GEN_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_GEN_REMARKS)));
            aDebtor.setFDEBTOR_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ID)));
            aDebtor.setFDEBTOR_LYLTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LYLTY)));
            aDebtor.setFDEBTOR_MOB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_MOB)));
            aDebtor.setFDEBTOR_NAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NAME)));
            aDebtor.setFDEBTOR_OUT_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_OUT_DIS)));
            aDebtor.setFDEBTOR_POSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_POSTCODE)));
            aDebtor.setFDEBTOR_PRILLCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_PRILLCODE)));
            aDebtor.setFDEBTOR_RANK_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RANK_CODE)));
            aDebtor.setFDEBTOR_RECORD_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RECORD_ID)));
            aDebtor.setFDEBTOR_REM_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REM_DIS)));
            aDebtor.setFDEBTOR_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REP_CODE)));
            aDebtor.setFDEBTOR_STATUS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_STATUS)));
            aDebtor.setFDEBTOR_SUMMARY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_SUMMARY)));
            aDebtor.setFDEBTOR_TAX_REG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TAX_REG)));
            aDebtor.setFDEBTOR_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TELE)));
            aDebtor.setFDEBTOR_TIME_STAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TIME_STAMP)));
            aDebtor.setFDEBTOR_TOWN_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TOWN_CODE)));
            aDebtor.setFDEBTOR_TRAN_BATCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_BATCH)));
            aDebtor.setFDEBTOR_TRAN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_DATE)));
            aDebtor.setFDEBTOR_NIC(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NIC)));
            aDebtor.setFDEBTOR_BIS_REG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BIS_REG)));
            aDebtor.setFDEBTOR_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LATITUDE)));
            aDebtor.setFDEBTOR_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LONGITUDE)));

            list.add(aDebtor);
        }

        return list;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public Debtor getSelectedCustomerByCode(String code) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FDEBTOR + " Where " + DatabaseHelper.FDEBTOR_CODE + "='" + code + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                Debtor aDebtor = new Debtor();

                aDebtor.setFDEBTOR_ACCTNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ACCTNO)));
                aDebtor.setFDEBTOR_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD1)));
                aDebtor.setFDEBTOR_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD2)));
                aDebtor.setFDEBTOR_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD3)));
                aDebtor.setFDEBTOR_ADD_DATE_DEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_DATE_DEB)));
                aDebtor.setFDEBTOR_ADD_MACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_MACH)));
                aDebtor.setFDEBTOR_ADD_USER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_USER)));
                aDebtor.setFDEBTOR_AREA_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_AREA_CODE)));
                aDebtor.setFDEBTOR_BANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BANK)));
                aDebtor.setFDEBTOR_BRANCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCH)));
                aDebtor.setFDEBTOR_BRANCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCODE)));
                aDebtor.setFDEBTOR_BUS_RGNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BUS_RGNO)));
                aDebtor.setFDEBTOR_CHK_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_LIMIT)));
                aDebtor.setFDEBTOR_CHK_CRD_PRD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_PRD)));
                aDebtor.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CODE)));
//                aDebtor.setFDEBTOR_CRD_LIMIT("200000");
//                aDebtor.setFDEBTOR_CRD_PERIOD("60");
                aDebtor.setFDEBTOR_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_LIMIT)));
                aDebtor.setFDEBTOR_CRD_PERIOD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_PERIOD)));
                aDebtor.setFDEBTOR_CREATEDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CREATEDATE)));
                aDebtor.setFDEBTOR_CUS_VATNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUS_VATNO)));
                aDebtor.setFDEBTOR_CUSDISPER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISPER)));
                aDebtor.setFDEBTOR_CUSDISSTAT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISSTAT)));
                aDebtor.setFDEBTOR_DATE_OFB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DATE_OFB)));
                aDebtor.setFDEBTOR_DBGR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DBGR_CODE)));
                aDebtor.setFDEBTOR_DEAL_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEAL_CODE)));
                aDebtor.setFDEBTOR_DEB_CAT_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CAT_CODE)));
                aDebtor.setFDEBTOR_DEB_CLS_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CLS_CODE)));
                aDebtor.setFDEBTOR_DEB_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
                aDebtor.setFDEBTOR_DEB_WEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
                aDebtor.setFDEBTOR_DEBCT_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD1)));
                aDebtor.setFDEBTOR_DEBCT_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD2)));
                aDebtor.setFDEBTOR_DEBCT_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD3)));
                aDebtor.setFDEBTOR_DEBCT_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_EMAIL)));
                aDebtor.setFDEBTOR_DEBCT_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_FAX)));
                aDebtor.setFDEBTOR_DEBCT_NAM(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_NAM)));
                aDebtor.setFDEBTOR_DEBCT_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_TELE)));
                aDebtor.setFDEBTOR_DEL_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD1)));
                aDebtor.setFDEBTOR_DEL_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD2)));
                aDebtor.setFDEBTOR_DEL_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD3)));
                aDebtor.setFDEBTOR_DEL_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_EMAIL)));
                aDebtor.setFDEBTOR_DEL_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_FAX)));
                aDebtor.setFDEBTOR_DEL_PERSN(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_PERSN)));
                aDebtor.setFDEBTOR_DEL_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_TELE)));
                aDebtor.setFDEBTOR_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_EMAIL)));
                aDebtor.setFDEBTOR_GEN_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_GEN_REMARKS)));
                aDebtor.setFDEBTOR_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ID)));
                aDebtor.setFDEBTOR_LYLTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LYLTY)));
                aDebtor.setFDEBTOR_MOB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_MOB)));
                aDebtor.setFDEBTOR_NAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NAME)));
                aDebtor.setFDEBTOR_OUT_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_OUT_DIS)));
                aDebtor.setFDEBTOR_POSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_POSTCODE)));
                aDebtor.setFDEBTOR_PRILLCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_PRILLCODE)));
                aDebtor.setFDEBTOR_RANK_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RANK_CODE)));
                aDebtor.setFDEBTOR_RECORD_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RECORD_ID)));
                aDebtor.setFDEBTOR_REM_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REM_DIS)));
                aDebtor.setFDEBTOR_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REP_CODE)));
                aDebtor.setFDEBTOR_STATUS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_STATUS)));
                aDebtor.setFDEBTOR_SUMMARY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_SUMMARY)));
                aDebtor.setFDEBTOR_TAX_REG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TAX_REG)));
                aDebtor.setFDEBTOR_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TELE)));
                aDebtor.setFDEBTOR_TIME_STAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TIME_STAMP)));
                aDebtor.setFDEBTOR_TOWN_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TOWN_CODE)));
                aDebtor.setFDEBTOR_TRAN_BATCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_BATCH)));
                aDebtor.setFDEBTOR_TRAN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_DATE)));
                aDebtor.setFDEBTOR_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LATITUDE)));
                aDebtor.setFDEBTOR_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LONGITUDE)));

                return aDebtor;

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }

        return null;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getCustNameByCode(String code) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FDEBTOR + " WHERE " + DatabaseHelper.FDEBTOR_CODE + "='" + code + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NAME));
        }

        return "";
    }

    public String getCusDiscByCode(String code) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FDEBTOR + " WHERE " + DatabaseHelper.FDEBTOR_CODE + "='" + code + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            if (cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISPER)) == null)
                return "0";
            else
                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISPER));

        }

        return "";
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<Debtor> getDebDetails(String searchword) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Debtor> Itemname = new ArrayList<Debtor>();

        String selectQuery = "select DebName,DebCode from fDebtor where DebCode LIKE '%" + searchword + "%' OR DebName LIKE '%" + searchword + "%'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Debtor items = new Debtor();

            items.setFDEBTOR_NAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NAME)));
            items.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CODE)));
            Itemname.add(items);
        }

        return Itemname;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getDebNameByCode(String code) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FDEBTOR + " WHERE " + DatabaseHelper.FDEBTOR_CODE + "='" + code + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NAME));

        }
        return "";

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int saveLocationData(Debtor debtor) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int res = 0;
        try {

            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.FDEBTOR_LATITUDE, debtor.getFDEBTOR_LATITUDE());
            values.put(DatabaseHelper.FDEBTOR_LONGITUDE, debtor.getFDEBTOR_LONGITUDE());

            res = dB.update(DatabaseHelper.TABLE_FDEBTOR, values, DatabaseHelper.FDEBTOR_CODE + " =?", new String[]{debtor.getFDEBTOR_CODE()});

        } catch (Exception e) {
            e.printStackTrace();
            return res;
        } finally {
            dB.close();
        }

        return 0;
    }


    public ArrayList<Debtor> getCustomerByCodeAndNameForReceipt(String newText) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Debtor> list = new ArrayList<Debtor>();

        String selectQuery = "select * from " + dbHelper.TABLE_FDEBTOR + " where " + dbHelper.FDEBTOR_NAME + " like '" + newText + "%' "
                + "and " + dbHelper.FDEBTOR_CODE + " in ( select distinct " + dbHelper.FDEBTOR_CODE + " from " + dbHelper.TABLE_FDDBNOTE + " where " + dbHelper.FDDBNOTE_TOT_BAL + " > 0) ";
        // Original Menaka 25-05-2016 String selectQuery = "select * from " + dbHelper.TABLE_FDEBTOR + " where " + dbHelper.FDEBTOR_CODE + " || " + dbHelper.FDEBTOR_NAME + " like '%" + newText + "%'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            Debtor aDebtor = new Debtor();

            aDebtor.setFDEBTOR_ID(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_ID)));
            aDebtor.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_CODE)));
            aDebtor.setFDEBTOR_NAME(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_NAME)));
            aDebtor.setFDEBTOR_ADD1(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_ADD1)));
            aDebtor.setFDEBTOR_ADD2(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_ADD2)));
            aDebtor.setFDEBTOR_ADD3(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_ADD3)));
            aDebtor.setFDEBTOR_TELE(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_TELE)));
            aDebtor.setFDEBTOR_MOB(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_MOB)));
            aDebtor.setFDEBTOR_EMAIL(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_EMAIL)));
            aDebtor.setFDEBTOR_CREATEDATE(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_CREATEDATE)));
            aDebtor.setFDEBTOR_TOWN_CODE(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_TOWN_CODE)));
            aDebtor.setFDEBTOR_AREA_CODE(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_AREA_CODE)));
            //aDebtor.setFDEBTOR_DBGR_CODE(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_DBGR_CODE)));
            aDebtor.setFDEBTOR_STATUS(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_STATUS)));
            //aDebtor.setFDEBTOR_ADD_USER(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_ADD_USER)));
            //aDebtor.setFDEBTOR_ADD_DATE_DEB(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_ADD_DATE_DEB)));
            //aDebtor.setFDEBTOR_ADD_MACH(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_ADD_MACH)));
            aDebtor.setFDEBTOR_CRD_PERIOD(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_CRD_PERIOD)));
            aDebtor.setFDEBTOR_CHK_CRD_PRD(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_CHK_CRD_PRD)));
            aDebtor.setFDEBTOR_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_CRD_LIMIT)));
            aDebtor.setFDEBTOR_CHK_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_CHK_CRD_LIMIT)));
            //aDebtor.setFDEBTOR_REP_CODE(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_REP_CODE)));
            //aDebtor.setFDEBTOR_RANK_CODE(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_RANK_CODE)));
            aDebtor.setFDEBTOR_SUMMARY(cursor.getString(cursor.getColumnIndex(dbHelper.FDEBTOR_SUMMARY)));

            list.add(aDebtor);

        }

        return list;
    }

    public ArrayList<Debtor> getOutstandingCustomersForReceipt(String rCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Debtor> list = new ArrayList<Debtor>();
        //String selectQuery = "select * from " + dbHelper.TABLE_FDEBTOR + " where " + dbHelper.FDEBTOR_CODE + " in ( select distinct " + dbHelper.FDEBTOR_CODE + " from " + dbHelper.TABLE_FDDBNOTE + " where " + dbHelper.FDDBNOTE_TOT_BAL + " > 0) " ;
        //String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FROUTEDET + " RD, " + DatabaseHelper.TABLE_FDEBTOR + " D, " + DatabaseHelper.TABLE_FDDBNOTE + " FDD WHERE + RD." + DatabaseHelper.FROUTEDET_DEB_CODE + "=D." + DatabaseHelper.FDEBTOR_CODE + " AND D." + DatabaseHelper.FDEBTOR_CODE  + "=FDD." + DatabaseHelper.FDDBNOTE_DEB_CODE + " AND RD." + DatabaseHelper.FROUTEDET_ROUTE_CODE + "='" + RouteCode + "' AND FDD." + DatabaseHelper.FDDBNOTE_TOT_BAL + ">0" + " GROUP BY FDD." + DatabaseHelper.FDDBNOTE_DEB_CODE +  " , D." + DatabaseHelper.FDEBTOR_CODE ;
//        select * from fdebtor where DebCode in (select Debcode from fDdbNote where TotBal > 0) and DebCode in (select Debcode from fRouteDet where RouteCode = '00013')
        String newQuery = "select * from fDebtor where DebCode in ( select DebCode from FDDbNote where TotBal > 0) and Debcode in ( select Debcode from fRouteDet where RouteCode LIKE '%" + rCode + "%' ) ";

        Cursor cursor = dB.rawQuery(newQuery, null);

        try
        {
            while(cursor.moveToNext()){

                Debtor aDebtor = new Debtor();

                aDebtor.setFDEBTOR_ACCTNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ACCTNO)));
                aDebtor.setFDEBTOR_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD1)));
                aDebtor.setFDEBTOR_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD2)));
                aDebtor.setFDEBTOR_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD3)));
                aDebtor.setFDEBTOR_ADD_DATE_DEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_DATE_DEB)));
                aDebtor.setFDEBTOR_ADD_MACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_MACH)));
                aDebtor.setFDEBTOR_ADD_USER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD_USER)));
                aDebtor.setFDEBTOR_AREA_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_AREA_CODE)));
                aDebtor.setFDEBTOR_BANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BANK)));
                aDebtor.setFDEBTOR_BRANCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCH)));
                aDebtor.setFDEBTOR_BRANCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BRANCODE)));
                aDebtor.setFDEBTOR_BUS_RGNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_BUS_RGNO)));
                aDebtor.setFDEBTOR_CHK_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_LIMIT)));
                aDebtor.setFDEBTOR_CHK_CRD_PRD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CHK_CRD_PRD)));
                aDebtor.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CODE)));
                aDebtor.setFDEBTOR_CRD_LIMIT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_LIMIT)));
//                aDebtor.setFDEBTOR_CRD_LIMIT("100000");
                aDebtor.setFDEBTOR_CRD_PERIOD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CRD_PERIOD)));
//                aDebtor.setFDEBTOR_CRD_PERIOD("60");
                aDebtor.setFDEBTOR_CREATEDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CREATEDATE)));
                aDebtor.setFDEBTOR_CUS_VATNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUS_VATNO)));
                aDebtor.setFDEBTOR_CUSDISPER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISPER)));
                aDebtor.setFDEBTOR_CUSDISSTAT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_CUSDISSTAT)));
                aDebtor.setFDEBTOR_DATE_OFB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DATE_OFB)));
                aDebtor.setFDEBTOR_DBGR_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DBGR_CODE)));
                aDebtor.setFDEBTOR_DEAL_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEAL_CODE)));
                aDebtor.setFDEBTOR_DEB_CAT_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CAT_CODE)));
                aDebtor.setFDEBTOR_DEB_CLS_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_CLS_CODE)));
                aDebtor.setFDEBTOR_DEB_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
                aDebtor.setFDEBTOR_DEB_WEB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEB_FAX)));
                aDebtor.setFDEBTOR_DEBCT_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD1)));
                aDebtor.setFDEBTOR_DEBCT_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD2)));
                aDebtor.setFDEBTOR_DEBCT_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_ADD3)));
                aDebtor.setFDEBTOR_DEBCT_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_EMAIL)));
                aDebtor.setFDEBTOR_DEBCT_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_FAX)));
                aDebtor.setFDEBTOR_DEBCT_NAM(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_NAM)));
                aDebtor.setFDEBTOR_DEBCT_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEBCT_TELE)));
                aDebtor.setFDEBTOR_DEL_ADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD1)));
                aDebtor.setFDEBTOR_DEL_ADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD2)));
                aDebtor.setFDEBTOR_DEL_ADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_ADD3)));
                aDebtor.setFDEBTOR_DEL_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_EMAIL)));
                aDebtor.setFDEBTOR_DEL_FAX(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_FAX)));
                aDebtor.setFDEBTOR_DEL_PERSN(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_PERSN)));
                aDebtor.setFDEBTOR_DEL_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_DEL_TELE)));
                aDebtor.setFDEBTOR_EMAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_EMAIL)));
                aDebtor.setFDEBTOR_GEN_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_GEN_REMARKS)));
                aDebtor.setFDEBTOR_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ID)));
                aDebtor.setFDEBTOR_LYLTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_LYLTY)));
                aDebtor.setFDEBTOR_MOB(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_MOB)));
                aDebtor.setFDEBTOR_NAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NAME)));
                aDebtor.setFDEBTOR_OUT_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_OUT_DIS)));
                aDebtor.setFDEBTOR_POSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_POSTCODE)));
                aDebtor.setFDEBTOR_PRILLCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_PRILLCODE)));
                aDebtor.setFDEBTOR_RANK_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RANK_CODE)));
                aDebtor.setFDEBTOR_RECORD_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_RECORD_ID)));
                aDebtor.setFDEBTOR_REM_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REM_DIS)));
                aDebtor.setFDEBTOR_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_REP_CODE)));
                aDebtor.setFDEBTOR_STATUS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_STATUS)));
                aDebtor.setFDEBTOR_SUMMARY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_SUMMARY)));
                aDebtor.setFDEBTOR_TAX_REG(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TAX_REG)));
                aDebtor.setFDEBTOR_TELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TELE)));
                aDebtor.setFDEBTOR_TIME_STAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TIME_STAMP)));
                aDebtor.setFDEBTOR_TOWN_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TOWN_CODE)));
                aDebtor.setFDEBTOR_TRAN_BATCH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_BATCH)));
                aDebtor.setFDEBTOR_TRAN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TRAN_DATE)));

                list.add(aDebtor);
            }

        }catch (Exception e)
        {
            Log.v(TAG + " Exception", e.toString());
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }

        return list;
    }


    @SuppressWarnings("static-access")
    public String getRouteNameByCode(String code) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FROUTE + " WHERE " + dbHelper.FROUTE_ROUTECODE + " IN (SELECT " + dbHelper.FROUTEDET_ROUTE_CODE + " FROM " + dbHelper.TABLE_FROUTEDET + " WHERE " + dbHelper.FROUTEDET_DEB_CODE + " = '" + code + "')";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex(dbHelper.FROUTE_ROUTE_NAME));
        }

        return "";
    }


    @SuppressWarnings("static-access")
    public String getTownNameByCode(String code) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FTOWN + " WHERE " + dbHelper.FTOWN_CODE + " IN (SELECT "+dbHelper.FTOWN_CODE+" FROM "+dbHelper.TABLE_FDEBTOR+" WHERE "+dbHelper.FDEBTOR_CODE+" = '"+code+"')";
        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex(dbHelper.FTOWN_NAME));
        }

        return "";
    }

}
