package com.lankahardwared.lankahw.control.upload;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.TaskType;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.DepositHedDS;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.mapper.DepositMapper;

import java.util.ArrayList;
import java.util.List;

public class UploadDebtorCoordinates extends AsyncTask<ArrayList<Debtor>, Integer, ArrayList<Debtor>> {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    ProgressDialog dialog;
    UploadTaskListener taskListener;
    TaskType taskType;
    int totalRecords;

    public UploadDebtorCoordinates(Context context, UploadTaskListener taskListener, TaskType taskType) {

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
    protected ArrayList<Debtor> doInBackground(ArrayList<Debtor>... params) {

        int recordCount = 0;
        publishProgress(recordCount);

        ArrayList<Debtor> RCSList = params[0];
        totalRecords = RCSList.size();
        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;

        for (Debtor c : RCSList) {

            try {
                List<String> List = new ArrayList<String>();
                String sJsonHed = new Gson().toJson(c);
                List.add(sJsonHed);
                String sURL = URL + context.getResources().getString(R.string.ConnectionURL) + "/updateDebtorCordinates";
                //boolean bStatus = UtilityContainer.mHttpManager(sURL, new Gson().toJson(c));
                boolean bStatus = UtilityContainer.mHttpManager(sURL, List.toString());

                if (bStatus)
                    c.setFDEBTOR_IS_SYNCED("1");
                else
                    c.setFDEBTOR_IS_SYNCED("2");

            } catch (Exception e) {
                e.printStackTrace();
            }

            ++recordCount;
            publishProgress(recordCount);
        }

        return RCSList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        dialog.setMessage("Uploading.. Deposit Record " + values[0] + "/" + totalRecords);
    }

    @Override
    protected void onPostExecute(ArrayList<Debtor> RCSList) {

        super.onPostExecute(RCSList);
        List<String> list = new ArrayList<>();

        if (RCSList.size() > 0) {
            list.add("\nDEBTOR GPS UPLOAD SUMMARY");
            list.add("------------------------------------\n");
        }

        int i = 1;
        for (Debtor c : RCSList) {
            new DebtorDS(context).updateIsSynced(c);

            if (c.getFDEBTOR_IS_SYNCED().equals("1")) {
                list.add(i + ". " + c.getFDEBTOR_CODE() + " --> Success\n");
            } else {
                list.add(i + ". " + c.getFDEBTOR_CODE() + " --> Failed\n");
            }
            i++;
        }

        dialog.dismiss();
        taskListener.onTaskCompleted(taskType, list);
    }
}
