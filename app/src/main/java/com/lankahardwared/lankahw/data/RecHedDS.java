package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.PaymentAllocate;
import com.lankahardwared.lankahw.model.RecHed;
import com.lankahardwared.lankahw.model.mapper.ReceiptMapper;

import java.util.ArrayList;

public class RecHedDS {

	private SQLiteDatabase dB;
	private DatabaseHelper dbHelper;
	Context context;
	private String TAG = "FINAC_NEW";

	public static final String SETTINGS = "SETTINGS";
	public static SharedPreferences localSP;

	public RecHedDS(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		dB = dbHelper.getWritableDatabase();
	}

	public int createOrUpdateRecHed(ArrayList<RecHed> list) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			for (RecHed recHed : list) {

				String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE "
						+ DatabaseHelper.FPRECHED_REFNO + " = '" + recHed.getFPRECHED_REFNO() + "'";

				cursor = dB.rawQuery(selectQuery, null);

				ContentValues values = new ContentValues();

				values.put(DatabaseHelper.FPRECHED_ID, recHed.getFPRECHED_ID());
				values.put(DatabaseHelper.FPRECHED_REFNO, recHed.getFPRECHED_REFNO());
				values.put(DatabaseHelper.FPRECHED_REFNO1, recHed.getFPRECHED_REFNO1());
				//values.put(DatabaseHelper.FPRECHED_REFNO2, recHed.getFPRECHED_REFNO2());
				values.put(DatabaseHelper.FPRECHED_MANUREF, recHed.getFPRECHED_MANUREF());
				values.put(DatabaseHelper.FPRECHED_SALEREFNO, recHed.getFPRECHED_SALEREFNO());
				values.put(DatabaseHelper.FPRECHED_REPCODE, recHed.getFPRECHED_REPCODE());
				values.put(DatabaseHelper.FPRECHED_TXNTYPE, recHed.getFPRECHED_TXNTYPE());
				values.put(DatabaseHelper.FPRECHED_CHQNO, recHed.getFPRECHED_CHQNO());
				values.put(DatabaseHelper.FPRECHED_CHQDATE, recHed.getFPRECHED_CHQDATE());
				values.put(DatabaseHelper.FPRECHED_TXNDATE, recHed.getFPRECHED_TXNDATE());
				values.put(DatabaseHelper.FPRECHED_CURCODE, recHed.getFPRECHED_CURCODE());
				values.put(DatabaseHelper.FPRECHED_CURRATE1, "");
	
				values.put(DatabaseHelper.FPRECHED_DEBCODE, recHed.getFPRECHED_DEBCODE());
				values.put(DatabaseHelper.FPRECHED_TOTALAMT, recHed.getFPRECHED_TOTALAMT());
				values.put(DatabaseHelper.FPRECHED_BTOTALAMT, recHed.getFPRECHED_BTOTALAMT());
				values.put(DatabaseHelper.FPRECHED_PAYTYPE, recHed.getFPRECHED_PAYTYPE());
				values.put(DatabaseHelper.FPRECHED_PRTCOPY, "");
				values.put(DatabaseHelper.FPRECHED_REMARKS, recHed.getFPRECHED_REMARKS());
				values.put(DatabaseHelper.FPRECHED_ADDUSER, recHed.getFPRECHED_ADDUSER());
				values.put(DatabaseHelper.FPRECHED_ADDMACH, recHed.getFPRECHED_ADDMACH());
				values.put(DatabaseHelper.FPRECHED_ADDDATE, recHed.getFPRECHED_ADDDATE());
				values.put(DatabaseHelper.FPRECHED_RECORDID, "");
				values.put(DatabaseHelper.FPRECHED_TIMESTAMP, "");
				values.put(DatabaseHelper.FPRECHED_CURRATE, recHed.getFPRECHED_CURRATE());
				values.put(DatabaseHelper.FPRECHED_CUSBANK, recHed.getFPRECHED_CUSBANK());
				values.put(DatabaseHelper.FPRECHED_LONGITUDE, recHed.getFPRECHED_LONGITUDE());
				values.put(DatabaseHelper.FPRECHED_LATITUDE, recHed.getFPRECHED_LATITUDE());
				values.put(DatabaseHelper.FPRECHED_ADDRESS, recHed.getFPRECHED_ADDRESS());
				values.put(DatabaseHelper.FPRECHED_START_TIME, recHed.getFPRECHED_START_TIME());
				values.put(DatabaseHelper.FPRECHED_END_TIME, recHed.getFPRECHED_END_TIME());
				values.put(DatabaseHelper.FPRECHED_ISSYNCED, recHed.getFPRECHED_ISSYNCED());
				values.put(DatabaseHelper.FPRECHED_ISACTIVE, recHed.getFPRECHED_ISACTIVE());
				values.put(DatabaseHelper.FPRECHED_ISDELETE, recHed.getFPRECHED_ISDELETE());

				int cn = cursor.getCount();
				if (cn > 0) {
					count = dB.update(DatabaseHelper.TABLE_FPRECHED, values, DatabaseHelper.FPRECHED_REFNO + " =?",
							new String[] { String.valueOf(recHed.getFPRECHED_REFNO()) });
				} else {
					count = (int) dB.insert(DatabaseHelper.TABLE_FPRECHED, null, values);
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
	public ArrayList<RecHed> getRecHedDataByType(String payType) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<RecHed> list = new ArrayList<RecHed>();
		String selectQuery;

		try {

			selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " Where " + DatabaseHelper.FPRECHED_ISDEPOSIT+" ='0' and "+ DatabaseHelper.FPRECHED_PAYTYPE
					+ " = '" + payType + "';";

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				RecHed recHed = new RecHed();

				//recHed.setFPRECHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDDATE)));
				//recHed.setFPRECHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDMACH)));
				//recHed.setFPRECHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDRESS)));
				//recHed.setFPRECHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDUSER)));
				//recHed.setFPRECHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BTOTALAMT)));
				//recHed.setFPRECHED_CHQDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQDATE)));
				recHed.setFPRECHED_CHQNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQNO)));
				//recHed.setFPRECHED_CURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURCODE)));
				//recHed.setFPRECHED_CURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE)));
				//recHed.setFPRECHED_CURRATE1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE1)));
				//recHed.setFPRECHED_CUSBANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CUSBANK)));
				recHed.setFPRECHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_DEBCODE)));
				//recHed.setFPRECHED_END_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_END_TIME)));
				//recHed.setFPRECHED_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ID)));
				//recHed.setFPRECHED_ISACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISACTIVE)));
				//recHed.setFPRECHED_ISSYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISSYNCED)));
				//recHed.setFPRECHED_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LATITUDE)));
				//recHed.setFPRECHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LONGITUDE)));
				//recHed.setFPRECHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_MANUREF)));
				recHed.setFPRECHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PAYTYPE)));
				//recHed.setFPRECHED_PRTCOPY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PRTCOPY)));
				//recHed.setFPRECHED_RECORDID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_RECORDID)));
				recHed.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO)));
				//recHed.setFPRECHED_REFNO1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO1)));
				//recHed.setFPRECHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REMARKS)));
				//recHed.setFPRECHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REPCODE)));
				//recHed.setFPRECHED_SALEREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_SALEREFNO)));
				//recHed.setFPRECHED_START_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_START_TIME)));
				//recHed.setFPRECHED_TIMESTAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TIMESTAMP)));
				recHed.setFPRECHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TOTALAMT)));
				recHed.setFPRECHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNDATE)));
				//recHed.setFPRECHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNTYPE)));
				//recHed.setFPRECHED_ISDELETE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISDELETE)));

				list.add(recHed);

			}
			cursor.close();
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return list;
	}
	public int updateIsDepositStatus(boolean isChecked , ArrayList<String> receiptRefNoList) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try
		{
			for(int i = 0; i<receiptRefNoList.size(); i++)
			{
				String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE "
						+ DatabaseHelper.FPRECHED_REFNO + " = '" + receiptRefNoList.get(i) + "'";

				cursor = dB.rawQuery(selectQuery, null);

				ContentValues values = new ContentValues();

				if (isChecked) {
					values.put(DatabaseHelper.FPRECHED_ISDEPOSIT, "1");
				} else {
					values.put(DatabaseHelper.FPRECHED_ISDEPOSIT, "0");
				}

				int cn = cursor.getCount();

				if (cn > 0) {
					count = count + dB.update(DatabaseHelper.TABLE_FPRECHED, values, DatabaseHelper.FPRECHED_REFNO + " =?",
							new String[]{String.valueOf(receiptRefNoList.get(i))});
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

	/*
	 * create for store singlr receipt
	 */
	public int createOrUpdateRecHedS(ArrayList<RecHed> list) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			for (RecHed recHed : list) {

				String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE "
						+ DatabaseHelper.FPRECHED_REFNO + " = '" + recHed.getFPRECHED_REFNO() + "'";

				cursor = dB.rawQuery(selectQuery, null);

				ContentValues values = new ContentValues();

				//values.put(DatabaseHelper.FPRECHED_ID, recHed.getFPRECHED_ID());
				values.put(DatabaseHelper.FPRECHED_REFNO, recHed.getFPRECHED_REFNO());
				values.put(DatabaseHelper.FPRECHED_REFNO1, recHed.getFPRECHED_REFNO1());
				values.put(DatabaseHelper.FPRECHED_MANUREF, recHed.getFPRECHED_MANUREF());
				values.put(DatabaseHelper.FPRECHED_SALEREFNO, recHed.getFPRECHED_SALEREFNO());
				values.put(DatabaseHelper.FPRECHED_REPCODE, recHed.getFPRECHED_REPCODE());
				values.put(DatabaseHelper.FPRECHED_TXNTYPE, recHed.getFPRECHED_TXNTYPE());
				values.put(DatabaseHelper.FPRECHED_CHQNO, recHed.getFPRECHED_CHQNO());
				values.put(DatabaseHelper.FPRECHED_CHQDATE, recHed.getFPRECHED_CHQDATE());
				values.put(DatabaseHelper.FPRECHED_TXNDATE, recHed.getFPRECHED_TXNDATE());
				values.put(DatabaseHelper.FPRECHED_CURCODE, recHed.getFPRECHED_CURCODE());
				values.put(DatabaseHelper.FPRECHED_CURRATE1, "");
				values.put(DatabaseHelper.FPRECHED_DEBCODE, recHed.getFPRECHED_DEBCODE());
				values.put(DatabaseHelper.FPRECHED_TOTALAMT, recHed.getFPRECHED_TOTALAMT());
				values.put(DatabaseHelper.FPRECHED_BTOTALAMT, recHed.getFPRECHED_BTOTALAMT());
				values.put(DatabaseHelper.FPRECHED_PAYTYPE, recHed.getFPRECHED_PAYTYPE());
				values.put(DatabaseHelper.FPRECHED_PRTCOPY, "");
				values.put(DatabaseHelper.FPRECHED_REMARKS, recHed.getFPRECHED_REMARKS());
				values.put(DatabaseHelper.FPRECHED_ADDUSER, recHed.getFPRECHED_ADDUSER());
				values.put(DatabaseHelper.FPRECHED_ADDMACH, recHed.getFPRECHED_ADDMACH());
				values.put(DatabaseHelper.FPRECHED_ADDDATE, recHed.getFPRECHED_ADDDATE());
				values.put(DatabaseHelper.FPRECHED_RECORDID, "");
				values.put(DatabaseHelper.FPRECHED_TIMESTAMP, "");
				values.put(DatabaseHelper.FPRECHED_CURRATE, recHed.getFPRECHED_CURRATE());
				values.put(DatabaseHelper.FPRECHED_CUSBANK, recHed.getFPRECHED_CUSBANK());
				values.put(DatabaseHelper.FPRECHED_LONGITUDE, recHed.getFPRECHED_LONGITUDE());
				values.put(DatabaseHelper.FPRECHED_LATITUDE, recHed.getFPRECHED_LATITUDE());
				values.put(DatabaseHelper.FPRECHED_ADDRESS, recHed.getFPRECHED_ADDRESS());
				values.put(DatabaseHelper.FPRECHED_START_TIME, recHed.getFPRECHED_START_TIME());
				values.put(DatabaseHelper.FPRECHED_END_TIME, recHed.getFPRECHED_END_TIME());
				values.put(DatabaseHelper.FPRECHED_ISSYNCED, recHed.getFPRECHED_ISSYNCED());
				values.put(DatabaseHelper.FPRECHED_ISACTIVE, recHed.getFPRECHED_ISACTIVE());
				values.put(DatabaseHelper.FPRECHED_ISDELETE, recHed.getFPRECHED_ISDELETE());
				values.put(DatabaseHelper.FPRECHED_BANKCODE, recHed.getFPRECHED_BANKCODE());
				values.put(DatabaseHelper.FPRECHED_BRANCHCODE, recHed.getFPRECHED_BRANCHCODE());
				values.put(DatabaseHelper.FPRECHED_COMMON_REFNO, recHed.getFPRECHED_COMMON_RENNO());

				int cn = cursor.getCount();
				if (cn > 0) {
					count = dB.update(DatabaseHelper.TABLE_FPRECHED, values, DatabaseHelper.FPRECHED_REFNO + " =?",
							new String[] { String.valueOf(recHed.getFPRECHED_REFNO()) });
				} else {
					count = (int) dB.insert(DatabaseHelper.TABLE_FPRECHED, null, values);
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

	/*
	 * create for single receipt
	 */
	public ArrayList<RecHed> getAllCompletedRecHedS(String refno) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<RecHed> list = new ArrayList<RecHed>();
		String selectQuery;

		try {
			if (refno.equals("")) {

				selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " Where "
						+ DatabaseHelper.FPRECHED_ISDELETE + "='0' and "+ DatabaseHelper.FPRECHED_ISACTIVE 
						+ "='0' Order by "+DatabaseHelper.FPRECHED_ISSYNCED + "," +DatabaseHelper.FPRECHED_REFNO + " DESC"; 

			}
			else if(refno.equals("U")){

				selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " Where "
						+ DatabaseHelper.FPRECHED_ISDELETE + "='0' and "+ DatabaseHelper.FPRECHED_ISACTIVE + "='0' and " + DatabaseHelper.FPRECHED_ISSYNCED + "='1' Order by "+DatabaseHelper.FPRECHED_REFNO + " DESC";
			}
			else if(refno.equals("N")){

				selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " Where "
						+ DatabaseHelper.FPRECHED_ISDELETE + "='0' and "+ DatabaseHelper.FPRECHED_ISACTIVE + "='0' and " + DatabaseHelper.FPRECHED_ISSYNCED + "='0' Order by "+DatabaseHelper.FPRECHED_REFNO + " DESC";
			}
			else {

				selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " Where "
						+ DatabaseHelper.FPRECHED_ISDELETE + "='0' and "+ DatabaseHelper.FPRECHED_ISACTIVE + "='0' and " + DatabaseHelper.FPRECHED_REFNO + "='" + refno
						+ "' Order by "+DatabaseHelper.FPRECHED_ISSYNCED + "," +DatabaseHelper.FPRECHED_REFNO + " DESC";
			}

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				RecHed recHed = new RecHed();

				recHed.setFPRECHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDDATE)));
				recHed.setFPRECHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDMACH)));
				recHed.setFPRECHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDRESS)));
				recHed.setFPRECHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDUSER)));
				recHed.setFPRECHED_BANKCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BANKCODE)));
				recHed.setFPRECHED_BRANCHCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BRANCHCODE)));
				recHed.setFPRECHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BTOTALAMT)));
				recHed.setFPRECHED_CHQDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQDATE)));
				recHed.setFPRECHED_CHQNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQNO)));
				recHed.setFPRECHED_COSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_COST_CODE)));
				recHed.setFPRECHED_CURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURCODE)));
				recHed.setFPRECHED_CURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE)));
				recHed.setFPRECHED_CURRATE1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE1)));
				recHed.setFPRECHED_CUSBANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CUSBANK)));
				recHed.setFPRECHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_DEBCODE)));
				recHed.setFPRECHED_END_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_END_TIME)));
				recHed.setFPRECHED_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ID)));
				recHed.setFPRECHED_ISACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISACTIVE)));
				recHed.setFPRECHED_ISSYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISSYNCED)));
				recHed.setFPRECHED_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LATITUDE)));
				recHed.setFPRECHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LONGITUDE)));
				recHed.setFPRECHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_MANUREF)));
				recHed.setFPRECHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PAYTYPE)));
				recHed.setFPRECHED_PRTCOPY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PRTCOPY)));
				recHed.setFPRECHED_RECORDID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_RECORDID)));
				recHed.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO)));
				recHed.setFPRECHED_REFNO1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO1)));
				recHed.setFPRECHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REMARKS)));
				recHed.setFPRECHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REPCODE)));
				recHed.setFPRECHED_SALEREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_SALEREFNO)));
				recHed.setFPRECHED_START_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_START_TIME)));
				recHed.setFPRECHED_TIMESTAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TIMESTAMP)));
				recHed.setFPRECHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TOTALAMT)));
				recHed.setFPRECHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNDATE)));
				recHed.setFPRECHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNTYPE)));
				recHed.setFPRECHED_COMMON_RENNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_COMMON_REFNO)));

				list.add(recHed);

			}
			cursor.close();
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return list;
	}

	/*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public ArrayList<RecHed> getAllCompletedRecHed(String refno) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<RecHed> list = new ArrayList<RecHed>();
		String selectQuery;

		try {
			if (refno.equals("")) {

				selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " Where "
						+ DatabaseHelper.FPRECHED_ISDELETE + "='0' and "+ DatabaseHelper.FPRECHED_TOTALAMT
						+ ">'0'";

			} else {

				selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " Where "
						+ DatabaseHelper.FPRECHED_ISDELETE + "='0' and " + DatabaseHelper.FPRECHED_ISACTIVE
						+ "='0' and " + DatabaseHelper.FPRECHED_TOTALAMT
						+ ">'0' and " + DatabaseHelper.FPRECHED_REFNO + "='" + refno + "'";
			}

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				RecHed recHed = new RecHed();

				recHed.setFPRECHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDDATE)));
				recHed.setFPRECHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDMACH)));
				recHed.setFPRECHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDRESS)));
				recHed.setFPRECHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDUSER)));
				recHed.setFPRECHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BTOTALAMT)));
				recHed.setFPRECHED_CHQDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQDATE)));
				recHed.setFPRECHED_CHQNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQNO)));
				recHed.setFPRECHED_CURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURCODE)));
				recHed.setFPRECHED_CURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE)));
				recHed.setFPRECHED_CURRATE1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE1)));
				recHed.setFPRECHED_CUSBANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CUSBANK)));
				recHed.setFPRECHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_DEBCODE)));
				recHed.setFPRECHED_END_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_END_TIME)));
				recHed.setFPRECHED_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ID)));
				recHed.setFPRECHED_ISACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISACTIVE)));
				recHed.setFPRECHED_ISSYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISSYNCED)));
				recHed.setFPRECHED_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LATITUDE)));
				recHed.setFPRECHED_LONGITUDE(
						cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LONGITUDE)));
				recHed.setFPRECHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_MANUREF)));
				recHed.setFPRECHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PAYTYPE)));
				recHed.setFPRECHED_PRTCOPY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PRTCOPY)));
				recHed.setFPRECHED_RECORDID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_RECORDID)));
				recHed.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO)));
				recHed.setFPRECHED_REFNO1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO1)));
				recHed.setFPRECHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REMARKS)));
				recHed.setFPRECHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REPCODE)));
				recHed.setFPRECHED_SALEREFNO(
						cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_SALEREFNO)));
				recHed.setFPRECHED_START_TIME(
						cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_START_TIME)));
				recHed.setFPRECHED_TIMESTAMP(
						cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TIMESTAMP)));
				recHed.setFPRECHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TOTALAMT)));
				recHed.setFPRECHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNDATE)));
				recHed.setFPRECHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNTYPE)));
				recHed.setFPRECHED_ISDELETE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISDELETE)));

				list.add(recHed);

			}
			cursor.close();
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return list;
	}

	public RecHed getReceiptByRefno(String refno) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery;

		RecHed recHed = new RecHed();
		try {

			selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " Where " + DatabaseHelper.FPRECHED_REFNO
					+ "='" + refno + "'";

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				recHed.setFPRECHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDDATE)));
				recHed.setFPRECHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDMACH)));
				recHed.setFPRECHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDRESS)));
				recHed.setFPRECHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDUSER)));
				recHed.setFPRECHED_BANKCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BANKCODE)));
				recHed.setFPRECHED_BRANCHCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BRANCHCODE)));
				recHed.setFPRECHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BTOTALAMT)));
				recHed.setFPRECHED_CHQDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQDATE)));
				recHed.setFPRECHED_CHQNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQNO)));
				recHed.setFPRECHED_CURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURCODE)));
				recHed.setFPRECHED_CURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE)));
				recHed.setFPRECHED_CURRATE1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE1)));
				recHed.setFPRECHED_CUSBANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CUSBANK)));
				recHed.setFPRECHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_DEBCODE)));
				recHed.setFPRECHED_END_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_END_TIME)));
				recHed.setFPRECHED_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ID)));
				recHed.setFPRECHED_ISACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISACTIVE)));
				recHed.setFPRECHED_ISSYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISSYNCED)));
				recHed.setFPRECHED_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LATITUDE)));
				recHed.setFPRECHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LONGITUDE)));
				recHed.setFPRECHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_MANUREF)));
				recHed.setFPRECHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PAYTYPE)));
				recHed.setFPRECHED_PRTCOPY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PRTCOPY)));
				recHed.setFPRECHED_RECORDID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_RECORDID)));
				recHed.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO)));
				recHed.setFPRECHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REMARKS)));
				recHed.setFPRECHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REPCODE)));
				recHed.setFPRECHED_SALEREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_SALEREFNO)));
				recHed.setFPRECHED_START_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_START_TIME)));
				recHed.setFPRECHED_TIMESTAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TIMESTAMP)));
				recHed.setFPRECHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TOTALAMT)));
				recHed.setFPRECHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNDATE)));
				recHed.setFPRECHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNTYPE)));

			}
			cursor.close();
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return recHed;
	}

	/*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public int CancelReceipt(String Refno) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		int result = 0;
		try {
			result = dB.delete(DatabaseHelper.TABLE_FPRECHED, DatabaseHelper.FPRECHED_REFNO + "=?",
					new String[] { Refno });

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return result;
	}

	public int CancelReceiptS(String Refno) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		int result = 0;
		try {
			result = dB.delete(DatabaseHelper.TABLE_FPRECHED, DatabaseHelper.FPRECHED_REFNO + "=?",
					new String[] { Refno });

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return result;
	}

	/*-*-*-*-*-*-**-*-*--*-*-*-*-*-*-*--**-*-*-*-*-*---*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public int InactiveStatusUpdate(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE "
					+ DatabaseHelper.FPRECHED_REFNO + " = '" + refno + "'";

			cursor = dB.rawQuery(selectQuery, null);

			ContentValues values = new ContentValues();

			values.put(DatabaseHelper.FPRECHED_ISACTIVE, "0");

			int cn = cursor.getCount();

			if (cn > 0) {
				count = dB.update(DatabaseHelper.TABLE_FPRECHED, values, DatabaseHelper.FPRECHED_REFNO + " =?",
						new String[] { String.valueOf(refno) });
			} else {
				count = (int) dB.insert(DatabaseHelper.TABLE_FPRECHED, null, values);
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

	public void updateActiveStatus(String refno) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE "+ DatabaseHelper.FPRECHED_REFNO + " = '" + refno + "'";

			cursor = dB.rawQuery(selectQuery, null);

			ContentValues values = new ContentValues();

			values.put(DatabaseHelper.FPRECHED_ISACTIVE, "0");

			int cn = cursor.getCount();

			if (cn > 0) {
				dB.update(DatabaseHelper.TABLE_FPRECHED, values, DatabaseHelper.FPRECHED_REFNO + " =?",	new String[] { String.valueOf(refno) });
			} else {
				dB.insert(DatabaseHelper.TABLE_FPRECHED, null, values);
			}

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			dB.close();
		}

	}

	public int InactiveStatusUpdateForMultiREceipt(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE "
					+ DatabaseHelper.FPRECHED_REFNO1 + " = '" + refno + "'";

			cursor = dB.rawQuery(selectQuery, null);

			ContentValues values = new ContentValues();

			values.put(DatabaseHelper.FPRECHED_ISACTIVE, "0");

			int cn = cursor.getCount();

			if (cn > 0) {
				count = dB.update(DatabaseHelper.TABLE_FPRECHED, values, DatabaseHelper.FPRECHED_REFNO1 + " =?",
						new String[] { String.valueOf(refno) });
			} else {
				count = (int) dB.insert(DatabaseHelper.TABLE_FPRECHED, null, values);
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

	/***********************************************************************/
	public int DeleteStatusUpdateForMultiREceipt(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE "
					+ DatabaseHelper.FPRECHED_REFNO1 + " = '" + refno + "'";

			cursor = dB.rawQuery(selectQuery, null);

			ContentValues values = new ContentValues();

			values.put(DatabaseHelper.FPRECHED_ISDELETE, "1");

			int cn = cursor.getCount();

			if (cn > 0) {
				count = dB.update(DatabaseHelper.TABLE_FPRECHED, values, DatabaseHelper.FPRECHED_REFNO1 + " =?",
						new String[] { String.valueOf(refno) });
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
	/*-*-*-*-*-*-**-*-*--*-*-*-*-*-*-*--**-*-*-*-*-*---*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public int DeleteStatusUpdateForEceipt(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE "
					+ DatabaseHelper.FPRECHED_REFNO + " = '" + refno + "'";

			cursor = dB.rawQuery(selectQuery, null);

			ContentValues values = new ContentValues();

			values.put(DatabaseHelper.FPRECHED_ISDELETE, "1");

			int cn = cursor.getCount();

			if (cn > 0) {
				count = dB.update(DatabaseHelper.TABLE_FPRECHED, values, DatabaseHelper.FPRECHED_REFNO + " =?",
						new String[] { String.valueOf(refno) });
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
	/********************************************************************************/
	public void UpdateRecHedForMultiReceipt(RecHed recHed, String Refno) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ContentValues values = new ContentValues();

		try {
			values.put(DatabaseHelper.FPRECHED_LATITUDE, recHed.getFPRECHED_LATITUDE());
			values.put(DatabaseHelper.FPRECHED_LONGITUDE, recHed.getFPRECHED_LONGITUDE());
			values.put(DatabaseHelper.FPRECHED_START_TIME, recHed.getFPRECHED_START_TIME());
			values.put(DatabaseHelper.FPRECHED_END_TIME, recHed.getFPRECHED_END_TIME());
			values.put(DatabaseHelper.FPRECHED_ADDRESS, recHed.getFPRECHED_ADDRESS());

			dB.update(DatabaseHelper.TABLE_FPRECHED, values, DatabaseHelper.FPRECHED_REFNO1 + " =?",
					new String[] { String.valueOf(Refno) });

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}
	}

	public void UpdateRecHed(RecHed recHed, String Refno) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ContentValues values = new ContentValues();

		try {
			values.put(DatabaseHelper.FPRECHED_LATITUDE, recHed.getFPRECHED_LATITUDE());
			values.put(DatabaseHelper.FPRECHED_LONGITUDE, recHed.getFPRECHED_LONGITUDE());
			values.put(DatabaseHelper.FPRECHED_START_TIME, recHed.getFPRECHED_START_TIME());
			values.put(DatabaseHelper.FPRECHED_END_TIME, recHed.getFPRECHED_END_TIME());
			values.put(DatabaseHelper.FPRECHED_ADDRESS, recHed.getFPRECHED_ADDRESS());
			values.put(DatabaseHelper.FPRECHED_COST_CODE, recHed.getFPRECHED_COSTCODE());

			dB.update(DatabaseHelper.TABLE_FPRECHED, values, DatabaseHelper.FPRECHED_REFNO + " =?",
					new String[] { String.valueOf(Refno) });

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}
	}

	public ArrayList<ReceiptMapper> getAllUnsyncedRecHed() {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<ReceiptMapper> list = new ArrayList<ReceiptMapper>();
		String selectQuery;

		try {
			selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " Where "
					+ DatabaseHelper.FPRECHED_ISACTIVE + "='0' and " + DatabaseHelper.FPRECHED_ISSYNCED + "='0' and " 
					+ DatabaseHelper.FPRECHED_ISDELETE + "='0'";

//			localSP = context.getSharedPreferences(SETTINGS,
//					Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
			localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				ReceiptMapper mapper = new ReceiptMapper();

				mapper.setNextNumVal(new CompanyBranchDS(context)
						.getCurrentNextNumVal(context.getResources().getString(R.string.RecNumVal)));

				mapper.setDistDB(localSP.getString("Dist_DB", "").toString());
				mapper.setConsoleDB(localSP.getString("Console_DB", "").toString());
//				mapper.setSALEREP_DEALCODE(new SalRepDS(context).getDealCode());
//				mapper.setSALEREP_AREACODE(new SalRepDS(context).getAreaCode());

				mapper.setFPRECHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDDATE)));
				mapper.setFPRECHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDMACH)));
				mapper.setFPRECHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDRESS)));
				mapper.setFPRECHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDUSER)));
				mapper.setFPRECHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BTOTALAMT)));
				mapper.setFPRECHED_CHQDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQDATE)));
				mapper.setFPRECHED_CHQNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQNO)));
				mapper.setFPRECHED_CURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURCODE)));
				mapper.setFPRECHED_CURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE)));
				//mapper.setFPRECHED_CURRATE1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE1)));
				mapper.setFPRECHED_CUSBANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CUSBANK)));
				mapper.setFPRECHED_COSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_COST_CODE)));
				mapper.setFPRECHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_DEBCODE)));
				mapper.setFPRECHED_END_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_END_TIME)));
//				mapper.setFPRECHED_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ID)));
//				mapper.setFPRECHED_ISACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISACTIVE)));
//				mapper.setFPRECHED_ISSYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISSYNCED)));
				mapper.setFPRECHED_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LATITUDE)));
				mapper.setFPRECHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LONGITUDE)));
				mapper.setFPRECHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_MANUREF)));
				mapper.setFPRECHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PAYTYPE)));
				mapper.setFPRECHED_PRTCOPY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PRTCOPY)));
				//mapper.setFPRECHED_RECORDID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_RECORDID)));
				mapper.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO)));
				mapper.setFPRECHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REMARKS)));
				mapper.setFPRECHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REPCODE)));
				mapper.setFPRECHED_SALEREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_SALEREFNO)));
				mapper.setFPRECHED_START_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_START_TIME)));
				//mapper.setFPRECHED_TIMESTAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TIMESTAMP)));
				mapper.setFPRECHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TOTALAMT)));
				mapper.setFPRECHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNDATE)));
				mapper.setFPRECHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNTYPE)));
				mapper.setFPRECHED_BANKCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BANKCODE)));
				mapper.setFPRECHED_BRANCHCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BRANCHCODE)));
				mapper.setFPRECHED_COMMON_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_COMMON_REFNO)));
				String mref = mapper.getFPRECHED_COMMON_REFNO();
				Log.d("MULTIREF", mref);

				mapper.setRecDetList(new RecDetDS(context).GetReceiptByRefno(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO))));

				list.add(mapper);

			}
			cursor.close();
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return list;
	}

	/*-*-*-*-*-*-**-*-*--*-*-*-*-*-*-*--**-*-*-*-*-*---*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public ArrayList<ReceiptMapper> getAllUnsyncedRecHedMulti() {
		
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<ReceiptMapper> list = new ArrayList<ReceiptMapper>();
		String selectQuery;

		try {
			selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " Where "
					+ DatabaseHelper.FPRECHED_ISACTIVE + "='0' and " + DatabaseHelper.FPRECHED_ISSYNCED + "='0'  and " + DatabaseHelper.FPRECHED_ISDELETE + "='0'";

//			localSP = context.getSharedPreferences(SETTINGS,
//					Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
			localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				ReceiptMapper mapper = new ReceiptMapper();

				mapper.setNextNumVal(new CompanyBranchDS(context)
						.getCurrentNextNumVal(context.getResources().getString(R.string.RecNumVal)));

				mapper.setDistDB(localSP.getString("Dist_DB", "").toString());
				mapper.setConsoleDB(localSP.getString("Console_DB", "").toString());
//				mapper.setSALEREP_DEALCODE(new SalRepDS(context).getDealCode());
//				mapper.setSALEREP_AREACODE(new SalRepDS(context).getAreaCode());

				mapper.setFPRECHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDDATE)));
				mapper.setFPRECHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDMACH)));
				mapper.setFPRECHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDRESS)));
				mapper.setFPRECHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDUSER)));
				mapper.setFPRECHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BTOTALAMT)));
				mapper.setFPRECHED_CHQDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQDATE)));
				mapper.setFPRECHED_CHQNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQNO)));
				mapper.setFPRECHED_CURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURCODE)));
				mapper.setFPRECHED_CURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE)));
				mapper.setFPRECHED_CURRATE1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE1)));
				mapper.setFPRECHED_CUSBANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CUSBANK)));
				mapper.setFPRECHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_DEBCODE)));
				mapper.setFPRECHED_END_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_END_TIME)));
				mapper.setFPRECHED_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ID)));
				mapper.setFPRECHED_ISACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISACTIVE)));
				mapper.setFPRECHED_ISSYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISSYNCED)));
				mapper.setFPRECHED_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LATITUDE)));
				mapper.setFPRECHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LONGITUDE)));
				mapper.setFPRECHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_MANUREF)));
				mapper.setFPRECHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PAYTYPE)));
				mapper.setFPRECHED_PRTCOPY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PRTCOPY)));
				mapper.setFPRECHED_RECORDID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_RECORDID)));
				mapper.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO)));
				mapper.setFPRECHED_REFNO1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO1)));
				mapper.setFPRECHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REMARKS)));
				mapper.setFPRECHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REPCODE)));
				mapper.setFPRECHED_SALEREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_SALEREFNO)));
				mapper.setFPRECHED_START_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_START_TIME)));
				mapper.setFPRECHED_TIMESTAMP(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TIMESTAMP)));
				mapper.setFPRECHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TOTALAMT)));
				mapper.setFPRECHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNDATE)));
				mapper.setFPRECHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNTYPE)));

				mapper.setRecDetList(new RecDetDS(context).GetMReceiptByRefno(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO))));

				list.add(mapper);

			}
			cursor.close();
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return list;
	}

	/*-*-*-*-*-*-**-*-*--*-*-*-*-*-*-*--**-*-*-*-*-*---*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	@SuppressWarnings("static-access")
	public int updateIsSyncedReceipt(ReceiptMapper mapper) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {
			ContentValues values = new ContentValues();

			values.put(dbHelper.FPRECHED_ISSYNCED, "1");

			if (mapper.isSynced()) {
				count = dB.update(dbHelper.TABLE_FPRECHED, values, dbHelper.FPRECDET_REFNO + " =?",
						new String[] { String.valueOf(mapper.getFPRECHED_REFNO()) });
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

	@SuppressWarnings("static-access")
	public int updateIsSyncedMReceipt(ReceiptMapper mapper) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {
			ContentValues values = new ContentValues();

			values.put(dbHelper.FPRECHED_ISSYNCED, "1");

			if (mapper.isSynced()) {
				count = dB.update(dbHelper.TABLE_FPRECHED, values, dbHelper.FPRECHED_REFNO1 + " =?",
						new String[] { String.valueOf(mapper.getFPRECHED_REFNO()) });
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
	
	/**
	 * 
	 * @return RefNo2ForRecDet
	 */
	
	@SuppressWarnings("static-access")
	public String getRefNo2ForRecDet(String code, String status) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FPRECHED + " WHERE " + DatabaseHelper.FPRECHED_DEBCODE
				+ " = '" + code + "' AND " + DatabaseHelper.FPRECHED_ISACTIVE + " = '" + status + "'";

		Cursor cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {

			return cursor.getString(cursor.getColumnIndex(dbHelper.FPRECHED_REFNO));

		}

		return "";
	}

	@SuppressWarnings("static-access")
	public String getChequeDate(String refno) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FPRECHED + " WHERE " + DatabaseHelper.FPRECHED_REFNO
				+ " = '" + refno + "'";

		Cursor cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {

			return cursor.getString(cursor.getColumnIndex(dbHelper.FPRECHED_CHQDATE));

		}

		return "";
	}
	
	@SuppressWarnings("static-access")
	public String getChequeNo(String refno) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FPRECHED + " WHERE " + DatabaseHelper.FPRECHED_REFNO
				+ " = '" + refno + "'";

		Cursor cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {

			return cursor.getString(cursor.getColumnIndex(dbHelper.FPRECHED_CHQNO));

		}

		return "";
	}
	
	public int getHeaderCountForNnumVal(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE "
					+ DatabaseHelper.FPRECHED_REFNO1 + " = '" + refno + "'";

			cursor = dB.rawQuery(selectQuery, null);

			count = cursor.getCount();

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

	@SuppressWarnings("static-access")
	public int deleteData(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FPRECHED + " WHERE " + dbHelper.FPRECDET_REFNO1
					+ " = '" + refno + "'";
			cursor = dB.rawQuery(selectQuery, null);
			int cn = cursor.getCount();

			if (cn > 0) {
				int success = dB.delete(dbHelper.TABLE_FPRECHED, dbHelper.FPRECDET_REFNO1 + " ='" + refno + "'", null);
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

	public void UpdateRecHeadTotalAmount(String refNo) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;
		try {
			// commented due to pass RefNo instead of RefNo1
//			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE "
//					+ DatabaseHelper.FPRECHED_REFNO1 + " = '" + refNo + "'";

			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE "
					+ DatabaseHelper.FPRECHED_REFNO + " = '" + refNo + "'";

			cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {
				// commented due to refNo = refno
				// String refno = cursor.getString(cursor.getColumnIndex(dbHelper.FPRECHED_REFNO));
//				String query = "Update " + DatabaseHelper.TABLE_FPRECHED
//						+ " set TotalAmt = (select sum(AloAmt) Aloamt from fprecdet where refno='" + refno
//						+ "' ) where refno='" + refno + "'";

				String query = "Update " + DatabaseHelper.TABLE_FPRECHED
						+ " set TotalAmt = (select sum(AloAmt) Aloamt from fprecdet where refno='" + refNo
						+ "' ) where refno='" + refNo + "'";
				dB.execSQL(query);
			}
			

		} catch (Exception e) {
			Log.v(TAG + " Exception", e.toString());
		} finally {
			dB.close();
		}

	}
	
	public int DeleteHedUnnessary() {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;
		int result = 0;
		int result2 = 0;
		try {
			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE totalamt is null ";
					

			cursor = dB.rawQuery(selectQuery, null);

			cursor .moveToFirst();
			//int raws = cursor.getCount();
			
			//cursor.
			
			 while(!cursor.isAfterLast()) {

				 String refno = cursor.getString(cursor.getColumnIndex(dbHelper.FPRECHED_REFNO));
				 result = dB.delete(DatabaseHelper.TABLE_FPRECHED, DatabaseHelper.FPRECHED_REFNO + "=?",
						 new String[] { refno });
				 
				 result2=result2+1;
				 
		        cursor.moveToNext();
		    }
			 
//			while (cursor.moveToNext()) {
//				String refno = cursor.getString(cursor.getColumnIndex(dbHelper.FPRECHED_REFNO));
//				
//				result = dB.delete(DatabaseHelper.TABLE_FPRECHED, DatabaseHelper.FPRECHED_REFNO + "=?",
//						new String[] { refno });
//				result2=result2+1;
//				//String query = "Delete FROM " + DatabaseHelper.TABLE_FPRECHED + " where refno='" + refno + "'";
//				//dB.execSQL(query);
//			}
			

		}
//		try {
//
//				String query = "Delete from " + DatabaseHelper.TABLE_FPRECHED+ " where totalamt is null ";
//				dB.execSQL(query);						
//
//		}
		catch (Exception e) {
			Log.v(TAG + " Exception", e.toString());
		} finally {
			dB.close();
		}
		return result2;
	}
	
	public ArrayList<ReceiptMapper> getAllUnsyncedRecHedReceipt() {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<ReceiptMapper> list = new ArrayList<ReceiptMapper>();
		String selectQuery;

		try {
			selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " Where "
					+ DatabaseHelper.FPRECHED_ISACTIVE + "='0' and " + DatabaseHelper.FPRECHED_ISSYNCED + "='0'";

//			localSP = context.getSharedPreferences(SETTINGS,
//					Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
			localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				ReceiptMapper mapper = new ReceiptMapper();

				mapper.setNextNumVal(new CompanyBranchDS(context)
						.getCurrentNextNumVal(context.getResources().getString(R.string.RecNumVal)));

				mapper.setDistDB(localSP.getString("Dist_DB", "").toString());
				mapper.setConsoleDB(localSP.getString("Console_DB", "").toString());
//				mapper.setSALEREP_DEALCODE(new SalRepDS(context).getDealCode());
//				mapper.setSALEREP_AREACODE(new SalRepDS(context).getAreaCode());

				mapper.setFPRECHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDDATE)));
				mapper.setFPRECHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDMACH)));
				mapper.setFPRECHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDRESS)));
				mapper.setFPRECHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDUSER)));
				mapper.setFPRECHED_BTOTALAMT(
						cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BTOTALAMT)));
				mapper.setFPRECHED_CHQDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQDATE)));
				mapper.setFPRECHED_CHQNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQNO)));
				mapper.setFPRECHED_CURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURCODE)));
				mapper.setFPRECHED_CURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE)));
				mapper.setFPRECHED_CURRATE1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE1)));
				mapper.setFPRECHED_CUSBANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CUSBANK)));
				mapper.setFPRECHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_DEBCODE)));
				mapper.setFPRECHED_END_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_END_TIME)));
				mapper.setFPRECHED_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ID)));
				mapper.setFPRECHED_ISACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISACTIVE)));
				mapper.setFPRECHED_ISSYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISSYNCED)));
				mapper.setFPRECHED_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LATITUDE)));
				mapper.setFPRECHED_LONGITUDE(
						cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LONGITUDE)));
				mapper.setFPRECHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_MANUREF)));
				mapper.setFPRECHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PAYTYPE)));
				mapper.setFPRECHED_PRTCOPY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PRTCOPY)));
				mapper.setFPRECHED_RECORDID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_RECORDID)));
				mapper.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO)));
				mapper.setFPRECHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REMARKS)));
				mapper.setFPRECHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REPCODE)));
				mapper.setFPRECHED_SALEREFNO(
						cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_SALEREFNO)));
				mapper.setFPRECHED_START_TIME(
						cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_START_TIME)));
				mapper.setFPRECHED_TIMESTAMP(
						cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TIMESTAMP)));
				mapper.setFPRECHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TOTALAMT)));
				mapper.setFPRECHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNDATE)));
				mapper.setFPRECHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNTYPE)));

				mapper.setRecDetList(new RecDetDS(context)
						.GetReceiptByRefno(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO))));

				list.add(mapper);

			}
			cursor.close();
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return list;
	}

	// --------------------------------------------- Nuwan ------ 11/09/2018 ---------------------------------------------------------

	public boolean isAnyActiveRecHed() {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		boolean res = false;

		Cursor cursor = null;
		try {
			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE " + DatabaseHelper.FPRECHED_ISACTIVE + "='1'";
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

	public RecHed getActiveRecHed() {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " Where " + DatabaseHelper.FPRECHED_ISACTIVE + "='1' and " + DatabaseHelper.FPRECHED_ISSYNCED + "='0'";
		Cursor cursor = dB.rawQuery(selectQuery, null);
		RecHed recHed = new RecHed();

		try {
			while (cursor.moveToNext()) {


				recHed.setFPRECHED_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDMACH)));
				recHed.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO)));
				recHed.setFPRECHED_REFNO1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO1)));
				recHed.setFPRECHED_MANUREF(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_MANUREF)));
				recHed.setFPRECHED_SALEREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_SALEREFNO)));
				recHed.setFPRECHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REPCODE)));
				recHed.setFPRECHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNTYPE)));
				recHed.setFPRECHED_CHQNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQNO)));
				recHed.setFPRECHED_CHQDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CHQDATE)));
				recHed.setFPRECHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TXNDATE)));
				recHed.setFPRECHED_CURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURCODE)));
				recHed.setFPRECHED_CURRATE1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE1)));
				recHed.setFPRECHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_DEBCODE)));
				recHed.setFPRECHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_TOTALAMT)));
				recHed.setFPRECHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BTOTALAMT)));
				recHed.setFPRECHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PAYTYPE)));
				recHed.setFPRECHED_PRTCOPY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_PRTCOPY)));
				recHed.setFPRECHED_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REMARKS)));
				recHed.setFPRECHED_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDUSER)));
				recHed.setFPRECHED_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDDATE)));
				recHed.setFPRECHED_CURRATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CURRATE)));
				recHed.setFPRECHED_CUSBANK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_CUSBANK)));
				recHed.setFPRECHED_COSTCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_COST_CODE)));
				recHed.setFPRECHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LONGITUDE)));
				recHed.setFPRECHED_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_LATITUDE)));
				recHed.setFPRECHED_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ADDRESS)));
				recHed.setFPRECHED_START_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_START_TIME)));
				recHed.setFPRECHED_END_TIME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_END_TIME)));
				recHed.setFPRECHED_ISACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISACTIVE)));
				recHed.setFPRECHED_ISSYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISSYNCED)));
				recHed.setFPRECHED_ISDELETE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_ISDELETE)));
				recHed.setFPRECHED_BANKCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BANKCODE)));
				recHed.setFPRECHED_BRANCHCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_BRANCHCODE)));

			}
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			dB.close();
		}

		return recHed;
	}

	public int clearRecHedByComRefNo(String comRefNo) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		int result = 0;
		try {
			result = dB.delete(DatabaseHelper.TABLE_FPRECHED, DatabaseHelper.FPRECHED_COMMON_REFNO + "=?",
					new String[] { comRefNo });

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return result;
	}

	public ArrayList<RecHed> getHedRefNoByCommonRef(String comRefNo) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<RecHed> list = new ArrayList<RecHed>();
		try {

			String selectQuery;

			selectQuery = "select * from " + DatabaseHelper.TABLE_FPRECHED + " WHERE " + " MRefNo ='" + comRefNo + "'";

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				RecHed recHed = new RecHed();

				recHed.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO)));

				list.add(recHed);

			}
			cursor.close();
		} catch (Exception e) {
			Log.v(TAG, e.toString());

		} finally {
			dB.close();
		}

		return list;
	}

	public String getActiveReceiptByComRef() {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String res = null;

		Cursor cursor = null;
		try {
			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE " + DatabaseHelper.FPRECHED_ISACTIVE + "='1'";
			cursor = dB.rawQuery(selectQuery, null);

			if (cursor.getCount() > 0) {
				cursor.moveToNext();
				res = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_COMMON_REFNO));
				//res += " ( " + new DebtorDS(context).getDebNameByCode(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_DEBCODE))) + " )";
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

	public String getActiveReceiptByRef() {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String res = null;

		Cursor cursor = null;
		try {
			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE " + DatabaseHelper.FPRECHED_ISACTIVE + "='1'";
			cursor = dB.rawQuery(selectQuery, null);

			if (cursor.getCount() > 0) {
				cursor.moveToNext();
				res = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO));
				//res += " ( " + new DebtorDS(context).getDebNameByCode(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_DEBCODE))) + " )";
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

	public String getActiveReceiptByRefWithDebtor() {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String res = null;

		Cursor cursor = null;
		try {
			String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FPRECHED + " WHERE " + DatabaseHelper.FPRECHED_ISACTIVE + "='1'";
			cursor = dB.rawQuery(selectQuery, null);

			if (cursor.getCount() > 0) {
				cursor.moveToNext();
				res = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_REFNO));
				//res += " ( " + new DebtorDS(context).getDebNameByCode(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPRECHED_DEBCODE))) + " )";
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
}
