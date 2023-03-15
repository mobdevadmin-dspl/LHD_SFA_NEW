package com.lankahardwared.lankahw.adapter;

import android.content.Context;
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
public class Customer_Adapter extends ArrayAdapter<NewCustomer> {

    Context context;
    ArrayList<NewCustomer> list;

    public Customer_Adapter(Context context, ArrayList<NewCustomer> list){

        super(context, R.layout.row_item_listview, list);
        this.context = context;
        this.list = list;

    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_item_listview, parent, false);

        TextView itemCode = (TextView) row.findViewById(R.id.tv_item_code);
        TextView ItemName = (TextView) row.findViewById(R.id.tv_item_name);
        TextView Description=(TextView) row.findViewById(R.id.TextView01);

        Description.setText(list.get(position).getNAME());
        ItemName.setText(list.get(position).getC_TOWN());

        itemCode.setText(list.get(position).getCUSTOMER_ID());


        return row;
    }
}
