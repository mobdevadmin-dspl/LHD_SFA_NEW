package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.FDebTaxDS;
import com.lankahardwared.lankahw.data.ItemsDS;
import com.lankahardwared.lankahw.model.TranSODet;

import java.util.ArrayList;

public class PrintItemAdapter extends ArrayAdapter<TranSODet> {
    Context context;
    ArrayList<TranSODet> list;
    String refno;
    String debtorCode;

    public PrintItemAdapter(Context context, ArrayList<TranSODet> list, String refno, String debtorCode) {

        super(context, R.layout.presale_listview_printrow, list);
        this.context = context;
        this.list = list;
        this.refno = refno;
        this.debtorCode = debtorCode;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.presale_listview_printrow, parent, false);

        TextView itemname = (TextView) row.findViewById(R.id.printitemname);
        TextView pieceqty = (TextView) row.findViewById(R.id.printpiecesQty);
        TextView printindex = (TextView) row.findViewById(R.id.printindex);
        TextView mrp = (TextView) row.findViewById(R.id.printPreMRP);
        TextView disc = (TextView) row.findViewById(R.id.printPreDisc);
        TextView amount = (TextView) row.findViewById(R.id.printamount);
        TextView name = (TextView)row.findViewById(R.id.printName);

        itemname.setText(list.get(position).getFTRANSODET_ITEMCODE());
        pieceqty.setText(list.get(position).getFTRANSODET_QTY());
        double amt = Double.parseDouble(list.get(position).getFTRANSODET_AMT());
        double taxAmt = Double.parseDouble(list.get(position).getFTRANSODET_TAXAMT());
        double finalAmt = amt - taxAmt;

        // if Debtor is Tax enable

        if (list.get(position).getFTRANSODET_SELLPRICE().equals("0"))
        {
            amount.setText("0.00");
            mrp.setText("0.00");
        }
        else
        {
            if (new FDebTaxDS(context).isDebtorTax(debtorCode).equalsIgnoreCase("VAT"))
            {
                amount.setText(String.format("%.2f", finalAmt));
                mrp.setText(String.format("%.2f", Double.parseDouble(list.get(position).getFTRANSODET_SELLPRICE())));
            }
            else
            {
                amount.setText(list.get(position).getFTRANSODET_AMT());
                mrp.setText(String.format("%.2f", Double.parseDouble(list.get(position).getFTRANSODET_TSELLPRICE())));
            }

        }
        disc.setText(list.get(position).getFTRANSODET_SCHDISC());
        name.setText(new ItemsDS(context).getItemNameByCode(list.get(position).getFTRANSODET_ITEMCODE()));

        position = position + 1;
        String pos = Integer.toString(position);
        printindex.setText(pos + ". ");

        return row;
    }
}
