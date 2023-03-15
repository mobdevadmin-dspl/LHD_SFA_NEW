package com.lankahardwared.lankahw.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.model.FInvRDet;

import java.util.ArrayList;

public class FInvRDetDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "LHD";

    public FInvRDetDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateInvRDet(ArrayList<FInvRDet> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (FInvRDet invrDet : list) {

                ContentValues values = new ContentValues();

                String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FINVRDET
                        + " WHERE " + dbHelper.FINVRDET_ID + " = '"
                        + invrDet.getFINVRDET_ID() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                values.put(dbHelper.FINVRDET_REFNO, invrDet.getFINVRDET_REFNO());
                values.put(dbHelper.FINVRDET_TXN_DATE, invrDet.getFINVRDET_TXN_DATE());
                values.put(dbHelper.FINVRDET_QTY, invrDet.getFINVRDET_QTY());
                values.put(dbHelper.FINVRDET_BAL_QTY, invrDet.getFINVRDET_BAL_QTY());
                values.put(dbHelper.FINVRDET_ITEMCODE, invrDet.getFINVRDET_ITEMCODE());
                values.put(dbHelper.FINVRDET_TAXCOMCODE, invrDet.getFINVRDET_TAXCOMCODE());
                values.put(dbHelper.FINVRDET_PRILCODE, invrDet.getFINVRDET_PRILCODE());
                values.put(dbHelper.FINVRDET_IS_ACTIVE, invrDet.getFINVRDET_IS_ACTIVE());
                values.put(dbHelper.FINVRDET_COST_PRICE, invrDet.getFINVRDET_COST_PRICE());
                values.put(dbHelper.FINVRDET_SELL_PRICE, invrDet.getFINVRDET_SELL_PRICE());
                values.put(dbHelper.FINVRDET_T_SELL_PRICE, invrDet.getFINVRDET_T_SELL_PRICE());
                values.put(dbHelper.FINVRDET_AMT, invrDet.getFINVRDET_AMT());
                values.put(dbHelper.FINVRDET_DIS_AMT, invrDet.getFINVRDET_DIS_AMT());
                values.put(dbHelper.FINVRDET_TAX_AMT, invrDet.getFINVRDET_TAX_AMT());
                values.put(dbHelper.FINVRDET_TXN_TYPE, invrDet.getFINVRDET_TXN_TYPE());
                values.put(dbHelper.FINVRDET_SEQNO, invrDet.getFINVRDET_SEQNO());
                values.put(dbHelper.FINVRDET_REASON_NAME, invrDet.getFINVRDET_RETURN_REASON());
                values.put(dbHelper.FINVRDET_REASON_CODE, invrDet.getFINVRDET_RETURN_REASON_CODE());
                values.put(dbHelper.FINVRDET_RETURN_TYPE, invrDet.getFINVRDET_RETURN_TYPE());
                values.put(dbHelper.FINVRDET_FREE_QTY, invrDet.getFINVRDET_FREE_QTY());
                values.put(dbHelper.FINVRDET_PRICE, invrDet.getFINVRDET_PRICE());
                values.put(dbHelper.FINVRDET_CHANGED_PRICE, invrDet.getFINVRDET_CHANGED_PRICE());


                int cn = cursor.getCount();
                if (cn > 0) {

                    count = dB.update(dbHelper.TABLE_FINVRDET, values, dbHelper.FINVRDET_ID + " =?", new String[]{String
                            .valueOf(invrDet.getFINVRDET_ID())});

                } else {
                    count = (int) dB.insert(dbHelper.TABLE_FINVRDET, null, values);
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

    public int updateProductPrice(String itemCode, String price) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FINVRDET_SELL_PRICE, price);
            values.put(DatabaseHelper.FINVRDET_T_SELL_PRICE, price);
            count=(int)dB.update(DatabaseHelper.TABLE_FINVRDET, values, DatabaseHelper.FINVRDET_ITEMCODE + " =?", new String[]{String.valueOf(itemCode)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
        return  count;
    }
    public ArrayList<FInvRDet> getAllInvRDet(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FInvRDet> list = new ArrayList<FInvRDet>();

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FINVRDET
                + " WHERE "
                + DatabaseHelper.FINVRDET_REFNO + "='" + refno + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            FInvRDet invrDet = new FInvRDet();

            invrDet.setFINVRDET_ID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_ID)));
            invrDet.setFINVRDET_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_AMT)));
            invrDet.setFINVRDET_COST_PRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_COST_PRICE)));
            invrDet.setFINVRDET_SELL_PRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_SELL_PRICE)));
            invrDet.setFINVRDET_T_SELL_PRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_T_SELL_PRICE)));
            invrDet.setFINVRDET_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_IS_ACTIVE)));
            invrDet.setFINVRDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_ITEMCODE)));
            invrDet.setFINVRDET_QTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_QTY)));
            invrDet.setFINVRDET_BAL_QTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_BAL_QTY)));
            invrDet.setFINVRDET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_REFNO)));
            invrDet.setFINVRDET_TXN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_TXN_DATE)));
            invrDet.setFINVRDET_TAXCOMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_TAXCOMCODE)));
            invrDet.setFINVRDET_PRILCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_PRILCODE)));
            invrDet.setFINVRDET_DIS_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_DIS_AMT)));
            invrDet.setFINVRDET_TAX_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_TAX_AMT)));
            invrDet.setFINVRDET_TXN_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_TXN_TYPE)));
            invrDet.setFINVRDET_SEQNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_SEQNO)));
            invrDet.setFINVRDET_RETURN_REASON(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_REASON_NAME)));
            invrDet.setFINVRDET_RETURN_REASON_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_REASON_CODE)));
            invrDet.setFINVRDET_RETURN_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_RETURN_TYPE)));
            invrDet.setFINVRDET_PRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_PRICE)));
            invrDet.setFINVRDET_CHANGED_PRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_CHANGED_PRICE)));
            invrDet.setFINVRDET_DIS_RATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_DIS_RATE)));
            list.add(invrDet);

        }

        return list;
    }


//	public int deleteInvRDetByID(String id) {
//
//		int count = 0;
//
//		if (dB == null) {
//			open();
//		} else if (!dB.isOpen()) {
//			open();
//		}
//		Cursor cursor = null;
//		try {
//
//			cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FINVRDET+ " WHERE " + dbHelper.FINVRDET_ID + "='" + id + "'", null);
//			count = cursor.getCount();
//			if (count > 0) {
//				int success = dB.delete(dbHelper.TABLE_FINVRDET,dbHelper.FINVRDET_ID + "='" + id + "'", null);
//				Log.v("OrdDet Deleted ", success + "");
//			}
//		} catch (Exception e) {
//
//			Log.v(TAG + " Exception", e.toString());
//
//		} finally {
//			if (cursor != null) {
//				cursor.close();
//			}
//			dB.close();
//		}
//
//		return count;
//
//	}


//	public ArrayList<FInvRDet> getAllActiveVanDet(String refno) {
//
//		if (dB == null) {
//			open();
//		} else if (!dB.isOpen()) {
//			open();
//		}
//
//		ArrayList<FInvRDet> list = new ArrayList<FInvRDet>();
//		//
//		// String selectQuery = "select * from "+dbHelper.TABLE_FORDDET
//		// +" WHERE "+dbHelper.FORDDET_TXN_TYPE+"!='22' AND "+dbHelper.FORDDET_REFNO+"='"+refno+"'";
//
//		String selectQuery = "select * from " + dbHelper.TABLE_FINVRDET
//				+ " WHERE "
//				+ dbHelper.FINVRDET_REFNO + "='"
//				+ refno + "'";
//
//		Cursor cursor = dB.rawQuery(selectQuery, null);
//		while (cursor.moveToNext()) {
//
//			FInvRDet invrDet = new FInvRDet();
//
//			invrDet.setFINVRDET_ID(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_ID)));
//			invrDet.setFINVRDET_AMT(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_AMT)));
//			invrDet.setFINVRDET_COST_PRICE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_COST_PRICE)));
//			invrDet.setFINVRDET_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_IS_ACTIVE)));
//			invrDet.setFINVRDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_ITEMCODE)));
//			invrDet.setFINVRDET_QTY(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_QTY)));
//			invrDet.setFINVRDET_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_REFNO)));
//			invrDet.setFINVRDET_TXN_DATE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_TXN_DATE)));
//			invrDet.setFINVRDET_PACKSIZE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_PACKSIZE)));
//			
//			
//			list.add(invrDet);
//
//		}
//
//		return list;
//	}

//	public String getGrossValue(String refno) {
//		if (dB == null) {
//			open();
//		} else if (!dB.isOpen()) {
//			open();
//		}
//
//		// String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FREASON
//		// +" WHERE "+dbHelper.FREASON_NAME+"='"+name+"'";
//		@SuppressWarnings("static-access")
//		String selectQuery = "SELECT SUM(" + dbHelper.FINVRDET_QTY + " * "
//				+ dbHelper.FINVRDET_COST_PRICE + ") AS 'Gross Value'  FROM "
//				+ dbHelper.TABLE_FINVRDET + " WHERE " 
//				+ dbHelper.FINVRDET_REFNO + "='" + refno + "'";
//		Cursor cursor = null;
//		cursor = dB.rawQuery(selectQuery, null);
//
//		while (cursor.moveToNext()) {
//			if (cursor.getString(cursor.getColumnIndex("Gross Value")) != null)
//				return cursor.getString(cursor.getColumnIndex("Gross Value"));
//			else
//				return "0.00";
//		}
//
//		return "0.00";
//	}

//	public String getTotalReturns(String refno) {
//		if (dB == null) {
//			open();
//		} else if (!dB.isOpen()) {
//			open();
//		}
//
//		// String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FREASON
//		// +" WHERE "+dbHelper.FREASON_NAME+"='"+name+"'";
//		@SuppressWarnings("static-access")
//		String selectQuery = "SELECT SUM(" + dbHelper.FINVDET_BAL_QTY + " * "
//				+ dbHelper.FINVDET_B_SELL_PRICE + ") AS 'Total Return'  FROM "
//				+ dbHelper.TABLE_FINVDET + " WHERE " + dbHelper.FINVDET_TYPE
//				+ " IN('MR','UR') AND " + dbHelper.FINVDET_REFNO + "='" + refno
//				+ "'";
//		Cursor cursor = null;
//		cursor = dB.rawQuery(selectQuery, null);
//
//		while (cursor.moveToNext()) {
//			if (cursor.getString(cursor.getColumnIndex("Total Return")) != null)
//				return cursor.getString(cursor.getColumnIndex("Total Return"));
//			else
//				return "0.00";
//
//		}
//
//		return "0.00";
//	}

//	public String getTotalLineDiscount(String refno) {
//		if (dB == null) {
//			open();
//		} else if (!dB.isOpen()) {
//			open();
//		}
//
//		String selectQuery = "SELECT SUM((BalQty * BSellPrice)* disper/100  + DisValAmt) AS 'Total line'  FROM finvdet WHERE types ='SA' AND "
//				+ dbHelper.FINVDET_REFNO + "='" + refno + "'";
//		Cursor cursor = null;
//		cursor = dB.rawQuery(selectQuery, null);
//
//		while (cursor.moveToNext()) {
//
//			if (cursor.getString(cursor.getColumnIndex("Total line")) != null)
//				return cursor.getString(cursor.getColumnIndex("Total line"));
//			else
//				return "0.00";
//
//		}
//
//		return "0.00";
//	}

    public int InactiveStatusUpdate(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FINVRDET + " WHERE " + DatabaseHelper.FINVRDET_REFNO + " = '" + refno + "'";

            cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.FINVRDET_IS_ACTIVE, "0");

            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.update(DatabaseHelper.TABLE_FINVRDET, values, DatabaseHelper.FINVRDET_REFNO + " =?", new String[]{String.valueOf(refno)});
            } else {
                count = (int) dB.insert(DatabaseHelper.TABLE_FINVRDET, null, values);
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


    public int restData(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            count = dB.delete(DatabaseHelper.TABLE_FINVRDET, DatabaseHelper.FINVRDET_REFNO + " ='" + refno + "'", null);

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


    public int deleteRetDetByID(String id) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + dbHelper.TABLE_FINVRDET + " WHERE " + dbHelper.FINVRDET_ID + "='" + id + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(dbHelper.TABLE_FINVRDET, dbHelper.FINVRDET_ID + "='" + id + "'", null);
                Log.v("FinvrDet Deleted ", success + "");
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


//	public ArrayList<FInvRDet> getAllInvrDetDelete(String refno) {
//		if (dB == null) {
//			open();
//		} else if (!dB.isOpen()) {
//			open();
//		}
//
//		ArrayList<FInvRDet> list = new ArrayList<FInvRDet>();
//
//		String selectQuery = "select * from " + dbHelper.TABLE_FINVRDET
//				+ " WHERE " 
//				+ dbHelper.FINVRDET_REFNO + "='" + refno + "'  and ISACTIVE = '0' Order by FINVRDET_ID DESC";
//
//		Cursor cursor = dB.rawQuery(selectQuery, null);
//		while (cursor.moveToNext()) {
//
//			FInvRDet invrDet = new FInvRDet();
//
//			invrDet.setFINVRDET_ID(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_ID)));
//			invrDet.setFINVRDET_AMT(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_AMT)));
//			invrDet.setFINVRDET_COST_PRICE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_COST_PRICE)));
//			invrDet.setFINVRDET_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_IS_ACTIVE)));
//			invrDet.setFINVRDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_ITEMCODE)));
//			invrDet.setFINVRDET_QTY(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_QTY)));
//			invrDet.setFINVRDET_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_REFNO)));
//			invrDet.setFINVRDET_TXN_DATE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_TXN_DATE)));
//			invrDet.setFINVRDET_PACKSIZE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_PACKSIZE)));
//			
//
//			list.add(invrDet);
//
//		}
//
//		return list;
//	}


//	public ArrayList<FInvRDet> getAllUnsyncedUpload(String refno) {
//		if (dB == null) {
//			open();
//		} else if (!dB.isOpen()) {
//			open();
//		}
//		ArrayList<FInvRDet> list = new ArrayList<FInvRDet>();
//		Cursor cursor = null;
//	try {
//			
//		@SuppressWarnings("static-access")
//		String selectQuery = "select * from " + dbHelper.TABLE_FINVRDET
//		+ " WHERE " + dbHelper.FINVRDET_REFNO + "='" + refno + "' and  " + dbHelper.FINVRDET_IS_ACTIVE + "=0";
//	
//		cursor = dB.rawQuery(selectQuery, null);
//		
//		while(cursor.moveToNext()){
//			
//			FInvRDet invrtDet =new FInvRDet();
//		
//			//invrtDet.setFINVRTDET_ID(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRTDET_ID)));
//			invrtDet.setFINVRDET_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_REFNO)));
//			invrtDet.setFINVRDET_AMT(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_AMT)));
//			invrtDet.setFINVRDET_COST_PRICE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_COST_PRICE)));
//			invrtDet.setFINVRDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_ITEMCODE)));
//			invrtDet.setFINVRDET_TXN_DATE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_TXN_DATE)));
//			invrtDet.setFINVRDET_QTY(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_QTY)));
//			invrtDet.setFINVRDET_EXPR_DATE(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_EXPR_DATE)));
//			invrtDet.setFINVRDET_FREE_QTY(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_FREE_QTY)));
//			invrtDet.setFINVRDET_RETURN_REASON(cursor.getString(cursor.getColumnIndex(dbHelper.FINVRDET_RETURN_REASON)));
//			
//			
//			list.add(invrtDet);
//			
//		}
//		
//		
//		
//		}catch (Exception e) {
//			// TODO: handle exception
//		
//		}finally{  
//			if (cursor !=null) {
//				cursor.close();
//				}
//			dB.close();
//			}
//	return list;
// }

    public int getItemCount(String refNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            String selectQuery = "SELECT count(RefNo) as RefNo FROM " + DatabaseHelper.TABLE_FINVRDET + " WHERE  " + DatabaseHelper.FINVRDET_REFNO + "='" + refNo + "'";
            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                return Integer.parseInt(cursor.getString(cursor.getColumnIndex("RefNo")));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dB.close();
        }
        return 0;

    }

    // --------------------------------------- Nuwan 22/08/2018 ---------------------------------------------

    public ArrayList<FInvRDet> getReturnItemsforPrint(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        ArrayList<FInvRDet> list = new ArrayList<FInvRDet>();

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FINVRDET + " WHERE " + DatabaseHelper.FINVRDET_REFNO + "='" + refno + "'";

        try {

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                FInvRDet reDet = new FInvRDet();

                reDet.setFINVRDET_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_AMT)));
                reDet.setFINVRDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_ITEMCODE)));
                reDet.setFINVRDET_QTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_QTY)));
                reDet.setFINVRDET_TXN_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_TXN_TYPE)));
                reDet.setFINVRDET_REFNO(refno);
                reDet.setFINVRDET_DIS_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_DIS_AMT)));
                //reDet.setFTRANSODET_SCHDISC(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_DISVALAMT)));
                reDet.setFINVRDET_SELL_PRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_SELL_PRICE)));
                reDet.setFINVRDET_T_SELL_PRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_T_SELL_PRICE)));
                reDet.setFINVRDET_RETURN_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_RETURN_TYPE)));
                reDet.setFINVRDET_TAX_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_TAX_AMT)));

                list.add(reDet);
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return list;
    }

    public FInvRDet getReturnItemDetailsForPrint(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        FInvRDet REDet = new FInvRDet();

        String selectQuery = "select * from " + DatabaseHelper.TABLE_FINVRDET + " WHERE " + DatabaseHelper.FINVRDET_REFNO + "='" + refno + "'";

        try {

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                REDet.setFINVRDET_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_AMT)));
                REDet.setFINVRDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_ITEMCODE)));
                REDet.setFINVRDET_QTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_QTY)));
                REDet.setFINVRDET_TXN_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_TXN_TYPE)));
                REDet.setFINVRDET_REFNO(refno);
                REDet.setFINVRDET_DIS_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_DIS_AMT)));
                //reDet.setFTRANSODET_SCHDISC(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSODET_DISVALAMT)));
                REDet.setFINVRDET_SELL_PRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_SELL_PRICE)));

            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return REDet;
    }

    // -------------------------------------------------- Nuwan ---------------------------------------------------------------------

    public ArrayList<FInvRDet> getEveryItem(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FInvRDet> list = new ArrayList<FInvRDet>();

        String selectQuery = "select Itemcode,RefNo,TaxComCode,Price,Qty,Amt,ReturnType,DisAmt from " + DatabaseHelper.TABLE_FINVRDET + " WHERE RefNo='" + refno + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                FInvRDet IRDet = new FInvRDet();

                IRDet.setFINVRDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_ITEMCODE)));
                IRDet.setFINVRDET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_REFNO)));
                IRDet.setFINVRDET_TAXCOMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_TAXCOMCODE)));
                IRDet.setFINVRDET_T_SELL_PRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_PRICE)));
                IRDet.setFINVRDET_QTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_QTY)));
                IRDet.setFINVRDET_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_AMT)));
                IRDet.setFINVRDET_RETURN_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_RETURN_TYPE)));
                IRDet.setFINVRDET_DIS_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVRDET_DIS_AMT)));

                list.add(IRDet);

            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return list;
    }

    public void UpdateItemTaxInfo(ArrayList<FInvRDet> list, String debtorCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        double totTax = 0, totalAmt = 0, bTSellPrice = 0, tSellPrice = 0, totDisc=0, disR=0;

        try {

            for (FInvRDet invRDet : list) {

                /* Calculate only for MR or UR */
                if (invRDet.getFINVRDET_RETURN_TYPE().equals("SA")) {

                    String sArray[] = new TaxDetDS(context).calculateTaxForwardFromDebTax(debtorCode, invRDet.getFINVRDET_ITEMCODE(), Double.parseDouble(invRDet.getFINVRDET_AMT()));

                    bTSellPrice = Double.parseDouble(sArray[0])/Double.parseDouble(invRDet.getFINVRDET_QTY());
                    if (Double.parseDouble(invRDet.getFINVRDET_DIS_AMT())>0.0)
                    {
                        tSellPrice = (Double.parseDouble(sArray[0])+ Double.parseDouble(invRDet.getFINVRDET_DIS_AMT()))/Double.parseDouble(invRDet.getFINVRDET_QTY());
                        disR = ((Double.parseDouble(invRDet.getFINVRDET_DIS_AMT()))/(Double.parseDouble(sArray[0])+ Double.parseDouble(invRDet.getFINVRDET_DIS_AMT())))* 100;
                        totDisc += Double.parseDouble(invRDet.getFINVRDET_DIS_AMT());
                    }
                    else
                    {
                        tSellPrice = Double.parseDouble(sArray[0])/Double.parseDouble(invRDet.getFINVRDET_QTY());
                    }

                    totTax += Double.parseDouble(sArray[1]);
                    totalAmt += Double.parseDouble(sArray[0]);

                    String updateQuery = "UPDATE FInvRDet SET TaxAmt='" + sArray[1] + "', Amt='" + sArray[0] + "', DisRate='" + String.valueOf(disR) + "', TSellPrice ='" + tSellPrice + "' where Itemcode ='" + invRDet.getFINVRDET_ITEMCODE() + "' AND refno='" + invRDet.getFINVRDET_REFNO() + "' AND ReturnType!='FR'";
                    dB.execSQL(updateQuery);
                }
            }
            /* Update sales return Header TotalTax */
            dB.execSQL("UPDATE FInvRHed SET TotalTax='" + totTax + "',TotalDis='" + totDisc + "',TotalAmt='" + totalAmt + "' WHERE refno='" + list.get(0).getFINVRDET_REFNO() + "'");

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

    }


}
