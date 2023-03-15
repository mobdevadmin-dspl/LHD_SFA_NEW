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
import com.lankahardwared.lankahw.data.RecHedDS;
import com.lankahardwared.lankahw.model.mapper.ReceiptMapper;

import java.util.ArrayList;
import java.util.List;

public class UploadReceipt extends AsyncTask<ArrayList<ReceiptMapper>, Integer, ArrayList<ReceiptMapper>> {

	Context context;
	ProgressDialog dialog;
	UploadTaskListener taskListener;
	TaskType taskType;

	int totalRecords;

	public static final String SETTINGS = "SETTINGS";
	public static SharedPreferences localSP;

	public UploadReceipt(Context context, UploadTaskListener taskListener, TaskType taskType) {

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
	protected ArrayList<ReceiptMapper> doInBackground(ArrayList<ReceiptMapper>... params) {

		int recordCount = 0;
		publishProgress(recordCount);
		
		ArrayList<ReceiptMapper> RCSList = params[0];
		totalRecords = RCSList.size();
		//160/bb,nawala road,
		final String sp_url =localSP.getString("URL", "").toString();
		String URL="http://"+sp_url;

		for(ReceiptMapper c : RCSList) {

			try {
				List<String> List = new ArrayList<String>();
				String sJsonHed = new Gson().toJson(c);
				List.add(sJsonHed);

				String sURL = URL + context.getResources().getString(R.string.ConnectionURL) + "/insertFrecHed";
				boolean bStatus = UtilityContainer.mHttpManager(sURL, List.toString());

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
		dialog.setMessage("Uploading.. Receipt Record " + values[0] + "/" + totalRecords);
	}

	@Override
	protected void onPostExecute(ArrayList<ReceiptMapper> RCSList) {
		super.onPostExecute(RCSList);
		List<String> list = new ArrayList<>();

		if (RCSList.size() > 0) {
			list.add("\nRECEIPT");
			list.add("------------------------------------\n");
		}

		int i = 1;
		for (ReceiptMapper c : RCSList) {
			new RecHedDS(context).updateIsSyncedReceipt(c);

			if (c.isSynced()) {
				list.add(i + ". " + c.getFPRECHED_REFNO()+ " (" + new DebtorDS(context).getCustNameByCode(c.getFPRECHED_DEBCODE()) + ")" + " --> Success\n");
			} else {
				list.add(i + ". " + c.getFPRECHED_REFNO() + " (" + new DebtorDS(context).getCustNameByCode(c.getFPRECHED_DEBCODE()) + ")" + "--> Failed\n");
			}
			i++;
		}

		dialog.dismiss();
		taskListener.onTaskCompleted(taskType, list);
	}

}
