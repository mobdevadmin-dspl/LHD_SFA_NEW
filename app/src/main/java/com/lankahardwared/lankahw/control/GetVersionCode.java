package com.lankahardwared.lankahw.control;

import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;

import java.io.IOException;

public class GetVersionCode extends AsyncTask<Void, Void, String> {
    Context context;

    public GetVersionCode(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... params) {
        String newVersion = null;

        try {
            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + context.getPackageName() + "&hl=it")
                    .timeout(500)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get().select("div[itemprop=softwareVersion]")
                    .first().ownText();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return newVersion;
    }

}
