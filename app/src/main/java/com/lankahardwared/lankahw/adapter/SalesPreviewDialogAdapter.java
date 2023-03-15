package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.TranIssDS;
import com.lankahardwared.lankahw.model.TranIss;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

public class SalesPreviewDialogAdapter extends ArrayAdapter<TranIss> {
    Context context;
    ArrayList<TranIss> list;
    String refno;
    BigDecimal disc;

    public SalesPreviewDialogAdapter(Context context, ArrayList<TranIss> list, String refno, BigDecimal disc) {

        super(context, R.layout.row_printitems_listview, list);
        this.context = context;
        this.list = list;
        this.refno = refno;
        this.disc = disc;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_printitems_listview, parent, false);
            holder = new ViewHolder();
            holder.lblItemname = (TextView) convertView.findViewById(R.id.printitemnameVan);
            holder.lblPieceqty = (TextView) convertView.findViewById(R.id.printpiecesQtyVan);
            holder.lblAmount = (TextView) convertView.findViewById(R.id.printamountVan);
            //holder.lblPrice = (TextView) convertView.findViewById(R.id.printprice);
            holder.lblPrintindex = (TextView) convertView.findViewById(R.id.printindexVan);
            //holder.lblBrand = (TextView) convertView.findViewById(R.id.printVanBrand);
            holder.lblMrp = (TextView) convertView.findViewById(R.id.printVanMRP);
            //holder.lblSizeList = (TextView) convertView.findViewById(R.id.sizeqtyVan);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.lblItemname.setText(list.get(position).getITEMCODE());
        holder.lblPieceqty.setText(list.get(position).getQTY());
        holder.lblPrice.setText(getSellprice(list.get(position).getPRICE()));
        holder.lblAmount.setText(getAmount(holder.lblPrice.getText().toString(), list.get(position).getQTY()));
        holder.lblBrand.setText(list.get(position).getBrand());
        holder.lblMrp.setText(list.get(position).getPRICE());
        String s = new TranIssDS(context).getSizecodeString(list.get(position).getITEMCODE(), list.get(position).getPRICE(), refno);

        holder.lblSizeList.setText(s);
        position = position + 1;
        String pos = Integer.toString(position);
        holder.lblPrintindex.setText(pos + ". ");

        return convertView;
    }

    public String getSellprice(String Price) {
        return String.format(Locale.US, "%.2f", (new BigDecimal(Price).divide(new BigDecimal(100))).multiply(new BigDecimal(100).subtract(disc)));
    }

    public String getAmount(String qty, String price) {
        return String.format(Locale.US, "%.2f", Double.parseDouble(qty) * Double.parseDouble(price));
    }

    public static class ViewHolder {
        TextView lblItemname;
        TextView lblPieceqty;
        TextView lblAmount;
        TextView lblPrice;
        TextView lblPrintindex;
        TextView lblBrand;
        TextView lblMrp;
        TextView lblSizeList;
    }
}
