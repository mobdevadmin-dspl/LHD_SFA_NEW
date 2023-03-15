package com.lankahardwared.lankahw.control.upload;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.TaskType;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.DepositHedDS;
import com.lankahardwared.lankahw.data.InvHedDS;
import com.lankahardwared.lankahw.model.mapper.DepositMapper;
import com.lankahardwared.lankahw.model.mapper.VanSalesMapper;

import java.util.ArrayList;
import java.util.List;

public class UploadDeposit extends AsyncTask<ArrayList<DepositMapper>, Integer, ArrayList<DepositMapper>> {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    ProgressDialog dialog;
    UploadTaskListener taskListener;
    TaskType taskType;
    int totalRecords;

    public UploadDeposit(Context context, UploadTaskListener taskListener, TaskType taskType) {

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
    protected ArrayList<DepositMapper> doInBackground(ArrayList<DepositMapper>... params) {

        int recordCount = 0;
        publishProgress(recordCount);

        ArrayList<DepositMapper> RCSList = params[0];
        totalRecords = RCSList.size();
        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;

        for (DepositMapper c : RCSList) {

            try {
                List<String> List = new ArrayList<String>();
                String sJsonHed = new Gson().toJson(c);
                List.add(sJsonHed);
                String sURL = URL + context.getResources().getString(R.string.ConnectionURL) + "/insertfBankDepoHed";
                //boolean bStatus = UtilityContainer.mHttpManager(sURL, new Gson().toJson(c));
                boolean bStatus = UtilityContainer.mHttpManager(sURL, List.toString());

                if (bStatus)
                    c.setIS_SYNCED(true);
                else
                    c.setIS_SYNCED(false);

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
    protected void onPostExecute(ArrayList<DepositMapper> RCSList) {

        super.onPostExecute(RCSList);
        List<String> list = new ArrayList<>();

        if (RCSList.size() > 0) {
            list.add("\nDEPOSIT SUMMARY");
            list.add("------------------------------------\n");
        }

        int i = 1;
        for (DepositMapper c : RCSList) {
            new DepositHedDS(context).updateIsSynced(c);

            if (c.getIS_SYNCED()) {
                list.add(i + ". " + c.getFBANKDEPOHED_REFNO() + " --> Success\n");
            } else {
                list.add(i + ". " + c.getFBANKDEPOHED_REFNO() + " --> Failed\n");
            }
            i++;
        }

        dialog.dismiss();
        taskListener.onTaskCompleted(taskType, list);
    }
}
