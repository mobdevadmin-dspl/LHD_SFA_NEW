package com.lankahardwared.lankahw.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SharedPref {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private static SharedPref pref;

    public SharedPref(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //----------------kaveesha-----------11-08-2020-----To get selected location-------------------------
    public String getSelectedLocation() {
        return prefs.getString("selected_Loc_Code", "0");
    }

    public void setSelectedLocation(String Loc_Code) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("selected_Loc_Code", Loc_Code);
        editor.apply();
        editor.clear();
    }


    public void setGlobalVal(String mKey, String mValue) {
        editor = prefs.edit();
        editor.putString(mKey, mValue);
        editor.commit();
    }

    public  static SharedPref getInstance(Context context) {
        if (pref == null) {
            pref = new SharedPref(context);
        }

        return pref;
    }

    public String getGlobalVal(String mKey) {
        return prefs.getString(mKey, "");
    }
  //-----------dhanushika -----------------------------------------------------------------------------------------------------------
    public String getGlobalValue(String mKey) {
        return prefs.getString(mKey, "");
    }

    public String getBaseURL() {

       // return prefs.getString("baseURL", "http://192.168.0.5:1025");
        return prefs.getString("baseURL", "http://203.143.21.121:8080");


    }

    public void setDistDB(String dist) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Dist_DB", dist);
        editor.apply();
    }

    public String getDistDB() {

        return prefs.getString("Dist_DB", "LHD");
   //     return prefs.getString("Dist_DB", "LHD_PDA_TEST");

    }
    public void setInvoiceSaleTM(String sale) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("InvoiceSaleTM", sale);
        editor.apply();
    }

    public String getInvoiceSaleTM() {

        return prefs.getString("InvoiceSaleTM", "0.0");

    }
    public void setInvoiceSalePM(String sale) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("InvoiceSalePM", sale);
        editor.apply();
    }

    public String getInvoiceSalePM() {

        return prefs.getString("InvoiceSalePM", "0.0");

    }
    public  void setUserId (String position){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_id", position);
        editor.apply();
    }
    public String getUserId() {
        Log.d("check value>>>",prefs.getString("user_id",""));
        return  prefs.getString("user_id","");
    }
    public  void setUserPwd (String position){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_Pwd", position);
        editor.apply();
    }
    public String getUserPwd() {
        Log.d("check value>>>",prefs.getString("user_Pwd",""));
        return  prefs.getString("user_Pwd","");
    }

    public  void setUserPrefix (String prefix){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_prefix", prefix);
        editor.apply();
    }
    public String getUserPrefix() {
        Log.d("check value>>>",prefs.getString("user_prefix",""));
        return  prefs.getString("user_prefix","");
    }

    public void setLoginStatus(boolean status) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("login_status", status).apply();
    }

    public void setValidateStatus(boolean status) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("validate_status", status).apply();
    }

    public void setTransOrdSumTM(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("TransOrdSumTM", value);
        editor.apply();
    }

    public String getTransOrdSumTM() {

        return prefs.getString("TransOrdSumTM", "0.0");

    }

    public void setTransOrdDiscSumTM(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("TransOrdDiscSumTM", value);
        editor.apply();
    }

    public String getTransOrdDiscSumTM() {

        return prefs.getString("TransOrdDiscSumTM", "0.0");

    }

    public void setTransOrdSumPM(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("TransOrdSumPM", value);
        editor.apply();
    }

    public String getTransOrdSumPM() {

        return prefs.getString("TransOrdSumPM", "0.0");

    }

    public void setTransOrdDiscSumPM(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("TransOrdDiscSumPM", value);
        editor.apply();
    }

    public String getTransOrdDiscSumPM() {

        return prefs.getString("TransOrdDiscSumPM", "0.0");

    }

    public void setDayOrderSum(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("DayOrderSum", value);
        editor.apply();
    }

    public String getDayOrderSum() {

        return prefs.getString("DayOrderSum", "0.0");

    }

    public void setDayDiscountSum(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("DayDiscountSum", value);
        editor.apply();
    }

    public String getDayDiscountSum() {

        return prefs.getString("DayDiscountSum", "0.0");

    }

    public void setDayReturnSum(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("DayReturnSum", value);
        editor.apply();
    }

    public String getDayReturnSum() {

        return prefs.getString("DayReturnSum", "0.0");

    }

    public void setDayProdSum(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("DayProdSum", value);
        editor.apply();
    }

    public String getDayProdSum() {

        return prefs.getString("DayProdSum", "0.0");

    }

    public void setDayNonProdSum(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("DayNonProdSum", value);
        editor.apply();
    }

    public String getDayNonProdSum() {

        return prefs.getString("DayNonProdSum", "0.0");

    }

    public void setDayInvoiceSum(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("DayInvoiceSum", value);
        editor.apply();
    }

    public String getDayInvoiceSum() {

        return prefs.getString("DayInvoiceSum", "0.0");

    }

    public void setMonthReturnSumPM(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("MonthReturnSumPM", value);
        editor.apply();
    }

    public String getMonthReturnSumPM() {

        return prefs.getString("MonthReturnSumPM", "0.0");

    }

    public void setMonthReturnSumTM(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("MonthReturnSumTM", value);
        editor.apply();
    }

    public String getMonthReturnSumTM() {

        return prefs.getString("MonthReturnSumTM", "0.0");

    }




    public void setMonthProdSumTM(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("MonthProdSumTM", value);
        editor.apply();
    }

    public String getMonthProdSumTM() {

        return prefs.getString("MonthProdSumTM", "0.0");

    }

    public void setMonthProdSumPM(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("MonthProdSumPM", value);
        editor.apply();
    }

    public String getMonthProdSumPM() {

        return prefs.getString("MonthProdSumPM", "0.0");

    }

    public void setMonthNonProdSumTM(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("MonthNonProdSumTM", value);
        editor.apply();
    }

    public String getMonthNonProdSumTM() {

        return prefs.getString("MonthNonProdSumTM", "0.0");

    }

    public void setMonthNonProdSumPM(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("MonthNonProdSumPM", value);
        editor.apply();
    }

    public String getMonthNonProdSumPM() {

        return prefs.getString("MonthNonProdSumPM", "0.0");

    }


}
