package com.lankahardwared.lankahw.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lankahardwared.lankahw.PushNotification.objMessage;

import java.util.ArrayList;

/**
 * Created by Sathiyaraja on 12/6/2017.
 */

public class MessageDS {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "finac2.0";

    public MessageDS(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public long saveMessage(objMessage message) {

        long result = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.TAG_MSG, message.getMessage());
            values.put(DatabaseHelper.TAG_STATUS, message.getStatus());
            values.put(DatabaseHelper.TAG_FROM, message.getFrom());
            values.put(DatabaseHelper.TAG_SUBJECT, message.getSubject());
            values.put(DatabaseHelper.TAG_DATE_TIME, message.getDate_time());

            result = dB.insert(DatabaseHelper.TABLE_MESSAGE, null, values);

        } catch (SQLException e) {
            Log.v(TAG + " Exception", e.toString());
        }

        dB.close();
        return result;


    }

    public ArrayList<objMessage> getAllMessages() {


        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<objMessage> messages = new ArrayList<>();
        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_MESSAGE + " WHERE status = 0";
        Cursor cursor = dB.rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                objMessage message = new objMessage();
                message.setID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TAG_ID)));
                message.setMessage(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TAG_MSG)));
                message.setStatus(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TAG_STATUS)));
                message.setFrom(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TAG_FROM)));
                message.setSubject(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TAG_SUBJECT)));
                message.setDate_time(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TAG_DATE_TIME)));
                messages.add(message);
            }
            cursor.close();
        } catch (Exception e) {

        }

        return messages;

    }

    public void isMsgRed(String id) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.TAG_STATUS, "1");
        dB.update(DatabaseHelper.TABLE_MESSAGE, cv, DatabaseHelper.TAG_ID + " = ?", new String[]{id});
        dB.close();

    }

}
