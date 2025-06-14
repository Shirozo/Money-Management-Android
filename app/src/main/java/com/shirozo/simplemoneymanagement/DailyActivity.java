package com.shirozo.simplemoneymanagement;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shirozo.simplemoneymanagement.adapter.DailyAdapter;
import com.shirozo.simplemoneymanagement.classes.Transaction;

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

        ArrayList<Transaction> transactions = new ArrayList<>();

        Transaction transaction = new Transaction(1, "Salary", "2025-06-14 23:45:00", "1000", 0);
        Transaction transaction1 = new Transaction(2, "Food", "2025-06-14 23:46:00", "400", 1);

        transactions.add(transaction);
        transactions.add(transaction1);

        ListView listView = findViewById(R.id.daily_summary);
        listView.setDivider(null);
        listView.setDividerHeight(10);
        DailyAdapter adapter = new DailyAdapter(this, transactions);
        listView.setAdapter(adapter);
    }
}