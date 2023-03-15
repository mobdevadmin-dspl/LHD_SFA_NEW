package com.lankahardwared.lankahw.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class VanSalPrintDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";

    public VanSalPrintDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

	/*

	public ArrayList<VanSalPrintPre> getAllListPreview(String currentRefNo) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<VanSalPrintPre> list = new ArrayList<VanSalPrintPre>();

		try {
			String selectQuery = "SELECT FD.RefNo,FD.Itemcode,FD.Types,FD.Amt,FD.CaseQty,FD.Qty,FD.InvType,FD.PiceQty,FD.Types,FH.DebCode,FDE.DebName,FH.DisPer as a, FD.SeqNo,FH.TxnDate,FD.DisAmt,FH.TotalDis,FH.TotalAmt,FH.FIssType,FH.BTotalDis as c, FD.Disper as b,FD.disamt,FH.TotalDis,IM.Itemcode,IM.ItemName,FH.Remarks, FDE.DebAdd1,FDE.DebAdd2,FDE.DebAdd3,FDE.DebTele,FD.sellprice,FD.piceQty,FH.TotalitemDis,FH.TotMkrAmt,IM.NOUCase,FD.DisValAmt FROM Finvhed FH INNER JOIN FinvDet FD ON FD.InvRefNo = FH.RefNo INNER JOIN fDebtor FDE ON FDE.DebCode = FH.DebCode INNER JOIN fItem IM ON IM.Itemcode = FD.Itemcode WHERE  FD.InvRefNo = '" + currentRefNo + "' ORDER BY FD.TxnType";

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				VanSalPrintPre aVanSalPrintPre = new VanSalPrintPre();

				aVanSalPrintPre.setVAN_PRINT_REF_NO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_REFNO)));
				aVanSalPrintPre.setVAN_PRINT_ITEMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEM_ITEM_CODE)));
				aVanSalPrintPre.setVAN_PRINT_TRAN_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDET_TYPE)));
				aVanSalPrintPre.setVAN_PRINT_DETAIL_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDET_AMT)));
				aVanSalPrintPre.setVAN_PRINT_CASE_QTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDET_CASE_QTY)));
				aVanSalPrintPre.setVAN_PRINT_PIECE_QTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDET_PICE_QTY)));
				aVanSalPrintPre.setVAN_PRINT_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_DEB_CODE)));
				aVanSalPrintPre.setVAN_PRINT_DEBNAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_NAME)));
				aVanSalPrintPre.setVAN_HEAD_DIS_CALL(cursor.getString(cursor.getColumnIndex("a")));
				aVanSalPrintPre.setVAN_PRINT_SEQNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDET_SEQNO)));
				aVanSalPrintPre.setVAN_PRINT_TXN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_TXN_DATE)));
				aVanSalPrintPre.setVAN_PRINT_DISAMT_DETAIL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDET_DIS_AMT)));
				aVanSalPrintPre.setVAN_PRINT_TOTAL_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_TOTAL_AMT)));
				aVanSalPrintPre.setVAN_PRINT_TOTALDIS_HEAD_B(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_TOTAL_AMT))); // error
				aVanSalPrintPre.setVAN_PRINT_TOTAL_DIS_AMT(cursor.getString(cursor.getColumnIndex("c")));
				aVanSalPrintPre.setVAN_PRINT_DETAIL_DISPER(cursor.getString(cursor.getColumnIndex("b")));
				aVanSalPrintPre.setVAN_PRINT_TOTAL_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_TOTAL_AMT)));
				aVanSalPrintPre.setVAN_PRINT_TOTALDIS_HEAD(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_TOTALDIS)));
				aVanSalPrintPre.setVAN_PRINT_ITEMCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEM_ITEM_CODE)));
				aVanSalPrintPre.setVAN_PRINT_ITEMNAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEM_ITEM_NAME)));
				aVanSalPrintPre.setVAN_PRINT_REMARKS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_REMARKS)));
				aVanSalPrintPre.setVAN_PRINT_DEBADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD1)));
				aVanSalPrintPre.setVAN_PRINT_DEBADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD2)));
				aVanSalPrintPre.setVAN_PRINT_DEBADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_ADD3)));
				aVanSalPrintPre.setVAN_PRINT_DEBTELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDEBTOR_TELE)));
				aVanSalPrintPre.setVAN_PRINT_SELL_PRICE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORDDET_SELL_PRICE)));
				aVanSalPrintPre.setVAN_PRINT_TOTAL_UNIT_QTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FORDDET_CASE_QTY)));
				aVanSalPrintPre.setVAN_PRINT_ITEM_DIS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_TOTAL_ITM_DIS)));
				aVanSalPrintPre.setVAN_TOTALMKT_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_TOT_MKR_AMT)));
				aVanSalPrintPre.setVAN_PRINT_UNITS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FITEM_NOU_CASE)));
				aVanSalPrintPre.setVAN_DIS_VALUE_AMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDET_DIS_VAL_AMT)));
				
				aVanSalPrintPre.setVAN_HED_FISS_TYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_FISS_TYPE)));
				aVanSalPrintPre.setVAN_DET_QTY(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDET_QTY)));
				aVanSalPrintPre.setVAN_DET_INVTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVDET_INV_TYPE)));

				aVanSalPrintPre.setVAN_HEAD_DIS(cursor.getString(cursor.getColumnIndex("a")));

				Log.v("#######Testing print ########", aVanSalPrintPre.getVAN_PRINT_REF_NO());

				list.add(aVanSalPrintPre);
				System.out.println(list.size());
			}

			cursor.close();
			
		} catch (Exception e) {
			Log.v(TAG, e.toString());
		} finally {
			dB.close();
		}

		return list;
	}

	public String getTotalCaseQtyReturns(String refno) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "select sum(FD.CaseQty) as CaseQtySum from finvdet FD where InvRefNo = '" + refno + "'";
		Cursor cursor = null;
		cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {
			if (cursor.getString(cursor.getColumnIndex("CaseQtySum")) != null)
				return cursor.getString(cursor.getColumnIndex("CaseQtySum"));
			else
				return "0";

		}

		return "0";
	}

	public String getTotalPieceQtyReturns(String refno, String TranType) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "select sum(FD.Qty) as PieceQtySum from finvdet FD where InvRefNo ='" + refno + "' AND InvType='" + TranType + "'";
		Cursor cursor = null;
		cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {
			if (cursor.getString(cursor.getColumnIndex("PieceQtySum")) != null)
				return cursor.getString(cursor.getColumnIndex("PieceQtySum"));
			else
				return "0";

		}

		return "0";
	}
	
	
*/
}
