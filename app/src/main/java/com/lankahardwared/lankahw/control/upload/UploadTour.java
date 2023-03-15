package com.lankahardwared.lankahw.control.upload;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.TaskType;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.TourDS;
import com.lankahardwared.lankahw.model.mapper.TourMapper;

import java.util.ArrayList;

public class UploadTour extends AsyncTask<ArrayList<TourMapper>, Integer, ArrayList<TourMapper>> {

    // Shared Preferences variables
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    ProgressDialog dialog;
    UploadTaskListener taskListener;
    TaskType taskType;
    int totalRecords;

    public UploadTour(Context context, UploadTaskListener taskListener, TaskType taskType) {

        this.context = context;
        this.taskListener = taskListener;
        this.taskType = taskType;

        //localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.show();
    }

    @Override
    protected ArrayList<TourMapper> doInBackground(ArrayList<TourMapper>... params) {

        int recordCount = 0;
        publishProgress(recordCount);

        ArrayList<TourMapper> RCSList = params[0];
        totalRecords = RCSList.size();
        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;

        for (TourMapper c : RCSList) {

            try {
                String sURL = URL + context.getResources().getString(R.string.ConnectionURL) + "/insertTourInfo";
                boolean bStatus = UtilityContainer.mHttpManager(sURL, new Gson().toJson(c));

                if (bStatus) {
                    c.setSynced(true);
                } else {
                    c.setSynced(false);
                }

            } catch (Exception e) {

                e.getStackTrace();
            }

            ++recordCount;
            publishProgress(recordCount);
        }

        return RCSList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        dialog.setMessage("Uploading.. Tour record " + values[0] + "/" + totalRecords);
    }

    @Override
    protected void onPostExecute(ArrayList<TourMapper> RCSList) {

        super.onPostExecute(RCSList);

        for (TourMapper c : RCSList) {
            new TourDS(context).updateIsSynced(c);
        }

        dialog.dismiss();
        taskListener.onTaskCompleted(taskType, new ArrayList<String>());
    }
}
