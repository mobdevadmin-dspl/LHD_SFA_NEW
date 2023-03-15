package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.Locations;

import java.util.ArrayList;

public class LocationAdapter extends BaseAdapter {

    Context context;
    ArrayList<Locations> list;

    public LocationAdapter(Context context, ArrayList<Locations> list) {
        //super(context, textViewResourceId, list);
        this.context = context;
        this.list = list;

    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_location, parent, false);

        TextView location = (TextView) row.findViewById(R.id.row_locationName);
        TextView loccode= (TextView) row.findViewById(R.id.row_locationcode);

        location.setText(list.get(position).getFLOCATIONS_LOC_NAME());
        loccode.setText(list.get(position).getFLOCATIONS_LOC_CODE());



        return row;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}
