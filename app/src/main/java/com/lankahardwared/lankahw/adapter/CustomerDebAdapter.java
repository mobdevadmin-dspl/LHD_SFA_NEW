package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.FDDbNote;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomerDebAdapter extends ArrayAdapter<FDDbNote> {
	Context context;
	ArrayList<FDDbNote> list;

	public CustomerDebAdapter(Context context, ArrayList<FDDbNote> list) {

		super(context, R.layout.row_rec_cus_debt, list);
		this.context = context;
		this.list = list;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {

		View row = null;
		try {

			LayoutInflater inflater = null;

			row = null;

			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.row_rec_cus_debt, parent, false);

			TextView txtRefno = (TextView) row.findViewById(R.id.txtRefno);
			TextView txtDate = (TextView) row.findViewById(R.id.txtDate);
			TextView txtRepName = (TextView) row.findViewById(R.id.txtRepName);
			TextView txtEntRemark = (TextView) row.findViewById(R.id.txtEntRemark);
			TextView txtAge = (TextView) row.findViewById(R.id.txtAge);
			TextView txtAmount = (TextView) row.findViewById(R.id.txtAmount);

			txtRefno.setText(list.get(position).getFDDBNOTE_REFNO());
			txtDate.setText(list.get(position).getFDDBNOTE_TXN_DATE());
			txtRepName.setText(list.get(position).getFDDBNOTE_REPNAME());			
			txtEntRemark.setText(list.get(position).getFDDBNOTE_ENTREMARK());
			txtAge.setText(Daybetween(list.get(position).getFDDBNOTE_TXN_DATE()));

			txtAmount.setText(
					getBalance(list.get(position).getFDDBNOTE_TOT_BAL(), list.get(position).getFDDBNOTE_TOT_BAL1()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return row;
	}

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/

	public String getBalance(String totBal, String totBal1) {

		return String.format("%,.2f", (Double.parseDouble(totBal) - Double.parseDouble(totBal1)));
	}

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/

	public String Daybetween(String sDate) throws ParseException {
		/*
		 * Modified by Rashmi 2017/05/02
		 */
		long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
		
		
		
//		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//	
//		Date date;
//		int txn = 0;
//		try {
//			date = (Date)formatter.parse(sDate);
//			System.out.println("receipt date is " +date.getTime());
//			 txn = (int) (date.getTime());
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date,cDate;
		long txn = 0;
		int current =0;
		try {
			date = (Date)formatter.parse(sDate);
			System.out.println("receipt date is " +date.getTime());
			 txn = date.getTime();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//int current = (int) (new Date().getTime());
		//int numOfDays = ((int) (System.currentTimeMillis() - txn) / (int) DAY_IN_MILLIS);
		int numOfDays =   (int) ((System.currentTimeMillis()  - txn) / DAY_IN_MILLIS);
		//int numOfDays = ((int) (current - txn) / (int) DAY_IN_MILLIS);

		return String.valueOf(numOfDays);
	}
}
