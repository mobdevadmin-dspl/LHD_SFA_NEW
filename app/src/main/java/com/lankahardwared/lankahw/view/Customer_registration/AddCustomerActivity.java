package com.lankahardwared.lankahw.view.Customer_registration;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.Customer_Adapter;
import com.lankahardwared.lankahw.adapter.DistrictAdapter;
import com.lankahardwared.lankahw.adapter.RouteAdapter;
import com.lankahardwared.lankahw.adapter.TownAdapter;
import com.lankahardwared.lankahw.control.GPSTracker;
import com.lankahardwared.lankahw.control.NetworkUtil;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.control.TaskType;
import com.lankahardwared.lankahw.data.CompanyBranchDS;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.NewCustomerDS;
import com.lankahardwared.lankahw.data.RouteDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.data.TownDS;
import com.lankahardwared.lankahw.data.fDistrictDS;
import com.lankahardwared.lankahw.model.NewCustomer;
import com.lankahardwared.lankahw.model.Route;
import com.lankahardwared.lankahw.model.Town;
import com.lankahardwared.lankahw.model.fDistrict;
import com.lankahardwared.lankahw.view.MainActivity;


import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class AddCustomerActivity extends AppCompatActivity implements AsyncTaskListener{
    public EditText customerCode,
            customerName, editTextCNic, OtherCode, businessRegno, district,
            town, route, addressline1, addressline2, city, mobile, phone, fax, emailaddress, editRemark;
    public ImageButton btn_Route, btn_District, btn_Town, CustomerbtnSearch;
    private ArrayList<fDistrict> fDistrictArrayList;
    private ArrayList<Town> townArrayList;
    private ArrayList<Route> routeArrayList;
    private ArrayList<NewCustomer> newCustomerArrayList;
    private String DisCode;
    private ImageView img, img2, img3, img4;
    private Switch mySwitch;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static int IMAGE1 = 0;
    private byte[] byteArray;
    Bitmap bitimage = null;
    private static String pictureName = null;
    private Uri filePath;
    ArrayList<Uri> uris = new ArrayList<>();
    private FloatingActionButton fab, fabDiscard, fabExit;
    SharedPref mSharedPref;
    private ArrayList<String> pictureDownloadurl;
    private ReferenceNum referenceNum;
    private String jsonstr;
    private ProgressDialog progressDialog;
    public static SharedPreferences localSP;
    public static final String SETTINGS = "SETTINGS";


    int CUSFLG = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finac_new_customer_registration);

        mSharedPref = new SharedPref(this);
        referenceNum = new ReferenceNum(this);
        localSP = this.getSharedPreferences(SETTINGS, 0);
        FirebaseApp.initializeApp(this);
        //checkFilePermission();
        customerCode = (EditText) findViewById(R.id.editTextCustomer_Code);
        customerName = (EditText) findViewById(R.id.editText2);
        editTextCNic = (EditText) findViewById(R.id.editTextCNic);
        businessRegno = (EditText)findViewById(R.id.editText3);
        addressline1 = (EditText) findViewById(R.id.editText7);
        addressline2 = (EditText) findViewById(R.id.editText8);
        city = (EditText)         findViewById(R.id.editText9);
        mobile = (EditText)       findViewById(R.id.editText10);
        phone = (EditText)        findViewById(R.id.editText11);
        fax = (EditText)          findViewById(R.id.editText12);
        emailaddress = (EditText) findViewById(R.id.editText20);
        route = (EditText)        findViewById(R.id.spinner4);
        district = (EditText)     findViewById(R.id.spinner5);
        town = (EditText)         findViewById(R.id.spinner3);
        editRemark = (EditText)   findViewById(R.id.editDetails);
        pictureDownloadurl = new ArrayList<>();



        btn_Town = (ImageButton)         findViewById(R.id.btn_T);
        btn_Route = (ImageButton)        findViewById(R.id.btn_R);
        btn_District = (ImageButton)     findViewById(R.id.btn_D);
        CustomerbtnSearch = (ImageButton)findViewById(R.id.btn_C6);

        img = (ImageView)           findViewById(R.id.imageView2);
        mySwitch = (Switch)         findViewById(R.id.switch1);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        //-----------------------------------------------------------------------------------------------------------------

        //show new customer ref no
        if (mySwitch.isChecked() == true) {
            customerName.requestFocus();
            customerCode.setText(referenceNum.getCurrentRefNo(getResources().getString(R.string.newCusVal)));
            CustomerbtnSearch.setEnabled(false);
        } else {
            CustomerbtnSearch.setEnabled(true);
        }

        btn_District.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodcutDetailsDialogbox(1);

            }
        });
        btn_Town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodcutDetailsDialogbox(2);
            }
        });
        btn_Route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodcutDetailsDialogbox(3);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMAGE1 = 1;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        //get old customers
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    referenceNum = new ReferenceNum(getApplicationContext());

                    CUSFLG = 1;
                    customerName.setFocusable(true);
                    customerName.setEnabled(true);
                    customerName.setClickable(true);
                    customerName.setFocusableInTouchMode(true);

                    DebtorDS TW = new DebtorDS(getApplicationContext());

                    customerName.setEnabled(true);
                    editTextCNic.setEnabled(true);
                    OtherCode.setEnabled(true);
                    businessRegno.setEnabled(true);
                    addressline1.setEnabled(true);
                    addressline2.setEnabled(true);
                    mobile.setEnabled(true);
                    phone.setEnabled(true);
                    fax.setEnabled(true);
                    emailaddress.setEnabled(true);
                    route.setEnabled(true);
                    district.setEnabled(true);
                    town.setEnabled(true);
                    editRemark.setEnabled(true);
                    city.setEnabled(true);
                    btn_Town.setEnabled(true);
                    btn_Route.setEnabled(true);
                    btn_District.setEnabled(true);

                    customerName.setText("");
                    editTextCNic.setText("");
                    //OtherCode.setText("");
                    editRemark.setText("");
                    businessRegno.setText("");
                    addressline1.setText("");
                    addressline2.setText("");
                    city.setText("");
                    mobile.setText("");
                    phone.setText("");
                    fax.setText("");
                    emailaddress.setText("");
                    route.setText("");
                    district.setText("");
                    town.setText("");


                } else {
                    //WHEN NEW CUSTOMER MODE OFF
                    customerName.setFocusable(false);
                    customerName.setEnabled(false);
                    customerName.setClickable(false);
                    customerName.setFocusableInTouchMode(false);

                    customerName.setEnabled(false);
                    editTextCNic.setEnabled(false);
                    OtherCode.setEnabled(false);
                    businessRegno.setEnabled(false);
                    addressline1.setEnabled(false);
                    addressline2.setEnabled(false);
                    mobile.setEnabled(false);
                    phone.setEnabled(false);
                    fax.setEnabled(false);
                    emailaddress.setEnabled(false);
                    route.setEnabled(false);
                    district.setEnabled(false);
                    town.setEnabled(false);
                    city.setEnabled(false);
                    btn_Town.setEnabled(false);
                    btn_Route.setEnabled(false);
                    btn_District.setEnabled(false);
                    editRemark.setEnabled(false);
                    OtherCode.setText("");
                    CUSFLG = 0;
                    customerCode.setText("");
                }
            }
        });

        //save new customer
        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (CUSFLG == 1) {

                    if(NetworkUtil.isNetworkAvailable(AddCustomerActivity.this)) {
                        uploadFile(pictureName);
                    }else{
                        Toast.makeText(AddCustomerActivity.this,"Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                    }

                    // if (customerName.getText().length() != 0 && addressline1.getText().length() != 0 && addressline2.getText().length() != 0 && mobile.getText().length() != 0 && town.getText().length() != 0 && route.getText().length() != 0 && city.getText().length() != 0) {
                    if (customerName.getText().length() != 0 && addressline1.getText().length() != 0 && addressline2.getText().length() != 0 && mobile.getText().length() != 0) {

                        if (isEmailValid(emailaddress.getText().toString()) == false) {
                            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                        } else if (pictureDownloadurl.size() == 0) {

                            Toast.makeText(getApplicationContext(), "Please add business image", Toast.LENGTH_SHORT).show();
                        } else {
                            //submit form


                            TownDS TW = new TownDS(getApplicationContext());
                            RouteDS RO = new RouteDS(getApplicationContext());

                            DateFormat Dformat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();

                            SalRepDS fSalRepDS = new SalRepDS(getApplicationContext());
                            CompanyBranchDS branchDS = new CompanyBranchDS(getApplicationContext());

                            GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                            referenceNum = new ReferenceNum(getApplicationContext());

                            NewCustomer customer = new NewCustomer();
                            customer.setC_REGNUM(businessRegno.getText().toString());
                            customer.setNAME(customerName.getText().toString());
                            customer.setCUSTOMER_NIC(editTextCNic.getText().toString());
                            customer.setCUSTOMER_ID(customerCode.getText().toString());
                            customer.setC_TOWN(TW.getCode(town.getText().toString()));
                            customer.setROUTE_ID(RO.getRouteCodeByRoute(route.getText().toString()));
                            customer.setADDRESS1(addressline1.getText().toString());
                            customer.setADDRESS2(addressline2.getText().toString());
                            customer.setCITY(city.getText().toString());
                            customer.setMOBILE(mobile.getText().toString());
                            customer.setPHONE(phone.getText().toString());
                            customer.setFAX(fax.getText().toString());
                            customer.setE_MAIL(emailaddress.getText().toString());
                            customer.setC_IMAGE(pictureDownloadurl.get(0).toString());
                            customer.setC_SYNCSTATE("0");
                            customer.setAddMac("NA");
                            customer.setC_ADDDATE(Dformat.format(date));
                            customer.setC_LATITUDE("" + gpsTracker.getLatitude());
                            customer.setC_LONGITUDE("" + gpsTracker.getLongitude());
                            customer.setnNumVal(new ReferenceNum(AddCustomerActivity.this).getCurrentRefNo(getResources().getString(R.string.newCusVal)));
                            customer.setTxnDate(Dformat.format(date));
                            customer.setCONSOLE_DB(localSP.getString("Console_DB", "").toString());


                            ArrayList<NewCustomer> cusList = new ArrayList<>();
                            cusList.add(customer);


                            NewCustomerDS customerDS = new NewCustomerDS(getApplicationContext());
                            int result = customerDS.createOrUpdateCustomer(cusList);

                            if (result > 0) {

                                Toast.makeText(getApplicationContext(), "New Customer saved", Toast.LENGTH_SHORT).show();
                                ClearFiled();

                                //insert current NC number for next num generation
                                referenceNum = new ReferenceNum(AddCustomerActivity.this);
                                referenceNum.nNumValueInsertOrUpdate(getResources().getString(R.string.newCusVal));

                                    if (NetworkUtil.isNetworkAvailable(AddCustomerActivity.this)) {

                                        new UploadNewCustomer(AddCustomerActivity.this, AddCustomerActivity.this, TaskType.UPLOADNEWCUSTOMER, cusList).execute();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                                    }
//
                            }

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Please fill all compulsory fields", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });



        CustomerbtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodcutDetailsDialogbox(4);
            }
        });
        //get old customer for update record
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    customerName.requestFocus();
                    customerCode.setText(referenceNum.getCurrentRefNo(getResources().getString(R.string.newCusVal)));
                    CustomerbtnSearch.setEnabled(false);

                    editTextCNic.setEnabled(true);
                    customerCode.setEnabled(true);
                    customerName.setEnabled(true);
                    businessRegno.setEnabled(true);
                    btn_District.setEnabled(true);
                    btn_Town.setEnabled(true);
                    btn_Route.setEnabled(true);
                    addressline1.setEnabled(true);
                    addressline2.setEnabled(true);
                    city.setEnabled(true);
                    mobile.setEnabled(true);
                    phone.setEnabled(true);
                    fax.setEnabled(true);
                    emailaddress.setEnabled(true);
                    img.setEnabled(true);
                    editRemark.setEnabled(true);
                } else {
                    CustomerbtnSearch.setEnabled(true);

                    customerCode.setText("");
                    customerCode.setEnabled(false);
                    customerName.setEnabled(false);
                    editTextCNic.setEnabled(false);
                    businessRegno.setEnabled(false);
                    btn_District.setEnabled(false);
                    btn_Town.setEnabled(false);
                    btn_Route.setEnabled(false);
                    addressline1.setEnabled(false);
                    addressline2.setEnabled(false);
                    city.setEnabled(false);
                    mobile.setEnabled(false);
                    phone.setEnabled(false);
                    fax.setEnabled(false);
                    emailaddress.setEnabled(false);
                    img.setEnabled(false);
                    editRemark.setEnabled(false);
                }
            }
        });


    }



@RequiresApi(api = Build.VERSION_CODES.M)
//private void checkFilePermission(){
//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
//            int permissionCheck = AddCustomerActivity.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
//            permissionCheck += AddCustomerActivity.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
//        if(permissionCheck != 0){
//            this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},permissionCheck);
//        }
//
//        }
//}
    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    //---------------------------------Get Details from db---------------------------------------
    public void prodcutDetailsDialogbox(final int Flag) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.details_search_item);
        dialog.setCancelable(true);

        final SearchView search = (SearchView) dialog.findViewById(R.id.et_search);
        final ListView Detlist = (ListView) dialog.findViewById(R.id.lv_product_list);

        final fDistrictDS ds = new fDistrictDS(this);
        final TownDS townDS = new TownDS(this);
        final RouteDS routeDS = new RouteDS(this);
        final NewCustomerDS newCustomerDS = new NewCustomerDS(this);

        Detlist.clearTextFilter();
        if (Flag == 1) {
            fDistrictArrayList = ds.getDistrict("");
            Detlist.setAdapter(new DistrictAdapter(this, fDistrictArrayList));
            town.setText("");
            route.setText("");
        } else if (Flag == 2) {
            townArrayList = townDS.getTown(DisCode);
            Detlist.setAdapter(new TownAdapter(this, townArrayList));
            route.setText("");
        } else if (Flag == 3) {
            routeArrayList = routeDS.getRoute();
            Detlist.setAdapter(new RouteAdapter(this, routeArrayList));

        } else if (Flag == 4) {
            newCustomerArrayList = newCustomerDS.getAllNewCusDetailsForEdit("");
            Detlist.setAdapter(new Customer_Adapter(this, newCustomerArrayList));

        }
//================================================
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (Flag == 1) {
                        fDistrictArrayList = ds.getDistrict(newText);
                        Detlist.setAdapter(new DistrictAdapter(getApplicationContext(), fDistrictArrayList));
                    } else if (Flag == 2) {
                        //search from arraylist not from DB
                        ArrayList<Town> townS_result = new ArrayList<Town>();
                        townS_result.clear();
                        for (Town town : townArrayList) {

                            String tName = town.getFTOWN_NAME();
                            if (tName.startsWith(newText)) {

                                townS_result.add(town);
                            }
                        }
                        Detlist.setAdapter(new TownAdapter(getApplicationContext(), townS_result));

                    } else if (Flag == 3) {
                        routeArrayList = routeDS.getRoute();
                        Detlist.setAdapter(new RouteAdapter(getApplicationContext(), routeArrayList));
                    } else if (Flag == 4) {
                        newCustomerArrayList = newCustomerDS.getAllNewCusDetailsForEdit("");
                        Detlist.setAdapter(new Customer_Adapter(getApplicationContext(), newCustomerArrayList));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }
        });
        //-=========================================
        Detlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (Flag == 1) {
                    district.setText(fDistrictArrayList.get(position).getDISTRICT_NAME());
                    DisCode = fDistrictArrayList.get(position).getDISTRICT_CODE();
                } else if (Flag == 2) {
                    town.setText(townArrayList.get(position).getFTOWN_NAME());
                } else if (Flag == 3) {
                    route.setText(routeArrayList.get(position).getFROUTE_ROUTECODE());

                } else if (Flag == 4) {
                    editTextCNic.setEnabled(true);
                    customerName.setEnabled(true);
                    businessRegno.setEnabled(true);
                    addressline1.setEnabled(true);
                    addressline2.setEnabled(true);
                    city.setEnabled(true);
                    mobile.setEnabled(true);
                    phone.setEnabled(true);
                    fax.setEnabled(true);
                    emailaddress.setEnabled(true);
                    img.setEnabled(true);
                    btn_District.setEnabled(true);
                    btn_Town.setEnabled(true);
                    btn_Route.setEnabled(true);
                    editRemark.setEnabled(true);


                    customerCode.setText(newCustomerArrayList.get(position).getCUSTOMER_ID());
                    customerName.setText(newCustomerArrayList.get(position).getNAME());
//                    OtherCode.setText(newCustomerArrayList.get(position).getC_OTHERCODE());
                    businessRegno.setText(newCustomerArrayList.get(position).getC_REGNUM());
                    editTextCNic.setText(newCustomerArrayList.get(position).getCUSTOMER_NIC());
                    addressline1.setText(newCustomerArrayList.get(position).getADDRESS1());
                    addressline2.setText(newCustomerArrayList.get(position).getADDRESS2());
                   // addressline2.setText(newCustomerArrayList.get(position).getREMARK());

                    fDistrictDS districtDS = new fDistrictDS(getApplicationContext());
                    district.setText(districtDS.getName(newCustomerArrayList.get(position).getDISTRICT()));

                   // RouteDS routeDS1 = new RouteDS(getActivity());
                    route.setText(newCustomerArrayList.get(position).getROUTE_ID());

                    TownDS townDS1 = new TownDS(getApplicationContext());
                    route.setText(townDS1.getCode(newCustomerArrayList.get(position).getC_TOWN()));
                    if (newCustomerArrayList.get(position).getCITY().isEmpty()) {
                        city.setText("N/A");
                    } else {
                        city.setText(newCustomerArrayList.get(position).getCITY());
                    }

                    if (newCustomerArrayList.get(position).getMOBILE().isEmpty()) {
                        mobile.setText("-");
                    } else {
                        mobile.setText(newCustomerArrayList.get(position).getMOBILE());
                    }

                    if (newCustomerArrayList.get(position).getPHONE().isEmpty()) {
                        phone.setText("N/A");
                    } else {
                        phone.setText(newCustomerArrayList.get(position).getPHONE());
                    }

                    if (newCustomerArrayList.get(position).getFAX().isEmpty()) {
                        fax.setText("N/A");
                    } else {
                        fax.setText(newCustomerArrayList.get(position).getFAX());
                    }

                    if (newCustomerArrayList.get(position).getE_MAIL().isEmpty()) {
                        emailaddress.setText("N/A");
                    } else {
                        emailaddress.setText(newCustomerArrayList.get(position).getE_MAIL());
                    }

                }


                dialog.dismiss();
            }
        });


        dialog.show();
    }

    //--------------------------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

                    Bitmap bmp = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    try {
                        if (IMAGE1 == 1) {
                            img.setImageBitmap(bitmap);
                            bitimage = bitmap;

                            pictureName = "img";

                        //    IMAGE1 = 0;

                            filePath = data.getData();
                            uris.add(filePath);
                            Log.d("<>1>>>>", "" + filePath);

                        }
                        System.out.println("uris ; " + uris.size() + "-" + pictureName);
                        Log.v("ACTICITY RESULT: ","REQUESTCODE "+requestCode+" REQUEST CODE "+resultCode+" INTENT "+data.toString());

                       // uploadFile(filePath,pictureName);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    // if(resultCode!=1)


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void uploadFile(String imgName) {
        //if there is a file to upload

        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String imagepathName = sdf.format(new Date());
        imagepathName = imgName + imagepathName;

        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            FirebaseApp.initializeApp(this);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            //FirebaseStorage storage = mAuth.getCurrentUser().
            StorageReference mStorageReference = storage.getReferenceFromUrl("gs://lhdsfa-e3b75.appspot.com/").child("images/" + imagepathName);
            mStorageReference.putFile(filePath)
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();
                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            System.out.println("downloadUri" + downloadUri.toString());

                            pictureDownloadurl.add(downloadUri.toString()); // add uploaded image url to list

                            //and displaying a success toast
                            // Toast.makeText(getActivity(), "Picture Uploaded ", Toast.LENGTH_SHORT).show();
                        }

                    })


                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            //Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


        }


        //if there is not any file
        else {
            //you can display an error toast
        }

    }


    public void ClearFiled() {
        img.setImageResource(0);
        img.setBackgroundResource(R.drawable.ic_image2);
        customerName.setText("");
        editTextCNic.setText("");
        businessRegno.setText("");
        district.setText("");
        town.setText("");
        route.setText("");
        addressline1.setText("");
        addressline2.setText("");
        city.setText("");
        mobile.setText("");
        phone.setText("");
        fax.setText("");
        emailaddress.setText("");
        editRemark.setText("");

    }


    private void exitData() {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing())
           progressDialog.dismiss();

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("OnResume", "oo");

    }

    @Override
    public void onTaskCompleted(TaskType taskType) {
        Toast.makeText(this, "New Customer Uploaded Successfuly", Toast.LENGTH_SHORT).show();
        //UtilityContainer.mLoadFragment(new CustomerRegMain(),AddCustomerActivity.this);
        Intent in = new Intent(AddCustomerActivity.this, MainActivity.class);
        startActivity(in);
        finish();

    }


}
