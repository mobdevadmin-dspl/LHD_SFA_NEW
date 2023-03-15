package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.NewCustomer;

import java.util.ArrayList;

/**
 * Created by Dhanushika on 4/5/2018.
 */

public class NewCustomerAdapter  extends ArrayAdapter<NewCustomer> {
    Context context;
    ArrayList<NewCustomer> list;


    public NewCustomerAdapter(Context context, ArrayList<NewCustomer> list) {
        super(context, R.layout.row_cus_view, list);
        this.context = context;
        this.list = list;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position,  View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        row = inflater.inflate(R.layout.row_cus_view, parent, false);

        TextView Itemname = (TextView) row.findViewById(R.id.row_refno);
        TextView Itemcode = (TextView) row.findViewById(R.id.row_cus_name);
        TextView syncStatus=(TextView) row.findViewById(R.id.addeddate);




        Itemname.setText(list.get(position).getCUSTOMER_ID());
        Itemcode.setText(list.get(position).getNAME());
        syncStatus.setText("");

        return row;
    }
}
