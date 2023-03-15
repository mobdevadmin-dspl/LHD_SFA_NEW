package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.fOrdDetSummary;
import com.lankahardwared.lankahw.data.fOrdhedSummary;

import java.util.ArrayList;

/**
 * Created by Dhanushika on 2/12/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context  context;
    private LayoutInflater inflater;
    private ArrayList<fOrdhedSummary>_listDataHeader;


    public ExpandableListAdapter(Context context, ArrayList<fOrdhedSummary> listDataHeader) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this._listDataHeader = listDataHeader;

    }


    @Override
    public int getChildrenCount(int groupPosition) {
         ArrayList<fOrdDetSummary>ordDetSummaryArrayList=_listDataHeader.get(groupPosition).getlistChildData();
        //return ordDetSummaryArrayList.size()+1;

        if (ordDetSummaryArrayList != null && ordDetSummaryArrayList.size() > 0) {
            if (ordDetSummaryArrayList.size() < 2)
                return ordDetSummaryArrayList.size() + 1;
            return ordDetSummaryArrayList.size();

        }
       return 0;
    }



    @Override
    public fOrdDetSummary getChild(int groupPosition, int childPosition) {
        if (childPosition == 0) {
            return null;
        } else {

            if(_listDataHeader!=null) {
                ArrayList<fOrdDetSummary> ordDetSummaryArrayList = _listDataHeader.get(groupPosition).getlistChildData();
                if (ordDetSummaryArrayList != null && ordDetSummaryArrayList.size() > 0) {
                   return ordDetSummaryArrayList.get(childPosition - 1);
                }
                return _listDataHeader.get(groupPosition).getlistChildData().get(childPosition);

            }return  null;
            /*ArrayList<fOrdDetSummary> ordDetSummaryArrayList = _listDataHeader.get(groupPosition).getlistChildData();
            return ordDetSummaryArrayList.get(childPosition);*/
        }
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (childPosition == 0)
            return 0;
        return groupPosition;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = inflater.inflate(R.layout.child_items, parent, false);


            childHolder.headerContainer =(RelativeLayout) convertView.findViewById(R.id.rl_header_container);
            childHolder.layout=(RelativeLayout)convertView.findViewById(R.id.r2_child) ;
            childHolder.Itemcode = (TextView) convertView.findViewById(R.id.txtItemcodeRow);
            childHolder.qty_childItem = (TextView) convertView.findViewById(R.id.txtqtyRow);
            childHolder.date_childItem = (TextView) convertView.findViewById(R.id.txtDateRow);
            childHolder.amt_childItem = (TextView) convertView.findViewById(R.id.txtAmtRow);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }


        if (childPosition == 0) {
            childHolder.headerContainer.setVisibility(View.VISIBLE);

        } else  {
            childHolder.headerContainer.setVisibility(View.GONE);

            fOrdDetSummary detailInfo = getChild(groupPosition, childPosition);

         if(detailInfo!=null){

             childHolder.Itemcode.setText(detailInfo.getItemCode().trim());
             childHolder.qty_childItem.setText(detailInfo.getQty().trim());
             childHolder.date_childItem.setText(detailInfo.getTxnDate());
             childHolder.amt_childItem.setText(detailInfo.getAmt().trim());
         }


        }
        return convertView;
    }


    @Override
    public fOrdhedSummary getGroup(int groupPosition) {

        if (_listDataHeader != null) {
            return _listDataHeader.get(groupPosition);
        }
        return null;
    }
    @Override
    public int getGroupCount() {
        if (_listDataHeader != null) {
            return _listDataHeader.size();
        }
        return 0;
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;

        if (convertView == null) {
            groupHolder=new GroupHolder();
            convertView = inflater.inflate(R.layout.group_items, null);

            groupHolder.RefNoHed = (TextView) convertView.findViewById(R.id.header_reFNo);
            groupHolder.dateHed = (TextView) convertView.findViewById(R.id.txtDATE_header);
            groupHolder.debCodeHed = (TextView) convertView.findViewById(R.id.txt_debcode_header);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        final fOrdhedSummary headerInfo =getGroup(groupPosition);
        groupHolder.RefNoHed.setText(headerInfo.getRefNo().trim());
        groupHolder.dateHed.setText(headerInfo.getTxnDate().trim());
        groupHolder.debCodeHed.setText(headerInfo.getDebCode().trim());
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private static class ChildHolder {
        RelativeLayout headerContainer;
        RelativeLayout layout;
        TextView Itemcode;
        TextView qty_childItem;
        TextView date_childItem;
        TextView amt_childItem;

    }


    private static  class  GroupHolder{
        TextView RefNoHed;
        TextView dateHed;
        RelativeLayout indicatorContainer;
        TextView debCodeHed;
    }

    public void setData(ArrayList<fOrdhedSummary> listDataHeader) {
        this._listDataHeader=listDataHeader;
        notifyDataSetChanged();
    }
}
