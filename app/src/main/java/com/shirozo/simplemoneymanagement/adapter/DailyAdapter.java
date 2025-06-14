package com.shirozo.simplemoneymanagement.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shirozo.simplemoneymanagement.R;
import com.shirozo.simplemoneymanagement.classes.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DailyAdapter extends BaseAdapter {

    private final ArrayList<Transaction> transactions;

    private final Context context;
    public DailyAdapter(Context context, ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        this.context = context;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return transactions.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.daily_report_card, null);

            ImageView image = convertView.findViewById(R.id.transaction_image);
            TextView transaction = convertView.findViewById(R.id.transaction);
            TextView transaction_date = convertView.findViewById(R.id.transaction_date);
            TextView transaction_amount = convertView.findViewById(R.id.transaction_amount);

            Transaction transaction_data = transactions.get(position);

            if (transaction_data.getType() == 0) {
                transaction_amount.setTextColor(Color.parseColor("#16A34A"));
                transaction_amount.setText(String.format("+ %s", transaction_data.getTransaction_amount()));
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.trending_up));
            } else {
                //"#DC2626"
                transaction_amount.setTextColor(Color.parseColor("#DC2626"));
                transaction_amount.setText(String.format("- %s", transaction_data.getTransaction_amount()));
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.trending_down));
            }

            transaction.setText(transaction_data.getTransaction());

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            transaction_date.setText(sdf.format(transaction_data.getTransaction_date()));

        }
        return convertView;
    }
}
