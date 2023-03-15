package com.lankahardwared.lankahw.control.upload;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.TaskType;
import com.lankahardwared.lankahw.data.FDaynPrdHedDS;
import com.lankahardwared.lankahw.model.mapper.NonProdMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UploadNonProd extends AsyncTask<ArrayList<NonProdMapper>, Integer, ArrayList<NonProdMapper>> {

    // Shared Preferences variables
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    ProgressDialog dialog;
    UploadTaskListener taskListener;
    TaskType taskType;
    int totalRecords;

    public UploadNonProd(Context context, UploadTaskListener taskListener, TaskType taskType) {

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
    protected ArrayList<NonProdMapper> doInBackground(ArrayList<NonProdMapper>... params) {

        int recordCount = 0;
        publishProgress(recordCount);

        ArrayList<NonProdMapper> NonPrdList = params[0];
        totalRecords = NonPrdList.size();

        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;

        for (NonProdMapper c : NonPrdList) {

            List<String> List = new ArrayList<String>();
            String sJsonHed = new Gson().toJson(c);
            List.add(sJsonHed);

            try{

                FileWriter writer=new FileWriter(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +"/"+ "testr.txt");
                writer.write(sJsonHed);
                writer.close();
            }catch(Exception e){

            }


            Log.v("## Json ##",  List.toString());


                try {
                    // PDADBWebServiceMO
                    HttpPost requestfDam = new HttpPost(URL+ context.getResources().getString(R.string.ConnectionURL) +"/insertFDaynPrdHed");
                    StringEntity entityfDam = new StringEntity( List.toString(), "UTF-8");
                    entityfDam.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    entityfDam.setContentType("application/json");
                    requestfDam.setEntity(entityfDam);
                    // Send request to WCF service
                    DefaultHttpClient httpClientfDamRec = new DefaultHttpClient();


                    HttpResponse responsefDamRec = httpClientfDamRec.execute(requestfDam);
                    HttpEntity entityfDamEntity = responsefDamRec.getEntity();
                    InputStream is = entityfDamEntity.getContent();

                    //StatusLine statusLine = responsefDamRec.getStatusLine();
                    //int statusCode = statusLine.getStatusCode();

                    if (is != null)
                    {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();

                        String line = null;
                        while ((line = reader.readLine()) != null){
                            sb.append(line + "\n");
                        }

                        is.close();

                        String result = sb.toString();
                        String result_fDamRec = result.replace("\"", "");

                        Log.e("response", "connect:" + result_fDamRec);

                        if (result_fDamRec.trim().equals("200")){
                            c.setSynced(true);
                        }else {
                            c.setSynced(false);
                        }
                    }
                }catch(Exception e){

                    e.getStackTrace();
                }

                ++recordCount;
                publishProgress(recordCount);
            }

            return NonPrdList;
        }


        @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        dialog.setMessage("Uploading.. Non Prod Record " + values[0] + "/" + totalRecords);
    }

    @Override
    protected void onPostExecute(ArrayList<NonProdMapper> NonPrdList) {

        super.onPostExecute(NonPrdList);
        List<String> list = new ArrayList<>();

        if (NonPrdList.size() > 0) {
            list.add("\nNONPRODUCTIVE");
            list.add("------------------------------------\n");
        }
        int i = 1;
        for (NonProdMapper c : NonPrdList) {

            new FDaynPrdHedDS(context).updateIsSynced(c);

            if (c.isSynced()) {
                list.add(i + ". " + c.getNONPRDHED_REFNO()+ " --> Success\n");
            } else {
                list.add(i + ". " + c.getNONPRDHED_REFNO() + " --> Failed\n");
            }
            i++;
        }

        dialog.dismiss();
        taskListener.onTaskCompleted(taskType, list);
    }

}
