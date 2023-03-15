package com.lankahardwared.lankahw.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.SharedPref;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rashmi on 7/15/2019.
 */

public class ApiCllient {
    private final String LOG_TAG = ApiCllient.class.getSimpleName();
    private static String baseURL;
    private static SharedPref pref;
    private static Retrofit retrofit = null;
    public static SharedPreferences localSP;
    public static final String SETTINGS = "SETTINGS";
    public static Retrofit getClient(Context contextt) {

        //add timouts 2020-03-19 becz sockettimeoutexception by rashmi
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                // .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES);

        pref = SharedPref.getInstance(contextt);
        localSP = contextt.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

        String URL = "http://" + localSP.getString("URL", "").toString();
      //  String domain = pref.getBaseURL();
        Log.d("baseURL>>>>>>>>>", URL);
        baseURL = URL + contextt.getResources().getString(R.string.ConnectionURL)+"/";

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create());
        builder.client(httpClient.build());

        if(retrofit == null){
            retrofit = builder.build();
        }
        return retrofit;
    }
}
