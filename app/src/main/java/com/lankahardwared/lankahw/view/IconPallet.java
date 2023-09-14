package com.lankahardwared.lankahw.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lankahardwared.lankahw.PushNotification.objMessage;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.GetMacAddress;
import com.lankahardwared.lankahw.adapter.downloadListAdapter;
import com.lankahardwared.lankahw.control.ConnectionDetector;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.control.TaskType;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.control.download.DownloadTaskListener;
import com.lankahardwared.lankahw.control.download.Downloader;
import com.lankahardwared.lankahw.control.upload.UploadDebtorCoordinates;
import com.lankahardwared.lankahw.control.upload.UploadDeposit;
import com.lankahardwared.lankahw.control.upload.UploadExpenses;
import com.lankahardwared.lankahw.control.upload.UploadNonProd;
import com.lankahardwared.lankahw.control.upload.UploadTaskListener;
import com.lankahardwared.lankahw.control.upload.UploadTour;
import com.lankahardwared.lankahw.control.upload.UploadVanSales;
import com.lankahardwared.lankahw.control.upload.UplordNewCustomer;
import com.lankahardwared.lankahw.data.ControlDS;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.DepositHedDS;
import com.lankahardwared.lankahw.data.FDDbNoteDS;
import com.lankahardwared.lankahw.data.FDayExpHedDS;
import com.lankahardwared.lankahw.data.FDaynPrdHedDS;
import com.lankahardwared.lankahw.data.FInvRDetDS;
import com.lankahardwared.lankahw.data.FInvRHedDS;
import com.lankahardwared.lankahw.data.InvDetDS;
import com.lankahardwared.lankahw.data.InvHedDS;
import com.lankahardwared.lankahw.data.MessageDS;
import com.lankahardwared.lankahw.data.NewCustomerDS;
import com.lankahardwared.lankahw.data.OrdFreeIssueDS;
import com.lankahardwared.lankahw.data.OrderDiscDS;
import com.lankahardwared.lankahw.data.PayModeDS;
import com.lankahardwared.lankahw.data.PaymentAllocateDS;
import com.lankahardwared.lankahw.data.PreProductDS;
import com.lankahardwared.lankahw.data.PreSaleTaxDTDS;
import com.lankahardwared.lankahw.data.PreSaleTaxRGDS;
import com.lankahardwared.lankahw.data.RecDetDS;
import com.lankahardwared.lankahw.data.RecHedDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.data.ServerDatabaseDS;
import com.lankahardwared.lankahw.data.SharedPreferencesClass;
import com.lankahardwared.lankahw.data.TourDS;
import com.lankahardwared.lankahw.data.TranIssDS;
import com.lankahardwared.lankahw.data.TranSODetDS;
import com.lankahardwared.lankahw.data.TranSOHedDS;
import com.lankahardwared.lankahw.model.Control;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.NewCustomer;
import com.lankahardwared.lankahw.model.PaymentAllocate;
import com.lankahardwared.lankahw.model.mapper.DepositMapper;
import com.lankahardwared.lankahw.model.mapper.ExpenseMapper;
import com.lankahardwared.lankahw.model.mapper.NonProdMapper;
import com.lankahardwared.lankahw.model.mapper.PreSalesMapper;
import com.lankahardwared.lankahw.model.mapper.ReceiptMapper;
import com.lankahardwared.lankahw.model.mapper.SalesReturnMapper;
import com.lankahardwared.lankahw.model.mapper.TourMapper;
import com.lankahardwared.lankahw.model.mapper.VanSalesMapper;
import com.lankahardwared.lankahw.view.Customer_registration.CustomerRegMain;
import com.lankahardwared.lankahw.view.chat.objUser;
import com.lankahardwared.lankahw.view.deposit.DepositInvoice;
import com.lankahardwared.lankahw.view.expense.ExpenseMain;
import com.lankahardwared.lankahw.view.non_productive.NonProductiveMain;
import com.lankahardwared.lankahw.view.presale.PreSales;
import com.lankahardwared.lankahw.view.presale.PreSalesInvoice;
import com.lankahardwared.lankahw.view.presale.UploadPreSales;
import com.lankahardwared.lankahw.view.receipt.Receipt;
import com.lankahardwared.lankahw.view.receipt.ReceiptInvoice;
import com.lankahardwared.lankahw.view.receipt.UploadReceipt;
import com.lankahardwared.lankahw.view.sales_return.SalesReturn;
import com.lankahardwared.lankahw.view.sales_return.SalesReturnHistory;
import com.lankahardwared.lankahw.view.sales_return.UploadSalesReturn;
import com.lankahardwared.lankahw.view.stock_inquiry.StockInquiryDialog;
import com.lankahardwared.lankahw.view.tour.TourInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.ContentValues.TAG;


public class IconPallet extends Fragment implements View.OnClickListener, DownloadTaskListener, UploadTaskListener {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    SharedPref pref;
    public static String connURLsvc;
    MainActivity activity;
    boolean isSecondarySync = false;
    Animation animScale;
    boolean downloadStock = false;
    boolean isNoActiveInvoice = false, orderApproved = false;
    List<String> resultList;
    ArrayList<Control> downloadList;
    ImageView imgSync, imgUpload, imgPrinter, imgDatabase, imgStockDown, imgStockInq, imgSalesRep
            , imgAddUser, imgTour, imgNonprod, imgExpense, imgReceipt, imgSalRet, imgPresale, imgDashboard, imgMktShr;
    View view;
    boolean isPresalePending = false, isInvoicePending = false, isNonprdPending = false, isSalesReturnPending = false, isReceiptPending = false;
    private TextView msgCount;

    private ArrayList<objUser> users;
    private RecyclerView recyclerView;
    public static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String RepCode;
    String commonRefNo;
    String currentVersion = null;
//    SharedPref pref;

    //icon
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.icon_pallet, container, false);
        activity = (MainActivity) getActivity();

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        getActivity().setTitle("mFiNAC");

        currentVersion = getVersionCode();
        toolbar.setLogo(R.drawable.lhd_logo_64);
        setHasOptionsMenu(true);
        resultList = new ArrayList<>();
        downloadList = new ArrayList<>();
        connURLsvc = getResources().getString(R.string.ConnectionURL);
        localSP = activity.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        //localSP = activity.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITABLE);

        pref = SharedPref.getInstance(getActivity());

        animScale = AnimationUtils.loadAnimation(activity, R.anim.anim_scale);
        imgTour = (ImageView) view.findViewById(R.id.imgTourInfo);
        imgNonprod = (ImageView) view.findViewById(R.id.imgNonProd);
        imgDashboard = (ImageView) view.findViewById(R.id.imgDashboard);
        imgExpense = (ImageView) view.findViewById(R.id.imgExpense);
        imgStockInq = (ImageView) view.findViewById(R.id.imgStockInquiry);
        imgSync = (ImageView) view.findViewById(R.id.imgSync);
        imgUpload = (ImageView) view.findViewById(R.id.imgUpload);
        imgStockDown = (ImageView) view.findViewById(R.id.imgDownload);
        imgPrinter = (ImageView) view.findViewById(R.id.imgPrinter);
        //imgScheduleUp = (ImageButton) view.findViewById(R.id.imgScheUpload);
        imgDatabase = (ImageView) view.findViewById(R.id.imgSqlite);
        imgSalesRep = (ImageView) view.findViewById(R.id.imgSalrep);
        imgSalRet = (ImageView) view.findViewById(R.id.imgReturn);
        imgReceipt = (ImageView) view.findViewById(R.id.imgReceipt);
        imgPresale = (ImageView) view.findViewById(R.id.imgPresale);
        //imgVansale = (ImageButton) view.findViewById(R.id.imgVansale);
        imgAddUser= (ImageView) view.findViewById(R.id.imgAddUser);
        imgMktShr= (ImageView) view.findViewById(R.id.imgMrkShr);

        RepCode = new SalRepDS(getActivity()).getCurrentRepCode();
        Log.d("****", "repCode: "+RepCode);

//        notification = (ImageButton) view.findViewById(R.id.notification);
//        chat = (ImageButton) view.findViewById(R.id.Chat);
        //imgDashboard2 = (ImageButton) view.findViewById(R.id.imgDashboard2);
        if(new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            new checkVersion().execute();
        }else{
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG).show();
        }
        if (activity.synced) {

            if (new TranSOHedDS(activity).validateActivePreSales())
            {
                imgPresale.setImageResource(R.drawable.presale_active);
                isPresalePending = true;
            }
//            else if(new ApprOrdHedDS(activity).isAnyApproved())
//            {
//                imgPresale.setImageResource(R.drawable.presales_approved3);
//                orderApproved = true;
//            }
            else
            {
                isPresalePending = false;
            }

//            if(new FDaynPrdHedDS(activity).validateActiveNonPrd()){
//                imgNonprod.setImageResource(R.drawable.nonprod_pending);
//                isNonprdPending = true;
//            } else
//                isNonprdPending = false;

            if (new FInvRHedDS(getActivity()).isAnyActive()){
                imgSalRet.setImageResource(R.drawable.returns_active);
                isSalesReturnPending = true;
            }
            else
            {
                isSalesReturnPending = false;
            }

            if (new RecHedDS(getActivity()).isAnyActiveRecHed())
            {
                imgReceipt.setImageResource(R.drawable.receipt_active);
                isReceiptPending = true;
            }
            else
            {
                isReceiptPending = false;
            }

            if (new FDaynPrdHedDS(getActivity()).isAnyActiveNonPHed())
            {
                imgNonprod.setImageResource(R.drawable.nonprod_pending);
                isNonprdPending = true;
            }
            else
            {
                isNonprdPending = false;
            }
        }

        imgTour.setOnClickListener(IconPallet.this);
        imgPresale.setOnClickListener(IconPallet.this);
        //imgVansale.setOnClickListener(IconPallet.this);
        imgNonprod.setOnClickListener(IconPallet.this);
        imgDashboard.setOnClickListener(IconPallet.this);
        imgExpense.setOnClickListener(IconPallet.this);
        imgStockInq.setOnClickListener(IconPallet.this);
        imgSync.setOnClickListener(IconPallet.this);
        imgUpload.setOnClickListener(IconPallet.this);
        imgStockDown.setOnClickListener(IconPallet.this);
        imgPrinter.setOnClickListener(IconPallet.this);
        //imgScheduleUp.setOnClickListener(IconPallet.this);
        imgDatabase.setOnClickListener(IconPallet.this);
        imgSalesRep.setOnClickListener(IconPallet.this);
        imgSalRet.setOnClickListener(IconPallet.this);
        imgReceipt.setOnClickListener(IconPallet.this);
//        notification.setOnClickListener(IconPallet.this);
//        chat.setOnClickListener(IconPallet.this);
        //imgDashboard2.setOnClickListener(IconPallet.this);
        imgAddUser.setOnClickListener(IconPallet.this);
        imgMktShr.setOnClickListener(IconPallet.this);


        //get all messages
        MessageDS messageDS = new MessageDS(getActivity());
        ArrayList<objMessage> messages = messageDS.getAllMessages();
        msgCount = (TextView) view.findViewById(R.id.msgCount);
        if (messages.size() > 0) {
            msgCount.setVisibility(View.VISIBLE);
            msgCount.setText("" + messages.size());
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    //   Login("test@gmail.com", "123456");
                }
                // ...
            }
        };

        return view;
    }

    public void Login(String uname, String pw) {

        mAuth.signInWithEmailAndPassword(uname, pw)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            //   Signup("ss@gmail.com", "123456");
                        } else {
                            Log.e("signup", localSP.getString("MAC_Address", "No MAC Address").replace(":","_") + "@gmail.com");
                            // Signup(localSP.getString("MAC_Address", "No MAC Address") + "@gmail.com", "123456");
                        }

                        // ...
                    }

                });
    }

    public void Signup(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Login(localSP.getString("MAC_Address", "No MAC Address") + "@gmail.com", "123456");
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            //  updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        MessageDS messageDS = new MessageDS(getActivity());
        ArrayList<objMessage> messages = messageDS.getAllMessages();
        if (messages.size() > 0) {
            msgCount.setVisibility(View.VISIBLE);
            msgCount.setText("" + messages.size());
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imgTourInfo:
                imgTour.startAnimation(animScale);
                UtilityContainer.mLoadFragment(new TourInfo(), activity);
                break;

//            case R.id.imgDashboard2:
//                imgTour.startAnimation(animScale);
//                UtilityContainer.mLoadFragment(new DashboardSummery(), activity);
//                break;

            case R.id.imgPresale:
                imgPresale.startAnimation(animScale);

                if (isPresalePending) {
                    PreSales preSales = new PreSales();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("Active", true);
                    preSales.setArguments(bundle);
                    UtilityContainer.mLoadFragment(preSales, activity);
                } else
                    UtilityContainer.mLoadFragment(new PreSalesInvoice(), activity);

                break;

//            case R.id.imgVansale:
//                imgVansale.startAnimation(animScale);
//
//                if (isInvoicePending) {
//                    VanSales vanSales = new VanSales();
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean("Active", true);
//                    vanSales.setArguments(bundle);
//                    UtilityContainer.mLoadFragment(vanSales, activity);
//                } else
//                    UtilityContainer.mLoadFragment(new VanSaleInvoice(), activity);
//
//                break;
//            case R.id.notification:
//                notification.startAnimation(animScale);
//
//                if (isInvoicePending) {
//                    MessagesView messagesView = new MessagesView();
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean("Active", true);
//                    messagesView.setArguments(bundle);
//                    UtilityContainer.mLoadFragment(messagesView, activity);
//                } else
//                    UtilityContainer.mLoadFragment(new MessagesView(), activity);
//
//                break;
//
//            case R.id.Chat:
//                chat.startAnimation(animScale);
//
//                if (isInvoicePending) {
//                    DeviceChat deviceChat = new DeviceChat();
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean("Active", true);
//                    deviceChat.setArguments(bundle);
//                    UtilityContainer.mLoadFragment(deviceChat, activity);
//                } else
//                    UtilityContainer.mLoadFragment(new DeviceChat(), activity);
//
//                break;

            case R.id.imgNonProd:
                imgNonprod.startAnimation(animScale);
                if (isNonprdPending){
                    NonProductiveMain nonprd = new NonProductiveMain();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("Active", true);
                    nonprd.setArguments(bundle);
                    UtilityContainer.mLoadFragment(nonprd, activity);
                } else
                    UtilityContainer.mLoadFragment(new NonProductiveMain(), activity);
                break;

            case R.id.imgDashboard:
                // need to validate the secondary sync to navigate for the dashboard
                if (!pref.getTransOrdSumPM().equals("0.0"))
                {
                    imgDashboard.startAnimation(animScale);
                    UtilityContainer.mLoadFragment(new Dashboard_New(), activity);
                }
                else
                {
                    mDevelopingMessage("Please do the secondary sync", "Secondary sync Required");
                }
                break;

            case R.id.imgExpense:
                imgExpense.startAnimation(animScale);
                UtilityContainer.mLoadFragment(new ExpenseMain(), activity);
                break;

            case R.id.imgStockInquiry:
                imgStockInq.startAnimation(animScale);
                new StockInquiryDialog(getActivity());
//                mDevelopingMessage("Still under development", "Stock Inquiry");
                break;

            case R.id.imgSync:
                imgSync.startAnimation(animScale);
                Log.d("**", "onClick: "+new SalRepDS(getActivity()).getCurrentRepCode());
                mSecondarySynchronize();
                break;

            case R.id.imgUpload:
                imgUpload.startAnimation(animScale);
                removeActives();
                mUploadDialog();
                break;

            case R.id.imgDownload:
                imgStockDown.startAnimation(animScale);
                mDownloadStockData(activity);
                break;

            case R.id.imgPrinter:
                imgPrinter.startAnimation(animScale);
                UtilityContainer.mPrinterDialogbox(activity);
                break;

//            case R.id.imgScheUpload:
//                imgScheduleUp.startAnimation(animScale);
//                UtilityContainer.mRescheduleUploadDialogbox(activity);
//                break;

            case R.id.imgSqlite:
                imgDatabase.startAnimation(animScale);
                UtilityContainer.mSQLiteDatabase(activity);
                break;

            case R.id.imgSalrep:
                imgSalesRep.startAnimation(animScale);
                UtilityContainer.mRepsDetailsDialogbox(activity);
                break;
            case R.id.imgReturn:
                imgSalRet.startAnimation(animScale);

                if (isSalesReturnPending) {
                    SalesReturn salesReturn = new SalesReturn();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("Active", true);
                    salesReturn.setArguments(bundle);
                    UtilityContainer.mLoadFragment(salesReturn, activity);
                } else
                {
                    //UtilityContainer.mLoadFragment(new PreSalesInvoice(), activity);
                    UtilityContainer.mLoadFragment(new SalesReturnHistory(), activity);
                }

                //UtilityContainer.mLoadFragment(new SalesReturnHistory(), activity);
                break;
            case R.id.imgReceipt:
                imgReceipt.startAnimation(animScale);

                if (isReceiptPending)
                {
                    Receipt receipt = new Receipt();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("Active", true);
                    receipt.setArguments(bundle);
                    UtilityContainer.mLoadFragment(receipt, activity);
                }
                else
                {
                    UtilityContainer.mLoadFragment(new ReceiptInvoice(),activity);
                }

                break;

            case R.id.imgAddUser:

                UtilityContainer.mLoadFragment(new CustomerRegMain(),activity);
                imgAddUser.startAnimation(animScale);
                //mDevelopingMessage("Still under development", "New Customer");

                break;

            case R.id.imgMrkShr:

                UtilityContainer.mLoadFragment(new DepositInvoice(),activity);
                imgAddUser.startAnimation(animScale);
                //UtilityContainer.mLoadFragment(new DepositInvoice(), activity);
                //mDevelopingMessage("Still under development", "Market Share");

                break;

        }
    }
    public String getVersionCode() {
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            return pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "0";

    }
    public class checkVersion extends AsyncTask<String, String, String> {

        private SweetAlertDialog dialog;

        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new SweetAlertDialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String version = "0";
            try {
                URL json = new URL("http://" + localSP.getString("URL", "").toString()+getResources().getString(R.string.ConnectionURL) +"/fControl/mobile123/" + localSP.getString("Console_DB", "").toString());
                URLConnection jc = json.openConnection();

                BufferedReader readerfdblist = new BufferedReader(new InputStreamReader(jc.getInputStream()));
                String line = readerfdblist.readLine();
                JSONObject jsonResponse = new JSONObject(line);
                JSONArray jsonArray = jsonResponse.getJSONArray("fControlResult");
                JSONObject jObject = (JSONObject) jsonArray.get(0);
                version = jObject.getString("AppVersion");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return version;
        }

        protected void onPostExecute(String newVersion) {
            super.onPostExecute(newVersion);
            if(dialog.isShowing())
                dialog.dismiss();

            if (newVersion != null && !newVersion.isEmpty()) {
                int New = Integer.parseInt(newVersion.replace(".", ""));
                int Current = Integer.parseInt(currentVersion.replace(".", ""));
                //163>162
                if (New > Current) {
                    //show dialog
                    Log.v("UPDATE AVAILABLESSSS", "USSPDATE");
                    // Create custom dialog object

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("New Update Available.")
                            .setContentText("Vesrion : " + newVersion)
                            .setConfirmText("Yes,Update!")
                            .setCancelText("No,cancel!")
                            .showCancelButton(false)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    //Loading();
                                    sDialog.cancel();
                                    getActivity().finish();

                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

//                                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.datamation.dss"));
//                                    startActivity(intent);
                                    final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                }
                            })

                            .show();

                } else {
                    Toast.makeText(getActivity(), "Your application is up to date", Toast.LENGTH_LONG).show();

                }

            } else
            {
                Toast.makeText(getActivity(), "Invalid response from server when check version", Toast.LENGTH_LONG).show();

            }


        }


    }
    public void removeActives()
    {
        String resPreSales = new TranSOHedDS(getActivity()).getActivePreSalesRef();
        String resRetSales = new FInvRHedDS(getActivity()).getActiveReturnRef();
        String comReceipt = new RecHedDS(getActivity()).getActiveReceiptByComRef();
        String sinReceipt = new RecHedDS(getActivity()).getActiveReceiptByRef();

        if (resPreSales!= null && !resPreSales.equals("None"))
        {
            boolean result = new TranSOHedDS(getActivity()).restData(resPreSales);

            if (result)
            {
                new TranSODetDS(getActivity()).restData(resPreSales);
                new PreProductDS(getActivity()).mClearTables();
                new OrderDiscDS(getActivity()).clearData(resPreSales);
                new OrdFreeIssueDS(getActivity()).ClearFreeIssues(resPreSales);
                new PreSaleTaxDTDS(getActivity()).ClearTable(resPreSales);
                new PreSaleTaxRGDS(getActivity()).ClearTable(resPreSales);

                isNoActiveInvoice = true;
            }
            activity.cusPosition = 0;
            activity.selectedDebtor = null;
            activity.selectedSOHed = null;
            UtilityContainer.PreClearSharedPref(getActivity());
        }

        if (resRetSales!= null && !resRetSales.equals("None"))
        {
            int result = new FInvRHedDS(getActivity()).restData(resRetSales);

            if (result > 0) {
                new FInvRDetDS(getActivity()).restData(resRetSales);
                isNoActiveInvoice = true;
            }
            UtilityContainer.ClearReturnSharedPref(getActivity());
            activity.cusPosition = 0;
            activity.selectedRetDebtor = null;
            activity.selectedReturnHed = null;
        }

        if (comReceipt == null)
        {
            if (sinReceipt != null && !sinReceipt.equals("None"))
            {
                new FDDbNoteDS(getActivity()).ClearFddbNoteData();
                new PayModeDS(getActivity()).clearAllPayModeS();
                new RecHedDS(getActivity()).CancelReceiptS(sinReceipt);
                new RecDetDS(getActivity()).clearRecDet(sinReceipt);
                new PaymentAllocateDS(getActivity()).clearPaymentAlloc(sinReceipt);

                isNoActiveInvoice = true;

                activity.cusPosition = 0;
                activity.selectedDebtor = null;
                activity.selectedRecHed = null;
                UtilityContainer.ClearReceiptSharedPref(getActivity());
            }
        }
        else if (!comReceipt.equals("None"))
        {
            new FDDbNoteDS(getActivity()).ClearFddbNoteData();
            new PayModeDS(getActivity()).clearAllPayModeS();
            ArrayList<PaymentAllocate>refList = new PaymentAllocateDS(getActivity()).getRefNoByCommonRef(comReceipt);

            if (refList.size()>0)
            {
                for (PaymentAllocate paymentAllocate: refList)
                {
                    new RecHedDS(getActivity()).CancelReceiptS(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
                    new RecDetDS(getActivity()).clearRecDet(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
                    new PaymentAllocateDS(getActivity()).clearPaymentAlloc(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
                }

                isNoActiveInvoice = true;
            }

            activity.cusPosition = 0;
            activity.selectedDebtor = null;
            activity.selectedRecHed = null;
            UtilityContainer.ClearReceiptSharedPref(getActivity());
        }

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onTaskCompleted(TaskType taskType, String result) {
       // downloadList.addAll(dlist);


       String URL = "http://" + localSP.getString("URL", "").toString();
       SalRepDS salRepDS = new SalRepDS(getActivity());

        int iYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int iMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        String thisM = "/"+String.valueOf(iYear) + "/" + String.valueOf(iMonth);
        String previousM = "/"+String.valueOf(iYear) + "/" + String.valueOf(iMonth-1);

        switch (taskType) {

            case FSALREP:

                Log.v("ICON_PALLET", result.toString());
                if (!result.equals("0")) {
                    String FCONTROL_URL = connURLsvc + "/fControl/mobile123/" + localSP.getString("Console_DB", "").toString();
                    Log.d("ICON_PALLET", " CONNECTION_URL_SVC:" + FCONTROL_URL);
                 new Downloader(getActivity(), IconPallet.this, TaskType.FCONTROL, URL, FCONTROL_URL).execute();

                } else {
                    Toast.makeText(getActivity(), "MAC address is not registered with ERP !", Toast.LENGTH_LONG).show();
                    mSyncDialogbox();
                }
                break;

            case FCONTROL:
                Log.v("FCONTROL", result.toString());
                String downLoadURL = connURLsvc + "/fItemLoc/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FITEMLOC, URL, downLoadURL).execute();

                break;

            case FITEMLOC:
                if (downloadStock) {
                    Log.v("FSTKIN", result.toString());
                    String fstkin_URL = connURLsvc + "/fstkin/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                    new Downloader(getActivity(), IconPallet.this, TaskType.FSTKIN, URL, fstkin_URL).execute();
                } else {
                    Log.v("FITEMLOC", result.toString());
                    String FITEMPRI_URL = connURLsvc + "/fItemPri/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                    new Downloader(getActivity(), IconPallet.this, TaskType.FITEMPRI, URL, FITEMPRI_URL).execute();
                }

                break;

            case FITEMPRI:
                Log.v("FITEMPRI", result.toString());

                String FITEM_URL = connURLsvc + "/fItems/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FITEMS, URL, FITEM_URL).execute();

                break;

            case FITEMS:
                Log.v("FITEMS", result.toString());

                String FDEBTOR_URL = connURLsvc + "/Fdebtor/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FDEBTOR, URL, FDEBTOR_URL).execute();

                break;

            case FDEBTOR:
                Log.v("FDEBTOR", result.toString());

                String FCOMPANYSETTING_URL = connURLsvc + "/fCompanySetting/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FCOMPANYSETTING, URL, FCOMPANYSETTING_URL).execute();

                break;

            case FCOMPANYSETTING:
                Log.v("FCOMPANYSETTING", result.toString());

                String FLOCATIONS_URL = connURLsvc + "/fLocations/mobile123/" + localSP.getString("Console_DB", "")+"/"+salRepDS.getCurrentRepCode().toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FLOCATIONS, URL, FLOCATIONS_URL).execute();

                break;

            case FLOCATIONS:
                Log.v("FLOCATIONS", result.toString());

                String FCOMPANYBRANCH_URL = connURLsvc + "/FCompanyBranch/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FCOMPANYBRANCH, URL, FCOMPANYBRANCH_URL).execute();

                break;

            case FCOMPANYBRANCH:
                Log.v("FCOMPANYBRANCH", result.toString());
                String ftax = connURLsvc + "/Ftax/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FTAX, URL, ftax).execute();

                break;

            case FTAX:
                Log.v("FTAX", result.toString());
                String ftaxhed = connURLsvc + "/Ftaxhed/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FTAXHED, URL, ftaxhed).execute();

                break;

            case FTAXHED:
                Log.v("FTAXHED", result.toString());
                String ftaxdet = connURLsvc + "/Ftaxdet/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FTAXDET, URL, ftaxdet).execute();

                break;
            case FTAXDET:
                Log.v("FTAXDET", result.toString());
                String fddbnote = connURLsvc + "/fDdbNoteWithCondition/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FDDBNOTE, URL, fddbnote).execute();

                break;
            case FDDBNOTE:
                Log.v("FDDBNOTE", result.toString());
                String freason = connURLsvc + "/freason/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FREASON, URL, freason).execute();

                break;

            case FREASON:
                Log.v("FREASON", result.toString());
                String fexpense = connURLsvc + "/fexpense/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FEXPENSE, URL, fexpense).execute();

                break;

            case FEXPENSE:
                Log.v("FEXPENSE", result.toString());
                String tourhed = connURLsvc + "/ftourhed/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FTOURHED, URL, tourhed).execute();

                break;

            case FTOURHED:

                Log.v("FTOURHED", result.toString());
                String FFREEITEM_URL = connURLsvc + "/fFreeslab/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FFREESLAB, URL, FFREEITEM_URL).execute();

                break;

            case FFREESLAB:

                Log.v("FFREESLAB", result.toString());
                String FFREEHed_URL = connURLsvc + "/fFreehed/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FFREEHED, URL, FFREEHed_URL).execute();

                break;

            case FFREEHED:

                Log.v("FFREEHED", result.toString());
                String FFREEdet_URL = connURLsvc + "/fFreedet/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FFREEDET, URL, FFREEdet_URL).execute();

                break;

            case FFREEDET:

                Log.v("FFREEDET", result.toString());
                String FFREEdeb_URL = connURLsvc + "/fFreedeb/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FFREEDEB, URL, FFREEdeb_URL).execute();

                break;

            case FFREEDEB:

                Log.v("FAREA", result.toString());
                String Froute_URL = connURLsvc + "/froute/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FROUTE, URL, Froute_URL).execute();

                break;

            case FROUTE:

                Log.v("FROUTE", result.toString());
                String Ftown_URL = connURLsvc + "/ftown/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FTOWN, URL, Ftown_URL).execute();

                break;

            case FTOWN:

                Log.v("FTOWN", result.toString());
                String Ffree_URL = connURLsvc + "/ffreeitem/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FFREEITEM, URL, Ffree_URL).execute();

                break;

            case FFREEITEM:

                Log.v("FFREEITEM", result.toString());
                String Fdebpri_URL = connURLsvc + "/fdebitempri/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FDEBITEMPRI, URL, Fdebpri_URL).execute();

                break;

            case FDEBITEMPRI:

                Log.v("FDEBITEMPRI", result.toString());
                String Fbank_URL = connURLsvc + "/fbank/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FBANK, URL, Fbank_URL).execute();
                break;

            case FBANK:

                Log.v("FBANK", result.toString());
                String Froutedet_URL = connURLsvc + "/froutedet/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FROUTEDET, URL, Froutedet_URL).execute();

                break;

            case FROUTEDET:
                Log.v("FROUTEDET", result.toString());
                String Fdiscdeb_URL = connURLsvc + "/fdiscdeb/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FDISCDEB, URL, Fdiscdeb_URL).execute();

                break;

            case FDISCDEB:

                Log.v("FDISCDEB", result.toString());
                String Fdiscdet_URL = connURLsvc + "/fdiscdet/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();;
                new Downloader(getActivity(), IconPallet.this, TaskType.FDISCDET, URL, Fdiscdet_URL).execute();

                break;

            case FDISCDET:

                Log.v("FDISCDET", result.toString());
                String Fdiscslab_URL = connURLsvc + "/fdiscslab/mobile123/" + localSP.getString("Console_DB", "").toString()+ "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FDISCSLAB, URL, Fdiscslab_URL).execute();

                break;

            case FDISCSLAB:

                Log.v("FDISCDET", result.toString());
                String Fdisched_URL = connURLsvc + "/fdisched/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FDISCHED, URL, Fdisched_URL).execute();

                break;

            case FDISCHED:

                Log.v("FDISCHED", result.toString());
                String Ffreeslab_URL = connURLsvc + "/ffreemslab/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FFREEMSLAB, URL, Ffreeslab_URL).execute();

                break;

            case FFREEMSLAB:

                Log.v("FFREEMSLAB", result.toString());
                String Farea_URL = connURLsvc + "/farea/mobile123/" + localSP.getString("Console_DB", "").toString();
                new Downloader(getActivity(), IconPallet.this, TaskType.FAREA, URL, Farea_URL).execute();

                break;

            case FAREA:

                Log.v("FAREA", result.toString());
                String fstkin_URL = connURLsvc + "/fstkin/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FSTKIN, URL, fstkin_URL).execute();

                break;

            case FSTKIN:

                Log.v("FSTKIN", result.toString());
                String FSTKIN_URL = connURLsvc + "/fbrandtarget/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                new Downloader(getActivity(), IconPallet.this, TaskType.FBRANDTARGET, URL, FSTKIN_URL).execute();

                break;

            case FBRANDTARGET:

                Log.v("fItTourDisc", result.toString());
                String FItTourDisc_URL = connURLsvc + "/fItTourDisc/mobile123/" + localSP.getString("Console_DB", "").toString();
                Log.d("ICON_PALLET_TOUR_DISC", "URL:" + FItTourDisc_URL);
                new Downloader(getActivity(), IconPallet.this, TaskType.FITTOURDISC, URL, FItTourDisc_URL).execute();

                break;

            case FITTOURDISC:

                Log.v("FDEBTAX", result.toString());
                String FDebTax_URL = connURLsvc + "/fdebtax/mobile123/" + localSP.getString("Console_DB", "").toString();
                Log.d("ICON_PALLET_DEBTAX", "URL:" + FDebTax_URL);
                new Downloader(getActivity(), IconPallet.this, TaskType.FDEBTAX, URL, FDebTax_URL).execute();

                break;

            case FDEBTAX:

                Log.v("fbrandtarget", result.toString());
                String FAPPRORDHED_URL = connURLsvc + "/fApprOrdHed/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                Log.d("ICON_PALLET_APPRORDHED", "URL:" + FAPPRORDHED_URL);
                new Downloader(getActivity(), IconPallet.this, TaskType.FAPPRORDHED, URL, FAPPRORDHED_URL).execute();

                break;


            case FAPPRORDHED:
                int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
                int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
                Log.v("invoiceSale", result.toString());
                String invoiceSaleTM_URL = connURLsvc + "/invoiceSale/mobile123/" + localSP.getString("Console_DB", "").toString()+ "/" + salRepDS.getCurrentRepCode()+ "/" +curYear+ "/" +curMonth;
                Log.d("ICON_PALLET_invoiceSale", "URL:" + invoiceSaleTM_URL);
                new Downloader(getActivity(), IconPallet.this, TaskType.INVOICESALETM, URL, invoiceSaleTM_URL).execute();

                break;
            case INVOICESALETM:
                int currentMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
                int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
                int previousMonth = currentMonth -1;

                Log.v("invoiceSale", result.toString());
                String invoiceSalePM_URL = connURLsvc + "/invoiceSale/mobile123/" + localSP.getString("Console_DB", "").toString()+ "/" + salRepDS.getCurrentRepCode()+ "/" +currentYear+ "/" +previousMonth;
                Log.d("ICON_PALLET_invoiceSale", "URL:" + invoiceSalePM_URL);
                new Downloader(getActivity(), IconPallet.this, TaskType.INVOICESALEPM, URL, invoiceSalePM_URL).execute();

                break;
            case INVOICESALEPM:

                Log.v("fapprordhed", result.toString());
                String FDistrict_URL = connURLsvc + "/fDistrict/mobile123/" + localSP.getString("Console_DB", "").toString();
                Log.d("ICON_PALLET_DISTRICT", "URL:" + FDistrict_URL);
                new Downloader(getActivity(), IconPallet.this, TaskType.FDISTRICT, URL, FDistrict_URL).execute();

                break;
            case FDISTRICT:
                Log.v("FTARGETDET", result.toString());
                String targetDet_URL = connURLsvc + "/FRepTrgDet/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                Log.d("FTARGETDET","URL:" + targetDet_URL);
                new Downloader(getActivity(), IconPallet.this, TaskType.FTARGETDET, URL, targetDet_URL).execute();

                break;

            case FTARGETDET:
                Log.v("FACHIEVEMENT", result.toString());
                String achieve_URL = connURLsvc + "/getRepAchi/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + salRepDS.getCurrentRepCode();
                Log.d("FACHIEVEMENT","URL:" + achieve_URL);
                new Downloader(getActivity(), IconPallet.this, TaskType.FACHIEVEMENT, URL, achieve_URL).execute();

                break;

            case FACHIEVEMENT:

                Log.v("FOTHERTRANS", result.toString());
                String fothertrans = connURLsvc + "/FOtherTrans/mobile123/" + localSP.getString("Console_DB", "").toString()+"/"+ salRepDS.getCurrentRepCode();
                Log.d("FOTHERTRANS_url ", "URL:" + fothertrans);
                new Downloader(getActivity(), IconPallet.this, TaskType.FOTHERTRANS, URL, fothertrans).execute();

                break;

            case FOTHERTRANS:

                new Downloader(getActivity(), IconPallet.this, TaskType.MONTHORDERSUM, URL, setOrderEndPoint(thisM)).execute();

                break;

            case MONTHORDERSUM:

                new Downloader(getActivity(), IconPallet.this, TaskType.MONTHORDERDISCSUM, URL, setOrderDiscEndPoint(thisM)).execute();

                break;

            case MONTHORDERDISCSUM:

                new Downloader(getActivity(), IconPallet.this, TaskType.MONTHORDERSUMPM, URL, setOrderEndPoint(previousM)).execute();

                break;

            case MONTHORDERSUMPM:

                new Downloader(getActivity(), IconPallet.this, TaskType.DAYORDERSUM, URL, setDayOrderTotEndPoint()).execute();

                break;

            case DAYORDERSUM:

                new Downloader(getActivity(), IconPallet.this, TaskType.DAYDISCOUNTSSUM, URL, setDayDiscountTotEndPoint()).execute();

                break;

            case DAYDISCOUNTSSUM:

                new Downloader(getActivity(), IconPallet.this, TaskType.DAYRETURNSUM, URL, setDayReturnTotEndPoint()).execute();

                break;

            case DAYRETURNSUM:

                new Downloader(getActivity(), IconPallet.this, TaskType.DAYPRODUCTIVESUM, URL, setDayProductiveTotEndPoint()).execute();

                break;

            case DAYPRODUCTIVESUM:

                new Downloader(getActivity(), IconPallet.this, TaskType.DAYNONPRODUCTIVESUM, URL, setDayNonProductiveTotEndPoint()).execute();

                break;

            case DAYNONPRODUCTIVESUM:

                new Downloader(getActivity(), IconPallet.this, TaskType.DAYINVOICESUM, URL, setDayInvoiceTotEndPoint()).execute();

                break;

            case DAYINVOICESUM:

                new Downloader(getActivity(), IconPallet.this, TaskType.MONTHRETURNSUMTM, URL, setMonthReturnEndPoint(thisM)).execute();

                break;

            case MONTHRETURNSUMTM:

                new Downloader(getActivity(), IconPallet.this, TaskType.MONTHRETURNSUMPM, URL, setMonthReturnEndPoint(previousM)).execute();

                break;

            case MONTHRETURNSUMPM:

                new Downloader(getActivity(), IconPallet.this, TaskType.MONTHPRODSUMTM, URL, setMonthProdEndPoint(thisM)).execute();

                break;

            case MONTHPRODSUMTM:

                new Downloader(getActivity(), IconPallet.this, TaskType.MONTHPRODSUMPM, URL, setMonthProdEndPoint(previousM)).execute();

                break;

            case MONTHPRODSUMPM:

                new Downloader(getActivity(), IconPallet.this, TaskType.MONTHNONPROSUMTM, URL, setMonthNonProdEndPoint(thisM)).execute();

                break;

            case MONTHNONPROSUMTM:

                new Downloader(getActivity(), IconPallet.this, TaskType.MONTHNONPROSUMPM, URL, setMonthNonProdEndPoint(previousM)).execute();

                break;

            case MONTHNONPROSUMPM:

                new Downloader(getActivity(), IconPallet.this, TaskType.MONTHORDERDISCSUMPM, URL, setOrderDiscEndPoint(previousM)).execute();

                break;

            case MONTHORDERDISCSUMPM:

                SharedPreferencesClass.setLocalSharedPreference(getActivity(), "Sync_Status", "Success");

                Log.v("ICON_PALLET_fDistrict", result.toString() + ", " + RepCode + ", " + localSP.getString("Sync_Status", "").toString());
                Log.d("ICON_PALLET_fDistrict", "onTaskCompleted: "+ RepCode+" new*** ");
               // downloadList = new ControlDS(activity).getAllDownload();

                int i = 1;
                for (Control c : new ControlDS(getActivity()).getAllDownload()) {
                    downloadList.add(c);
                    i++;
                }
//                String dmsg = "";
//                for (String s : downloadList) {
//                    dmsg += s;
//                }

                if(downloadList.size()>0) {
                    mDownloadResult(downloadList);
                }
                downloadList.clear();
                if (!RepCode.equals("") && localSP.getString("Sync_Status", "").toString().equals("Success")) {

                    if (downloadStock) {
                        downloadStock = false;
                        Toast.makeText(getActivity(), "Stock download completed successfully!", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getActivity(), "Sync completed successfully!", Toast.LENGTH_LONG).show();

                    if (!isSecondarySync) {
                        Intent mainActivity = new Intent(activity, LoginActivity.class);
                        startActivity(mainActivity);
                        activity.finish();
                    }

                } else {
                    Toast.makeText(getActivity(), "Sync unsuccessful!.Please check sales representative data...", Toast.LENGTH_LONG).show();
                    Intent mainActivity = new Intent(activity, SplashActivity.class);
                    startActivity(mainActivity);
                    activity.finish();
                }

                break;



            default:
                break;
        }
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mSyncDialogbox() {

        final String sp_url = localSP.getString("URL", "").toString();
        String spConsole_DB = localSP.getString("Console_DB", "").toString();

        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View promptView = layoutInflater.inflate(R.layout.settings_sync_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Download Data");
        alertDialogBuilder.setView(promptView);

        final EditText serverURL = (EditText) promptView.findViewById(R.id.et_server_url);
        final Spinner sp_serverDB = (Spinner) promptView.findViewById(R.id.spinner_server_db);

        ServerDatabaseDS ds = new ServerDatabaseDS(activity);

        List<String> list = ds.getAllDatabaseName();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_serverDB.setAdapter(dataAdapter);

        if (!sp_url.equals(""))
            serverURL.setText(sp_url);

        if (!spConsole_DB.equals("")) {
            ArrayAdapter myAdap = (ArrayAdapter) sp_serverDB.getAdapter();
            int spinnerPosition = myAdap.getPosition(spConsole_DB);
            sp_serverDB.setSelection(spinnerPosition);
        }

        alertDialogBuilder.setCancelable(false).setPositiveButton("DOWNLOAD", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String selectedDB = sp_serverDB.getSelectedItem().toString();
                SharedPreferencesClass.setLocalSharedPreference(activity, "Console_DB", selectedDB);

                if (localSP.getString("MAC_Address", "No MAC Address").equals("No MAC Address")) {


                    GetMacAddress macAddress = new GetMacAddress();
                    SharedPreferencesClass.setLocalSharedPreference(activity, "MAC_Address", macAddress.getMacAddress(activity));
                }
                Log.e("Mac", localSP.getString("MAC_Address", "No MAC Address"));

                //register user with firebase db for chating
                String URL = "http://" + sp_url;
                boolean connectionStatus = new ConnectionDetector(activity).isConnectingToInternet();
                if (connectionStatus == true) {

                    if (Patterns.WEB_URL.matcher(URL).matches()) {

                        try {
                            String FSALREP_URL = connURLsvc + "/fSalRep/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + localSP.getString("MAC_Address", "").toString().replace(":", "");
                            new Downloader(activity, IconPallet.this, TaskType.FSALREP, URL, FSALREP_URL).execute();
                        } catch (Exception e) {

                        }

                    } else {
                        Toast.makeText(activity, "Invalid URL Entered. Please Enter Valid URL.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }


    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mRemoveActiveInvoice() {

        // clear active Invoice
        String refno = new InvHedDS(activity).getActiveInvoiceRef();

        if (!new InvHedDS(activity).getActiveInvoiceRef().equals("None")) {

            new InvHedDS(activity).restData(refno);
            new TranIssDS(activity).ClearTable(refno);
            new InvDetDS(activity).restData(refno);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();

            isNoActiveInvoice = true;
        }


        UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mSecondarySynchronize() {

        isSecondarySync = true;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("Do you want to sync data..?");
        alertDialogBuilder.setTitle("Synchronize Data");
        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(DialogInterface dialog, int id) {

                boolean connectionStatus = new ConnectionDetector(activity).isConnectingToInternet();

                if (connectionStatus)
                {
                    String sText = "Complete following transactions to continue;\n";

                    String resPreSales = new TranSOHedDS(getActivity()).getActivePreSalesRef();

                    if (!resPreSales.equals("None"))
                        sText += "\nPre Sales : " + resPreSales;

                    String resVanSales = new InvHedDS(activity).getActiveInvoiceRef();

                    if (!resVanSales.equals("None"))
                        sText += "\nVan Sales : " + resVanSales;

                    String resRetSales = new FInvRHedDS(getActivity()).getActiveReturnRef();

                    if (!resRetSales.equals("None"))
                        sText += "\nRet Sales --> " + resRetSales;

                    String resReceipt = new RecHedDS(getActivity()).getActiveReceiptByRef();

                    if (!resReceipt.equals("None"))
                        sText += "\nRec Sales --> " + resReceipt;

                    if (sText.length() >= 45) {
                        String URL = "http://" + localSP.getString("URL", "").toString();
//                        String FSALREP_URL = connURLsvc + "/fSalRep/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + localSP.getString("MAC_Address", "").toString().replace(":", "");
                        String FSALREP_URL_NEW = connURLsvc + "/fSalRepNew/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + pref.getUserId() + "/" + pref.getUserPwd();
                        if (new InvHedDS(activity).isAllSynced() && new TranSOHedDS(activity).isAllSynced()) {
                            new ControlDS(activity).deleteAll();
                            new Downloader(activity, IconPallet.this, TaskType.FSALREP, URL, FSALREP_URL_NEW).execute();
                        }else
                            Toast.makeText(activity, "Please upload completed invoices before sync..!", Toast.LENGTH_SHORT).show();
                    } else
                        mActiveTransactionDialog(sText);
                }
                else
                {
//                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    noInternetDialog();
                }
            }

        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mDownloadStockData(final Context context) {

        isSecondarySync = true;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("Do you want to download stock..?");
        alertDialogBuilder.setTitle("Stock Update");
        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(DialogInterface dialog, int id) {
        new ControlDS(activity).deleteAll();
                String sText = "Complete following transactions to continue;\n";

                String resPreSales = new TranSOHedDS(getActivity()).getActivePreSalesRef();

                if (!resPreSales.equals("None"))
                    sText += "\nPre Sales : " + resPreSales;

                String resVanSales = new InvHedDS(activity).getActiveInvoiceRef();

                if (!resVanSales.equals("None"))
                    sText += "\nVan Sales : " + resVanSales;

                if (sText.length() >= 45) {

                    final String downLoadURL = connURLsvc + "/fItemLoc/mobile123/" + localSP.getString("Console_DB", "").toString() + "/" + new SalRepDS(context).getCurrentRepCode();

                    if (new InvHedDS(context).isAllSynced() && new TranSOHedDS(context).isAllSynced()) {
                        downloadStock = true;
                        new Downloader(context, IconPallet.this, TaskType.FCONTROL, downLoadURL, "").execute();

                    } else
                        Toast.makeText(context, "Please upload completed invoices before sync..!", Toast.LENGTH_SHORT).show();

                } else
                    mActiveTransactionDialog(sText);


            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
//    public void onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//        menu.clear();
//        Log.d("ICON_PALLET", "GOT_ITEM: " + getActivity());
//        //getActivity().getMenuInflater().inflate(R.menu.mnu_exit, menu);
//    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.quit:

//                List<Fragment> list = activity.getSupportFragmentManager().getFragments();
//                activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).remove(list.get(0)).commit();
//
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                pref.setUserId("");
                pref.setUserPwd("");
                getActivity().finish();

//                    }
//                }, 1000);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }
        inflater.inflate(R.menu.mnu_exit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private void mUploadDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("Do you want to upload data..?");
        alertDialogBuilder.setTitle("Uploader");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("UPLOAD", new DialogInterface.OnClickListener() {
            @SuppressWarnings("unchecked")
            public void onClick(DialogInterface dialog, int id) {

                boolean connectionStatus = new ConnectionDetector(activity).isConnectingToInternet();

                if (connectionStatus == true)
                {
//                    ArrayList<PreSalesMapper> ordHedList = new TranSOHedDS(getActivity()).getAllUnSyncOrdHed();
//                    ArrayList<VanSalesMapper> InvHedList = new InvHedDS(getActivity()).getAllUnsynced();
//                    ArrayList<TourMapper> TourList = new TourDS(getActivity()).getUnsyncedTourData();
//                    ArrayList<ExpenseMapper> Explist = new FDayExpHedDS(getActivity()).getUnSyncedData();
//                    ArrayList<NonProdMapper> NonProdList = new FDaynPrdHedDS(getActivity()).getUnSyncedData();
//                    ArrayList<NewCustomer>customerArrayList=new NewCustomerDS(getActivity()).getUnsyncRecord();
//                    ArrayList<ReceiptMapper> ReceiptList = new RecHedDS(getActivity()).getAllUnsyncedRecHed();
//                    ArrayList<SalesReturnMapper>ReturnList = new FInvRHedDS(getActivity()).getAllUnsynced();
//
//                    /* If records available for upload then */
//                    if ((ordHedList.size() <= 0) && (InvHedList.size() <= 0) && (TourList.size() <= 0) && (Explist.size() <= 0) && (NonProdList.size() <= 0) &&(customerArrayList.size()<=0)&& (ReceiptList.size() <= 0)&& (ReturnList.size() <= 0))
//                        Toast.makeText(getActivity(), "No Records to upload !", Toast.LENGTH_LONG).show();
//                        //UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
//
//                    else
//                        new UploadPreSales(activity, IconPallet.this, TaskType.UPLOADSPRESALE).execute(ordHedList);

                    String sText = "Complete following transactions to continue;\n";

                    String resPreSales = new TranSOHedDS(getActivity()).getActivePreSalesRef();

                    if (!resPreSales.equals("None"))
                        sText += "\nPre Sales --> " + resPreSales;

                    String resVanSales = new InvHedDS(activity).getActiveInvoiceRef();

                    if (!resVanSales.equals("None"))
                        sText += "\nVan Sales --> " + resVanSales;

                    String resRetSales = new FInvRHedDS(getActivity()).getActiveReturnRef();

                    if (!resRetSales.equals("None"))
                        sText += "\nVan Sales --> " + resRetSales;

                    if (sText.length() == 45) {

                        ArrayList<PreSalesMapper> ordHedList = new TranSOHedDS(getActivity()).getAllUnSyncOrdHed();
                        ArrayList<VanSalesMapper> InvHedList = new InvHedDS(getActivity()).getAllUnsynced();
                        ArrayList<TourMapper> TourList = new TourDS(getActivity()).getUnsyncedTourData();
                        ArrayList<ExpenseMapper> Explist = new FDayExpHedDS(getActivity()).getUnSyncedData();
                        ArrayList<NonProdMapper> NonProdList = new FDaynPrdHedDS(getActivity()).getUnSyncedData();
                        ArrayList<NewCustomer>customerArrayList=new NewCustomerDS(getActivity()).getUnsyncRecord();
                        ArrayList<ReceiptMapper> ReceiptList = new RecHedDS(getActivity()).getAllUnsyncedRecHed();
                        ArrayList<SalesReturnMapper>ReturnList = new FInvRHedDS(getActivity()).getAllUnsynced();
                        ArrayList<DepositMapper>DepositList = new DepositHedDS(getActivity()).getAllUnsynced();
                        ArrayList<Debtor> DebtorCoordinateList = new DebtorDS(getActivity()).getAllDebtorsToCordinatesUpdate();


                        for (int i=0; i<ReturnList.size(); i++)
                        {
                            Log.d("ICON_PALLET", "REP_CODE_IS: " + ReturnList.get(i).getFINVRHED_REPCODE());
                        }


                        /* If records available for upload then */
                        if ((ordHedList.size() <= 0) && (InvHedList.size() <= 0) && (TourList.size() <= 0) && (Explist.size() <= 0) && (NonProdList.size() <= 0) &&(customerArrayList.size()<=0)&& (ReceiptList.size() <= 0)&& (ReturnList.size() <= 0) && (DepositList.size() <= 0) && (DebtorCoordinateList.size() <= 0))
                            Toast.makeText(getActivity(), "No Records to upload !", Toast.LENGTH_LONG).show();
                        else
                            new UploadPreSales(activity, IconPallet.this, TaskType.UPLOADSPRESALE).execute(ordHedList);

                    } else
                        mActiveTransactionDialog(sText);

                } else
//                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    noInternetDialog();

//                if (connectionStatus == true) {
//
//                    String sText = "Complete following transactions to continue;\n";
//
//                    String resPreSales = new TranSOHedDS(getActivity()).getActiveInvoiceRef();
//
//
//                    if (!resPreSales.equals("None"))
//                        sText += "\nPre Sales --> " + resPreSales;
//
//                    String resVanSales = new InvHedDS(activity).getActiveInvoiceRef();
//
//                    if (!resVanSales.equals("None"))
//                        sText += "\nVan Sales --> " + resVanSales;
//
//                    String resRetSales = new FInvRHedDS(getActivity()).getActiveReturnRef();
//
//                    if (!resRetSales.equals("None"))
//                        sText += "\nRet Sales --> " + resRetSales;
//
//                    //clear multi receipt by common ref no
//                    String comReceipt = new RecHedDS(getActivity()).getActiveReceiptByComRef();
//
//                    if (comReceipt != null)
//                    {
//                        if (!comReceipt.equals("None"))
//                        {
//                            sText += "\nReceipt --> " + comReceipt;
//
//                            new FDDbNoteDS(getActivity()).ClearFddbNoteData();
//                            new PayModeDS(getActivity()).clearAllPayModeS();
//                            ArrayList<PaymentAllocate>refList = new PaymentAllocateDS(getActivity()).getRefNoByCommonRef(comReceipt);
//
//                            if (refList.size()>0)
//                            {
//                                for (PaymentAllocate paymentAllocate: refList)
//                                {
//                                    new RecHedDS(getActivity()).CancelReceiptS(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
//                                    new RecDetDS(getActivity()).clearRecDet(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
//                                    new PaymentAllocateDS(getActivity()).clearPaymentAlloc(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
//                                }
//                            }
//
//                            activity.cusPosition = 0;
//                            activity.selectedDebtor = null;
//                            activity.selectedRecHed = null;
//                            UtilityContainer.ClearReceiptSharedPref(getActivity());
//                        }
////                        else
////                        {
////                            String sinReceipt = new RecHedDS(getActivity()).getActiveReceiptByRef();
////
////                            if (!sinReceipt.equals("None"))
////                            {
////                                sText += "\nReceipt --> " + sinReceipt;
////
////                                new FDDbNoteDS(getActivity()).ClearFddbNoteData();
////                                new PayModeDS(getActivity()).clearAllPayModeS();
////                                new RecHedDS(getActivity()).CancelReceiptS(sinReceipt);
////                                new RecDetDS(getActivity()).clearRecDet(sinReceipt);
////                                new PaymentAllocateDS(getActivity()).clearPaymentAlloc(sinReceipt);
////
////                                activity.cusPosition = 0;
////                                activity.selectedDebtor = null;
////                                activity.selectedRecHed = null;
////                                UtilityContainer.ClearReceiptSharedPref(getActivity());
////                            }
////                        }
//                    }
//                    else
//                    {
//                        // clear single receipt by ref no
//
//                        String sinReceipt = new RecHedDS(getActivity()).getActiveReceiptByRef();
//
//                        if (!sinReceipt.equals("None"))
//                        {
//                            sText += "\nReceipt --> " + sinReceipt;
//
//                            new FDDbNoteDS(getActivity()).ClearFddbNoteData();
//                            new PayModeDS(getActivity()).clearAllPayModeS();
//                            new RecHedDS(getActivity()).CancelReceiptS(sinReceipt);
//                            new RecDetDS(getActivity()).clearRecDet(sinReceipt);
//                            new PaymentAllocateDS(getActivity()).clearPaymentAlloc(sinReceipt);
//
//                            activity.cusPosition = 0;
//                            activity.selectedDebtor = null;
//                            activity.selectedRecHed = null;
//                            UtilityContainer.ClearReceiptSharedPref(getActivity());
//                        }
//                    }
//
//                    if (sText.length() == 45)
//                    {
//                        ArrayList<PreSalesMapper> ordHedList = new TranSOHedDS(getActivity()).getAllUnSyncOrdHed();
//                        ArrayList<VanSalesMapper> InvHedList = new InvHedDS(getActivity()).getAllUnsynced();
//                        ArrayList<TourMapper> TourList = new TourDS(getActivity()).getUnsyncedTourData();
//                        ArrayList<ExpenseMapper> Explist = new FDayExpHedDS(getActivity()).getUnSyncedData();
//                        ArrayList<NonProdMapper> NonProdList = new FDaynPrdHedDS(getActivity()).getUnSyncedData();
//                        ArrayList<NewCustomer>customerArrayList=new NewCustomerDS(getActivity()).getUnsyncRecord();
//                        ArrayList<ReceiptMapper> ReceiptList = new RecHedDS(getActivity()).getAllUnsyncedRecHed();
//                        ArrayList<SalesReturnMapper>ReturnList = new FInvRHedDS(getActivity()).getAllUnsynced();
//
//					/* If records available for upload then */
//                        if ((ordHedList.size() <= 0) && (InvHedList.size() <= 0) && (TourList.size() <= 0) && (Explist.size() <= 0) && (NonProdList.size() <= 0) &&(customerArrayList.size()<=0)&& (ReceiptList.size() <= 0)&& (ReturnList.size() <= 0))
//                        {
//                            Toast.makeText(getActivity(), "No Records to upload !", Toast.LENGTH_LONG).show();
//                        }
//                        else
//                        {
//                            new UploadPreSales(activity, IconPallet.this, TaskType.UPLOADSPRESALE).execute(ordHedList);
//                            //UtilityContainer.mLoadFragment(new ReceiptInvoice(), getActivity());
//                        }
//                    }
//                    else
//                    {
//                        mActiveDialog("Before upload clear all actives");
//
//                    }
//
//                } else
//                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onTaskCompleted(TaskType taskType, List<String> tempList) {

        resultList.addAll(tempList);

        switch (taskType) {

            case UPLOADSPRESALE: {
                ArrayList<VanSalesMapper> list = new InvHedDS(getActivity()).getAllUnsynced();
                new UploadVanSales(getActivity(), IconPallet.this, TaskType.UPLOADVANSALES).execute(list);
            }
            break;

            case UPLOADVANSALES: {
                ArrayList<TourMapper> list = new TourDS(getActivity()).getUnsyncedTourData();
                new UploadTour(getActivity(), IconPallet.this, TaskType.UPLOADTOUR).execute(list);
            }
            break;

            case UPLOADTOUR: {
                ArrayList<ExpenseMapper> list = new FDayExpHedDS(getActivity()).getUnSyncedData();
                new UploadExpenses(getActivity(), IconPallet.this, TaskType.UPLOAD_EXPENSE).execute(list);
            }
            break;

            case UPLOAD_EXPENSE: {
                ArrayList<NonProdMapper> list = new FDaynPrdHedDS(getActivity()).getUnSyncedData();
                new UploadNonProd(getActivity(), IconPallet.this, TaskType.UPLOAD_NONPROD).execute(list);
            }
            break;
            case UPLOAD_NONPROD: {
                ArrayList<NewCustomer> list = new NewCustomerDS(getActivity()).getUnsyncRecord();
                new UplordNewCustomer(getActivity(), IconPallet.this, TaskType.UPLOADNEWCUSTOMER).execute(list);
            }
            break;
            case UPLOADNEWCUSTOMER: {
                ArrayList<ReceiptMapper> list = new RecHedDS(getActivity()).getAllUnsyncedRecHed();
                new UploadReceipt(getActivity(), IconPallet.this, TaskType.UPLOADSALESRETURN).execute(list);
            }
            break;

            case UPLOADSALESRETURN: {
                ArrayList<SalesReturnMapper> list = new FInvRHedDS(getActivity()).getAllUnsynced();
                new UploadSalesReturn(getActivity(), IconPallet.this, TaskType.UPLOADDEPOSIT).execute(list);
            }
            break;

            case UPLOADDEPOSIT: {
                ArrayList<DepositMapper> list = new DepositHedDS(getActivity()).getAllUnsynced();
                new UploadDeposit(getActivity(), IconPallet.this, TaskType.UPLOADRECEIPT).execute(list);
            }
            break;
            case UPLOADRECEIPT: {
                ArrayList<Debtor> list = new DebtorDS(getActivity()).getAllDebtorsToCordinatesUpdate();
                new UploadDebtorCoordinates(getActivity(), IconPallet.this, TaskType.UPLOADDEBTORGPS).execute(list);
            }
            break;
            case UPLOADDEBTORGPS:{
//                String msg = "";
//                for (String s : resultList) {
//                    msg += s;
//                }
//                resultList.clear();
//                mUploadResult(msg);
//
                //
                if (isNoActiveInvoice)
                    UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
            }
            break;

            default:
                break;
        }
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mActiveTransactionDialog(String sMessage) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(sMessage);
        alertDialogBuilder.setTitle("Active Transactions");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_info);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void noInternetDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("Please check your Internet Connection and sync again.");
        alertDialogBuilder.setTitle("No Internet");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void mActiveDialog(String sMessage) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(sMessage);
        alertDialogBuilder.setTitle("Active Transactions");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_info);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mRemoveActiveInvoice();

                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mUploadResult(String message) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setTitle("Upload Summary");

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }
    private String getCustomText(int index){
        String fld = "", clr = "#EE0000";
        fld = downloadList.get(index).getFCONTROL_COM_NAME()+" - "+downloadList.get(index).getFCONTROL_COM_ADD1()+"/"+downloadList.get(index).getFCONTROL_COM_ADD2();
        if(!downloadList.get(index).getFCONTROL_COM_ADD1().equals(downloadList.get(index).getFCONTROL_COM_ADD2()) || downloadList.get(index).getFCONTROL_COM_ADD1().equals("0"))
            clr = "#EE0000";
        else
            clr = "#4CAF50";

        fld = fld.split("-")[0] + "<font color='"+clr+"'><b>"+fld.split("-")[1]+"</b></font>";

        return fld;
    }
    public void mDownloadResult(ArrayList<Control> downloadList) {

        final Dialog repDialog = new Dialog(activity);
        repDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        repDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        repDialog.setCancelable(false);
        repDialog.setCanceledOnTouchOutside(false);
        repDialog.setContentView(R.layout.download_dialog);

        ListView downloadlist = (ListView) repDialog.findViewById(R.id.download_listview);
        downloadList = new ControlDS(getActivity()).getAllDownload();
        downloadlist.setAdapter(new downloadListAdapter(getActivity(), downloadList));



//
//        //initializations
//
//        TextView field1 =  (TextView) repDialog.findViewById(R.id.field1 );
//        TextView field2 =  (TextView) repDialog.findViewById(R.id.field2 );
//        TextView field3 =  (TextView) repDialog.findViewById(R.id.field3 );
//        TextView field4 =  (TextView) repDialog.findViewById(R.id.field4 );
//        TextView field5 =  (TextView) repDialog.findViewById(R.id.field5 );
//        TextView field6 =  (TextView) repDialog.findViewById(R.id.field6 );
//        TextView field7 =  (TextView) repDialog.findViewById(R.id.field7 );
//        TextView field8 =  (TextView) repDialog.findViewById(R.id.field8 );
//        TextView field9 =  (TextView) repDialog.findViewById(R.id.field9 );
//        TextView field10 = (TextView) repDialog.findViewById(R.id.field10);
//        TextView field11 = (TextView) repDialog.findViewById(R.id.field11);
//        TextView field12 = (TextView) repDialog.findViewById(R.id.field12);
//        TextView field13 = (TextView) repDialog.findViewById(R.id.field13);
//        TextView field14 = (TextView) repDialog.findViewById(R.id.field14);
//        TextView field15 = (TextView) repDialog.findViewById(R.id.field15);
//        TextView field16 = (TextView) repDialog.findViewById(R.id.field16);
//        TextView field17 = (TextView) repDialog.findViewById(R.id.field17);
//        TextView field18 = (TextView) repDialog.findViewById(R.id.field18);
//        TextView field19 = (TextView) repDialog.findViewById(R.id.field19);
//        TextView field20 = (TextView) repDialog.findViewById(R.id.field20);
//        TextView field21 = (TextView) repDialog.findViewById(R.id.field21);
//        TextView field22 = (TextView) repDialog.findViewById(R.id.field22);
//        TextView field23 = (TextView) repDialog.findViewById(R.id.field23);
//        TextView field24 = (TextView) repDialog.findViewById(R.id.field24);
//        TextView field25 = (TextView) repDialog.findViewById(R.id.field25);
//        TextView field26 = (TextView) repDialog.findViewById(R.id.field26);
//        TextView field27 = (TextView) repDialog.findViewById(R.id.field27);
//        TextView field28 = (TextView) repDialog.findViewById(R.id.field28);
//        TextView field29 = (TextView) repDialog.findViewById(R.id.field29);
//        TextView field30 = (TextView) repDialog.findViewById(R.id.field30);
//        TextView field31 = (TextView) repDialog.findViewById(R.id.field31);
//        TextView field32 = (TextView) repDialog.findViewById(R.id.field32);
//        TextView field33 = (TextView) repDialog.findViewById(R.id.field33);
//        TextView field34 = (TextView) repDialog.findViewById(R.id.field34);
//        field1.setText(Html.fromHtml(getCustomText(0)));
//        field2.setText(Html.fromHtml(getCustomText(1)));
//        field3.setText(Html.fromHtml(getCustomText(2)));
//        field4.setText(Html.fromHtml(getCustomText(3)));
//        field5.setText(Html.fromHtml(getCustomText(4)));
//        field6.setText(Html.fromHtml(getCustomText(5)));
//        field7.setText(Html.fromHtml(getCustomText(6)));
//        field8.setText(Html.fromHtml(getCustomText(7)));
//        field9.setText(Html.fromHtml(getCustomText(8)));
//        field10.setText(Html.fromHtml(getCustomText(9)));
//        field11.setText(Html.fromHtml(getCustomText(10)));
//        field12.setText(Html.fromHtml(getCustomText(11)));
//        field13.setText(Html.fromHtml(getCustomText(12)));
//        field14.setText(Html.fromHtml(getCustomText(13)));
//        field15.setText(Html.fromHtml(getCustomText(14)));
//        field16.setText(Html.fromHtml(getCustomText(15)));
//        field17.setText(Html.fromHtml(getCustomText(16)));
//        field18.setText(Html.fromHtml(getCustomText(17)));
//        field19.setText(Html.fromHtml(getCustomText(18)));
//        field20.setText(Html.fromHtml(getCustomText(19)));
//        field21.setText(Html.fromHtml(getCustomText(20)));
//        field22.setText(Html.fromHtml(getCustomText(21)));
//        field23.setText(Html.fromHtml(getCustomText(22)));
//        field24.setText(Html.fromHtml(getCustomText(23)));
//        field25.setText(Html.fromHtml(getCustomText(24)));
//        field26.setText(Html.fromHtml(getCustomText(25)));
//        field27.setText(Html.fromHtml(getCustomText(26)));
//        field28.setText(Html.fromHtml(getCustomText(27)));
//        field29.setText(Html.fromHtml(getCustomText(28)));
//        field30.setText(Html.fromHtml(getCustomText(29)));
//        field31.setText(Html.fromHtml(getCustomText(30)));
//        field32.setText(Html.fromHtml(getCustomText(31)));
//        field33.setText(Html.fromHtml(getCustomText(32)));
        //field34.setText(Html.fromHtml(getCustomText(33)));

//Html.fromHtml(
//        //close
        repDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repDialog.dismiss();
            }
        });

        repDialog.show();

    }

    public void mDevelopingMessage(String message, String title) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(R.drawable.info);


        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private String setOrderDiscEndPoint(String date)
    {
        SalRepDS salRepDS = new SalRepDS(getActivity());
        String transorddiscsum = connURLsvc + "/monOrdDisTot/mobile123/" + localSP.getString("Console_DB", "").toString()+"/"+ salRepDS.getCurrentRepCode()+ date;

        return transorddiscsum;
    }

    private String setOrderEndPoint(String date)
    {
        SalRepDS salRepDS = new SalRepDS(getActivity());
        String transordsum = connURLsvc + "/monOrdTot/mobile123/" + localSP.getString("Console_DB", "").toString()+"/"+ salRepDS.getCurrentRepCode()+ date;

        return transordsum;
    }

    private String setDayOrderTotEndPoint()
    {
        SalRepDS salRepDS = new SalRepDS(getActivity());
        String dayOrdTot = connURLsvc + "/dayOrdTot/mobile123/" + localSP.getString("Console_DB", "").toString()+"/"+ salRepDS.getCurrentRepCode();

        return dayOrdTot;
    }

    private String setDayDiscountTotEndPoint()
    {
        SalRepDS salRepDS = new SalRepDS(getActivity());
        String dayOrdDisTot = connURLsvc + "/dayOrdDisTot/mobile123/" + localSP.getString("Console_DB", "").toString()+"/"+ salRepDS.getCurrentRepCode();

        return dayOrdDisTot;
    }

    private String setDayReturnTotEndPoint()
    {
        SalRepDS salRepDS = new SalRepDS(getActivity());
        String dayRtnTot = connURLsvc + "/dayRtnTot/mobile123/" + localSP.getString("Console_DB", "").toString()+"/"+ salRepDS.getCurrentRepCode();

        return dayRtnTot;
    }

    private String setDayProductiveTotEndPoint()
    {
        SalRepDS salRepDS = new SalRepDS(getActivity());
        String dayProd = connURLsvc + "/dayProd/mobile123/" + localSP.getString("Console_DB", "").toString()+"/"+ salRepDS.getCurrentRepCode();

        return dayProd;
    }

    private String setDayNonProductiveTotEndPoint()
    {
        SalRepDS salRepDS = new SalRepDS(getActivity());
        String dayNonProd = connURLsvc + "/dayNonProd/mobile123/" + localSP.getString("Console_DB", "").toString()+"/"+ salRepDS.getCurrentRepCode();

        return dayNonProd;
    }

    private String setDayInvoiceTotEndPoint()
    {
        SalRepDS salRepDS = new SalRepDS(getActivity());
        String dayInvSale = connURLsvc + "/dayInvSale/mobile123/" + localSP.getString("Console_DB", "").toString()+"/"+ salRepDS.getCurrentRepCode();

        return dayInvSale;
    }

    private String setMonthReturnEndPoint(String date)
    {
        SalRepDS salRepDS = new SalRepDS(getActivity());
        String monRtnTot = connURLsvc + "/monRtnTot/mobile123/" + localSP.getString("Console_DB", "").toString()+"/"+ salRepDS.getCurrentRepCode()+ date;

        return monRtnTot;
    }

    private String setMonthProdEndPoint(String date)
    {
        SalRepDS salRepDS = new SalRepDS(getActivity());
        String monProd = connURLsvc + "/monProd/mobile123/" + localSP.getString("Console_DB", "").toString()+"/"+ salRepDS.getCurrentRepCode()+ date;

        return monProd;
    }

    private String setMonthNonProdEndPoint(String date)
    {
        SalRepDS salRepDS = new SalRepDS(getActivity());
        String monNonProd = connURLsvc + "/monNonProd/mobile123/" + localSP.getString("Console_DB", "").toString()+"/"+ salRepDS.getCurrentRepCode()+ date;

        return monNonProd;
    }
}
