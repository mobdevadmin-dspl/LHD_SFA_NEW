package com.lankahardwared.lankahw.listviewitems;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.lankahardwared.lankahw.R;

import java.util.ArrayList;


/**
 * Created by rashmi on 22/05/2019.
 * The custom built progress dialog with material progress animations.
 */
public class CustomProgressDialog extends Dialog {

    private TextView tvProgressText;
    private ListView listDownloaded;
    private ProgressWheel progressWheel;
    private Context context;

    public CustomProgressDialog(Context context) {
        super(context, false, null);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_progress);

        tvProgressText = (TextView)findViewById(R.id.progress_dialog_progress_txt);
        progressWheel = (ProgressWheel)findViewById(R.id.progress_dialog_progress_wheel);
        listDownloaded = (ListView)findViewById(R.id.progress_list);
        progressWheel.setBarColor(context.getResources().getColor(R.color.blueColor));
        progressWheel.spin();

    }

    public void setMessage(String message) {
        if(tvProgressText != null) {
            tvProgressText.setText(message);
        }
    }
    public void setMessage(ArrayList<String> messages) {
        if(listDownloaded != null) {
            ArrayAdapter<String> downloadList = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, messages);
            listDownloaded.setAdapter(downloadList);
        }
    }

    public void setProgress(float progress){
        progressWheel.setProgress(progress);
    }

//    public void setIndeterminate(){
//        progressWheel.spin();
//    }

}
