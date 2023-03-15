package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.finvDetSummary;
import com.lankahardwared.lankahw.data.finvhedSummary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhanushika on 2/12/2018.
 */

public class InvoiceExpandableListAdapter extends BaseExpandableListAdapter {
    private Context  context;
    private List<finvhedSummary> _listDataHeader;
    private List<finvDetSummary>fOrddetSummaryArrayList;
    private LayoutInflater inflater;


    public InvoiceExpandableListAdapter(Context context, ArrayList<finvhedSummary> listDataHeader) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this._listDataHeader = listDataHeader;

    }


    @Override
    public int getChildrenCount(int groupPosition) {
         ArrayList<finvDetSummary>ordDetSummaryArrayList=_listDataHeader.get(groupPosition).getlistChildData();
       // return ordDetSummaryArrayList.size();

        if (ordDetSummaryArrayList != null && ordDetSummaryArrayList.size() > 0) {
            if (ordDetSummaryArrayList.size() < 2)
                return ordDetSummaryArrayList.size() + 1;
            return ordDetSummaryArrayList.size();
        }
      return 0;
    }



    @Override
    public finvDetSummary getChild(int groupPosition, int childPosition) {
        if (childPosition == 0) {
            return null;
        } else {

            if(_listDataHeader!=null) {
                ArrayList<finvDetSummary> ordDetSummaryArrayList = _listDataHeader.get(groupPosition).getlistChildData();
                if (ordDetSummaryArrayList != null && ordDetSummaryArrayList.size() > 0) {
                   return ordDetSummaryArrayList.get(childPosition - 1);
                }
                return _listDataHeader.get(groupPosition).getlistChildData().get(childPosition);


            }return  null;



       }
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (childPosition == 0)
            return 0;
        return childPosition;
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

           finvDetSummary detailInfo = getChild(groupPosition, childPosition);
   /*        finvDetSummary detailInfo = _listDataHeader.get(groupPosition).getlistChildData().get(childPosition);*/
             childHolder.Itemcode.setText(detailInfo.getItemCode().trim());
             childHolder.qty_childItem.setText(detailInfo.getQty().trim());
             childHolder.date_childItem.setText(detailInfo.getTxnDate());
             childHolder.amt_childItem.setText(detailInfo.getAmt().trim());

        }
        return convertView;
    }


    @Override
    public finvhedSummary getGroup(int groupPosition) {

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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
       // final finvhedSummary headerInfo =getGroup(groupPosition);

        if (convertView == null) {
            groupHolder=new GroupHolder();
            convertView = inflater.inflate(R.layout.group_items, parent, false);
             groupHolder.hed=(RelativeLayout)convertView.findViewById(R.id.rl_hed);
            groupHolder.RefNoHed = (TextView) convertView.findViewById(R.id.header_reFNo);
            groupHolder.dateHed = (TextView) convertView.findViewById(R.id.txtDATE_header);
            groupHolder.debCodeHed = (TextView) convertView.findViewById(R.id.txt_debcode_header);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        finvhedSummary headerInfo=_listDataHeader.get(groupPosition);
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
        RelativeLayout hed;
        TextView RefNoHed;
        TextView dateHed;
        TextView debCodeHed;
    }

   /* public void setData(ArrayList<finvhedSummary> listDataHeader) {
        this._listDataHeader=listDataHeader;
        notifyDataSetChanged();
    }*/
}
