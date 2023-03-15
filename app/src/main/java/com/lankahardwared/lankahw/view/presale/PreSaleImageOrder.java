package com.lankahardwared.lankahw.view.presale;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.Image_serach_Adapter;
import com.lankahardwared.lankahw.adapter.NewPreProductAdapter;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.control.SharedPref;
import com.lankahardwared.lankahw.data.ItemsDS;
import com.lankahardwared.lankahw.data.PreProductDS;
import com.lankahardwared.lankahw.data.SalRepDS;
import com.lankahardwared.lankahw.model.Items;
import com.lankahardwared.lankahw.model.PreProduct;
import com.lankahardwared.lankahw.model.TranSODet;
import com.lankahardwared.lankahw.model.TranSOHed;
import com.lankahardwared.lankahw.view.MainActivity;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Pubudu on 10/31/2017.
 */

public class PreSaleImageOrder extends Fragment {
    private static final String TAG = "PreSaleImageOrder";
    View view;
    public String RefNo = "";
    String locCode;
    ArrayList<Items> list = null;
    private GridView grd;
    private SweetAlertDialog pDialog;
    SearchView search;
    ImageView images;
    float x1, x2, y1, y2;
    int position = 0;
    final float GESTURE_OFFSET = 50;
    ImageSwitcher SW;
    TextView txt_styleNo;
    int seqno = 0;
    private SharedPref mSharedPref;
    private MyReceiver r;
    MainActivity activity;
    private  ArrayList<PreProduct> PreproductList = null, selectedItemList = null;
    private ListView lvProducts;
    private TranSOHed tmpsoHed=null;




    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_pre_sales_image, container, false);
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.NumVal));
        locCode = new SharedPref(getActivity()).getGlobalVal("preKeyLocCode");
        grd = (GridView) view.findViewById(R.id.gridView1);
        search = (SearchView) view.findViewById(R.id.et_search);
        mSharedPref = new SharedPref(getActivity());
        activity = (MainActivity) getActivity();
       tmpsoHed=new TranSOHed();

       /* new PrefetchData().execute();*/

        return view;

    }


    private class FetchData extends  AsyncTask<String,Integer,ArrayList<PreProduct>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Fetch Data Please Wait.");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected ArrayList<PreProduct> doInBackground(String... strings) {
            if (new PreProductDS(getActivity()).tableHasRecords()) {
               PreproductList = new PreProductDS(getActivity()).getAllItems("");
            } else {
                PreproductList =new ItemsDS(getActivity()).getAllItemForPreSales("","txntype ='21'",RefNo, new SalRepDS(getActivity()).getCurrentLocCode(),activity.selectedDebtor.getFDEBTOR_PRILLCODE());
               new PreProductDS(getActivity()).insertOrUpdateProducts(PreproductList);

               //---------re Order Temp product  list added for  fProducts_pre table-----------------added by dhanushika-------------------------------
                if(tmpsoHed!=null) {
                 ArrayList<TranSODet> detArrayList = tmpsoHed.getSoDetArrayList();

                 for (int i = 0; i < detArrayList.size(); i++) {
                     String tmpItemName = detArrayList.get(i).getFTRANSODET_ITEMCODE();
                     String tmpQty = detArrayList.get(i).getFTRANSODET_QTY();
                                //Update Qty in  fProducts_pre table
                     int count = new PreProductDS(getActivity()).updateProductQtyFor(tmpItemName, tmpQty);
                     if (count > 0) {
                         Log.d("InsertOrUpdate", "success");
                     } else {
                         Log.d("InsertOrUpdate", "Failed");
                     }

                 }
             }
             //----------------------------------------------------------------------------

            }
            return PreproductList;
        }

        @Override
        protected void onPostExecute(ArrayList<PreProduct> preProducts) {
            super.onPostExecute(preProducts);
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            prodcutDetailsDialogbox();
        }
    }
    //--------------------------------------------set items For grd view---------------------------------------------------
    public void prodcutDetailsDialogbox() {
        grd.clearTextFilter();
        grd.setAdapter(new Image_serach_Adapter(getActivity(), PreproductList));
        grd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductDataDialog(position,PreproductList);
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                PreproductList.clear();
                PreproductList =new ItemsDS(getActivity()).getAllItemForPreSales(newText,"txntype ='21'",RefNo, new SalRepDS(getActivity()).getCurrentLocCode(),activity.selectedDebtor.getFDEBTOR_PRILLCODE());
                grd.clearTextFilter();
                grd.setAdapter(new Image_serach_Adapter(getActivity(), PreproductList));

                return true;
            }
        });
    }
//--------------------------------------------------show Product Image  - Item code vise--------------------------------------------
    private void ProductDataDialog(int Posi, final ArrayList<PreProduct> result) {

        final Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.image_details_so_dialogbox);
        position = Posi;
        SW = (ImageSwitcher)dialog.findViewById(R.id.imageSwitcher1);
        images = (ImageView)dialog.findViewById(R.id.imageView1);
        txt_styleNo = (TextView)dialog.findViewById(R.id.txt_styleNo);
        lvProducts = (ListView) dialog.findViewById(R.id.lv_product_SO_list);
        seqno = 0;

        //----------------------------------------------------show Produt Sub details----------------------------------------------
        new Prefetchdetails().execute(result.get(position).getPREPRODUCT_ITEMCODE().trim());
        setData(result.get(position).getPREPRODUCT_ITEMCODE().trim());
        //------------------------------------------------------------------------------------------------------------------------
        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
     images.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        return true;
    }
      });
        images.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {


                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        break;

                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        v.performClick();

                        if (x1 < x2)
                        {
                            if ((x2 - x1) > GESTURE_OFFSET) {
                                if (position > 0) {
                                    SW.showPrevious();
                                    position = position - 1;

                                    setData(result.get(position).getPREPRODUCT_ITEMCODE().trim());
                                    new Prefetchdetails().execute(result.get(position).getPREPRODUCT_ITEMCODE().trim());

                                } else
                                    Toast.makeText(getActivity(), "First image", Toast.LENGTH_LONG).show();
                            }

                        } else if (x1 > x2)
                        {
                            if ((x1 - x2) > GESTURE_OFFSET) {

                                if (position < (result.size() - 1)) {
                                    position =  position+1;

                                    setData(result.get(position).getPREPRODUCT_ITEMCODE().trim());
                                    new Prefetchdetails().execute(result.get(position).getPREPRODUCT_ITEMCODE().trim());
                                    SW.showNext();
                                } else
                                    Toast.makeText(getActivity(), "Last image", Toast.LENGTH_LONG).show();
                            }
                        } else if (y1 < y2)
                        {
                            if ((y2 - y1) > GESTURE_OFFSET)
                                Toast.makeText(getActivity(), "UP to Down Swap Performed", Toast.LENGTH_LONG).show();

                        } else if (y1 > y2)
                        {
                            if ((y1 - y2) > GESTURE_OFFSET)
                                Toast.makeText(getActivity(), "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                        }
                        break;
                }

                return false;
            }
        });

        dialog.show();
    }
    //--------------------------------------------------------------show Produt Sub details--------------------------------------------------------------
     private class Prefetchdetails extends AsyncTask<String, Integer, ArrayList<PreProduct>> {
        protected void onPreExecute() {
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Fetch Data Please Wait.");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected ArrayList<PreProduct> doInBackground(String... Params) {
            if (new PreProductDS(getActivity()).tableHasRecords()) {
                selectedItemList = new PreProductDS(getActivity()).getItemsCodeVise("",Params[0].trim());
            }

            return selectedItemList;
        }

        protected void onPostExecute(ArrayList<PreProduct> result) {
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }

          /*  for (PreProduct det:result)
            {*/
                lvProducts.clearTextFilter();
                lvProducts.setAdapter(new NewPreProductAdapter(getActivity(), result));

          //  }


        }
    }
//----------------------------------------------set Image------------------------------------------------------------------------------------
    public void setData(String p) {
        String sPath = Environment.getExternalStorageDirectory() + "/FINAC_IMAGES/" + p + ".jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(sPath);
        images.setImageBitmap(bitmap);
        txt_styleNo.setText(p);
    }

     /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

   	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();
        r = new PreSaleImageOrder.MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_ImG_DETAILS"));
    }



		/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            PreSaleImageOrder.this.mToggleTextbox();
        }
    }
    //---------------------------------------------------------------------------------------------------------------------------
    public void mToggleTextbox() {

        if (mSharedPref.getGlobalVal("PrekeyCustomer").equals("Y")) {
            new FetchData().execute();

            Bundle mBundle = getArguments();
            if (mBundle != null) {
                tmpsoHed = (TranSOHed) mBundle.getSerializable("order");
            }

        } else {

        }
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------





}
