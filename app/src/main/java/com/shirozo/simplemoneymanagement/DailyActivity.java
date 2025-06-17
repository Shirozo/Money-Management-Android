package com.shirozo.simplemoneymanagement;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shirozo.simplemoneymanagement.adapter.DailyAdapter;
import com.shirozo.simplemoneymanagement.classes.Transaction;
import com.shirozo.simplemoneymanagement.database.DatabaseHelper;

import java.util.ArrayList;

public class DailyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        FloatingActionButton button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(v -> {
            CustomModal modal = new CustomModal(this);
            modal.setOnAddedListener(this::loadData);
            modal.show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        DatabaseHelper db = new DatabaseHelper(this);

        String[] cardData = db.getPerDaySummary(getIntent().getStringExtra("date"));

        TextView income = findViewById(R.id.daily_income);
        TextView expense = findViewById(R.id.daily_expenses);
        TextView saved = findViewById(R.id.daily_saved);

        income.setText("₱ " + cardData[0]);
        expense.setText("₱ " + cardData[1]);
        saved.setText("₱ " + cardData[2]);

        ArrayList<Transaction> transactions = db.getPerDay(getIntent().getStringExtra("date"));

        ListView listView = findViewById(R.id.daily_summary);
        listView.setDivider(null);
        listView.setDividerHeight(10);
        DailyAdapter adapter = new DailyAdapter(this, transactions);
        listView.setAdapter(adapter);
    }
}