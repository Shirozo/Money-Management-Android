package com.shirozo.simplemoneymanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.shirozo.simplemoneymanagement.classes.Money;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<Money> monthlies;
    private Context context;

    public ListViewAdapter(Context context, ArrayList<Money> monthlies) {
        this.monthlies = monthlies;
        this.context = context;
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

            DecimalFormat df = new DecimalFormat("#,###.##");

            date.setText(monthlies.get(position).getDate());
            income.setText(String.format("Income: ₱ %s", df.format(monthlies.get(position).getIncome())));
            expenses.setText(String.format("Expenses: ₱ %s", df.format(monthlies.get(position).getExpenses())));
            status.setText(String.format("Saved: ₱ %s", df.format(monthlies.get(position).getSaved())));

            convertView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MonthlyActivity.class);
                intent.putExtra("id", String.valueOf(monthlies.get(position).getId()));
                context.startActivity(intent);
            });
        }

        return  convertView;
    }
}
