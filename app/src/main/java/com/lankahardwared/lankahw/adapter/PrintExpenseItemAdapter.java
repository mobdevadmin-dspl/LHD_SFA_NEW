package com.lankahardwared.lankahw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.ReferenceNum;
import com.lankahardwared.lankahw.data.ExpensePrintDS;
import com.lankahardwared.lankahw.model.ExpesePrintPre;

import java.util.ArrayList;

public class PrintExpenseItemAdapter extends ArrayAdapter<ExpesePrintPre> {
    Context context;
    ArrayList<ExpesePrintPre> list;
    int totcases = 0;
    int totpieces = 0;
    int StrPrintCaseQtyVal = 0;
    ReferenceNum referenceNum;
    private ExpensePrintDS dsexpensePrintDS;

    public PrintExpenseItemAdapter(Context context, ArrayList<ExpesePrintPre> list) {

        super(context, R.layout.row_printexpenseitems_listview, list);
        this.context = context;
        this.list = list;
        dsexpensePrintDS = new ExpensePrintDS(context);
        referenceNum = new ReferenceNum(context);
    }

    @Override
    public View getView(int position, final View convertView,
                        final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;
        ArrayList<ExpesePrintPre> expenseprintList;
        expenseprintList = dsexpensePrintDS.getAllExpenseListPreview(referenceNum.getCurrentRefNo(context.getResources().getString(R.string.NumVal)));
        int ll = expenseprintList.size();

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_printexpenseitems_listview, parent, false);

        LinearLayout layout = (LinearLayout) row.findViewById(R.id.linearLayout);
        TextView itemname = (TextView) row.findViewById(R.id.printitemname);
        TextView printindex = (TextView) row.findViewById(R.id.printindex);
        TextView amount = (TextView) row.findViewById(R.id.printamount);

        itemname.setText(list.get(position).getEXPENSE_PRINT_EXPNAME());
        amount.setText(list.get(position).getEXPENSE_PRINT_AMT());
        position = position + 1;
        String pos = Integer.toString(position);
        printindex.setText(pos + ".");


        return row;
    }
}
