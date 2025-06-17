package com.shirozo.simplemoneymanagement;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shirozo.simplemoneymanagement.adapter.ListViewAdapter;
import com.shirozo.simplemoneymanagement.classes.Money;
import com.shirozo.simplemoneymanagement.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Objects;

public class MonthlyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_montly);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LoadData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadData();
    }

    public void LoadData() {
        DatabaseHelper db = new DatabaseHelper(this);

        String[] cardData = db.getPerMonthSummary(getIntent().getStringExtra("date"));

        TextView income = findViewById(R.id.monthly_income);
        TextView expense = findViewById(R.id.monthly_expenses);
        TextView saved = findViewById(R.id.monthly_saved);

        income.setText("₱ " + cardData[0]);
        expense.setText("₱ " + cardData[1]);
        saved.setText("₱ " + cardData[2]);

        ArrayList<Money> monthlies = db.getMonthData(Objects.requireNonNull(getIntent().getStringExtra("date")));

        int phase = 2;

        ListView listView = findViewById(R.id.daily_summary);
        listView.setDivider(null);
        listView.setDividerHeight(10);
        ListViewAdapter adapter = new ListViewAdapter(this, monthlies, phase);
        listView.setAdapter(adapter);
    }
}