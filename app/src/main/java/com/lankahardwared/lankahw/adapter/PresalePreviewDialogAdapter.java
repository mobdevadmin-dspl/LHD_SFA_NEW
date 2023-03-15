package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.TranIss;

import java.util.ArrayList;

public class PresalePreviewDialogAdapter extends ArrayAdapter<TranIss> {
    Context context;
    ArrayList<TranIss> list;
    String refno;

    public PresalePreviewDialogAdapter(Context context, ArrayList<TranIss> list, String refno) {

        super(context, R.layout.presale_listview_printrow, list);
        this.context = context;
        this.list = list;
        this.refno = refno;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.presale_listview_printrow, parent, false);
            holder = new ViewHolder();
            holder.lblItemname = (TextView) convertView.findViewById(R.id.printitemname);
            holder.lblPieceqty = (TextView) convertView.findViewById(R.id.printpiecesQty);
            holder.lblAmount = (TextView) convertView.findViewById(R.id.printamount);
            holder.lblPrintindex = (TextView) convertView.findViewById(R.id.printindex);
            holder.lblBrand = (TextView) convertView.findViewById(R.id.printPreDisc);
            holder.lblMrp = (TextView) convertView.findViewById(R.id.printPreMRP);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.lblItemname.setText(list.get(position).getITEMCODE());
        holder.lblPieceqty.setText(list.get(position).getQTY());
        holder.lblAmount.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getAMT())));
        holder.lblBrand.setText(list.get(position).getBrand());
        holder.lblMrp.setText(list.get(position).getPRICE());

        position = position + 1;
        String pos = Integer.toString(position);
        holder.lblPrintindex.setText(pos + ". ");

        return convertView;
    }

    public static class ViewHolder {
        TextView lblItemname;
        TextView lblPieceqty;
        TextView lblAmount;
        TextView lblPrintindex;
        TextView lblBrand;
        TextView lblMrp;
        TextView lblSizeList;
    }
}
