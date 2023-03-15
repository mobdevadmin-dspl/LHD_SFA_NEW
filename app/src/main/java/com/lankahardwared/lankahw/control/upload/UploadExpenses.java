package com.lankahardwared.lankahw.control.upload;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.TaskType;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.FDayExpHedDS;
import com.lankahardwared.lankahw.model.mapper.ExpenseMapper;

import java.util.ArrayList;
import java.util.List;

public class UploadExpenses extends AsyncTask<ArrayList<ExpenseMapper>, Integer, ArrayList<ExpenseMapper>> {

    // Shared Preferences variables
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    ProgressDialog dialog;
    UploadTaskListener taskListener;
    TaskType taskType;
    int totalRecords;

    public UploadExpenses(Context context, UploadTaskListener taskListener, TaskType taskType) {

        this.context = context;
        this.taskListener = taskListener;
        this.taskType = taskType;

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
    protected ArrayList<ExpenseMapper> doInBackground(ArrayList<ExpenseMapper>... params) {

        int recordCount = 0;
        publishProgress(recordCount);

        ArrayList<ExpenseMapper> RCSList = params[0];
        totalRecords = RCSList.size();

        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;

        for (ExpenseMapper c : RCSList) {

            try {

                List<String> List = new ArrayList<String>();
                String sJsonHed = new Gson().toJson(c);
                List.add(sJsonHed);
                //  Log.v("## Json1 ##",  List.toString());

                String sURL = URL + context.getResources().getString(R.string.ConnectionURL) + "/insertDayExpense";

                boolean bStatus = UtilityContainer.mHttpManager(sURL, List.toString());
               // boolean bStatus = UtilityContainer.mHttpManager(sURL, new Gson().toJson(c));

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
        dialog.setMessage("Uploading.. Expense Record " + values[0] + "/" + totalRecords);
    }

    @Override
    protected void onPostExecute(ArrayList<ExpenseMapper> RCSList) {

        super.onPostExecute(RCSList);

        List<String> list = new ArrayList<>();

        if (RCSList.size() > 0)
        {
            list.add("\nDAILYEXPENSE");
            list.add("------------------------------------\n");
        }

        int i = 1;
        for (ExpenseMapper c : RCSList) {
            new FDayExpHedDS(context).updateIsSynced(c);

            if (c.isSynced()) {
                list.add(i + ". " + c.getEXP_REFNO()+ " --> Success\n");
            } else {
                list.add(i + ". " + c.getEXP_REFNO() + " --> Failed\n");
            }
            i++;
        }
        dialog.dismiss();
        taskListener.onTaskCompleted(taskType, list);
    }

}
