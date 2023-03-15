package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.PreProduct;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/11/2016.
 */

public class Image_serach_Adapter extends BaseAdapter {

    private LayoutInflater inflater;

    ArrayList<PreProduct> list;


    public Image_serach_Adapter(Context context, ArrayList<PreProduct> list){
        this.inflater = LayoutInflater.from(context);
        this.list = list;

    }
    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }

    @Override
    public PreProduct getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView ==null){
            viewHolder =new ViewHolder();

            convertView =inflater.inflate(R.layout.finac_image_search_adapter,parent,false);
            viewHolder.layout=(RelativeLayout)convertView.findViewById(R.id.linearLayout);
            viewHolder.itemCode=(TextView) convertView.findViewById(R.id.tv_item_code);
            viewHolder.ItemName =(TextView) convertView.findViewById(R.id.tv_item_name);
            viewHolder.QOH =(TextView) convertView.findViewById(R.id.tv_item_QOH);
            viewHolder.Img = (ImageView) convertView.findViewById(R.id.img_view);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try
        {
            viewHolder.Img.clearAnimation();




            String sPath = Environment.getExternalStorageDirectory() + "/FINAC_IMAGES/" + list.get(position).getPREPRODUCT_ITEMCODE().trim() + ".jpg";
            Bitmap bitmap = BitmapFactory.decodeFile(sPath);
            viewHolder.Img.setImageBitmap(Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.08), (int)(bitmap.getHeight()*0.08), true));
            //Log.v("IMAGE VIEW",);


        }catch (Exception e)
        {
            viewHolder.Img.setImageResource(0);
        }


            //dhanushika---------------
        viewHolder.itemCode.setText(list.get(position).getPREPRODUCT_ITEMCODE());
        viewHolder.ItemName.setText(list.get(position).getPREPRODUCT_ITEMNAME());
        viewHolder.QOH.setText(list.get(position).getPREPRODUCT_QOH());



        return convertView;
    }
    private  static  class  ViewHolder{
        RelativeLayout layout;
        TextView itemCode;
        TextView ItemName;
        TextView QOH;
        ImageView Img;
    }

}
