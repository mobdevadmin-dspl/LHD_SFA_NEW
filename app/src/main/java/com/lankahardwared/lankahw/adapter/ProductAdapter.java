package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.Items;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Items> {
    Context context;
    ArrayList<Items> list;

    public ProductAdapter(Context context, ArrayList<Items> list) {

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
        TextView HoQ = (TextView) row.findViewById(R.id.TextView01);

        itemCode.setText(list.get(position).getFITEM_ITEM_CODE());
        ItemName.setText(list.get(position).getFITEM_ITEM_NAME());
        HoQ.setText(list.get(position).getFITEM_QOH());

        return row;
    }
}
