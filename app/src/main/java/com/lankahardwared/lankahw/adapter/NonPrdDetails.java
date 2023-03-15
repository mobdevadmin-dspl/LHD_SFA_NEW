package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.ReasonDS;
import com.lankahardwared.lankahw.model.fDaynPrdDet;

import java.util.ArrayList;

public class NonPrdDetails extends ArrayAdapter<fDaynPrdDet> {
    Context context;
    ArrayList<fDaynPrdDet> list;

    public NonPrdDetails(Context context, ArrayList<fDaynPrdDet> list) {
        super(context, R.layout.row_nonproduct_det, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_nonproduct_det, parent, false);

        TextView Itemname = (TextView) row.findViewById(R.id.row_nonprd_refno);
        TextView Itemcode = (TextView) row.findViewById(R.id.row_nonprd_reason);
        //TextView _id = (TextView) row.findViewById(R.id._id);

        //DebtorDS deb = new DebtorDS(getContext());
        ReasonDS reason = new ReasonDS(getContext());

        //Itemname.setText(deb.getDebNameByCode(list.get(position).getNONPRDDET_REASON_CODE()));
//        Itemname.setText(reason.getReaNameByCode(list.get(position).getNONPRDDET_REASON_CODE()));
        Itemname.setText(list.get(position).getNONPRDDET_REASON_CODE());
        Itemcode.setText(list.get(position).getNONPRDDET_REASON());
        //_id.setText(list.get(position).getNONPRDDET_ID());

        return row;
    }

}
