package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.lankahardwared.lankahw.model.ItemLoc;
import com.lankahardwared.lankahw.model.TranSODet;

import java.util.ArrayList;

public class ItemLocDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "Ebony";

    public ItemLocDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

	/*-*-*-**-*---*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*/
/*
    public int createOrUpdateItemLoc(ArrayList<ItemLoc> list) {

		int serverdbID = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		try {

			for (ItemLoc loc : list) {

				Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FITEMLOC + " WHERE " + DatabaseHelper.FITEMLOC_ITEM_CODE + " = '" + loc.getFITEMLOC_ITEM_CODE() + "' AND " + DatabaseHelper.FITEMLOC_LOC_CODE + " = '" + loc.getFITEMLOC_LOC_CODE() + "' ", null);

				ContentValues values = new ContentValues();

				values.put(DatabaseHelper.FITEMLOC_ITEM_CODE, loc.getFITEMLOC_ITEM_CODE());
				values.put(DatabaseHelper.FITEMLOC_LOC_CODE, loc.getFITEMLOC_LOC_CODE());
				values.put(DatabaseHelper.FITEMLOC_QOH, loc.getFITEMLOC_QOH());
				values.put(DatabaseHelper.FITEMLOC_RECORD_ID, loc.getFITEMLOC_RECORD_ID());

				if (cursor.getCount() > 0) {
					dB.update(DatabaseHelper.TABLE_FITEMLOC, values, DatabaseHelper.FITEMLOC_ITEM_CODE + " =?" + " AND " + DatabaseHelper.FITEMLOC_LOC_CODE + " =?", new String[] { String.valueOf(loc.getFITEMLOC_ITEM_CODE()), String.valueOf(loc.getFITEMLOC_LOC_CODE()) });
					Log.v("FITEMLOC : ", "Updated");
				} else {
					serverdbID = (int) dB.insert(DatabaseHelper.TABLE_FITEMLOC, null, values);
					Log.v("FITEMLOC : ", "INSERTED " + serverdbID);
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
	
	*/


    public void InsertOrReplaceItemLoc(ArrayList<ItemLoc> list) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + DatabaseHelper.TABLE_FITEMLOC + " (ItemCode,LocCode,QOH,RecordId) VALUES (?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (ItemLoc itemLoc : list) {

                stmt.bindString(1, itemLoc.getFITEMLOC_ITEM_CODE());
                stmt.bindString(2, itemLoc.getFITEMLOC_LOC_CODE());
                stmt.bindString(3, itemLoc.getFITEMLOC_QOH());
                stmt.bindString(4, itemLoc.getFITEMLOC_RECORD_ID());

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
	
	
	

	/*-*-*-**-*---*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*/

    public ArrayList<ItemLoc> getAllItemLoc() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<ItemLoc> list = new ArrayList<ItemLoc>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FITEMLOC + " WHERE " + DatabaseHelper.FITEMLOC_LOC_CODE + " ='MS'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            ItemLoc itemLoc = new ItemLoc();

            itemLoc.setFITEMLOC_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMLOC_ID)));
            itemLoc.setFITEMLOC_ITEM_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMLOC_ITEM_CODE)));
            itemLoc.setFITEMLOC_LOC_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMLOC_LOC_CODE)));
            itemLoc.setFITEMLOC_QOH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMLOC_QOH)));
            itemLoc.setFITEMLOC_RECORD_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMLOC_RECORD_ID)));

            list.add(itemLoc);

        }

        return list;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int deleteAllItemLoc() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FITEMLOC, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DatabaseHelper.TABLE_FITEMLOC, null, null);
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//	public void UpdatePreSaleQOH(String RefNo, String Task) {
//
//		if (dB == null) {
//			open();
//		} else if (!dB.isOpen()) {
//			open();
//		}
//
//		try {
//			String ResLoCode = new LocationsDS(context).GetReserveCode("LT4");
//			String LoCode = new SalRepDS(context).getCurrentSaleLocCode();
//
//			ArrayList<TranSODet> list = new TranSODetDS(context).getAllItemsforPrint(RefNo);
//
//			for (TranSODet item : list) {
//
//				double qoh = 0, ResQoh = 0;
//
//				Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FITEMLOC + " WHERE " + DatabaseHelper.FITEMLOC_ITEM_CODE + "='" + item.getFTRANSODET_ITEMCODE() + "' AND " + DatabaseHelper.FITEMLOC_LOC_CODE + "='" + LoCode + "'", null);
//				Cursor cursorRESERVE = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FITEMLOC + " WHERE " + DatabaseHelper.FITEMLOC_ITEM_CODE + "='" + item.getFTRANSODET_ITEMCODE() + "' AND " + DatabaseHelper.FITEMLOC_LOC_CODE + "='" + ResLoCode + "'", null);
//				double Qty = Double.parseDouble(item.getFTRANSODET_QTY());
//
//				if (cursor.getCount() > 0) {
//
//					while (cursor.moveToNext()) {
//						qoh = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMLOC_QOH)));
//					}
//					/* Reserve record value */
//					while (cursorRESERVE.moveToNext()) {
//						ResQoh = Double.parseDouble(cursorRESERVE.getString(cursorRESERVE.getColumnIndex(DatabaseHelper.FITEMLOC_QOH)));
//					}
//
//					ContentValues values = new ContentValues();
//					ContentValues valuesRES = new ContentValues();
//
//					if (Task.equals("+")) {
//						values.put(DatabaseHelper.FITEMLOC_QOH, String.valueOf(qoh + Qty));
//						valuesRES.put(DatabaseHelper.FITEMLOC_QOH, String.valueOf(ResQoh - Qty));
//					} else {
//						values.put(DatabaseHelper.FITEMLOC_QOH, String.valueOf(qoh - Qty));
//						valuesRES.put(DatabaseHelper.FITEMLOC_QOH, String.valueOf(ResQoh + Qty));
//					}
//
//					dB.update(DatabaseHelper.TABLE_FITEMLOC, values, DatabaseHelper.FITEMLOC_ITEM_CODE + "=? AND " + DatabaseHelper.FITEMLOC_LOC_CODE + "=?", new String[] { item.getFTRANSODET_ITEMCODE(), LoCode });
//					dB.update(DatabaseHelper.TABLE_FITEMLOC, valuesRES, DatabaseHelper.FITEMLOC_ITEM_CODE + "=? AND " + DatabaseHelper.FITEMLOC_LOC_CODE + "=?", new String[] { item.getFTRANSODET_ITEMCODE(), ResLoCode });
//
//				}
//
//				cursor.close();
//				cursorRESERVE.close();
//
//			}
//
//		} catch (Exception e) {
//			Log.v(TAG + " Exception", e.toString());
//		} finally {
//			dB.close();
//		}
//
//	}

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void UpdateInvoiceQOH(String RefNo, String Task, String locCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            //ArrayList<InvDet> list = new InvDetDS(context).getAllItemsforPrint(RefNo);
            ArrayList<TranSODet> list = new TranSODetDS(context).getAllItemsforPrint(RefNo);

            for (TranSODet item : list) {

                int qoh = 0;

                Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FITEMLOC + " WHERE " + DatabaseHelper.FITEMLOC_ITEM_CODE + "='" + item.getFTRANSODET_ITEMCODE() + "' AND " + DatabaseHelper.FITEMLOC_LOC_CODE + "='" + locCode + "'", null);
                int Qty = Integer.parseInt(item.getFTRANSODET_QTY());

                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {
                        qoh = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMLOC_QOH)));
                    }

                    ContentValues values = new ContentValues();

                    if (Task.equals("+")) {
                        values.put(DatabaseHelper.FITEMLOC_QOH, String.valueOf(qoh + Qty));
                    } else {
                        values.put(DatabaseHelper.FITEMLOC_QOH, String.valueOf(qoh - Qty));
                    }

                    dB.update(DatabaseHelper.TABLE_FITEMLOC, values, DatabaseHelper.FITEMLOC_ITEM_CODE + "=? AND " + DatabaseHelper.FITEMLOC_LOC_CODE + "=?", new String[]{item.getFTRANSODET_ITEMCODE(), locCode});

                }

                cursor.close();

            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public double GetQohByItemCode(String LocCode, String ItemCode) {

        double qoh = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            Cursor cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FITEMLOC + " WHERE " + DatabaseHelper.FITEMLOC_ITEM_CODE + "='" + ItemCode + "' AND " + DatabaseHelper.FITEMLOC_LOC_CODE + "='" + LocCode + "'", null);

            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    qoh = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMLOC_QOH)));
                }

            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }
        return qoh;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getAllitemloc(String Itemcode, String Loccode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String list = null;

        String selectQuery = "select QOH from FItemLoc where Itemcode = '" + Itemcode + "' AND LocCode ='" + Loccode + "' ";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            ItemLoc loc = new ItemLoc();

            // loc.setFITEMLOC_ITEM_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMLOC_ITEM_CODE)));
            // loc.setFITEMLOC_LOC_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMLOC_LOC_CODE)));
            loc.setFITEMLOC_QOH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEMLOC_QOH)));

            list = cursor.getString(0);

        }

        return list;
    }

}
