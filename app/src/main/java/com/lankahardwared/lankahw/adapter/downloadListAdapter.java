package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.Control;

import java.util.ArrayList;


public class downloadListAdapter extends ArrayAdapter<Control> {
    Context context;
    ArrayList<Control> list;


    public downloadListAdapter(Context context, ArrayList<Control> list) {
        super(context, R.layout.row_download_view, list);
        this.context = context;
        this.list = list;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        row = inflater.inflate(R.layout.row_download_view, parent, false);

        TextView title = (TextView) row.findViewById(R.id.row_title);
        TextView count = (TextView) row.findViewById(R.id.row_count);
        title.setText(list.get(position).getFCONTROL_COM_NAME());
        if(!(list.get(position).getFCONTROL_COM_ADD1().equals(list.get(position).getFCONTROL_COM_ADD2())) || (list.get(position).getFCONTROL_COM_ADD1().equals("0")))
        {

            count.setTextColor(Color.parseColor("#EE0000"));
            count.setText(list.get(position).getFCONTROL_COM_ADD1()+"/"+list.get(position).getFCONTROL_COM_ADD2());
        }
        else
        {
            count.setTextColor(Color.parseColor("#4CAF50"));
            count.setText(list.get(position).getFCONTROL_COM_ADD1()+"/"+list.get(position).getFCONTROL_COM_ADD2());
        }

        return row;
    }

//    private String getCustomText(String count1,String count2){
//        String fld = "", clr = "#EE0000";
//        fld = count1+"/"+count2;
//        if(!(count1.equals(count2)) || count1.equals("0"))
//            clr = "#EE0000";
//        else
//            clr = "#4CAF50";
//
//        fld = fld.split("/")[0] + "<font color='"+clr+"'><b>"+fld.split("/")[1]+"</b></font>";
//
//        return fld;
//    }
}
