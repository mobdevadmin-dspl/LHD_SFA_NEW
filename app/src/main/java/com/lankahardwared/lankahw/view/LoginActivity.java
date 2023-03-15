package com.lankahardwared.lankahw.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.DetailedState;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.NetworkUtil;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.control.TaskType;
import com.lankahardwared.lankahw.control.download.Downloader;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.data.SharedPreferencesClass;
import com.lankahardwared.lankahw.listviewitems.CustomProgressDialog;
import com.lankahardwared.lankahw.model.SalRep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class LoginActivity extends Activity implements OnClickListener {

    EditText username, password;
    TextView txtver;
    int tap;
    SharedPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        pref = new SharedPref(LoginActivity.this);
        username = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);
        Button login = (Button) findViewById(R.id.btnlogin);
        txtver = (TextView) findViewById(R.id.textVer);
        txtver.setText("Version " + getVersionCode());

        login.setOnClickListener(this);

        txtver.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                tap += 1;
                StartTimer(3000);
                if (tap >= 7) {
                    showCredits(3000);
                }
            }
        });

    }

    public String getVersionCode() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return "0";

    }

    public void StartTimer(int timeout) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tap = 0;
            }
        }, timeout);

    }

    public void showCredits(int timeout) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Designed & Developed by M.Himas");
        alertDialogBuilder.setTitle("FINAC SFA");
        alertDialogBuilder.setIcon(R.drawable.developer);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.cancel();
            }
        }, timeout);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogin: {
//			try {

//				if (getInternetStatus()) {

//					String ver = new GetVersionCode(this).execute().get();
//
//					if (ver != null) {
//						double newVersion = Double.parseDouble(ver);
//						double currentVersion = Double.parseDouble(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
//
//						if (newVersion > currentVersion) {
//							Toast.makeText(this, "New version available.\nPlease update before continue...", Toast.LENGTH_LONG).show();
//						} else {
//							StartApp();
//						}
//					} else
//						StartApp();

//				} else
            //    StartApp();

//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			} catch (NameNotFoundException e) {
//				e.printStackTrace();
//			}

                //kaveesha - 14-02-2022
                if (!(username.getText().toString().equalsIgnoreCase("")) && !(password.getText().toString().equalsIgnoreCase(""))) {
                    //temparary for datamation
                    Log.d(">>>", "Validation :: " + username.getText().toString());
                    Log.d(">>>", "Validation :: " + password.getText().toString());



                    if(NetworkUtil.isNetworkAvailable(LoginActivity.this)){
                        new Validate(username.getText().toString().trim(),password.getText().toString()).execute();

                    }else{
                        Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Log.d(">>>", "Validation :: " + username.getText().toString());
                    Log.d(">>>", "Validation :: " + password.getText().toString());
                    Toast.makeText(this, "Please fill the valid credentials", Toast.LENGTH_LONG).show();
                    username.setText("");
                    password.setText("");
                }

            }
            break;

            default:
                break;
        }
    }

    private void StartApp() {
        SharedPreferencesClass.setLocalSharedPreference(getApplicationContext(), "first_login", "Success");
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
//--
    private void LoginValidation() {
        // TODO Auto-generated method stub
        SalRepDS ds = new SalRepDS(getApplicationContext());
        ArrayList<SalRep> list = ds.getSaleRepDetails();
        for (SalRep salRep : list) {

            if (salRep.getREPCODE().equals(username.getText().toString().toUpperCase()) && salRep.getNAME().equals(password.getText().toString().toUpperCase())) {

                StartApp();

            } else {
                Toast.makeText(getApplicationContext(), "Invalid username or password.", Toast.LENGTH_LONG).show();

            }
        }
    }

    public boolean getInternetStatus() {

        boolean conStatus = false;

        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() && wifi.getDetailedState() == DetailedState.CONNECTED) {
            conStatus = true;
        } else if (mobile.isAvailable() && mobile.getDetailedState() == DetailedState.CONNECTED) {
            conStatus = true;
        } else {
            conStatus = false;
        }

        return conStatus;

    }

    // Kaveesha - 14-02-2022
    private class Validate extends AsyncTask<String, Integer, Boolean> {
        int totalRecords = 0;
        CustomProgressDialog pdialog;
        private String username,password;

        public Validate(String uid,String pwd) {
            this.username = uid;
            this.password = pwd;
            this.pdialog = new CustomProgressDialog(LoginActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Validating...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            try {

                String baseURL, restOfURL,dbname;

                try {


                    String domain = pref.getBaseURL();
                    Log.d("baseURL>>>>>>>>>", domain);
                    baseURL = domain + getResources().getString(R.string.ConnectionURL);
                    dbname = pref.getDistDB();
                    restOfURL = "/mobile123/" + dbname;

                    String FsalRep_URL =   baseURL + "/" + "fSalRepNew" + restOfURL + "/" + username + "/" + password;

                    URL json = new URL(FsalRep_URL);

                    URLConnection jc = json.openConnection();
                    BufferedReader readerfdblist = new BufferedReader(new InputStreamReader(jc.getInputStream()));

                    String line = readerfdblist.readLine();
                    JSONObject jsonResponse = new JSONObject(line);

                    if (jsonResponse != null) {
                        pref = SharedPref.getInstance(LoginActivity.this);
                        //dbHandler.clearTables();
                        // Login successful. Proceed to download other items

                        JSONArray repArray = jsonResponse.getJSONArray("fSalRepNewResult");
                        ArrayList<SalRep> salRepList = new ArrayList<>();
                        for (int i = 0; i < repArray.length(); i++) {
                            JSONObject userJSON = repArray.getJSONObject(i);
                            pref.setUserId(userJSON.getString("RepCode"));
                            pref.setUserPwd(userJSON.getString("Password"));

                            salRepList.add(SalRep.parseUser(userJSON));
                        }
                        new SalRepDS(getApplicationContext()).createOrUpdateSalRep(salRepList);
                        pref.setValidateStatus(true);
                        pref.setLoginStatus(true);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pdialog.setMessage("Authenticated...");
                            }
                        });

                        return true;
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid response from server when getting sales rep data", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                } catch (IOException e) {
                    Log.e("networkFunctions ->", "IOException -> " + e.toString());
                    throw e;
                } catch (JSONException e) {
                    Log.e("networkFunctions ->", "JSONException -> " + e.toString());
                    throw e;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

//        protected void onProgressUpdate(Integer... progress) {
//            super.onProgressUpdate(progress);
//            pDialog.setMessage("Prefetching data..." + progress[0] + "/" + totalRecords);
//
//        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (pdialog.isShowing())
                pdialog.cancel();
            // pdialog.cancel();
            if (result) {

                if (pref.getUserPwd().trim().length() > 0) {

                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    String dbname = pref.getDistDB();
                    SharedPreferencesClass.setLocalSharedPreference(LoginActivity.this, "Console_DB", dbname);
                    SharedPreferencesClass.setLocalSharedPreference(LoginActivity.this, "URL", getResources().getString(R.string.baseURL));
                    pref.setValidateStatus(true);

                    StartApp();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    reCallActivity();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Invalid response from server", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void reCallActivity() {
        Intent mainActivity = new Intent(LoginActivity.this, LoginActivity.class);
        startActivity(mainActivity);
        finish();
    }

}
