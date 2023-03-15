package com.lankahardwared.lankahw.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.lankahardwared.lankahw.model.SalRep;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *   create by kaveesha - 11-03-2020
 */

public class DashboardNewDS {

    private  DatabaseHelper dbHelper;
    private SQLiteDatabase dB;
    Context context;
    private String TAG = "DashboardNewDS";

    //Shared Peferences variables
    public static final String SETTINGS = "SETTINGS";

    public DashboardNewDS(Context context)
    {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException
    {
        dB =dbHelper.getWritableDatabase();
    }

    //get today achievement
    public double getDailyAchieve()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }


        Cursor cursor = null;
        double todayAchive = 0.0 ;

        String  repcode = new SalRepDS(context).getCurrentRepCode();


        try
        {
            String selectQuery = "select DayInvVal FROM fAchievement where RepCode = '" + repcode + "'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                todayAchive = Double.parseDouble(cursor.getString(cursor.getColumnIndex("DayInvVal")));
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getAchieve");
        }
        finally {
            if (cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return todayAchive;
    }

    //get today gross sale
    public double getDailyGross()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }


        Cursor cursor = null;
        double todayAchive = 0.0 ;

        int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int cMonth =Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int cDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));


        try
        {
            String selectQuery = "select ifnull(sum(Amt),0) as grossAmt from fTranSoDet where txndate = '" + cYear + "-" + String.format("%02d", cMonth) + "-" + String.format("%02d", cDate) + "'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                todayAchive = Double.parseDouble(cursor.getString(cursor.getColumnIndex("DayInvVal")));
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getGSale");
        }
        finally {
            if (cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return todayAchive;
    }

    //get today Return
    public Double getTodayReturn()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int month =Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int date = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));

        Cursor cursor = null;
        double result = 0;

        try
        {
            String selectQuery = "select ifnull(sum(Amt),0) as Ramount from fInvRdet  where txnDate = '" + year + "-" + String.format("%02d" , month) + "-" + String.format("%02d" , date) + "'";
            cursor = dB.rawQuery(selectQuery , null);

            while (cursor.moveToNext())
            {
                result = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Ramount")));
            }

        }catch (Exception e)
        {
                Log.d(TAG,"Exception getReturn");
        }
        finally
        {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return result;
    }

    //get Today productive count
    public int getProductivecount()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int cDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));

        Cursor cursor = null;
        int cProductive = 0;

        try
        {
            String selectQuery = "select count(DISTINCT debcode) from fTranSoHed where txndate =  '" + cYear + "-" + String.format("%02d", cMonth) + "-" + String.format("%02d", cDate) + "'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                cProductive = cursor.getInt(0);

                if(cProductive > 0)
                {
                    return cProductive;
                }
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getTodayProductive");
        }
        finally {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return cProductive;
    }


    //get Today Non-Productive count
    public int getNonProductiveCount()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int cDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));

        Cursor cursor = null;
        int cNonP = 0;

        try
        {
            String selectQuery = "select count(RepCode) from FDaynprdHed where txndate = '" + cYear + "-" + String.format("%02d", cMonth) + "-" + String.format("%02d", cDate) + "'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                cNonP = cursor.getInt(0);

                if(cNonP > 0)
                {
                    return cNonP;
                }
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getTodayNProductive");
        }
        finally {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return cNonP;
    }

    //get Today Discount
    public Double getDiscount()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int cDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));

        Cursor cursor = null;
        double discount = 0.0;

        try
        {
            String selectQuery = "select ifnull(sum(DisAmt),0) as Tdiscount from fordDisc where txndate = '" + cYear + "-" + String.format("%02d", cMonth) + "-" + String.format("%02d", cDate) + "'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                discount = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Tdiscount")));
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getTodayDiscount");
        }
        finally {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return discount;
    }

    //get this month Achieve
    public double getTMAchieve()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        String  repcode = new SalRepDS(context).getCurrentRepCode();

        Cursor cursor = null;
        double monthAchive = 0.0 ;

        try
        {
            String selectQuery = "select CumInvVal FROM fAchievement where RepCode = '" + repcode + "'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                monthAchive = Double.parseDouble(cursor.getString(cursor.getColumnIndex("CumInvVal")));
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getTMAchieve");
        }
        finally {
            if (cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return monthAchive;
    }

    //get this month gross sale
    public double getTMGross()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        Cursor cursor = null;
        double monthGross = 0.0 ;

        try
        {
            String selectQuery = "select ifnull(sum(Amt),0) as grossAmt from fTranSoDet where txndate LIKE '" + cYear + "-" + String.format("%02d",cMonth) + "-_%'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                monthGross = Double.parseDouble(cursor.getString(cursor.getColumnIndex("grossAmt")));
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getTMGrossSale");
        }
        finally {
            if (cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return monthGross;
    }

    //get this month Return
    public Double getTMReturn()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int month =Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        Cursor cursor = null;
        double result = 0.0;

        try
        {
            String selectQuery = "select ifnull(sum(Amt),0) as Ramount from fInvRdet  where txnDate LIKE '" + year + "-" + String.format("%02d" ,month) + "-_%'";
            cursor = dB.rawQuery(selectQuery , null);

            while (cursor.moveToNext())
            {
                result = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Ramount")));

            }

        }catch (Exception e)
        {
            Log.d(TAG,"Exception TMReturn");
        }
        finally
        {

            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return result;
    }

    //get this month productive count
    public int getTMProductivecount()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        Cursor cursor = null;
        int cTMProductive = 0;

        try
        {
            String selectQuery = "select count(DISTINCT debcode) from fTranSoHed where txndate LIKE '" + cYear + "-" + String.format("%02d",cMonth) + "-_%'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                cTMProductive = cursor.getInt(0);

                if(cTMProductive > 0)
                {
                    return cTMProductive;
                }
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getTMProductive");
        }
        finally {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return cTMProductive;
    }


    //get this month Non-Productive count
    public int getTMNonProductiveCount()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        Cursor cursor = null;
        int cTMNonP = 0;

        try
        {
            String selectQuery = "select count(RepCode) from FDaynprdHed where txndate LIKE '" + cYear + "-" + String.format("%02d",cMonth) + "-_%'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                cTMNonP = cursor.getInt(0);

                if(cTMNonP > 0)
                {
                    return cTMNonP;
                }
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getTMNProductive");
        }
        finally {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return cTMNonP;
    }

    //get this month Discount
    public Double getTMDiscount()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        Cursor cursor = null;
        double TMdiscount = 0.0;

        try
        {
            String selectQuery = "select ifnull(sum(DisAmt),0) as Tdiscount from fordDisc where txndate LIKE '" + cYear + "-" + String.format("%02d",cMonth) + "-_%'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                    TMdiscount = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Tdiscount")));
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getTMDiscount");
        }
        finally {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return TMdiscount;
    }

    //get this month invoice sale
    public Double getTMInvoiceSale()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        String year = (new SimpleDateFormat("yyyy").format(new Date()).substring(2,4));
        String month = new SimpleDateFormat("MM").format(new Date());
        String txndate = year + month;

        Cursor cursor = null;
        double invoicesale = 0.0;

        try
        {
            String selectQuery = "select ifnull(sum(TotalAmt),0) as totAmt from FApprOrdHed where substr(RefNo,4,4) = '" + txndate + "'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                    invoicesale = Double.parseDouble(cursor.getString(cursor.getColumnIndex("totAmt")));
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getTMInvoiceSale");
        }
        finally {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return invoicesale;
    }

    //current month target
    public Double getRepTarget() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        Cursor cursor = null;
        double targetsum = 0.0 ;
        //String selectQuery = "SELECT TrAmt from fTargetDet where txndate LIKE '" + curYear + "-" + String.format("%02d", curMonth) + "-_%'";
        String selectQuery = " SELECT TrAmt from fTargetDet where YearT = '" + curYear + "' AND Monthn = '" + curMonth + "'";

        cursor = dB.rawQuery(selectQuery, null);
        try {

            while (cursor.moveToNext()) {
                targetsum = Double.parseDouble(cursor.getString(cursor.getColumnIndex("TrAmt")));
            }

        } catch (Exception e) {

            Log.v(TAG + " Excep getRepTarget", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return targetsum;

    }

    //get previous month gross sale
    public double getpPMAchieve()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        Cursor cursor = null;
        double PMmonthAchive = 0.0 ;

        try
        {
            String selectQuery = " select ifnull(sum(Amt),0) as grossAmt from fTranSoDet where txndate LIKE '" + cYear + "-" + String.format("%02d",cMonth - 1) + "-_%'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                PMmonthAchive = Double.parseDouble(cursor.getString(cursor.getColumnIndex("grossAmt")));
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getPMGrossSale");
        }
        finally {
            if (cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return PMmonthAchive;
    }

    //get Previous month Return
    public Double getPMReturn()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int month =Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        Cursor cursor = null;
        double result = 0.0;

        try
        {
            String selectQuery = "select ifnull(sum(Amt),0) as PMRamount from fInvRdet  where txnDate LIKE '" + year + "-" + String.format("%02d" ,month - 1) + "-_%'";
            cursor = dB.rawQuery(selectQuery , null);

            while (cursor.moveToNext())
            {
                result = Double.parseDouble(cursor.getString(cursor.getColumnIndex("PMRamount")));
            }

        }catch (Exception e)
        {
            Log.d(TAG,"Exception PMReturn");
        }
        finally
        {

            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return result;
    }

    //get Previous month productive count
    public int getPMProductivecount()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        Cursor cursor = null;
        int cPMProductive = 0;

        try
        {
            String selectQuery = "select count(DISTINCT debcode) from fTranSoHed where txndate LIKE '" + cYear + "-" + String.format("%02d",cMonth - 1) + "-_%'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                cPMProductive = cursor.getInt(0);

                if(cPMProductive > 0)
                {
                    return cPMProductive;
                }
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getPMProductive");
        }
        finally {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return cPMProductive;
    }


    //get Previous month Non-Productive count
    public int getPMNonProductiveCount()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        Cursor cursor = null;
        int cPMNonP = 0;

        try
        {
            String selectQuery = "select count(RepCode) from FDaynprdHed where txndate LIKE '" + cYear + "-" + String.format("%02d",cMonth - 1) + "-_%'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                cPMNonP = cursor.getInt(0);

                if(cPMNonP > 0)
                {
                    return cPMNonP;
                }
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getPMNProductive");
        }
        finally {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return cPMNonP;
    }

    //get Previous month Discount
    public Double getPMDiscount()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int cYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        Cursor cursor = null;
        double PMdiscount = 0.0;

        try
        {
            String selectQuery = "select ifnull(sum(DisAmt),0) as Tdiscount from fordDisc where txndate LIKE '" + cYear + "-" + String.format("%02d",cMonth - 1) + "-_%'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                    PMdiscount = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Tdiscount")));
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getPMDiscount");
        }
        finally {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return PMdiscount;
    }

    //get previous month invoice sale
    public Double getPMInvoiceSale()
    {
        if(dB == null)
        {
            open();
        }
        else if(! dB.isOpen())
        {
            open();
        }

        int cMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        String year = (new SimpleDateFormat("yyyy").format(new Date()).substring(2,4));
        String month = String.valueOf(cMonth - 1);
        String txndate = year + month ;

        Cursor cursor = null;
        double invoicesale = 0.0;

        try
        {
            String selectQuery = "select ifnull(sum(TotalAmt),0) as totAmt from FApprOrdHed where substr(RefNo,4,4) = '" + txndate + "'";
            cursor = dB.rawQuery(selectQuery,null);

            while (cursor.moveToNext())
            {
                    invoicesale = Double.parseDouble(cursor.getString(cursor.getColumnIndex("totAmt")));
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Exception getPMInvoicesale");
        }
        finally {
            if(cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return invoicesale;
    }

    //previous month target
    public Double getPMRepTarget() {
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        String preMonth = String.valueOf(curMonth - 1);

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        double targetsum = 0.0;
        try {
            String selectQuery = "SELECT TrAmt from fTargetDet where Monthn = '" + preMonth + "' and YearT = '" + curYear + "'";
            //String selectQuery = "SELECT TrAmt from fTargetDet where Monthn = '" + String.format("%02d", curMonth - 1) + "' and YearT = '" + curYear + "'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                targetsum = Double.parseDouble(cursor.getString(cursor.getColumnIndex("TrAmt")));
            }

        } catch (Exception e) {

            Log.v(TAG + " Excep getPMTarget", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return targetsum;
    }



}
