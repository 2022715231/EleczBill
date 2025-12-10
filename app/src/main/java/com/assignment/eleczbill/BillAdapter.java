package com.assignment.eleczbill;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.List;

public class BillAdapter extends ArrayAdapter<String> {

    public BillAdapter(Context ctx, List<String> data) {
        super(ctx, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.row_bill, parent, false);
        }

        TextView text = convertView.findViewById(R.id.textBill);
        text.setText(getItem(position));

        return convertView;
    }
}
