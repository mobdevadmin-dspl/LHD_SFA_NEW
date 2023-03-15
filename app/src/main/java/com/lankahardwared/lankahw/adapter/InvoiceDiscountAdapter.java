package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.ItemsDS;
import com.lankahardwared.lankahw.model.InvDet;

import java.util.ArrayList;

public class InvoiceDiscountAdapter extends ArrayAdapter<InvDet> {
    Context context;
    ArrayList<InvDet> list;

    public InvoiceDiscountAdapter(Context context, ArrayList<InvDet> list) {

        super(context, R.layout.row_order_details, list);
        this.context = context;
        this.list = list;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_order_details, parent, false);

        TextView item = (TextView) row.findViewById(R.id.row_item);
        TextView cases = (TextView) row.findViewById(R.id.row_cases);
        TextView piece = (TextView) row.findViewById(R.id.row_piece);

        ItemsDS ds = new ItemsDS(getContext());
        item.setText(ds.getItemNameByCode(list.get(position).getFINVDET_ITEM_CODE()));

        cases.setText(list.get(position).getFINVDET_DIS_AMT());
        piece.setText(list.get(position).getFINVDET_AMT());

        return row;
    }
}
