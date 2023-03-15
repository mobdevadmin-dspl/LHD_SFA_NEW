package com.lankahardwared.lankahw.view.stock_inquiry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.StockInquiryAdaptor;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.ControlDS;
import com.lankahardwared.lankahw.data.ItemsDS;
import com.lankahardwared.lankahw.data.TourHedDS;
import com.lankahardwared.lankahw.model.Control;
import com.lankahardwared.lankahw.model.StockInfo;
import com.lankahardwared.lankahw.model.TourHed;
import com.lankahardwared.lankahw.view.IconPallet;
import com.lankahardwared.lankahw.view.MainActivity;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class StockInquiry extends Fragment {
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    View view;
    ListView lvStockData;
    String LocCode = "";
    ArrayList<StockInfo> arrayList;
    Spinner spnTrans, spnTourcode;
    TextView txtTotQty;
    String PRINTER_MAC_ID;
    BluetoothAdapter mBTAdapter;
    BluetoothSocket mBTSocket;
    Dialog dialogProgress;
    String printString = "";
    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            try {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device.getAddress().equalsIgnoreCase(PRINTER_MAC_ID)) {
                        mBTAdapter.cancelDiscovery();
                        dialogProgress.dismiss();
                        printBillToDevice(PRINTER_MAC_ID, printString);
                    }
                }
            } catch (Exception e) {
                Log.e("Class  ", "fire 1 ", e);

            }
        }
    };
    ArrayList<TourHed> tourList;
    SearchView searchView;

	/*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        } else if (!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        return true;
    }

	/*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_mangement_stock_inquiry, container, false);
        lvStockData = (ListView) view.findViewById(R.id.listviewStockData);
//        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        PRINTER_MAC_ID = localSP.getString("printer_mac_address", "").toString();

        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle("Stock Inquiry");
        toolbar.setLogo(R.drawable.dm_logo_64);
        setHasOptionsMenu(true);
        spnTrans = (Spinner) view.findViewById(R.id.spnTrans);
        spnTourcode = (Spinner) view.findViewById(R.id.spnTourCode);
        txtTotQty = (TextView) view.findViewById(R.id.txtTotQty);
        searchView = (SearchView) view.findViewById(R.id.stock_search);


        ArrayList<String> strList = new ArrayList<String>();
        strList.add("Transaction ..");
        strList.add("Pre sales");
        strList.add("Van sales");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, strList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTrans.setAdapter(dataAdapter1);

		/*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        spnTrans.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    tourList = new TourHedDS(getActivity()).getTourDetails(position == 1 ? "S" : "V");
                    loadTourCodes(tourList);
                } else {
                    lvStockData.setAdapter(null);
                    spnTourcode.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

		/*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        spnTourcode.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {

                    if (!LocCode.equals(tourList.get(position - 1).getTOURHED_LOCCODE())) {
                        txtTotQty.setText(new ItemsDS(getActivity()).getTotalStockQOH(tourList.get(position - 1).getTOURHED_LOCCODE()));
                        new TaskRunner().execute();
                    }
                    LocCode = tourList.get(position - 1).getTOURHED_LOCCODE();

                } else {
                    lvStockData.setAdapter(null);
                    LocCode = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchView.getWidth() > 0)
                    new TaskRunner().execute(newText);
                return false;
            }
        });


        return view;
    }

	/*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listviewStockData) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

	/*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void doProgress(String s, int timeout) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(s);
        progressDialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, timeout);

    }

	/*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }

        inflater.inflate(R.menu.stock_inquiry_menu, menu);

//        final SearchView searchView = (SearchView) menu.findItem(R.id.stock_inquiry_search).getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (searchView.getWidth() > 0)
//                    new TaskRunner().execute(newText);
//                return false;
//            }
//        });

        super.onCreateOptionsMenu(menu, inflater);
    }

	/*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.stock_inquiry_print) {
            setBluetooth(true);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Please confirm printing ?");
            alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                    PrintStock();
                    doProgress("Stock details being printed..!", 3000);
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    setBluetooth(false);
                    dialog.cancel();
                }
            });

            AlertDialog alertD = alertDialogBuilder.create();
            alertD.show();
        } else if (item.getItemId() == R.id.stock_inquiry_close) {

            UtilityContainer.mLoadFragment(new IconPallet(), getActivity());

        }

        return super.onOptionsItemSelected(item);
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**/

    public void PrintStock() {

        ArrayList<Control> controlList;
        printString = "";
        int i = 0;
        String space;
        final int LINECHAR = 44;

        controlList = new ControlDS(getActivity()).getAllControl();

        int lengthDealABCom = (LINECHAR - controlList.get(0).getFCONTROL_COM_NAME().length()) / 2;
        space = String.format("%" + lengthDealABCom + "s", " ");
        String aa = space + controlList.get(0).getFCONTROL_COM_NAME() + "\r\n";

        int lengthDealBBCom = (LINECHAR - controlList.get(0).getFCONTROL_COM_ADD1().length()) / 2;
        space = String.format("%" + lengthDealBBCom + "s", " ");
        String bb = space + controlList.get(0).getFCONTROL_COM_ADD1() + "\r\n";

        String addressCCom = controlList.get(0).getFCONTROL_COM_ADD2().trim() + ", " + controlList.get(0).getFCONTROL_COM_ADD3().trim() + ".";
        int lengthDealCBCom = (LINECHAR - addressCCom.length()) / 2;
        space = String.format("%" + lengthDealCBCom + "s", " ");
        String cc = space + addressCCom + "\r\n";

        String TelCom = "Tel:" + controlList.get(0).getFCONTROL_COM_TEL1().trim();
        int lengthDealDBCom = (LINECHAR - TelCom.length()) / 2;
        space = String.format("%" + lengthDealDBCom + "s", " ");
        String dd = space + TelCom + "\r\n";

        String line = "--------------------------------------------" + "\r\n";
        // String line = String.format("%48s", "-") + "\r\n";

        String compAdd = aa + bb + cc + dd;

        // |<------44------------------------>|
        // ITEMCODE <-14-> ITEMNAME <-11-> QOH

        String sGroup = "GROUP CODE: " + "" + " - " + new ItemsDS(getActivity()).getGroupByCode("");
        String sSaleType = "SALE MODE: " + spnTourcode.getSelectedItem().toString();

        printString += compAdd;
        printString += "\r\n" + sGroup + "\r\n" + sSaleType + "\r\n";
        printString += line + "ITEMCODE" + String.format("%14s", " ") + "ITEMNAME" + String.format("%11s", " ") + "QOH" + "\r\n" + "(SIZE x QTY)" + "\r\n";
        printString += line;

        for (StockInfo item : arrayList) {

            i++;
            int len = (i + "] ").length() + item.getStock_Itemcode().length() + item.getStock_Itemname().length();
            String space1 = String.format("%" + (30 - len) + "s", " ");
            String qoh = String.valueOf((int) Double.parseDouble(item.getStock_Qoh()));
            String Space2 = String.format("%" + (14 - qoh.length()) + "s", " ");
            printString += i + "] " + item.getStock_Itemcode() + space1 + item.getStock_Itemname() + Space2 + qoh + "\r\n";
            String sizeString = item.getStock_Sizes();

            if (sizeString.length() > 0)
                printString += sizeString + "\r\n\r\n";

        }

        space = String.format("%" + ((LINECHAR - "Developed by Datamation Systems.".length()) / 2) + "s", " ");
        printString += line + space + "Developed by Datamation Systems.";

        Log.v("**", printString);

        DetectPrinter();

    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void DetectPrinter() {

        mBTAdapter = BluetoothAdapter.getDefaultAdapter();

        try {
            if (mBTAdapter.isDiscovering())
                mBTAdapter.cancelDiscovery();
            else
                mBTAdapter.startDiscovery();
        } catch (Exception e) {
            Log.e("Class ", "fire 4", e);
        }
        System.out.println("BT Searching status :" + mBTAdapter.isDiscovering());

        if (mBTAdapter == null) {
            Toast.makeText(getActivity(), "Device has no bluetooth capability...", Toast.LENGTH_SHORT).show();
        } else {
            if (!mBTAdapter.isEnabled()) {
            }
            printBillToDevice(PRINTER_MAC_ID, printString);
        }

    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void printBillToDevice(final String address, String BILL) {

        mBTAdapter.cancelDiscovery();
        try {
            BluetoothDevice mdevice = mBTAdapter.getRemoteDevice(address);
            Method m = mdevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
            mBTSocket = (BluetoothSocket) m.invoke(mdevice, 1);

            mBTSocket.connect();
            OutputStream os = mBTSocket.getOutputStream();
            os.flush();
            os.write(BILL.getBytes());
            System.out.println(BILL);

            if (mBTAdapter != null)
                mBTAdapter.cancelDiscovery();

        } catch (Exception e) {
            Log.e("Class ", "fire 2 ", e);
            Toast.makeText(getActivity(), "Printer Device Disable Or Invalid MAC.Please Enable the Printer or MAC Address.", Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }

    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void loadTourCodes(ArrayList<TourHed> list) {

        ArrayList<String> strList = new ArrayList<String>();
        strList.add("Tours ..");

        for (TourHed hed : list) {
            strList.add(hed.getTOURHED_REFNO() + " - " + hed.getTOURHED_ID());
        }

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, strList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTourcode.setAdapter(dataAdapter2);


    }

    class TaskRunner extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        StockInquiryAdaptor adaptor;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait while loading ...");
            progressDialog.show();
            lvStockData.clearTextFilter();
            lvStockData.setAdapter(null);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            if (params.length > 0)
                arrayList = new ItemsDS(getActivity()).getStocks(params[0], LocCode);
            else
                arrayList = new ItemsDS(getActivity()).getStocks("", LocCode);

            adaptor = new StockInquiryAdaptor(getActivity(), arrayList);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            lvStockData.setAdapter(adaptor);
            progressDialog.dismiss();
            super.onPostExecute(result);
        }
    }


}
