package com.lankahardwared.lankahw.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lankahardwared.lankahw.model.MonthSale;
import com.lankahardwared.lankahw.model.SalesStat;
import com.lankahardwared.lankahw.model.TopSales;

import java.util.ArrayList;

/**
 * Created by Sathiyaraja on 2/8/2018.
 */

public class DashboardDS {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";

    public DashboardDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public ArrayList<SalesStat> GetsalesStats() {

        ArrayList<SalesStat> salesStats = new ArrayList<>();
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        String selectQuery = "SELECT TxnDate,SUM(TotalAmt) as Total FROM " + DatabaseHelper.TABLE_FTRANSOHED + " WHERE strftime('%m',TxnDate) = strftime('%m',date('now')) GROUP BY TxnDate";
        //String selectQuery = "SELECT TxnDate,SUM(TotalAmt) as Total FROM " + DatabaseHelper.TABLE_FINVHED + " WHERE strftime('%m',TxnDate) = strftime('%m',date('now')) GROUP BY TxnDate";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                SalesStat stat = new SalesStat();
                String date[] = cursor.getString(cursor.getColumnIndex("TxnDate")).split("-");
                stat.setmDate(date[1] + "/" + (date[2]));
                stat.setTotalAmount(cursor.getFloat(cursor.getColumnIndex("Total")));

                salesStats.add(stat);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();

        }

        return salesStats;

    }

    public ArrayList<TopSales> GetTopSales(String month) {

        ArrayList<TopSales> temp = new ArrayList<>();
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        String selectQuery = "SELECT fi.RefNo,fi.TxnDate,SUM(TotalAmt) as total,fd.DebName,SUM(Qty) as topItem,fit.ItemName FROM finvHed as fi " +
                "INNER JOIN fDebtor as fd ON fi.DebCode=fd.DebCode " +
                "INNER JOIN finvDet as ft ON fi.RefNo=ft.RefNo " +
                "INNER JOIN fitem as fit ON ft.Itemcode=fit.Itemcode " +
                "WHERE  strftime('%m', fi.TxnDate) = '" + month + "' " +
                "GROUP BY fd.DebCode ORDER BY total DESC";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                TopSales topSales = new TopSales();
                topSales.setTopDate(cursor.getString(cursor.getColumnIndex("TxnDate")));
                topSales.setTopCustomer(cursor.getString(cursor.getColumnIndex("DebName")));
                topSales.setTopItem(cursor.getString(cursor.getColumnIndex("ItemName")));
                temp.add(topSales);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();

        }

        return temp;


    }

    public ArrayList<MonthSale> GetmonthSales() {

        ArrayList<MonthSale> monthSales = new ArrayList<>();
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        String selectQuery = "SELECT TxnDate,TotalAmt from finvHed GROUP BY strftime('%m',TxnDate)";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                MonthSale monthSale = new MonthSale();
                String Month = getMonthName(cursor.getString(cursor.getColumnIndex("TxnDate")));
                monthSale.setMonth(Month);
                monthSale.setSalesAmount(cursor.getFloat(cursor.getColumnIndex("TotalAmt")));
                monthSales.add(monthSale);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();

        }

        return monthSales;

    }

    public String getMonthName(String date) {

        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        String[] tempDate = date.split("-");

        int intmonth = Integer.parseInt(tempDate[1]);

        if (intmonth == 1) {
            intmonth = 0;
        } else if (intmonth == 2) {
            intmonth = 1;
        } else if (intmonth == 3) {
            intmonth = 2;
        } else if (intmonth == 4) {
            intmonth = 3;
        } else if (intmonth == 5) {
            intmonth = 4;
        } else if (intmonth == 6) {
            intmonth = 5;
        } else if (intmonth == 7) {
            intmonth = 6;
        } else if (intmonth == 8) {
            intmonth = 7;
        } else if (intmonth == 9) {
            intmonth = 8;
        } else if (intmonth == 10) {
            intmonth = 9;
        } else if (intmonth == 11) {
            intmonth = 10;
        } else if (intmonth == 12) {
            intmonth = 11;
        }
        return months[intmonth];

    }


}
