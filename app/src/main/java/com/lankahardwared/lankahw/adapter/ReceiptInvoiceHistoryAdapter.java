package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.DebtorDS;
import com.lankahardwared.lankahw.data.RecDetDS;
import com.lankahardwared.lankahw.model.RecHed;

import java.util.ArrayList;

public class ReceiptInvoiceHistoryAdapter  extends ArrayAdapter<RecHed> {
	Context context;
	ArrayList<RecHed> list;
	
	public ReceiptInvoiceHistoryAdapter(Context context, ArrayList<RecHed> list){
		
		super(context, R.layout.row_receipt_history, list);
		this.context = context;
		this.list = list;		
	}
	
	
	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		
		LayoutInflater inflater = null;
		View row = null;
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		row = inflater.inflate(R.layout.row_receipt_history, parent, false);
		
		TextView refno = (TextView) row.findViewById(R.id.refno);
		TextView date = (TextView) row.findViewById(R.id.date);
		TextView route = (TextView) row.findViewById(R.id.route);
		TextView amount = (TextView) row.findViewById(R.id.amount);
		TextView customer =(TextView) row.findViewById(R.id.customer);
		TextView hoverloc =(TextView) row.findViewById(R.id.handlocation);
		
		DebtorDS debtorDS =new DebtorDS(context);
		//CostDS costDS = new CostDS(context);
		refno.setText(list.get(position).getFPRECHED_REFNO());
		date.setText(list.get(position).getFPRECHED_ADDDATE());
		//route.setText(" ");
		amount.setText(list.get(position).getFPRECHED_TOTALAMT());
		//amount.setText(new RecDetDS(context).getAllocAmtByReceiptRefNo(list.get(position).getFPRECHED_REFNO()));
		customer.setText(debtorDS.getCustNameByCode(list.get(position).getFPRECHED_DEBCODE()));

		return row;
	}
}
