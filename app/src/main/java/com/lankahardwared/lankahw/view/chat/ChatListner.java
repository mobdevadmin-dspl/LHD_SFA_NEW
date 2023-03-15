package com.lankahardwared.lankahw.view.chat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

/**
 * Created by Sathiyaraja on 12/15/2017.
 */

public class ChatListner extends Service{

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("started","ChatListner");
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
