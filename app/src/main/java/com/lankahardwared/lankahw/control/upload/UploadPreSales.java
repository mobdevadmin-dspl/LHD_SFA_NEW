package com.lankahardwared.lankahw.control.upload;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.TaskType;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.TranSOHedDS;
import com.lankahardwared.lankahw.model.mapper.PreSalesMapper;

import java.util.ArrayList;
import java.util.List;

public class UploadPreSales extends AsyncTask<ArrayList<PreSalesMapper>, Integer, ArrayList<PreSalesMapper>> {

    // Shared Preferences variables
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    ProgressDialog dialog;
    UploadTaskListener taskListener;
    TaskType taskType;
    int totalRecords;

    public UploadPreSales(Context context, UploadTaskListener taskListener, TaskType taskType) {

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
        // dialog.setTitle("Uploading records");
        dialog.show();
    }

    @Override
    protected ArrayList<PreSalesMapper> doInBackground(ArrayList<PreSalesMapper>... params) {

        int recordCount = 0;
        publishProgress(recordCount);

        ArrayList<PreSalesMapper> RCSList = params[0];
        totalRecords = RCSList.size();

        Log.d("UPLOAD_PRE_SALES", "TOTAL_RECORD_SIZE_IS: " + totalRecords);

        final String sp_url = localSP.getString("URL", "");
        String URL = "http://" + sp_url;

        for (PreSalesMapper c : RCSList) {
//            try {
//                FileWriter writer = new FileWriter(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "test.txt");
//                writer.write(new Gson().toJson(c));
//                writer.close();
//            } catch (Exception e) {
//
//            }
            try {
                List<String> List = new ArrayList<String>();
                String sJsonHed = new Gson().toJson(c);
                List.add(sJsonHed);
                String sURL = URL + context.getResources().getString(R.string.ConnectionURL) + "/insertFOrdHed";
                boolean bStatus = UtilityContainer.mHttpManager(sURL, List.toString());
               // boolean bStatus = UtilityContainer.mHttpManager(sURL, new Gson().toJson(c));

                if (bStatus) {
                    c.setSynced(true);
                } else {
                    c.setSynced(false);
                }

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
        dialog.setMessage("Uploading.. PreSale Record " + values[0] + "/" + totalRecords);
    }

    @Override
    protected void onPostExecute(ArrayList<PreSalesMapper> RCSList) {

        super.onPostExecute(RCSList);
        List<String> list = new ArrayList<>();

        if (RCSList.size() > 0) {
            list.add("PRE SALES SUMMARY\n");
            list.add("------------------------------------\n");
        }

        int i = 1;
        for (PreSalesMapper c : RCSList) {
            new TranSOHedDS(context).updateIsSynced(c);

            if (c.isSynced()) {
                list.add(i + ". " + c.getFTRANSOHED_REFNO() + " (" + new DebtorDS(context).getCustNameByCode(c.getFTRANSOHED_DEBCODE()) + ")" + " --> Success\n");
            } else {
                list.add(i + ". " + c.getFTRANSOHED_REFNO() + " (" + new DebtorDS(context).getCustNameByCode(c.getFTRANSOHED_DEBCODE()) + ")" + " --> Failed\n");
            }
            i++;
        }

        dialog.dismiss();
        taskListener.onTaskCompleted(taskType, list);
    }

}
