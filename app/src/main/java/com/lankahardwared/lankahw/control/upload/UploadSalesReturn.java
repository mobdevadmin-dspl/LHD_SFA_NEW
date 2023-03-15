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
import com.lankahardwared.lankahw.data.FInvRHedDS;
import com.lankahardwared.lankahw.model.mapper.SalesReturnMapper;

import java.util.ArrayList;
import java.util.List;

public class UploadSalesReturn extends AsyncTask<ArrayList<SalesReturnMapper>, Integer, ArrayList<SalesReturnMapper>> {

	Context context;
	ProgressDialog dialog;
	UploadTaskListener taskListener;
	TaskType taskType;

	int totalRecords;

	// Shared Preferences variables
	public static final String SETTINGS = "SETTINGS";
	public static SharedPreferences localSP;

	public UploadSalesReturn(Context context, UploadTaskListener taskListener, TaskType taskType) {

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
		//dialog.setTitle("Uploading records");
		dialog.show();
	}

	@Override
	protected ArrayList<SalesReturnMapper> doInBackground(ArrayList<SalesReturnMapper>... params) {

		int recordCount = 0;
		publishProgress(recordCount);

		ArrayList<SalesReturnMapper> RCSList = params[0];
		totalRecords = RCSList.size();

		final String sp_url = localSP.getString("URL", "").toString();
		String URL = "http://" + sp_url;

		for (SalesReturnMapper c : RCSList) {

			try {
				List<String> List = new ArrayList<String>();
				String sJsonHed = new Gson().toJson(c);
				List.add(sJsonHed);
				String sURL = URL + context.getResources().getString(R.string.ConnectionURL) + "/insertReturn";
				boolean bStatus = UtilityContainer.mHttpManager(sURL, List.toString());
				// boolean bStatus = UtilityContainer.mHttpManager(sURL, new Gson().toJson(c));

				if (bStatus) {
					c.setSYNCSTATUS(true);
				} else {
					c.setSYNCSTATUS(false);
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
		dialog.setMessage("Uploading.. Sales Return Record " + values[0] + "/" + totalRecords);
	}

	@Override
	protected void onPostExecute(ArrayList<SalesReturnMapper> RCSList) {

		super.onPostExecute(RCSList);
		List<String> list = new ArrayList<>();

		if (RCSList.size() > 0) {
			list.add("SALES RETURN SUMMARY\n");
			list.add("------------------------------------\n");
		}
		int i = 1;
		for (SalesReturnMapper c : RCSList) {
			new FInvRHedDS(context).updateIsSynced(c);

			if (c.isSYNCSTATUS()) {
				list.add(i + ". " + c.getFINVRHED_REFNO()+ " (" + new DebtorDS(context).getCustNameByCode(c.getFINVRHED_DEBCODE()) + ")" + " --> Success\n");
			} else {
				list.add(i + ". " + c.getFINVRHED_REFNO() + " (" + new DebtorDS(context).getCustNameByCode(c.getFINVRHED_DEBCODE()) + ")" + " --> Failed\n");
			}
			i++;
		}
		dialog.dismiss();
		taskListener.onTaskCompleted(taskType,list);
	}

}
