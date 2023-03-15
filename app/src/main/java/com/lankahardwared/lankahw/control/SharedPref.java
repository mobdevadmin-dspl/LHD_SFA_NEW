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


}
