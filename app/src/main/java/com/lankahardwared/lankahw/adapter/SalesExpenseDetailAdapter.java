package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.model.Expense;

import java.util.ArrayList;

public class SalesExpenseDetailAdapter extends ArrayAdapter<Expense> {
    Context context;
    ArrayList<Expense> list;

    public SalesExpenseDetailAdapter(Context context, ArrayList<Expense> list) {
        super(context, R.layout.row_sales_expense_detail, list);
        this.context = context;
        this.list = list;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_sales_expense_detail, parent, false);

        TextView Itemname = (TextView) row.findViewById(R.id.row_sales_expensename);
        TextView Itemcode = (TextView) row.findViewById(R.id.row_sales_expensecode);

        Itemname.setText(list.get(position).getFEXPENSE_NAME());
        Itemcode.setText(list.get(position).getFEXPENSE_CODE());

        return row;
    }
}
