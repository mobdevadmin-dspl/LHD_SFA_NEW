package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.TranSODet;

import java.util.ArrayList;

//pubudu
public class DiscountAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    ArrayList<TranSODet> list;

  /*  public DiscountAdapter(Context context, ArrayList<TranSODet> list) {

        super(context, R.layout.row_free_items, list);
        this.context = context;
        this.list = list;
    }*/
  public DiscountAdapter(Context context, ArrayList<TranSODet> list){
      this.inflater = LayoutInflater.from(context);
      this.list = list;

  }
    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }

    @Override
    public TranSODet getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView ==null) {
            viewHolder = new ViewHolder();
            convertView =inflater.inflate(R.layout.row_free_items,parent,false);

            viewHolder.code = (TextView) convertView.findViewById(R.id.tv_item_code);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_item_name);
            viewHolder.alloc = (TextView) convertView.findViewById(R.id.tv_alloc);

            viewHolder.code.setText("Item code: " + list.get(position).getFTRANSODET_ITEMCODE());
            viewHolder.name.setText("QTY: " + list.get(position).getFTRANSODET_QTY());
            viewHolder.alloc.setText(list.get(position).getFTRANSODET_DISAMT());

        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        return convertView;
    }

    private  static  class  ViewHolder{
        RelativeLayout layout;
        TextView code;
        TextView name;
        TextView alloc;
    }

}
