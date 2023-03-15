package com.lankahardwared.lankahw.view.receipt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.api.ApiCllient;
import com.lankahardwared.lankahw.api.ApiInterface;
import com.lankahardwared.lankahw.control.TaskType;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.control.upload.UploadTaskListener;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.RecHedDS;
import com.lankahardwared.lankahw.data.TranSOHedDS;
import com.lankahardwared.lankahw.model.mapper.ReceiptMapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadReceipt extends AsyncTask<ArrayList<ReceiptMapper>, Integer, ArrayList<ReceiptMapper>> {

	Context context;
	ProgressDialog dialog;
	UploadTaskListener taskListener;
	TaskType taskType;
	List<String> resultListPreSale;
	private Handler mHandler;
	int totalRecords;
	public static final String SETTINGS = "SETTINGS";
	public static SharedPreferences localSP;

	public UploadReceipt(Context context, UploadTaskListener taskListener, TaskType taskType) {
		resultListPreSale = new ArrayList<>();
		mHandler = new Handler(Looper.getMainLooper());
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
		
		final ArrayList<ReceiptMapper> RCSList = params[0];
		totalRecords = RCSList.size();
		//160/bb,nawala road,
		final String sp_url =localSP.getString("URL", "").toString();
		String URL="http://"+sp_url;

		for(final ReceiptMapper c : RCSList) {

			try {
				String content_type = "application/json";
				ApiInterface apiInterface = ApiCllient.getClient(context).create(ApiInterface.class);
				JsonParser jsonParser = new JsonParser();
				String orderJson = new Gson().toJson(c);
				JsonObject objectFromString = jsonParser.parse(orderJson).getAsJsonObject();
				JsonArray jsonArray = new JsonArray();
				jsonArray.add(objectFromString);
				Call<String> resultCall = apiInterface.uploadReceipt(jsonArray, content_type);
				resultCall.enqueue(new Callback<String>() {
					@Override
					public void onResponse(Call<String> call, Response<String> response) {
						if (response != null && response.body() != null) {
							int status = response.code();
							Log.d(">>>response code", ">>>res " + status);
							Log.d(">>>response message", ">>>res " + response.message());
							Log.d(">>>response body", ">>>res " + response.body().toString());
							int resLength = response.body().toString().trim().length();
							String resmsg = "" + response.body().toString();
							if (status == 200 && !resmsg.equals("") && !resmsg.equals(null)) {
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										c.setSynced(true);
										addRefNoResults(c.getFPRECHED_REFNO() + " --> Success\n", RCSList.size());
										new RecHedDS(context).updateIsSyncedReceipt(c);

									}
								});
								//addRefNoResults(c.getORDER_REFNO() +" --> Success\n",RCSList.size());

								//  Toast.makeText(context, c.getORDER_REFNO()+" - Order uploded Successfully", Toast.LENGTH_SHORT).show();
							} else {
								// Log.d(">>response" + status, "" + c.getORDER_REFNO());
								c.setSynced(false);
								new RecHedDS(context).updateIsSyncedReceipt(c);
								addRefNoResults(c.getFPRECHED_REFNO() + " --> Failed\n", RCSList.size());
								//   Toast.makeText(context, c.getORDER_REFNO()+" - Order uplod Failed", Toast.LENGTH_SHORT).show();
							}
						}else{
							Toast.makeText(context, " Invalid response when order upload", Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure (Call < String > call, Throwable t){
						Toast.makeText(context, "Error response " + t.toString(), Toast.LENGTH_SHORT).show();
					}

				});

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

	}
	private void addRefNoResults(String ref, int count) {
		resultListPreSale.add(ref);
		if(count == resultListPreSale.size()) {
			mUploadResult(resultListPreSale);
		}
	}

	public void mUploadResult(List<String> messages) {
		String msg = "";
		for (String s : messages) {
			msg += s;
		}
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setMessage(msg);
		alertDialogBuilder.setTitle("Upload Receipt Summary");

		alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int id) {
				taskListener.onTaskCompleted(taskType, resultListPreSale);
				dialog.cancel();
			}
		});
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();
		alertD.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
	}
}
