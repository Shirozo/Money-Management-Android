package com.shirozo.simplemoneymanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shirozo.simplemoneymanagement.DailyActivity;
import com.shirozo.simplemoneymanagement.MonthlyActivity;
import com.shirozo.simplemoneymanagement.R;
import com.shirozo.simplemoneymanagement.classes.Money;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<Money> monthlies;
    private Context context;

    private final int phase;

    public ListViewAdapter(Context context, ArrayList<Money> monthlies, int phase) {
        this.monthlies = monthlies;
        this.context = context;
        this.phase = phase;
    }

    @Override
    public int getCount() {
        return monthlies.size();
    }

    @Override
    public Object getItem(int position) {
        return monthlies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monthlies.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.report_card, null);

            TextView date = convertView.findViewById(R.id.date);
            TextView income = convertView.findViewById(R.id.income);
            TextView expenses = convertView.findViewById(R.id.expenses);
            TextView status = convertView.findViewById(R.id.saved);


            String[] months = {
                    "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
            };

            String[] parts = monthlies.get(position).getDate().split("-");
            String newDate = "";
            if (phase == 1) {
                newDate = months[Integer.parseInt(parts[1]) - 1] + " " + parts[0];
            } else {
                newDate = parts[0] + " " +months[Integer.parseInt(parts[1]) - 1];
            }
            date.setText(newDate);

            DecimalFormat df = new DecimalFormat("#,###.##");

            income.setText(String.format("Income: ₱ %s", df.format(monthlies.get(position).getIncome())));
            expenses.setText(String.format("Expenses: ₱ %s", df.format(monthlies.get(position).getExpenses())));
            Float saved = monthlies.get(position).getSaved();
            status.setText(String.format(df.format(saved)));
            status.setTextColor(Color.parseColor(saved > 0 ? "#16A34A" : "#DC2626"));

            convertView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MonthlyActivity.class);
                if (phase != 1) {
                    intent = new Intent(context, DailyActivity.class);

                }
                intent.putExtra("date", String.valueOf(monthlies.get(position).getDate()));
                context.startActivity(intent);
            });
        }

        return  convertView;
    }
}
