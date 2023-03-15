package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.FinacFont;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.fDaynPrdDetDS;
import com.lankahardwared.lankahw.model.FDaynPrdHed;

import java.util.ArrayList;

public class NonPrdHeader extends ArrayAdapter<FDaynPrdHed> {
    Context context;
    ArrayList<FDaynPrdHed> list;

    public NonPrdHeader(Context context, ArrayList<FDaynPrdHed> list) {
        super(context, R.layout.row_non_productive_header, list);
        this.context = context;
        this.list = list;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_non_productive_header, parent, false);

        TextView Itemname = (TextView) row.findViewById(R.id.row_nonprdhed_refno);
        TextView Itemcode = (TextView) row.findViewById(R.id.date);
        FinacFont customer = (FinacFont)row.findViewById(R.id.row_nonprdhed_date);


        fDaynPrdDetDS ds = new fDaynPrdDetDS(getContext());
        DebtorDS deb = new DebtorDS(getContext());

        Itemname.setText(list.get(position).getNONPRDHED_REFNO());
        Itemcode.setText(list.get(position).getNONPRDHED_ADDDATE());
        customer.setText(deb.getCustNameByCode(list.get(position).getNONPRDHED_DEBCODE()));

        return row;
    }
}
