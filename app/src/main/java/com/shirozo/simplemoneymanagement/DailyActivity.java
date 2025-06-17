package com.shirozo.simplemoneymanagement;

import android.os.Bundle;
import android.widget.ListView;

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

        DatabaseHelper db = new DatabaseHelper(this);

        ArrayList<Transaction> transactions = db.getAll();

        ListView listView = findViewById(R.id.daily_summary);
        listView.setDivider(null);
        listView.setDividerHeight(10);
        DailyAdapter adapter = new DailyAdapter(this, transactions);
        listView.setAdapter(adapter);

        FloatingActionButton button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(v -> {
            CustomModal modal = new CustomModal(this);
            modal.setOnAddedListener(() -> {
                ArrayList<Transaction> updatedData = db.getAll();
                DailyAdapter newAdapter = new DailyAdapter(this, updatedData);
                listView.setAdapter(newAdapter);
            });
            modal.show();
        });
    }
}