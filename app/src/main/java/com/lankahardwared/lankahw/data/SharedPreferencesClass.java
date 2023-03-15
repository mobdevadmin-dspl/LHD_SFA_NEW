package com.lankahardwared.lankahw.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesClass extends Activity {

    public static String SETTINGS = "SETTINGS";
    public static SharedPreferences localSharedPreferences;

    public static void setLocalSharedPreference(final Context con, String localSPKey, String localSPValue) {
        SharedPreferences localSP = con.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        Editor localBackupEditor = localSP.edit();
        localBackupEditor.putString(localSPKey, localSPValue);
        localBackupEditor.commit();
    }


}